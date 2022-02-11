package cn.luckycurve.proxyspring.scheduled;

import cn.luckycurve.proxyspring.config.FileConfig;
import cn.luckycurve.proxyspring.util.PushPlusUtil;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
@Component
public class ScheduledTask {

    @Resource
    FileConfig fileConfig;

    private static final Logger logger = getLogger(ScheduledTask.class);

    @Scheduled(cron = "0 30 2 * * *")
    public void test1() throws IOException {
        final List<String> lines = Files.readLines(new File(fileConfig.getPath()), Charsets.UTF_8);

        logger.info("exist Items: " + lines.size());

        final CharSink charSink = Files.asCharSink(new File(fileConfig.getPath()), Charsets.UTF_8);

        Document documentForCur = Jsoup.connect("https://www.cfmem.com/search/label/free").get();

        List<String> res = new LinkedList<>();

        AtomicInteger index = new AtomicInteger();

        documentForCur.body().getElementsByClass("entry-title").forEach(element -> {
            try {
                res.addAll(innerElementParse(element));

                logger.info("get proxy sources item: {} 。。。success", index.incrementAndGet());
            } catch (IOException e) {
                PushPlusUtil.sendMessage("network error", "can't not get website response" + "\n" + Throwables.getStackTraceAsString(e), "txt");

                e.printStackTrace();
            }
        });

        final HashSet<String> set = Sets.newHashSet(lines);

        set.addAll(res);

        // result rewrite to file
        charSink.writeLines(set);
    }


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
