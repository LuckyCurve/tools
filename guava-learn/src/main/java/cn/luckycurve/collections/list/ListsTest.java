package cn.luckycurve.collections.list;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * working with List
 * <p>
 * All Lists methods just return a new Object not modify in current Object
 *
 * @author LuckyCurve
 */
public class ListsTest {

    /**
     * method reverse create a new list to store reversed value
     * not in current list
     */
    @Test
    public void reverseList() {
        List<String> list = Lists.newArrayList("jelly", "Tina");

        final List<String> reverse = Lists.reverse(list);

        System.out.println(list);
        System.out.println(reverse);
    }

    @Test
    public void generateCharacterListFromString() {
        List<Character> list = Lists.charactersOf("JOHN");

        assertThat(list).contains('J', 'H', 'O', 'N');
    }

    @Test
    public void partitionList() {
        List<String> list = Lists.newArrayList("jelly", "Tina", "Sarah");

        final List<List<String>> partition = Lists.partition(list, 2);

        assertThat(partition.size()).isEqualTo(2);
        assertThat(partition.get(0).size()).isEqualTo(2);
        assertThat(partition.get(1).size()).isEqualTo(1);
    }

    /**
     * use set feature to complete
     */
    @Test
    public void removeDuplicate() {
        String input = "hello";
        final List<Character> list = Lists.charactersOf(input);

        final ImmutableList<Character> res = ImmutableSet.copyOf(list).asList();

        assertThat(res.size()).isEqualTo(4);
    }


    /**
     * warning: change in current object
     */
    @Test
    public void remoteNullValue() {
        List<String> list = Lists.newArrayList("jelly", "Tina", null);

        Iterables.removeIf(list, Predicates.isNull());

        assertThat(list.size()).isEqualTo(2);

    }

    @Test
    public void convertListToImmutable() {
        List<String> list = Lists.newArrayList("jelly", "Tina", null);

        final ImmutableList<String> immutableList = ImmutableList.copyOf(list);

        assertEquals(list, immutableList);
    }


}
