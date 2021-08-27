package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.model.Spreadsheet;
import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.SpreadsheetUserDTO;
import com.fourbudget.spreadsheet.repository.SpreadsheetFromUserRepository;
import com.fourbudget.spreadsheet.repository.SpreadsheetRepository;
import com.fourbudget.spreadsheet.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SpreadsheetService {

    private final UserProfileRepository userProfileRepository;
    private final SpreadsheetFromUserRepository spreadsheetFromUserRepository;
    private final SpreadsheetRepository spreadsheetRepository;

    public SpreadsheetFromUser registerSpreadsheetLink(SpreadsheetUserDTO spreadsheetUserDTO) {
        Long idProfileUser = spreadsheetUserDTO.getUserId();
        String link = spreadsheetUserDTO.getSpreadsheetLink();
        Optional<UserProfile> optUserProfile = this.userProfileRepository.findById(idProfileUser);
        if (!optUserProfile.isPresent()) {
            throw new NoSuchElementException("User doesn't exist");
        }

        UserProfile userProfile = optUserProfile.get();
        Optional<SpreadsheetFromUser> optSuRelation = this.spreadsheetFromUserRepository.findByUserProfileId(idProfileUser);
        SpreadsheetFromUser suRelation;
        if (!optSuRelation.isPresent()) {
            Spreadsheet spreadsheet = new Spreadsheet(link);
            this.spreadsheetRepository.save(spreadsheet);
            suRelation = new SpreadsheetFromUser(userProfile, spreadsheet);
        } else {
            suRelation = optSuRelation.get();
            Spreadsheet spreadsheet = suRelation.getSpreadsheet();
            spreadsheet.setSpreadsheetLink(link);
            this.spreadsheetRepository.save(spreadsheet);
        }
        this.spreadsheetFromUserRepository.save(suRelation);
        return suRelation;
    }

    public List<SpreadsheetFromUser> findAllSURelations() {
        return this.spreadsheetFromUserRepository.findAll();
    }

}
