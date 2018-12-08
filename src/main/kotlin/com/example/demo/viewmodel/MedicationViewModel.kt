package com.example.demo.viewmodel

import com.example.demo.model.Medication
import tornadofx.*

class MedicationViewModel : ItemViewModel<Medication>(){
    val medName = bind(Medication::medNamedProperty)
    val medDesc = bind(Medication::medDescProperty)
    val medGuide = bind(Medication::medGuideProperty)
}