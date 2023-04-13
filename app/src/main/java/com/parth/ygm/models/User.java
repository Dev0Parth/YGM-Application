package com.parth.ygm.models;

public class User {
    String Emp_Code, Department, Name, Contact_No, Ga_Id, Created_At;
    public User() {
    }

    public User(String Emp_Code, String Department, String Name, String Contact_No, String Ga_Id, String Created_At) {
        this.Emp_Code = Emp_Code;
        this.Department = Department;
        this.Name = Name;
        this.Contact_No = Contact_No;
        this.Ga_Id = Ga_Id;
        this.Created_At = Created_At;
    }

    public String getEmp_Code() {
        return Emp_Code;
    }

    public String getDepartment() {
        return Department;
    }

    public String getName() {
        return Name;
    }

    public String getContact_No() {
        return Contact_No;
    }

    public String getGa_Id() {
        return Ga_Id;
    }

    public String getCreated_At() {
        return Created_At;
    }

    public void setEmp_Code(String Emp_Code) {
        this.Emp_Code = Emp_Code;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setContact_No(String Contact_No) {
        this.Contact_No = Contact_No;
    }

    public void setGa_Id(String Gsf_Id) {
        this.Ga_Id = Gsf_Id;
    }

    public void setCreated_At(String Created_At) {
        this.Created_At = Created_At;
    }
}
