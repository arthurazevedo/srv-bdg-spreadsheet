package com.fourbudget.spreadsheet.gateway;

import com.fourbudget.spreadsheet.annotations.NoAuth;
import com.fourbudget.spreadsheet.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "${google.api}" , name= "google.api")
public interface GoogleGateway {

    @NoAuth
    @GetMapping("/userinfo?access_token={token}")
    UserInfo autenticar(@PathVariable("token") String token);
}