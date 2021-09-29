package com.fourbudget.spreadsheet.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "my_services")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Services extends Sale {

    private String phone;
    private String type;
    private String address;

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
