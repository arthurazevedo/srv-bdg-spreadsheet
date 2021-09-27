package com.fourbudget.spreadsheet.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectDTO {

    @NotNull
    private Long userId;

    @NotNull
    @NotEmpty
    private List<ItemDTO> listItemDTO;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Double price;

    @NotNull
    private Double discount;
}
