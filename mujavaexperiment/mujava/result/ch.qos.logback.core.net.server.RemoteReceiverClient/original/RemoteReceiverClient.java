// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.server;


import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import ch.qos.logback.core.spi.ContextAware;


interface RemoteReceiverClient extends ch.qos.logback.core.net.server.Client, ch.qos.logback.core.spi.ContextAware
{

     void setQueue( java.util.concurrent.BlockingQueue<Serializable> queue );

     boolean offer( java.io.Serializable event );

}
