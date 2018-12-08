package com.example.demo.viewmodel

import com.example.demo.model.BloodReciever
import tornadofx.*

class RecieverViewModel : ItemViewModel<BloodReciever>() {

    val house = bind(BloodReciever::houseProperty)
    val street = bind(BloodReciever::streetProperty)
    val area = bind(BloodReciever::areaProperty)
    val zipCod = bind(BloodReciever::zipCodeProperty)
    val city = bind(BloodReciever::cityProperty)
    val country = bind(BloodReciever::countryProperty)

    val fname = bind(BloodReciever::fNameProperty)
    val lName = bind(BloodReciever::lNameProperty)
    val dob = bind(BloodReciever::dobProperty)
    val mobile = bind(BloodReciever::mobileProperty)
    val sex = bind(BloodReciever::sexProperty)
    val email = bind(BloodReciever::emailProperty)

    val don_id = bind(BloodReciever::donationIdProperty)
    val rec_date = bind(BloodReciever::recDateProperty)
}