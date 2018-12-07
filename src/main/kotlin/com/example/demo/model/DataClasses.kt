package com.example.demo.model

import tornadofx.*
import java.sql.Date
import java.time.LocalDate

data class LiveDonor(val id: Int,
                     val don_data: Date,
                     val bloodGroup: String,
                     val fname: String,
                     val lname: String,
                     val dob:Date,
                     val phone: String,
                     val email: String)
data class ChartData(val bloodGroup: String, val count: Int)
data class PendingUser(val userName: String, val email: String)