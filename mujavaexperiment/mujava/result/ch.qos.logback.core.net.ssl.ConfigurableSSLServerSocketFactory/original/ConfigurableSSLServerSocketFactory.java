// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;


public class ConfigurableSSLServerSocketFactory extends javax.net.ServerSocketFactory
{

    private final ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters;

    private final javax.net.ssl.SSLServerSocketFactory delegate;

    public ConfigurableSSLServerSocketFactory( ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters, javax.net.ssl.SSLServerSocketFactory delegate )
    {
        this.parameters = parameters;
        this.delegate = delegate;
    }

    public  java.net.ServerSocket createServerSocket( int port, int backlog, java.net.InetAddress ifAddress )
        throws java.io.IOException
    {
        javax.net.ssl.SSLServerSocket socket = (javax.net.ssl.SSLServerSocket) delegate.createServerSocket( port, backlog, ifAddress );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableServerSocket( socket ) );
        return socket;
    }

    public  java.net.ServerSocket createServerSocket( int port, int backlog )
        throws java.io.IOException
    {
        javax.net.ssl.SSLServerSocket socket = (javax.net.ssl.SSLServerSocket) delegate.createServerSocket( port, backlog );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableServerSocket( socket ) );
        return socket;
    }

    public  java.net.ServerSocket createServerSocket( int port )
        throws java.io.IOException
    {
        javax.net.ssl.SSLServerSocket socket = (javax.net.ssl.SSLServerSocket) delegate.createServerSocket( port );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableServerSocket( socket ) );
        return socket;
    }

}
