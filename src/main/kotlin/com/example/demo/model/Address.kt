package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

open class Address(var add_id: Int = -1){
    val houseProperty = SimpleStringProperty(this, "23/1", "")
    var house by houseProperty

    val streetProperty = SimpleStringProperty(this, "Shadhinata Sharani", " ")
    var street by streetProperty

    val areaProperty = SimpleStringProperty(this, "Uttor Badda", " ")
    var area by areaProperty

    val zipCodeProperty = SimpleStringProperty(this, "1200", "")
    var zipCod by zipCodeProperty

    val cityProperty = SimpleStringProperty(this, "Dhaka", "")
    var city by cityProperty

    val countryProperty = SimpleStringProperty(this, "Bangladesh", "")
    var country by countryProperty
}