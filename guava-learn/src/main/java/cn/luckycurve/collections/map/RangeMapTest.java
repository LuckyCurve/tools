package cn.luckycurve.collections.map;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * not stable
 * <p>
 * same with segmentation function
 *
 * @author LuckyCurve
 */
public class RangeMapTest {

    @Test
    public void test() {
        final TreeRangeMap<Integer, String> map = TreeRangeMap.create();

        map.put(Range.closed(0, 2), "small");
        map.put(Range.closed(3, 4), "middle");
        map.put(Range.closed(5, 6), "big");

        Assertions.assertThat(map.get(2)).isEqualTo("small");

        // coverage
        map.put(Range.closed(Integer.MIN_VALUE, Integer.MAX_VALUE), "Lucky");

        Assertions.assertThat(map.get(2)).isEqualTo("Lucky");

        // remove value based on range
        map.remove(Range.closed(1, 2));

        Assertions.assertThat(map.get(2)).isNull();

        // get sub range map
        final RangeMap<Integer, String> subRangeMap = map.subRangeMap(Range.closed(3, 4));

        Assertions.assertThat(subRangeMap.span().lowerEndpoint()).isEqualTo(3);

        Assertions.assertThat(subRangeMap.span().upperEndpoint()).isEqualTo(4);

        // get entry
        final Map.Entry<Range<Integer>, String> entry = map.getEntry(Integer.MAX_VALUE);

        Assertions.assertThat(entry.getKey()).isEqualTo(Range.openClosed(2, Integer.MAX_VALUE));
        Assertions.assertThat(entry.getValue()).isEqualTo("Lucky");
    }
}
