package com.fourbudget.spreadsheet.util;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

public class ReturnDefaultObjectsUtil {

    public static Sale returnSaleProduct() {
        Product product = new Product();

        product.setId(new Long(10));
        product.setUserId("1");
        product.setName("Escova");
        product.setCode("1000");
        product.setDescription("dental");
        product.setImageUrl("https://random-imgs.com/12312");
        product.setFavorite(false);
        product.setPrice(new Double(22));
        product.setPath("Higiene");

        return product;
    }

    public static Sale returnSaleService() {
        Services service = new Services();

        service.setAddress("R. Eguinacio Orocio Araujo, 3331");
        service.setType("Segurança");
        service.setPhone("9999-8888");

        service.setId(new Long(20));
        service.setUserId("1");
        service.setName("Escolta");
        service.setCode("1049");
        service.setDescription("Escolta de onibus");
        service.setImageUrl("https://random-imgs.com/19978");
        service.setFavorite(true);
        service.setPrice(new Double(500));
        service.setPath("Segurança");

        return service;
    }

    public static ProjectDTO returnProjectDTO1(List<ItemDTO> listItemDTO, Double price) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setUserId("1");
        projectDTO.setListItemDTO(listItemDTO);
        projectDTO.setName("Padrão");
        projectDTO.setEmail("test@test.com");
        projectDTO.setPrice(price);
        projectDTO.setDiscount(new Double(10));

        return projectDTO;
    }

    public static ProjectDTO returnProjectDTO2(Double price) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setUserId("1");
        projectDTO.setListItemDTO(returnListItemDTO2());
        projectDTO.setName("Alternativo");
        projectDTO.setEmail("test@test.com");
        projectDTO.setPrice(price);
        projectDTO.setDiscount(new Double(10));

        return projectDTO;
    }

    public static List<ItemDTO> returnListItemDTO1() {
        ItemDTO itemDTO1 = new ItemDTO(new Long(10), 5);
        ItemDTO itemDTO2 = new ItemDTO(new Long(20), 2);

        List<ItemDTO> itemsDTO = new ArrayList<>();
        itemsDTO.add(itemDTO1);
        itemsDTO.add(itemDTO2);

        return itemsDTO;
    }

    private static List<ItemDTO> returnListItemDTO2() {
        ItemDTO itemDTO1 = new ItemDTO(new Long(10), 10);
        ItemDTO itemDTO2 = new ItemDTO(new Long(20), 8);

        List<ItemDTO> itemsDTO = new ArrayList<>();
        itemsDTO.add(itemDTO1);
        itemsDTO.add(itemDTO2);

        return itemsDTO;
    }
}
