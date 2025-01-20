package com.colman.students.repositories

import com.colman.students.models.Student

object StudentRepository {
    val students = mutableListOf(
        Student("1", "John Doe", "123-456-7890", "123 Main St"),
        Student("2", "Jane Smith", "987-654-3210", "456 Elm St"),
        Student("3", "Bob Johnson", "555-555-5555", "789 Oak St")
    )

    fun addStudent(student: Student) {
        students.add(student)
    }
}