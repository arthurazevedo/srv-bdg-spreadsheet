package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.ProjectRepository;
import com.fourbudget.spreadsheet.repository.SaleRepository;
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

    private final String ERROR_MESSAGE_PRODUCT_NOT_FOUND = "Product not found";
    private final String ERROR_MESSAGE_PRODUCT_SERVICE_NOT_EXISTS= "Service or Product doesnt exists.";

    public Project createProject(Long userId, ProjectDTO projectDTO) {
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

            Sale sale = this.saleRepository.findById(saleId).orElseThrow(() -> new MySystemException(HttpStatus.NOT_FOUND, ERROR_MESSAGE_PRODUCT_SERVICE_NOT_EXISTS));
            item.setItem(sale, quantity);

            this.itemRepository.save(item);
            itemsList.add(item);
        }

        return itemsList;
    }

    public Project updateProject(Long id, ProjectDTO projectDTO) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new MySystemException(HttpStatus.NOT_FOUND, "Project dont exists"));

        List<Item> itens = fillItemsList(projectDTO.getListItemDTO());

        project.setItemsList(itens);

        projectRepository.save(project);

        return project;
    }

    public Project getProject(Long projectId){
        Optional<Project> projectOptional = this.projectRepository.findById(projectId);

        if(!projectOptional.isPresent()){
            throw new MySystemException(HttpStatus.OK, ERROR_MESSAGE_PRODUCT_NOT_FOUND);
        }

        return projectOptional.orElseThrow(() -> new MySystemException(HttpStatus.NO_CONTENT, ERROR_MESSAGE_PRODUCT_NOT_FOUND));
    }
}
