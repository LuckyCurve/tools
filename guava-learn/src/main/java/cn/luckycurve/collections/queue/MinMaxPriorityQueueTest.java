package cn.luckycurve.collections.queue;

import com.google.common.collect.MinMaxPriorityQueue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * offer method peekFirst and peekLast to get min and max value
 * <p>
 * like PriorityQueue but can get Queue tail
 *
 * when we control queue size, when it achieve max size, the element in tail
 * will be removed
 *
 * @author LuckyCurve
 */
public class MinMaxPriorityQueueTest {

    @Test
    public void test() {
        final MinMaxPriorityQueue<Integer> queue = MinMaxPriorityQueue
                .orderedBy(Comparator.comparingInt(o -> (int) o))
                .maximumSize(4).create();

        IntStream.rangeClosed(1, 5).forEach(queue::add);

        System.out.println(queue);

        Assertions.assertThat(queue.peekFirst()).isEqualTo(1);
        Assertions.assertThat(queue.peekLast()).isEqualTo(4);
    }
}
