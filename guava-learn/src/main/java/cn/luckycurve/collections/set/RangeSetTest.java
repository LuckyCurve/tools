package cn.luckycurve.collections.set;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
public class RangeSetTest {

    /**
     * operate same with RangeSet
     * RangeSet is best Range operator
     */
    @Test
    public void test() {
        final TreeRangeSet<Comparable<?>> set = TreeRangeSet.create();

        set.add(Range.closed(1, 2));
        set.add(Range.closed(4, 6));

        Assertions.assertThat(set.span().lowerEndpoint()).isEqualTo(1);

        Assertions.assertThat(set.span().upperEndpoint()).isEqualTo(6);
    }
}
