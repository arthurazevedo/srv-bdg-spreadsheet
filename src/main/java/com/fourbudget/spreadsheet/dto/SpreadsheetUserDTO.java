package com.fourbudget.spreadsheet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpreadsheetUserDTO {

    private String spreadsheetLink;
    private Long userId;
}
