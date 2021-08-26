package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.service.UserProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.fourbudget.spreadsheet.util.AsJsonToStringUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserProfileController.class)
@ActiveProfiles("test")
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserProfileService upService;

    @Test
    void contextLoads() {
    }

    @Test
    void succesfullyCreateUser() throws Exception {
        UserProfile userProfileTest = new UserProfile(new Long(1), "test", "test@test");
        UserProfileDTO upTest = new UserProfileDTO(userProfileTest);
        given(this.upService.createUser(upTest)).willReturn(userProfileTest);

        MvcResult result = this.mockMvc.perform(post("/user_profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(upTest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), asJsonString(userProfileTest));
    }
}
