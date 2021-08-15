package com.fourbudget.spreadsheet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "spreadsheet_from_user_profile")
public class SpreadsheetFromUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long relationId;

    @Column(unique = true, nullable = false)
    private Long userProfileId;

    @NotNull
    @Column(unique = true)
    private Long spreadsheetId;

    public SpreadsheetFromUser(Long idProfileUser, Long spreadsheetId) {
        this.userProfileId = idProfileUser;
        this.spreadsheetId = spreadsheetId;
    }
}
