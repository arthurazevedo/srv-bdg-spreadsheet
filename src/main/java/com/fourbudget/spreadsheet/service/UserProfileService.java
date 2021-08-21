package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile createUser(UserProfileDTO userProfileDto) {
        UserProfile userProfile = new UserProfile(userProfileDto);
        this.userProfileRepository.save(userProfile);
        return userProfile;
    }

}
