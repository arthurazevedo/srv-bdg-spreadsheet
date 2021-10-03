/**package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
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

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

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

        Project project = this.projectService.createProject("1", projectDTO);
        Assertions.assertNotNull(project.getName());
    }

    @Test
    public void errorCreatingProjectWithInvalidPrice() {
        List<ItemDTO> defaultListItems = ReturnDefaultObjectsUtil.returnListItemDTO1();
        ProjectDTO projectDTO = ReturnDefaultObjectsUtil.returnProjectDTO1(defaultListItems, new Double(500));

        when(this.saleRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(this.saleRepository.findById(service.getId())).thenReturn(Optional.of(service));

        Assertions.assertThrows(SpreadsheetApplicationException.class, () -> {
            this.projectService.createProject("1", projectDTO);
        });

        try {
            this.projectService.createProject("1", projectDTO);
        } catch (SpreadsheetApplicationException e) {
            Assertions.assertEquals(ErrorMessageProject.ERROR_MESSAGE_NOT_MATCHING_PRICE, e.getMessage());
        }
    }

}**/
