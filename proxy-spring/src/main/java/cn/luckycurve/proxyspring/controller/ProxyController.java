package cn.luckycurve.proxyspring.controller;

import cn.luckycurve.proxyspring.service.ProxyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author LuckyCurve
 */
@RestController
public class ProxyController {

    @Resource
    ProxyService proxyService;

    @GetMapping("/num")
    public Integer num() throws IOException {
        return proxyService.num();
    }

    @GetMapping("/proxy")
    public String proxy(@RequestParam(value = "num", required = false) Integer num) throws IOException {
        return proxyService.proxy(num);
    }
}
