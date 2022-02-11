package cn.luckycurve.collections.queue;

import com.google.common.base.Joiner;
import com.google.common.collect.EvictingQueue;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * an implementation of the circular buffer concept
 * <p>
 * when the queue is full, it automatically evicts an element from its head
 *
 * @author LuckyCurve
 */
public class EvictingQueueTest {

    @Test
    public void evictingQueueTest() {
        final EvictingQueue<Integer> queue = EvictingQueue.create(10);

        IntStream.rangeClosed(0, 9)
                .forEach(queue::add);

        assertThat(Joiner.on("").join(queue)).isEqualTo("0123456789");

        queue.add(100);

        assertThat(Joiner.on("").join(queue)).isEqualTo("123456789100");

    }
}
