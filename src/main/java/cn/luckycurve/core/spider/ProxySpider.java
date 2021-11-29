package cn.luckycurve.core.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * @author LuckyCurve
 */
public class ProxySpider {

    static final String FLGA_STR = "clash订阅链接：";

    public static String proxyRedirect() throws IOException {
        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        String curUrl = documentForCur.body().getElementsByClass("entry-title").get(0).getElementsByTag("a").attr("href");

        Document documentForResource = Jsoup.connect(curUrl).get();

        List<String> info = documentForResource.body().getElementsByTag("span").eachText();

        return info.stream().filter(s -> s.startsWith(FLGA_STR))
                .map(s -> s.substring(s.indexOf(FLGA_STR) + FLGA_STR.length()))
                .findFirst().get();
    }
}
