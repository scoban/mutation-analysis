// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import static ch.qos.logback.core.CoreConstants.UNBOUND_HISTORY;
import static ch.qos.logback.core.CoreConstants.UNBOUNDED_TOTAL_SIZE_CAP;
import java.io.File;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.ArchiveRemover;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.Compressor;
import ch.qos.logback.core.rolling.helper.FileFilterUtil;
import ch.qos.logback.core.rolling.helper.FileNamePattern;
import ch.qos.logback.core.rolling.helper.RenameUtil;
import ch.qos.logback.core.util.FileSize;


public class TimeBasedRollingPolicy<E> extends ch.qos.logback.core.rolling.RollingPolicyBase implements ch.qos.logback.core.rolling.TriggeringPolicy<E>
{

    static final java.lang.String FNP_NOT_SET = "The FileNamePattern option must be set before using TimeBasedRollingPolicy. ";

    ch.qos.logback.core.rolling.helper.FileNamePattern fileNamePatternWithoutCompSuffix;

    private ch.qos.logback.core.rolling.helper.Compressor compressor;

    private ch.qos.logback.core.rolling.helper.RenameUtil renameUtil = new ch.qos.logback.core.rolling.helper.RenameUtil();

    java.util.concurrent.Future<?> compressionFuture;

    java.util.concurrent.Future<?> cleanUpFuture;

    private int maxHistory = ch.qos.logback.core.CoreConstants.UNBOUND_HISTORY;

    protected ch.qos.logback.core.util.FileSize totalSizeCap = new ch.qos.logback.core.util.FileSize( ch.qos.logback.core.CoreConstants.UNBOUNDED_TOTAL_SIZE_CAP );

    private ch.qos.logback.core.rolling.helper.ArchiveRemover archiveRemover;

    ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy<E> timeBasedFileNamingAndTriggeringPolicy;

    boolean cleanHistoryOnStart = false;

    public  void start()
    {
        renameUtil.setContext( this.context );
        if (fileNamePatternStr != null) {
            fileNamePattern = new ch.qos.logback.core.rolling.helper.FileNamePattern( fileNamePatternStr, this.context );
            determineCompressionMode();
        } else {
            addWarn( FNP_NOT_SET );
            addWarn( CoreConstants.SEE_FNP_NOT_SET );
            throw new java.lang.IllegalStateException( FNP_NOT_SET + CoreConstants.SEE_FNP_NOT_SET );
        }
        compressor = new ch.qos.logback.core.rolling.helper.Compressor( compressionMode );
        compressor.setContext( context );
        fileNamePatternWithoutCompSuffix = new ch.qos.logback.core.rolling.helper.FileNamePattern( Compressor.computeFileNameStrWithoutCompSuffix( fileNamePatternStr, compressionMode ), this.context );
        addInfo( "Will use the pattern " + fileNamePatternWithoutCompSuffix + " for the active file" );
        if (compressionMode == CompressionMode.ZIP) {
            java.lang.String zipEntryFileNamePatternStr = transformFileNamePattern2ZipEntry( fileNamePatternStr );
            zipEntryFileNamePattern = new ch.qos.logback.core.rolling.helper.FileNamePattern( zipEntryFileNamePatternStr, context );
        }
        if (timeBasedFileNamingAndTriggeringPolicy == null) {
            timeBasedFileNamingAndTriggeringPolicy = new ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy<E>();
        }
        timeBasedFileNamingAndTriggeringPolicy.setContext( context );
        timeBasedFileNamingAndTriggeringPolicy.setTimeBasedRollingPolicy( this );
        timeBasedFileNamingAndTriggeringPolicy.start();
        if (!timeBasedFileNamingAndTriggeringPolicy.isStarted()) {
            addWarn( "Subcomponent did not start. TimeBasedRollingPolicy will not start." );
            return;
        }
        if (maxHistory != ch.qos.logback.core.CoreConstants.UNBOUND_HISTORY) {
            archiveRemover = timeBasedFileNamingAndTriggeringPolicy.getArchiveRemover();
            archiveRemover.setMaxHistory( maxHistory );
            archiveRemover.setTotalSizeCap( totalSizeCap.getSize() );
            if (cleanHistoryOnStart) {
                addInfo( "Cleaning on start up" );
                java.util.Date now = new java.util.Date( timeBasedFileNamingAndTriggeringPolicy.getCurrentTime() );
                cleanUpFuture = archiveRemover.cleanAsynchronously( now );
            }
        } else {
            if (!isUnboundedTotalSizeCap()) {
                addWarn( "'maxHistory' is not set, ignoring 'totalSizeCap' option with value [" + totalSizeCap + "]" );
            }
        }
        super.start();
    }

    protected  boolean isUnboundedTotalSizeCap()
    {
        return totalSizeCap.getSize() == ch.qos.logback.core.CoreConstants.UNBOUNDED_TOTAL_SIZE_CAP;
    }

    public  void stop()
    {
        if (!isStarted()) {
            return;
        }
        waitForAsynchronousJobToStop( compressionFuture, "compression" );
        waitForAsynchronousJobToStop( cleanUpFuture, "clean-up" );
        super.stop();
    }

    private  void waitForAsynchronousJobToStop( java.util.concurrent.Future<?> aFuture, java.lang.String jobDescription )
    {
        if (aFuture != null) {
            try {
                aFuture.get( CoreConstants.SECONDS_TO_WAIT_FOR_COMPRESSION_JOBS, TimeUnit.SECONDS );
            } catch ( java.util.concurrent.TimeoutException e ) {
                addError( "Timeout while waiting for " + jobDescription + " job to finish", e );
            } catch ( java.lang.Exception e ) {
                addError( "Unexpected exception while waiting for " + jobDescription + " job to finish", e );
            }
        }
    }

    private  java.lang.String transformFileNamePattern2ZipEntry( java.lang.String fileNamePatternStr )
    {
        java.lang.String slashified = FileFilterUtil.slashify( fileNamePatternStr );
        return FileFilterUtil.afterLastSlash( slashified );
    }

    public  void setTimeBasedFileNamingAndTriggeringPolicy( ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy<E> timeBasedTriggering )
    {
        this.timeBasedFileNamingAndTriggeringPolicy = timeBasedTriggering;
    }

    public  ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy<E> getTimeBasedFileNamingAndTriggeringPolicy()
    {
        return timeBasedFileNamingAndTriggeringPolicy;
    }

    public  void rollover()
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        java.lang.String elapsedPeriodsFileName = timeBasedFileNamingAndTriggeringPolicy.getElapsedPeriodsFileName();
        java.lang.String elapsedPeriodStem = FileFilterUtil.afterLastSlash( elapsedPeriodsFileName );
        if (compressionMode == CompressionMode.NONE) {
            if (getParentsRawFileProperty() != null) {
                renameUtil.rename( getParentsRawFileProperty(), elapsedPeriodsFileName );
            }
        } else {
            if (getParentsRawFileProperty() == null) {
                compressionFuture = compressor.asyncCompress( elapsedPeriodsFileName, elapsedPeriodsFileName, elapsedPeriodStem );
            } else {
                compressionFuture = renameRawAndAsyncCompress( elapsedPeriodsFileName, elapsedPeriodStem );
            }
        }
        if (archiveRemover != null) {
            java.util.Date now = new java.util.Date( timeBasedFileNamingAndTriggeringPolicy.getCurrentTime() );
            this.cleanUpFuture = archiveRemover.cleanAsynchronously( now );
        }
    }

     java.util.concurrent.Future<?> renameRawAndAsyncCompress( java.lang.String nameOfCompressedFile, java.lang.String innerEntryName )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        java.lang.String parentsRawFile = getParentsRawFileProperty();
        java.lang.String tmpTarget = nameOfCompressedFile + System.nanoTime() + ".tmp";
        renameUtil.rename( parentsRawFile, tmpTarget );
        return compressor.asyncCompress( tmpTarget, nameOfCompressedFile, innerEntryName );
    }

    public  java.lang.String getActiveFileName()
    {
        java.lang.String parentsRawFileProperty = getParentsRawFileProperty();
        if (parentsRawFileProperty != null) {
            return parentsRawFileProperty;
        } else {
            return timeBasedFileNamingAndTriggeringPolicy.getCurrentPeriodsFileNameWithoutCompressionSuffix();
        }
    }

    public  boolean isTriggeringEvent( java.io.File activeFile, final E event )
    {
        return timeBasedFileNamingAndTriggeringPolicy.isTriggeringEvent( activeFile, event );
    }

    public  int getMaxHistory()
    {
        return maxHistory;
    }

    public  void setMaxHistory( int maxHistory )
    {
        this.maxHistory = maxHistory;
    }

    public  boolean isCleanHistoryOnStart()
    {
        return cleanHistoryOnStart;
    }

    public  void setCleanHistoryOnStart( boolean cleanHistoryOnStart )
    {
        this.cleanHistoryOnStart = cleanHistoryOnStart;
    }

    public  java.lang.String toString()
    {
        return "c.q.l.core.rolling.TimeBasedRollingPolicy@" + ch.qos.logback.core.CoreConstants.hashCode();
    }

    public  void setTotalSizeCap( ch.qos.logback.core.util.FileSize totalSizeCap )
    {
        addInfo( "setting totalSizeCap to " + totalSizeCap.toString() );
        this.totalSizeCap = totalSizeCap;
    }

}
