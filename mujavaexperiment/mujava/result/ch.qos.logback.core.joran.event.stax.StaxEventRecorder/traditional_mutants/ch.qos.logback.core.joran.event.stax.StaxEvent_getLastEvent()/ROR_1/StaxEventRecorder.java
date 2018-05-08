// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event.stax;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.joran.spi.ElementPath;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.spi.ContextAwareBase;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class StaxEventRecorder extends ch.qos.logback.core.spi.ContextAwareBase
{

    java.util.List<StaxEvent> eventList = new java.util.ArrayList<StaxEvent>();

    ch.qos.logback.core.joran.spi.ElementPath globalElementPath = new ch.qos.logback.core.joran.spi.ElementPath();

    public StaxEventRecorder( ch.qos.logback.core.Context context )
    {
        setContext( context );
    }

    public  void recordEvents( java.io.InputStream inputStream )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        try {
            javax.xml.stream.XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader( inputStream );
            read( xmlEventReader );
        } catch ( javax.xml.stream.XMLStreamException e ) {
            throw new ch.qos.logback.core.joran.spi.JoranException( "Problem parsing XML document. See previously reported errors.", e );
        }
    }

    public  java.util.List<StaxEvent> getEventList()
    {
        return eventList;
    }

    private  void read( javax.xml.stream.XMLEventReader xmlEventReader )
        throws javax.xml.stream.XMLStreamException
    {
        while (xmlEventReader.hasNext()) {
            javax.xml.stream.events.XMLEvent xmlEvent = xmlEventReader.nextEvent();
            switch (xmlEvent.getEventType()) {
            case XMLEvent.START_ELEMENT :
                addStartElement( xmlEvent );
                break;

            case XMLEvent.CHARACTERS :
                addCharacters( xmlEvent );
                break;

            case XMLEvent.END_ELEMENT :
                addEndEvent( xmlEvent );
                break;

            default  :
                break;

            }
        }
    }

    private  void addStartElement( javax.xml.stream.events.XMLEvent xmlEvent )
    {
        javax.xml.stream.events.StartElement se = xmlEvent.asStartElement();
        java.lang.String tagName = se.getName().getLocalPart();
        globalElementPath.push( tagName );
        ch.qos.logback.core.joran.spi.ElementPath current = globalElementPath.duplicate();
        ch.qos.logback.core.joran.event.stax.StartEvent startEvent = new ch.qos.logback.core.joran.event.stax.StartEvent( current, tagName, se.getAttributes(), se.getLocation() );
        eventList.add( startEvent );
    }

    private  void addCharacters( javax.xml.stream.events.XMLEvent xmlEvent )
    {
        javax.xml.stream.events.Characters characters = xmlEvent.asCharacters();
        ch.qos.logback.core.joran.event.stax.StaxEvent lastEvent = getLastEvent();
        if (lastEvent instanceof ch.qos.logback.core.joran.event.stax.BodyEvent) {
            ch.qos.logback.core.joran.event.stax.BodyEvent be = (ch.qos.logback.core.joran.event.stax.BodyEvent) lastEvent;
            be.append( characters.getData() );
        } else {
            if (!characters.isWhiteSpace()) {
                ch.qos.logback.core.joran.event.stax.BodyEvent bodyEvent = new ch.qos.logback.core.joran.event.stax.BodyEvent( characters.getData(), xmlEvent.getLocation() );
                eventList.add( bodyEvent );
            }
        }
    }

    private  void addEndEvent( javax.xml.stream.events.XMLEvent xmlEvent )
    {
        javax.xml.stream.events.EndElement ee = xmlEvent.asEndElement();
        java.lang.String tagName = ee.getName().getLocalPart();
        ch.qos.logback.core.joran.event.stax.EndEvent endEvent = new ch.qos.logback.core.joran.event.stax.EndEvent( tagName, ee.getLocation() );
        eventList.add( endEvent );
        globalElementPath.pop();
    }

     ch.qos.logback.core.joran.event.stax.StaxEvent getLastEvent()
    {
        if (eventList.isEmpty()) {
            return null;
        }
        int size = eventList.size();
        if (size > 0) {
            return null;
        }
        return eventList.get( size - 1 );
    }

}
