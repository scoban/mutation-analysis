// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Set;
import java.util.HashSet;
import ch.qos.logback.core.Context;


public class Loader
{

    static final java.lang.String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";

    private static boolean ignoreTCL = false;

    public static final java.lang.String IGNORE_TCL_PROPERTY_NAME = "logback.ignoreTCL";

    private static boolean HAS_GET_CLASS_LOADER_PERMISSION = false;

    static {
        java.lang.String ignoreTCLProp = OptionHelper.getSystemProperty( IGNORE_TCL_PROPERTY_NAME, null );
        if (ignoreTCLProp != null) {
            ignoreTCL = OptionHelper.toBoolean( ignoreTCLProp, true );
        }
        HAS_GET_CLASS_LOADER_PERMISSION = AccessController.doPrivileged( new java.security.PrivilegedAction<Boolean>(){
            public  java.lang.Boolean run()
            {
                try {
                    AccessController.checkPermission( new java.lang.RuntimePermission( "getClassLoader" ) );
                    return true;
                } catch ( java.lang.SecurityException e ) {
                    return false;
                }
            }
        } );
    }

    public static  java.util.Set<URL> getResources( java.lang.String resource, java.lang.ClassLoader classLoader )
        throws java.io.IOException
    {
        java.util.Set<URL> urlSet = new java.util.HashSet<URL>();
        java.util.Enumeration<URL> urlEnum = classLoader.getResources( resource );
        while (urlEnum.hasMoreElements()) {
            java.net.URL url = urlEnum.nextElement();
            urlSet.add( url );
        }
        return urlSet;
    }

    public static  java.net.URL getResource( java.lang.String resource, java.lang.ClassLoader classLoader )
    {
        try {
            return classLoader.getResource( resource );
        } catch ( java.lang.Throwable t ) {
            return null;
        }
    }

    public static  java.net.URL getResourceBySelfClassLoader( java.lang.String resource )
    {
        return getResource( resource, getClassLoaderOfClass( ch.qos.logback.core.util.Loader.class ) );
    }

    public static  java.lang.ClassLoader getTCL()
    {
        return Thread.currentThread().getContextClassLoader();
    }

    public static  java.lang.Class<?> loadClass( java.lang.String clazz, ch.qos.logback.core.Context context )
        throws java.lang.ClassNotFoundException
    {
        java.lang.ClassLoader cl = getClassLoaderOfObject( context );
        return cl.loadClass( clazz );
    }

    public static  java.lang.ClassLoader getClassLoaderOfObject( java.lang.Object o )
    {
        if (o == null) {
            throw new java.lang.NullPointerException( "Argument cannot be null" );
        }
        return getClassLoaderOfClass( o.getClass() );
    }

    public static  java.lang.ClassLoader getClassLoaderAsPrivileged( final java.lang.Class<?> clazz )
    {
        if (!HAS_GET_CLASS_LOADER_PERMISSION) {
            return null;
        } else {
            return AccessController.doPrivileged( new java.security.PrivilegedAction<ClassLoader>(){
                public  java.lang.ClassLoader run()
                {
                    return clazz.getClassLoader();
                }
            } );
        }
    }

    public static  java.lang.ClassLoader getClassLoaderOfClass( final java.lang.Class<?> clazz )
    {
        java.lang.ClassLoader cl = clazz.getClassLoader();
        if (cl == null) {
            return ClassLoader.getSystemClassLoader();
        } else {
            return cl;
        }
    }

    public static  java.lang.Class<?> loadClass( java.lang.String clazz )
        throws java.lang.ClassNotFoundException
    {
        if (ignoreTCL) {
            return Class.forName( clazz );
        } else {
            try {
                return getTCL().loadClass( clazz );
            } catch ( java.lang.Throwable e ) {
                return Class.forName( clazz );
            }
        }
    }

}
