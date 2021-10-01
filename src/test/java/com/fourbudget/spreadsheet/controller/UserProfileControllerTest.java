package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.UserProfile;
import com.fourbudget.spreadsheet.model.dto.UserProfileDTO;
import com.fourbudget.spreadsheet.service.UserProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.fourbudget.spreadsheet.util.AsJsonToStringUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserProfileController.class)
@WebAppConfiguration
public class UserProfileControllerTest {

    @Autowired
    private UserProfileController userProfileController;

    @MockBean
    private UserProfileService upService;

    @Test
    void contextLoads() {
    }

    @Test
    void succesfullyCreateUser() throws Exception {
        UserProfile userProfileTest = new UserProfile("1", "test", "test@test");
        UserProfileDTO upTest = new UserProfileDTO(userProfileTest);
        given(this.upService.createUser(upTest)).willReturn(userProfileTest);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.userProfileController).build();
        MvcResult result = mockMvc.perform(post("/user-profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(upTest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), asJsonString(userProfileTest));
    }
}
