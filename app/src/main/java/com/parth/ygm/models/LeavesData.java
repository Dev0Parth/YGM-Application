package com.parth.ygm.models;

public class LeavesData {
    String empId, department, fullName, fromDate, toDate, leaveType, leaveReason, createdAt, half_leaves, full_leaves;

    public LeavesData() {
    }

    public LeavesData(String empId, String department, String fullName, String fromDate, String toDate, String leaveType, String leaveReason, String createdAt, String half_leaves, String full_leaves) {
        this.empId = empId;
        this.department = department;
        this.fullName = fullName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.leaveType = leaveType;
        this.leaveReason = leaveReason;
        this.createdAt = createdAt;
        this.half_leaves = half_leaves;
        this.full_leaves = full_leaves;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHalf_leaves() {
        return half_leaves;
    }

    public void setHalf_leaves(String half_leaves) {
        this.half_leaves = half_leaves;
    }

    public String getFull_leaves() {
        return full_leaves;
    }

    public void setFull_leaves(String full_leaves) {
        this.full_leaves = full_leaves;
    }
}
