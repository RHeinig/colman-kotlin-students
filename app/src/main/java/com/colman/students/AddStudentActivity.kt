package com.colman.students

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.colman.students.models.Student
import com.colman.students.repositories.StudentRepository

class AddStudentActivity: AppCompatActivity() {
    private val students = StudentRepository.students

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val cancelButton: Button = findViewById(R.id.add_student_cancel_button)
        val saveButton: Button = findViewById(R.id.add_student_save_button)

        val id = findViewById<EditText>(R.id.add_student_id_text_field)
        val name = findViewById<EditText>(R.id.add_student_name_text_field)
        val phone = findViewById<EditText>(R.id.add_student_phone_text_field)
        val address = findViewById<EditText>(R.id.add_student_address_text_field)
        val isChecked = findViewById<CheckBox>(R.id.student_checkbox)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Not relevant
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveButton.isEnabled = id.text.toString().isNotEmpty() &&
                        name.text.toString().isNotEmpty() &&
                        phone.text.toString().isNotEmpty() &&
                        address.text.toString().isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Not relevant
            }
        }

        id.addTextChangedListener(textWatcher)
        name.addTextChangedListener(textWatcher)
        phone.addTextChangedListener(textWatcher)
        address.addTextChangedListener(textWatcher)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveStudent(
                id.text.toString(),
                name.text.toString(),
                phone.text.toString(),
                address.text.toString(),
                isChecked.isChecked
            )
        }
    }

    private fun saveStudent(id: String, name: String, phone: String, address: String, isChecked: Boolean) {
        if (id.isNotEmpty() && name.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {
            val student = Student(id, name, phone, address, isChecked)
            students.add(student)
            finish()
        }
    }
}