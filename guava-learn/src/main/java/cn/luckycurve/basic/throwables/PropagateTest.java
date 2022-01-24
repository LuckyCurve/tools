package cn.luckycurve.basic.throwables;

import com.google.common.base.Throwables;
import org.junit.jupiter.api.Test;

/**
 * finish the exception propagate
 *
 * @author LuckyCurve
 */
public class PropagateTest {

    @Test
    public void testGetExceptionCauseAndStackTraceAsString() {

        try {
            testPropagateWithThrows();
        } catch (Exception e) {
            System.out.println(Throwables.getRootCause(e));
            System.out.println("=======================================");
            System.out.println(Throwables.getCausalChain(e));
            System.out.println("=======================================");
            System.out.println(Throwables.getStackTraceAsString(e));
        }
    }

    @Test
    public void testPropagateWithThrows() {
        try {
            exception();
        } catch (IndexOutOfBoundsException exception) {
            // can deal
        } catch (Throwable throwable) {
            // can not deal, throw away, do not throw directly
            // for subclass: Error, RuntimeException, IllegalArgumentException we throw direct
            Throwables.propagateIfPossible(throwable, IllegalArgumentException.class);
            // or we not need third class we can use as follow
            Throwables.throwIfUnchecked(throwable);
            throw new RuntimeException("unknown exception:", throwable);
        }
    }

    public void exception() throws Throwable {
        throw new Throwable();
    }
}
