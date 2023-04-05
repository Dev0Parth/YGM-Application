package com.parth.ygm.models;

public class User {
    String empId, department, name, phone, created_At;

    public User() {
    }

    public User(String empId, String department, String name, String phone, String created_At) {
        this.empId = empId;
        this.department = department;
        this.name = name;
        this.phone = phone;
        this.created_At = created_At;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreated_At() {
        return created_At;
    }

    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }
}
