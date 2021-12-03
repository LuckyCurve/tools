package cn.luckycurve.proxypoolcollect.controller;

import cn.luckycurve.proxypoolcollect.service.ProxyCollectService;
import cn.luckycurve.proxypoolcollect.vo.ProxyInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 完成节点资源的操作
 *
 * @author LuckyCurve
 */
@RestController
public class ProxyCollectController {
    @Resource
    ProxyCollectService service;

    @GetMapping("/add")
    public boolean add(@RequestParam String src) throws IOException {
        return service.add(src);
    }

    @GetMapping("/list")
    public List<ProxyInfo> list() throws IOException {
        return service.list();
    }

    @GetMapping("/all")
    public String all() throws IOException {
        return service.all();
    }
}
