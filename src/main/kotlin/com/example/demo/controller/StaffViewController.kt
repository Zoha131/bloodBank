package com.example.demo.controller

import com.example.demo.Utility.JodaConverters
import com.example.demo.database.AddressTable
import com.example.demo.database.CategoryTable
import com.example.demo.database.PersonTable
import com.example.demo.database.StaffTable
import com.example.demo.model.Address
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import org.apache.commons.io.IOUtils
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.LocalDate
import tornadofx.*

class StaffViewController : Controller(){

    fun getDataList():ArrayList<Staff> {

        val staffList = ArrayList<Staff>()

        transaction {

            StaffTable.selectAll().forEach { it ->
                val staff = Staff(it[StaffTable.staff_id], it[StaffTable.per_id])

                staff.eduBack = it[StaffTable.edu_back]
                staff.expDetails = it[StaffTable.exp_details]
                staff.joinDate = JodaConverters.jodaToJavaLocalDate(it[StaffTable.join_date].toLocalDate())
                staff.refDetails = it[StaffTable.ref_details]
                staff.cat_id = it[StaffTable.cat_id]
                if(it[StaffTable.resign_date]!= null)
                    staff.resignDate = JodaConverters.jodaToJavaLocalDate(it[StaffTable.resign_date]?.toLocalDate())


                PersonTable.select{PersonTable.per_id eq it[StaffTable.per_id]}.forEach {per ->
                    staff.fName = per[PersonTable.f_name]
                    staff.lName = per[PersonTable.l_name]
                    staff.dob = JodaConverters.jodaToJavaLocalDate(per[PersonTable.dob].toLocalDate())
                    staff.mobile = per[PersonTable.mobile_no]
                    staff.sex = per[PersonTable.sex]
                    staff.email = per[PersonTable.email]
                    staff.add_id = per[PersonTable.add_id]

                    if(per[PersonTable.pic] != null)
                        staff.pic = IOUtils.toByteArray(per[PersonTable.pic]?.binaryStream)
                }

                AddressTable.select{AddressTable.add_id eq staff.add_id}.forEach {addrs->
                    staff.house = addrs[AddressTable.house_no]
                    staff.street = addrs[AddressTable.street]
                    staff.area = addrs[AddressTable.area]
                    staff.zipCod = addrs[AddressTable.zip_code]
                    staff.city = addrs[AddressTable.city]
                    staff.country = addrs[AddressTable.country]
                }

                staffList.add( staff)
            }


        }

        return  staffList
    }

    fun updateItem(staff: Staff){

        transaction {
            StaffTable.update({StaffTable.staff_id eq staff.staff_id}) {
                it[StaffTable.edu_back] = staff.eduBack
                it[StaffTable.exp_details] = staff.expDetails
                it[StaffTable.ref_details] = staff.refDetails
                it[StaffTable.cat_id] = staff.cat_id
                it[StaffTable.join_date] = DateTime(staff.joinDate.year, staff.joinDate.monthValue, staff.joinDate.dayOfMonth, 0,0)
                it[StaffTable.resign_date] = DateTime(staff.resignDate.year, staff.resignDate.monthValue, staff.resignDate.dayOfMonth, 0,0)
            }

            PersonTable.update({PersonTable.per_id eq staff.per_id}) {
                it[PersonTable.f_name] = staff.fName
                it[PersonTable.l_name] = staff.lName
                it[PersonTable.mobile_no] = staff.mobile
                it[PersonTable.sex] = staff.sex
                it[PersonTable.email] = staff.email
                it[PersonTable.add_id] = staff.add_id
                it[PersonTable.dob] = DateTime(staff.dob.year, staff.dob.monthValue, staff.dob.dayOfMonth, 0,0)

            }

            AddressTable.update({AddressTable.add_id eq staff.add_id}){
                it[AddressTable.house_no] = staff.house
                it[AddressTable.street] = staff.street
                it[AddressTable.area] = staff.area
                it[AddressTable.zip_code] = staff.zipCod
                it[AddressTable.city] = staff.city
                it[AddressTable.country] = staff.country
            }
        }
    }

    fun newItem(staff: Staff): Staff{

        transaction {

            val add_id = AddressTable.insert {
                it[AddressTable.house_no] = staff.house
                it[AddressTable.street] = staff.street
                it[AddressTable.area] = staff.area
                it[AddressTable.zip_code] = staff.zipCod
                it[AddressTable.city] = staff.city
                it[AddressTable.country] = staff.country
            }

            staff.add_id = add_id.generatedKey!!.toInt()

            val per_id = PersonTable.insert {
                it[PersonTable.f_name] = staff.fName
                it[PersonTable.l_name] = staff.lName
                it[PersonTable.mobile_no] = staff.mobile
                it[PersonTable.sex] = staff.sex
                it[PersonTable.email] = staff.email
                it[PersonTable.add_id] = staff.add_id
                it[PersonTable.dob] = DateTime(staff.dob.year, staff.dob.monthValue, staff.dob.dayOfMonth, 0,0)

            }

            val staff_id = StaffTable.insert {
                it[StaffTable.per_id] = per_id.generatedKey!!.toInt()
                it[StaffTable.edu_back] = staff.eduBack
                it[StaffTable.exp_details] = staff.expDetails
                it[StaffTable.ref_details] = staff.refDetails
                it[StaffTable.cat_id] = staff.cat_id
                it[StaffTable.join_date] = DateTime(staff.joinDate.year, staff.joinDate.monthValue, staff.joinDate.dayOfMonth, 0,0)
                it[StaffTable.resign_date] = DateTime(staff.resignDate.year, staff.resignDate.monthValue, staff.resignDate.dayOfMonth, 0,0)
            }
        }

        return staff
    }

    fun deleteItem(staff: Staff){

        println(staff.staff_id)

        transaction {
            StaffTable.deleteWhere { StaffTable.staff_id eq staff.staff_id }
        }


    }
}


/*fun getDataList():ArrayList<StaffCategory> {

        val staffCatList = ArrayList<StaffCategory>()

        transaction {
            CategoryTable.selectAll().forEach {

                val category = StaffCategory(it[CategoryTable.cat_id])

                category.jobTitle = it[CategoryTable.job_title]
                category.jobDesc = it[CategoryTable.job_descr]
                category.eduReq = it[CategoryTable.edu_req]
                category.expReq = it[CategoryTable.exp_req]
                category.baseSalary = it[CategoryTable.base_salary]

                staffCatList.add(category)
                println(category.cat_id)
            }
        }

        return staffCatList
    }*/