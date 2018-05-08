// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.sift;


import java.util.List;
import java.util.Map;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.JoranException;


public abstract class AbstractAppenderFactoryUsingJoran<E> implements ch.qos.logback.core.sift.AppenderFactory<E>
{

    final java.util.List<SaxEvent> eventList;

    protected java.lang.String key;

    protected java.util.Map<String,String> parentPropertyMap;

    protected AbstractAppenderFactoryUsingJoran( java.util.List<SaxEvent> eventList, java.lang.String key, java.util.Map<String,String> parentPropertyMap )
    {
        this.eventList = removeSiftElement( eventList );
        this.key = key;
        this.parentPropertyMap = parentPropertyMap;
    }

     java.util.List<SaxEvent> removeSiftElement( java.util.List<SaxEvent> eventList )
    {
        return eventList.subList( 1, eventList.size() - 1 );
    }

    public abstract  ch.qos.logback.core.sift.SiftingJoranConfiguratorBase<E> getSiftingJoranConfigurator( java.lang.String k );

    public  ch.qos.logback.core.Appender<E> buildAppender( ch.qos.logback.core.Context context, java.lang.String discriminatingValue )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        ch.qos.logback.core.sift.SiftingJoranConfiguratorBase<E> sjc = getSiftingJoranConfigurator( discriminatingValue );
        sjc.setContext( context );
        sjc.doConfigure( eventList );
        return sjc.getAppender();
    }

    public  java.util.List<SaxEvent> getEventList()
    {
        return eventList;
    }

}
