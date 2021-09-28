package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.GoogleAuthorizeUtil;
import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.constants.ProductFields;
import com.fourbudget.spreadsheet.model.constants.ServicesFields;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import com.fourbudget.spreadsheet.repository.ServicesRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
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

@AllArgsConstructor
@Service
public class SpreadsheetService {

    private final UserProfileRepository userProfileRepository;
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;
    private final ProductRepository productRepository;
    private final ServicesRepository servicesRepository;

    private final String isTrue = "SIM";

    @Transactional
    public SpreadsheetFromUser registerSpreadsheetLink(SpreadsheetUserDTO spreadsheetUserDTO) throws IOException, GeneralSecurityException {
        Long idProfileUser = spreadsheetUserDTO.getUserId();
        String link = spreadsheetUserDTO.getSpreadsheetLink();
        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if (!optUserProfile.isPresent()) {
            throw new MySystemException(HttpStatus.NO_CONTENT, "User doesn't exist");
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
            deleteExistents(idProfileUser);
            this.spreadsheetRepository.save(spreadsheet);
        }

        populate(spreadsheet.getSpreadsheetLink(), idProfileUser);

        this.spreadsheetFromUserRepository.save(suRelation);

        return suRelation;
    }

    private void deleteExistents(Long idProfileUser) {
        this.servicesRepository.deleteAllByUserId(idProfileUser);
        this.productRepository.deleteAllByUserId(idProfileUser);
    }

    public List<SpreadsheetFromUser> findAllSURelations() {
        return this.spreadsheetFromUserRepository.findAll();
    }

    public void populate(String spreadsheet, Long userId) throws GeneralSecurityException, IOException {
        this.populateProducts(spreadsheet, userId, "products");
        this.populateProducts(spreadsheet, userId, "services");
    }

    private void populateProducts(String spreadsheet, Long userId, String tab) throws IOException, GeneralSecurityException {
        Sheets sheets = GoogleAuthorizeUtil.getSheetsService();
        final String range = "'" + tab + "'!A:Z";

        ValueRange response = sheets.spreadsheets().values()
                .get(spreadsheet, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            throw new MySystemException(HttpStatus.BAD_REQUEST, "Spreadsheet is empty");
        } else {
            for (int i = 1; i < values.size(); i++) {
                List<Object> column = values.get(i);
                if (tab.equals("products")) {
                    boolean isFavorite = column.get(ProductFields.FIELD_FAVORITE).toString().equalsIgnoreCase(isTrue);

                    Product product = new Product(
                            userId,
                            column.get(ProductFields.FIELD_NAME).toString(),
                            column.get(ProductFields.FIELD_CODE).toString(),
                            column.get(ProductFields.FIELD_DESCRIPTION).toString(),
                            column.get(ProductFields.FIELD_IMAGE).toString(),
                            isFavorite,
                            Double.parseDouble(column.get(ProductFields.FIELD_PRICE).toString()),
                            column.get(ProductFields.FIELD_PATH).toString());

                    this.productRepository.save(product);
                } else if (tab.equals("services")) {
                    boolean isFavorite = column.get(ServicesFields.FIELD_FAVORITE).toString().equalsIgnoreCase(isTrue);

                    Services services = new Services(
                            userId,
                            column.get(ServicesFields.FIELD_NAME).toString(),
                            column.get(ServicesFields.FIELD_CODE).toString(),
                            column.get(ServicesFields.FIELD_DESCRIPTION).toString(),
                            column.get(ServicesFields.FIELD_IMAGE).toString(),
                            isFavorite,
                            Double.parseDouble(column.get(ServicesFields.FIELD_PRICE).toString()),
                            column.get(ServicesFields.FIELD_PATH).toString(),
                            column.get(ServicesFields.FIELD_PHONE).toString(),
                            column.get(ServicesFields.FIELD_TYPE).toString(),
                            column.get(ServicesFields.FIELD_ADDRESS).toString());

                    this.servicesRepository.save(services);
                }
            }
        }
    }

    public Spreadsheet findByUserId(Long userId) {
        return this.spreadsheetFromUserRepository.findOneByUserProfileId(userId).
                orElseThrow(() -> new NoSuchElementException("User doesn't exist")).getSpreadsheet();
    }
}
