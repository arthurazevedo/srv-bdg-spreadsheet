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
import com.fourbudget.spreadsheet.util.ReturnDefaultObjectsUtil;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageProject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    @MockBean
    private ProjectRepository projectRepository;

    @Autowired
    @MockBean
    private SaleRepository saleRepository;

    private Sale product;
    private Sale service;

    @Before
    public void init() {
        this.product = ReturnDefaultObjectsUtil.returnSaleProduct();
        this.service = ReturnDefaultObjectsUtil.returnSaleService();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void successfullyCreateProject() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(1110));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));

        Project project = this.projectService.createProject(new Long(1), projectDTO);
        Assertions.assertNotNull(project.getName());
    }

    @Test
    public void errorCreatingProjectWithInvalidPrice() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(500));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));

        Assertions.assertThrows(SpreadsheetApplicationException.class, () -> {
            this.projectService.createProject(new Long(1), projectDTO);
        });

        try {
            this.projectService.createProject(new Long(1), projectDTO);
        } catch (SpreadsheetApplicationException e) {
            Assertions.assertEquals(ErrorMessageProject.ERROR_MESSAGE_NOT_MATCHING_PRICE, e.getMessage());
        }
    }

    @Test
    public void successfullyUpdateProduct() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(1110));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));

        Project project1 = this.projectService.createProject(new Long(1), projectDTO);
        when(this.projectRepository.findById(any(Long.class))).thenReturn(Optional.of(project1));
        List<Item> oldItemsList = project1.getItemsList();

        ProjectDTO projectDTO2 = ReturnDefaultObjectsUtil.returnProjectDTO2(new Double(4220));
        this.projectService.updateProject(new Long(50), projectDTO2);

        Assertions.assertNotEquals(project1.getItemsList(), oldItemsList);
    }

    @Test
    public void errorUpdatingProjectWithInvalidPrice() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(1110));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));
        Project project1 = this.projectService.createProject(new Long(1), projectDTO);
        when(this.projectRepository.findById(any(Long.class))).thenReturn(Optional.of(project1));

        ProjectDTO projectDTO2 = ReturnDefaultObjectsUtil.returnProjectDTO2(new Double(5000));

        Assertions.assertThrows(SpreadsheetApplicationException.class, () -> {
            this.projectService.updateProject(new Long(50), projectDTO2);
        });

        try {
            this.projectService.updateProject(new Long(50), projectDTO2);
        } catch (SpreadsheetApplicationException e) {
            Assertions.assertEquals(ErrorMessageProject.ERROR_MESSAGE_NOT_MATCHING_PRICE, e.getMessage());
        }
    }

    @Test
    public void successfullyFinishingProject() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(1110));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));

        Project project1 = this.projectService.createProject(new Long(1), projectDTO);
        when(this.projectRepository.findById(any(Long.class))).thenReturn(Optional.of(project1));
        boolean oldIsFinished = project1.isFinished();

        this.projectService.finishProject(new Long(10));

        Assertions.assertNotEquals(project1.isFinished(), oldIsFinished);
    }

}
