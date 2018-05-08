// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event.stax;


import javax.xml.stream.Location;


public class StaxEvent
{

    final java.lang.String name;

    final javax.xml.stream.Location location;

    StaxEvent( java.lang.String name, javax.xml.stream.Location location )
    {
        this.name = name;
        this.location = location;
    }

    public  java.lang.String getName()
    {
        return name;
    }

    public  javax.xml.stream.Location getLocation()
    {
        return location;
    }

}
