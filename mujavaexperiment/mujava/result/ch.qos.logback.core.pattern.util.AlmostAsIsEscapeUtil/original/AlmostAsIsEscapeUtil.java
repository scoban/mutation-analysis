// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern.util;


import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.rolling.helper.FileNamePattern;


public class AlmostAsIsEscapeUtil extends ch.qos.logback.core.pattern.util.RestrictedEscapeUtil
{

    public  void escape( java.lang.String escapeChars, java.lang.StringBuffer buf, char next, int pointer )
    {
        super.escape( "" + CoreConstants.PERCENT_CHAR + CoreConstants.RIGHT_PARENTHESIS_CHAR, buf, next, pointer );
    }

}
