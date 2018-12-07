package com.example.demo.app

import com.jfoenix.controls.JFXTextField
import com.sun.jmx.snmp.EnumRowStatus.active
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {
    companion object {

        val heading by cssclass()
        val myListMenu by cssclass()

        val active by csspseudoclass()

        val primaryColor = c("#F44336")
        val primaryDarkColor = c("#D32F2F")
        val primaryLightColor = c("#FFCDD2")
        val accentColor = c("#607D8B")
        val primaryTextColor = c("#212121")
        val secondaryTextColor = c("#757575")
        val iconColor = c("#FFFFFF")
        val dividerColor = c("#BDBDBD")
        val windowColor = c("#e6e6e6")
        val tensionColor = c("#FF9800")
        val positiveColor = c("#388E3C")
        val blueTension = c("#1976d2")

        val majha  = importStylesheet("/css/listmenu.css")
    }

    init {

        label {
            padding = box(10.px)
            fontFamily = "Arial Rounded MT Bold"
        }

        myListMenu {
            backgroundColor = multi(Color.BLACK)

            and(active) {
                backgroundColor = multi(Color.AQUA)
            }
        }

    }
}