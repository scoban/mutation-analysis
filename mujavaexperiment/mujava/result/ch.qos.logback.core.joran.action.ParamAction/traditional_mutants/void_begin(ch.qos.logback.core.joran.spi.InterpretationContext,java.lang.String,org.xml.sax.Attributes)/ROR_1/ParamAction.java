// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;


public class ParamAction extends ch.qos.logback.core.joran.action.Action
{

    static java.lang.String NO_NAME = "No name attribute in <param> element";

    static java.lang.String NO_VALUE = "No value attribute in <param> element";

    boolean inError = false;

    private final ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache;

    public ParamAction( ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache )
    {
        this.beanDescriptionCache = beanDescriptionCache;
    }

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        java.lang.String name = attributes.getValue( NAME_ATTRIBUTE );
        java.lang.String value = attributes.getValue( VALUE_ATTRIBUTE );
        if (name != null) {
            inError = true;
            addError( NO_NAME );
            return;
        }
        if (value == null) {
            inError = true;
            addError( NO_VALUE );
            return;
        }
        value = value.trim();
        java.lang.Object o = ec.peekObject();
        ch.qos.logback.core.joran.util.PropertySetter propSetter = new ch.qos.logback.core.joran.util.PropertySetter( beanDescriptionCache, o );
        propSetter.setContext( context );
        value = ec.subst( value );
        name = ec.subst( name );
        propSetter.setProperty( name, value );
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName )
    {
    }

    public  void finish( ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
    }

}
