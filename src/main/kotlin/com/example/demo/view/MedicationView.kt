package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.model.Donor
import com.example.demo.model.Medication
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXComboBox
import com.jfoenix.controls.JFXDatePicker
import com.jfoenix.controls.JFXTextField
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
import javafx.stage.StageStyle
import tornadofx.*

class MedicationView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    //todo me: change this placehodler data
    private val medicationList = listOf(
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication(),
            Medication()
    ).observable()
    private val medication = Medication()


    init {

        myListMenu = listmenu(theme = "blue") {

            prefWidth = 200.00
            minWidth = 80.00
            useMaxHeight = true

            effect = DropShadow(50.0, Color.GRAY)


            item(text = "Dashboard", graphic = imageview("logo/speedometer.png")) {

                whenSelected {
                    replaceWith<DashBoardView>()
                }
            }
            item(text = "Users", graphic = imageview("logo/community.png") ) {
                whenSelected {
                    replaceWith<UserView>()
                }
            }
            item(text = "Settings", graphic =  imageview("logo/settings.png") ) {
                whenSelected {
                    replaceWith<SettingView>()
                }
            }



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

            label("MedicalCondition") {
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

                    marginTop = 100.00
                    marginLeft = 50.00
                }

                label("Search Person for MedicalCondition: ") {  }

                this += JFXTextField().apply {
                    promptText = "Search"

                    hboxConstraints {
                        marginLeft = 20.00
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

            hbox {

                alignment = Pos.TOP_LEFT

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT

                    marginTop = 160.00
                }

                this += JFXButton("Delete MedicalCondition").apply{
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 70.00
                    }

                    style {
                        backgroundColor = multi(c("#E30000"))
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }
                }

                this += JFXButton("New MedicalCondition").apply{
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 60.00
                    }

                    style {
                        backgroundColor = multi(Styles.positiveColor)
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }
                }
            }

            tableview(medicationList) {

                maxHeight = 350.00
                maxWidth = 400.00


                stackpaneConstraints {
                    hgrow = Priority.ALWAYS
                    alignment = Pos.BOTTOM_LEFT
                    margin = Insets(30.00)
                }


                readonlyColumn("ID", Medication::med_id)
                readonlyColumn("Name", Medication::medNamed)
                readonlyColumn("Description", Medication::medDesc)
                readonlyColumn("Guide", Medication::medGuide)
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
                    minHeight = 500.00

                    style {
                        backgroundColor = multi(Color.WHITE)
                    }

                    fieldset("Medical Condition") {

                        field("Name:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                text = medication.medNamed
                            }
                            paddingBottom = 30.00
                            paddingTop = 40.00
                        }

                        field("Description:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = medication.medDesc

                            }
                            paddingBottom = 30.00

                        }

                        field("Guide:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                text = medication.medGuide

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

                    setOnAction {
                        replaceWith<StaffView>()
                    }
                }

                this += JFXButton("Staff Category").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        replaceWith<StaffCategoryView>()
                    }
                }

                this += JFXButton("Donor").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        replaceWith<DonorView>()
                    }
                }

                this += JFXButton("Receiver").apply {
                    icon = imageview("logo/square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        replaceWith<RecieverView>()
                    }
                }

                this += JFXButton("Medication").apply {
                    icon = imageview("logo/square.png")


                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        replaceWith<MedicationView>()
                    }
                }

                this += JFXButton("Medical Condition").apply {
                    icon = imageview("logo/square.png")

                    style {
                        textFill = Styles.iconColor
                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        replaceWith<MConditionView>()
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

                    setOnAction {
                        close()
                        find<MainView>().openWindow(stageStyle = StageStyle.TRANSPARENT)
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