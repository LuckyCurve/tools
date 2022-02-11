package cn.luckycurve.proxyspring.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LuckyCurve
 */
class ProxyServiceTest {

    @Test
    void get() throws IOException {
        final ProxyService service = new ProxyService();

        final String s = service.get();

        System.out.println(s);
    }
}