package com.worksphere.controller;

import java.time.LocalDate;

import com.worksphere.model.AttendanceRecord;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class AttendanceController {

    private TableView<AttendanceRecord> tableView;
    private DatePicker calendar;
    private ObservableList<AttendanceRecord> attendanceRecords;

    public AttendanceController(TableView<AttendanceRecord> tableView, DatePicker calendar,
            ObservableList<AttendanceRecord> records) {
        this.tableView = tableView;
        this.calendar = calendar;
        this.attendanceRecords = records;
        initialize();
    }

    private void initialize() {
        tableView.setItems(attendanceRecords);
        calendar.setValue(LocalDate.now());
    }
}