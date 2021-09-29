package com.fourbudget.spreadsheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "db_su_relations")
public class SpreadsheetFromUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long relationId;

    @OneToOne(fetch = FetchType.EAGER)
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.EAGER)
    private Spreadsheet spreadsheet;

    public SpreadsheetFromUser(UserProfile userProfile, Spreadsheet spreadsheet) {
        this.userProfile = userProfile;
        this.spreadsheet = spreadsheet;
    }
}
