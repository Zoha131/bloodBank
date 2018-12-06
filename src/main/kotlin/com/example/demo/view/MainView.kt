package com.example.demo.view

import com.example.demo.app.Styles
import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXPasswordField
import com.jfoenix.controls.JFXTextField
import javafx.beans.property.ObjectProperty
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import tornadofx.*

class MainView : View("Hello TornadoFX") {


    var rightView by singleAssign<Form>()
    var leftView by singleAssign<Form>()
    var rightBanner by singleAssign<VBox>()
    var leftBanner by singleAssign<VBox>()
    var animateStackPan by singleAssign<StackPane>()

    private val radius = 20
    private val vboxPadding = 30.00
    private val myFocusColer = Color.GRAY


    override val root = stackpane {
        prefWidth = 1120.00
        prefHeight = 700.00

        style {
            backgroundColor = multi(Color.TRANSPARENT)
        }


        hbox {
            maxWidth = 800.00
            maxHeight = 400.00
            alignment = Pos.CENTER_LEFT

            style {
                backgroundColor = multi(Color.rgb(35, 35, 35, .9))
                backgroundRadius = multi(CssBox(radius.px, radius.px, radius.px, radius.px))
            }
        }

        animateStackPan = stackpane {

            rightBanner = vbox {
                maxHeight = 300.00
                maxWidth = 400.00
                alignment = Pos.CENTER

                stackpaneConstraints {
                    marginLeft = 150.00
                    alignment = Pos.CENTER_LEFT
                }

                label("Already have an \nAccount?") {
                    textFill = Color.WHITE
                    textAlignment = TextAlignment.CENTER

                    vboxConstraints {
                        marginBottom = 20.00
                    }

                    style{
                        fontSize = 25.px
                    }
                }
                this+= JFXButton("Sign In").apply {
                    style {
                        backgroundColor = multi(Color.WHITE)
                        textFill = Color.BLACK

                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        leftView.visibleProperty().value = true
                        rightView.visibleProperty().value = false

                        animateStackPan.paddingLeft = 350
                        animateStackPan.paddingLeftProperty.animate(0, 0.3.seconds)

                        rightBanner.visibleProperty().value = false
                        leftBanner.visibleProperty().animate(true, 0.2.seconds)
                    }
                }
            }

            leftView = form {
                maxWidth = 400.00
                maxHeight = 500.00
                paddingAll = vboxPadding
                alignment = Pos.CENTER

                stackpaneConstraints {
                    marginLeft = 180.00
                    alignment = Pos.CENTER_LEFT
                }

                style {
                    backgroundColor = multi(Color.WHITE)
                    backgroundRadius = multi(CssBox(radius.px, radius.px, radius.px, radius.px))
                }

                fieldset {

                    alignment = Pos.CENTER

                    label("Sign In") {
                        style {
                            fontSize = 38.px
                            textFill = c("#E30000")
                        }
                    }

                    field {
                        this += JFXTextField().apply {
                            promptText = "Username"
                            isLabelFloat = true

                            focusColor = myFocusColer


                        }

                        paddingTop = 50
                        paddingBottom = 30.00
                    }

                    field {
                        this += JFXPasswordField().apply {
                            promptText = "Password"
                            isLabelFloat = true

                            focusColor = myFocusColer
                        }

                        paddingBottom = 30.00
                    }

                    this += JFXButton("Sign In").apply{
                        buttonType = JFXButton.ButtonType.RAISED

                        style {
                            backgroundColor = multi(c("#E30000"))
                            textFill = Color.WHITE

                            fontWeight = FontWeight.BOLD
                        }

                        setOnAction {
                            close()
                            find<DashBoardView>().openWindow()
                        }
                    }
                }
            }

            leftBanner = vbox {
                maxHeight = 300.00
                maxWidth = 400.00
                alignment = Pos.CENTER

                stackpaneConstraints {
                    marginRight = 150.00
                    alignment = Pos.CENTER_RIGHT
                }

                label("Do not have an \nAccount?") {
                    textFill = Color.WHITE
                    textAlignment = TextAlignment.CENTER

                    vboxConstraints {
                        marginBottom = 20.00
                    }

                    style{
                        fontSize = 25.px
                    }
                }
                this+= JFXButton("Request for One").apply {
                    style {
                        backgroundColor = multi(Color.WHITE)
                        textFill = Color.BLACK

                        fontWeight = FontWeight.BOLD
                    }

                    setOnAction {
                        leftView.visibleProperty().value = false
                        rightView.visibleProperty().value = true

                        animateStackPan.paddingRight = 350
                        animateStackPan.paddingRightProperty.animate(0, 0.3.seconds)

                        leftBanner.visibleProperty().value = false
                        rightBanner.visibleProperty().animate(true, 0.2.seconds)
                    }
                }
            }

            rightView = form {
                maxWidth = 400.00
                maxHeight = 500.00
                paddingAll = vboxPadding
                alignment = Pos.CENTER

                stackpaneConstraints {
                    marginRight = 180.00
                    alignment = Pos.CENTER_RIGHT
                }

                style {
                    backgroundColor = multi(Color.WHITE)

                    val radius = 20
                    backgroundRadius = multi(CssBox(radius.px, radius.px, radius.px, radius.px))
                }

                fieldset {

                    alignment = Pos.CENTER

                    label("Request an Account") {
                        style {
                            fontSize = 28.px
                            textFill = c("#E30000")
                        }
                    }

                    field {
                        this += JFXTextField().apply {
                            promptText = "Username"
                            isLabelFloat = true
                            focusColor = myFocusColer
                        }

                        paddingTop = 50
                        paddingBottom = 30.00
                    }

                    field {
                        this += JFXTextField().apply {
                            promptText = "Email"
                            isLabelFloat = true
                            focusColor = myFocusColer
                        }

                        paddingBottom = 30.00
                    }

                    field {
                        this += JFXPasswordField().apply {
                            promptText = "Password"
                            isLabelFloat = true
                            focusColor = myFocusColer
                        }

                        paddingBottom = 30.00
                    }

                    this += JFXButton("Submit Request").apply{
                        buttonType = JFXButton.ButtonType.RAISED

                        style {
                            backgroundColor = multi(c("#E30000"))
                            textFill = Color.WHITE

                            fontWeight = FontWeight.BOLD
                        }
                    }
                }
            }
        }

        leftView.visibleProperty().value = false
    }

    override fun onDock() {
        super.onDock()
        root.scene.fill = Color.TRANSPARENT
    }
}