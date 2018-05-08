// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


class RemoteReceiverServerListener extends ch.qos.logback.core.net.server.ServerSocketListener<RemoteReceiverClient>
{

    public RemoteReceiverServerListener( java.net.ServerSocket serverSocket )
    {
        super( serverSocket );
    }

    protected  ch.qos.logback.core.net.server.RemoteReceiverClient createClient( java.lang.String id, java.net.Socket socket )
        throws java.io.IOException
    {
        return new ch.qos.logback.core.net.server.RemoteReceiverStreamClient( id, socket );
    }

}
