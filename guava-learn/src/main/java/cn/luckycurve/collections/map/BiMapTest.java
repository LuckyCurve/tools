package cn.luckycurve.collections.map;

import com.google.common.collect.HashBiMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

/**
 * bidirectional map 双向Map
 *
 * @author LuckyCurve
 */
public class BiMapTest {

    /**
     * based implements class: HashBiMap
     * if key is Enum, use class EnumHashBiMap
     * if is immutable, using ImmutableBiMap's builder method to build object
     */
    @Test
    public void test() {
        final HashBiMap<Integer, Character> map = HashBiMap.create();

        IntStream.rangeClosed(0, 4).forEach(i -> {
            map.put(i, (char) ('a' + i));
        });

        // bidirectional search
        Assertions.assertEquals(map.inverse().get('a'), 0);

        assertThatThrownBy(() -> {
            map.put(1, 'a');
            map.put(1, 'b');
        }).isInstanceOf(IllegalArgumentException.class);

        // use forcePut method to replace element that exist in BiMap
    }
}
