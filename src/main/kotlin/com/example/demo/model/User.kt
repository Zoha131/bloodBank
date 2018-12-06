package com.example.demo.model

import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class User(val u_id:Int = -1, p_id:Int = -2): Person(p_id) {



    val uNameProperty = SimpleStringProperty(this, "uName", "nowroz131")
    var uName by uNameProperty

    val passProperty = SimpleStringProperty(this, "pass", "12345")
    var pass by passProperty

    val statusProperty = SimpleStringProperty(this, "status", Status.Pending.toString())
    var status by statusProperty

    val accessTypeProperty = SimpleStringProperty(this, "accessType", AccessType.RecordManager.toString())
    var accessType by accessTypeProperty
}

sealed class Status {
    object Pending: Status() {
        override fun toString(): String {
            return "Pending"
        }
    }
    object Allowed: Status(){
        override fun toString(): String {
            return "Allowed"
        }
    }
    object Blocked: Status(){
        override fun toString(): String {
            return "Blocked"
        }
    }
}

sealed class AccessType {
    object Admin: AccessType() {
        override fun toString(): String {
            return "Admin"
        }
    }
    object RecordManager: AccessType(){
        override fun toString(): String {
            return "RecordManager"
        }
    }
}