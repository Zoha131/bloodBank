package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Donor(val dnr_id: Int = -1, per_id: Int=-1):Person(per_id){

    val bloodGroupProperty = SimpleStringProperty(this, "bloodGroup", "A+")
    var bloodGroup by bloodGroupProperty
}