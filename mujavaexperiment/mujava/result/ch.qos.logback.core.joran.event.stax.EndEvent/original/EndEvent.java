// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event.stax;


import javax.xml.stream.Location;


public class EndEvent extends ch.qos.logback.core.joran.event.stax.StaxEvent
{

    public EndEvent( java.lang.String name, javax.xml.stream.Location location )
    {
        super( name, location );
    }

    public  java.lang.String toString()
    {
        return "EndEvent(" + getName() + ")  [" + location.getLineNumber() + "," + location.getColumnNumber() + "]";
    }

}
