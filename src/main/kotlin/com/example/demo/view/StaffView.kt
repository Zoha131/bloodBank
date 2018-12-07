package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.StaffViewController
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import com.example.demo.viewmodel.StaffViewModel
import com.jfoenix.controls.*
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Alert
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
import java.lang.Exception

class StaffView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    val staffViewController: StaffViewController by inject()
    val staffModel: StaffViewModel by inject()

    private val staffList = staffViewController.getStaffSet()
    private val staffListObservable = ArrayList(staffList).observable()
    private val searchString = SimpleStringProperty()

    private var z_save = false
    private var z_delete = false

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
            item(text = "Users", graphic = imageview("logo/community.png")) {
                whenSelected {
                    replaceWith<UserView>()
                }
            }
            item(text = "Settings", graphic = imageview("logo/settings.png")) {
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

            label("Staff") {
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

                alignment = Pos.TOP_LEFT

                stackpaneConstraints {
                    alignment = Pos.TOP_LEFT

                    marginTop = 120.00
                }

                this += JFXButton("Delete Staff").apply {
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 40.00
                    }

                    style {
                        backgroundColor = multi(c("#E30000"))
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {

                        try {
                            if(z_delete){

                                val staff = staffModel.item
                                staffViewController.deleteStaff(staff)

                                staffListObservable.remove(staff)
                                staffList.remove(staff)

                                alert(Alert.AlertType.INFORMATION, "Confirmation!", "Data has been deleted successfully!")
                            } else {
                                alert(Alert.AlertType.INFORMATION, "Error!", "Please Select a data to delete")
                            }
                        }catch (ex: Exception){
                            alert(Alert.AlertType.INFORMATION, "Error!", "Something went wrong! ${ex.message}")
                        }
                    }
                }

                this += JFXButton("New Staff").apply {
                    buttonType = JFXButton.ButtonType.RAISED

                    hboxConstraints {
                        marginLeft = 40.00
                    }

                    style {
                        backgroundColor = multi(Styles.positiveColor)
                        textFill = Color.WHITE

                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        z_save = true
                        z_delete = false

                        staffModel.rebind {
                            item = Staff()
                        }
                    }
                }

                this += JFXTextField().apply {
                    promptText = "Search"

                    searchString.bindBidirectional(textProperty())

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

                    setOnAction {

                        z_delete = false
                        z_save = false

                        val list = staffList.filter { ss -> searchString.value.isEmpty() || "${ss.fName} ${ss.lName}".contains(searchString.value, true) }

                        println(staffList.size.toString())
                        println(list.size.toString())
                        staffListObservable.clear()
                        staffListObservable.addAll(list)
                    }
                }
            }

            tableview(staffListObservable) {

                maxHeight = 400.00
                maxWidth = 400.00


                stackpaneConstraints {
                    hgrow = Priority.ALWAYS
                    alignment = Pos.BOTTOM_LEFT
                    margin = Insets(30.00)
                }


                readonlyColumn("Name", Staff::fName)
                readonlyColumn("Joining Date", Staff::joinDate)
                readonlyColumn("Sex", Staff::sex)
                readonlyColumn("Email", Staff::email)

                bindSelected(staffModel).apply {
                    z_save = false
                    z_delete = true
                }
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

                                bind(staffModel.fname)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Last Name:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.lName)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Date of Birth:") {
                            this += JFXDatePicker().apply {

                                bind(staffModel.dob)

                                validator {
                                    if (it.toString().isBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Mobile No:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.mobile)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Sex:") {
                            this += JFXComboBox<String>().apply {
                                focusColor = Styles.accentColor

                                items.add("Male")
                                items.add("Female")
                                items.add("Other")

                                bind(staffModel.sex)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Email:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.email)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }
                    }

                    fieldset("Address") {

                        field("House No:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                bind(staffModel.house)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }

                            }
                            paddingBottom = 30.00
                        }

                        field("Street:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.street)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Area:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.area)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Zip Code:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.zipCod)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("City:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.city)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Country:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.country)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }
                    }

                    fieldset("Staff") {

                        field("Category:") {
                            this += JFXComboBox<Number>().apply {
                                focusColor = Styles.accentColor

                                items.add(100001)
                                items.add(100002)
                                items.add(100003)
                                items.add(100004)
                                items.add(100005)


                                bind(staffModel.cat_id)

                                validator {
                                    if (it.toString().isBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Education:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                bind(staffModel.eduBack)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }
                            paddingBottom = 30.00
                        }

                        field("Experience:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.expDetails)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Reference:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(staffModel.refDetails)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Joining Date:") {
                            this += JFXDatePicker().apply {
                                bind(staffModel.joinDate)

                                validator {
                                    if (it.toString().isBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Resigning Date:") {
                            this += JFXDatePicker().apply {
                                bind(staffModel.resignDate)
                            }

                            paddingBottom = 30.00
                        }
                    }

                    hbox {

                        alignment = Pos.CENTER

                        this += JFXButton("Save").apply {
                            buttonType = JFXButton.ButtonType.RAISED

                            enableWhen(staffModel.valid.and(staffModel.dirty))

                            style {
                                backgroundColor = multi(Styles.positiveColor)
                                textFill = Color.WHITE

                                fontWeight = FontWeight.BOLD
                            }

                            setOnAction {
                                staffModel.commit()
                                val staff = staffModel.item

                                try {
                                    if(!z_save){
                                        staffViewController.updateStaff(staff)
                                        alert(Alert.AlertType.INFORMATION, "Confirmation!", "Data has been saved successfully!")
                                    } else {
                                        val newStaff = staffViewController.newStaff(staff)

                                        alert(Alert.AlertType.INFORMATION, "Confirmation!", "New Staff has been created successfully!")

                                        staffList.add(newStaff)
                                        staffListObservable.add(newStaff)

                                    }
                                }catch (ex: Exception){
                                    alert(Alert.AlertType.INFORMATION, "Error!", "Something went wrong!")
                                }
                            }
                        }

                        this += JFXButton("Reset").apply {
                            buttonType = JFXButton.ButtonType.RAISED

                            enableWhen(staffModel.dirty)

                            hboxConstraints {
                                marginLeft = 50.00
                            }

                            style {
                                backgroundColor = multi(Styles.tensionColor)
                                textFill = Color.WHITE

                                fontWeight = FontWeight.BOLD
                            }

                            setOnAction {
                                staffModel.rollback()
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