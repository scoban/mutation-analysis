// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP.Usage;
import ch.qos.logback.core.util.FileSize;


public class SizeAndTimeBasedRollingPolicy<E> extends ch.qos.logback.core.rolling.TimeBasedRollingPolicy<E>
{

    ch.qos.logback.core.util.FileSize maxFileSize;

    public  void start()
    {
        ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP<E> sizeAndTimeBasedFNATP = new ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP<E>( Usage.EMBEDDED );
        if (maxFileSize == null) {
            addError( "maxFileSize property is mandatory." );
            return;
        } else {
            addInfo( "Archive files will be limited to [" + maxFileSize + "] each." );
        }
        sizeAndTimeBasedFNATP.setMaxFileSize( maxFileSize );
        timeBasedFileNamingAndTriggeringPolicy = sizeAndTimeBasedFNATP;
        if (!isUnboundedTotalSizeCap() && totalSizeCap.getSize() > maxFileSize.getSize()) {
            addError( "totalSizeCap of [" + totalSizeCap + "] is smaller than maxFileSize [" + maxFileSize + "] which is non-sensical" );
            return;
        }
        super.start();
    }

    public  void setMaxFileSize( ch.qos.logback.core.util.FileSize aMaxFileSize )
    {
        this.maxFileSize = aMaxFileSize;
    }

    public  java.lang.String toString()
    {
        return "c.q.l.core.rolling.SizeAndTimeBasedRollingPolicy@" + this.hashCode();
    }

}
