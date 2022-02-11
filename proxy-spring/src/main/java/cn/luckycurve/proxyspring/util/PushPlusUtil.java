package cn.luckycurve.proxyspring.util;

import okhttp3.*;

/**
 * @author LuckyCurve
 */
public class PushPlusUtil {

    public static final String SEND_URL = "http://www.pushplus.plus/send";

    /**
     * @param title    message title
     * @param content  message content
     * @param template commonly used: html, txt, json, markdown
     * @return request success return true
     */
    public static boolean sendMessage(String title, String content, String template) {
        final FormBody body = new FormBody.Builder()
                .add("token", "aa7ea9fc16f546b6be55fa06965f8d33")
                .add("title", title)
                .add("content", content)
                .add("template", template)
                .build();

        final Request request = new Request.Builder()
                .url(SEND_URL)
                .post(body)
                .build();

        final OkHttpClient client = new OkHttpClient();

        Response response = null;
        try {
            response = client.newCall(request).execute();

            if (response.code() != 200) {
                throw new Exception("request pushPlusPlus API failed! code not equals 200");
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        } finally {
            response.close();
        }

        return true;
    }
}
