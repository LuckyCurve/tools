package cn.luckycurve.basic.preconditions;

import com.google.common.base.Preconditions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 判断是否越界，但传入的不是数组或者字符串本身
 *
 * @author LuckyCurve
 */
public class CheckElementIndexTest {

    /**
     * 看下实现就知道了，很简单，直接传入的size
     */
    @Test
    public void givenArrayAndMsgWhenCheckElementEvaluateFalseThrowsException() {
        int[] array = {1, 2, 3, 4, 5};
        int index = 5;
        String msg = "input index out of bounds";
        Assertions.assertThatThrownBy(() -> Preconditions.checkElementIndex(index, array.length, msg))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageStartingWith(msg).hasCause(null);
    }
}
