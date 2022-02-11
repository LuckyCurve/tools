package cn.luckycurve.proxyspring;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ProxySpringApplicationTests {

    @Test
    void contextLoads() throws IOException {
        final Document document = Jsoup.connect("https://www.bilibili.com/v/popular/rank/all").get();

        final Elements elements = document.getElementsByClass("rank-item");

        for (Element element : elements) {
            final Elements title = element.getElementsByClass("title");

            System.out.println(title.text());
        }
    }

}
