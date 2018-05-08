// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.status;


public class InfoStatus extends ch.qos.logback.core.status.StatusBase
{

    public InfoStatus( java.lang.String msg, java.lang.Object origin )
    {
        super( Status.INFO, msg, origin );
    }

    public InfoStatus( java.lang.String msg, java.lang.Object origin, java.lang.Throwable t )
    {
        super( Status.INFO, msg, origin, t );
    }

}
