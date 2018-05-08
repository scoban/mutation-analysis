// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net;


import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import ch.qos.logback.core.net.ssl.ConfigurableSSLSocketFactory;
import ch.qos.logback.core.net.ssl.SSLComponent;
import ch.qos.logback.core.net.ssl.SSLConfiguration;
import ch.qos.logback.core.net.ssl.SSLParametersConfiguration;


public abstract class AbstractSSLSocketAppender<E> extends ch.qos.logback.core.net.AbstractSocketAppender<E> implements ch.qos.logback.core.net.ssl.SSLComponent
{

    private ch.qos.logback.core.net.ssl.SSLConfiguration ssl;

    private javax.net.SocketFactory socketFactory;

    protected AbstractSSLSocketAppender()
    {
    }

    protected  javax.net.SocketFactory getSocketFactory()
    {
        return socketFactory;
    }

    public  void start()
    {
        try {
            javax.net.ssl.SSLContext sslContext = getSsl().createContext( this );
            ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters = getSsl().getParameters();
            parameters.setContext( getContext() );
            socketFactory = new ch.qos.logback.core.net.ssl.ConfigurableSSLSocketFactory( parameters, sslContext.getSocketFactory() );
            super.start();
        } catch ( java.lang.Exception ex ) {
            addError( ex.getMessage(), ex );
        }
    }

    public  ch.qos.logback.core.net.ssl.SSLConfiguration getSsl()
    {
        if (ssl == null) {
            ssl = new ch.qos.logback.core.net.ssl.SSLConfiguration();
        }
        return ssl;
    }

    public  void setSsl( ch.qos.logback.core.net.ssl.SSLConfiguration ssl )
    {
        this.ssl = ssl;
    }

}
