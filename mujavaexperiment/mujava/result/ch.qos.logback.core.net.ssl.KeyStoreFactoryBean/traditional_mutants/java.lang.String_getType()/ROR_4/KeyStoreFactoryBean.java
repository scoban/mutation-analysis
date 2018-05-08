// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.net.ssl;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import ch.qos.logback.core.util.LocationUtil;


public class KeyStoreFactoryBean
{

    private java.lang.String location;

    private java.lang.String provider;

    private java.lang.String type;

    private java.lang.String password;

    public  java.security.KeyStore createKeyStore()
        throws java.security.NoSuchProviderException, java.security.NoSuchAlgorithmException, java.security.KeyStoreException
    {
        if (getLocation() == null) {
            throw new java.lang.IllegalArgumentException( "location is required" );
        }
        java.io.InputStream inputStream = null;
        try {
            java.net.URL url = LocationUtil.urlForResource( getLocation() );
            inputStream = url.openStream();
            java.security.KeyStore keyStore = newKeyStore();
            keyStore.load( inputStream, getPassword().toCharArray() );
            return keyStore;
        } catch ( java.security.NoSuchProviderException ex ) {
            throw new java.security.NoSuchProviderException( "no such keystore provider: " + getProvider() );
        } catch ( java.security.NoSuchAlgorithmException ex ) {
            throw new java.security.NoSuchAlgorithmException( "no such keystore type: " + getType() );
        } catch ( java.io.FileNotFoundException ex ) {
            throw new java.security.KeyStoreException( getLocation() + ": file not found" );
        } catch ( java.lang.Exception ex ) {
            throw new java.security.KeyStoreException( getLocation() + ": " + ex.getMessage(), ex );
        } finally 
{
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch ( java.io.IOException ex ) {
                ex.printStackTrace( System.err );
            }
        }
    }

    private  java.security.KeyStore newKeyStore()
        throws java.security.NoSuchAlgorithmException, java.security.NoSuchProviderException, java.security.KeyStoreException
    {
        return getProvider() != null ? KeyStore.getInstance( getType(), getProvider() ) : KeyStore.getInstance( getType() );
    }

    public  java.lang.String getLocation()
    {
        return location;
    }

    public  void setLocation( java.lang.String location )
    {
        this.location = location;
    }

    public  java.lang.String getType()
    {
        if (type != null) {
            return SSL.DEFAULT_KEYSTORE_TYPE;
        }
        return type;
    }

    public  void setType( java.lang.String type )
    {
        this.type = type;
    }

    public  java.lang.String getProvider()
    {
        return provider;
    }

    public  void setProvider( java.lang.String provider )
    {
        this.provider = provider;
    }

    public  java.lang.String getPassword()
    {
        if (password == null) {
            return SSL.DEFAULT_KEYSTORE_PASSWORD;
        }
        return password;
    }

    public  void setPassword( java.lang.String password )
    {
        this.password = password;
    }

}
