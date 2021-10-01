package com.fourbudget.spreadsheet.model.dto;

import com.fourbudget.spreadsheet.model.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    @NotNull
    private String id;

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
