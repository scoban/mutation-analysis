// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


public class JoranException extends java.lang.Exception
{

    private static final long serialVersionUID = 1112493363728774021L;

    public JoranException( java.lang.String msg )
    {
        super( msg );
    }

    public JoranException( java.lang.String msg, java.lang.Throwable cause )
    {
        super( msg, cause );
    }

}
