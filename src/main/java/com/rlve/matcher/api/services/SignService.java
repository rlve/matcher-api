package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.Details;
import com.rlve.matcher.api.domain.Match;
import com.rlve.matcher.api.domain.MatchState;
import com.rlve.matcher.api.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class SignService {
    private UserService userService;
    private MatchService matchService;
    private DetailsService detailsService;

    @Autowired
    public SignService(UserService userService, MatchService matchService, DetailsService detailsService) {
        this.userService = userService;
        this.matchService = matchService;
        this.detailsService = detailsService;
    }

    public void signUserToMatch(Long userId, Long matchId) {
        User user = userService.findById(userId);
        Match match = matchService.findById(matchId);

        Details details = detailsService.findByUserIdAndMatchId(user.getId(), match.getId())
                .orElse(new Details(match, user));

        MatchState status = new MatchState();

        if (match.getSquad().contains(userId)) {
            status.setSignState(MatchState.SIGN.IN_SQUAD);
        } else {
            if (match.getSquad().size() >= match.getMaxPlayers()) {
                if (match.getReserves().contains(userId)) {
                    status.setSignState(MatchState.SIGN.IN_RESERVES);
                } else {
                    status.setSignState(MatchState.SIGN.OK_RESERVES);
                    details.setInReserves(Boolean.TRUE);
                    match.getReserves().add(userId);
                }
            } else {
                status.setSignState(MatchState.SIGN.OK);
                details.setInSquad(Boolean.TRUE);
                match.getSquad().add(userId);
            }
        }

        if (details.getId() == null)
            detailsService.save(details);
    }

    public void removeUserFromMatch(Long userId, Long matchId) {
        User user = userService.findById(userId);
        Match match = matchService.findById(matchId);

        Optional<Details> details = detailsService.findByUserIdAndMatchId(user.getId(), match.getId());

        if (details.isPresent()) {
            match.getDetails().remove(details.get());

            if (details.get().getInSquad()) {
                match.getSquad().remove(userId);

                if (match.getSquad().size() < match.getMaxPlayers()) {
                    if (match.getReserves().size() > 0) {
                        Long fromReserves = match.getReserves().iterator().next();
                        match.getSquad().add(fromReserves);
                        match.getReserves().remove(fromReserves);
                    }
                }
            } else {
                match.getReserves().remove(userId);
            }

            matchService.save(match);
        }
    }
}
