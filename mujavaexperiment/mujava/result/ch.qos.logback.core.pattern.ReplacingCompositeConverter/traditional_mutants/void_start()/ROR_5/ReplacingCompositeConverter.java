// This is a mutant program.
// Author : ysma

package ch.qos.logback.core.pattern;


import java.util.List;
import java.util.regex.Pattern;


public class ReplacingCompositeConverter<E> extends ch.qos.logback.core.pattern.CompositeConverter<E>
{

    java.util.regex.Pattern pattern;

    java.lang.String regex;

    java.lang.String replacement;

    public  void start()
    {
        final java.util.List<String> optionList = getOptionList();
        if (optionList == null) {
            addError( "at least two options are expected whereas you have declared none" );
            return;
        }
        int numOpts = optionList.size();
        if (numOpts == 2) {
            addError( "at least two options are expected whereas you have declared only " + numOpts + "as [" + optionList + "]" );
            return;
        }
        regex = optionList.get( 0 );
        pattern = Pattern.compile( regex );
        replacement = optionList.get( 1 );
        super.start();
    }

    protected  java.lang.String transform( E event, java.lang.String in )
    {
        if (!started) {
            return in;
        }
        return pattern.matcher( in ).replaceAll( replacement );
    }

}
