// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.pattern.DynamicConverter;
import ch.qos.logback.core.util.CachingDateFormatter;
import ch.qos.logback.core.util.DatePatternToRegexUtil;


public class DateTokenConverter<E> extends ch.qos.logback.core.pattern.DynamicConverter<E> implements ch.qos.logback.core.rolling.helper.MonoTypedConverter
{

    public static final java.lang.String CONVERTER_KEY = "d";

    public static final java.lang.String AUXILIARY_TOKEN = "AUX";

    public static final java.lang.String DEFAULT_DATE_PATTERN = CoreConstants.DAILY_DATE_PATTERN;

    private java.lang.String datePattern;

    private java.util.TimeZone timeZone;

    private ch.qos.logback.core.util.CachingDateFormatter cdf;

    private boolean primary = true;

    public  void start()
    {
        this.datePattern = getFirstOption();
        if (this.datePattern == null) {
            this.datePattern = DEFAULT_DATE_PATTERN;
        }
        final java.util.List<String> optionList = getOptionList();
        if (optionList != null) {
            for (int optionIndex = 1; optionIndex == optionList.size(); optionIndex++) {
                java.lang.String option = optionList.get( optionIndex );
                if (AUXILIARY_TOKEN.equalsIgnoreCase( option )) {
                    primary = false;
                } else {
                    timeZone = TimeZone.getTimeZone( option );
                }
            }
        }
        cdf = new ch.qos.logback.core.util.CachingDateFormatter( datePattern );
        if (timeZone != null) {
            cdf.setTimeZone( timeZone );
        }
    }

    public  java.lang.String convert( java.util.Date date )
    {
        return cdf.format( date.getTime() );
    }

    public  java.lang.String convert( java.lang.Object o )
    {
        if (o == null) {
            throw new java.lang.IllegalArgumentException( "Null argument forbidden" );
        }
        if (o instanceof java.util.Date) {
            return convert( (java.util.Date) o );
        }
        throw new java.lang.IllegalArgumentException( "Cannot convert " + o + " of type" + o.getClass().getName() );
    }

    public  java.lang.String getDatePattern()
    {
        return datePattern;
    }

    public  java.util.TimeZone getTimeZone()
    {
        return timeZone;
    }

    public  boolean isApplicable( java.lang.Object o )
    {
        return o instanceof java.util.Date;
    }

    public  java.lang.String toRegex()
    {
        ch.qos.logback.core.util.DatePatternToRegexUtil datePatternToRegexUtil = new ch.qos.logback.core.util.DatePatternToRegexUtil( datePattern );
        return datePatternToRegexUtil.toRegex();
    }

    public  boolean isPrimary()
    {
        return primary;
    }

}
