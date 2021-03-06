package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.DashboardController
import com.example.demo.model.Donor
import com.example.demo.model.LiveDonor
import com.example.demo.model.User
import com.jfoenix.controls.JFXButton
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.paint.ImagePattern
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle
import tornadofx.*

class DashBoardView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()

    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    val dashboardController: DashboardController by inject()

    //todo me: change this placehodler data
    private val userRequest = listOf(
            User(),
            User(),
            User(),
            User(),
            User(),
            User()
    ).observable()

    private val livedonorList = dashboardController.getLiveDonorList().observable()
    private val bloodBagData = dashboardController.getBloodBagCount()
    private val donorData = dashboardController.getDonorCount()

    private val bloodBagNotification = dashboardController.getBloodBagNotification()
    private val pedingUserNotification = dashboardController.getPedingUserNotification()


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
        dashboardController.getLiveDonorList()
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

                vboxConstraints {
                    marginTop = 20.00
                }

                imageview("logo/blood_bag.png")
                label("Blood Bags") {
                    style {
                        fontSize = 17.px
                        fontWeight = FontWeight.BOLD
                        textFill = Styles.primaryDarkColor
                    }
                }
            }

            listview(bloodBagNotification.observable()){

                prefHeight = 250.00

                style{
                    borderColor = multi(CssBox(Styles.iconColor, Styles.iconColor, Styles.iconColor, Styles.iconColor))
                    backgroundColor = multi(Styles.iconColor)
                }

                cellFormat {
                    graphic = vbox {

                        paddingLeft = 24.00

                        style {
                            when{

                                it.count < 4 ->{
                                    backgroundColor = multi(Styles.primaryColor)
                                }
                                it.count < 6 ->{
                                    backgroundColor = multi(Styles.tensionColor)
                                }
                                it.count < 11 ->{
                                    backgroundColor = multi(Styles.blueTension)
                                }

                            }

                            val radius = 3
                            backgroundRadius = multi(CssBox(radius.px,radius.px,radius.px,radius.px))
                        }

                        label("${it.bloodGroup} remaining ${it.count}"){
                            alignment = Pos.CENTER
                            style{
                                fontSize = 14.px
                                fontWeight = FontWeight.BOLD
                                textFill = Styles.iconColor
                            }
                        }
                    }
                }
            }

            hbox {

                alignment = Pos.CENTER_LEFT

                vboxConstraints {
                    marginTop = 30.00
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

            listview(pedingUserNotification.observable()){

                prefHeight = 250.00

                style{
                    borderColor = multi(CssBox(Styles.iconColor, Styles.iconColor, Styles.iconColor, Styles.iconColor))
                    backgroundColor = multi(Styles.iconColor)
                }

                cellFormat {
                    graphic = vbox {
                        paddingLeft = 24.00

                        label(it.userName){
                            style{
                                fontSize = 14.px
                                paddingAll = 5.00
                                fontWeight = FontWeight.BOLD
                            }
                        }
                        label(it.email){
                            style{
                                fontSize = 9.px
                                paddingAll = 3.00
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

            piechart("Donors") {


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

                for (bloodBagDatum in donorData) {
                    data(bloodBagDatum.bloodGroup, bloodBagDatum.count.toDouble())
                }
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

                    for (bloodBagDatum in bloodBagData) {
                        data(bloodBagDatum.bloodGroup, bloodBagDatum.count)
                    }
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

                tableview(livedonorList) {

                    maxHeight = 200.00

                    vboxConstraints {
                        hgrow = Priority.ALWAYS
                    }

                    //readonlyColumn("ID",LiveDonor::id)
                    readonlyColumn("First Name", LiveDonor::fname)
                    readonlyColumn("Last Name", LiveDonor::lname)
                    readonlyColumn("Donation Date", LiveDonor::don_data)
                    readonlyColumn("Blood Group", LiveDonor::bloodGroup)
                    readonlyColumn("Phone",LiveDonor::phone)
                    readonlyColumn("Email",LiveDonor::email)
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