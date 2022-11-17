package com.zzq.sample.controller;

import com.zzq.sample.service.CompletableFutureService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Zhou Zhongqing
 * @ClassName TestController
 * @description: 测试接口
 * @date 2022-11-14 13:08
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private CompletableFutureService completableFutureService;
    @RequestMapping("/completeNotify")
    public String completeNotify(){
        return completableFutureService.completeNotify();
    }

    @RequestMapping("/asyncTask")
    public String asyncTask(){
        return completableFutureService.asyncTask();
    }

    @RequestMapping("/stream")
    public String stream(){
        return completableFutureService.stream();
    }
    @RequestMapping("/exception")
    public String exception(){
        return completableFutureService.exception();
    }



    @RequestMapping("/compose")
    public String compose(){
        return completableFutureService.compose();
    }
}
