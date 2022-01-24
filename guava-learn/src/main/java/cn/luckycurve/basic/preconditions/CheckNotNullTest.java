package cn.luckycurve.basic.preconditions;

import com.google.common.base.Preconditions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
public class CheckNotNullTest {

    @Test
    public void givenNullStringWhenCheckNotNullWithMessageThrowsException() {
        String arg = null;
        String message = "Please check the Object supplied, its null";

        Assertions.assertThatThrownBy(() -> Preconditions.checkNotNull(arg, message))
                .isInstanceOf(NullPointerException.class)
                .hasMessage(message).hasCause(null);
    }
}
