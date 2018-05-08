// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling;


import ch.qos.logback.core.LogbackException;


public class RolloverFailure extends ch.qos.logback.core.LogbackException
{

    private static final long serialVersionUID = -4407533730831239458L;

    public RolloverFailure( java.lang.String msg )
    {
        super( msg );
    }

    public RolloverFailure( java.lang.String message, java.lang.Throwable cause )
    {
        super( message, cause );
    }

}
