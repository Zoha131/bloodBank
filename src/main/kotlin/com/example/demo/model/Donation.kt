package com.example.demo.model

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import java.time.LocalDate
import tornadofx.*

class Donation(val don_id: Int = -1){

    val donorIdProperty = SimpleIntegerProperty(this, "donationId", -1)
    var donorID by donorIdProperty

    val donDateProperty = SimpleObjectProperty<LocalDate>(this, "donDate", LocalDate.now())
    var donDate by donDateProperty

    val expiryDateProperty = SimpleObjectProperty<LocalDate>(this, "expDate", LocalDate.now().plusDays(40))
    var expiry by expiryDateProperty

    val dispatchedProperty = SimpleStringProperty(this, "dispatched", "false")
    var dispatched by dispatchedProperty

    override fun toString(): String {
        return don_id.toString()
    }
}