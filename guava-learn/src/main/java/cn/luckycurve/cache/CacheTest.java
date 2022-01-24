package cn.luckycurve.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Guava cache test
 * <p>
 * Guava cache is not only a map to store key-value, but has a ClassLoader
 * to manager key-value
 * <p>
 * cache eviction policies is LRU
 *
 * thread safe
 * can using <b>CacheStats</b> to measure your cache performance
 *
 * @author LuckyCurve
 */
public class CacheTest {

    /**
     * store value is key uppercase
     * <p>
     * core method: Cache#get Cache#getUnchecked
     * different between in them is getUnchecked request ClassLoader#load not throw check exception such as IOException if this use method get
     * because method getUnchecked will wrap exception by RuntimeException
     */
    @Test
    public void useGuavaCache() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            /**
             * value generic strategy
             */
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        // use util CacheBuilder to build a cache
        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(cacheLoader);

        assertEquals(cache.size(), 0);
        assertEquals("HELLO!", cache.getUnchecked("hello!"));
        assertEquals(cache.size(), 1);
    }

    /**
     * =======================================================
     * <p>
     * default LRU eviction policy
     */
    @Test
    public void evictionPoliciesEvictionBySize() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(3).build(cacheLoader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("first");
        cache.getUnchecked("forth");
        assertEquals(cache.size(), 3);
        assertNull(cache.getIfPresent("second"));
        assertEquals("FIRST", cache.getUnchecked("first"));
    }

    /**
     * weight should defined by us
     */
    @Test
    public void evictionPoliciesByWeight() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .weigher((Weigher<String, String>) (key, value) -> value.length())
                .maximumWeight(16).build(cacheLoader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("first");
        cache.getUnchecked("forth");
        assertEquals(cache.size(), 3);
        assertNull(cache.getIfPresent("second"));
        assertEquals("FIRST", cache.getUnchecked("first"));
    }


    /**
     * core method: expireAfterAccess and expireAfterWrite
     * former is base on idle and letter base on TTL
     * <p>
     * evict by time not create a thread background and to destroy entry but when we use method get
     * it compare current time and last time and handle it
     */
    @Test
    public void evictionPoliciesByTime() throws InterruptedException {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MILLISECONDS).build(cacheLoader);

        cache.getUnchecked("first");
        assertNotNull(cache.getIfPresent("first"));

        TimeUnit.SECONDS.sleep(1);

        assertNull(cache.getIfPresent("first"));
    }


    /**
     * recommend use in couple
     */
    @Test
    public void usingWeakKeyAndValueReferenceInCache() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .weakKeys()
                .weakValues()
                .build(cacheLoader);

        cache.getUnchecked("hello");

        assertNotNull(cache.getIfPresent("hello"));

        System.gc();

        assertNull(cache.getIfPresent("hello"));
    }


    /**
     * many soft reference may affect system performance, recommend use method maximumSize to instead
     */
    @Test
    public void usingSoftValueReferenceInCache() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .softValues()
                .build(cacheLoader);
    }


    @Test
    public void guavaCacheHandlerNullValues() {
        final CacheLoader<String, String> loader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return null;
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

        assertThatThrownBy(() -> {
            cache.getUnchecked("2");
        }).isInstanceOf(CacheLoader.InvalidCacheLoadException.class)
                .hasMessageContaining("null")
                .hasCause(null);
    }


    /**
     * ===================================================================
     * cache refresh
     * <p>
     * until the new value is successfully loaded, the previous value will be returned by the get(key)
     */
    @Test
    public void singleRefresh() {
        final CacheLoader<String, String> cacheLoader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                TimeUnit.SECONDS.sleep(2);
                return new Date().toString();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(cacheLoader);

        System.out.println(cache.getUnchecked("hello"));

        System.out.println("refresh");
        cache.refresh("key");

        System.out.println(cache.getUnchecked("hello"));
    }


    /**
     * important point: the value will actually be refresh only it required by get(key)
     * <p>
     * it just after a specified duration this key is eligible to refresh not current refresh
     */
    @Test
    public void automaticRefresh() {
        final CacheLoader<String, String> loader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        CacheBuilder.newBuilder().refreshAfterWrite(1, TimeUnit.HOURS)
                .build(loader);
    }


    @Test
    public void preloadCache() {
        final CacheLoader<String, String> loader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(loader);

        cache.put("hello", "HELLO");

        final HashMap<String, String> map = Maps.newHashMap();
        map.put("hello", "HELLO2");

        cache.putAll(map);

        assertEquals(cache.getIfPresent("hello"), "HELLO2");
    }


    /**
     * ===================================================================
     * sometimes you need to make some action when a record removed from the cache
     */
    @Test
    public void removalNotification() throws InterruptedException {
        final CacheLoader<String, String> loader = new CacheLoader<>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        final LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .removalListener(notification -> {
                    System.out.println(notification);
                    System.out.println(notification.getCause());
                })
                .expireAfterAccess(2, TimeUnit.MILLISECONDS)
                .build(loader);


        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        TimeUnit.MILLISECONDS.sleep(5);
        cache.getUnchecked("first");

        TimeUnit.SECONDS.sleep(10);

    }

}
