package cn.luckycurve.core.file;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 持久化到指定文件目录
 *
 * @author LuckyCurve
 */
public class ProxyPersistence {

    public static final String FILE_PATH = "F:\\OneDrive - for personal\\Entertainment\\Clash.txt";

    private static final Logger logger = LoggerFactory.getLogger(ProxyPersistence.class);

    public static void main(String[] args) throws IOException {
        final File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.createNewFile();
        }

        try (final FileWriter writer = new FileWriter(file, true)) {
            final List<String> data = collectData();

            for (String s : data) {
                writer.write(s + "\n");
            }

            writer.write("\n\n\n\n");
        }

        logger.info("写入成功");
    }


    /**
     * 完成数据采集，缓存失效时完成该方法调用
     *
     * @return
     */
    private static List<String> collectData() throws IOException {
        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        List<String> res = new LinkedList<>();

        documentForCur.body().getElementsByClass("entry-title").forEach(element -> {
            try {
                res.addAll(innerElementParse(element));
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
    private static List<String> innerElementParse(Element src) throws IOException {
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
}
