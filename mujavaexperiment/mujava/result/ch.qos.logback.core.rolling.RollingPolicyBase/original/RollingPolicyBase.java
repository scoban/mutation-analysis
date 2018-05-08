// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.helper.CompressionMode;
import ch.qos.logback.core.rolling.helper.FileNamePattern;
import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class RollingPolicyBase extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.rolling.RollingPolicy
{

    protected ch.qos.logback.core.rolling.helper.CompressionMode compressionMode = CompressionMode.NONE;

    ch.qos.logback.core.rolling.helper.FileNamePattern fileNamePattern;

    protected java.lang.String fileNamePatternStr;

    private ch.qos.logback.core.FileAppender<?> parent;

    ch.qos.logback.core.rolling.helper.FileNamePattern zipEntryFileNamePattern;

    private boolean started;

    protected  void determineCompressionMode()
    {
        if (fileNamePatternStr.endsWith( ".gz" )) {
            addInfo( "Will use gz compression" );
            compressionMode = CompressionMode.GZ;
        } else {
            if (fileNamePatternStr.endsWith( ".zip" )) {
                addInfo( "Will use zip compression" );
                compressionMode = CompressionMode.ZIP;
            } else {
                addInfo( "No compression will be used" );
                compressionMode = CompressionMode.NONE;
            }
        }
    }

    public  void setFileNamePattern( java.lang.String fnp )
    {
        fileNamePatternStr = fnp;
    }

    public  java.lang.String getFileNamePattern()
    {
        return fileNamePatternStr;
    }

    public  ch.qos.logback.core.rolling.helper.CompressionMode getCompressionMode()
    {
        return compressionMode;
    }

    public  boolean isStarted()
    {
        return started;
    }

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        started = false;
    }

    public  void setParent( ch.qos.logback.core.FileAppender<?> appender )
    {
        this.parent = appender;
    }

    public  boolean isParentPrudent()
    {
        return parent.isPrudent();
    }

    public  java.lang.String getParentsRawFileProperty()
    {
        return parent.rawFileProperty();
    }

}
