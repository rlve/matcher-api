package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SignServiceTest {
    private static final String NAME = "John Test";
    private static final String PLACE = "Test Place";
    private static final Instant MATCH_DATE =
            LocalDateTime.of(2018, 11, 15, 18, 0)
                    .atZone(ZoneId.systemDefault())
                    .toInstant();
    private static final Integer MAX_PLAYERS = 20;
    private static final Long USER_ID = 123L;
    private static final Long MATCH_ID = 321L;
    private static final Long DETAILS_ID = 333L;

    @Mock
    private UserService userService;
    @Mock
    private MatchService matchService;
    @Mock
    private DetailsService detailsService;

    private SignService signService;

    private Match mockMatch;
    private User mockUser;
    private Details mockDetailsNew;
    private Details mockDetailsFromDb;

    private Details cloneDetails(Details details){
        Details newDetails = new Details();
        newDetails.setAddingDate(details.getAddingDate());
        newDetails.setUserPresent(details.getUserPresent());
        newDetails.setUserPaid(details.getUserPaid());
        newDetails.setInReserves(details.getInReserves());
        newDetails.setInSquad(details.getInSquad());
        newDetails.setId(details.getId());
        newDetails.setUser(details.getUser());
        newDetails.setMatch(details.getMatch());
        return newDetails;
    }

    @Before
    public void setUp() {
        signService = new SignService(userService, matchService, detailsService);

        mockUser = new User(NAME);
        mockUser.setId(USER_ID);
        when(userService.findById(anyLong())).thenReturn(mockUser);

        mockMatch = new Match(PLACE, MATCH_DATE, MAX_PLAYERS);
        mockMatch.setId(MATCH_ID);
        when(matchService.findById(anyLong())).thenReturn(mockMatch);

        mockDetailsNew = new Details(mockMatch, mockUser);
        mockDetailsFromDb = cloneDetails(mockDetailsNew);
        mockDetailsFromDb.setId(DETAILS_ID);
    }

    @Test
    public void userShouldBeAbleToSignToMatch() {
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsNew));

        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);
        assertEquals(result.getSignState(), MatchState.SIGN.OK);
        verify(detailsService, times(1)).save(any(Details.class));
    }

    @Test
    public void userNotSignedIfAlreadyInSquad() {
        mockMatch.getSquad().add(mockUser.getId());

        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);
        assertEquals(result.getSignState(), MatchState.SIGN.IN_SQUAD);
        verify(detailsService, times(0)).save(any(Details.class));
    }
}