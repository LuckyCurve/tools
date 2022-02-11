package cn.luckycurve.collections.set;

import com.google.common.collect.HashMultiset;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Multiset contains occurrence of the elements than default Set
 * <p>
 * total store a Map in MultiSet
 * <p>
 * if we want to guarantee safe in concurrency environment, we should
 * using class ConcurrentHashMultiset, but this class only guarantee methods like
 * add or remove, but can't not guarantee method setCount. it not predicate
 * <p>
 * deals this method is using expected value in setCount
 * public boolean setCount(E element, int expectedOldCount, int newCount)
 * return false when we modify value fail
 * <p>
 * A multi-threaded programmer cloud perform a retry if changing the count failed
 *
 * @author LuckyCurve
 */
public class MultisetTest {

    @Test
    public void usingMultiSet() {
        final HashMultiset<String> multiset = HashMultiset.create();

        multiset.add("Potter");
        multiset.add("Potter");
        multiset.add("Potter");

        Assertions.assertThat(multiset.contains("Potter")).isTrue();
        Assertions.assertThat(multiset.count("Potter")).isEqualTo(3);

        // batch add
        multiset.setCount("Alias", 100);
        Assertions.assertThat(multiset.contains("Alias")).isTrue();
        Assertions.assertThat(multiset.count("Alias")).isEqualTo(100);
    }


}
