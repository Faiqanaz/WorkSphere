package com.worksphere.controller;


import java.time.LocalDateTime;

import com.worksphere.model.AttendanceRecord;
import com.worksphere.model.Employee;

import javafx.collections.ObservableList;

public class LoginController {

    private ObservableList<Employee> employees;
    private ObservableList<AttendanceRecord> attendanceRecords;

    public LoginController(ObservableList<Employee> employees, ObservableList<AttendanceRecord> attendanceRecords) {
        this.employees = employees;
        this.attendanceRecords = attendanceRecords;
    }

    public Employee login(String enteredId, String enteredPassword) {
        Employee user = employees.stream()
                .filter(emp -> emp.getId().equalsIgnoreCase(enteredId) &&
                        emp.getPassword().equals(enteredPassword))
                .findFirst().orElse(null);

        if (user != null && !user.isAdmin()) {
            AttendanceRecord record = attendanceRecords.stream()
                    .filter(r -> r.getEmployee().getId().equals(user.getId()))
                    .findFirst().orElse(null);
            if (record != null && record.getTimeIn() == null) {
                record.setTimeIn(LocalDateTime.now());
            }
        }

        return user;
    }

    public void logout(Employee employee) {
        AttendanceRecord record = attendanceRecords.stream()
                .filter(r -> r.getEmployee().getId().equals(employee.getId()))
                .findFirst().orElse(null);
        if (record != null) {
            record.setTimeOut(LocalDateTime.now());
        }
    }
}