package com.canvasbids.gateway.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER")
public interface UserFeignDao {
    @GetMapping("/isValid/{token}")
    boolean isValid(@PathVariable("token") String token);

}
