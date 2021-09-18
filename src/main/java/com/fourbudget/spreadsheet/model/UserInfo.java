package com.fourbudget.spreadsheet.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {
    private String email;
    private String id;
    private String name;
}
