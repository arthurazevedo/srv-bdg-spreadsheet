package com.fourbudget.spreadsheet.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return getQuantity() == item.getQuantity() && getSale().equals(item.getSale());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSale(), getQuantity());
    }
}
