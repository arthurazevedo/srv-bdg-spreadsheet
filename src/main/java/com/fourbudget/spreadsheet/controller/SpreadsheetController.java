package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.service.SpreadsheetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;


@RestController
@AllArgsConstructor
@RequestMapping("/spreadsheet")
public class SpreadsheetController {

    private final SpreadsheetService spreadsheetService;

    @PostMapping
    public ResponseEntity<SpreadsheetFromUser> postSpreadsheetLink(@RequestBody @Valid SpreadsheetUserDTO spreadsheetUserDto) throws GeneralSecurityException, IOException {
        SpreadsheetFromUser suRelation = this.spreadsheetService.registerSpreadsheetLink(spreadsheetUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(suRelation);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Spreadsheet> getSpreadSheetByUserId(@PathVariable Long userId) {
        Spreadsheet spreadsheet = this.spreadsheetService.findByUserId(userId);
        return new ResponseEntity<>(spreadsheet, HttpStatus.OK);
    }
}
