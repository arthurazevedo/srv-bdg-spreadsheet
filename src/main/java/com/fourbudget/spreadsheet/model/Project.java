package com.fourbudget.spreadsheet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private boolean isFinished;

    private String email;

    private Double price;

    private Double discount;

    @Column(nullable = false)
    private Long userId;

    @CreatedDate
    @Column(length = 1000)
    @JsonProperty("created_at")
    protected Date createdAt;

    @CreatedBy
    @JsonProperty("created_by")
    protected Long createdBy;

    @OneToMany(targetEntity=Item.class, fetch= FetchType.EAGER)
    private List<Item> itemsList;

    public Project(Long userId, List<Item> itemsList, String email, String name, Double price, Double discount) {
        this.userId = userId;
        this.itemsList = itemsList;
        this.email = email;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }
}
