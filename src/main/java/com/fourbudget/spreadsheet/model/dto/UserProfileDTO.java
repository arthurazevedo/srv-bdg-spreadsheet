package com.fourbudget.spreadsheet.model.dto;

import com.fourbudget.spreadsheet.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    @NotNull
    private Long id;

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    public UserProfileDTO(UserProfile userProfile) {
        this.name = userProfile.getName();
        this.email = userProfile.getEmail();
        this.id = userProfile.getId();
    }
}
