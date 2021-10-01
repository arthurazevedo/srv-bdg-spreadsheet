package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ServicesService {

    private final ServicesRepository servicesRepository;


    public List<Services> getServicosList(String userId) {
        Optional<List<Services>> servicesOptional = this.servicesRepository.findByUserId(userId);

        List<Services> services = servicesOptional.orElseThrow(() -> new SpreadsheetApplicationException(HttpStatus.NO_CONTENT, ErrorMessageService.ERROR_SERVICES_NOT_FOUND));

        if (services.isEmpty()) {
            throw new SpreadsheetApplicationException(HttpStatus.NO_CONTENT, ErrorMessageService.ERROR_SERVICES_NOT_FOUND);
        }

        return services;
    }
}
