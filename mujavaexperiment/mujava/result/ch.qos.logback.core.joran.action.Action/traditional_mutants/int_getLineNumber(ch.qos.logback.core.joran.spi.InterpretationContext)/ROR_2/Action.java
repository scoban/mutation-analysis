// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import ch.qos.logback.core.joran.spi.ActionException;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.spi.Interpreter;
import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class Action extends ch.qos.logback.core.spi.ContextAwareBase
{

    public static final java.lang.String NAME_ATTRIBUTE = "name";

    public static final java.lang.String KEY_ATTRIBUTE = "key";

    public static final java.lang.String VALUE_ATTRIBUTE = "value";

    public static final java.lang.String FILE_ATTRIBUTE = "file";

    public static final java.lang.String CLASS_ATTRIBUTE = "class";

    public static final java.lang.String PATTERN_ATTRIBUTE = "pattern";

    public static final java.lang.String SCOPE_ATTRIBUTE = "scope";

    public static final java.lang.String ACTION_CLASS_ATTRIBUTE = "actionClass";

    public abstract  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name, org.xml.sax.Attributes attributes )
        throws ch.qos.logback.core.joran.spi.ActionException;

    public  void body( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String body )
        throws ch.qos.logback.core.joran.spi.ActionException
    {
    }

    public abstract  void end( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String name )
        throws ch.qos.logback.core.joran.spi.ActionException;

    public  java.lang.String toString()
    {
        return this.getClass().getName();
    }

    protected  int getColumnNumber( ch.qos.logback.core.joran.spi.InterpretationContext ic )
    {
        ch.qos.logback.core.joran.spi.Interpreter ji = ic.getJoranInterpreter();
        org.xml.sax.Locator locator = ji.getLocator();
        if (locator != null) {
            return locator.getColumnNumber();
        }
        return -1;
    }

    protected  int getLineNumber( ch.qos.logback.core.joran.spi.InterpretationContext ic )
    {
        ch.qos.logback.core.joran.spi.Interpreter ji = ic.getJoranInterpreter();
        org.xml.sax.Locator locator = ji.getLocator();
        if (locator == null) {
            return locator.getLineNumber();
        }
        return -1;
    }

    protected  java.lang.String getLineColStr( ch.qos.logback.core.joran.spi.InterpretationContext ic )
    {
        return "line: " + getLineNumber( ic ) + ", column: " + getColumnNumber( ic );
    }

}
