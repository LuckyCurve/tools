package cn.luckycurve.proxyspring.scheduled;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LuckyCurve
 */
class ScheduledTaskTest {

    @Test
    void test1() throws IOException {

        final ScheduledTask task = new ScheduledTask();

        task.test1();
    }
}