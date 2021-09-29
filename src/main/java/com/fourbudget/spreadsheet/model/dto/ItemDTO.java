package com.fourbudget.spreadsheet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class ItemDTO {

    @NotNull
    private Long saleId;

    @NotNull
    private int quantity;
}
