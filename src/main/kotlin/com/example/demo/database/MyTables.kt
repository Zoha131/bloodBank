package com.example.demo.database

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.sql.Blob

object AddressTable : Table("address") {
    val add_id: Column<Int> = integer("add_id").autoIncrement().primaryKey()
    val house_no: Column<String> = varchar("house_no", 10)
    val street: Column<String> = varchar("street", 255)
    val area: Column<String> = varchar("area", 255).default("Mohammadpur")
    val zip_code: Column<String> = varchar("zip_code", 16)
    val city: Column<String> = varchar("city", 255)
    val country: Column<String> = varchar("country", 74)
}

object PersonTable : Table("person") {
    val per_id: Column<Int> = integer("per_id").autoIncrement().primaryKey()
    val add_id: Column<Int> = reference("add_id", AddressTable.add_id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.SET_NULL)
    val f_name: Column<String> = varchar("f_name", 30)
    val l_name: Column<String> = varchar("l_name", 30)
    val dob: Column<DateTime> = date("dob")
    val mobile_no: Column<String> = varchar("mobile_no", 20)
    val pic: Column<Blob?> = blob("pic").nullable()
    val sex: Column<String> = varchar("sex", 6)
    val email: Column<String> = varchar("email", 254)
}

object UserTable : Table("user") {
    val u_id: Column<Int> = integer("u_id").autoIncrement().primaryKey()
    val per_id: Column<Int> = reference("per_id", PersonTable.per_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val u_name: Column<String> = varchar("u_name", 255)
    val pass: Column<String> = varchar("pass", 255)
    val status: Column<String> = varchar("status", 10)
    val access_level: Column<String> = varchar("access_level", 10)
}

object CategoryTable : Table("staff_category") {
    val cat_id: Column<Int> = integer("cat_id").autoIncrement().primaryKey()
    val job_title: Column<String> = varchar("job_title", 255)
    val job_descr: Column<String?> = text("job_descr").nullable()
    val edu_req: Column<String?> = text("edu_req").nullable()
    val exp_req: Column<String?> = text("exp_req").nullable()
    val base_salary: Column<Int> = integer("base_salary")
}

object StaffTable : Table("staff") {
    val staff_id: Column<Int> = integer("staff_id").autoIncrement().primaryKey()
    val per_id: Column<Int> = reference("per_id", PersonTable.per_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val cat_id: Column<Int> = reference("cat_id", CategoryTable.cat_id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.SET_NULL)
    val edu_back: Column<String> = text("edu_back")
    val exp_details: Column<String> = text("exp_details")
    val ref_details: Column<String> = text("ref_details")
    val join_date: Column<DateTime> = date("join_date")
    val resign_date: Column<DateTime?> = date("resign_date").nullable()
}

object DonorTable : Table("donor") {
    val dnr_id: Column<Int> = integer("dnr_id").autoIncrement().primaryKey()
    val per_id: Column<Int> = reference("per_id", PersonTable.per_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val bld_group: Column<String> = varchar("bld_group", 3)
}

object MedicationTable : Table("medication") {
    val med_id: Column<Int> = integer("med_id").autoIncrement().primaryKey()
    val med_name: Column<String> = varchar("med_name", 255)
    val med_descr: Column<String> = text("med_descr")
    val med_guide: Column<String> = text("med_guide")
}

object DnrMedicationTable : Table("dnr_medication") {
    val dnr_id: Column<Int> = reference("dnr_id", DonorTable.dnr_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val med_id: Column<Int> = reference("cat_id", MedicationTable.med_id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.SET_NULL)
}

object MedConditionTable : Table("medical_condition") {
    val medcon_id: Column<Int> = integer("medcon_id").autoIncrement().primaryKey()
    val medcon_name: Column<String> = varchar("medcon_name", 255)
    val medcon_descr: Column<String> = text("medcon_descr")
}

object DnrMedConTable : Table("dnr_medcon") {
    val dnr_id: Column<Int> = reference("dnr_id", DonorTable.dnr_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val medcon_id: Column<Int> = reference("medcon_id", MedConditionTable.medcon_id, onUpdate = ReferenceOption.CASCADE, onDelete = ReferenceOption.SET_NULL)
    val severity: Column<String> = varchar("severity", 255)
}

object DonationTable : Table("donation") {
    val don_id: Column<Int> = integer("don_id").autoIncrement().primaryKey()
    val dnr_id: Column<Int> = reference("dnr_id", DonorTable.dnr_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val don_date: Column<DateTime> = date("don_date")
    val expiry_date: Column<DateTime> = date("expiry_date")
    val dispatched: Column<String> = varchar("dispatched", 10)
}

object ReceiverTable : Table("blood_receiver") {
    val rec_id: Column<Int> = integer("rec_id").autoIncrement().primaryKey()
    val don_id: Column<Int> = reference("don_id", DonationTable.don_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val per_id: Column<Int> = reference("per_id", PersonTable.per_id, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val rec_date: Column<DateTime> = date("rec_date")
}