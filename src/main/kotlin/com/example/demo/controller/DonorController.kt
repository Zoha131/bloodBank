package com.example.demo.controller

import com.example.demo.Utility.JodaConverters
import com.example.demo.database.AddressTable
import com.example.demo.database.DonationTable
import com.example.demo.database.PersonTable
import com.example.demo.database.DonorTable
import com.example.demo.model.Donation
import com.example.demo.model.Donor
import org.apache.commons.io.IOUtils
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import tornadofx.*

class DonorController: Controller(){

    fun getDataList():ArrayList<Donor> {

        val DonorList = ArrayList<Donor>()

        transaction {

            com.example.demo.database.DonorTable.selectAll().forEach { it ->
                val Donor = Donor(it[DonorTable.dnr_id], it[DonorTable.per_id])

                Donor.bloodGroup = it[DonorTable.bld_group]


                com.example.demo.database.PersonTable.select{ PersonTable.per_id eq it[DonorTable.per_id]}.forEach { per ->
                    Donor.fName = per[PersonTable.f_name]
                    Donor.lName = per[PersonTable.l_name]
                    Donor.dob = JodaConverters.jodaToJavaLocalDate(per[PersonTable.dob].toLocalDate())
                    Donor.mobile = per[PersonTable.mobile_no]
                    Donor.sex = per[PersonTable.sex]
                    Donor.email = per[PersonTable.email]
                    Donor.add_id = per[PersonTable.add_id]

                    if(per[PersonTable.pic] != null)
                        Donor.pic = IOUtils.toByteArray(per[PersonTable.pic]?.binaryStream)
                }

                com.example.demo.database.AddressTable.select{ AddressTable.add_id eq Donor.add_id}.forEach { addrs->
                    Donor.house = addrs[AddressTable.house_no]
                    Donor.street = addrs[AddressTable.street]
                    Donor.area = addrs[AddressTable.area]
                    Donor.zipCod = addrs[AddressTable.zip_code]
                    Donor.city = addrs[AddressTable.city]
                    Donor.country = addrs[AddressTable.country]
                }

                DonorList.add( Donor)
            }


        }

        return  DonorList
    }

    fun updateItem(donor: Donor){

        transaction {
            com.example.demo.database.DonorTable.update({ DonorTable.dnr_id eq donor.dnr_id}) {
                it[DonorTable.bld_group] = donor.bloodGroup
            }

            com.example.demo.database.PersonTable.update({ PersonTable.per_id eq donor.per_id}) {
                it[f_name] = donor.fName
                it[l_name] = donor.lName
                it[mobile_no] = donor.mobile
                it[sex] = donor.sex
                it[email] = donor.email
                it[add_id] = donor.add_id
                it[dob] = DateTime(donor.dob.year, donor.dob.monthValue, donor.dob.dayOfMonth, 0,0)

            }

            com.example.demo.database.AddressTable.update({ AddressTable.add_id eq donor.add_id}){
                it[house_no] = donor.house
                it[street] = donor.street
                it[area] = donor.area
                it[zip_code] = donor.zipCod
                it[city] = donor.city
                it[country] = donor.country
            }
        }
    }

    fun newDonation(donor: Donor) {

        transaction {

            val don_id = DonationTable.insert {
                it[DonationTable.dispatched] = "No"
                it[DonationTable.dnr_id] = donor.dnr_id
                it[DonationTable.don_date] = DateTime.now()
                it[DonationTable.expiry_date] = DateTime.now().plus(40)

            }
        }
    }

    fun newItem(donor: Donor): Donor {

        transaction {

            val add_id = com.example.demo.database.AddressTable.insert {
                it[house_no] = donor.house
                it[street] = donor.street
                it[area] = donor.area
                it[zip_code] = donor.zipCod
                it[city] = donor.city
                it[country] = donor.country
            }

            donor.add_id = add_id.generatedKey!!.toInt()

            val per_id = com.example.demo.database.PersonTable.insert {
                it[f_name] = donor.fName
                it[l_name] = donor.lName
                it[mobile_no] = donor.mobile
                it[sex] = donor.sex
                it[email] = donor.email
                it[PersonTable.add_id] = donor.add_id
                it[dob] = DateTime(donor.dob.year, donor.dob.monthValue, donor.dob.dayOfMonth, 0,0)

            }

            val Donor_id = com.example.demo.database.DonorTable.insert {
                it[DonorTable.per_id] = per_id.generatedKey!!.toInt()
                it[DonorTable.bld_group] = donor.bloodGroup
            }
        }

        return donor
    }

    fun deleteItem(donor: Donor){

        transaction {
            com.example.demo.database.DonorTable.deleteWhere { DonorTable.dnr_id eq donor.dnr_id }
        }


    }
}