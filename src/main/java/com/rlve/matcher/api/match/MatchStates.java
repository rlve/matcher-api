package com.rlve.matcher.api.match;

public class MatchStates {
    public enum SIGN {
        OK, IN_SQUAD, IN_RESERVES, OK_RESERVES, OK_REMOVED, NO_USER
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

            case OK_REMOVED:
                message = "User removed.";
                break;

            case NO_USER:
                message = "No user signed.";
                break;

            default:
                break;
        }
        return message;
    }
}
