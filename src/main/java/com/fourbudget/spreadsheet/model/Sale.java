package com.fourbudget.spreadsheet.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected Long userId;

    @Column(length = 50, nullable = false)
    protected String name;

    protected String code;

    @Column(length = 200)
    protected String description;

    protected String imageUrl;

    protected boolean isFavorite;

    @Column(nullable = false)
    protected Double price;

    protected String path;

    @CreatedDate
    protected Date createdAt;

    @CreatedBy
    protected Long createdBy;
}
