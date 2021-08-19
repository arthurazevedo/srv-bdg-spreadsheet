package com.fourbudget.spreadsheet.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "su_relations")
public class SpreadsheetFromUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long relationId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userProfileId")
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spreadsheetId")
    private Spreadsheet spreadsheet;

    public SpreadsheetFromUser(UserProfile userProfile, Spreadsheet spreadsheet) {
        this.userProfile = userProfile;
        this.spreadsheet = spreadsheet;
    }
}
