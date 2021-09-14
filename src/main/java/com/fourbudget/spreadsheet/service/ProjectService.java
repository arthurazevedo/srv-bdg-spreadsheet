package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import com.fourbudget.spreadsheet.repository.ProjectRepository;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProjectService {

    private final ProductRepository productRepository;
    private final ServicesRepository servicesRepository;
    private final ItemRepository itemRepository;
    private final ProjectRepository projectRepository;

    private final String ERROR_MESSAGE_SERVICE_NOT_FOUND = "Service not found";
    private final String ERROR_MESSAGE_PRODUCT_NOT_FOUND = "Product not found";
    private final String ERROR_MESSAGE_INVALID_QUANTITY = "Item quantity is invalid";

    public Project createProject(Long userId, ProjectDTO projectDTO) {
        ArrayList<ItemDTO> itemsDTOList = (ArrayList<ItemDTO>) projectDTO.getListItemDTO();
        ArrayList<Item> itemsList = this.fillItemsList(itemsDTOList);
        Project project = new Project(userId, itemsList);
        this.projectRepository.save(project);
        return project;
    }

    private ArrayList<Item> fillItemsList(ArrayList<ItemDTO> itemsDTOList) {
        ArrayList<Item> itemsList = new ArrayList<>();

        for (ItemDTO itemDTO : itemsDTOList) {
            Long saleId = itemDTO.getSaleId();
            int quantity = itemDTO.getQuantity();
            Item item = new Item();

            if (quantity == 0) {
                Optional<Services> optService = this.servicesRepository.findById(saleId);
                Services service = validateAndReturnService(optService);
                item.setItem(service);
            } else if (quantity > 0) {
                Optional<Product> optProduct = this.productRepository.findById(saleId);
                Product product = validateAndReturnProduct(optProduct);
                item.setItem(product, quantity);
            } else {
                throw new MySystemException(HttpStatus.OK, ERROR_MESSAGE_INVALID_QUANTITY);
            }
            this.itemRepository.save(item);
        }

        return itemsList;
    }

    private Product validateAndReturnProduct(Optional<Product> optProduct) {
        if (!optProduct.isPresent()) {
            throw new MySystemException(HttpStatus.OK, ERROR_MESSAGE_PRODUCT_NOT_FOUND);
        }
        Product product = optProduct.get();
        return product;
    }

    private Services validateAndReturnService(Optional<Services> optService) {
        if (!optService.isPresent()) {
            throw new MySystemException(HttpStatus.OK, ERROR_MESSAGE_SERVICE_NOT_FOUND);
        }
        Services service = optService.get();
        return service;
    }
}
