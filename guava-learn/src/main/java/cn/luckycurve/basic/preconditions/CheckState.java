package cn.luckycurve.basic.preconditions;

import com.google.common.base.Preconditions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
public class CheckState {

    @Test
    public void givenStatesAndMessageWhenCheckStateEvaluatesFalseThrowsException() {
        int age = -1;
        String message = "error supplied, age must be positive.";

        Assertions.assertThatThrownBy(() -> Preconditions.checkState(age > 0, message))
                .hasMessage(message).hasCause(null);


    }
}
