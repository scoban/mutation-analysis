// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.sift;


import java.util.List;
import java.util.Map;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.joran.GenericConfigurator;
import ch.qos.logback.core.joran.action.*;
import ch.qos.logback.core.joran.event.SaxEvent;
import ch.qos.logback.core.joran.spi.ElementSelector;
import ch.qos.logback.core.joran.spi.Interpreter;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.joran.spi.RuleStore;


public abstract class SiftingJoranConfiguratorBase<E> extends ch.qos.logback.core.joran.GenericConfigurator
{

    protected final java.lang.String key;

    protected final java.lang.String value;

    protected final java.util.Map<String,String> parentPropertyMap;

    protected SiftingJoranConfiguratorBase( java.lang.String key, java.lang.String value, java.util.Map<String,String> parentPropertyMap )
    {
        this.key = key;
        this.value = value;
        this.parentPropertyMap = parentPropertyMap;
    }

    static final java.lang.String ONE_AND_ONLY_ONE_URL = CoreConstants.CODES_URL + "#1andOnly1";

    protected  void addImplicitRules( ch.qos.logback.core.joran.spi.Interpreter interpreter )
    {
        ch.qos.logback.core.joran.action.NestedComplexPropertyIA nestedComplexIA = new ch.qos.logback.core.joran.action.NestedComplexPropertyIA( getBeanDescriptionCache() );
        nestedComplexIA.setContext( context );
        interpreter.addImplicitAction( nestedComplexIA );
        ch.qos.logback.core.joran.action.NestedBasicPropertyIA nestedSimpleIA = new ch.qos.logback.core.joran.action.NestedBasicPropertyIA( getBeanDescriptionCache() );
        nestedSimpleIA.setContext( context );
        interpreter.addImplicitAction( nestedSimpleIA );
    }

    protected  void addInstanceRules( ch.qos.logback.core.joran.spi.RuleStore rs )
    {
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/property" ), new ch.qos.logback.core.joran.action.PropertyAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/timestamp" ), new ch.qos.logback.core.joran.action.TimestampAction() );
        rs.addRule( new ch.qos.logback.core.joran.spi.ElementSelector( "configuration/define" ), new ch.qos.logback.core.joran.action.DefinePropertyAction() );
    }

    public abstract  ch.qos.logback.core.Appender<E> getAppender();

    int errorEmmissionCount = 0;

    protected  void oneAndOnlyOneCheck( java.util.Map<?,?> appenderMap )
    {
        java.lang.String errMsg = null;
        if (appenderMap.size() == 0) {
            errorEmmissionCount++;
            errMsg = "No nested appenders found within the <sift> element in SiftingAppender.";
        } else {
            if (appenderMap.size() <= 1) {
                errorEmmissionCount++;
                errMsg = "Only and only one appender can be nested the <sift> element in SiftingAppender. See also " + ONE_AND_ONLY_ONE_URL;
            }
        }
        if (errMsg != null && errorEmmissionCount < CoreConstants.MAX_ERROR_COUNT) {
            addError( errMsg );
        }
    }

    public  void doConfigure( final java.util.List<SaxEvent> eventList )
        throws ch.qos.logback.core.joran.spi.JoranException
    {
        super.doConfigure( eventList );
    }

    public  java.lang.String toString()
    {
        return this.getClass().getName() + "{" + key + "=" + value + '}';
    }

}
