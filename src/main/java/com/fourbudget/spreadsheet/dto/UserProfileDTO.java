package com.fourbudget.spreadsheet.dto;

import com.fourbudget.spreadsheet.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    private String name;
    private String email;
//    private String spreadSheetLink;

    public UserProfileDTO(UserProfile userProfile){
        this.name = userProfile.getName();
        this.email = userProfile.getEmail();

    }
}
