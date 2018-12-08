package com.example.demo.controller

import com.example.demo.database.CategoryTable
import com.example.demo.database.StaffTable
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import tornadofx.*

class CategoryController: Controller(){


    fun getDataList():ArrayList<StaffCategory> {

        val staffCatList = ArrayList<StaffCategory>()

        /*TransactionManager
                .currentOrNew(1)
                .exec(""" SELECT * FROM blood_bank.staff_category;""") { rs ->

                    val result = arrayListOf<Pair<String, String>>()
                    while (rs.next()) {

                        val staffCategory = StaffCategory(rs.getInt("cat_id"))
                        staffCategory.jobTitle = rs.getString("job_title")
                        staffCategory.eduReq = rs.getString("edu_req")
                        staffCategory.jobDesc = rs.getString("job_descr")
                        staffCategory.expReq = rs.getString("exp_req")
                        staffCategory.baseSalary = rs.getInt("base_salary")

                        staffCatList.add(staffCategory)
                    }

                    result
                }*/

        transaction {
            CategoryTable.selectAll().forEach {

                val category = StaffCategory(it[CategoryTable.cat_id])

                category.jobTitle = it[CategoryTable.job_title]
                category.jobDesc = it[CategoryTable.job_descr]
                category.eduReq = it[CategoryTable.edu_req]
                category.expReq = it[CategoryTable.exp_req]
                category.baseSalary = it[CategoryTable.base_salary]

                staffCatList.add(category)
            }
        }

        return staffCatList
    }

    fun newItem(category: StaffCategory): StaffCategory{

        transaction {

            val cat_id = CategoryTable.insert {
                it[CategoryTable.job_title] = category.jobTitle
                it[CategoryTable.job_descr] = category.jobDesc
                it[CategoryTable.edu_req] = category.eduReq
                it[CategoryTable.exp_req] = category.expReq
                it[CategoryTable.base_salary] = category.baseSalary
            }

            category.cat_id = cat_id.generatedKey!!.toInt()
        }

        return category
    }

    fun updateItem(category: StaffCategory){

        transaction {
            CategoryTable.update({CategoryTable.cat_id eq category.cat_id}) {
                it[CategoryTable.job_title] = category.jobTitle
                it[CategoryTable.job_descr] = category.jobDesc
                it[CategoryTable.edu_req] = category.eduReq
                it[CategoryTable.exp_req] = category.expReq
                it[CategoryTable.base_salary] = category.baseSalary
            }
        }
    }

    fun deleteItem(category: StaffCategory){

        transaction {
            StaffTable.deleteWhere { CategoryTable.cat_id eq category.cat_id}
        }


    }
}