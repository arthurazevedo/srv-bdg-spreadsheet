package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserProfileServiceTest {

    @Autowired
    @MockBean
    private UserProfileRepository upRepository;

    @Autowired
    private UserProfileService upService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void succesfullyCreateUser(){
        UserProfile userTest = new UserProfile(new Long(1), "test", "test@test");
        UserProfileDTO upDtoTest = new UserProfileDTO(userTest);
        Optional<UserProfile> optUserProfile = Optional.of(userTest);
        given(this.upRepository.findById(userTest.getId())).willReturn(optUserProfile);

        Assertions.assertEquals(this.upService.createUser(upDtoTest), userTest);
    }
    @Test
    public void createUserWithExistingIdThrowsException(){
        UserProfile userTest = new UserProfile(new Long(1), "test", "test@test");
        UserProfileDTO upDtoTest = new UserProfileDTO(userTest);
        given(this.upRepository.existsById(userTest.getId())).willReturn(true);

        Assertions.assertThrows(SpreadsheetApplicationException.class, () -> {
            this.upService.createUser(upDtoTest);
        });
    }
}
