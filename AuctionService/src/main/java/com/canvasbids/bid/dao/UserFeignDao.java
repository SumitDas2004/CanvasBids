package com.canvasbids.bid.dao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Repository
@FeignClient(name="USER")
public interface UserFeignDao {

    @GetMapping("/nameAndPicture/{username}")
    Map<String, String> getNameAndPicture(@PathVariable("username") String username);

}
