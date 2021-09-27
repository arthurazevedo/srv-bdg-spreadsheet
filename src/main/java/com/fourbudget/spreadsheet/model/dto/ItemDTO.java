package com.fourbudget.spreadsheet.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemDTO {

    @NotNull
    private Long saleId;

    @NotNull
    private int quantity;
}
