package cn.luckycurve.proxyspring.service;

import cn.luckycurve.proxyspring.config.FileConfig;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Predicates;
import com.google.common.base.Throwables;
import com.google.common.io.Files;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author LuckyCurve
 */
@Service
public class ProxyService {

    @Autowired
    FileConfig fileConfig;

    public final String prefix = "http://localhost:25500/sub?target=clash&new_name=true&url=";

    public final String suffix = "&insert=false&config=https%3A%2F%2Fraw.githubusercontent.com%2FACL4SSR%2FACL4SSR%2Fmaster%2FClash%2Fconfig%2FACL4SSR_Online.ini";


    public String get() {
        String url;

        try {
            // get file
            final List<String> lines = Files.readLines(new File(fileConfig.getPath()), Charsets.UTF_8);

            url = Joiner.on("|").join(lines.stream().filter(Predicates.notNull()).collect(Collectors.toSet()));

            url = URLEncoder.encode(url, Charsets.UTF_8);

        } catch (IOException e) {
            // if e is Uncheck Exception, thrwo it directly
            Throwables.throwIfUnchecked(e);

            throw new RuntimeException("get local file fail!", e);
        }

        try {
            final OkHttpClient client = new OkHttpClient.Builder()
                    .callTimeout(1, TimeUnit.MINUTES)
                    .build();

            final Request request = new Request.Builder()
                    .url(prefix + url + suffix)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            // if e is Uncheck Exception, thrwo it directly
            Throwables.throwIfUnchecked(e);

            throw new RuntimeException("request subconverter fail!", e);
        }
    }
}