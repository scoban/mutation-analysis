// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.joran.spi;


public class HostClassAndPropertyDouble
{

    final java.lang.Class<?> hostClass;

    final java.lang.String propertyName;

    public HostClassAndPropertyDouble( java.lang.Class<?> hostClass, java.lang.String propertyName )
    {
        this.hostClass = hostClass;
        this.propertyName = propertyName;
    }

    public  java.lang.Class<?> getHostClass()
    {
        return hostClass;
    }

    public  java.lang.String getPropertyName()
    {
        return propertyName;
    }

    public  int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (hostClass == null ? 0 : hostClass.hashCode());
        result = prime * result + (propertyName == null ? 0 : propertyName.hashCode());
        return result;
    }

    public  boolean equals( java.lang.Object obj )
    {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble other = (ch.qos.logback.core.joran.spi.HostClassAndPropertyDouble) obj;
        if (hostClass == null) {
            if (other.hostClass != null) {
                return false;
            }
        } else {
            if (!hostClass.equals( other.hostClass )) {
                return false;
            }
        }
        if (propertyName == null) {
            if (other.propertyName != null) {
                return false;
            }
        } else {
            if (!propertyName.equals( other.propertyName )) {
                return false;
            }
        }
        return true;
    }

}
