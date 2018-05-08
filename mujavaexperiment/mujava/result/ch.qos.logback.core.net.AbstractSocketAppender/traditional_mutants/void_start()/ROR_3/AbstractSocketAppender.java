// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.spi.PreSerializationTransformer;
import ch.qos.logback.core.util.CloseUtil;
import ch.qos.logback.core.util.Duration;


public abstract class AbstractSocketAppender<E> extends ch.qos.logback.core.AppenderBase<E> implements SocketConnector.ExceptionHandler
{

    public static final int DEFAULT_PORT = 4560;

    public static final int DEFAULT_RECONNECTION_DELAY = 30000;

    public static final int DEFAULT_QUEUE_SIZE = 128;

    private static final int DEFAULT_ACCEPT_CONNECTION_DELAY = 5000;

    private static final int DEFAULT_EVENT_DELAY_TIMEOUT = 100;

    private final ch.qos.logback.core.net.ObjectWriterFactory objectWriterFactory;

    private final ch.qos.logback.core.net.QueueFactory queueFactory;

    private java.lang.String remoteHost;

    private int port = DEFAULT_PORT;

    private java.net.InetAddress address;

    private ch.qos.logback.core.util.Duration reconnectionDelay = new ch.qos.logback.core.util.Duration( DEFAULT_RECONNECTION_DELAY );

    private int queueSize = DEFAULT_QUEUE_SIZE;

    private int acceptConnectionTimeout = DEFAULT_ACCEPT_CONNECTION_DELAY;

    private ch.qos.logback.core.util.Duration eventDelayLimit = new ch.qos.logback.core.util.Duration( DEFAULT_EVENT_DELAY_TIMEOUT );

    private java.util.concurrent.BlockingDeque<E> deque;

    private java.lang.String peerId;

    private ch.qos.logback.core.net.SocketConnector connector;

    private java.util.concurrent.Future<?> task;

    private volatile java.net.Socket socket;

    protected AbstractSocketAppender()
    {
        this( new ch.qos.logback.core.net.QueueFactory(), new ch.qos.logback.core.net.ObjectWriterFactory() );
    }

    AbstractSocketAppender( ch.qos.logback.core.net.QueueFactory queueFactory, ch.qos.logback.core.net.ObjectWriterFactory objectWriterFactory )
    {
        this.objectWriterFactory = objectWriterFactory;
        this.queueFactory = queueFactory;
    }

    public  void start()
    {
        if (isStarted()) {
            return;
        }
        int errorCount = 0;
        if (port < 0) {
            errorCount++;
            addError( "No port was configured for appender" + name + " For more information, please visit http://logback.qos.ch/codes.html#socket_no_port" );
        }
        if (remoteHost == null) {
            errorCount++;
            addError( "No remote host was configured for appender" + name + " For more information, please visit http://logback.qos.ch/codes.html#socket_no_host" );
        }
        if (queueSize == 0) {
            addWarn( "Queue size of zero is deprecated, use a size of one to indicate synchronous processing" );
        }
        if (queueSize < 0) {
            errorCount++;
            addError( "Queue size must be greater than zero" );
        }
        if (errorCount == 0) {
            try {
                address = InetAddress.getByName( remoteHost );
            } catch ( java.net.UnknownHostException ex ) {
                addError( "unknown host: " + remoteHost );
                errorCount++;
            }
        }
        if (errorCount == 0) {
            deque = queueFactory.newLinkedBlockingDeque( queueSize );
            peerId = "remote peer " + remoteHost + ":" + port + ": ";
            connector = createConnector( address, port, 0, reconnectionDelay.getMilliseconds() );
            task = getContext().getScheduledExecutorService().submit( new java.lang.Runnable(){
                public  void run()
                {
                    connectSocketAndDispatchEvents();
                }
            } );
            super.start();
        }
    }

    public  void stop()
    {
        if (!isStarted()) {
            return;
        }
        CloseUtil.closeQuietly( socket );
        task.cancel( true );
        super.stop();
    }

    protected  void append( E event )
    {
        if (event == null || !isStarted()) {
            return;
        }
        try {
            final boolean inserted = deque.offer( event, eventDelayLimit.getMilliseconds(), TimeUnit.MILLISECONDS );
            if (!inserted) {
                addInfo( "Dropping event due to timeout limit of [" + eventDelayLimit + "] being exceeded" );
            }
        } catch ( java.lang.InterruptedException e ) {
            addError( "Interrupted while appending event to SocketAppender", e );
        }
    }

    private  void connectSocketAndDispatchEvents()
    {
        try {
            while (socketConnectionCouldBeEstablished()) {
                try {
                    ch.qos.logback.core.net.ObjectWriter objectWriter = createObjectWriterForSocket();
                    addInfo( peerId + "connection established" );
                    dispatchEvents( objectWriter );
                } catch ( java.io.IOException ex ) {
                    addInfo( peerId + "connection failed: " + ex );
                } finally 
{
                    CloseUtil.closeQuietly( socket );
                    socket = null;
                    addInfo( peerId + "connection closed" );
                }
            }
        } catch ( java.lang.InterruptedException ex ) {
             assert true;
        }
        addInfo( "shutting down" );
    }

    private  boolean socketConnectionCouldBeEstablished()
        throws java.lang.InterruptedException
    {
        return (socket = connector.call()) != null;
    }

    private  ch.qos.logback.core.net.ObjectWriter createObjectWriterForSocket()
        throws java.io.IOException
    {
        socket.setSoTimeout( acceptConnectionTimeout );
        ch.qos.logback.core.net.ObjectWriter objectWriter = objectWriterFactory.newAutoFlushingObjectWriter( socket.getOutputStream() );
        socket.setSoTimeout( 0 );
        return objectWriter;
    }

    private  ch.qos.logback.core.net.SocketConnector createConnector( java.net.InetAddress address, int port, int initialDelay, long retryDelay )
    {
        ch.qos.logback.core.net.SocketConnector connector = newConnector( address, port, initialDelay, retryDelay );
        connector.setExceptionHandler( this );
        connector.setSocketFactory( getSocketFactory() );
        return connector;
    }

    private  void dispatchEvents( ch.qos.logback.core.net.ObjectWriter objectWriter )
        throws java.lang.InterruptedException, java.io.IOException
    {
        while (true) {
            E event = deque.takeFirst();
            postProcessEvent( event );
            java.io.Serializable serializableEvent = getPST().transform( event );
            try {
                objectWriter.write( serializableEvent );
            } catch ( java.io.IOException e ) {
                tryReAddingEventToFrontOfQueue( event );
                throw e;
            }
        }
    }

    private  void tryReAddingEventToFrontOfQueue( E event )
    {
        final boolean wasInserted = deque.offerFirst( event );
        if (!wasInserted) {
            addInfo( "Dropping event due to socket connection error and maxed out deque capacity" );
        }
    }

    public  void connectionFailed( ch.qos.logback.core.net.SocketConnector connector, java.lang.Exception ex )
    {
        if (ex instanceof java.lang.InterruptedException) {
            addInfo( "connector interrupted" );
        } else {
            if (ex instanceof java.net.ConnectException) {
                addInfo( peerId + "connection refused" );
            } else {
                addInfo( peerId + ex );
            }
        }
    }

    protected  ch.qos.logback.core.net.SocketConnector newConnector( java.net.InetAddress address, int port, long initialDelay, long retryDelay )
    {
        return new ch.qos.logback.core.net.DefaultSocketConnector( address, port, initialDelay, retryDelay );
    }

    protected  javax.net.SocketFactory getSocketFactory()
    {
        return SocketFactory.getDefault();
    }

    protected abstract  void postProcessEvent( E event );

    protected abstract  ch.qos.logback.core.spi.PreSerializationTransformer<E> getPST();

    public  void setRemoteHost( java.lang.String host )
    {
        remoteHost = host;
    }

    public  java.lang.String getRemoteHost()
    {
        return remoteHost;
    }

    public  void setPort( int port )
    {
        this.port = port;
    }

    public  int getPort()
    {
        return port;
    }

    public  void setReconnectionDelay( ch.qos.logback.core.util.Duration delay )
    {
        this.reconnectionDelay = delay;
    }

    public  ch.qos.logback.core.util.Duration getReconnectionDelay()
    {
        return reconnectionDelay;
    }

    public  void setQueueSize( int queueSize )
    {
        this.queueSize = queueSize;
    }

    public  int getQueueSize()
    {
        return queueSize;
    }

    public  void setEventDelayLimit( ch.qos.logback.core.util.Duration eventDelayLimit )
    {
        this.eventDelayLimit = eventDelayLimit;
    }

    public  ch.qos.logback.core.util.Duration getEventDelayLimit()
    {
        return eventDelayLimit;
    }

     void setAcceptConnectionTimeout( int acceptConnectionTimeout )
    {
        this.acceptConnectionTimeout = acceptConnectionTimeout;
    }

}
