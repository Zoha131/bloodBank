package com.example.demo.controller

import com.example.demo.database.MedConditionTable
import com.example.demo.database.MedicationTable
import com.example.demo.model.MedicalCondition
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import tornadofx.*

class MedConditionController: Controller(){

    fun getDataList():ArrayList<MedicalCondition> {

        val staffList = ArrayList<MedicalCondition>()

        transaction {
            MedConditionTable.selectAll().forEach {
                val medication = MedicalCondition(it[MedConditionTable.medcon_id])

                medication.medConName = it[MedConditionTable.medcon_name]
                medication.medConDesc = it[MedConditionTable.medcon_descr]

                staffList.add(medication)
            }

        }

        return  staffList
    }

    fun updateItem(medication: MedicalCondition){

        transaction {

            MedConditionTable.update({ MedConditionTable.medcon_id eq medication.medcon_id}) {
                it[MedConditionTable.medcon_name] = medication.medConName
                it[MedConditionTable.medcon_descr] = medication.medConDesc
            }

        }
    }

    fun newItem(medication: MedicalCondition): MedicalCondition {

        transaction {
            val med_id = MedConditionTable.insert {
                it[MedConditionTable.medcon_name] = medication.medConName
                it[MedConditionTable.medcon_descr] = medication.medConDesc
            }

            medication.medcon_id = med_id.generatedKey!!.toInt()

        }

        return medication
    }

    fun deleteItem(staff: MedicalCondition){


        transaction {
            MedConditionTable.deleteWhere { MedConditionTable.medcon_id eq staff.medcon_id }
        }


    }
}