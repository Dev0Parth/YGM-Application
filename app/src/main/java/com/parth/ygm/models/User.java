package com.parth.ygm.models;

public class User {
    String empId, department, name, email, phone, password, created_At;

    public User() {
    }

    public User(String empId, String department, String name, String email, String phone, String password, String created_At) {
        this.empId = empId;
        this.department = department;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated_At() {
        return created_At;
    }

    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }
}
