package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ServicesServiceTest {

    private String userId;

    private List<Services> servicesList;

    private Services service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ServicesService servicesService;

    @Mock
    private ServicesRepository servicesRepository;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        servicesList = new ArrayList<>();
        service = new Services();
        userId = "1";
        service.setUserId(userId);
        servicesService = new ServicesService(servicesRepository);
        servicesList.add(service);
    }

    @Test
    public void sucessfullyGetServicosList(){
        Mockito.when(servicesRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(servicesList));
        List<Services> returned = servicesService.getServicosList(userId);
        Assert.assertEquals(1,returned.size());
        Assert.assertEquals(service,returned.get(0));
    }

    @Test
    public void errorGetServicosList(){
        expectedException.expect(SpreadsheetApplicationException.class);
        Mockito.when(servicesRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(new ArrayList<Services>()));
        servicesService.getServicosList(userId);
    }

    @Test
    public void errorGetServicosListEmpty(){
        expectedException.expect(SpreadsheetApplicationException.class);
        Mockito.when(servicesRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());
        servicesService.getServicosList(userId);
    }
}
