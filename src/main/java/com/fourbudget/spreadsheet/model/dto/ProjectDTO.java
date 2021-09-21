package com.fourbudget.spreadsheet.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long userId;
    private List<ItemDTO> listItemDTO;
    private String name;
    private String email;
    private Double price;
    private Double discount;
}
