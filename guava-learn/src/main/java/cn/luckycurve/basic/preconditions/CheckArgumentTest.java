package cn.luckycurve.basic.preconditions;

import com.google.common.base.Preconditions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 只检查Boolean类型参数
 *
 * @author LuckyCurve
 */
public class CheckArgumentTest {

    /**
     * 无参情况，直接抛出IllegalArgumentException
     */
    @Test
    public void whenCheckArgumentEvaluatesFalseThrowsException() {
        final int age = -1;

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(null).hasCause(null);

    }

    @Test
    public void givenErrorMessageWhenCheckArgumentEvaluatesFalseThrowsException() {
        final int age = -1;
        final String message = "Age can`t be zero or less than zero.";

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0, message))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message).hasCause(null);
    }

    @Test
    public void givenTemplateMessageWhenCheckArgumentEvaluatesFalseThrowsException() {
        final int age = -1;
        final String message = "Age can`t be zero or less than zero, you supplied %s.";

        assertThatThrownBy(() -> Preconditions.checkArgument(age > 0, message, age))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message, age).hasCause(null);

    }
}
