// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.boolex;


import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;


public class Matcher extends ch.qos.logback.core.spi.ContextAwareBase implements ch.qos.logback.core.spi.LifeCycle
{

    private java.lang.String regex;

    private java.lang.String name;

    private boolean caseSensitive = true;

    private boolean canonEq = false;

    private boolean unicodeCase = false;

    private boolean start = false;

    private java.util.regex.Pattern pattern;

    public  java.lang.String getRegex()
    {
        return regex;
    }

    public  void setRegex( java.lang.String regex )
    {
        this.regex = regex;
    }

    public  void start()
    {
        if (name == null) {
            addError( "All Matcher objects must be named" );
            return;
        }
        try {
            int code = 0;
            if (!caseSensitive) {
                code |= Pattern.CASE_INSENSITIVE;
            }
            if (canonEq) {
                code |= Pattern.CANON_EQ;
            }
            if (unicodeCase) {
                code |= Pattern.UNICODE_CASE;
            }
            pattern = Pattern.compile( regex, code );
            start = true;
        } catch ( java.util.regex.PatternSyntaxException pse ) {
            addError( "Failed to compile regex [" + regex + "]", pse );
        }
    }

    public  void stop()
    {
        start = false;
    }

    public  boolean isStarted()
    {
        return start;
    }

    public  boolean matches( java.lang.String input )
        throws ch.qos.logback.core.boolex.EvaluationException
    {
        if (start) {
            java.util.regex.Matcher matcher = pattern.matcher( input );
            return matcher.find();
        } else {
            throw new ch.qos.logback.core.boolex.EvaluationException( "Matcher [" + regex + "] not started" );
        }
    }

    public  boolean isCanonEq()
    {
        return canonEq;
    }

    public  void setCanonEq( boolean canonEq )
    {
        this.canonEq = canonEq;
    }

    public  boolean isCaseSensitive()
    {
        return caseSensitive;
    }

    public  void setCaseSensitive( boolean caseSensitive )
    {
        this.caseSensitive = caseSensitive;
    }

    public  boolean isUnicodeCase()
    {
        return unicodeCase;
    }

    public  void setUnicodeCase( boolean unicodeCase )
    {
        this.unicodeCase = unicodeCase;
    }

    public  java.lang.String getName()
    {
        return name;
    }

    public  void setName( java.lang.String name )
    {
        this.name = name;
    }

}
