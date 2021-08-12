package com.fourbudget.spreadsheet.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

@RestController()
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity health(){
        return ResponseEntity.ok().body(new Date().toString());
    }

}
