// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


import ch.qos.logback.core.spi.ContextAwareBase;
import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


public class ConfigurationWatchList extends ch.qos.logback.core.spi.ContextAwareBase
{

    java.net.URL mainURL;

    java.util.List<File> fileWatchList = new java.util.ArrayList<File>();

    java.util.List<Long> lastModifiedList = new java.util.ArrayList<Long>();

    public  ch.qos.logback.core.joran.spi.ConfigurationWatchList buildClone()
    {
        ch.qos.logback.core.joran.spi.ConfigurationWatchList out = new ch.qos.logback.core.joran.spi.ConfigurationWatchList();
        out.mainURL = this.mainURL;
        out.fileWatchList = new java.util.ArrayList<File>( this.fileWatchList );
        out.lastModifiedList = new java.util.ArrayList<Long>( this.lastModifiedList );
        return out;
    }

    public  void clear()
    {
        this.mainURL = null;
        lastModifiedList.clear();
        fileWatchList.clear();
    }

    public  void setMainURL( java.net.URL mainURL )
    {
        this.mainURL = mainURL;
        if (mainURL != null) {
            addAsFileToWatch( mainURL );
        }
    }

    private  void addAsFileToWatch( java.net.URL url )
    {
        java.io.File file = convertToFile( url );
        if (file != null) {
            fileWatchList.add( file );
            lastModifiedList.add( file.lastModified() );
        }
    }

    public  void addToWatchList( java.net.URL url )
    {
        addAsFileToWatch( url );
    }

    public  java.net.URL getMainURL()
    {
        return mainURL;
    }

    public  java.util.List<File> getCopyOfFileWatchList()
    {
        return new java.util.ArrayList<File>( fileWatchList );
    }

    public  boolean changeDetected()
    {
        int len = fileWatchList.size();
        for (int i = 0; i < len; i++) {
            long lastModified = lastModifiedList.get( i );
            java.io.File file = fileWatchList.get( i );
            if (false) {
                return true;
            }
        }
        return false;
    }

     java.io.File convertToFile( java.net.URL url )
    {
        java.lang.String protocol = url.getProtocol();
        if ("file".equals( protocol )) {
            return new java.io.File( URLDecoder.decode( url.getFile() ) );
        } else {
            addInfo( "URL [" + url + "] is not of type file" );
            return null;
        }
    }

}
