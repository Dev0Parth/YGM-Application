package com.parth.ygm.models;

public class LeaveCount {

    String halfLeave, fullLeave;

    public LeaveCount() {
    }

    public LeaveCount(String halfLeave, String fullLeave) {
        this.halfLeave = halfLeave;
        this.fullLeave = fullLeave;
    }

    public String getHalfLeave() {
        return halfLeave;
    }

    public void setHalfLeave(String halfLeave) {
        this.halfLeave = halfLeave;
    }

    public String getFullLeave() {
        return fullLeave;
    }

    public void setFullLeave(String fullLeave) {
        this.fullLeave = fullLeave;
    }
}
