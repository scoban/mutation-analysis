// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import java.io.File;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.util.EnvUtil;
import ch.qos.logback.core.util.FileUtil;


public class RenameUtil extends ch.qos.logback.core.spi.ContextAwareBase
{

    static java.lang.String RENAMING_ERROR_URL = CoreConstants.CODES_URL + "#renamingError";

    public  void rename( java.lang.String src, java.lang.String target )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        if (src.equals( target )) {
            addWarn( "Source and target files are the same [" + src + "]. Skipping." );
            return;
        }
        java.io.File srcFile = new java.io.File( src );
        if (srcFile.exists()) {
            java.io.File targetFile = new java.io.File( target );
            createMissingTargetDirsIfNecessary( targetFile );
            addInfo( "Renaming file [" + srcFile + "] to [" + targetFile + "]" );
            boolean result = srcFile.renameTo( targetFile );
            if (!result) {
                addWarn( "Failed to rename file [" + srcFile + "] as [" + targetFile + "]." );
                java.lang.Boolean areOnDifferentVolumes = areOnDifferentVolumes( srcFile, targetFile );
                if (Boolean.TRUE.equals( areOnDifferentVolumes )) {
                    addWarn( "Detected different file systems for source [" + src + "] and target [" + target + "]. Attempting rename by copying." );
                    renameByCopying( src, target );
                    return;
                } else {
                    addWarn( "Please consider leaving the [file] option of " + (ch.qos.logback.core.rolling.RollingFileAppender.class).getSimpleName() + " empty." );
                    addWarn( "See also " + RENAMING_ERROR_URL );
                }
            }
        } else {
            throw new ch.qos.logback.core.rolling.RolloverFailure( "File [" + src + "] does not exist." );
        }
    }

     java.lang.Boolean areOnDifferentVolumes( java.io.File srcFile, java.io.File targetFile )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        if (!EnvUtil.isJDK7OrHigher()) {
            return false;
        }
        java.io.File parentOfTarget = targetFile.getAbsoluteFile().getParentFile();
        if (parentOfTarget == null) {
            addWarn( "Parent of target file [" + targetFile + "] is null" );
            return null;
        }
        if (!parentOfTarget.exists()) {
            addWarn( "Parent of target file [" + targetFile + "] does not exist" );
            return null;
        }
        try {
            boolean onSameFileStore = FileStoreUtil.areOnSameFileStore( srcFile, parentOfTarget );
            return !onSameFileStore;
        } catch ( ch.qos.logback.core.rolling.RolloverFailure rf ) {
            addWarn( "Error while checking file store equality", rf );
            return null;
        }
    }

    public  void renameByCopying( java.lang.String src, java.lang.String target )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        ch.qos.logback.core.util.FileUtil fileUtil = new ch.qos.logback.core.util.FileUtil( getContext() );
        fileUtil.copy( src, target );
        java.io.File srcFile = new java.io.File( src );
        if (!srcFile.delete()) {
            addWarn( "Could not delete " + src );
        }
    }

     void createMissingTargetDirsIfNecessary( java.io.File toFile )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        boolean result = FileUtil.createMissingParentDirectories( toFile );
        if (!result) {
            throw new ch.qos.logback.core.rolling.RolloverFailure( "Failed to create parent directories for [" + toFile.getAbsolutePath() + "]" );
        }
    }

    public  java.lang.String toString()
    {
        return "c.q.l.co.rolling.helper.RenameUtil";
    }

}
