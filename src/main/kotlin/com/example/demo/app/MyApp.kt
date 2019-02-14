package com.example.demo.app

import com.example.demo.database.*
import com.example.demo.view.MainView
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.*

class MyApp: App(MainView::class, Styles::class){

    val myConnection = MyConnection()

    init {
        reloadStylesheetsOnFocus()
        myConnection.connect()
    }

    //git hub set up test

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        super.start(stage)
    }
}
