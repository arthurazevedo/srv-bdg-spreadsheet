package com.fourbudget.spreadsheet.model;

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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 50, nullable = false)
    private String name;

    private String code;

    @Column(length = 200)
    private String description;

    private String imageUrl;

    private boolean isFavorite;

    @Column(nullable = false)
    private Double price;

    private String path;

    private String phone;

    private String type;

    private String address;

    @CreatedDate
    private Date createdAt;

    @CreatedBy
    private Long createdBy;

    public Services(Long userId, String name, String code, String description, String imageUrl, boolean isFavorite, Double price, String path, String phone, String type, String address) {
        this.userId = userId;
        this.name = name;
        this.code = code;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
        this.price = price;
        this.path = path;
        this.phone = phone;
        this.type = type;
        this.address = address;
    }
}
