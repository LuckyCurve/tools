package cn.luckycurve.proxyspring.service;

import cn.luckycurve.proxyspring.cache.ProxyCache;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
@Service
public class ProxyService {

    @Resource
    ProxyCache cache;

    public List<String> list() throws IOException {
        return getCrawlConfigurationSource().stream()
                .map(Node::toString).collect(Collectors.toList());
    }


    public String proxy(List<Integer> list) throws IOException {
        List<Element> source = getCrawlConfigurationSource();

        List<Element> cur;

        if (list == null) {
            logger.info("list参数为空，默认全选");
            cur = source;
        } else {
            cur = new ArrayList<>();

            for (Integer i : list) {
                cur.add(source.get(i % source.size()));
            }
        }

        List<String> target = new ArrayList<>(cur.size());

        cur.forEach(element -> {
            try {
                target.add(innerElementParse(element));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Url拼接
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < target.size(); i++) {
            logger.info("添加第 {} 个源。。。成功", i + 1);
            builder.append(target.get(i));
            if (i != target.size() - 1) {
                builder.append("|");
            }
        }

        return START + URLEncoder.encode(builder.toString());
    }

    public static final String FLGA_STR = "clash订阅链接：";

    public static final String START = "https://subcon.dlj.tf/sub?target=clash&new_name=true&url=";

    private static final Logger logger = getLogger(ProxyService.class);

    /**
     * 获取待抓取配置源信息
     *
     * @return 包含Title和其他标签的配置源信息
     */
    private List<Element> getCrawlConfigurationSource() throws IOException {
        List<Element> list = cache.get();

        if (list != null) {
            return list;
        }

        // 缓存失效
        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        List<Element> res = new ArrayList<>();

        documentForCur.body().getElementsByClass("entry-title").forEach(element -> {
            logger.info("获取配置源：{}", element.toString());
            res.add(element);
        });

        cache.set(res);

        return res;
    }

    /**
     * 完成对配置源信息的解析
     *
     * @param src 待抓取配置源
     * @return 节点列表
     */
    private String innerElementParse(Element src) throws IOException {
        String url = src.getElementsByTag("a").attr("href");

        Document document = Jsoup.connect(url).get();

        return document.body().getElementsByTag("span").eachText()
                .stream().filter(s -> s.startsWith(FLGA_STR))
                .map(s -> s.substring(s.indexOf(FLGA_STR) + FLGA_STR.length()))
                .findFirst().get();
    }

}
