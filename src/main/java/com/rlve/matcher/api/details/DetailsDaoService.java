package com.rlve.matcher.api.details;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Component
public class DetailsDaoService {
    private static List<Details> allDetails = new ArrayList<>();

    static {
        ZoneId zoneId = ZoneId.of("UTC+1");
        allDetails.add(new Details(UUID.fromString("c8e3cf4e-df16-4bff-8bd7-f3b8eea9880c"), UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), UUID.fromString("7039d273-8e53-4345-b218-d5058b7edd70")));
        allDetails.add(new Details(UUID.fromString("a9e80e76-817b-48ab-99a3-e587ea3c9c4b"), UUID.fromString("2473d25f-eafe-49c5-b966-d834c6adbdbe"), UUID.fromString("36a88684-b377-41fd-8957-f8db3d91602a")));

        allDetails.add(new Details(UUID.fromString("a3fe3e25-61ba-40e3-a468-a9a7f151d605"), UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), UUID.fromString("7039d273-8e53-4345-b218-d5058b7edd70")));
        allDetails.add(new Details(UUID.fromString("75b381f1-d645-4537-8229-217495dc029e"), UUID.fromString("642fd0a5-866f-4f94-9cce-9e5ddde6451a"), UUID.fromString("36a88684-b377-41fd-8957-f8db3d91602a")));
    }

    public List<Details> findAllByMatchId(UUID matchId) {
        Iterator<Details> iterator = allDetails.iterator();
        List<Details> returnList = new ArrayList<>();

        while (iterator.hasNext()) {
            Details details = iterator.next();

            if (details.getMatchId().equals(matchId)) {
                returnList.add(details);
            }
        }

        return returnList;
    }

    public List<Details> findAllByUserId(UUID userId) {
        Iterator<Details> iterator = allDetails.iterator();
        List<Details> returnList = new ArrayList<>();

        while (iterator.hasNext()) {
            Details details = iterator.next();

            if (details.getUserId().equals(userId)) {
                returnList.add(details);
            }
        }

        return returnList;
    }

    public Details findOne(UUID id) {
        Iterator<Details> iterator = allDetails.iterator();

        while (iterator.hasNext()) {
            Details details = iterator.next();

            if (details.getId().equals(id)) {
                return details;
            }
        }
        return null;
    }

    public void updateById(UUID id, Details updatedDetails) {
        Iterator<Details> iterator = allDetails.iterator();

        while (iterator.hasNext()) {
            Details details = iterator.next();

            if (details.getId().equals(id)) {
                if (updatedDetails.getUserPresent() != null)
                    details.setUserPresent(updatedDetails.getUserPresent());
                if (updatedDetails.getUserPaid() != null)
                    details.setUserPaid(updatedDetails.getUserPaid());
            }
        }
    }
}