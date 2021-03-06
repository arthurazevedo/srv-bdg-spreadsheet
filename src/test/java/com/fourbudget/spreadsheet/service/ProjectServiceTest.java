package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Sale;
import com.fourbudget.spreadsheet.model.dto.ItemDTO;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ItemRepository;
import com.fourbudget.spreadsheet.repository.SaleRepository;
import com.fourbudget.spreadsheet.util.EmailSender;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProjectServiceTest {

    private ProjectService projectService;

    private ProjectDTO projectDTO;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private Sale sale;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        projectService = new ProjectService(itemRepository, saleRepository, emailSender);

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
    public void successfullyCreateProject() {
        when(saleRepository.findById(Mockito.any())).thenReturn(Optional.of(sale));
        when(itemRepository.save(Mockito.any())).thenReturn(null);
        doNothing().when(emailSender).sendEmail(Mockito.any());

        Project project = projectService.createProject(projectDTO);

        Assertions.assertNotNull(project);
    }

    @Test
    public void errorCreatingProjectWithInvalidPrice() {
        expectedException.expect(SpreadsheetApplicationException.class);
        when(saleRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Project project = projectService.createProject(projectDTO);

        Assertions.assertNotNull(project);
    }

}
