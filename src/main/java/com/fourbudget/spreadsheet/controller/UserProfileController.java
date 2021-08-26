package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.openmbean.KeyAlreadyExistsException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/user_profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfileDTO userProfileDto){
        UserProfile userProfile = null;
        try{
            userProfile = this.userProfileService.createUser(userProfileDto);
        } catch (KeyAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } finally {
            return new ResponseEntity(userProfile, HttpStatus.CREATED);
        }
    }
}
