package com.fourbudget.spreadsheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(targetEntity=Item.class, fetch=FetchType.EAGER)
    private List<Item> itemsList;

    public Project(Long userId, ArrayList<Item> itemsList) {
        this.userId = userId;
        this.itemsList = itemsList;
    }
}
