package cn.luckycurve.collections.map;

import com.google.common.collect.MapMaker;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentMap;

/**
 * create thread-safe maps
 *
 * only set weak or value is weak reference will not occur memory leak
 *
 * if we use weak reference, it compare equals not through method equals, but operate ==
 *
 * @author LuckyCurve
 */
public class MapMakerTest {
    @Test
    public void createConcurrentMap() {
        final ConcurrentMap<String, Object> map = new MapMaker()
                // if we note this line, it will throw OOM Exception
                .weakKeys()
                .makeMap();


        while (true) {
            map.put("hello" + System.currentTimeMillis(), new byte[1024]);
            System.out.println(map.size());
        }
    }

}
