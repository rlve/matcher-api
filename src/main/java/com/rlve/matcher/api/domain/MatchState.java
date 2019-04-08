package com.rlve.matcher.api.domain;

public class MatchState {
    private SIGN signState = SIGN.UNDEFINED;

    public MatchState() {
    }

    public MatchState(SIGN state) {
        this.signState = state;
    }


    public SIGN getSignState() {
        return signState;
    }

    public void setSignState(SIGN signState) {
        this.signState = signState;
    }

    public enum SIGN {
        OK, IN_SQUAD, IN_RESERVES, OK_RESERVES, OK_REMOVED, NO_USER, UNDEFINED
    }

    public String getMessage() {
        String message = "Unknown state.";
        switch (this.getSignState()) {
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

            case UNDEFINED:
                message = "Something went wrong.";
                break;

            default:
                break;
        }
        return message;
    }
}
