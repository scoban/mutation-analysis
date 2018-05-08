// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ConfigurableSSLSocketFactory extends javax.net.SocketFactory
{

    private final ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters;

    private final javax.net.ssl.SSLSocketFactory delegate;

    public ConfigurableSSLSocketFactory( ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters, javax.net.ssl.SSLSocketFactory delegate )
    {
        this.parameters = parameters;
        this.delegate = delegate;
    }

    public  java.net.Socket createSocket( java.net.InetAddress address, int port, java.net.InetAddress localAddress, int localPort )
        throws java.io.IOException
    {
        javax.net.ssl.SSLSocket socket = (javax.net.ssl.SSLSocket) delegate.createSocket( address, port, localAddress, localPort );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableSocket( socket ) );
        return socket;
    }

    public  java.net.Socket createSocket( java.net.InetAddress host, int port )
        throws java.io.IOException
    {
        javax.net.ssl.SSLSocket socket = (javax.net.ssl.SSLSocket) delegate.createSocket( host, port );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableSocket( socket ) );
        return socket;
    }

    public  java.net.Socket createSocket( java.lang.String host, int port, java.net.InetAddress localHost, int localPort )
        throws java.io.IOException, java.net.UnknownHostException
    {
        javax.net.ssl.SSLSocket socket = (javax.net.ssl.SSLSocket) delegate.createSocket( host, port, localHost, localPort );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableSocket( socket ) );
        return socket;
    }

    public  java.net.Socket createSocket( java.lang.String host, int port )
        throws java.io.IOException, java.net.UnknownHostException
    {
        javax.net.ssl.SSLSocket socket = (javax.net.ssl.SSLSocket) delegate.createSocket( host, port );
        parameters.configure( new ch.qos.logback.core.net.ssl.SSLConfigurableSocket( socket ) );
        return socket;
    }

}
