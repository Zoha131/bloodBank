package com.example.demo.view

import com.example.demo.app.Styles
import com.example.demo.controller.CategoryController
import com.example.demo.model.Staff
import com.example.demo.model.StaffCategory
import com.example.demo.viewmodel.CategoryViewModel
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXTextField
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

class StaffCategoryView : View("My View") {

    var myListMenu by singleAssign<ListMenu>()
    var myMenuTop by singleAssign<StackPane>()
    var openMenuBtn by singleAssign<JFXButton>()
    var closeMenuBtn by singleAssign<JFXButton>()

    private val controller: CategoryController by inject()
    private val viewmodel:CategoryViewModel by inject()

    private val dataList = controller.getDataList()
    private val dataListObservable = ArrayList(dataList).observable()
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

            label("Staff Category") {
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

                                val staff = viewmodel.item
                                controller.deleteItem(staff)

                                dataListObservable.remove(staff)
                                dataList.remove(staff)

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

                        viewmodel.rebind {
                            item = StaffCategory()
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

                        val list = dataList.filter { ss -> searchString.value.isEmpty() || "${ss.jobTitle} ${ss.jobDesc}".contains(searchString.value, true) }

                        println(dataList.size.toString())
                        println(list.size.toString())
                        dataListObservable.clear()
                        dataListObservable.addAll(list)
                    }
                }
            }

            tableview(dataListObservable) {

                maxHeight = 400.00
                maxWidth = 400.00


                stackpaneConstraints {
                    hgrow = Priority.ALWAYS
                    alignment = Pos.BOTTOM_LEFT
                    margin = Insets(30.00)
                }


                readonlyColumn("ID", StaffCategory::cat_id)
                readonlyColumn("Job Title", StaffCategory::jobTitle)
                readonlyColumn("Education", StaffCategory::eduReq)
                readonlyColumn("Experience", StaffCategory::expReq)
                readonlyColumn("Salary", StaffCategory::baseSalary)

                bindSelected(viewmodel).apply {
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
                    minHeight = 500.00

                    style {
                        backgroundColor = multi(Color.WHITE)
                    }

                    fieldset("Staff Category") {

                        field("Job Title:") {
                            this += JFXTextField().apply {

                                focusColor = Styles.accentColor

                                bind(viewmodel.jobTitle)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }
                            paddingBottom = 30.00
                            paddingTop = 40.00
                        }

                        field("Job Desc:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(viewmodel.jobDesc)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Education:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(viewmodel.eduReq)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }

                        field("Experience:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(viewmodel.expReq)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                        }

                            paddingBottom = 30.00
                        }

                        field("Salary:") {
                            this += JFXTextField().apply {
                                focusColor = Styles.accentColor

                                bind(viewmodel.salary)

                                validator {
                                    if (it.isNullOrBlank()) error("The name field is required") else null
                                }
                            }

                            paddingBottom = 30.00
                        }
                    }

                    hbox {

                        alignment = Pos.CENTER

                        this += JFXButton("Save").apply {
                            buttonType = JFXButton.ButtonType.RAISED

                            enableWhen(viewmodel.valid.and(viewmodel.dirty))

                            style {
                                backgroundColor = multi(Styles.positiveColor)
                                textFill = Color.WHITE

                                fontWeight = FontWeight.BOLD
                            }

                            setOnAction {
                                viewmodel.commit()
                                val item = viewmodel.item

                                try {
                                    if(!z_save){
                                        controller.updateItem(item)
                                        alert(Alert.AlertType.INFORMATION, "Confirmation!", "Data has been saved successfully!")

                                        dataList.remove(item)
                                        dataList.add(item)

                                        dataListObservable.remove(item)
                                        dataListObservable.add(item)

                                    } else {
                                        val newStaff = controller.newItem(item)

                                        alert(Alert.AlertType.INFORMATION, "Confirmation!", "New Staff has been created successfully!")

                                        dataList.add(newStaff)
                                        dataListObservable.add(newStaff)

                                    }
                                }catch (ex: Exception){
                                    alert(Alert.AlertType.INFORMATION, "Error!", "Something went wrong!")
                                }
                            }
                        }

                        this += JFXButton("Reset").apply {
                            buttonType = JFXButton.ButtonType.RAISED

                            enableWhen(viewmodel.dirty)

                            hboxConstraints {
                                marginLeft = 50.00
                            }

                            style {
                                backgroundColor = multi(Styles.tensionColor)
                                textFill = Color.WHITE

                                fontWeight = FontWeight.BOLD
                            }

                            setOnAction {
                                viewmodel.rollback()
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