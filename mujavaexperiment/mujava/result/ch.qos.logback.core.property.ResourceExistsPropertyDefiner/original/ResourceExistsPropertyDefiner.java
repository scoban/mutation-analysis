// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.property;


import ch.qos.logback.core.PropertyDefinerBase;
import ch.qos.logback.core.util.Loader;
import ch.qos.logback.core.util.OptionHelper;
import java.net.URL;


public class ResourceExistsPropertyDefiner extends ch.qos.logback.core.PropertyDefinerBase
{

    java.lang.String resourceStr;

    public  java.lang.String getResource()
    {
        return resourceStr;
    }

    public  void setResource( java.lang.String resource )
    {
        this.resourceStr = resource;
    }

    public  java.lang.String getPropertyValue()
    {
        if (OptionHelper.isEmpty( resourceStr )) {
            addError( "The \"resource\" property must be set." );
            return null;
        }
        java.net.URL resourceURL = Loader.getResourceBySelfClassLoader( resourceStr );
        return booleanAsStr( resourceURL != null );
    }

}
