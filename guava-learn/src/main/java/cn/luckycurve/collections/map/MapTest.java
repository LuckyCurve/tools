package cn.luckycurve.collections.map;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LuckyCurve
 */
public class MapTest {

    @Test
    public void createImmutableMap() {
        final ImmutableMap<String, Integer> map = ImmutableMap.<String, Integer>builder()
                .put("Tom", 2)
                .put("John", 3)
                .build();

        assertEquals(map.size(), 2);
    }


    /**
     * sorted by key
     */
    @Test
    public void createSortMap() {
        final ImmutableSortedMap<Comparable<?>, Object> salary = ImmutableSortedMap.naturalOrder()
                .put("John", 2000)
                .put("Adam", 1000)
                .put("Tom", 5000)
                .build();

        assertEquals(salary.firstKey(), "Adam");
        assertEquals(salary.lastKey(), "Tom");
    }


    /**
     * using BiMap if we want to use key back to value
     * we must make sure the value we put in bitMap value is unique
     */
    @Test
    public void testBiMap() {
        final HashBiMap<Object, Object> biMap =
                HashBiMap.create();

        biMap.put("first", 1);
        biMap.put("second", 2);
        biMap.put("third", 3);

        assertEquals(biMap.get("first"), 1);
        assertEquals(biMap.inverse().get(3), "third");
    }

    @Test
    public void testMultiMap() {
        final ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();

        for (int i = 0; i < 100; i++) {
            multimap.put("hello", Integer.toString(i));
        }

        System.out.println(multimap.get("hello"));
    }

    @Test
    public void testTable() {
        final HashBasedTable<String, String, Integer> distance = HashBasedTable.create();
        distance.put("London", "Paris", 340);
        distance.put("New York", "Los Angeles", 3940);
        distance.put("London", "New York", 5576);

        assertThat(distance.rowKeySet())
                .containsAll(Lists.newArrayList("London", "New York"));

        assertThat(distance.columnKeySet())
                .containsAll(Lists.newArrayList("Paris", "Los Angeles", "New York"));


        final Table<String, String, Integer> transpose = Tables.transpose(distance);

        assertThat(transpose.columnKeySet())
                .containsAll(Lists.newArrayList("London", "New York"));

        assertThat(transpose.rowKeySet())
                .containsAll(Lists.newArrayList("Paris", "Los Angeles", "New York"));
    }

    @Test
    public void classToInstanceMap() {
        final MutableClassToInstanceMap<Number> map = MutableClassToInstanceMap.create();

        map.put(Integer.class, 1);
        map.put(Double.class, 1.5);

        assertEquals(1, map.get(Integer.class));
        assertEquals(1.5, map.get(Double.class));
    }

    /**
     * actually use Iterator to finish it
     */
    @Test
    public void testUsingMultiMap() {
        final ArrayList<String> list = Lists.newArrayList("John", "Adam", "Tom");

        final ImmutableListMultimap<Integer, String> index = Multimaps.index(list, input -> input.length());

        System.out.println(index);
    }
}
