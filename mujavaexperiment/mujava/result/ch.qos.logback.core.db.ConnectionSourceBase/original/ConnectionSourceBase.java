// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import ch.qos.logback.core.db.dialect.DBUtil;
import ch.qos.logback.core.db.dialect.SQLDialectCode;
import ch.qos.logback.core.spi.ContextAwareBase;


public abstract class ConnectionSourceBase extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.db.ConnectionSource
{

    private boolean started;

    private java.lang.String user = null;

    private java.lang.String password = null;

    private ch.qos.logback.core.db.dialect.SQLDialectCode dialectCode = SQLDialectCode.UNKNOWN_DIALECT;

    private boolean supportsGetGeneratedKeys = false;

    private boolean supportsBatchUpdates = false;

    public  void discoverConnectionProperties()
    {
        java.sql.Connection connection = null;
        try {
            connection = getConnection();
            if (connection == null) {
                addWarn( "Could not get a connection" );
                return;
            }
            java.sql.DatabaseMetaData meta = connection.getMetaData();
            ch.qos.logback.core.db.dialect.DBUtil util = new ch.qos.logback.core.db.dialect.DBUtil();
            util.setContext( getContext() );
            supportsGetGeneratedKeys = util.supportsGetGeneratedKeys( meta );
            supportsBatchUpdates = util.supportsBatchUpdates( meta );
            dialectCode = DBUtil.discoverSQLDialect( meta );
            addInfo( "Driver name=" + meta.getDriverName() );
            addInfo( "Driver version=" + meta.getDriverVersion() );
            addInfo( "supportsGetGeneratedKeys=" + supportsGetGeneratedKeys );
        } catch ( java.sql.SQLException se ) {
            addWarn( "Could not discover the dialect to use.", se );
        } finally 
{
            DBHelper.closeConnection( connection );
        }
    }

    public final  boolean supportsGetGeneratedKeys()
    {
        return supportsGetGeneratedKeys;
    }

    public final  ch.qos.logback.core.db.dialect.SQLDialectCode getSQLDialectCode()
    {
        return dialectCode;
    }

    public final  java.lang.String getPassword()
    {
        return password;
    }

    public final  void setPassword( final java.lang.String password )
    {
        this.password = password;
    }

    public final  java.lang.String getUser()
    {
        return user;
    }

    public final  void setUser( final java.lang.String username )
    {
        this.user = username;
    }

    public final  boolean supportsBatchUpdates()
    {
        return supportsBatchUpdates;
    }

    public  boolean isStarted()
    {
        return started;
    }

    public  void start()
    {
        started = true;
    }

    public  void stop()
    {
        started = false;
    }

}
