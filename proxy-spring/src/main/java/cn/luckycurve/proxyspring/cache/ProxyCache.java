package cn.luckycurve.proxyspring.cache;

import cn.hutool.core.date.DateUtil;
import lombok.AccessLevel;
import lombok.Setter;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LuckyCurve
 */
@Component
public class ProxyCache {
    private static final Logger logger = LoggerFactory.getLogger(ProxyCache.class);

    @Setter(AccessLevel.NONE)
    private CacheObject<List<Element>> cache;

    public List<Element> get() {
        if (cache == null || DateUtil.date().isAfter(cache.getDate())) {
            return null;
        }

        logger.info("从缓存当中获取数据：{}条", cache.getVal().size());

        return cache.getVal();
    }

    public void set(List<Element> list) {
        cache = new CacheObject<>(list, DateUtil.beginOfDay(DateUtil.tomorrow()));
    }

    public void clear() {
        cache = null;
    }
}
