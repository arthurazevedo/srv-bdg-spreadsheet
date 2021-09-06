package com.fourbudget.spreadsheet.config.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormError {
    private String field;
    private String message;

    public FormError(String message) {
        this.field = "";
        this.message = message;
    }
}