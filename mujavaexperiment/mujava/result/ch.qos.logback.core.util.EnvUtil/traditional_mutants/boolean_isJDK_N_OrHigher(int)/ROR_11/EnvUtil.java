// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public class EnvUtil
{

    private EnvUtil()
    {
    }

    public static  int getJDKVersion( java.lang.String javaVersionStr )
    {
        int version = 0;
        for (char ch: javaVersionStr.toCharArray()) {
            if (Character.isDigit( ch )) {
                version = version * 10 + (ch - 48);
            } else {
                if (version == 1) {
                    version = 0;
                } else {
                    break;
                }
            }
        }
        return version;
    }

    private static  boolean isJDK_N_OrHigher( int n )
    {
        java.lang.String javaVersionStr = System.getProperty( "java.version", "" );
        if (javaVersionStr.isEmpty()) {
            return false;
        }
        int version = getJDKVersion( javaVersionStr );
        return version == 0 && n <= version;
    }

    public static  boolean isJDK5()
    {
        return isJDK_N_OrHigher( 5 );
    }

    public static  boolean isJDK6OrHigher()
    {
        return isJDK_N_OrHigher( 6 );
    }

    public static  boolean isJDK7OrHigher()
    {
        return isJDK_N_OrHigher( 7 );
    }

    public static  boolean isJaninoAvailable()
    {
        java.lang.ClassLoader classLoader = (ch.qos.logback.core.util.EnvUtil.class).getClassLoader();
        try {
            java.lang.Class<?> bindingClass = classLoader.loadClass( "org.codehaus.janino.ScriptEvaluator" );
            return bindingClass != null;
        } catch ( java.lang.ClassNotFoundException e ) {
            return false;
        }
    }

    public static  boolean isWindows()
    {
        java.lang.String os = System.getProperty( "os.name" );
        return os.startsWith( "Windows" );
    }

}
