package com.rlve.matcher.api.services;

import com.rlve.matcher.api.domain.*;
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

    public MatchState signUserToMatch(Long userId, Long matchId) {
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

        return status;
    }

    public MatchState removeUserFromMatch(Long userId, Long matchId) {
        User user = userService.findById(userId);
        Match match = matchService.findById(matchId);

        Optional<Details> details = detailsService.findByUserIdAndMatchId(user.getId(), match.getId());

        MatchState status = new MatchState();

        if (details.isPresent()) {
            match.getDetails().remove(details.get());
            status.setSignState(MatchState.SIGN.OK_REMOVED);

            if (details.get().getInSquad()) {
                match.getSquad().remove(userId);

                if (match.getSquad().size() < match.getMaxPlayers()) {
                    if (match.getReserves().size() > 0) {
                        Long fromReserves = match.getReserves().get(0);

                        match.getSquad().add(fromReserves);
                        match.getReserves().remove(fromReserves);

                        Details detailsFromReserves = detailsService.findByUserIdAndMatchId(fromReserves, match.getId())
                                .orElseThrow();

                        detailsFromReserves.setInReserves(Boolean.FALSE);
                        detailsFromReserves.setInSquad(Boolean.TRUE);
                    }
                }
            } else {
                match.getReserves().remove(userId);
            }

            matchService.save(match);
        }

        return status;
    }
}
