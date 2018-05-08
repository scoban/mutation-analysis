// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.recovery;


import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import ch.qos.logback.core.net.SyslogOutputStream;


public class ResilientSyslogOutputStream extends ch.qos.logback.core.recovery.ResilientOutputStreamBase
{

    java.lang.String syslogHost;

    int port;

    public ResilientSyslogOutputStream( java.lang.String syslogHost, int port )
        throws java.net.UnknownHostException, java.net.SocketException
    {
        this.syslogHost = syslogHost;
        this.port = port;
        super.os = new ch.qos.logback.core.net.SyslogOutputStream( syslogHost, port );
        this.presumedClean = true;
    }

     java.lang.String getDescription()
    {
        return "syslog [" + syslogHost + ":" + port + "]";
    }

     java.io.OutputStream openNewOutputStream()
        throws java.io.IOException
    {
        return new ch.qos.logback.core.net.SyslogOutputStream( syslogHost, port );
    }

    public  java.lang.String toString()
    {
        return "c.q.l.c.recovery.ResilientSyslogOutputStream@" + System.identityHashCode( this );
    }

}
