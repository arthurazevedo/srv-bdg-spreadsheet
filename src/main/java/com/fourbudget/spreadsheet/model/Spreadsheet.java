package com.fourbudget.spreadsheet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "spreadsheets")
public class Spreadsheet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 200)
    private String spreadsheetLink;

    public Spreadsheet(String link) {
        this.spreadsheetLink = link;
    }
}
