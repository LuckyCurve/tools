package cn.luckycurve.proxyspring.service;

import cn.luckycurve.proxyspring.cache.ProxyCache;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
@Service
public class ProxyService {

    @Resource
    ProxyCache cache;

    public String proxy(Integer num) throws IOException {
        if (cache.getList().isEmpty() || cache.outDate()) {
            cache.set(collectData());
        }

        List<String> target = cache.get(num);

        // Url拼接
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < target.size(); i++) {
            builder.append(target.get(i));
            if (i != target.size() - 1) {
                builder.append("|");
            }
        }

        CloseableHttpResponse response = HttpClients.createDefault().execute(new HttpGet(START + URLEncoder.encode(builder.toString())));

        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    public static final String START = "http://localhost:25500/sub?target=clash&new_name=true&url=";

    private static final Logger logger = getLogger(ProxyService.class);

    /**
     * 完成数据采集，缓存失效时完成该方法调用
     *
     * @return
     */
    private List<List<String>> collectData() throws IOException {
        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        List<List<String>> res = new LinkedList<>();

        documentForCur.body().getElementsByClass("entry-title").forEach(element -> {
            try {
                res.add(innerElementParse(element));
                logger.info("采集配置源 {} 信息。。。成功", element.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return res;
    }

    /**
     * 完成对配置源信息的解析
     *
     * @param src 待抓取配置源
     * @return 节点列表
     */
    private List<String> innerElementParse(Element src) throws IOException {
        String url = src.getElementsByTag("a").attr("href");

        Document document = Jsoup.connect(url).get();

        LinkedList<String> list = new LinkedList<>();

        document.select("span[role=presentation]span[style=box-sizing: border-box; padding-right: 0.1px;]").forEach(element -> {
            String candidate = element.text();
            if (!candidate.contains("\n")) {
                list.add(candidate);
            }
        });

        list.removeLast();
        list.removeLast();

        return list;
    }

    public Integer num() {
        return cache.getList().size();
    }
}