package com.fourbudget.spreadsheet.model;

import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "items")
@ToString
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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
