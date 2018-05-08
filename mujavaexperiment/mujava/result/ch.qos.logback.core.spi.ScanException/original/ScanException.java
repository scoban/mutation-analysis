// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


public class ScanException extends java.lang.Exception
{

    private static final long serialVersionUID = -3132040414328475658L;

    java.lang.Throwable cause;

    public ScanException( java.lang.String msg )
    {
        super( msg );
    }

    public ScanException( java.lang.String msg, java.lang.Throwable rootCause )
    {
        super( msg );
        this.cause = rootCause;
    }

    public  java.lang.Throwable getCause()
    {
        return cause;
    }

}
