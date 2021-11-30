package cn.luckycurve.proxyspring.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

/**
 * @author LuckyCurve
 */
@AllArgsConstructor
@Getter
public class CacheObject<T> {
    private final T val;

    private final Date date;

}
