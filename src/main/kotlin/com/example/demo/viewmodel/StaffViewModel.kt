package com.example.demo.viewmodel

import com.example.demo.model.Staff
import tornadofx.*

class StaffViewModel: ItemViewModel<Staff>() {

    val house = bind(Staff::houseProperty)
    val street = bind(Staff::streetProperty)
    val area = bind(Staff::areaProperty)
    val zipCod = bind(Staff::zipCodeProperty)
    val city = bind(Staff::cityProperty)
    val country = bind(Staff::countryProperty)

    val fname = bind(Staff::fNameProperty)
    val lName = bind(Staff::lNameProperty)
    val dob = bind(Staff::dobProperty)
    val mobile = bind(Staff::mobileProperty)
    val sex = bind(Staff::sexProperty)
    val email = bind(Staff::emailProperty)

    val cat_id = bind(Staff::catIdProperty)
    val eduBack = bind(Staff::eduBackProperty)
    val expDetails = bind(Staff::expDetailsProperty)
    val joinDate = bind(Staff::joinDateProperty)
    val resignDate = bind(Staff::resignDateProperty)
    val refDetails = bind(Staff::refDetailsProperty)
}