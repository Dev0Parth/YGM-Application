package com.parth.ygm.models;

public class LeavesData {
    String Emp_Id, Department, Name, From_Date, To_Date, Leave_Type, Leave_Reason, Created_At, half_leaves, full_leaves;

    public LeavesData() {
    }

    public LeavesData(String Emp_Id, String Department, String Name, String From_Date, String To_Date, String Leave_Type, String Leave_Reason, String Created_At, String half_leaves, String full_leaves) {
        this.Emp_Id = Emp_Id;
        this.Department = Department;
        this.Name = Name;
        this.From_Date = From_Date;
        this.To_Date = To_Date;
        this.Leave_Type = Leave_Type;
        this.Leave_Reason = Leave_Reason;
        this.Created_At = Created_At;
        this.half_leaves = half_leaves;
        this.full_leaves = full_leaves;
    }

    public String getEmp_Id() {
        return Emp_Id;
    }

    public void setEmp_Id(String Emp_Id) {
        this.Emp_Id = Emp_Id;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getFrom_Date() {
        return From_Date;
    }

    public void setFrom_Date(String From_Date) {
        this.From_Date = From_Date;
    }

    public String getTo_Date() {
        return To_Date;
    }

    public void setTo_Date(String To_Date) {
        this.To_Date = To_Date;
    }

    public String getLeave_Type() {
        return Leave_Type;
    }

    public void setLeave_Type(String Leave_Type) {
        this.Leave_Type = Leave_Type;
    }

    public String getLeave_Reason() {
        return Leave_Reason;
    }

    public void setLeave_Reason(String Leave_Reason) {
        this.Leave_Reason = Leave_Reason;
    }

    public String getCreated_At() {
        return Created_At;
    }

    public void setCreated_At(String Created_At) {
        this.Created_At = Created_At;
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
