package com.fourbudget.spreadsheet.model;

import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_users_profiles")
public class UserProfile {

    @Id
    private String id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String name;

    @Column(name = "user_email", length = 50, unique = true, nullable = false)
    private String email;

    public UserProfile(UserProfileDTO upDto) {
        this.name = upDto.getName();
        this.email = upDto.getEmail();
        this.id = upDto.getId();
    }

}
