package com.example.demo.model

import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

data class User(val id: Int,
                val name: String,
                val userName:String,
                val email:String,
                val password: String,
                val status: Status,
                val image : ByteArray = IOUtils.toByteArray(FileInputStream(File("E:\\work\\code\\tornadofx\\bloodBank\\src\\main\\resources\\user.jpg")))) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (userName != other.userName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (status != other.status) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
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