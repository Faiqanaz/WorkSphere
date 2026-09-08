package com.worksphere.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AttendanceRecord {
    private Employee employee;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public AttendanceRecord(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }

    public StringProperty getEmployeeNameProperty() {
        return new SimpleStringProperty(employee.getName());
    }

    public StringProperty getTimeInProperty() {
        return new SimpleStringProperty(timeIn != null ? timeIn.format(formatter) : "");
    }

    public StringProperty getTimeOutProperty() {
        return new SimpleStringProperty(timeOut != null ? timeOut.format(formatter) : "");
    }
}