package com.example.demo.viewmodel

import com.example.demo.model.MedicalCondition
import com.example.demo.model.Medication
import tornadofx.*

class MedConViewModel: ItemViewModel<MedicalCondition>(){
    val medName = bind(MedicalCondition::medConNameProperty)
    val medDesc = bind(MedicalCondition::medConDescProperty)
}