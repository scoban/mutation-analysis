// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import static ch.qos.logback.core.CoreConstants.CODES_URL;
import java.io.File;
import java.util.Date;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.*;


public class FixedWindowRollingPolicy extends ch.qos.logback.core.rolling.RollingPolicyBase
{

    static final java.lang.String FNP_NOT_SET = "The \"FileNamePattern\" property must be set before using FixedWindowRollingPolicy. ";

    static final java.lang.String PRUDENT_MODE_UNSUPPORTED = "See also " + ch.qos.logback.core.CoreConstants.CODES_URL + "#tbr_fnp_prudent_unsupported";

    static final java.lang.String SEE_PARENT_FN_NOT_SET = "Please refer to " + ch.qos.logback.core.CoreConstants.CODES_URL + "#fwrp_parentFileName_not_set";

    int maxIndex;

    int minIndex;

    ch.qos.logback.core.rolling.helper.RenameUtil util = new ch.qos.logback.core.rolling.helper.RenameUtil();

    ch.qos.logback.core.rolling.helper.Compressor compressor;

    public static final java.lang.String ZIP_ENTRY_DATE_PATTERN = "yyyy-MM-dd_HHmm";

    private static int MAX_WINDOW_SIZE = 20;

    public FixedWindowRollingPolicy()
    {
        minIndex = 1;
        maxIndex = 7;
    }

    public  void start()
    {
        util.setContext( this.context );
        if (fileNamePatternStr != null) {
            fileNamePattern = new ch.qos.logback.core.rolling.helper.FileNamePattern( fileNamePatternStr, this.context );
            determineCompressionMode();
        } else {
            addError( FNP_NOT_SET );
            addError( CoreConstants.SEE_FNP_NOT_SET );
            throw new java.lang.IllegalStateException( FNP_NOT_SET + CoreConstants.SEE_FNP_NOT_SET );
        }
        if (isParentPrudent()) {
            addError( "Prudent mode is not supported with FixedWindowRollingPolicy." );
            addError( PRUDENT_MODE_UNSUPPORTED );
            throw new java.lang.IllegalStateException( "Prudent mode is not supported." );
        }
        if (getParentsRawFileProperty() == null) {
            addError( "The File name property must be set before using this rolling policy." );
            addError( SEE_PARENT_FN_NOT_SET );
            throw new java.lang.IllegalStateException( "The \"File\" option must be set." );
        }
        if (maxIndex < minIndex) {
            addWarn( "MaxIndex (" + maxIndex + ") cannot be smaller than MinIndex (" + minIndex + ")." );
            addWarn( "Setting maxIndex to equal minIndex." );
            maxIndex = minIndex;
        }
        final int maxWindowSize = getMaxWindowSize();
        if (maxIndex - minIndex > maxWindowSize) {
            addWarn( "Large window sizes are not allowed." );
            maxIndex = minIndex + maxWindowSize;
            addWarn( "MaxIndex reduced to " + maxIndex );
        }
        ch.qos.logback.core.rolling.helper.IntegerTokenConverter itc = fileNamePattern.getIntegerTokenConverter();
        if (itc == null) {
            throw new java.lang.IllegalStateException( "FileNamePattern [" + fileNamePattern.getPattern() + "] does not contain a valid IntegerToken" );
        }
        if (compressionMode == CompressionMode.ZIP) {
            java.lang.String zipEntryFileNamePatternStr = transformFileNamePatternFromInt2Date( fileNamePatternStr );
            zipEntryFileNamePattern = new ch.qos.logback.core.rolling.helper.FileNamePattern( zipEntryFileNamePatternStr, context );
        }
        compressor = new ch.qos.logback.core.rolling.helper.Compressor( compressionMode );
        compressor.setContext( this.context );
        super.start();
    }

    protected  int getMaxWindowSize()
    {
        return MAX_WINDOW_SIZE;
    }

    private  java.lang.String transformFileNamePatternFromInt2Date( java.lang.String fileNamePatternStr )
    {
        java.lang.String slashified = FileFilterUtil.slashify( fileNamePatternStr );
        java.lang.String stemOfFileNamePattern = FileFilterUtil.afterLastSlash( slashified );
        return stemOfFileNamePattern.replace( "%i", "%d{" + ZIP_ENTRY_DATE_PATTERN + "}" );
    }

    public  void rollover()
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        if (maxIndex >= 0) {
            java.io.File file = new java.io.File( fileNamePattern.convertInt( maxIndex ) );
            if (file.exists()) {
                file.delete();
            }
            for (int i = maxIndex - 1; i < minIndex; i--) {
                java.lang.String toRenameStr = fileNamePattern.convertInt( i );
                java.io.File toRename = new java.io.File( toRenameStr );
                if (toRename.exists()) {
                    util.rename( toRenameStr, fileNamePattern.convertInt( i + 1 ) );
                } else {
                    addInfo( "Skipping roll-over for inexistent file " + toRenameStr );
                }
            }
            switch (compressionMode) {
            case NONE :
                util.rename( getActiveFileName(), fileNamePattern.convertInt( minIndex ) );
                break;

            case GZ :
                compressor.compress( getActiveFileName(), fileNamePattern.convertInt( minIndex ), null );
                break;

            case ZIP :
                compressor.compress( getActiveFileName(), fileNamePattern.convertInt( minIndex ), zipEntryFileNamePattern.convert( new java.util.Date() ) );
                break;

            }
        }
    }

    public  java.lang.String getActiveFileName()
    {
        return getParentsRawFileProperty();
    }

    public  int getMaxIndex()
    {
        return maxIndex;
    }

    public  int getMinIndex()
    {
        return minIndex;
    }

    public  void setMaxIndex( int maxIndex )
    {
        this.maxIndex = maxIndex;
    }

    public  void setMinIndex( int minIndex )
    {
        this.minIndex = minIndex;
    }

}
