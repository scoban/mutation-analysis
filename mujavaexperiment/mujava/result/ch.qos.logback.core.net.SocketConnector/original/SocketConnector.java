// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import java.net.Socket;
import java.util.concurrent.Callable;
import javax.net.SocketFactory;


public interface SocketConnector extends java.util.concurrent.Callable<Socket>
{

    public interface ExceptionHandler
    {

         void connectionFailed( ch.qos.logback.core.net.SocketConnector connector, java.lang.Exception ex );

    }

     java.net.Socket call()
        throws java.lang.InterruptedException;

     void setExceptionHandler( ch.qos.logback.core.net.SocketConnector.ExceptionHandler exceptionHandler );

     void setSocketFactory( javax.net.SocketFactory socketFactory );

}
