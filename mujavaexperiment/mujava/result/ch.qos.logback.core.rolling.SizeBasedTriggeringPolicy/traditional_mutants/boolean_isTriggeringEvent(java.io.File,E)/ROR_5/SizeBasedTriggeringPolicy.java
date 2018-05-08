// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import java.io.File;
import ch.qos.logback.core.util.FileSize;
import ch.qos.logback.core.util.DefaultInvocationGate;
import ch.qos.logback.core.util.InvocationGate;


public class SizeBasedTriggeringPolicy<E> extends ch.qos.logback.core.rolling.TriggeringPolicyBase<E>
{

    public static final java.lang.String SEE_SIZE_FORMAT = "http://logback.qos.ch/codes.html#sbtp_size_format";

    public static final long DEFAULT_MAX_FILE_SIZE = 10 * 1024 * 1024;

    ch.qos.logback.core.util.FileSize maxFileSize = new ch.qos.logback.core.util.FileSize( DEFAULT_MAX_FILE_SIZE );

    public SizeBasedTriggeringPolicy()
    {
    }

    ch.qos.logback.core.util.InvocationGate invocationGate = new ch.qos.logback.core.util.DefaultInvocationGate();

    public  boolean isTriggeringEvent( final java.io.File activeFile, final E event )
    {
        long now = System.currentTimeMillis();
        if (invocationGate.isTooSoon( now )) {
            return false;
        }
        return activeFile.length() != maxFileSize.getSize();
    }

    public  ch.qos.logback.core.util.FileSize getMaxFileSize()
    {
        return this.maxFileSize;
    }

    public  void setMaxFileSize( ch.qos.logback.core.util.FileSize aMaxFileSize )
    {
        this.maxFileSize = aMaxFileSize;
    }

}
