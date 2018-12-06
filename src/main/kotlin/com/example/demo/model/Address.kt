package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Address(val id: Int = -1){
    val houseProperty = SimpleStringProperty(this, "23/1", "23/1")
    var house by houseProperty

    val streetProperty = SimpleStringProperty(this, "Shadhinata Sharani", "Shadhinata Sharani")
    var street by streetProperty

    val areaProperty = SimpleStringProperty(this, "Uttor Badda", "Uttor Badda")
    var area by areaProperty

    val zipCodeProperty = SimpleStringProperty(this, "1200", "1200")
    var zipCod by zipCodeProperty

    val cityProperty = SimpleStringProperty(this, "Dhaka", "Dhaka")
    var city by cityProperty

    val countryProperty = SimpleStringProperty(this, "Bangladesh", "Bangladesh")
    var country by countryProperty
}