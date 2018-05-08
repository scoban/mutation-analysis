// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event.stax;


import javax.xml.stream.Location;


public class BodyEvent extends ch.qos.logback.core.joran.event.stax.StaxEvent
{

    private java.lang.String text;

    BodyEvent( java.lang.String text, javax.xml.stream.Location location )
    {
        super( null, location );
        this.text = text;
    }

    public  java.lang.String getText()
    {
        return text;
    }

     void append( java.lang.String txt )
    {
        text += txt;
    }

    public  java.lang.String toString()
    {
        return "BodyEvent(" + getText() + ")" + location.getLineNumber() + "," + location.getColumnNumber();
    }

}
