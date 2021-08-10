package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.config.GoogleAuthorizeUtil;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/{id}")
    public String getTable(@PathVariable("id") String id) throws IOException, GeneralSecurityException {
        ValueRange response = GoogleAuthorizeUtil.getSheetsService().spreadsheets().values().get(id, "A:Z").execute();
        return response.getValues().toString();
    }
}
