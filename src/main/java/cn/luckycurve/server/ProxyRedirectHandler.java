package cn.luckycurve.server;

import cn.luckycurve.core.spider.ProxySpider;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author LuckyCurve
 */
public class ProxyRedirectHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final Logger logger = getLogger(getClass());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        logger.info("Proxy请求处理");

        String url = ProxySpider.proxyRedirect();

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.PERMANENT_REDIRECT);
        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with,content-type");
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET");
        headers.set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        headers.set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        headers.set(HttpHeaderNames.LOCATION, url);

        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE);
    }
}
