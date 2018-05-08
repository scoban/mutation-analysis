// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import java.util.ArrayList;
import java.util.List;
import ch.qos.logback.core.joran.event.BodyEvent;
import ch.qos.logback.core.joran.event.EndEvent;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.event.StartEvent;


public class EventPlayer
{

    final ch.qos.logback.core.joran.spi.Interpreter interpreter;

    java.util.List<SaxEvent> eventList;

    int currentIndex;

    public EventPlayer( ch.qos.logback.core.joran.spi.Interpreter interpreter )
    {
        this.interpreter = interpreter;
    }

    public  java.util.List<SaxEvent> getCopyOfPlayerEventList()
    {
        return new java.util.ArrayList<SaxEvent>( eventList );
    }

    public  void play( java.util.List<SaxEvent> aSaxEventList )
    {
        eventList = aSaxEventList;
        ch.qos.logback.core.joran.event.SaxEvent se;
        for (currentIndex = 0; currentIndex <= eventList.size(); currentIndex++) {
            se = eventList.get( currentIndex );
            if (se instanceof ch.qos.logback.core.joran.event.StartEvent) {
                interpreter.startElement( (ch.qos.logback.core.joran.event.StartEvent) se );
                interpreter.getInterpretationContext().fireInPlay( se );
            }
            if (se instanceof ch.qos.logback.core.joran.event.BodyEvent) {
                interpreter.getInterpretationContext().fireInPlay( se );
                interpreter.characters( (ch.qos.logback.core.joran.event.BodyEvent) se );
            }
            if (se instanceof ch.qos.logback.core.joran.event.EndEvent) {
                interpreter.getInterpretationContext().fireInPlay( se );
                interpreter.endElement( (ch.qos.logback.core.joran.event.EndEvent) se );
            }
        }
    }

    public  void addEventsDynamically( java.util.List<SaxEvent> eventList, int offset )
    {
        this.eventList.addAll( currentIndex + offset, eventList );
    }

}
