package cn.luckycurve.proxyspring;

import cn.hutool.core.date.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

/**
 * @author LuckyCurve
 */
public class JsoupTest {

    @Test
    public void test() throws IOException {
        System.out.println(DateUtil.tomorrow());

        LinkedList<String> list = new LinkedList<>();

        Document document = Jsoup.connect("https://www.cfmem.com/2021/11/20211201-20211394k-v2rayclash-vpn.html").get();

        document.select("span[role=presentation]span[style=box-sizing: border-box; padding-right: 0.1px;]").forEach(element -> {
            String candidate = element.text();
            if (!candidate.contains("\n") && candidate != " ") {
                list.add(candidate);
            }
        });

        list.removeLast();
        list.removeLast();

        System.out.println(list.size());
    }
}