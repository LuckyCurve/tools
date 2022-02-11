package cn.luckycurve.proxyspring.util;

import org.junit.jupiter.api.Test;

/**
 * @author LuckyCurve
 */
class PushPlusUtilTest {

    @Test
    public void testSendMessage() {
        PushPlusUtil.sendMessage("title", "Content", "txt");
    }

}