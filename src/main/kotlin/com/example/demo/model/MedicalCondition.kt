package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MedicalCondition(var medcon_id: Int = -1){
    val medConNameProperty = SimpleStringProperty(this, "medConName", "medConName")
    var medConName by medConNameProperty

    val medConDescProperty = SimpleStringProperty(this, "medConDesc", "medConDesc")
    var medConDesc by medConDescProperty
}