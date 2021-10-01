package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.ProjectRepository;
import com.fourbudget.spreadsheet.repository.SaleRepository;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProduct;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProductOrService;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProject;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjectService {

    private final ItemRepository itemRepository;
    private final ProjectRepository projectRepository;
    private final SaleRepository saleRepository;

    public Project createProject(String userId, ProjectDTO projectDTO) {
        List<ItemDTO> itemsDTOList = projectDTO.getListItemDTO();
        List<Item> itemsList = this.fillItemsList(itemsDTOList);

        Project project = new Project(userId, itemsList, projectDTO.getEmail(), projectDTO.getName(), projectDTO.getPrice(), projectDTO.getDiscount());
        this.projectRepository.save(project);
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

    public Project updateProject(Long projectId, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new SpreadsheetApplicationException(HttpStatus.NOT_FOUND, ErrorMessageProject.ERROR_MESSAGE_PROJECT_DOESNT_EXIST));

        List<Item> items = fillItemsList(projectDTO.getListItemDTO());
        project.updateProject(items, projectDTO);
        projectRepository.save(project);

        return project;
    }

    public Project getProject(Long projectId) {
        Optional<Project> projectOptional = this.projectRepository.findById(projectId);

        if (!projectOptional.isPresent()) {
            throw new SpreadsheetApplicationException(HttpStatus.OK, ErrorMessageProduct.ERROR_PRODUCTS_NOT_FOUND);
        }

        return projectOptional.orElseThrow(() -> new SpreadsheetApplicationException(HttpStatus.NO_CONTENT, ErrorMessageProduct.ERROR_PRODUCTS_NOT_FOUND));
    }

    public Project finishProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new SpreadsheetApplicationException(HttpStatus.NOT_FOUND, ErrorMessageProject.ERROR_MESSAGE_PROJECT_DOESNT_EXIST));

        project.finishProject();
        projectRepository.save(project);

        return project;
    }
}
