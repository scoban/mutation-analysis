// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.action;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.xml.sax.Attributes;
import ch.qos.logback.core.joran.action.ActionUtil.Scope;
import ch.qos.logback.core.joran.spi.InterpretationContext;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;


public class PropertyAction extends ch.qos.logback.core.joran.action.Action
{

    static final java.lang.String RESOURCE_ATTRIBUTE = "resource";

    static java.lang.String INVALID_ATTRIBUTES = "In <property> element, either the \"file\" attribute alone, or " + "the \"resource\" element alone, or both the \"name\" and \"value\" attributes must be set.";

    public  void begin( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String localName, org.xml.sax.Attributes attributes )
    {
        if ("substitutionProperty".equals( localName )) {
            addWarn( "[substitutionProperty] element has been deprecated. Please use the [property] element instead." );
        }
        java.lang.String name = attributes.getValue( NAME_ATTRIBUTE );
        java.lang.String value = attributes.getValue( VALUE_ATTRIBUTE );
        java.lang.String scopeStr = attributes.getValue( SCOPE_ATTRIBUTE );
        ch.qos.logback.core.joran.action.ActionUtil.Scope scope = ActionUtil.stringToScope( scopeStr );
        if (checkFileAttributeSanity( attributes )) {
            java.lang.String file = attributes.getValue( FILE_ATTRIBUTE );
            file = ec.subst( file );
            try {
                java.io.FileInputStream istream = new java.io.FileInputStream( file );
                loadAndSetProperties( ec, istream, scope );
            } catch ( java.io.FileNotFoundException e ) {
                addError( "Could not find properties file [" + file + "]." );
            } catch ( java.io.IOException e1 ) {
                addError( "Could not read properties file [" + file + "].", e1 );
            }
        } else {
            if (checkResourceAttributeSanity( attributes )) {
                java.lang.String resource = attributes.getValue( RESOURCE_ATTRIBUTE );
                resource = ec.subst( resource );
                java.net.URL resourceURL = Loader.getResourceBySelfClassLoader( resource );
                if (resourceURL == null) {
                    addError( "Could not find resource [" + resource + "]." );
                } else {
                    try {
                        java.io.InputStream istream = resourceURL.openStream();
                        loadAndSetProperties( ec, istream, scope );
                    } catch ( java.io.IOException e ) {
                        addError( "Could not read resource file [" + resource + "].", e );
                    }
                }
            } else {
                if (checkValueNameAttributesSanity( attributes )) {
                    value = RegularEscapeUtil.basicEscape( value );
                    value = value.trim();
                    value = ec.subst( value );
                    ActionUtil.setProperty( ec, name, value, scope );
                } else {
                    addError( INVALID_ATTRIBUTES );
                }
            }
        }
    }

     void loadAndSetProperties( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.io.InputStream istream, ch.qos.logback.core.joran.action.ActionUtil.Scope scope )
        throws java.io.IOException
    {
        java.util.Properties props = new java.util.Properties();
        props.load( istream );
        istream.close();
        ActionUtil.setProperties( ec, props, scope );
    }

     boolean checkFileAttributeSanity( org.xml.sax.Attributes attributes )
    {
        java.lang.String file = attributes.getValue( FILE_ATTRIBUTE );
        java.lang.String name = attributes.getValue( NAME_ATTRIBUTE );
        java.lang.String value = attributes.getValue( VALUE_ATTRIBUTE );
        java.lang.String resource = attributes.getValue( RESOURCE_ATTRIBUTE );
        return !OptionHelper.isEmpty( file ) && (OptionHelper.isEmpty( name ) && OptionHelper.isEmpty( value ) && OptionHelper.isEmpty( resource ));
    }

     boolean checkResourceAttributeSanity( org.xml.sax.Attributes attributes )
    {
        java.lang.String file = attributes.getValue( FILE_ATTRIBUTE );
        java.lang.String name = attributes.getValue( NAME_ATTRIBUTE );
        java.lang.String value = attributes.getValue( VALUE_ATTRIBUTE );
        java.lang.String resource = attributes.getValue( RESOURCE_ATTRIBUTE );
        return !OptionHelper.isEmpty( resource ) && (OptionHelper.isEmpty( name ) && OptionHelper.isEmpty( value ) && OptionHelper.isEmpty( file ));
    }

     boolean checkValueNameAttributesSanity( org.xml.sax.Attributes attributes )
    {
        java.lang.String file = attributes.getValue( FILE_ATTRIBUTE );
        java.lang.String name = attributes.getValue( NAME_ATTRIBUTE );
        java.lang.String value = attributes.getValue( VALUE_ATTRIBUTE );
        java.lang.String resource = attributes.getValue( RESOURCE_ATTRIBUTE );
        return !(OptionHelper.isEmpty( name ) || OptionHelper.isEmpty( value )) && (OptionHelper.isEmpty( file ) && OptionHelper.isEmpty( resource ));
    }

    public  void end( ch.qos.logback.core.joran.spi.InterpretationContext ec, java.lang.String name )
    {
    }

    public  void finish( ch.qos.logback.core.joran.spi.InterpretationContext ec )
    {
    }

}
