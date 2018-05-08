// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import ch.qos.logback.core.joran.spi.DefaultNestedComponentRegistry;


public class SSLNestedComponentRegistryRules
{

    public static  void addDefaultNestedComponentRegistryRules( ch.qos.logback.core.joran.spi.DefaultNestedComponentRegistry registry )
    {
        registry.add( ch.qos.logback.core.net.ssl.SSLComponent.class, "ssl", ch.qos.logback.core.net.ssl.SSLConfiguration.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "parameters", ch.qos.logback.core.net.ssl.SSLParametersConfiguration.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "keyStore", ch.qos.logback.core.net.ssl.KeyStoreFactoryBean.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "trustStore", ch.qos.logback.core.net.ssl.KeyStoreFactoryBean.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "keyManagerFactory", ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "trustManagerFactory", ch.qos.logback.core.net.ssl.TrustManagerFactoryFactoryBean.class );
        registry.add( ch.qos.logback.core.net.ssl.SSLConfiguration.class, "secureRandom", ch.qos.logback.core.net.ssl.SecureRandomFactoryBean.class );
    }

}
