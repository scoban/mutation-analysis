// This is a mutant program.
// Author : ysma

package ch.qos.logback.core;


import java.io.OutputStream;
import java.util.Arrays;
import ch.qos.logback.core.joran.spi.ConsoleTarget;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.WarnStatus;
import ch.qos.logback.core.util.EnvUtil;
import ch.qos.logback.core.util.OptionHelper;


public class ConsoleAppender<E> extends ch.qos.logback.core.OutputStreamAppender<E>
{

    protected ch.qos.logback.core.joran.spi.ConsoleTarget target = ConsoleTarget.SystemOut;

    protected boolean withJansi = false;

    private static final java.lang.String WindowsAnsiOutputStream_CLASS_NAME = "org.fusesource.jansi.WindowsAnsiOutputStream";

    public  void setTarget( java.lang.String value )
    {
        ch.qos.logback.core.joran.spi.ConsoleTarget t = ConsoleTarget.findByName( value.trim() );
        if (t == null) {
            targetWarn( value );
        } else {
            target = t;
        }
    }

    public  java.lang.String getTarget()
    {
        return target.getName();
    }

    private  void targetWarn( java.lang.String val )
    {
        ch.qos.logback.core.status.Status status = new ch.qos.logback.core.status.WarnStatus( "[" + val + "] should be one of " + Arrays.toString( ConsoleTarget.values() ), this );
        status.add( new ch.qos.logback.core.status.WarnStatus( "Using previously set target, System.out by default.", this ) );
        addStatus( status );
    }

    public  void start()
    {
        java.io.OutputStream targetStream = target.getStream();
        if (EnvUtil.isWindows() && withJansi) {
            targetStream = getTargetStreamForWindows( targetStream );
        }
        setOutputStream( targetStream );
        super.start();
    }

    private  java.io.OutputStream getTargetStreamForWindows( java.io.OutputStream targetStream )
    {
        try {
            addInfo( "Enabling JANSI WindowsAnsiOutputStream for the console." );
            java.lang.Object windowsAnsiOutputStream = OptionHelper.instantiateByClassNameAndParameter( WindowsAnsiOutputStream_CLASS_NAME, java.lang.Object.class, context, java.io.OutputStream.class, targetStream );
            return (java.io.OutputStream) windowsAnsiOutputStream;
        } catch ( java.lang.Exception e ) {
            addWarn( "Failed to create WindowsAnsiOutputStream. Falling back on the default stream.", e );
        }
        return targetStream;
    }

    public  boolean isWithJansi()
    {
        return withJansi;
    }

    public  void setWithJansi( boolean withJansi )
    {
        this.withJansi = withJansi;
    }

}
