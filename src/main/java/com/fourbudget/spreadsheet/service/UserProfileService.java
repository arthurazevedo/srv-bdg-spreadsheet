package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import com.fourbudget.spreadsheet.util.messages.ErrorMessageUserProfile;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile createUser(UserProfileDTO userProfileDto) {
        if (this.userProfileRepository.existsById(userProfileDto.getId())) {
            throw new SpreadsheetApplicationException(HttpStatus.OK, ErrorMessageUserProfile.ERROR_MESSAGE_USER_ALREADY_EXISTS);
        }
        UserProfile userProfile = new UserProfile(userProfileDto);
        System.out.println("Before -> " + userProfile);
        this.userProfileRepository.save(userProfile);
        System.out.println("After -> " + userProfile);
        return userProfile;
    }

}
