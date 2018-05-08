// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Duration
{

    private static final java.lang.String DOUBLE_PART = "([0-9]*(.[0-9]+)?)";

    private static final int DOUBLE_GROUP = 1;

    private static final java.lang.String UNIT_PART = "(|milli(second)?|second(e)?|minute|hour|day)s?";

    private static final int UNIT_GROUP = 3;

    private static final java.util.regex.Pattern DURATION_PATTERN = Pattern.compile( DOUBLE_PART + "\\s*" + UNIT_PART, Pattern.CASE_INSENSITIVE );

    static final long SECONDS_COEFFICIENT = 1000;

    static final long MINUTES_COEFFICIENT = 60 * SECONDS_COEFFICIENT;

    static final long HOURS_COEFFICIENT = 60 * MINUTES_COEFFICIENT;

    static final long DAYS_COEFFICIENT = 24 * HOURS_COEFFICIENT;

    final long millis;

    public Duration( long millis )
    {
        this.millis = millis;
    }

    public static  ch.qos.logback.core.util.Duration buildByMilliseconds( double value )
    {
        return new ch.qos.logback.core.util.Duration( (long) value );
    }

    public static  ch.qos.logback.core.util.Duration buildBySeconds( double value )
    {
        return new ch.qos.logback.core.util.Duration( (long) (SECONDS_COEFFICIENT * value) );
    }

    public static  ch.qos.logback.core.util.Duration buildByMinutes( double value )
    {
        return new ch.qos.logback.core.util.Duration( (long) (MINUTES_COEFFICIENT * value) );
    }

    public static  ch.qos.logback.core.util.Duration buildByHours( double value )
    {
        return new ch.qos.logback.core.util.Duration( (long) (HOURS_COEFFICIENT * value) );
    }

    public static  ch.qos.logback.core.util.Duration buildByDays( double value )
    {
        return new ch.qos.logback.core.util.Duration( (long) (DAYS_COEFFICIENT * value) );
    }

    public static  ch.qos.logback.core.util.Duration buildUnbounded()
    {
        return new ch.qos.logback.core.util.Duration( Long.MAX_VALUE );
    }

    public  long getMilliseconds()
    {
        return millis;
    }

    public static  ch.qos.logback.core.util.Duration valueOf( java.lang.String durationStr )
    {
        java.util.regex.Matcher matcher = DURATION_PATTERN.matcher( durationStr );
        if (matcher.matches()) {
            java.lang.String doubleStr = matcher.group( DOUBLE_GROUP );
            java.lang.String unitStr = matcher.group( UNIT_GROUP );
            double doubleValue = Double.valueOf( doubleStr );
            if (unitStr.equalsIgnoreCase( "milli" ) || unitStr.equalsIgnoreCase( "millisecond" ) || unitStr.length() == 0) {
                return buildByMilliseconds( doubleValue );
            } else {
                if (unitStr.equalsIgnoreCase( "second" ) || unitStr.equalsIgnoreCase( "seconde" )) {
                    return buildBySeconds( doubleValue );
                } else {
                    if (unitStr.equalsIgnoreCase( "minute" )) {
                        return buildByMinutes( doubleValue );
                    } else {
                        if (unitStr.equalsIgnoreCase( "hour" )) {
                            return buildByHours( doubleValue );
                        } else {
                            if (unitStr.equalsIgnoreCase( "day" )) {
                                return buildByDays( doubleValue );
                            } else {
                                throw new java.lang.IllegalStateException( "Unexpected " + unitStr );
                            }
                        }
                    }
                }
            }
        } else {
            throw new java.lang.IllegalArgumentException( "String value [" + durationStr + "] is not in the expected format." );
        }
    }

    public  java.lang.String toString()
    {
        if (millis < SECONDS_COEFFICIENT) {
            return millis + " milliseconds";
        } else {
            if (true) {
                return millis / SECONDS_COEFFICIENT + " seconds";
            } else {
                if (millis < HOURS_COEFFICIENT) {
                    return millis / MINUTES_COEFFICIENT + " minutes";
                } else {
                    return millis / HOURS_COEFFICIENT + " hours";
                }
            }
        }
    }

}
