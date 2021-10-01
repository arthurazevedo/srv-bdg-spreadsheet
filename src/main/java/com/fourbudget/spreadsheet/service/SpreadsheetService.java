package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.GoogleAuthorizeUtil;
import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.*;
import com.fourbudget.spreadsheet.model.constants.ProductConstants;
import com.fourbudget.spreadsheet.model.constants.ServicesConstants;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.SaleRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageSpreadsheet;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.fourbudget.spreadsheet.model.constants.SpreadsheetServiceConstants.isTrue;

@AllArgsConstructor
@Service
public class SpreadsheetService {

    private final UserProfileRepository userProfileRepository;
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public SpreadsheetFromUser registerSpreadsheetLink(SpreadsheetUserDTO spreadsheetUserDTO) throws IOException, GeneralSecurityException {
        String idProfileUser = spreadsheetUserDTO.getUserId();
        String link = spreadsheetUserDTO.getSpreadsheetLink();
        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if (!optUserProfile.isPresent()) {
            throw new SpreadsheetApplicationException(HttpStatus.NO_CONTENT, ErrorMessageSpreadsheet.ERROR_USER_DOESNT_EXIST);
        }

        UserProfile userProfile = optUserProfile.get();
        Optional<SpreadsheetFromUser> optSuRelation = this.spreadsheetFromUserRepository.findOneByUserProfileId(idProfileUser);

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
            deleteUserById(idProfileUser);
            this.spreadsheetRepository.save(spreadsheet);
        }

        populate(spreadsheet.getSpreadsheetLink(), idProfileUser);

        this.spreadsheetFromUserRepository.save(suRelation);

        return suRelation;
    }

    private void deleteUserById(String idProfileUser) {
        this.saleRepository.deleteAllByUserId(idProfileUser);
    }

    public List<SpreadsheetFromUser> findAllSURelations() {
        return this.spreadsheetFromUserRepository.findAll();
    }

    public void populate(String spreadsheet, String userId) throws GeneralSecurityException, IOException {
        this.populateProducts(spreadsheet, userId, ProductConstants.PRODUCT_TAB_NAME);
        this.populateProducts(spreadsheet, userId, ServicesConstants.SERVICES_TAB_NAME);
    }

    private void populateProducts(String spreadsheet, String userId, String tab) throws IOException, GeneralSecurityException {
        Sheets sheets = GoogleAuthorizeUtil.getSheetsService();
        final String range = "'" + tab + "'!A:Z";

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheet, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            throw new SpreadsheetApplicationException(HttpStatus.BAD_REQUEST, ErrorMessageSpreadsheet.ERROR_EMPTY_SPREADSHEET);
        } else {
            for (int i = 1; i < values.size(); i++) {
                List<Object> column = values.get(i);
                if (tab.equals(ProductConstants.PRODUCT_TAB_NAME)) {
                    boolean isFavorite = column.get(ProductConstants.FIELD_FAVORITE).toString().equalsIgnoreCase(isTrue);

                    Product product = new Product(
                            userId,
                            column.get(ProductConstants.FIELD_NAME).toString(),
                            column.get(ProductConstants.FIELD_CODE).toString(),
                            column.get(ProductConstants.FIELD_DESCRIPTION).toString(),
                            column.get(ProductConstants.FIELD_IMAGE).toString(),
                            isFavorite,
                            Double.parseDouble(column.get(ProductConstants.FIELD_PRICE).toString()),
                            column.get(ProductConstants.FIELD_PATH).toString());

                    this.saleRepository.save(product);
                } else if (tab.equals(ServicesConstants.SERVICES_TAB_NAME)) {
                    boolean isFavorite = column.get(ServicesConstants.FIELD_FAVORITE).toString().equalsIgnoreCase(isTrue);

                    Services services = new Services(
                            userId,
                            column.get(ServicesConstants.FIELD_NAME).toString(),
                            column.get(ServicesConstants.FIELD_CODE).toString(),
                            column.get(ServicesConstants.FIELD_DESCRIPTION).toString(),
                            column.get(ServicesConstants.FIELD_IMAGE).toString(),
                            isFavorite,
                            Double.parseDouble(column.get(ServicesConstants.FIELD_PRICE).toString()),
                            column.get(ServicesConstants.FIELD_PATH).toString(),
                            column.get(ServicesConstants.FIELD_PHONE).toString(),
                            column.get(ServicesConstants.FIELD_TYPE).toString(),
                            column.get(ServicesConstants.FIELD_ADDRESS).toString());

                    this.saleRepository.save(services);
                }
            }
        }
    }

    public Spreadsheet findByUserId(String userId) {
        return this.spreadsheetFromUserRepository.findOneByUserProfileId(userId).
                orElseThrow(() -> new NoSuchElementException(ErrorMessageSpreadsheet.ERROR_USER_DOESNT_EXIST)).getSpreadsheet();
    }
}
