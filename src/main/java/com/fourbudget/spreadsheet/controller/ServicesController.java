package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.service.ServicesService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/services")
public class ServicesController {

    private final ServicesService servicesService;

    @GetMapping("/user/{userId}")
    public List<Services> getServices(@PathVariable String userId) {
        return this.servicesService.getServicosList(userId);
    }
}
