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

    @GetMapping("/list")
    public List<String> list() throws IOException {
        return proxyService.list();
    }

    @GetMapping("/num")
    public Integer num() throws IOException {
        return proxyService.list().size();
    }

    @GetMapping("/proxy")
    public RedirectView proxy(@RequestParam(required = false) List<Integer> list) throws IOException {
        return new RedirectView(proxyService.proxy(list));
    }

}
