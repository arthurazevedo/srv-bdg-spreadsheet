package com.fourbudget.spreadsheet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@ToString
public abstract class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    protected Long id;

    @Column(nullable = false)
    @JsonProperty("user_id")
    protected Long userId;

    @Column(length = 50, nullable = false)
    @JsonProperty("name")
    protected String name;

    @JsonProperty("code")
    protected String code;

    @Column(length = 200)
    @JsonProperty("description")
    protected String description;

    @Column(length = 200)
    @JsonProperty("image_url")
    protected String imageUrl;

    @JsonProperty("is_favorite")
    protected boolean isFavorite;

    @Column(nullable = false)
    @JsonProperty("price")
    protected Double price;

    @JsonProperty("path")
    protected String path;

    @CreatedDate
    @Column(length = 1000)
    @JsonProperty("created_at")
    protected Date createdAt;

    @CreatedBy
    @JsonProperty("created_by")
    protected Long createdBy;
}
