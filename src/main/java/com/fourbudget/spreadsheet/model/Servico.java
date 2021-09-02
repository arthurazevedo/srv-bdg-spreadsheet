package com.fourbudget.spreadsheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
@ToString
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long userId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(length = 200)
    private String description;

    public Servico(Long userId, String name, Double price, String description) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.description = description;
    }


}
