// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import static ch.qos.logback.core.CoreConstants.MANUAL_URL_PREFIX;
import java.io.File;
import java.util.Date;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.helper.ArchiveRemover;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.FileFilterUtil;
import ch.qos.logback.core.rolling.helper.SizeAndTimeBasedArchiveRemover;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.DefaultInvocationGate;
import ch.qos.logback.core.util.InvocationGate;


public class SizeAndTimeBasedFNATP<E> extends ch.qos.logback.core.rolling.TimeBasedFileNamingAndTriggeringPolicyBase<E>
{

    enum Usage 
    {
        EMBEDDED,
        DIRECT;

    }

    int currentPeriodsCounter = 0;

    ch.qos.logback.core.util.FileSize maxFileSize;

    long nextSizeCheck = 0;

    static java.lang.String MISSING_INT_TOKEN = "Missing integer token, that is %i, in FileNamePattern [";

    static java.lang.String MISSING_DATE_TOKEN = "Missing date token, that is %d, in FileNamePattern [";

    private final ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP.Usage usage;

    public SizeAndTimeBasedFNATP()
    {
        this( Usage.DIRECT );
    }

    public SizeAndTimeBasedFNATP( ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP.Usage usage )
    {
        this.usage = usage;
    }

    public  void start()
    {
        super.start();
        if (usage == Usage.DIRECT) {
            addWarn( CoreConstants.SIZE_AND_TIME_BASED_FNATP_IS_DEPRECATED );
            addWarn( "For more information see " + ch.qos.logback.core.CoreConstants.MANUAL_URL_PREFIX + "appenders.html#SizeAndTimeBasedRollingPolicy" );
        }
        if (!super.isErrorFree()) {
            return;
        }
        if (maxFileSize == null) {
            addError( "maxFileSize property is mandatory." );
            withErrors();
        }
        if (!validateDateAndIntegerTokens()) {
            withErrors();
            return;
        }
        archiveRemover = createArchiveRemover();
        archiveRemover.setContext( context );
        java.lang.String regex = tbrp.fileNamePattern.toRegexForFixedDate( dateInCurrentPeriod );
        java.lang.String stemRegex = FileFilterUtil.afterLastSlash( regex );
        computeCurrentPeriodsHighestCounterValue( stemRegex );
        if (isErrorFree()) {
            started = true;
        }
    }

    private  boolean validateDateAndIntegerTokens()
    {
        boolean inError = false;
        if (tbrp.fileNamePattern.getIntegerTokenConverter() == null) {
            inError = true;
            addError( MISSING_INT_TOKEN + tbrp.fileNamePatternStr + "]" );
            addError( CoreConstants.SEE_MISSING_INTEGER_TOKEN );
        }
        if (tbrp.fileNamePattern.getPrimaryDateTokenConverter() == null) {
            inError = true;
            addError( MISSING_DATE_TOKEN + tbrp.fileNamePatternStr + "]" );
        }
        return !inError;
    }

    protected  ch.qos.logback.core.rolling.helper.ArchiveRemover createArchiveRemover()
    {
        return new ch.qos.logback.core.rolling.helper.SizeAndTimeBasedArchiveRemover( tbrp.fileNamePattern, rc );
    }

     void computeCurrentPeriodsHighestCounterValue( final java.lang.String stemRegex )
    {
        java.io.File file = new java.io.File( getCurrentPeriodsFileNameWithoutCompressionSuffix() );
        java.io.File parentDir = file.getParentFile();
        java.io.File[] matchingFileArray = FileFilterUtil.filesInFolderMatchingStemRegex( parentDir, stemRegex );
        if (matchingFileArray == null || matchingFileArray.length == 0) {
            currentPeriodsCounter = 0;
            return;
        }
        currentPeriodsCounter = FileFilterUtil.findHighestCounter( matchingFileArray, stemRegex );
        if (tbrp.getParentsRawFileProperty() != null || tbrp.compressionMode != CompressionMode.NONE) {
            currentPeriodsCounter++;
        }
    }

    ch.qos.logback.core.util.InvocationGate invocationGate = new ch.qos.logback.core.util.DefaultInvocationGate();

    public  boolean isTriggeringEvent( java.io.File activeFile, final E event )
    {
        long time = getCurrentTime();
        if (time == nextCheck) {
            java.util.Date dateInElapsedPeriod = dateInCurrentPeriod;
            elapsedPeriodsFileName = tbrp.fileNamePatternWithoutCompSuffix.convertMultipleArguments( dateInElapsedPeriod, currentPeriodsCounter );
            currentPeriodsCounter = 0;
            setDateInCurrentPeriod( time );
            computeNextCheck();
            return true;
        }
        if (invocationGate.isTooSoon( time )) {
            return false;
        }
        if (activeFile == null) {
            addWarn( "activeFile == null" );
            return false;
        }
        if (maxFileSize == null) {
            addWarn( "maxFileSize = null" );
            return false;
        }
        if (activeFile.length() >= maxFileSize.getSize()) {
            elapsedPeriodsFileName = tbrp.fileNamePatternWithoutCompSuffix.convertMultipleArguments( dateInCurrentPeriod, currentPeriodsCounter );
            currentPeriodsCounter++;
            return true;
        }
        return false;
    }

    public  java.lang.String getCurrentPeriodsFileNameWithoutCompressionSuffix()
    {
        return tbrp.fileNamePatternWithoutCompSuffix.convertMultipleArguments( dateInCurrentPeriod, currentPeriodsCounter );
    }

    public  void setMaxFileSize( ch.qos.logback.core.util.FileSize aMaxFileSize )
    {
        this.maxFileSize = aMaxFileSize;
    }

}
