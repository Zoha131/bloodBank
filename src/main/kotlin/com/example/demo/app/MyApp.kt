package com.example.demo.app

import com.example.demo.view.DashBoardView
import com.example.demo.view.MainView
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*

class MyApp: App(MainView::class, Styles::class){

    init {
        reloadStylesheetsOnFocus()
    }

    override fun start(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        super.start(stage)
    }
}