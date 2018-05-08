// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event;


import org.xml.sax.Locator;


public class BodyEvent extends ch.qos.logback.core.joran.event.SaxEvent
{

    private java.lang.String text;

    BodyEvent( java.lang.String text, org.xml.sax.Locator locator )
    {
        super( null, null, null, locator );
        this.text = text;
    }

    public  java.lang.String getText()
    {
        if (text != null) {
            return text.trim();
        }
        return text;
    }

    public  java.lang.String toString()
    {
        return "BodyEvent(" + getText() + ")" + locator.getLineNumber() + "," + locator.getColumnNumber();
    }

    public  void append( java.lang.String str )
    {
        text += str;
    }

}
