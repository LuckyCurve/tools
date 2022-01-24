package cn.luckycurve.basic.joinandsplitcollections;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * convert collections into a string with the joiner
 * split a string into a collection with splitter
 *
 * @author LuckyCurve
 */
public class JoinerAndSplitterTest {

    @Test
    public void convertListIntoString() {
        final ArrayList<String> list = Lists.newArrayList("Tina", "Seth", "LuckyCurve");

        assertEquals(Joiner.on(',').join(list), "Tina,Seth,LuckyCurve");
    }

    @Test
    public void convertMapIntoString() {
        final HashMap<String, String> map = Maps.newHashMap();
        map.put("name", "LuckyCurve");
        map.put("age", "22");

        final String result = Joiner.on('\n').withKeyValueSeparator(":").join(map);

        System.out.println(result);

        assertTrue(result.contains("name:LuckyCurve"));
        assertTrue(result.contains("age:22"));
    }

    /**
     * actually just convert inner List first and then process outer List
     */
    @Test
    public void joinNestedCollection() {
        List<ArrayList<String>> list = Lists.newArrayList(
                Lists.newArrayList("apple", "banana", "orange"),
                Lists.newArrayList("cat", "dog", "bird"),
                Lists.newArrayList("John", "Jane", "Adam"));

        final String res = Joiner.on("\n").join(list.stream().map(inner -> Joiner.on('-').join(inner)).collect(Collectors.toSet()));

        System.out.println(res);
    }


    @Test
    public void joinNestedCollectionComplex() {
        final HashMap<Object, Object> map = Maps.newHashMap();
        map.put("name", Lists.newArrayList("LuckyCurve", "Tina"));
        map.put("age", Lists.newArrayList(1, 2, 3));

        final String res = Joiner.on("\n")
                .join(map.entrySet().stream().map(entry -> entry.getKey() + ":" + Joiner.on(",").join((List) entry.getValue())).collect(Collectors.toSet()));

        System.out.println(res);
    }


    @Test
    public void joinHandleNullValueInCollection() {
        final ArrayList<String> list = Lists.newArrayList("LuckyCurve", null, "helloWorld");

        System.out.println(Joiner.on(",").skipNulls().join(list));

        System.out.println(Joiner.on(",").useForNull("nameless").join(list));
    }

    /**
     * ===============================================================================================
     * Splitter usage
     */
    @Test
    public void createListFromString() {
        String input = " orange - apple - banana ";
        List<String> res = Splitter.on("-").trimResults().splitToList(input);

        assertThat(res).contains("apple", "orange");
    }


    @Test
    public void createMapFromString() {
        String input = "a=1; b=2";
        final Map<String, String> map =
                Splitter.on(";").trimResults().withKeyValueSeparator("=").split(input);

        assertEquals("1", map.get("a"));
        assertEquals("2", map.get("b"));
    }

    @Test
    public void createListFromStringWithMultipleSeparators(){
        String input = "apple.banana,,orange";

        final List<String> list = Splitter.onPattern("[.,]")
                .omitEmptyStrings()
                .splitToList(input);

        assertThat(list).contains("apple", "banana", "orange");
    }

    @Test
    public void splitStringAtSpecificLength() {
        String input = "helloWorld";

        final List<String> list = Splitter.fixedLength(3).splitToList(input);

        assertThat(list).contains("hel", "loW", "orl", "d");
    }

    @Test
    public void splitStringLimitSize() {
        String input = "helloWorld";

        final List<String> list = Splitter.fixedLength(3).limit(2).splitToList(input);

        assertEquals(list.size(), 2);
        assertThat(list).contains("hel", "loWorld");
    }


}
