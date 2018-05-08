// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import ch.qos.logback.core.rolling.RolloverFailure;


public class FileStoreUtil
{

    static final java.lang.String PATH_CLASS_STR = "java.nio.file.Path";

    static final java.lang.String FILES_CLASS_STR = "java.nio.file.Files";

    public static  boolean areOnSameFileStore( java.io.File a, java.io.File b )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        if (!a.exists()) {
            throw new java.lang.IllegalArgumentException( "File [" + a + "] does not exist." );
        }
        if (!b.exists()) {
            throw new java.lang.IllegalArgumentException( "File [" + b + "] does not exist." );
        }
        try {
            java.nio.file.Path pathA = a.toPath();
            java.nio.file.Path pathB = b.toPath();
            java.nio.file.FileStore fileStoreA = Files.getFileStore( pathA );
            java.nio.file.FileStore fileStoreB = Files.getFileStore( pathB );
            return fileStoreA.equals( fileStoreB );
        } catch ( java.lang.Exception e ) {
            throw new ch.qos.logback.core.rolling.RolloverFailure( "Failed to check file store equality for [" + a + "] and [" + b + "]", e );
        }
    }

}
