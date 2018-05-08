// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;


public class PatternLayoutEncoderBase<E> extends ch.qos.logback.core.encoder.LayoutWrappingEncoder<E>
{

    java.lang.String pattern;

    protected boolean outputPatternAsHeader = false;

    public  java.lang.String getPattern()
    {
        return pattern;
    }

    public  void setPattern( java.lang.String pattern )
    {
        this.pattern = pattern;
    }

    public  boolean isOutputPatternAsHeader()
    {
        return outputPatternAsHeader;
    }

    public  void setOutputPatternAsHeader( boolean outputPatternAsHeader )
    {
        this.outputPatternAsHeader = outputPatternAsHeader;
    }

    public  boolean isOutputPatternAsPresentationHeader()
    {
        return outputPatternAsHeader;
    }

    public  void setOutputPatternAsPresentationHeader( boolean outputPatternAsHeader )
    {
        addWarn( "[outputPatternAsPresentationHeader] property is deprecated. Please use [outputPatternAsHeader] option instead." );
        this.outputPatternAsHeader = outputPatternAsHeader;
    }

    public  void setLayout( ch.qos.logback.core.Layout<E> layout )
    {
        throw new java.lang.UnsupportedOperationException( "one cannot set the layout of " + this.getClass().getName() );
    }

}
