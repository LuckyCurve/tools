package cn.luckycurve.core.spider;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

/**
 * @author LuckyCurve
 */
public class BulianglinSpider {

    private static final Logger logger = LoggerFactory.getLogger(BulianglinSpider.class);

    private static final String FILE_PATH = "F:\\OneDrive - for personal\\Entertainment\\Good.txt";

    public static void main(String[] args) throws IOException {
        final CloseableHttpClient client = HttpClients.createDefault();

        final CloseableHttpResponse response = client.execute(new HttpGet("https://api.buliang0.cf/easyclash"));

        String res = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);

        final byte[] decode = Base64.getDecoder().decode(res);

        res = new String(decode, Charsets.UTF_8);

        final File file = new File(FILE_PATH);

        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file));) {
            writer.write(res);
        }

        logger.info("持久化成功");

    }
}
