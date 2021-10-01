package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpreadsheetServiceTest {

    @Autowired
    private SpreadsheetService spreadsheetService;

    @Autowired
    @MockBean
    private UserProfileRepository userProfileRepository;

    @Autowired
    @MockBean
    private SpreadsheetFromUserRepository spreadsheetFromUserRepository;

    @Autowired
    @MockBean
    private SpreadsheetRepository spreadsheetRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void succesfullyCreateSURelation() throws IOException, GeneralSecurityException {
        UserProfile userTest = new UserProfile("1", "test", "test@test");
        Optional<UserProfile> optUserProfile = Optional.of(userTest);
        when(this.userProfileRepository.findById(userTest.getId())).thenReturn(optUserProfile);

        SpreadsheetUserDTO suDto = new SpreadsheetUserDTO("1IqjNBIRu8Ks9AT_tEpwOArHZryT0y7w5jiJXvVuek-M", "1");

        Assertions.assertEquals(this.spreadsheetService.registerSpreadsheetLink(suDto).getUserProfile(), userTest);
        Assertions.assertEquals(this.spreadsheetService.registerSpreadsheetLink(suDto).getSpreadsheet().getSpreadsheetLink(), suDto.getSpreadsheetLink());
    }

    @Test
    public void userNotFoundException() {
        SpreadsheetUserDTO suDto = new SpreadsheetUserDTO("test", "1");

        Assertions.assertThrows(SpreadsheetApplicationException.class, () -> {
            this.spreadsheetService.registerSpreadsheetLink(suDto);
        });
    }
}
