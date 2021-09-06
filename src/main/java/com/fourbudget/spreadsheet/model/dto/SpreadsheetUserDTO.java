package com.fourbudget.spreadsheet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpreadsheetUserDTO {

    @NotNull
    @NotEmpty
    private String spreadsheetLink;

    @NotNull
    private Long userId;
}
