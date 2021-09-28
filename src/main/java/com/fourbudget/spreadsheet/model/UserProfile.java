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
public class UserProfile {

    @Id
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, unique = true, nullable = false)
    private String email;

    public UserProfile(UserProfileDTO upDto) {
        this.name = upDto.getName();
        this.email = upDto.getEmail();
        this.id = upDto.getId();
    }

}
