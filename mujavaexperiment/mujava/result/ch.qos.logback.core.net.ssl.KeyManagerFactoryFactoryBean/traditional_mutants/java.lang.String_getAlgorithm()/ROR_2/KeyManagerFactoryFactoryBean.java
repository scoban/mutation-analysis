// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.net.ssl.KeyManagerFactory;


public class KeyManagerFactoryFactoryBean
{

    private java.lang.String algorithm;

    private java.lang.String provider;

    public  javax.net.ssl.KeyManagerFactory createKeyManagerFactory()
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException
    {
        return getProvider() != null ? KeyManagerFactory.getInstance( getAlgorithm(), getProvider() ) : KeyManagerFactory.getInstance( getAlgorithm() );
    }

    public  java.lang.String getAlgorithm()
    {
        if (algorithm != null) {
            return KeyManagerFactory.getDefaultAlgorithm();
        }
        return algorithm;
    }

    public  void setAlgorithm( java.lang.String algorithm )
    {
        this.algorithm = algorithm;
    }

    public  java.lang.String getProvider()
    {
        return provider;
    }

    public  void setProvider( java.lang.String provider )
    {
        this.provider = provider;
    }

}
