package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class Medication(val med_id: Int = -1){
    val medNamedProperty = SimpleStringProperty(this, "medNamed", "medNamed")
    var medNamed by medNamedProperty

    val medDescProperty = SimpleStringProperty(this, "medDesc", "medDesc")
    var medDesc by medDescProperty

    val medGuideProperty = SimpleStringProperty(this, "medGuide", "medGuide")
    var medGuide by medGuideProperty
}