package com.example.HRApplication.Models.Enums;

public enum LeaveReason {
    ANNUAL(14),
    MEDICAL(14),
    HOSPITALIZATION(60),
    COMPASSIONATE(3),
    MARRIAGE(5),
    MATERNITY(60),
    REPLACEMENT(1);


    private final int maxDays;

    LeaveReason(int maxDays) {
        this.maxDays = maxDays;
    }

    public int getMaxDays(){
        return maxDays;
    }

    public static LeaveReason fromString(String reason){
        for(LeaveReason leaveReason : LeaveReason.values()){
            if(leaveReason.name().equalsIgnoreCase(reason)){
                return leaveReason;
            }
        }
        throw new IllegalArgumentException("Unknown leave reason: " + reason);

    }

    }