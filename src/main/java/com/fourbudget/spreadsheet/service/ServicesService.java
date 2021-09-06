package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ServicesService {

    private final ServicesRepository servicesRepository;

    private final String ERROR_SERVICES_NOT_FOUND = "Services not found.";

    public List<Services> getServicosList(Long userId) {
        Optional<List<Services>> servicesOptional = this.servicesRepository.findByUserId(userId);

        List<Services> services = servicesOptional.orElseThrow(() -> new MySystemException(HttpStatus.NO_CONTENT, ERROR_SERVICES_NOT_FOUND));

        if (services.isEmpty()) {
           throw new MySystemException(HttpStatus.NO_CONTENT, ERROR_SERVICES_NOT_FOUND);
        }

        return services;
    }
}
