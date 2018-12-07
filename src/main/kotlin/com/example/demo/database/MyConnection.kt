package com.example.demo.database

import org.jetbrains.exposed.sql.Database

class MyConnection {

    fun connect():Database {
        return Database.connect("jdbc:mysql://localhost:3306/blood_bank?useSSL=true", driver = "com.mysql.jdbc.Driver",user = "root", password = "root")
    }
}