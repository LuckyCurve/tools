package cn.luckycurve.proxypoolcollect.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author LuckyCurve
 */
@Data
@AllArgsConstructor
public class ProxyInfo {

    private Boolean healthCheck;

    private Integer proxyIndex;

    private String url;
}
