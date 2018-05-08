// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;


public class LocationUtil
{

    public static final java.lang.String SCHEME_PATTERN = "^\\p{Alpha}[\\p{Alnum}+.-]*:.*$";

    public static final java.lang.String CLASSPATH_SCHEME = "classpath:";

    public static  java.net.URL urlForResource( java.lang.String location )
        throws java.net.MalformedURLException, java.io.FileNotFoundException
    {
        if (location == null) {
            throw new java.lang.NullPointerException( "location is required" );
        }
        java.net.URL url = null;
        if (!location.matches( SCHEME_PATTERN )) {
            url = Loader.getResourceBySelfClassLoader( location );
        } else {
            if (location.startsWith( CLASSPATH_SCHEME )) {
                java.lang.String path = location.substring( CLASSPATH_SCHEME.length() );
                if (path.startsWith( "/" )) {
                    path = path.substring( 1 );
                }
                if (path.length() <= 0) {
                    throw new java.net.MalformedURLException( "path is required" );
                }
                url = Loader.getResourceBySelfClassLoader( path );
            } else {
                url = new java.net.URL( location );
            }
        }
        if (url == null) {
            throw new java.io.FileNotFoundException( location );
        }
        return url;
    }

}
