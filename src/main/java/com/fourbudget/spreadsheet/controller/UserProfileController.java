package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody @Valid UserProfileDTO userProfileDto) {
        UserProfile userProfile = this.userProfileService.createUser(userProfileDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfile);
    }
}
