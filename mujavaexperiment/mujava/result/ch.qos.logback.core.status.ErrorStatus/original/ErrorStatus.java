// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


public class ErrorStatus extends ch.qos.logback.core.status.StatusBase
{

    public ErrorStatus( java.lang.String msg, java.lang.Object origin )
    {
        super( Status.ERROR, msg, origin );
    }

    public ErrorStatus( java.lang.String msg, java.lang.Object origin, java.lang.Throwable t )
    {
        super( Status.ERROR, msg, origin, t );
    }

}
