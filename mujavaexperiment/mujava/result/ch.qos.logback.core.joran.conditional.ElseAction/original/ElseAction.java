// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.conditional;


import java.util.List;
import ch.qos.logback.core.joran.event.SaxEvent;


public class ElseAction extends ch.qos.logback.core.joran.conditional.ThenOrElseActionBase
{

     void registerEventList( ch.qos.logback.core.joran.conditional.IfAction ifAction, java.util.List<SaxEvent> eventList )
    {
        ifAction.setElseSaxEventList( eventList );
    }

}
