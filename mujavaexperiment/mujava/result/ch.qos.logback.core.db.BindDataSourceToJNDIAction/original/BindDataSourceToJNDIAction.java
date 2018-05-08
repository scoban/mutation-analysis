// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.joran.util.PropertySetter;
import ch.qos.logback.core.joran.util.beans.BeanDescriptionCache;
import ch.qos.logback.core.util.OptionHelper;


public class BindDataSourceToJNDIAction extends ch.qos.logback.core.joran.action.Action
{

    static final java.lang.String DATA_SOURCE_CLASS = "dataSourceClass";

    static final java.lang.String URL = "url";

    static final java.lang.String USER = "user";

    static final java.lang.String PASSWORD = "password";

    private final ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache;

    public BindDataSourceToJNDIAction( ch.qos.logback.core.joran.util.beans.BeanDescriptionCache beanDescriptionCache )
    {
        this.beanDescriptionCache = beanDescriptionCache;
    }

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        java.lang.String dsClassName = ec.getProperty( DATA_SOURCE_CLASS );
        if (OptionHelper.isEmpty( dsClassName )) {
            addWarn( "dsClassName is a required parameter" );
            ec.addError( "dsClassName is a required parameter" );
            return;
        }
        java.lang.String urlStr = ec.getProperty( URL );
        java.lang.String userStr = ec.getProperty( USER );
        java.lang.String passwordStr = ec.getProperty( PASSWORD );
        try {
            javax.sql.DataSource ds = (javax.sql.DataSource) OptionHelper.instantiateByClassName( dsClassName, javax.sql.DataSource.class, context );
            ch.qos.logback.core.joran.util.PropertySetter setter = new ch.qos.logback.core.joran.util.PropertySetter( beanDescriptionCache, ds );
            setter.setContext( context );
            if (!OptionHelper.isEmpty( urlStr )) {
                setter.setProperty( "url", urlStr );
            }
            if (!OptionHelper.isEmpty( userStr )) {
                setter.setProperty( "user", userStr );
            }
            if (!OptionHelper.isEmpty( passwordStr )) {
                setter.setProperty( "password", passwordStr );
            }
            javax.naming.Context ctx = new javax.naming.InitialContext();
            ctx.rebind( "dataSource", ds );
        } catch ( java.lang.Exception oops ) {
            addError( "Could not bind  datasource. Reported error follows.", oops );
            ec.addError( "Could not not bind  datasource of type [" + dsClassName + "]." );
        }
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
    {
    }

}
