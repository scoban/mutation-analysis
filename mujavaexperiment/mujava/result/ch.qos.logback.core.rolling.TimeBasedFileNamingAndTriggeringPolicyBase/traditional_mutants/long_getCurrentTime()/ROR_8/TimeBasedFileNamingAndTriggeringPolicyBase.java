// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import static ch.qos.logback.core.CoreConstants.CODES_URL;
import java.io.File;
import java.util.Date;
import java.util.Locale;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.ArchiveRemover;
import ch.qos.logback.core.rolling.helper.DateTokenConverter;
import ch.qos.logback.core.rolling.helper.RollingCalendar;
import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class TimeBasedFileNamingAndTriggeringPolicyBase<E> extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicy<E>
{

    private static java.lang.String COLLIDING_DATE_FORMAT_URL = ch.qos.logback.core.CoreConstants.CODES_URL + "#rfa_collision_in_dateFormat";

    protected ch.qos.logback.core.rolling.TimeBasedRollingPolicy<E> tbrp;

    protected ch.qos.logback.core.rolling.helper.ArchiveRemover archiveRemover = null;

    protected java.lang.String elapsedPeriodsFileName;

    protected ch.qos.logback.core.rolling.helper.RollingCalendar rc;

    protected long artificialCurrentTime = -1;

    protected java.util.Date dateInCurrentPeriod = null;

    protected long nextCheck;

    protected boolean started = false;

    protected boolean errorFree = true;

    public  boolean isStarted()
    {
        return started;
    }

    public  void start()
    {
        ch.qos.logback.core.rolling.helper.DateTokenConverter<Object> dtc = tbrp.fileNamePattern.getPrimaryDateTokenConverter();
        if (dtc == null) {
            throw new java.lang.IllegalStateException( "FileNamePattern [" + tbrp.fileNamePattern.getPattern() + "] does not contain a valid DateToken" );
        }
        if (dtc.getTimeZone() != null) {
            rc = new ch.qos.logback.core.rolling.helper.RollingCalendar( dtc.getDatePattern(), dtc.getTimeZone(), Locale.getDefault() );
        } else {
            rc = new ch.qos.logback.core.rolling.helper.RollingCalendar( dtc.getDatePattern() );
        }
        addInfo( "The date pattern is '" + dtc.getDatePattern() + "' from file name pattern '" + tbrp.fileNamePattern.getPattern() + "'." );
        rc.printPeriodicity( this );
        if (!rc.isCollisionFree()) {
            addError( "The date format in FileNamePattern will result in collisions in the names of archived log files." );
            addError( CoreConstants.MORE_INFO_PREFIX + COLLIDING_DATE_FORMAT_URL );
            withErrors();
            return;
        }
        setDateInCurrentPeriod( new java.util.Date( getCurrentTime() ) );
        if (tbrp.getParentsRawFileProperty() != null) {
            java.io.File currentFile = new java.io.File( tbrp.getParentsRawFileProperty() );
            if (currentFile.exists() && currentFile.canRead()) {
                setDateInCurrentPeriod( new java.util.Date( currentFile.lastModified() ) );
            }
        }
        addInfo( "Setting initial period to " + dateInCurrentPeriod );
        computeNextCheck();
    }

    public  void stop()
    {
        started = false;
    }

    protected  void computeNextCheck()
    {
        nextCheck = rc.getNextTriggeringDate( dateInCurrentPeriod ).getTime();
    }

    protected  void setDateInCurrentPeriod( long now )
    {
        dateInCurrentPeriod.setTime( now );
    }

    public  void setDateInCurrentPeriod( java.util.Date _dateInCurrentPeriod )
    {
        this.dateInCurrentPeriod = _dateInCurrentPeriod;
    }

    public  java.lang.String getElapsedPeriodsFileName()
    {
        return elapsedPeriodsFileName;
    }

    public  java.lang.String getCurrentPeriodsFileNameWithoutCompressionSuffix()
    {
        return tbrp.fileNamePatternWithoutCompSuffix.convert( dateInCurrentPeriod );
    }

    public  void setCurrentTime( long timeInMillis )
    {
        artificialCurrentTime = timeInMillis;
    }

    public  long getCurrentTime()
    {
        if (artificialCurrentTime != 0) {
            return artificialCurrentTime;
        } else {
            return System.currentTimeMillis();
        }
    }

    public  void setTimeBasedRollingPolicy( ch.qos.logback.core.rolling.TimeBasedRollingPolicy<E> _tbrp )
    {
        this.tbrp = _tbrp;
    }

    public  ch.qos.logback.core.rolling.helper.ArchiveRemover getArchiveRemover()
    {
        return archiveRemover;
    }

    protected  void withErrors()
    {
        errorFree = false;
    }

    protected  boolean isErrorFree()
    {
        return errorFree;
    }

}
