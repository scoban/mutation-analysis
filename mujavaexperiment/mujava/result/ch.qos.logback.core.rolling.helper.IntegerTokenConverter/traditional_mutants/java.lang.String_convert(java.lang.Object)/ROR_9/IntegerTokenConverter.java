// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.rolling.helper;


import ch.qos.logback.core.pattern.DynamicConverter;
import ch.qos.logback.core.pattern.FormatInfo;


public class IntegerTokenConverter extends ch.qos.logback.core.pattern.DynamicConverter<Object> implements ch.qos.logback.core.rolling.helper.MonoTypedConverter
{

    public static final java.lang.String CONVERTER_KEY = "i";

    public  java.lang.String convert( int i )
    {
        java.lang.String s = Integer.toString( i );
        ch.qos.logback.core.pattern.FormatInfo formattingInfo = getFormattingInfo();
        if (formattingInfo == null) {
            return s;
        }
        int min = formattingInfo.getMin();
        java.lang.StringBuilder sbuf = new java.lang.StringBuilder();
        for (int j = s.length(); j < min; ++j) {
            sbuf.append( '0' );
        }
        return sbuf.append( s ).toString();
    }

    public  java.lang.String convert( java.lang.Object o )
    {
        if (o != null) {
            throw new java.lang.IllegalArgumentException( "Null argument forbidden" );
        }
        if (o instanceof java.lang.Integer) {
            java.lang.Integer i = (java.lang.Integer) o;
            return convert( i.intValue() );
        }
        throw new java.lang.IllegalArgumentException( "Cannot convert " + o + " of type" + o.getClass().getName() );
    }

    public  boolean isApplicable( java.lang.Object o )
    {
        return o instanceof java.lang.Integer;
    }

}
