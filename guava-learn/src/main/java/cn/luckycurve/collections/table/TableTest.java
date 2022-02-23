package cn.luckycurve.collections.table;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Guava Table two key map
 *
 * @author LuckyCurve
 */
public class TableTest {

    /**
     * if we need to order with two key, we should use TreeBasedTable
     * if table size is fixed, we should use ArrayTable
     * if table is immutable, we should use ImmutableTable
     */
    @Test
    public void test() {
        final HashBasedTable<String, String, Integer> table = HashBasedTable.create();

        // put and get
        table.put("rowKey", "columnKey", 1);

        Assertions.assertThat(table.get("rowKey", "columnKey")).isEqualTo(1);

        // check row or column is contains in table method is start with contains

        // convert table to map
        table.put("rowKey", "c", 1);
        final Map<String, Integer> map = table.row("rowKey");

        Assertions.assertThat(map.keySet()).containsAll(Lists.newArrayList("columnKey", "c"));

        // get row and column key
        Assertions.assertThat(table.rowKeySet()).containsAll(Lists
                .newArrayList("rowKey"));
    }
}
