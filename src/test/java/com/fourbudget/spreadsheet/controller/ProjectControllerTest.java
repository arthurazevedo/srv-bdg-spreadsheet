package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.SaleRepository;
import com.fourbudget.spreadsheet.service.ProjectService;
import com.fourbudget.spreadsheet.util.EmailSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProjectControllerTest {

    @Mock
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private ProjectDTO projectDTO;

    @Mock
    private Sale sale;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        projectService = new ProjectService(itemRepository, saleRepository, emailSender);
        projectController = new ProjectController(projectService);

        projectDTO = new ProjectDTO();
        List<ItemDTO> itemDTOList = new ArrayList<>();

        ItemDTO itemDTO = new ItemDTO(Long.valueOf(1), 2);
        itemDTO.setQuantity(1);

        itemDTOList.add(itemDTO);

        projectDTO.setListItemDTO(itemDTOList);
        sale.setId(Long.valueOf(1));

        projectDTO.setPrice(Double.valueOf(0));
        projectDTO.setDiscount(Double.valueOf(0));
    }

    @Test
    public void postProject() {
       when(saleRepository.findById(Mockito.any())).thenReturn(Optional.of(sale));
       when(itemRepository.save(Mockito.any())).thenReturn(null);
       doNothing().when(emailSender).sendEmail(Mockito.any());

       ResponseEntity<Project> projectResponse = projectController.postProject("1", projectDTO);

       Project project = projectResponse.getBody();

       Assertions.assertNotNull(project);
    }
}
