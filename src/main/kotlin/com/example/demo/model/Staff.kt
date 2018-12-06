package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.time.LocalDate

class Staff(val staff_id: Int = -1, per_id: Int = -1): Person(per_id){

    var category = StaffCategory()

    val eduBackProperty = SimpleStringProperty(this, "eduBack", "HSC")
    var eduBack by eduBackProperty

    val expDetailsProperty = SimpleStringProperty(this, "expDetails", "Entry Data")
    var expDetails by expDetailsProperty

    val refDetailsProperty = SimpleStringProperty(this, "refDetails", "Baker")
    var refDetails by refDetailsProperty

    val joinDateProperty = SimpleObjectProperty<LocalDate>(this, "joinDateProperty", LocalDate.now())
    var joinDate by joinDateProperty

    val resignDateProperty = SimpleObjectProperty<LocalDate>(this, "resignDate", LocalDate.now())
    var resignDate by resignDateProperty
}