package com.example.demo.model

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class StaffCategory(val cat_id: Int = -1){

    val jobTitleProperty = SimpleStringProperty(this, "jobTitle", "Data Operator")
    var jobTitle by jobTitleProperty

    val jobDescProperty = SimpleStringProperty(this, "jobDesc", "Entry Data")
    var jobDesc by jobDescProperty

    val eduReqProperty = SimpleStringProperty(this, "eduReq", "BSc in Marin")
    var eduReq by eduReqProperty

    val expReqProperty = SimpleStringProperty(this, "expReq", "4 Years")
    var expReq by expReqProperty

    val baseSalaryProperty = SimpleDoubleProperty(this, "Base Salary", 0.0)
    var baseSalary by baseSalaryProperty

    override fun toString(): String {
        return jobTitle
    }
}