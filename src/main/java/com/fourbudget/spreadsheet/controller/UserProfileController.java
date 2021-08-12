package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.domain.UserProfile;
import com.fourbudget.spreadsheet.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/userprofile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping("/spreadsheet")
    public ResponseEntity<Void> postSpreadsheetLink(@RequestHeader Long idProfileUser, @RequestBody String link){
        try {
            this.userProfileService.registerSpreadsheetLink(idProfileUser, link);
        } catch (NoSuchElementException e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<UserProfile> createUser(@RequestBody UserProfileDTO userProfileDto){
        UserProfile userProfile = this.userProfileService.createUser(userProfileDto);
        return new ResponseEntity(userProfile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserProfile>> getAllUsersProfiles(){
        List<UserProfile> userProfilesList = this.userProfileService.findAll();
        return new ResponseEntity(userProfilesList, HttpStatus.OK);
    }

    @GetMapping("/health")
    public ResponseEntity health(){
        return ResponseEntity.ok().body(new Date().toString());
    }
}
