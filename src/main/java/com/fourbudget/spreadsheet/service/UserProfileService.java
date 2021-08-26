package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;

@AllArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile createUser(UserProfileDTO userProfileDto) {
        if (this.userProfileRepository.existsById(userProfileDto.getId())){
            throw new KeyAlreadyExistsException();
        }
        UserProfile userProfile = new UserProfile(userProfileDto);
        this.userProfileRepository.save(userProfile);
        return userProfile;
    }

}
