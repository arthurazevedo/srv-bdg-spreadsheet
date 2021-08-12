package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.domain.UserProfile;
import com.fourbudget.spreadsheet.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public void registerSpreadsheetLink(Long idProfileUser, String link){

        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if(!optUserProfile.isPresent()){
            throw new NoSuchElementException("UserProfile does not exist");
        } else {
            UserProfile userProfile = optUserProfile.get();
            userProfile.setSpreadsheetLink(link);
            this.userProfileRepository.save(userProfile);
        }
    }

    public UserProfile createUser(UserProfileDTO userProfileDto) {
        UserProfile userProfile = new UserProfile(userProfileDto);
        this.userProfileRepository.save(userProfile);
        return userProfile;
    }

    public List<UserProfile> findAll(){
        return  this.userProfileRepository.findAll();
    }

}
