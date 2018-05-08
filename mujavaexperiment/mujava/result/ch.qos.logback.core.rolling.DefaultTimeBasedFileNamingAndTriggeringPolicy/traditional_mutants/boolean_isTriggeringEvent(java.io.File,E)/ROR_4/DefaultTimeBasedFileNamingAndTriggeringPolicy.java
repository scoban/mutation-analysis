// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import java.io.File;
import java.util.Date;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover;


public class DefaultTimeBasedFileNamingAndTriggeringPolicy<E> extends ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase<E>
{

    public  void start()
    {
        super.start();
        if (!super.isErrorFree()) {
            return;
        }
        if (tbrp.fileNamePattern.hasIntegerTokenCOnverter()) {
            addError( "Filename pattern [" + tbrp.fileNamePattern + "] contains an integer token converter, i.e. %i, INCOMPATIBLE with this configuration. Remove it." );
            return;
        }
        archiveRemover = new ch.qos.logback.core.rolling.helper.TimeBasedArchiveRemover( tbrp.fileNamePattern, rc );
        archiveRemover.setContext( context );
        started = true;
    }

    public  boolean isTriggeringEvent( java.io.File activeFile, final E event )
    {
        long time = getCurrentTime();
        if (time == nextCheck) {
            java.util.Date dateOfElapsedPeriod = dateInCurrentPeriod;
            addInfo( "Elapsed period: " + dateOfElapsedPeriod );
            elapsedPeriodsFileName = tbrp.fileNamePatternWithoutCompSuffix.convert( dateOfElapsedPeriod );
            setDateInCurrentPeriod( time );
            computeNextCheck();
            return true;
        } else {
            return false;
        }
    }

    public  java.lang.String toString()
    {
        return "c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy";
    }

}
