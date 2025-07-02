package com.microservice.auth.client;

import com.microservice.auth.dto.UserInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="microservice-user", url="http://localhost:8082")
public interface UserClient {
    @GetMapping("/api/users/by-email")
    UserInfoDTO getByEmail(@RequestParam("email") String email);
}
