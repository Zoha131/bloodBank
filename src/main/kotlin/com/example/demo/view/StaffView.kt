package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import com.jfoenix.controls.*
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.text.FontWeight
import tornadofx.*

class StaffView :  View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    //todo me: change this placehodler data
    private val staffList = listOf(
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff(),
            Staff()
    ).observable()
    private val staff = Staff()


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
            item(text = "Users", graphic = imageview("logo/community.png") )
            item(text = "Settings", graphic =  imageview("logo/settings.png") )



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

            label("Staff") {
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
                    marginTop = 20.00
                }
            }

            hbox {

                alignment = Pos.TOP_LEFT

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT

                    marginTop = 120.00
                }

                this += JFXButton("Delete Staff").apply{
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 40.00
                    }

                    style {
                        backgroundColor = multi(c("#E30000"))
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("New Staff").apply{
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 40.00
                    }

                    style {
                        backgroundColor = multi(Styles.positiveColor)
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXTextField().apply {
                    promptText = "Search"

                    hboxConstraints {
                        marginLeft = 40.00
                        focusColor = Styles.accentColor
                    }
                }

                this += JFXButton().apply {

                    graphic = circle {
                        radius = 10.00

                        fill = ImagePattern(Image("logo/search.png"))
                    }
                }
            }

            tableview(staffList) {

                maxHeight = 400.00
                maxWidth = 400.00


                stackpaneConstraints {
                    hgrow = Priority.ALWAYS
                    alignment = Pos.BOTTOM_LEFT
                    margin = Insets(30.00)
                }


                readonlyColumn("ID",Staff::staff_id)
                readonlyColumn("Name", Staff::fName)
                readonlyColumn("Joining Date", Staff::joinDate)
                readonlyColumn("Sex",Staff::sex)
                readonlyColumn("Email",Staff::email)
            }

            scrollpane {
                maxWidth = 400.00
                useMaxHeight = true
                paddingAll = 30.00

                hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER

                stackpaneConstraints {
                    margin = Insets(30.00)
                    alignment = Pos.BOTTOM_RIGHT
                }

                style {
                    backgroundColor = multi(Color.WHITE)

                    val radius = 2
                    backgroundRadius = multi(CssBox(radius.px, radius.px, radius.px, radius.px))
                }

                form {

                    useMaxWidth = true
                    prefWidth = 350.00

                    style {
                        backgroundColor = multi(Color.WHITE)
                    }

                    fieldset("Person") {

                        field("First Name:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                text = staff.fName
                            }
                            paddingBottom = 30.00
                        }

                        field("Last Name:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.lName
                            }

                            paddingBottom = 30.00
                        }

                        field("Date of Birth:") {
                            this+= JFXDatePicker().apply {
                                value = staff.dob
                            }

                            paddingBottom = 30.00
                        }

                        field("Mobile No:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.mobile
                            }

                            paddingBottom = 30.00
                        }

                        field("Sex:") {
                            this += JFXComboBox<String>().apply {
                                focusColor = Styles.accentColor

                                items.add("Male")
                                items.add("Female")

                                value = staff.sex
                            }

                            paddingBottom = 30.00
                        }

                        field("Email:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.email
                            }

                            paddingBottom = 30.00
                        }
                    }

                    fieldset("Address") {

                        field("House No:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                text = staff.address.house
                            }
                            paddingBottom = 30.00
                        }

                        field("Street:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.address.street
                            }

                            paddingBottom = 30.00
                        }

                        field("Area:") {
                            this+= JFXTextField().apply {
                                text = staff.address.area
                                focusColor = Styles.accentColor
                            }

                            paddingBottom = 30.00
                        }

                        field("Zip Code:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.address.zipCod
                            }

                            paddingBottom = 30.00
                        }

                        field("City:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.address.city
                            }

                            paddingBottom = 30.00
                        }

                        field("Country:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.address.country
                            }

                            paddingBottom = 30.00
                        }
                    }

                    fieldset("Staff") {

                        field("Category:") {
                            this += JFXComboBox<StaffCategory>().apply {
                                focusColor = Styles.accentColor

                                val cat = StaffCategory(1)

                                items.add(cat)
                                items.add(StaffCategory(2))

                                value = cat
                            }

                            paddingBottom = 30.00
                        }

                        field("Education:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                text = staff.eduBack
                            }
                            paddingBottom = 30.00
                        }

                        field("Experience:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.expDetails
                            }

                            paddingBottom = 30.00
                        }

                        field("Reference:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = staff.refDetails
                            }

                            paddingBottom = 30.00
                        }

                        field("Joining Date:") {
                            this+= JFXDatePicker().apply {
                                value = staff.joinDate
                            }

                            paddingBottom = 30.00
                        }

                        field("Resigning Date:") {
                            this+= JFXDatePicker().apply {
                                value = staff.resignDate
                            }

                            paddingBottom = 30.00
                        }
                    }

                    this += JFXButton("Save").apply{
                        buttonType = JFXButton.ButtonType.RAISED

                        style {
                            backgroundColor = multi(Styles.positiveColor)
                            textFill = Color.WHITE

                            fontWeight = FontWeight.BOLD
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

                this += JFXButton("Staff").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Staff Category").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Donor").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Receiver").apply {
                    icon = imageview("logo/square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Medication").apply {
                    icon = imageview("logo/square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("Medical Condition").apply {
                    icon = imageview("logo/square.png")


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