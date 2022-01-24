package cn.luckycurve.basic.preconditions;

import com.google.common.base.Preconditions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
public class CheckPositionIndex {

    @Test
    public void givenArrayAndIndexWhenCheckPositionEvaluatesFalseThrowsException() {
        int[] nums = {1, 2, 3, 4, 5};
        int index = 5;

        String message = "the index you supplied out of bounds, please check it.";

        Assertions.assertThatThrownBy(() -> Preconditions.checkPositionIndex(index, nums.length - 1, message))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageStartingWith(message).hasCause(null);


    }
}
