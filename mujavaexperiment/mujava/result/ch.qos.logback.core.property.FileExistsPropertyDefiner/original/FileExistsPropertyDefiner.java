// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.property;


import ch.qos.logback.core.PropertyDefinerBase;
import ch.qos.logback.core.util.OptionHelper;
import java.io.File;


public class FileExistsPropertyDefiner extends ch.qos.logback.core.PropertyDefinerBase
{

    java.lang.String path;

    public  java.lang.String getPath()
    {
        return path;
    }

    public  void setPath( java.lang.String path )
    {
        this.path = path;
    }

    public  java.lang.String getPropertyValue()
    {
        if (OptionHelper.isEmpty( path )) {
            addError( "The \"path\" property must be set." );
            return null;
        }
        java.io.File file = new java.io.File( path );
        return booleanAsStr( file.exists() );
    }

}
