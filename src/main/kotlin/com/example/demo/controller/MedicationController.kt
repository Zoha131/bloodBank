package com.example.demo.controller


import com.example.demo.database.MedicationTable
import com.example.demo.model.Medication
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import tornadofx.*

class MedicationController : Controller(){

    fun getDataList():ArrayList<Medication> {

        val staffList = ArrayList<Medication>()

        transaction {
            MedicationTable.selectAll().forEach {
                val medication = Medication(it[MedicationTable.med_id])

                medication.medNamed = it[MedicationTable.med_name]
                medication.medDesc = it[MedicationTable.med_descr]
                medication.medGuide = it[MedicationTable.med_guide]

                staffList.add(medication)
            }

        }

        return  staffList
    }

    fun updateItem(medication: Medication){

        transaction {

            MedicationTable.update({MedicationTable.med_id eq medication.med_id}) {
                it[MedicationTable.med_name] = medication.medNamed
                it[MedicationTable.med_descr] = medication.medDesc
                it[MedicationTable.med_guide] = medication.medGuide
            }

        }
    }

    fun newItem(medication: Medication): Medication {

        transaction {
            val med_id = MedicationTable.insert {
                it[MedicationTable.med_name] = medication.medNamed
                it[MedicationTable.med_descr] = medication.medDesc
                it[MedicationTable.med_guide] = medication.medGuide
            }

            medication.med_id = med_id.generatedKey!!.toInt()

        }

        return medication
    }

    fun deleteItem(staff: Medication){


        transaction {
            MedicationTable.deleteWhere { MedicationTable.med_id eq staff.med_id }
        }


    }
}