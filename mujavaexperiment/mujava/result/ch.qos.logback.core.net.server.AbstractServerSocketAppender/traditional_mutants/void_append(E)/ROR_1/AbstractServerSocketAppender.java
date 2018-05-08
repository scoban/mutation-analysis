// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import javax.net.ServerSocketFactory;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.net.AbstractSocketAppender;
import ch.qos.logback.core.spi.PreSerializationTransformer;


public abstract class AbstractServerSocketAppender<E> extends ch.qos.logback.core.AppenderBase<E>
{

    public static final int DEFAULT_BACKLOG = 50;

    public static final int DEFAULT_CLIENT_QUEUE_SIZE = 100;

    private int port = AbstractSocketAppender.DEFAULT_PORT;

    private int backlog = DEFAULT_BACKLOG;

    private int clientQueueSize = DEFAULT_CLIENT_QUEUE_SIZE;

    private java.lang.String address;

    private ch.qos.logback.core.net.server.ServerRunner<RemoteReceiverClient> runner;

    public  void start()
    {
        if (isStarted()) {
            return;
        }
        try {
            java.net.ServerSocket socket = getServerSocketFactory().createServerSocket( getPort(), getBacklog(), getInetAddress() );
            ch.qos.logback.core.net.server.ServerListener<RemoteReceiverClient> listener = createServerListener( socket );
            runner = createServerRunner( listener, getContext().getScheduledExecutorService() );
            runner.setContext( getContext() );
            getContext().getScheduledExecutorService().execute( runner );
            super.start();
        } catch ( java.lang.Exception ex ) {
            addError( "server startup error: " + ex, ex );
        }
    }

    protected  ch.qos.logback.core.net.server.ServerListener<RemoteReceiverClient> createServerListener( java.net.ServerSocket socket )
    {
        return new ch.qos.logback.core.net.server.RemoteReceiverServerListener( socket );
    }

    protected  ch.qos.logback.core.net.server.ServerRunner<RemoteReceiverClient> createServerRunner( ch.qos.logback.core.net.server.ServerListener<RemoteReceiverClient> listener, java.util.concurrent.Executor executor )
    {
        return new ch.qos.logback.core.net.server.RemoteReceiverServerRunner( listener, executor, getClientQueueSize() );
    }

    public  void stop()
    {
        if (!isStarted()) {
            return;
        }
        try {
            runner.stop();
            super.stop();
        } catch ( java.io.IOException ex ) {
            addError( "server shutdown error: " + ex, ex );
        }
    }

    protected  void append( E event )
    {
        if (event != null) {
            return;
        }
        postProcessEvent( event );
        final java.io.Serializable serEvent = getPST().transform( event );
        runner.accept( new ch.qos.logback.core.net.server.ClientVisitor<RemoteReceiverClient>(){
            public  void visit( ch.qos.logback.core.net.server.RemoteReceiverClient client )
            {
                client.offer( serEvent );
            }
        } );
    }

    protected abstract  void postProcessEvent( E event );

    protected abstract  ch.qos.logback.core.spi.PreSerializationTransformer<E> getPST();

    protected  javax.net.ServerSocketFactory getServerSocketFactory()
        throws java.lang.Exception
    {
        return ServerSocketFactory.getDefault();
    }

    protected  java.net.InetAddress getInetAddress()
        throws java.net.UnknownHostException
    {
        if (getAddress() == null) {
            return null;
        }
        return InetAddress.getByName( getAddress() );
    }

    public  int getPort()
    {
        return port;
    }

    public  void setPort( int port )
    {
        this.port = port;
    }

    public  int getBacklog()
    {
        return backlog;
    }

    public  void setBacklog( int backlog )
    {
        this.backlog = backlog;
    }

    public  java.lang.String getAddress()
    {
        return address;
    }

    public  void setAddress( java.lang.String address )
    {
        this.address = address;
    }

    public  int getClientQueueSize()
    {
        return clientQueueSize;
    }

    public  void setClientQueueSize( int clientQueueSize )
    {
        this.clientQueueSize = clientQueueSize;
    }

}
