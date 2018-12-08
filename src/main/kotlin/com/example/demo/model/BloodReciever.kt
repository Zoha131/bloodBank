package com.example.demo.model

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import java.time.LocalDate
import tornadofx.*

class BloodReciever(val rec_id:Int = -1, per_id: Int = -1): Person(per_id){

    val donationIdProperty = SimpleIntegerProperty(this, "donationId", -1)
    var donId by donationIdProperty

    val recDateProperty = SimpleObjectProperty<LocalDate>(this, "donDate", LocalDate.now().plusDays(40))
    var recDate by recDateProperty
}