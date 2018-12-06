package com.example.demo.model

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import org.apache.commons.io.IOUtils
import tornadofx.*
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.util.*

open class Person(val per_id:Int = -1) {

    var address: Address = Address()

    val fNameProperty = SimpleStringProperty(this, "Nowroz", "Nowroz")
    var fName by fNameProperty

    val lNameProperty = SimpleStringProperty(this, "Islam", "Islam")
    var lName by lNameProperty

    val dobProperty = SimpleObjectProperty<LocalDate>(this, "DOB", LocalDate.now())
    var dob by dobProperty

    val mobileProperty = SimpleStringProperty(this, "Mobile", "0174785658")
    var mobile by mobileProperty

    val sexProperty = SimpleStringProperty(this, "Sex", "Male")
    var sex by sexProperty

    var pic: ByteArray = IOUtils.toByteArray(FileInputStream(File("src\\main\\resources\\logo\\user.jpg")))

    val emailProperty = SimpleStringProperty(this, "Email", "nowroz@diu.edu.bd")
    var email by emailProperty
}