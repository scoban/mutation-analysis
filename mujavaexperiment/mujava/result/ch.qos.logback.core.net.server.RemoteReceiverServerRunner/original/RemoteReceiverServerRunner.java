// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;


class RemoteReceiverServerRunner extends ch.qos.logback.core.net.server.ConcurrentServerRunner<RemoteReceiverClient>
{

    private final int clientQueueSize;

    public RemoteReceiverServerRunner( ch.qos.logback.core.net.server.ServerListener<RemoteReceiverClient> listener, java.util.concurrent.Executor executor, int clientQueueSize )
    {
        super( listener, executor );
        this.clientQueueSize = clientQueueSize;
    }

    protected  boolean configureClient( ch.qos.logback.core.net.server.RemoteReceiverClient client )
    {
        client.setContext( getContext() );
        client.setQueue( new java.util.concurrent.ArrayBlockingQueue<Serializable>( clientQueueSize ) );
        return true;
    }

}
