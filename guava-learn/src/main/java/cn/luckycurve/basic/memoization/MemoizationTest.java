package cn.luckycurve.basic.memoization;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Main class: Suppliers
 *
 * @author LuckyCurve
 */
public class MemoizationTest {

    /**
     * --------------for no arguments method----------------------------------------
     * <p>
     * once the get method is called, the returned value will persist in machine memory
     * while the Java Application is still running
     */
    @Test
    public void supplierMemoizationWithoutEviction() {
        final Supplier<String> supplier = Suppliers.memoize(this::computationExpensiveMethod);
        for (int i = 0; i < 10; i++) {
            System.out.println("get method result: " + supplier.get());
        }
    }

    @Test
    public void supplierMemoizationWithEvictionByTTL() throws InterruptedException {
        final Supplier<String> memoize = Suppliers.memoizeWithExpiration(this::computationExpensiveMethod, 5, TimeUnit.MILLISECONDS);
        for (int i = 0; i < 20; i++) {
            System.out.println("get method result:" + memoize.get());
        }
        TimeUnit.MILLISECONDS.sleep(10);
        System.out.println("get method result:" + memoize.get());
    }

    public String computationExpensiveMethod() {
        System.out.println("execute compute, expensive");
        return "hello world";
    }

    @Test
    public void functionMemoizationWithoutEviction() throws ExecutionException, InterruptedException {
        final LoadingCache<Integer, String> cache = CacheBuilder.newBuilder()
                // evict 2 second ago input data
                .expireAfterAccess(2, TimeUnit.SECONDS)
                // evict oldest entry once the memoize size has been 100
                //.maximumSize(1) can't exist conflict with expireAfterAccess
                .build(CacheLoader.from(this::computationExpensiveMethodWithSingleArgs));

        System.out.println(cache.get(1));
        System.out.println(cache.get(1));
        TimeUnit.SECONDS.sleep(2);
        System.out.println(cache.get(1));
    }


    public String computationExpensiveMethodWithSingleArgs(Integer i) {
        System.out.println("execute compute, expensive");
        return "hello world" + i;
    }


    @Test
    public void testAssertions() {
        Assertions.assertThat(1).isCloseTo(2, Offset.offset(1));
    }
}
