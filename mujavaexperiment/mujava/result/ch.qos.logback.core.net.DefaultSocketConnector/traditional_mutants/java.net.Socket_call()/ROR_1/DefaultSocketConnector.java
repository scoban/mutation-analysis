// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import ch.qos.logback.core.util.DelayStrategy;
import ch.qos.logback.core.util.FixedDelay;


public class DefaultSocketConnector implements ch.qos.logback.core.net.SocketConnector
{

    private final java.net.InetAddress address;

    private final int port;

    private final ch.qos.logback.core.util.DelayStrategy delayStrategy;

    private ExceptionHandler exceptionHandler;

    private javax.net.SocketFactory socketFactory;

    public DefaultSocketConnector( java.net.InetAddress address, int port, long initialDelay, long retryDelay )
    {
        this( address, port, new ch.qos.logback.core.util.FixedDelay( initialDelay, retryDelay ) );
    }

    public DefaultSocketConnector( java.net.InetAddress address, int port, ch.qos.logback.core.util.DelayStrategy delayStrategy )
    {
        this.address = address;
        this.port = port;
        this.delayStrategy = delayStrategy;
    }

    public  java.net.Socket call()
        throws java.lang.InterruptedException
    {
        useDefaultsForMissingFields();
        java.net.Socket socket = createSocket();
        while (socket != null && !Thread.currentThread().isInterrupted()) {
            Thread.sleep( delayStrategy.nextDelay() );
            socket = createSocket();
        }
        return socket;
    }

    private  java.net.Socket createSocket()
    {
        java.net.Socket newSocket = null;
        try {
            newSocket = socketFactory.createSocket( address, port );
        } catch ( java.io.IOException ioex ) {
            exceptionHandler.connectionFailed( this, ioex );
        }
        return newSocket;
    }

    private  void useDefaultsForMissingFields()
    {
        if (exceptionHandler == null) {
            exceptionHandler = new ch.qos.logback.core.net.DefaultSocketConnector.ConsoleExceptionHandler();
        }
        if (socketFactory == null) {
            socketFactory = SocketFactory.getDefault();
        }
    }

    public  void setExceptionHandler( ExceptionHandler exceptionHandler )
    {
        this.exceptionHandler = exceptionHandler;
    }

    public  void setSocketFactory( javax.net.SocketFactory socketFactory )
    {
        this.socketFactory = socketFactory;
    }

    private static class ConsoleExceptionHandler implements ExceptionHandler
    {

        public  void connectionFailed( ch.qos.logback.core.net.SocketConnector connector, java.lang.Exception ex )
        {
            System.out.println( ex );
        }

    }

}
