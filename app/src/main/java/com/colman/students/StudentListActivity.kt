package com.colman.students

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.colman.students.adapters.StudentAdapter
import com.colman.students.models.Student
import com.colman.students.repositories.StudentRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentListActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter
    private val students = StudentRepository.students

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        val recyclerView = findViewById<RecyclerView>(R.id.activity_student_list)
        recyclerView.layoutManager = LinearLayoutManager(this)

        studentAdapter = StudentAdapter(
            students,
            { student -> openStudentDetails(student) },
            { student, isChecked -> toggleIsChecked(student, isChecked) }
        )
        recyclerView.adapter = studentAdapter

        val addStudentButton: FloatingActionButton = findViewById(R.id.addStudentButton)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("student_id", student.id)
        intent.putExtra("student_name", student.name)
        intent.putExtra("student_phone", student.phone)
        intent.putExtra("student_address", student.address)
        intent.putExtra("student_is_checked", student.isChecked)
        startActivity(intent)
    }

    private fun toggleIsChecked(student: Student, isChecked: Boolean) {
        student.isChecked = isChecked
    }

    override fun onResume() {
        super.onResume()
        studentAdapter.notifyDataSetChanged()
    }
}