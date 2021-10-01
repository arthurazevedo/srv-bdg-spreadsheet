package com.fourbudget.spreadsheet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "db_projects")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @CreatedDate
    @Column(length = 1000)
    @JsonProperty("created_at")
    protected Date createdAt;
    @CreatedBy
    @JsonProperty("created_by")
    protected Long createdBy;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "project_name")
    private String name;
    private boolean isFinished;
    @Column(name = "project_email")
    private String email;
    private Double price;
    private Double discount;
    @Column(nullable = false)
    private String userId;
    @OneToMany(targetEntity = Item.class, fetch = FetchType.EAGER)
    private List<Item> itemsList;

    public Project(String userId, List<Item> itemsList, String email, String name, Double price, Double discount) {
        this.validatePrice(itemsList, price);
        this.itemsList = itemsList;
        this.price = price;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.discount = discount;
        this.isFinished = false;
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

    public void updateProject(List<Item> itemsList, ProjectDTO projectDTO) {
        this.validatePrice(itemsList, projectDTO.getPrice());
        this.price = projectDTO.getPrice();
        this.name = projectDTO.getName();
        this.itemsList = itemsList;
        this.email = projectDTO.getEmail();
        this.discount = projectDTO.getDiscount();
    }

    public Double getPriceWithDiscount() {
        Double priceWithDiscount = this.price * ((100 - this.discount) / 100);
        return priceWithDiscount;
    }

    public void finishProject() {
        this.isFinished = true;
    }
}
