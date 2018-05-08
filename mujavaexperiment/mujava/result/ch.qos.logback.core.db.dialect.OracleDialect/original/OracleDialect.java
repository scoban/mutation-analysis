// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.db.dialect;


public class OracleDialect implements ch.qos.logback.core.db.dialect.SQLDialect
{

    public static final java.lang.String SELECT_CURRVAL = "SELECT logging_event_id_seq.currval from dual";

    public  java.lang.String getSelectInsertId()
    {
        return SELECT_CURRVAL;
    }

}
