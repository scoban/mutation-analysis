// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.util.Properties;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.util.ContextUtil;
import ch.qos.logback.core.util.OptionHelper;


public class ActionUtil
{

    public enum Scope 
    {
        LOCAL,
        CONTEXT,
        SYSTEM;

    }

    public static  ch.qos.logback.core.joran.action.ActionUtil.Scope stringToScope( java.lang.String scopeStr )
    {
        if (Scope.SYSTEM.toString().equalsIgnoreCase( scopeStr )) {
            return Scope.SYSTEM;
        }
        if (Scope.CONTEXT.toString().equalsIgnoreCase( scopeStr )) {
            return Scope.CONTEXT;
        }
        return Scope.LOCAL;
    }

    public static  void setProperty( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.lang.String key, java.lang.String value, ch.qos.logback.core.joran.action.ActionUtil.Scope scope )
    {
        switch (scope) {
        case LOCAL :
            ic.addSubstitutionProperty( key, value );
            break;

        case CONTEXT :
            ic.getContext().putProperty( key, value );
            break;

        case SYSTEM :
            OptionHelper.setSystemProperty( ic, key, value );

        }
    }

    public static  void setProperties( ch.qos.logback.core.joran.spi.InterpretationContext ic, java.util.Properties props, ch.qos.logback.core.joran.action.ActionUtil.Scope scope )
    {
        switch (scope) {
        case LOCAL :
            ic.addSubstitutionProperties( props );
            break;

        case CONTEXT :
            ch.qos.logback.core.util.ContextUtil cu = new ch.qos.logback.core.util.ContextUtil( ic.getContext() );
            cu.addProperties( props );
            break;

        case SYSTEM :
            OptionHelper.setSystemProperties( ic, props );

        }
    }

}
