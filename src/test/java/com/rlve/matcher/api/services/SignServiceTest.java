package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
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
    private static final Integer MAX_PLAYERS = 10;
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

    private Details cloneDetails(Details details) {
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
    public void userSignedToMatch() {
        // given
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsNew));

        // when
        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.OK);

        assertTrue(mockDetailsNew.getInSquad());
        assertFalse(mockDetailsNew.getInReserves());

        assertTrue(mockMatch.getSquad().contains(USER_ID));
        assertFalse(mockMatch.getReserves().contains(USER_ID));

        verify(detailsService, times(1)).save(any(Details.class));
    }

    @Test
    public void userNotSignedIfAlreadyInSquad() {
        // given
        mockMatch.getSquad().add(mockUser.getId());
        mockDetailsFromDb.setInSquad(Boolean.TRUE);
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.IN_SQUAD);

        assertTrue(mockDetailsFromDb.getInSquad());
        assertFalse(mockDetailsFromDb.getInReserves());

        assertTrue(mockMatch.getSquad().contains(USER_ID));
        assertFalse(mockMatch.getReserves().contains(USER_ID));

        verify(detailsService, times(0)).save(any(Details.class));
    }

    @Test
    public void userNotSignedIfAlreadyInReserves() {
        // given
        mockMatch.setSquad(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)));
        mockMatch.getReserves().add(mockUser.getId());
        mockDetailsFromDb.setInReserves(Boolean.TRUE);
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.IN_RESERVES);

        assertFalse(mockDetailsFromDb.getInSquad());
        assertTrue(mockDetailsFromDb.getInReserves());

        assertFalse(mockMatch.getSquad().contains(USER_ID));
        assertTrue(mockMatch.getReserves().contains(USER_ID));

        verify(detailsService, times(0)).save(any(Details.class));
    }

    @Test
    public void userSignedToReservesIfSquadIsFull() {
        // given
        mockMatch.setSquad(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)));
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.signUserToMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.OK_RESERVES);

        assertFalse(mockDetailsFromDb.getInSquad());
        assertTrue(mockDetailsFromDb.getInReserves());

        assertFalse(mockMatch.getSquad().contains(USER_ID));
        assertTrue(mockMatch.getReserves().contains(USER_ID));

        verify(detailsService, times(0)).save(any(Details.class));
    }

    @Test
    public void userRemovedFromSquadEmptyReserves() {
        // given
        mockDetailsFromDb.setInSquad(Boolean.TRUE);
        mockMatch.setSquad(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, USER_ID, 7L, 8L, 9L, 10L)));
        mockMatch.getDetails().add(mockDetailsFromDb);
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.removeUserFromMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.OK_REMOVED);
        assertFalse(mockMatch.getDetails().contains(mockDetailsFromDb));

        assertFalse(mockMatch.getSquad().contains(USER_ID));
        assertFalse(mockMatch.getReserves().contains(USER_ID));
    }

    @Test
    public void userRemovedFromSquadNotEmptyReserves() {
        final Long FIRST_FROM_RESERVES = 11L;

        // given
        Details mockDetailsFromReserves = cloneDetails(mockDetailsFromDb);
        when(detailsService.findByUserIdAndMatchId(eq(FIRST_FROM_RESERVES), anyLong())).thenReturn(Optional.of(mockDetailsFromReserves));

        mockDetailsFromDb.setInSquad(Boolean.TRUE);
        mockMatch.setSquad(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, USER_ID, 7L, 8L, 9L, 10L)));
        mockMatch.setReserves(new ArrayList<>(Arrays.asList(FIRST_FROM_RESERVES, 22L, 33L, 44L, 55L)));
        mockMatch.getDetails().add(mockDetailsFromDb);
        when(detailsService.findByUserIdAndMatchId(eq(USER_ID), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.removeUserFromMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.OK_REMOVED);
        assertFalse(mockMatch.getDetails().contains(mockDetailsFromDb));

        assertFalse(mockMatch.getSquad().contains(USER_ID));
        assertFalse(mockMatch.getReserves().contains(USER_ID));

        assertTrue(mockMatch.getSquad().contains(FIRST_FROM_RESERVES));
        assertFalse(mockMatch.getReserves().contains(FIRST_FROM_RESERVES));

        assertTrue(mockDetailsFromReserves.getInSquad());
        assertFalse(mockDetailsFromReserves.getInReserves());
    }

    @Test
    public void userRemovedFromReserves() {
        // given
        mockDetailsFromDb.setInReserves(Boolean.TRUE);
        mockMatch.setSquad(new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)));
        mockMatch.setReserves(new ArrayList<>(Arrays.asList(USER_ID, 22L, 33L, 44L, 55L)));
        mockMatch.getDetails().add(mockDetailsFromDb);
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.of(mockDetailsFromDb));

        // when
        MatchState result = signService.removeUserFromMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(result.getSignState(), MatchState.SIGN.OK_REMOVED);
        assertFalse(mockMatch.getDetails().contains(mockDetailsFromDb));

        assertFalse(mockMatch.getSquad().contains(USER_ID));
        assertFalse(mockMatch.getReserves().contains(USER_ID));
    }

    @Test
    public void userNotRemovedIfNotPlaying() {
        // given
        when(detailsService.findByUserIdAndMatchId(anyLong(), anyLong())).thenReturn(Optional.empty());

        // when
        MatchState result = signService.removeUserFromMatch(USER_ID, MATCH_ID);

        // then
        assertEquals(MatchState.SIGN.NO_USER, result.getSignState());
    }

}