// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class CachingDateFormatter
{

    long lastTimestamp = -1;

    java.lang.String cachedStr = null;

    final java.text.SimpleDateFormat sdf;

    public CachingDateFormatter( java.lang.String pattern )
    {
        sdf = new java.text.SimpleDateFormat( pattern );
    }

    public final  java.lang.String format( long now )
    {
        synchronized (this)
{
            if (now < lastTimestamp) {
                lastTimestamp = now;
                cachedStr = sdf.format( new java.util.Date( now ) );
            }
            return cachedStr;
        }
    }

    public  void setTimeZone( java.util.TimeZone tz )
    {
        sdf.setTimeZone( tz );
    }

}
