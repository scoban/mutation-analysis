// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import javax.net.ssl.SSLContext;


public class SSLConfiguration extends ch.qos.logback.core.net.ssl.SSLContextFactoryBean
{

    private ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters;

    public  ch.qos.logback.core.net.ssl.SSLParametersConfiguration getParameters()
    {
        if (parameters == null) {
            parameters = new ch.qos.logback.core.net.ssl.SSLParametersConfiguration();
        }
        return parameters;
    }

    public  void setParameters( ch.qos.logback.core.net.ssl.SSLParametersConfiguration parameters )
    {
        this.parameters = parameters;
    }

}
