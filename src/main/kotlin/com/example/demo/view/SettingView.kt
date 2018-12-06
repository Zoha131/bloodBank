package com.example.demo.view

import com.example.demo.model.Status
import com.example.demo.model.UserDemo
import com.example.demo.app.Styles
import com.jfoenix.controls.JFXButton
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.ToggleGroup
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate

class SettingView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    val filterToggleGroup = ToggleGroup()

    //todo me: change this placehodler data
    private val users = listOf(
            UserDemo(10001,"Baker Zahir", "eeee", "baker@diu.edu.bd", "ComeOnBaker", Status.Pending),
            UserDemo(10002,"Abir Hasan", "zoha131", "abir@diu.edu.bd", "ComeOnBaker", Status.Pending)
    ).observable()


    init {

        myListMenu = listmenu(theme = "blue") {

            prefWidth = 200.00
            minWidth = 80.00
            useMaxHeight = true

            effect = DropShadow(50.0, Color.GRAY)


            item(text = "Dashboard", graphic = imageview("logo/speedometer.png")) {

                whenSelected {

                }

                activeItem = this

                minWidth = 80.00
            }
            item(text = "Users", graphic = imageview("logo/community.png"))
            item(text = "Settings", graphic = imageview("logo/settings.png"))


        }
    }

    override val root = borderpane {
        prefWidth = 1120.00
        prefHeight = 700.00

        left = myListMenu

        center = stackpane {
            style {
                backgroundColor = multi(Styles.windowColor)
            }

            label("Settings") {
                style {
                    fontSize = 18.px
                    backgroundColor = multi(Styles.primaryColor)
                    backgroundRadius = multi(CssBox(0.px, 5.px, 5.px, 0.px))
                    paddingAll = 5
                    fontWeight = FontWeight.BOLD
                    textFill = Styles.iconColor

                    padding = CssBox(3.px, 8.px, 3.px, 18.px)
                }

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT
                    marginTop = 20.00
                }
            }
            
            
            form {

                maxWidth = 300.00
                stackpaneConstraints {
                    marginTop = 120.00
                    marginLeft = 100.00
                    alignment = Pos.TOP_LEFT
                }

                fieldset {

                    alignment = Pos.CENTER


                    field("First Name:") {
                        textfield ()

                        paddingAll = 6.00
                    }

                    field("Last Name:") {
                        textfield ()

                        paddingAll = 6.00
                    }

                    field("Email:") {
                        textfield ()

                        paddingAll = 6.00
                    }

                    field("Sex:") {

                        val texasCities = FXCollections.observableArrayList("Male", "Female", "Other")
                        val selectedCity = SimpleStringProperty("Male")

                        combobox(selectedCity, texasCities)


                        paddingAll = 6.00
                    }

                    field("Date of Birth:") {
                        datepicker {
                            value = LocalDate.of(1669,12,18)
                        }

                        paddingAll = 6.00
                    }


                    field("Password:") {

                        this += JFXButton("Change Password").apply{

                            style {
                                backgroundColor = multi(Styles.accentColor)
                                textFill = Color.WHITE

                                fontWeight = FontWeight.BOLD
                            }
                        }


                        paddingAll = 6.00
                    }

                }
            }

            rectangle {
                height = 130.00
                width = 130.00

                fill = ImagePattern(Image("logo/user.jpg"))

                stackpaneConstraints {
                    alignment = Pos.TOP_RIGHT
                    marginTop = 120.00
                    marginRight = 120.00
                }

            }

            this += JFXButton("Save").apply{
                buttonType = JFXButton.ButtonType.RAISED

                style {
                    backgroundColor = multi(c("#E30000"))
                    textFill = Color.WHITE

                    fontWeight = FontWeight.BOLD
                }

                stackpaneConstraints {
                    alignment = Pos.BOTTOM_RIGHT
                    marginBottom = 150.00
                    marginRight = 220.00
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
                    icon = imageview("logo/menu.png")

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
                    icon = imageview("logo/back.png")

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

                // this hbox helps to have alignment of logout hbox
            }

            hbox {
                useMaxHeight = true

                alignment = Pos.CENTER_RIGHT
                paddingRight = 30.00

                this += JFXButton().apply {
                    icon = imageview("logo/logout.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                label("UserDemo Name") {
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

                    fill = ImagePattern(Image("logo/user.jpg"))
                }
            }

        }
    }
}


