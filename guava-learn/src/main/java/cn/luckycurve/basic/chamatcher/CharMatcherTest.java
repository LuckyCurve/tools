package cn.luckycurve.basic.chamatcher;

import com.google.common.base.CharMatcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Guava Char Matcher
 * its just <b>Char</b> Matcher not <b>String</b> Matcher
 *
 * @author LuckyCurve
 */
public class CharMatcherTest {

    /**
     * using method javaLetterOrDigit but it's deprecated, recommend use method in range
     */
    @Test
    public void removeSpecialCharacterFromString() {
        String input = "he##ll%%o^!2@";
        //final CharMatcher matcher = CharMatcher.javaLetterOrDigit();
        final CharMatcher matcher = CharMatcher.inRange('0', '9')
                .or(CharMatcher.inRange('a', 'z'));

        assertEquals(matcher.retainFrom(input), "hello2");
    }

    @Test
    public void removeNotASCIICharacterFromString() {
        String input = "世界hello我的2";
        final CharMatcher matcher = CharMatcher.ascii();
        assertEquals(matcher.retainFrom(input), "hello2");
    }

    /**
     * in CharMatcher view when we use CharMatcher
     */
    @Test
    public void validateString() {
        String input = "hello";

        CharMatcher matcher = CharMatcher.inRange('a', 'z');
        assertTrue(matcher.matchesAllOf(input));

        matcher = CharMatcher.inRange('a', 'e');
        assertTrue(matcher.matchesAnyOf(input));

        matcher = CharMatcher.inRange('1', '9');
        assertTrue(matcher.matchesNoneOf(input));
    }

    @Test
    public void trimString() {
        String input = "---hello...";

        CharMatcher matcher = CharMatcher.is('-');
        assertEquals("hello...", matcher.trimLeadingFrom(input));

        matcher = CharMatcher.is('.');
        assertEquals("---hello", matcher.trimTrailingFrom(input));

        matcher = CharMatcher.anyOf("-.");
        assertEquals("hello", matcher.trimFrom(input));
    }

    /**
     * replace consecutive string as a char
     */
    @Test
    public void collapseString() {
        String input = "    hell     o    ";

        final CharMatcher matcher = CharMatcher.is(' ');
        assertEquals("-hell-o-", matcher.collapseFrom(input, '-'));

        assertEquals("hell-o", matcher.trimAndCollapseFrom(input, '-'));
    }

    @Test
    public void replaceFromString() {
        String input = "hel!lo:";
        final CharMatcher matcher = CharMatcher.anyOf("!:");

        assertEquals("hello", matcher.replaceFrom(input, ""));
    }

    @Test
    public void countCharacterOccurrences() {
        String input = "a b c d  sadawdsad";

        assertEquals(13, CharMatcher.inRange('a', 'z').countIn(input));
    }
}
