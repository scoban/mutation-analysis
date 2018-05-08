// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


public class SystemInfo
{

    public static  java.lang.String getJavaVendor()
    {
        return OptionHelper.getSystemProperty( "java.vendor", null );
    }

}
