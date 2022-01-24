package cn.luckycurve.core.file;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

/**
 * @author LuckyCurve
 */
public class TrackerFormat {

    private static String srcBest = "https://ngosang.github.io/trackerslist/trackers_best.txt";

    private static String srcAll = "https://ngosang.github.io/trackerslist/trackers_all.txt";

    public static void main(String[] args) throws IOException {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.setContents(new StringSelection(trackerAria2Create(srcAll)), null);

        System.out.println("success");
    }

    private static String trackerAria2Create(String src) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        CloseableHttpResponse response = client.execute(new HttpGet(src));

        String info = EntityUtils.toString(response.getEntity());

        while (info.endsWith("\n\n")) {
            info = info.substring(0, info.length() - 2);
        }

        return info.replaceAll("\n\n", ",\n");
    }
}