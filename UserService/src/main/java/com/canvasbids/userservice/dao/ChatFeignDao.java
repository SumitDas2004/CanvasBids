package com.canvasbids.userservice.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("CHAT")
public interface ChatFeignDao {

    @PostMapping("/create")
    void createChat(@RequestBody Map<String, String> map);
}
