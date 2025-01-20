package com.colman.students

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.colman.students.repositories.StudentRepository

class StudentDetailsActivity : AppCompatActivity() {
    private var studentId: String? = null

    private val editStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newId = result.data?.getStringExtra("updatedStudentId")
            if (!newId.isNullOrBlank()) {
                studentId = newId
                intent.putExtra("student_id", newId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        studentId = intent.getStringExtra("student_id")

        val studentName: String? = intent.getStringExtra("student_name")
        val studentPhone: String? = intent.getStringExtra("student_phone")
        val studentAddress: String? = intent.getStringExtra("student_address")
        val studentIsChecked: Boolean = intent.getBooleanExtra("student_is_checked", false)

        // Display the details in the UI
        findViewById<TextView>(R.id.student_id_text).text = studentId
        findViewById<TextView>(R.id.student_name_text).text = studentName
        findViewById<TextView>(R.id.student_phone_text).text = studentPhone
        findViewById<TextView>(R.id.student_address_text).text = studentAddress
        findViewById<CheckBox>(R.id.student_checkbox).isChecked = studentIsChecked

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val editButton: Button = findViewById(R.id.student_edit_button)
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_id", studentId)
            editStudentLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val updatedStudent = StudentRepository.students.find { it.id == studentId }

        findViewById<TextView>(R.id.student_id_text).text = updatedStudent?.id
        findViewById<TextView>(R.id.student_name_text).text = updatedStudent?.name
        findViewById<TextView>(R.id.student_phone_text).text = updatedStudent?.phone
        findViewById<TextView>(R.id.student_address_text).text = updatedStudent?.address
        findViewById<CheckBox>(R.id.student_checkbox).isChecked = updatedStudent?.isChecked ?: false
    }
}
