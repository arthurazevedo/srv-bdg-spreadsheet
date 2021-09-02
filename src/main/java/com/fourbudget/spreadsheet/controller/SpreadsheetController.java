package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
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
    public ResponseEntity<SpreadsheetFromUser> postSpreadsheetLink(@RequestBody SpreadsheetUserDTO spreadsheetUserDto) {
        SpreadsheetFromUser suRelation = null;
        try {
            suRelation = this.spreadsheetService.registerSpreadsheetLink(spreadsheetUserDto);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } finally {
            return new ResponseEntity(suRelation, HttpStatus.CREATED);
        }

    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<Spreadsheet> getSpreadSheetByUserId(@PathVariable Long userId) {
        Spreadsheet spreadsheet = this.spreadsheetService.findByUserId(userId);
        return new ResponseEntity<>(spreadsheet, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity health() {
        return ResponseEntity.ok().body(new Date().toString());
    }
}
