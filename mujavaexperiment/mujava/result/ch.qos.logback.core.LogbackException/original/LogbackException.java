// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


public class LogbackException extends java.lang.RuntimeException
{

    private static final long serialVersionUID = -799956346239073266L;

    public LogbackException( java.lang.String msg )
    {
        super( msg );
    }

    public LogbackException( java.lang.String msg, java.lang.Throwable nested )
    {
        super( msg, nested );
    }

}
