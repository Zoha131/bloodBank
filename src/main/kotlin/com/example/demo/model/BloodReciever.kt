package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import java.time.LocalDate
import tornadofx.*

class BloodReciever(val rec_id:Int = -1, per_id: Int = -1): Person(per_id){

    var donation = Donation()

    val recDateProperty = SimpleObjectProperty<LocalDate>(this, "donDate", LocalDate.now().plusDays(40))
    var recieve by recDateProperty
}