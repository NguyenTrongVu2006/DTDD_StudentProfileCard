package com.ute.studentprofile.model

data class Student(
    val id: String,
    val name: String,
    val className: String,
    val email: String,
    val phoneNumber: String,
    val gpa: Double
)