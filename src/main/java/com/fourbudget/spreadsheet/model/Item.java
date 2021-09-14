package com.fourbudget.spreadsheet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "items")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private int quantity;

    public void setItem(Sale sale, int quantity) {
        this.sale = sale;
        this.quantity = quantity;
    }
    public void setItem(Sale sale) {
        this.sale = sale;
        this.quantity = 0;
    }
}
