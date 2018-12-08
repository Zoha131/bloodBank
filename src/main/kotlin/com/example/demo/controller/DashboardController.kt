package com.example.demo.controller

import com.example.demo.database.CategoryTable
import com.example.demo.model.ChartData
import com.example.demo.model.LiveDonor
import com.example.demo.model.PendingUser
import com.example.demo.model.StaffCategory
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class DashboardController : Controller() {

    fun getLiveDonorList(): ArrayList<LiveDonor> {

        val dataList = ArrayList<LiveDonor>()

        transaction {
            TransactionManager.current()
                    .exec(""" SELECT * FROM (SELECT donor.dnr_id, don_date, bld_group, f_name, l_name, dob, mobile_no, email FROM donor
                                INNER JOIN person ON donor.per_id = person.per_id
                                INNER JOIN donation ON donor.dnr_id = donation.dnr_id) AS rs
                                WHERE datediff(curdate(),rs.don_date) >= 10;""") { rs ->

                        val result = arrayListOf<Pair<String, String>>()
                        while (rs.next()) {
                            val liveDonor = LiveDonor(
                                    rs.getInt("dnr_id"),
                                    rs.getDate("don_date"),
                                    rs.getString("bld_group"),
                                    rs.getString("f_name"),
                                    rs.getString("l_name"),
                                    rs.getDate("dob"),
                                    rs.getString("mobile_no"),
                                    rs.getString("email"))

                            dataList.add(liveDonor)
                        }

                        result
                    }
        }

        return dataList
    }

    fun getBloodBagCount(): ArrayList<ChartData> {

        val dataList = ArrayList<ChartData>()


        transaction {
            TransactionManager
                    .current()
                    .exec(""" SELECT rs.bld_group, count(rs.bld_group) AS count FROM
                                ( SELECT bld_group FROM donation INNER JOIN donor ON
                                donation.dnr_id = donor.dnr_id WHERE
                                datediff(curdate(),don_date) < 42) AS rs GROUP BY rs.bld_group;""") { rs ->

                        val result = arrayListOf<Pair<String, String>>()
                        while (rs.next()) {
                            val chartData = ChartData(rs.getString("bld_group"), rs.getInt("count"))

                            dataList.add(chartData)
                        }

                        result
                    }
        }


        return dataList
    }

    fun getDonorCount(): ArrayList<ChartData> {

        val dataList = ArrayList<ChartData>()

        transaction {
            TransactionManager
                    .current()
                    .exec(""" SELECT bld_group,count(bld_group) AS count FROM donor GROUP BY bld_group;""") { rs ->

                        val result = arrayListOf<Pair<String, String>>()
                        while (rs.next()) {
                            val chartData = ChartData(rs.getString("bld_group"), rs.getInt("count"))

                            dataList.add(chartData)
                        }

                        result
                    }
        }

        return dataList
    }

    fun getBloodBagNotification(): ArrayList<ChartData> {

        val dataList = ArrayList<ChartData>()

        transaction {
            TransactionManager
                    .current()
                    .exec(""" SELECT rs.bld_group, count(rs.bld_group) AS count FROM
                                ( SELECT bld_group FROM donation INNER JOIN donor ON
                                donation.dnr_id = donor.dnr_id WHERE
                                datediff(curdate(),don_date) < 42 ) AS rs GROUP BY rs.bld_group HAVING count < 10;""") { rs ->

                        val result = arrayListOf<Pair<String, String>>()
                        while (rs.next()) {
                            val chartData = ChartData(rs.getString("bld_group"), rs.getInt("count"))

                            dataList.add(chartData)
                        }

                        result
                    }
        }

        return dataList
    }

    fun getPedingUserNotification(): ArrayList<PendingUser> {

        val dataList = ArrayList<PendingUser>()

        transaction {
            TransactionManager
                    .current()
                    .exec(""" SELECT u_name, email FROM user INNER JOIN person ON
                                user.per_id = person.per_id WHERE
                                status = 'Pending';""") { rs ->

                        val result = arrayListOf<Pair<String, String>>()
                        while (rs.next()) {
                            val chartData = PendingUser(rs.getString("u_name"), rs.getString("email"))

                            dataList.add(chartData)
                        }

                        result
                    }
        }

        return dataList
    }
}