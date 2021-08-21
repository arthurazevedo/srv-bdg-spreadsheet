package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.service.SpreadsheetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/spreadsheet")
public class SpreadsheetController {

    private final SpreadsheetService spreadsheetService;

    @PostMapping
    public ResponseEntity<Void> postSpreadsheetLink(@RequestBody SpreadsheetUserDTO spreadsheetUserDto){
        try {
            this.spreadsheetService.registerSpreadsheetLink(spreadsheetUserDto);
        } catch (NoSuchElementException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/health")
    public ResponseEntity health(){
        return ResponseEntity.ok().body(new Date().toString());
    }
}
