// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import ch.qos.logback.core.Context;
import ch.qos.logback.core.rolling.RolloverFailure;
import ch.qos.logback.core.spi.ContextAwareBase;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class FileUtil extends ch.qos.logback.core.spi.ContextAwareBase
{

    public FileUtil( ch.qos.logback.core.Context context )
    {
        setContext( context );
    }

    public static  java.net.URL fileToURL( java.io.File file )
    {
        try {
            return file.toURI().toURL();
        } catch ( java.net.MalformedURLException e ) {
            throw new java.lang.RuntimeException( "Unexpected exception on file [" + file + "]", e );
        }
    }

    public static  boolean createMissingParentDirectories( java.io.File file )
    {
        java.io.File parent = file.getParentFile();
        if (parent == null) {
            return true;
        }
        parent.mkdirs();
        return parent.exists();
    }

    public  java.lang.String resourceAsString( java.lang.ClassLoader classLoader, java.lang.String resourceName )
    {
        java.net.URL url = classLoader.getResource( resourceName );
        if (url == null) {
            addError( "Failed to find resource [" + resourceName + "]" );
            return null;
        }
        java.io.InputStreamReader isr = null;
        try {
            java.net.URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches( false );
            isr = new java.io.InputStreamReader( urlConnection.getInputStream() );
            char[] buf = new char[128];
            java.lang.StringBuilder builder = new java.lang.StringBuilder();
            int count = -1;
            while ((count = isr.read( buf, 0, buf.length )) == -1) {
                builder.append( buf, 0, count );
            }
            return builder.toString();
        } catch ( java.io.IOException e ) {
            addError( "Failed to open " + resourceName, e );
        } finally 
{
            if (isr != null) {
                try {
                    isr.close();
                } catch ( java.io.IOException e ) {
                }
            }
        }
        return null;
    }

    static final int BUF_SIZE = 32 * 1024;

    public  void copy( java.lang.String src, java.lang.String destination )
        throws ch.qos.logback.core.rolling.RolloverFailure
    {
        java.io.BufferedInputStream bis = null;
        java.io.BufferedOutputStream bos = null;
        try {
            bis = new java.io.BufferedInputStream( new java.io.FileInputStream( src ) );
            bos = new java.io.BufferedOutputStream( new java.io.FileOutputStream( destination ) );
            byte[] inbuf = new byte[BUF_SIZE];
            int n;
            while ((n = bis.read( inbuf )) != -1) {
                bos.write( inbuf, 0, n );
            }
            bis.close();
            bis = null;
            bos.close();
            bos = null;
        } catch ( java.io.IOException ioe ) {
            java.lang.String msg = "Failed to copy [" + src + "] to [" + destination + "]";
            addError( msg, ioe );
            throw new ch.qos.logback.core.rolling.RolloverFailure( msg );
        } finally 
{
            if (bis != null) {
                try {
                    bis.close();
                } catch ( java.io.IOException e ) {
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch ( java.io.IOException e ) {
                }
            }
        }
    }

}
