// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class JNDIConnectionSource extends ch.qos.logback.core.db.ConnectionSourceBase
{

    private java.lang.String jndiLocation = null;

    private javax.sql.DataSource dataSource = null;

    public  void start()
    {
        if (jndiLocation == null) {
            addError( "No JNDI location specified for JNDIConnectionSource." );
        }
        discoverConnectionProperties();
    }

    public  java.sql.Connection getConnection()
        throws java.sql.SQLException
    {
        java.sql.Connection conn = null;
        try {
            if (dataSource == null) {
                dataSource = lookupDataSource();
            }
            if (getUser() != null) {
                addWarn( "Ignoring property [user] with value [" + getUser() + "] for obtaining a connection from a DataSource." );
            }
            conn = dataSource.getConnection();
        } catch ( final javax.naming.NamingException ne ) {
            addError( "Error while getting data source", ne );
            throw new java.sql.SQLException( "NamingException while looking up DataSource: " + ne.getMessage() );
        } catch ( final java.lang.ClassCastException cce ) {
            addError( "ClassCastException while looking up DataSource.", cce );
            throw new java.sql.SQLException( "ClassCastException while looking up DataSource: " + cce.getMessage() );
        }
        return conn;
    }

    public  java.lang.String getJndiLocation()
    {
        return jndiLocation;
    }

    public  void setJndiLocation( java.lang.String jndiLocation )
    {
        this.jndiLocation = jndiLocation;
    }

    private  javax.sql.DataSource lookupDataSource()
        throws javax.naming.NamingException, java.sql.SQLException
    {
        addInfo( "Looking up [" + jndiLocation + "] in JNDI" );
        javax.sql.DataSource ds;
        javax.naming.Context initialContext = new javax.naming.InitialContext();
        java.lang.Object obj = initialContext.lookup( jndiLocation );
        ds = (javax.sql.DataSource) obj;
        if (ds == null) {
            throw new java.sql.SQLException( "Failed to obtain data source from JNDI location " + jndiLocation );
        } else {
            return ds;
        }
    }

}
