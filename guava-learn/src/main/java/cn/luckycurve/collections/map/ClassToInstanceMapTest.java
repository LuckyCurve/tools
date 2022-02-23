package cn.luckycurve.collections.map;

import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Range;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
public class ClassToInstanceMapTest {

    @Test
    public void test() {
        final MutableClassToInstanceMap<Object> map = MutableClassToInstanceMap.create();

        map.put(ClassToInstanceMapTest.class, this);

        final ClassToInstanceMapTest instance = map.getInstance(ClassToInstanceMapTest.class);

        Assertions.assertEquals(instance, this);
    }
}
