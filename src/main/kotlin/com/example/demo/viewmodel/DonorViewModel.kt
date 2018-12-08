package com.example.demo.viewmodel

import com.example.demo.model.Donor
import tornadofx.*

class DonorViewModel: ItemViewModel<Donor>() {

    val house = bind(Donor::houseProperty)
    val street = bind(Donor::streetProperty)
    val area = bind(Donor::areaProperty)
    val zipCod = bind(Donor::zipCodeProperty)
    val city = bind(Donor::cityProperty)
    val country = bind(Donor::countryProperty)

    val fname = bind(Donor::fNameProperty)
    val lName = bind(Donor::lNameProperty)
    val dob = bind(Donor::dobProperty)
    val mobile = bind(Donor::mobileProperty)
    val sex = bind(Donor::sexProperty)
    val email = bind(Donor::emailProperty)

    val bloodGroup = bind(Donor::bloodGroupProperty)
}