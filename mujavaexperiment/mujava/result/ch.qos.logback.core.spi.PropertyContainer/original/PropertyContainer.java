// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.spi;


import java.util.Map;


public interface PropertyContainer
{

     java.lang.String getProperty( java.lang.String key );

     java.util.Map<String,String> getCopyOfPropertyMap();

}
