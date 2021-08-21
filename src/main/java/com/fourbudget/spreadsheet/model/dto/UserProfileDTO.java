package com.fourbudget.spreadsheet.model.dto;

import com.fourbudget.spreadsheet.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    private Long id;
    private String name;
    private String email;

    public UserProfileDTO(UserProfile userProfile){
        this.name = userProfile.getName();
        this.email = userProfile.getEmail();
        this.id = userProfile.getId();
    }
}
