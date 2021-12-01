package cn.luckycurve.proxyspring.cache;

import cn.hutool.core.date.DateUtil;
import cn.luckycurve.proxyspring.service.ProxyService;
import lombok.Data;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author LuckyCurve
 */
@Component
@Data
public class ProxyCache {
    private static final Logger logger = LoggerFactory.getLogger(ProxyCache.class);

    private final List<List<String>> list = new ArrayList<>();

    private Date date = DateUtil.beginOfDay(DateUtil.tomorrow());

    @Value("${proxy.size}")
    private Integer size;

    public boolean outDate() {
        return DateUtil.date().isAfter(date);
    }

    public List<String> get(Integer num) throws IOException {
        List<String> res = new LinkedList<>();

        if (num == null) {
            num = list.size();
        }

        num = Math.min(num, list.size());

        for (int i = list.size() - 1; i >= 0; i--) {
            if (num <= 0) {
                break;
            }
            res.addAll(list.get(i));
            num--;
        }

        return res;
    }

    public void set(List<List<String>> temp) {
        for (int i = temp.size() - 1; i >= 0; i--) {
            List<String> element = temp.get(i);
            if (!this.list.contains(element)) {
                this.list.add(element);
            }
        }

        // 防止数据膨胀
        while (list.size() > size) {
            list.remove(0);
        }
    }
}
