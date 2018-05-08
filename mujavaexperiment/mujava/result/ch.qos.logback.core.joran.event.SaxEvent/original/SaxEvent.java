// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.event;


import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;


public class SaxEvent
{

    public final java.lang.String namespaceURI;

    public final java.lang.String localName;

    public final java.lang.String qName;

    public final org.xml.sax.Locator locator;

    SaxEvent( java.lang.String namespaceURI, java.lang.String localName, java.lang.String qName, org.xml.sax.Locator locator )
    {
        this.namespaceURI = namespaceURI;
        this.localName = localName;
        this.qName = qName;
        this.locator = new org.xml.sax.helpers.LocatorImpl( locator );
    }

    public  java.lang.String getLocalName()
    {
        return localName;
    }

    public  org.xml.sax.Locator getLocator()
    {
        return locator;
    }

    public  java.lang.String getNamespaceURI()
    {
        return namespaceURI;
    }

    public  java.lang.String getQName()
    {
        return qName;
    }

}
