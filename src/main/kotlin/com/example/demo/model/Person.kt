package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.commons.io.IOUtils
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.util.*

open class Person(var per_id:Int = -1, add_id: Int =- 1): Address(add_id) {

    val fNameProperty = SimpleStringProperty(this, "Nowroz", "")
    var fName by fNameProperty

    val lNameProperty = SimpleStringProperty(this, "Islam", "")
    var lName by lNameProperty

    val dobProperty = SimpleObjectProperty<LocalDate>(this, "DOB", LocalDate.now())
    var dob by dobProperty

    val mobileProperty = SimpleStringProperty(this, "Mobile", "")
    var mobile by mobileProperty

    val sexProperty = SimpleStringProperty(this, "Sex", "")
    var sex by sexProperty

    var pic: ByteArray = IOUtils.toByteArray(FileInputStream(File("src\\main\\resources\\logo\\user.jpg")))

    val emailProperty = SimpleStringProperty(this, "Email", "")
    var email by emailProperty
}