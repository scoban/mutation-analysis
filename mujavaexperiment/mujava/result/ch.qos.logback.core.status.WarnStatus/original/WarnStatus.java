// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


public class WarnStatus extends ch.qos.logback.core.status.StatusBase
{

    public WarnStatus( java.lang.String msg, java.lang.Object origin )
    {
        super( Status.WARN, msg, origin );
    }

    public WarnStatus( java.lang.String msg, java.lang.Object origin, java.lang.Throwable t )
    {
        super( Status.WARN, msg, origin, t );
    }

}
