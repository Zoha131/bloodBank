package com.example.demo.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import java.time.LocalDate
import tornadofx.*

class Donation(val don_id: Int = -1){

    var donor: Donor = Donor()

    val donDateProperty = SimpleObjectProperty<LocalDate>(this, "donDate", LocalDate.now())
    var donDate by donDateProperty

    val expiryDateProperty = SimpleObjectProperty<LocalDate>(this, "expDate", LocalDate.now().plusDays(40))
    var expiry by expiryDateProperty

    val dispatchedProperty = SimpleBooleanProperty(this, "dispatched", false)
    var dispatched by dispatchedProperty

    override fun toString(): String {
        return don_id.toString()
    }
}