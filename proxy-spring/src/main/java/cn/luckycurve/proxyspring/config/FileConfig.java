package cn.luckycurve.proxyspring.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author LuckyCurve
 */
@ConfigurationProperties("file")
@Configuration
@Getter
@Setter
public class FileConfig {

    private String path;
}
