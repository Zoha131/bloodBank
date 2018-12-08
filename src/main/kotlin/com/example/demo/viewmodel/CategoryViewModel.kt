package com.example.demo.viewmodel

import com.example.demo.model.StaffCategory
import tornadofx.*

class CategoryViewModel: ItemViewModel<StaffCategory>(){

    val jobTitle = bind(StaffCategory::jobTitleProperty)
    val jobDesc = bind(StaffCategory::jobDescProperty)
    val eduReq = bind(StaffCategory::eduReqProperty)
    val expReq = bind(StaffCategory::expReqProperty)
    val salary = bind(StaffCategory::baseSalaryProperty)
}