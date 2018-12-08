package com.example.demo.controller

import com.example.demo.Utility.JodaConverters
import com.example.demo.database.AddressTable
import com.example.demo.database.DonationTable
import com.example.demo.database.PersonTable
import com.example.demo.database.ReceiverTable
import com.example.demo.model.BloodReciever
import com.example.demo.model.Donation
import org.apache.commons.io.IOUtils
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import tornadofx.*

class RecieverController : Controller(){

    fun getDataList():ArrayList<BloodReciever> {

        val staffList = ArrayList<BloodReciever>()

        transaction {

            com.example.demo.database.ReceiverTable.selectAll().forEach { it ->
                val reciever = BloodReciever(it[ReceiverTable.rec_id], it[ReceiverTable.per_id])
                

                reciever.donId = it[ReceiverTable.don_id]
                reciever.recDate = JodaConverters.jodaToJavaLocalDate(it[ReceiverTable.rec_date].toLocalDate())


                com.example.demo.database.PersonTable.select{ PersonTable.per_id eq it[ReceiverTable.per_id]}.forEach { per ->
                    reciever.fName = per[PersonTable.f_name]
                    reciever.lName = per[PersonTable.l_name]
                    reciever.dob = JodaConverters.jodaToJavaLocalDate(per[PersonTable.dob].toLocalDate())
                    reciever.mobile = per[PersonTable.mobile_no]
                    reciever.sex = per[PersonTable.sex]
                    reciever.email = per[PersonTable.email]
                    reciever.add_id = per[PersonTable.add_id]

                    if(per[PersonTable.pic] != null)
                        reciever.pic = IOUtils.toByteArray(per[PersonTable.pic]?.binaryStream)
                }

                com.example.demo.database.AddressTable.select{ AddressTable.add_id eq reciever.add_id}.forEach { addrs->
                    reciever.house = addrs[AddressTable.house_no]
                    reciever.street = addrs[AddressTable.street]
                    reciever.area = addrs[AddressTable.area]
                    reciever.zipCod = addrs[AddressTable.zip_code]
                    reciever.city = addrs[AddressTable.city]
                    reciever.country = addrs[AddressTable.country]
                }

                staffList.add( reciever)
            }


        }

        return  staffList
    }

    fun getDonationList():ArrayList<Donation> {
        val dataList = ArrayList<Donation>()

        transaction {
            DonationTable.selectAll().forEach {
                val donation = Donation(it[DonationTable.don_id])

                donation.dispatched = it[DonationTable.dispatched]
                donation.donorID = it[DonationTable.dnr_id]
                donation.donDate = JodaConverters.jodaToJavaLocalDate(it[DonationTable.don_date].toLocalDate())
                donation.expiry = JodaConverters.jodaToJavaLocalDate(it[DonationTable.expiry_date].toLocalDate())

                if(donation.dispatched.equals("No", true))
                    dataList.add(donation)
            }
        }

        return dataList
    }

    fun updateItem(reciever: BloodReciever){

        transaction {
            ReceiverTable.update({ ReceiverTable.rec_id eq reciever.rec_id}) {

                it[ReceiverTable.don_id] = reciever.donId
                it[ReceiverTable.rec_date] = DateTime(reciever.recDate.year, reciever.recDate.monthValue, reciever.recDate.dayOfMonth, 0,0)
            }

            PersonTable.update({ PersonTable.per_id eq reciever.per_id}) {
                it[PersonTable.f_name] = reciever.fName
                it[PersonTable.l_name] = reciever.lName
                it[PersonTable.mobile_no] = reciever.mobile
                it[PersonTable.sex] = reciever.sex
                it[PersonTable.email] = reciever.email
                it[PersonTable.add_id] = reciever.add_id
                it[PersonTable.dob] = DateTime(reciever.dob.year, reciever.dob.monthValue, reciever.dob.dayOfMonth, 0,0)

            }

            AddressTable.update({ AddressTable.add_id eq reciever.add_id}){
                it[AddressTable.house_no] = reciever.house
                it[AddressTable.street] = reciever.street
                it[AddressTable.area] = reciever.area
                it[AddressTable.zip_code] = reciever.zipCod
                it[AddressTable.city] = reciever.city
                it[AddressTable.country] = reciever.country
            }
        }
    }

    fun newItem(reciever: BloodReciever): BloodReciever {

        transaction {

            val add_id = AddressTable.insert {
                it[AddressTable.house_no] = reciever.house
                it[AddressTable.street] = reciever.street
                it[AddressTable.area] = reciever.area
                it[AddressTable.zip_code] = reciever.zipCod
                it[AddressTable.city] = reciever.city
                it[AddressTable.country] = reciever.country
            }

            reciever.add_id = add_id.generatedKey!!.toInt()

            val per_id = PersonTable.insert {
                it[PersonTable.f_name] = reciever.fName
                it[PersonTable.l_name] = reciever.lName
                it[PersonTable.mobile_no] = reciever.mobile
                it[PersonTable.sex] = reciever.sex
                it[PersonTable.email] = reciever.email
                it[PersonTable.add_id] = reciever.add_id
                it[PersonTable.dob] = DateTime(reciever.dob.year, reciever.dob.monthValue, reciever.dob.dayOfMonth, 0,0)

            }

            val staff_id = com.example.demo.database.ReceiverTable.insert {
                it[ReceiverTable.per_id] = per_id.generatedKey!!.toInt()
                it[ReceiverTable.don_id] = reciever.donId
                it[ReceiverTable.rec_date] = DateTime(reciever.recDate.year, reciever.recDate.monthValue, reciever.recDate.dayOfMonth, 0,0)
            }
        }

        return reciever
    }

    fun deleteItem(reciever: BloodReciever){

        transaction {
            com.example.demo.database.ReceiverTable.deleteWhere { ReceiverTable.rec_id eq reciever.rec_id }
        }


    }
}