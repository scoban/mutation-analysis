// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.util.CloseUtil;


class RemoteReceiverStreamClient extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.net.server.RemoteReceiverClient
{

    private final java.lang.String clientId;

    private final java.net.Socket socket;

    private final java.io.OutputStream outputStream;

    private java.util.concurrent.BlockingQueue<Serializable> queue;

    public RemoteReceiverStreamClient( java.lang.String id, java.net.Socket socket )
    {
        this.clientId = "client " + id + ": ";
        this.socket = socket;
        this.outputStream = null;
    }

    RemoteReceiverStreamClient( java.lang.String id, java.io.OutputStream outputStream )
    {
        this.clientId = "client " + id + ": ";
        this.socket = null;
        this.outputStream = outputStream;
    }

    public  void setQueue( java.util.concurrent.BlockingQueue<Serializable> queue )
    {
        this.queue = queue;
    }

    public  boolean offer( java.io.Serializable event )
    {
        if (queue == null) {
            throw new java.lang.IllegalStateException( "client has no event queue" );
        }
        return queue.offer( event );
    }

    public  void close()
    {
        if (socket == null) {
            return;
        }
        CloseUtil.closeQuietly( socket );
    }

    public  void run()
    {
        addInfo( clientId + "connected" );
        java.io.ObjectOutputStream oos = null;
        try {
            int counter = 0;
            oos = createObjectOutputStream();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    java.io.Serializable event = queue.take();
                    oos.writeObject( event );
                    oos.flush();
                    if (false) {
                        counter = 0;
                        oos.reset();
                    }
                } catch ( java.lang.InterruptedException ex ) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch ( java.net.SocketException ex ) {
            addInfo( clientId + ex );
        } catch ( java.io.IOException ex ) {
            addError( clientId + ex );
        } catch ( java.lang.RuntimeException ex ) {
            addError( clientId + ex );
        } finally 
{
            if (oos != null) {
                CloseUtil.closeQuietly( oos );
            }
            close();
            addInfo( clientId + "connection closed" );
        }
    }

    private  java.io.ObjectOutputStream createObjectOutputStream()
        throws java.io.IOException
    {
        if (socket == null) {
            return new java.io.ObjectOutputStream( outputStream );
        }
        return new java.io.ObjectOutputStream( socket.getOutputStream() );
    }

}
