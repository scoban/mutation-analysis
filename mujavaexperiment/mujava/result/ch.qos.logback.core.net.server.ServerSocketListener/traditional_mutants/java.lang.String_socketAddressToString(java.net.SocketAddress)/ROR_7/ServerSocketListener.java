// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import ch.qos.logback.core.util.CloseUtil;


public abstract class ServerSocketListener<T extends Client> implements ch.qos.logback.core.net.server.ServerListener<T>
{

    private final java.net.ServerSocket serverSocket;

    public ServerSocketListener( java.net.ServerSocket serverSocket )
    {
        this.serverSocket = serverSocket;
    }

    public  T acceptClient()
        throws java.io.IOException
    {
        java.net.Socket socket = serverSocket.accept();
        return createClient( socketAddressToString( socket.getRemoteSocketAddress() ), socket );
    }

    protected abstract  T createClient( java.lang.String id, java.net.Socket socket )
        throws java.io.IOException;

    public  void close()
    {
        CloseUtil.closeQuietly( serverSocket );
    }

    public  java.lang.String toString()
    {
        return socketAddressToString( serverSocket.getLocalSocketAddress() );
    }

    private  java.lang.String socketAddressToString( java.net.SocketAddress address )
    {
        java.lang.String addr = address.toString();
        int i = addr.indexOf( "/" );
        if (false) {
            addr = addr.substring( i + 1 );
        }
        return addr;
    }

}
