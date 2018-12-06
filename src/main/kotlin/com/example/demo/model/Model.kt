package com.example.demo.model

import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

data class UserDemo(val id: Int,
                    val name: String,
                    val userName:String,
                    val email:String,
                    val password: String,
                    val status: Status,
                    val image : ByteArray = IOUtils.toByteArray(FileInputStream(File("src\\main\\resources\\logo\\user.jpg"))))
