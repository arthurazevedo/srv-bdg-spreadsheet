package com.fourbudget.spreadsheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    private UserProfile userProfile;

    @OneToOne(fetch = FetchType.EAGER)
    private Spreadsheet spreadsheet;

    public SpreadsheetFromUser(UserProfile userProfile, Spreadsheet spreadsheet) {
        this.userProfile = userProfile;
        this.spreadsheet = spreadsheet;
    }
}
