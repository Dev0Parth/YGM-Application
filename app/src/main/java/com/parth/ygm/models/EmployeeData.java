package com.parth.ygm.models;

public class EmployeeData {
    private String empId, fullName, department, date, present, leaveType, scopeOfWork, scoping, leaveReason, createdAt;

    public EmployeeData() {
    }

    public EmployeeData(String empId, String fullName, String department, String date, String present, String leaveType, String scopeOfWork, String scoping, String leaveReason, String createdAt) {
        this.empId = empId;
        this.fullName = fullName;
        this.department = department;
        this.date = date;
        this.present = present;
        this.leaveType = leaveType;
        this.scopeOfWork = scopeOfWork;
        this.scoping = scoping;
        this.leaveReason = leaveReason;
        this.createdAt = createdAt;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getScopeOfWork() {
        return scopeOfWork;
    }

    public void setScopeOfWork(String scopeOfWork) {
        this.scopeOfWork = scopeOfWork;
    }

    public String getScoping() {
        return scoping;
    }

    public void setScoping(String scoping) {
        this.scoping = scoping;
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
}
