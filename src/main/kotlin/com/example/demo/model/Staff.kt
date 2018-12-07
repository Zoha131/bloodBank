package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

import tornadofx.*
import java.time.LocalDate

class Staff(val staff_id: Int = -1, per_id: Int = -1): Person(per_id){

    val catIdProperty = SimpleIntegerProperty(this, "catID", 100001)
    var cat_id by catIdProperty

    val eduBackProperty = SimpleStringProperty(this, "eduBack", "")
    var eduBack by eduBackProperty

    val expDetailsProperty = SimpleStringProperty(this, "expDetails", " ")
    var expDetails by expDetailsProperty

    val refDetailsProperty = SimpleStringProperty(this, "refDetails", "")
    var refDetails by refDetailsProperty

    val joinDateProperty = SimpleObjectProperty<LocalDate>(this, "joinDateProperty", LocalDate.now())
    var joinDate by joinDateProperty

    val resignDateProperty = SimpleObjectProperty<LocalDate>(this, "resignDate", LocalDate.now())
    var resignDate by resignDateProperty
}