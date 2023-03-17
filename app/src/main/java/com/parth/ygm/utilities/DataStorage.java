package com.parth.ygm.utilities;

import com.parth.ygm.models.EmployeeData;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private List<EmployeeData> data = new ArrayList<>();

    public void add(EmployeeData employeeData) {
        data.add(employeeData);
    }

    public List<EmployeeData> getAll() {
        return data;
    }

    public void clearAll() {
        data.clear();
    }

    public boolean isInternetAvailable() {
        return false;
    }
}
