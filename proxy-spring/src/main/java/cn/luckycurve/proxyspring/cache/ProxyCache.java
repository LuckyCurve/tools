package cn.luckycurve.proxyspring.cache;

import cn.hutool.core.date.DateUnit;
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
@Setter
public class ProxyCache {
    private static final Logger logger = LoggerFactory.getLogger(ProxyCache.class);

    @Setter(AccessLevel.NONE)
    private CacheObject<List<Element>> cache;

    private Integer outDateTime = 1;

    private DateUnit dateUnit = DateUnit.DAY;

    public List<Element> get() {
        if (cache == null || DateUtil.between(cache.getDate(), DateUtil.date(), dateUnit) >= outDateTime) {
            return null;
        }

        logger.info("从缓存当中获取数据：{}条", cache.getVal().size());

        return cache.getVal();
    }

    public void set(List<Element> list) {
        cache = new CacheObject<>(list, DateUtil.date());
    }

    public void clear() {
        cache = null;
    }
}
