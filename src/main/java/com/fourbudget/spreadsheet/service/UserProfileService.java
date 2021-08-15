package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.domain.Spreadsheet;
import com.fourbudget.spreadsheet.domain.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.domain.UserProfile;
import com.fourbudget.spreadsheet.dto.SpreadsheetDTO;
import com.fourbudget.spreadsheet.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
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
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;

    public void registerSpreadsheetLink(Long idProfileUser, SpreadsheetDTO spreadsheetDTO){
        String link = spreadsheetDTO.getSpreadsheetLink();
        if(!this.userProfileRepository.existsById(idProfileUser)){
            throw new NoSuchElementException("User doesn't exist");
        }
        Optional<SpreadsheetFromUser> optSuRelation = this.spreadsheetFromUserRepository.findByUserProfileId(idProfileUser);
        if(!optSuRelation.isPresent()) {
            Spreadsheet spreadsheet = new Spreadsheet(link);
            this.spreadsheetRepository.save(spreadsheet);
            SpreadsheetFromUser suRelation = new SpreadsheetFromUser(idProfileUser, spreadsheet.getId());
            this.spreadsheetFromUserRepository.save(suRelation);
        } else {
            SpreadsheetFromUser suRelation = optSuRelation.get();
            Spreadsheet spreadsheet = this.spreadsheetRepository.findById(suRelation.getSpreadsheetId()).get();
            spreadsheet.setSpreadsheetLink(link);
            this.spreadsheetRepository.save(spreadsheet);
        }
    }

    public UserProfile createUser(UserProfileDTO userProfileDto) {
        UserProfile userProfile = new UserProfile(userProfileDto);
        this.userProfileRepository.save(userProfile);
        return userProfile;
    }

    public List<UserProfile> findAllUsers(){
        return  this.userProfileRepository.findAll();
    }

    public List<SpreadsheetFromUser> findAllSURelations(){
        return this.spreadsheetFromUserRepository.findAll();
    }

}
