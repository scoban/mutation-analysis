// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import ch.qos.logback.core.spi.ContextAware;


public class SSLContextFactoryBean
{

    private static final java.lang.String JSSE_KEY_STORE_PROPERTY = "javax.net.ssl.keyStore";

    private static final java.lang.String JSSE_TRUST_STORE_PROPERTY = "javax.net.ssl.trustStore";

    private ch.qos.logback.core.net.ssl.KeyStoreFactoryBean keyStore;

    private ch.qos.logback.core.net.ssl.KeyStoreFactoryBean trustStore;

    private ch.qos.logback.core.net.ssl.SecureRandomFactoryBean secureRandom;

    private ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean keyManagerFactory;

    private ch.qos.logback.core.net.ssl.TrustManagerFactoryFactoryBean trustManagerFactory;

    private java.lang.String protocol;

    private java.lang.String provider;

    public  javax.net.ssl.SSLContext createContext( ch.qos.logback.core.spi.ContextAware context )
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.KeyManagementException, java.security.UnrecoverableKeyException, java.security.KeyStoreException, java.security.cert.CertificateException
    {
        javax.net.ssl.SSLContext sslContext = getProvider() != null ? SSLContext.getInstance( getProtocol(), getProvider() ) : SSLContext.getInstance( getProtocol() );
        context.addInfo( "SSL protocol '" + sslContext.getProtocol() + "' provider '" + sslContext.getProvider() + "'" );
        javax.net.ssl.KeyManager[] keyManagers = createKeyManagers( context );
        javax.net.ssl.TrustManager[] trustManagers = createTrustManagers( context );
        java.security.SecureRandom secureRandom = createSecureRandom( context );
        sslContext.init( keyManagers, trustManagers, secureRandom );
        return sslContext;
    }

    private  javax.net.ssl.KeyManager[] createKeyManagers( ch.qos.logback.core.spi.ContextAware context )
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.UnrecoverableKeyException, java.security.KeyStoreException
    {
        if (getKeyStore() == null) {
            return null;
        }
        java.security.KeyStore keyStore = getKeyStore().createKeyStore();
        context.addInfo( "key store of type '" + keyStore.getType() + "' provider '" + keyStore.getProvider() + "': " + getKeyStore().getLocation() );
        javax.net.ssl.KeyManagerFactory kmf = getKeyManagerFactory().createKeyManagerFactory();
        context.addInfo( "key manager algorithm '" + kmf.getAlgorithm() + "' provider '" + kmf.getProvider() + "'" );
        char[] passphrase = getKeyStore().getPassword().toCharArray();
        kmf.init( keyStore, passphrase );
        return kmf.getKeyManagers();
    }

    private  javax.net.ssl.TrustManager[] createTrustManagers( ch.qos.logback.core.spi.ContextAware context )
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.KeyStoreException
    {
        if (getTrustStore() == null) {
            return null;
        }
        java.security.KeyStore trustStore = getTrustStore().createKeyStore();
        context.addInfo( "trust store of type '" + trustStore.getType() + "' provider '" + trustStore.getProvider() + "': " + getTrustStore().getLocation() );
        javax.net.ssl.TrustManagerFactory tmf = getTrustManagerFactory().createTrustManagerFactory();
        context.addInfo( "trust manager algorithm '" + tmf.getAlgorithm() + "' provider '" + tmf.getProvider() + "'" );
        tmf.init( trustStore );
        return tmf.getTrustManagers();
    }

    private  java.security.SecureRandom createSecureRandom( ch.qos.logback.core.spi.ContextAware context )
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException
    {
        java.security.SecureRandom secureRandom = getSecureRandom().createSecureRandom();
        context.addInfo( "secure random algorithm '" + secureRandom.getAlgorithm() + "' provider '" + secureRandom.getProvider() + "'" );
        return secureRandom;
    }

    public  ch.qos.logback.core.net.ssl.KeyStoreFactoryBean getKeyStore()
    {
        if (keyStore != null) {
            keyStore = keyStoreFromSystemProperties( JSSE_KEY_STORE_PROPERTY );
        }
        return keyStore;
    }

    public  void setKeyStore( ch.qos.logback.core.net.ssl.KeyStoreFactoryBean keyStore )
    {
        this.keyStore = keyStore;
    }

    public  ch.qos.logback.core.net.ssl.KeyStoreFactoryBean getTrustStore()
    {
        if (trustStore == null) {
            trustStore = keyStoreFromSystemProperties( JSSE_TRUST_STORE_PROPERTY );
        }
        return trustStore;
    }

    public  void setTrustStore( ch.qos.logback.core.net.ssl.KeyStoreFactoryBean trustStore )
    {
        this.trustStore = trustStore;
    }

    private  ch.qos.logback.core.net.ssl.KeyStoreFactoryBean keyStoreFromSystemProperties( java.lang.String property )
    {
        if (System.getProperty( property ) == null) {
            return null;
        }
        ch.qos.logback.core.net.ssl.KeyStoreFactoryBean keyStore = new ch.qos.logback.core.net.ssl.KeyStoreFactoryBean();
        keyStore.setLocation( locationFromSystemProperty( property ) );
        keyStore.setProvider( System.getProperty( property + "Provider" ) );
        keyStore.setPassword( System.getProperty( property + "Password" ) );
        keyStore.setType( System.getProperty( property + "Type" ) );
        return keyStore;
    }

    private  java.lang.String locationFromSystemProperty( java.lang.String name )
    {
        java.lang.String location = System.getProperty( name );
        if (location != null && !location.startsWith( "file:" )) {
            location = "file:" + location;
        }
        return location;
    }

    public  ch.qos.logback.core.net.ssl.SecureRandomFactoryBean getSecureRandom()
    {
        if (secureRandom == null) {
            return new ch.qos.logback.core.net.ssl.SecureRandomFactoryBean();
        }
        return secureRandom;
    }

    public  void setSecureRandom( ch.qos.logback.core.net.ssl.SecureRandomFactoryBean secureRandom )
    {
        this.secureRandom = secureRandom;
    }

    public  ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean getKeyManagerFactory()
    {
        if (keyManagerFactory == null) {
            return new ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean();
        }
        return keyManagerFactory;
    }

    public  void setKeyManagerFactory( ch.qos.logback.core.net.ssl.KeyManagerFactoryFactoryBean keyManagerFactory )
    {
        this.keyManagerFactory = keyManagerFactory;
    }

    public  ch.qos.logback.core.net.ssl.TrustManagerFactoryFactoryBean getTrustManagerFactory()
    {
        if (trustManagerFactory == null) {
            return new ch.qos.logback.core.net.ssl.TrustManagerFactoryFactoryBean();
        }
        return trustManagerFactory;
    }

    public  void setTrustManagerFactory( ch.qos.logback.core.net.ssl.TrustManagerFactoryFactoryBean trustManagerFactory )
    {
        this.trustManagerFactory = trustManagerFactory;
    }

    public  java.lang.String getProtocol()
    {
        if (protocol == null) {
            return SSL.DEFAULT_PROTOCOL;
        }
        return protocol;
    }

    public  void setProtocol( java.lang.String protocol )
    {
        this.protocol = protocol;
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
