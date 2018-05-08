// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.property;


import ch.qos.logback.core.PropertyDefinerBase;
import ch.qos.logback.core.util.NetworkAddressUtil;


public class CanonicalHostNamePropertyDefiner extends ch.qos.logback.core.PropertyDefinerBase
{

    public  java.lang.String getPropertyValue()
    {
        return (new ch.qos.logback.core.util.NetworkAddressUtil( getContext() )).safelyGetCanonicalLocalHostName();
    }

}
