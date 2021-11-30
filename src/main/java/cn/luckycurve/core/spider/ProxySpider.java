package cn.luckycurve.core.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
public class ProxySpider {

    static final String FLGA_STR = "clash订阅链接：";

    private static final Logger logger = getLogger(ProxySpider.class);

    public static List<String> proxyRedirect() throws IOException {
        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        List<String> res = new ArrayList<>();

        documentForCur.body().getElementsByClass("entry-title").forEach(element -> {
            logger.info("获取配置源：{}", element.toString());
            String url = element.getElementsByTag("a").attr("href");
            try {
                res.add(innerUrlParse(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return res;
    }

    public static String innerUrlParse(String src) throws IOException {
        Document document = Jsoup.connect(src).get();

        return document.body().getElementsByTag("span").eachText()
                .stream().filter(s -> s.startsWith(FLGA_STR))
                .map(s -> s.substring(s.indexOf(FLGA_STR) + FLGA_STR.length()))
                .findFirst().get();
    }
}
