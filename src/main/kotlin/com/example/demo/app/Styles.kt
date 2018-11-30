package com.example.demo.app

import com.jfoenix.controls.JFXTextField
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()


    }

    init {
        label {
            padding = box(10.px)
            fontFamily = "Arial Rounded MT Bold"
        }

    }
}