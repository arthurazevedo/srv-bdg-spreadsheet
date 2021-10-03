package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import com.fourbudget.spreadsheet.service.ServicesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicesControllerTest {

    private String userId;

    private List<Services> servicesList;

    private Services service;

    @Mock
    private ServicesController servicesController;

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
        servicesController = new ServicesController(servicesService);
        servicesList.add(service);
    }

    @Test
    public void getServices(){
        Mockito.when(servicesRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(servicesList));
        List<Services> returned = servicesController.getServices(userId);
        Assert.assertEquals(1,returned.size());
        Assert.assertEquals(service,returned.get(0));
    }
}
