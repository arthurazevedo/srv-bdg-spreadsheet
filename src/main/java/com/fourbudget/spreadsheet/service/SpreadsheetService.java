package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.GoogleAuthorizeUtil;
import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SpreadsheetService {

    private final UserProfileRepository userProfileRepository;
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;
    private final ProductRepository productRepository;

    public SpreadsheetFromUser registerSpreadsheetLink(SpreadsheetUserDTO spreadsheetUserDTO) throws IOException, GeneralSecurityException {
        Long idProfileUser = spreadsheetUserDTO.getUserId();
        String link = spreadsheetUserDTO.getSpreadsheetLink();
        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if (!optUserProfile.isPresent()) {
            throw new NoSuchElementException("User doesn't exist");
        }

        UserProfile userProfile = optUserProfile.get();
        Optional<SpreadsheetFromUser> optSuRelation = this.spreadsheetFromUserRepository.findByUserProfileId(idProfileUser);
        SpreadsheetFromUser suRelation;
        Spreadsheet spreadsheet;
        if (!optSuRelation.isPresent()) {
            spreadsheet = new Spreadsheet(link);
            this.spreadsheetRepository.save(spreadsheet);
            suRelation = new SpreadsheetFromUser(userProfile, spreadsheet);
        } else {
            suRelation = optSuRelation.get();
            spreadsheet = suRelation.getSpreadsheet();
            spreadsheet.setSpreadsheetLink(link);
            this.spreadsheetRepository.save(spreadsheet);
        }

        this.spreadsheetFromUserRepository.save(suRelation);

        populate(spreadsheet.getSpreadsheetLink(), idProfileUser);

        return suRelation;
    }

    public List<SpreadsheetFromUser> findAllSURelations() {
        return this.spreadsheetFromUserRepository.findAll();
    }

    public void populate(String spreadsheet, Long userId) throws IOException, GeneralSecurityException {
        Sheets sheets = GoogleAuthorizeUtil.getSheetsService();
        final String range = "A:Z";

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheet, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            throw new IOException("Spreadsheet is empty");
        } else {
            for (int i = 1; i < values.size(); i++) {
                List<Object> column = values.get(i);
                Product product = new Product(userId, column.get(0).toString(), Double.parseDouble(column.get(1).toString()), column.get(2).toString());
                this.productRepository.save(product);
            }
        }
    }

}
