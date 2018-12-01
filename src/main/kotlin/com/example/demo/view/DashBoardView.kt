package com.example.demo.view

import com.example.demo.app.Styles
import com.jfoenix.controls.JFXButton
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Shadow
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.text.FontWeight
import tornadofx.*

class DashBoardView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()


    init {

        myListMenu = listmenu(theme = "blue") {

            prefWidth = 200.00
            minWidth = 80.00
            useMaxHeight = true

            effect = DropShadow(50.0, Color.GRAY)


            item(text = "Dashboard", graphic = imageview("speedometer.png")) {

                whenSelected {

                }

                activeItem = this

                minWidth = 80.00
            }
            item(text = "Users", graphic = imageview("community.png") )
            item(text = "Settings", graphic =  imageview("settings.png") )



        }
    }

    override val root = borderpane {
        prefWidth = 1120.00
        prefHeight = 700.00

        right = vbox {

            prefWidth = 200.00

            style {
                backgroundColor = multi(Styles.dividerColor)
            }
        }

        /*left = stackpane {
            useMaxHeight = true
            paddingRight = 50.00
            this +=myListMenu
        }*/

        left = myListMenu

        center = stackpane {
            style {
                backgroundColor = multi(Styles.windowColor)
            }

            label("Dashboard") {
                style {
                    fontSize = 18.px
                    backgroundColor = multi(Styles.primaryColor)
                    backgroundRadius = multi(CssBox(0.px,5.px, 5.px, 0.px))
                    paddingAll = 5
                    fontWeight = FontWeight.BOLD
                    textFill = Styles.iconColor

                    padding = CssBox(3.px, 8.px, 3.px, 18.px)
                }

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT
                    marginTop = 30.00
                }
            }


            piechart("Doners") {


                maxHeight = 300.00
                maxWidth = 300.00

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT
                    marginLeft = 30.00
                    marginTop = 100.00
                }

                style {
                    val myRadius = 5
                    val myBWidth = 2
                    backgroundColor = multi(Styles.iconColor)
                    borderColor = multi(CssBox(Styles.dividerColor, Styles.dividerColor, Styles.dividerColor, Styles.dividerColor))
                    borderRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                    backgroundRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                    borderWidth = multi(CssBox(myBWidth.px,myBWidth.px, myBWidth.px, myBWidth.px))
                }

                data("A+", 17.0)
                data("A-", 16.0)
                data("B+", 17.0)
                data("B-", 16.0)
                data("AB+", 15.0)
                data("AB-", 16.0)
            }

            barchart("Blood Bags", CategoryAxis(), NumberAxis()) {

                maxHeight = 300.00
                maxWidth = 300.00

                stackpaneConstraints {
                    alignment = Pos.TOP_RIGHT
                    marginRight = 30.00
                    marginTop = 100.00
                }

                style {
                    val myRadius = 5
                    val myBWidth = 2
                    backgroundColor = multi(Styles.iconColor)
                    borderColor = multi(CssBox(Styles.dividerColor, Styles.dividerColor, Styles.dividerColor, Styles.dividerColor))
                    borderRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                    backgroundRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                    borderWidth = multi(CssBox(myBWidth.px,myBWidth.px, myBWidth.px, myBWidth.px))
                }

                series("Available Bags") {
                    data("A+", 17.0)
                    data("A-", 16.0)
                    data("B+", 17.0)
                    data("B-", 16.0)
                    data("AB+", 15.0)
                    data("AB-", 16.0)
                }
            }
        }

        top = hbox {

            prefHeight = 80.00
            useMaxWidth = true

            style {
                backgroundColor = multi(c("#414453"))
            }

            myMenuTop = stackpane {
                prefWidth = 200.00
                useMaxHeight = true

                alignment = Pos.CENTER_RIGHT
                paddingRight = 15.00

                style {
                    backgroundColor = multi(Styles.primaryColor)
                }

                openMenuBtn = JFXButton().apply {
                    icon = imageview("menu.png")

                    style {
                        backgroundColor = multi(Styles.primaryColor)
                    }

                    setOnAction {
                        openMenuBtn.visibleProperty().value = false
                        closeMenuBtn.visibleProperty().value = true


                        myListMenu.prefWidthProperty().animate(200, .3.seconds)
                        myMenuTop.prefWidthProperty().animate(200, 0.3.seconds)
                    }
                }

                closeMenuBtn = JFXButton().apply {
                    icon = imageview("back.png")

                    style {
                        backgroundColor = multi(Styles.primaryColor)
                    }

                    setOnAction {
                        openMenuBtn.visibleProperty().value = true
                        closeMenuBtn.visibleProperty().value = false


                        myListMenu.prefWidthProperty().animate(80, .3.seconds)
                        myMenuTop.prefWidthProperty().animate(80, 0.3.seconds)
                    }
                }

                this += openMenuBtn
                this += closeMenuBtn
            }

            hbox {
                useMaxHeight = true

                alignment = Pos.CENTER_LEFT
                paddingLeft = 30.00

                hboxConstraints {
                    hgrow = Priority.ALWAYS
                }

                this += JFXButton("Staff").apply {
                    icon = imageview("square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Staff Category").apply {
                    icon = imageview("square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Donor").apply {
                    icon = imageview("square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Receiver").apply {
                    icon = imageview("square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Medication").apply {
                    icon = imageview("square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Medical Condition").apply {
                    icon = imageview("square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }
            }

            hbox {
                useMaxHeight = true

                alignment = Pos.CENTER_RIGHT
                paddingRight = 30.00

                this += JFXButton().apply {
                    icon = imageview("logout.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                label("User Name") {
                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                circle {
                    radius = 25.00

                    hboxConstraints {
                        marginLeft = 10.00
                    }

                    fill = ImagePattern(Image("user.jpg"))
                }
            }

        }

    }
}
