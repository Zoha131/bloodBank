package com.example.demo.view

import com.example.demo.model.Status
import com.example.demo.model.UserDemo
import com.example.demo.app.Styles
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXRadioButton
import com.jfoenix.controls.JFXTextField
import javafx.geometry.Insets
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
import java.io.ByteArrayInputStream

class UserView : View("My View") {

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

            label("Users") {
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

            hbox {

                useMaxWidth = true
                maxHeight = 60.00
                alignment = Pos.CENTER

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT
                    marginTop = 70.00
                    marginLeft = 40.00
                    marginRight = 40.00
                }

                style {
                    backgroundColor = multi(Styles.iconColor)
                }

                this += JFXRadioButton(Status.Allowed.toString()).apply {
                    toggleGroup = filterToggleGroup
                    paddingAll = 6.00

                    isSelected = true
                }
                this += JFXRadioButton(Status.Pending.toString()).apply {
                    toggleGroup = filterToggleGroup
                    paddingAll = 6.00

                    style {
                        selectedColor = Styles.tensionColor
                    }
                }
                this += JFXRadioButton(Status.Blocked.toString()).apply {
                    toggleGroup = filterToggleGroup
                    paddingAll = 6.00

                    style {
                        selectedColor = Styles.primaryColor
                    }
                }

                this += JFXTextField().apply {
                    promptText = "Search"

                    hboxConstraints {
                        marginLeft = 160.00
                        focusColor = Styles.accentColor
                    }
                }

                this += JFXButton().apply {

                    graphic = imageview("logo/search.png")
                }




            }

            tableview(users) {

                stackpaneConstraints {
                    margin = Insets(20.00)
                    marginTop = 150.00
                }

                readonlyColumn("Name", UserDemo::name)
                readonlyColumn("Username", UserDemo::userName)
                readonlyColumn("Email", UserDemo::email)
                readonlyColumn("Account Status", UserDemo::status)
                rowExpander(expandOnDoubleClick = true) {

                    stackpane {

                        paddingAll = 10.00

                        style {
                            backgroundColor = multi(Styles.iconColor)
                        }

                        stackpane {

                            paddingAll = 10.00

                            val statusToggleGroup = ToggleGroup()

                            style {
                                val myRadius = 5
                                val myBWidth = 2
                                backgroundColor = multi(Styles.iconColor)
                                borderColor = multi(CssBox(Styles.dividerColor, Styles.dividerColor, Styles.dividerColor, Styles.dividerColor))
                                borderRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                                backgroundRadius = multi(CssBox(myRadius.px,myRadius.px, myRadius.px, myRadius.px))
                                borderWidth = multi(CssBox(myBWidth.px,myBWidth.px, myBWidth.px, myBWidth.px))
                            }


                            rectangle {
                                height = 80.00
                                width = 80.00

                                fill = ImagePattern(Image(ByteArrayInputStream(it.image)))

                                stackpaneConstraints {
                                    alignment = Pos.TOP_LEFT
                                }

                            }


                            form {

                                stackpaneConstraints {
                                    alignment = Pos.TOP_LEFT
                                    marginTop = 90.00
                                    marginLeft = 100.00
                                }

                                fieldset {
                                    field("Name"){
                                        label(it.name)
                                    }

                                    field("Username"){
                                        label(it.userName)
                                    }

                                    field("Email") {
                                        label(it.email)
                                    }
                                }
                            }

                            vbox {

                                stackpaneConstraints {
                                    alignment = Pos.TOP_LEFT
                                    marginTop = 100.00
                                    marginLeft = 400.00
                                }

                                this += JFXRadioButton(Status.Allowed.toString()).apply {
                                    toggleGroup = statusToggleGroup
                                    paddingAll = 6.00

                                    isSelected = true
                                }
                                this += JFXRadioButton(Status.Pending.toString()).apply {
                                    toggleGroup = statusToggleGroup
                                    paddingAll = 6.00

                                    style {
                                        selectedColor = Styles.tensionColor
                                    }
                                }
                                this += JFXRadioButton(Status.Blocked.toString()).apply {
                                    toggleGroup = statusToggleGroup
                                    paddingAll = 6.00

                                    style {
                                        selectedColor = Styles.primaryColor
                                    }
                                }
                            }

                            this += JFXButton("Save").apply {
                                buttonType = JFXButton.ButtonType.RAISED

                                stackpaneConstraints {
                                    alignment = Pos.BOTTOM_RIGHT
                                    marginBottom = 20.00
                                    marginRight = 150.00
                                }

                                style {
                                    backgroundColor = multi(c("#E30000"))
                                    textFill = Color.WHITE

                                    fontWeight = FontWeight.BOLD
                                }
                            }
                        }
                    }
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

