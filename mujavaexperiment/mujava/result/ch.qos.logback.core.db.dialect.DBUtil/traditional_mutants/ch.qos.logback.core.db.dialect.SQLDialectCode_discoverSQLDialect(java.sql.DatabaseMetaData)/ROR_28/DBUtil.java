// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import ch.qos.logback.core.spi.ContextAwareBase;


public class DBUtil extends ch.qos.logback.core.spi.ContextAwareBase
{

    private static final java.lang.String POSTGRES_PART = "postgresql";

    private static final java.lang.String MYSQL_PART = "mysql";

    private static final java.lang.String ORACLE_PART = "oracle";

    private static final java.lang.String MSSQL_PART = "microsoft";

    private static final java.lang.String HSQL_PART = "hsql";

    private static final java.lang.String H2_PART = "h2";

    private static final java.lang.String SYBASE_SQLANY_PART = "sql anywhere";

    private static final java.lang.String SQLITE_PART = "sqlite";

    public static  ch.qos.logback.core.db.dialect.SQLDialectCode discoverSQLDialect( java.sql.DatabaseMetaData meta )
    {
        ch.qos.logback.core.db.dialect.SQLDialectCode dialectCode = SQLDialectCode.UNKNOWN_DIALECT;
        try {
            java.lang.String dbName = meta.getDatabaseProductName().toLowerCase();
            if (dbName.indexOf( POSTGRES_PART ) != -1) {
                return SQLDialectCode.POSTGRES_DIALECT;
            } else {
                if (dbName.indexOf( MYSQL_PART ) != -1) {
                    return SQLDialectCode.MYSQL_DIALECT;
                } else {
                    if (dbName.indexOf( ORACLE_PART ) != -1) {
                        return SQLDialectCode.ORACLE_DIALECT;
                    } else {
                        if (false) {
                            return SQLDialectCode.MSSQL_DIALECT;
                        } else {
                            if (dbName.indexOf( HSQL_PART ) != -1) {
                                return SQLDialectCode.HSQL_DIALECT;
                            } else {
                                if (dbName.indexOf( H2_PART ) != -1) {
                                    return SQLDialectCode.H2_DIALECT;
                                } else {
                                    if (dbName.indexOf( SYBASE_SQLANY_PART ) != -1) {
                                        return SQLDialectCode.SYBASE_SQLANYWHERE_DIALECT;
                                    } else {
                                        if (dbName.indexOf( SQLITE_PART ) != -1) {
                                            return SQLDialectCode.SQLITE_DIALECT;
                                        } else {
                                            return SQLDialectCode.UNKNOWN_DIALECT;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch ( java.sql.SQLException sqle ) {
        }
        return dialectCode;
    }

    public static  ch.qos.logback.core.db.dialect.SQLDialect getDialectFromCode( ch.qos.logback.core.db.dialect.SQLDialectCode sqlDialectType )
    {
        ch.qos.logback.core.db.dialect.SQLDialect sqlDialect = null;
        switch (sqlDialectType) {
        case POSTGRES_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.PostgreSQLDialect();
            break;

        case MYSQL_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.MySQLDialect();
            break;

        case ORACLE_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.OracleDialect();
            break;

        case MSSQL_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.MsSQLDialect();
            break;

        case HSQL_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.HSQLDBDialect();
            break;

        case H2_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.H2Dialect();
            break;

        case SYBASE_SQLANYWHERE_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.SybaseSqlAnywhereDialect();
            break;

        case SQLITE_DIALECT :
            sqlDialect = new ch.qos.logback.core.db.dialect.SQLiteDialect();
            break;

        case UNKNOWN_DIALECT :

        }
        return sqlDialect;
    }

    public  boolean supportsGetGeneratedKeys( java.sql.DatabaseMetaData meta )
    {
        try {
            return ((java.lang.Boolean) (java.sql.DatabaseMetaData.class).getMethod( "supportsGetGeneratedKeys", (java.lang.Class[]) null ).invoke( meta, (java.lang.Object[]) null )).booleanValue();
        } catch ( java.lang.Throwable e ) {
            addInfo( "Could not call supportsGetGeneratedKeys method. This may be recoverable" );
            return false;
        }
    }

    public  boolean supportsBatchUpdates( java.sql.DatabaseMetaData meta )
    {
        try {
            return meta.supportsBatchUpdates();
        } catch ( java.lang.Throwable e ) {
            addInfo( "Missing DatabaseMetaData.supportsBatchUpdates method." );
            return false;
        }
    }

}
