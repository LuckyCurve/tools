package cn.luckycurve.server;

import cn.luckycurve.core.spider.ProxySpider;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;

import java.net.URLEncoder;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
public class ProxyRedirectHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    public static final String START = "https://subcon.dlj.tf/sub?target=clash&new_name=true&url=";

    private final Logger logger = getLogger(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        logger.info("Proxy请求处理");

        List<String> listSrc = ProxySpider.proxyRedirect();

        // Url拼接
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < listSrc.size(); i++) {
            logger.info("添加第 {} 个源。。。成功", i + 1);
            builder.append(listSrc.get(i));
            if (i != listSrc.size() - 1) {
                builder.append("|");
            }
        }

        String url = START + URLEncoder.encode(builder.toString());

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.PERMANENT_REDIRECT);
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with,content-type");
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET");
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.CACHE_CONTROL, "no-store");
        headers.set(HttpHeaderNames.LOCATION, url);

        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
