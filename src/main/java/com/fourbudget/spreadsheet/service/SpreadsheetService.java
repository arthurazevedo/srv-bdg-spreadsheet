package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.domain.Spreadsheet;
import com.fourbudget.spreadsheet.domain.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.domain.UserProfile;
import com.fourbudget.spreadsheet.dto.SpreadsheetUserDTO;
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
public class SpreadsheetService {

    private final UserProfileRepository userProfileRepository;
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;

    public void registerSpreadsheetLink(SpreadsheetUserDTO spreadsheetUserDTO) {
        Long idProfileUser = spreadsheetUserDTO.getUserId();
        String link = spreadsheetUserDTO.getSpreadsheetLink();
        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if(!optUserProfile.isPresent()) {
            throw new NoSuchElementException("User doesn't exist");
        }

        UserProfile userProfile = optUserProfile.get();
        Optional<SpreadsheetFromUser> optSuRelation = this.spreadsheetFromUserRepository.findByUserProfileId(idProfileUser);
        if(!optSuRelation.isPresent()) {
            Spreadsheet spreadsheet = new Spreadsheet(link);
            this.spreadsheetRepository.save(spreadsheet);
            SpreadsheetFromUser suRelation = new SpreadsheetFromUser(userProfile, spreadsheet);
            this.spreadsheetFromUserRepository.save(suRelation);
        } else {
            SpreadsheetFromUser suRelation = optSuRelation.get();
            Spreadsheet spreadsheet = suRelation.getSpreadsheet();
            spreadsheet.setSpreadsheetLink(link);
            this.spreadsheetRepository.save(spreadsheet);
            this.spreadsheetFromUserRepository.save(suRelation);
        }
    }

    public List<SpreadsheetFromUser> findAllSURelations(){
        return this.spreadsheetFromUserRepository.findAll();
    }

}
