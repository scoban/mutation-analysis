// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


public class MySQLDialect implements ch.qos.logback.core.db.dialect.SQLDialect
{

    public static final java.lang.String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

    public  java.lang.String getSelectInsertId()
    {
        return SELECT_LAST_INSERT_ID;
    }

}
