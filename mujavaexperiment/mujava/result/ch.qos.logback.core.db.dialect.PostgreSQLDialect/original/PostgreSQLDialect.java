// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


public class PostgreSQLDialect implements ch.qos.logback.core.db.dialect.SQLDialect
{

    public static final java.lang.String SELECT_CURRVAL = "SELECT currval('logging_event_id_seq')";

    public  java.lang.String getSelectInsertId()
    {
        return SELECT_CURRVAL;
    }

}
