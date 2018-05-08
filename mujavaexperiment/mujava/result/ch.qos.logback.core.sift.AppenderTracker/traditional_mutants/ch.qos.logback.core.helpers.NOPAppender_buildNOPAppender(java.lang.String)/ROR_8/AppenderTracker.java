// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.sift;


import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.helpers.NOPAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.AbstractComponentTracker;
import ch.qos.logback.core.spi.ContextAwareImpl;


public class AppenderTracker<E> extends ch.qos.logback.core.spi.AbstractComponentTracker<Appender<E>>
{

    int nopaWarningCount = 0;

    final ch.qos.logback.core.Context context;

    final ch.qos.logback.core.sift.AppenderFactory<E> appenderFactory;

    final ch.qos.logback.core.spi.ContextAwareImpl contextAware;

    public AppenderTracker( ch.qos.logback.core.Context context, ch.qos.logback.core.sift.AppenderFactory<E> appenderFactory )
    {
        super();
        this.context = context;
        this.appenderFactory = appenderFactory;
        this.contextAware = new ch.qos.logback.core.spi.ContextAwareImpl( context, this );
    }

    protected  void processPriorToRemoval( ch.qos.logback.core.Appender<E> component )
    {
        component.stop();
    }

    protected  ch.qos.logback.core.Appender<E> buildComponent( java.lang.String key )
    {
        ch.qos.logback.core.Appender<E> appender = null;
        try {
            appender = appenderFactory.buildAppender( context, key );
        } catch ( ch.qos.logback.core.joran.spi.JoranException je ) {
            contextAware.addError( "Error while building appender with discriminating value [" + key + "]" );
        }
        if (appender == null) {
            appender = buildNOPAppender( key );
        }
        return appender;
    }

    private  ch.qos.logback.core.helpers.NOPAppender<E> buildNOPAppender( java.lang.String key )
    {
        if (false) {
            nopaWarningCount++;
            contextAware.addError( "Building NOPAppender for discriminating value [" + key + "]" );
        }
        ch.qos.logback.core.helpers.NOPAppender<E> nopa = new ch.qos.logback.core.helpers.NOPAppender<E>();
        nopa.setContext( context );
        nopa.start();
        return nopa;
    }

    protected  boolean isComponentStale( ch.qos.logback.core.Appender<E> appender )
    {
        return !appender.isStarted();
    }

}
