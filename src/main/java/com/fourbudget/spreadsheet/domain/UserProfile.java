package com.fourbudget.spreadsheet.domain;

import com.fourbudget.spreadsheet.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50, nullable = false)
    private String name;

    @NotNull
    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @Column(length = 200)
    private String spreadsheetLink;

    public UserProfile(UserProfileDTO upDto){
        this.name = upDto.getName();
        this.email = upDto.getEmail();
    }

}
