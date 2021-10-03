package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.SaleRepository;
import com.fourbudget.spreadsheet.util.EmailSender;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProductOrService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProjectService {

    private final ItemRepository itemRepository;
    private final SaleRepository saleRepository;

    @Autowired
    EmailSender emailSender;

    public Project createProject(ProjectDTO projectDTO) {
        List<ItemDTO> itemsDTOList = projectDTO.getListItemDTO();
        List<Item> itemsList = this.fillItemsList(itemsDTOList);

        Project project = new Project(itemsList, projectDTO.getEmail(), projectDTO.getName(), projectDTO.getPrice(), projectDTO.getDiscount());

        emailSender.sendEmail(project);

        return project;
    }

    private List<Item> fillItemsList(List<ItemDTO> itemsDTOList) {
        List<Item> itemsList = new ArrayList<>();

        for (ItemDTO itemDTO : itemsDTOList) {
            Long saleId = itemDTO.getSaleId();
            int quantity = itemDTO.getQuantity();
            Item item = new Item();

            Sale sale = this.saleRepository.findById(saleId)
                    .orElseThrow(() -> new SpreadsheetApplicationException(HttpStatus.NOT_FOUND, ErrorMessageProductOrService.ERROR_MESSAGE_PRODUCT_SERVICE_DOES_NOT_EXIST));

            item.setItem(sale, quantity);
            this.itemRepository.save(item);
            itemsList.add(item);
        }

        return itemsList;
    }
}
