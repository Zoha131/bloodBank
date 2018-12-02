package com.example.demo.view

import com.example.demo.app.Styles
import com.jfoenix.controls.JFXButton
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.control.SelectionMode
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadow
import javafx.scene.effect.Shadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.text.FontWeight
import tornadofx.*
import java.time.LocalDate
import java.time.Period

class DashBoardView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    //todo me: change this placehodler data
    private val persons = listOf(
            Person(1,"Samantha Stuart",LocalDate.of(1981,12,4)),
            Person(2,"Tom Marks",LocalDate.of(2001,1,23)),
            Person(3,"Stuart Gills",LocalDate.of(1989,5,23)),
            Person(3,"Nicole Williams",LocalDate.of(1998,8,11))
    ).observable()

    private  val listData = persons.map { it.name }.observable()


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
                backgroundColor = multi(Styles.iconColor)
                fontFamily = "Arial Rounded MT Bold"
            }

            hbox {

                alignment = Pos.CENTER_LEFT

                imageview("logo/blood_bag.png")
                label("Blood Bags") {
                    style {
                        fontSize = 17.px
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.primaryDarkColor
                    }
                }
            }

            listview(persons){

                prefHeight = 250.00

                style{
                    borderColor = multi(CssBox(Styles.iconColor, Styles.iconColor, Styles.iconColor, Styles.iconColor))
                    backgroundColor = multi(Styles.iconColor)
                }

                cellFormat {
                    graphic = vbox {

                        paddingLeft = 24.00

                        label(it.name){
                            style{
                                fontSize = 14.px
                                fontWeight = FontWeight.BOLD
                            }
                        }
                        label(it.birthday.toString()){
                            style{
                                fontSize = 13.px
                                fontWeight = FontWeight.MEDIUM
                            }
                        }
                    }
                }
            }

            hbox {

                alignment = Pos.CENTER_LEFT

                vboxConstraints {
                    marginTop = 50.00
                }

                imageview("logo/user_request.png")
                label("User Requests") {
                    style {
                        fontSize = 17.px
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.primaryDarkColor
                    }
                }
            }

            listview(persons){

                prefHeight = 250.00

                style{
                    borderColor = multi(CssBox(Styles.iconColor, Styles.iconColor, Styles.iconColor, Styles.iconColor))
                    backgroundColor = multi(Styles.iconColor)
                }

                cellFormat {
                    graphic = vbox {
                        paddingLeft = 24.00

                        label(it.name){
                            style{
                                fontSize = 14.px
                                fontWeight = FontWeight.BOLD
                            }
                        }
                        label(it.birthday.toString()){
                            style{
                                fontSize = 13.px
                                fontWeight = FontWeight.MEDIUM
                            }
                        }
                    }
                }
            }
        }

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
                    marginTop = 20.00
                }
            }

            piechart("Doners") {


                maxHeight = 250.00
                maxWidth = 300.00

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT
                    marginLeft = 30.00
                    marginTop = 70.00
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

                maxHeight = 250.00
                maxWidth = 300.00

                stackpaneConstraints {
                    alignment = Pos.TOP_RIGHT
                    marginRight = 30.00
                    marginTop = 70.00
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


            vbox {
                maxHeight = 250.00
                useMaxWidth = true
                alignment = Pos.BOTTOM_CENTER

                stackpaneConstraints {
                    alignment = Pos.BOTTOM_CENTER
                    margin = Insets(30.00)
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

                label("Live Donors") {
                    style {
                        fontSize = 20.px
                        fontWeight = FontWeight.BOLD
                    }

                    vboxConstraints {
                        marginBottom = 10.00
                    }
                }

                tableview(persons) {

                    maxHeight = 200.00


                    vboxConstraints {
                        hgrow = Priority.ALWAYS
                    }


                    readonlyColumn("ID",Person::id)
                    readonlyColumn("Name", Person::name)
                    readonlyColumn("Birthday", Person::birthday)
                    readonlyColumn("Age",Person::age)
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

data class Person(val id: Int, val name: String, val birthday: LocalDate) {
    val age: Int get() = Period.between(birthday, LocalDate.now()).years
}
