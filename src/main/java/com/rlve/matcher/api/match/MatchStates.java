package com.rlve.matcher.api.match;

public class MatchStates {
    public enum SIGN {
        OK, IN_SQUAD, IN_RESERVES, OK_RESERVES
    }

    static public String getMessage(SIGN state) {
        String message = "Wrong state.";
        switch (state) {
            case OK:
                message = "User signed.";
                break;

            case IN_SQUAD:
                message = "User already in squad.";
                break;

            case IN_RESERVES:
                message = "User already in reserves.";
                break;

            case OK_RESERVES:
                message = "User added to reserves.";
                break;

            default:
                break;
        }
        return message;
    }
}
