package com.fourbudget.spreadsheet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    private String name;

    private String email;

    private Double price;

    private Double discount;

    private List<Item> itemsList;

    private Double totalPrice;

    public Project(List<Item> itemsList, String email, String name, Double price, Double discount) {
        this.validatePrice(itemsList, price);
        this.itemsList = itemsList;
        this.price = price;
        this.email = email;
        this.name = name;
        this.discount = discount;
        this.totalPrice = getPriceWithDiscount();
    }

    public void validatePrice(List<Item> itemsList, Double expectedPrice) {
        Double actualPrice = new Double(0);
        for (Item item : itemsList) {
            actualPrice += item.getItemPrice();
        }

        if (!actualPrice.equals(expectedPrice)) {
            throw new SpreadsheetApplicationException(HttpStatus.OK, ErrorMessageProject.ERROR_MESSAGE_NOT_MATCHING_PRICE);
        }
    }

    @JsonIgnore
    private Double getPriceWithDiscount() {
        return this.price - this.discount;
    }
}
