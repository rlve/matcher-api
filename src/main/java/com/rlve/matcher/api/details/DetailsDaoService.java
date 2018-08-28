package com.rlve.matcher.api.details;

import com.rlve.matcher.api.domain.DetailsEnriched;
import com.rlve.matcher.api.match.Match;
import com.rlve.matcher.api.match.MatchDaoService;
import com.rlve.matcher.api.user.User;
import com.rlve.matcher.api.user.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class DetailsDaoService {
    private static List<old_Details> allDetails = new ArrayList<>();

    @Autowired
    private MatchDaoService matchService;

    @Autowired
    private UserDaoService userService;

    static {
        ZoneId zoneId = ZoneId.of("UTC+1");
        allDetails.add(new old_Details(UUID.fromString("c8e3cf4e-df16-4bff-8bd7-f3b8eea9880c"), UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), UUID.fromString("7039d273-8e53-4345-b218-d5058b7edd70")));
        allDetails.add(new old_Details(UUID.fromString("a9e80e76-817b-48ab-99a3-e587ea3c9c4b"), UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), UUID.fromString("36a88684-b377-41fd-8957-f8db3d91602a")));

        allDetails.add(new old_Details(UUID.fromString("a3fe3e25-61ba-40e3-a468-a9a7f151d605"), UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), UUID.fromString("7039d273-8e53-4345-b218-d5058b7edd70")));
        allDetails.add(new old_Details(UUID.fromString("75b381f1-d645-4537-8229-217495dc029e"), UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), UUID.fromString("36a88684-b377-41fd-8957-f8db3d91602a")));
    }

    public List<DetailsEnriched> findAllByMatchId(UUID matchId) {
        Iterator<old_Details> iterator = allDetails.iterator();
        List<DetailsEnriched> returnList = new ArrayList<>();

        while (iterator.hasNext()) {
            old_Details details = iterator.next();

            if (details.getMatchId().equals(matchId)) {
                returnList.add(this.enrichDetails(details));
            }
        }

        return returnList;
    }

    public List<DetailsEnriched> findAllByUserId(UUID userId) {
        Iterator<old_Details> iterator = allDetails.iterator();
        List<DetailsEnriched> returnList = new ArrayList<>();

        while (iterator.hasNext()) {
            old_Details details = iterator.next();

            if (details.getUserId().equals(userId)) {
                returnList.add(this.enrichDetails(details));
            }
        }

        return returnList;
    }

    public DetailsEnriched findOne(UUID id) {
        Iterator<old_Details> iterator = allDetails.iterator();

        while (iterator.hasNext()) {
            old_Details details = iterator.next();

            if (details.getId().equals(id)) {
                return this.enrichDetails(details);
            }
        }
        return null;
    }

    public void updateById(UUID id, old_Details updatedDetails) {
        Iterator<old_Details> iterator = allDetails.iterator();

        while (iterator.hasNext()) {
            old_Details details = iterator.next();

            if (details.getId().equals(id)) {
                if (updatedDetails.getUserPresent() != null)
                    details.setUserPresent(updatedDetails.getUserPresent());
                if (updatedDetails.getUserPaid() != null)
                    details.setUserPaid(updatedDetails.getUserPaid());
            }
        }
    }

    public DetailsEnriched enrichDetails(old_Details details) {
        User user = userService.findOne(details.getUserId());
        Match match = matchService.findOne(details.getMatchId());

        DetailsEnriched enriched = new DetailsEnriched();
        enriched.setDetailsId(details.getId());
        enriched.setMatchId(details.getMatchId());
        enriched.setUserId(details.getUserId());
        enriched.setUserPresent(details.getUserPresent());
        enriched.setUserPaid(details.getUserPaid());

        enriched.setUserName(user.getName());

        enriched.setMatchDate(match.getMatchDate());
        enriched.setMatchPlace(match.getPlace());

        return enriched;
    }
}
