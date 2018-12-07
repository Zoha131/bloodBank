package com.example.demo.controller

import com.example.demo.Utility.JodaConverters
import com.example.demo.database.CategoryTable
import com.example.demo.database.StaffTable
import com.example.demo.model.Address
import com.example.demo.model.Person
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class BaseController : Controller(){


    fun getStaffMap():Map<Int, Staff> {

        val staffList = HashMap<Int, Staff>()

        transaction {


            StaffTable.selectAll().forEach { it ->
                val staff = Staff(it[StaffTable.staff_id], it[StaffTable.per_id])

                staff.eduBack = it[StaffTable.edu_back]
                staff.expDetails = it[StaffTable.exp_details]
                staff.joinDate = JodaConverters.jodaToJavaLocalDate(it[StaffTable.join_date].toLocalDate())
                staff.refDetails = it[StaffTable.ref_details]
                //staff.category = StaffCategory(it[StaffTable.cat_id]!!)

                staffList[staff.staff_id] = staff
            }


        }

        return  staffList
    }

    fun getPersonMap():Map<Int, Person>{
        val personMap = HashMap<Int, Person>()


        return personMap
    }

    fun getStaffCategoryMap():Map<Int, StaffCategory> {
        val categoryMap = HashMap<Int, StaffCategory>()

        transaction {
            CategoryTable.selectAll().forEach {
                val category = StaffCategory(it[CategoryTable.cat_id])
                category.jobTitle = it[CategoryTable.job_title]
                category.jobDesc = it[CategoryTable.job_descr]
                category.eduReq = it[CategoryTable.edu_req]
                category.expReq = it[CategoryTable.exp_req]
                category.baseSalary = it[CategoryTable.base_salary]

                categoryMap[category.cat_id] = category
            }
        }

        return categoryMap
    }
}