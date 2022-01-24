package cn.luckycurve.collections.set;

import com.google.common.base.Joiner;
import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LuckyCurve
 */
public class SetTest {

    @Test
    public void unionSetOperationTest() {
        final ImmutableSet<Character> setA = ImmutableSet.of('a', 'b');
        final ImmutableSet<Character> setB = ImmutableSet.of('a', 'c');

        final Sets.SetView<Character> res = Sets.union(setA, setB);

        assertEquals(res.size(), 3);
    }


    /**
     * cartesian product set
     * <p>
     * 笛卡尔积
     */
    @Test
    public void cartesianProductOfSet() {
        final ImmutableSet<Character> setA = ImmutableSet.of('a', 'c');
        final ImmutableSet<Character> setB = ImmutableSet.of('b', 'd');

        final Set<List<Character>> res = Sets.cartesianProduct(setA, setB);

        final String s = Joiner.on("\n")
                .join(res.stream().map(characters -> Joiner.on(" ").join(characters)).collect(Collectors.toList()));

        assertThat(s).contains(Lists.newArrayList("a b", "a d", "c b", "c d"));
    }


    @Test
    public void setIntersection() {
        final HashSet<Character> setA = Sets.newHashSet(Lists.charactersOf("abc"));
        final HashSet<Character> setB = Sets.newHashSet(Lists.charactersOf("bcd"));

        final Sets.SetView<Character> res = Sets.intersection(setA, setB);

        assertThat(res).containsAll(Lists.charactersOf("bc"));
    }

    /**
     * two set symmetric difference means the element not in the intersection of setA and setB
     * two set difference means the element contains in SetA not contains in SetB
     * <p>
     * setA {1, 2}  setB {2, 3}
     * <p>
     * symmetric difference: {1. 3}
     * difference: {1}
     */
    @Test
    public void symmetricDifferenceOfSet() {
        final HashSet<Integer> setA = Sets.newHashSet(1, 2);
        final HashSet<Integer> setB = Sets.newHashSet(3, 2);

        assertThat(Sets.difference(setA, setB)).containsAll(Sets.newHashSet(1));
        assertThat(Sets.symmetricDifference(setA, setB)).containsAll(Sets.newHashSet(1, 3));
    }


    /**
     * get all subsets of that set
     */
    @Test
    public void powerSet() {
        final Set<Set<Character>> res = Sets.powerSet(Sets.newHashSet('a', 'b'));

        assertThat(res)
                .contains(Sets.newHashSet())
                .contains(Sets.newHashSet('a'))
                .contains(Sets.newHashSet('b'))
                .contains(Sets.newHashSet('a', 'b'));
    }


    @Test
    public void getContiguousSet() {
        final ContiguousSet<Integer> set = ContiguousSet.create(Range.closed(1, 5), DiscreteDomain.integers());

        assertEquals(set.size(), 5);
        assertThat(set).startsWith(1).endsWith(5);
    }
}
