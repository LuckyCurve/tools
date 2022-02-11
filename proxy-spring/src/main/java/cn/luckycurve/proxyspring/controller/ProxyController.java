package cn.luckycurve.proxyspring.controller;

import cn.luckycurve.proxyspring.service.ProxyService;
import com.google.common.base.Suppliers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author LuckyCurve
 */
@RestController
public class ProxyController {

    @Resource
    ProxyService proxyService;

    Supplier<String> supplier;

    @PostConstruct
    public void init() {
        supplier = Suppliers.memoizeWithExpiration(proxyService::get, 12, TimeUnit.HOURS);
    }

    @GetMapping("/get")
    public String get(@RequestParam String password) {
        if (!Objects.equals(password, "1790")) {
            return "";
        }

        return supplier.get();
    }
}
