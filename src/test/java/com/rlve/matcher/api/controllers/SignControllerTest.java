package com.rlve.matcher.api.controllers;

import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.services.SignService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SignControllerTest {
    private MockMvc mvc;
    @Mock
    private SignService signService;

    private SignController signController;

    @Before
    public void setUp() throws Exception {
        signController = new SignController(signService);
        mvc = MockMvcBuilders.standaloneSetup(signController).build();
    }

    @Test
    public void signUserToMatch() throws Exception{
        //given
        MatchState result = new MatchState(MatchState.SIGN.OK);
        when(signService.signUserToMatch(anyLong(), anyLong())).thenReturn(result);

        //when then
        mvc.perform(get("/sign/user/222/match/333"))
                .andExpect(status().isOk())
                .andExpect(content().string(result.getMessage()));
    }

    @Test
    public void signUserToMatchIfInSquad() throws Exception{
        //given
        Long USER_ID = 222L;
        MatchState result = new MatchState(MatchState.SIGN.IN_RESERVES);
        when(signService.signUserToMatch(anyLong(), anyLong())).thenReturn(result);

        //when then
        mvc.perform(get(format("/sign/user/%s/match/333", USER_ID)))
                .andExpect(status().isConflict());
    }

    @Test
    public void removeUserFromMatch() {
    }
}