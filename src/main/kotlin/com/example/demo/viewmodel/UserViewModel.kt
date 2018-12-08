package com.example.demo.viewmodel

import com.example.demo.model.User
import tornadofx.*

class UserViewModel: ItemViewModel<User>(){
    val fname = bind(User::fNameProperty)
    val lname = bind(User::lNameProperty)
    val email = bind(User::emailProperty)
    val sex = bind(User::sexProperty)
    val password = bind(User::passProperty)
    val dob = bind(User::dobProperty)
}