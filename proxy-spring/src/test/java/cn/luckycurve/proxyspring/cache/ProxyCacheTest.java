package cn.luckycurve.proxyspring.cache;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LuckyCurve
 */
class ProxyCacheTest {

    @Test
    public void test() {
        ProxyCache cache = new ProxyCache();

        cache.set(new ArrayList<>());

        assertNotNull(cache.get());
    }

}