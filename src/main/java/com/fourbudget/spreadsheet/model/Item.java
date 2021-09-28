package com.fourbudget.spreadsheet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "items")
@ToString
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Sale sale;

    private int quantity;

    public void setItem(Sale sale, int quantity) {
        this.sale = sale;
        this.quantity = quantity;
    }

    public Double getItemPrice() {
        Double totalPrice = this.sale.getPrice() * this.quantity;
        return totalPrice;
    }
}
