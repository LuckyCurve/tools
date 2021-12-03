package cn.luckycurve.proxypoolcollect.service;

import cn.luckycurve.proxypoolcollect.vo.ProxyInfo;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;

/**
 * @author LuckyCurve
 */
@Service
public class ProxyCollectService {

    private List<String> list = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(ProxyCollectService.class);

    {
        try {
            add("https://sspool.herokuapp.com/clash/proxies");
            add("https://free.kingfu.cf/clash/proxies");
            add("https://sspool.herokuapp.com/clash/proxies");
            add("https://proxies.bihai.cf/clash/proxies");
            add("https://free.dswang.ga/clash/proxies");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean add(String src) throws IOException {
        CloseableHttpResponse response = request(src);

        if (!list.contains(src) && response.getStatusLine().getStatusCode() == 200) {
            logger.info("添加源信息成功：{}", src);
            list.add(src);
            return true;
        }

        return false;
    }

    public List<ProxyInfo> list() throws IOException {
        ArrayList<ProxyInfo> res = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            res.add(new ProxyInfo(
                    request(list.get(i)).getStatusLine().getStatusCode() == 200,
                    i,
                    list.get(i)));
        }

        return res;
    }

    public String all() {
        Map<String, List<Object>> res = new HashMap();

        final Yaml yaml = new Yaml();

        for (String s : list) {
            final Map<String, List<Object>> temp;
            try {
                temp = yaml.load(EntityUtils.toString(request(s).getEntity()));
                if (!res.containsKey("proxies")) {
                    res.put("proxies", new LinkedList<>());
                }

                res.get("proxies").addAll(temp.get("proxies"));
            } catch (IOException e) {
                logger.warn("链接{}无法打开", s);
                e.printStackTrace();
            }
        }

        final StringWriter writer = new StringWriter();
        yaml.dump(res, writer);

        return writer.toString();
    }

    private CloseableHttpResponse request(String url) throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(RequestConfig.custom().setProxy(new HttpHost("localhost", 7890)).build());
        return client.execute(httpGet);
    }
}
