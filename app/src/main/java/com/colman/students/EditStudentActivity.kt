package com.colman.students

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.colman.students.repositories.StudentRepository

class EditStudentActivity : AppCompatActivity() {
    private val students = StudentRepository.students

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val studentId = intent.getStringExtra("student_id")
        val student = students.find { it.id == studentId }

        val saveButton: Button = findViewById(R.id.edit_student_save_button)

        val id = findViewById<EditText>(R.id.edit_student_id_text_field)
        val name = findViewById<EditText>(R.id.edit_student_name_text_field)
        val phone = findViewById<EditText>(R.id.edit_student_phone_text_field)
        val address = findViewById<EditText>(R.id.edit_student_address_text_field)
        val isChecked = findViewById<CheckBox>(R.id.student_checkbox)

        val initialId = student?.id ?: ""
        val initialName = student?.name ?: ""
        val initialPhone = student?.phone ?: ""
        val initialAddress = student?.address ?: ""
        val initialChecked = student?.isChecked ?: false

        id.setText(student?.id ?: "")
        name.setText(student?.name ?: "")
        phone.setText(student?.phone ?: "")
        address.setText(student?.address ?: "")
        isChecked.isChecked = student?.isChecked ?: false

        fun areFieldsValid(): Boolean {
            return id.text.isNotBlank() &&
                   name.text.isNotBlank() &&
                   phone.text.isNotBlank() &&
                   address.text.isNotBlank()
        }

        fun fieldsChanged(): Boolean {
            return id.text.toString() != initialId ||
                   name.text.toString() != initialName ||
                   phone.text.toString() != initialPhone ||
                   address.text.toString() != initialAddress ||
                   isChecked.isChecked != initialChecked
        }

        fun canSave(): Boolean {
            return areFieldsValid() && fieldsChanged()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Not relevant
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                saveButton.isEnabled = canSave()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Not relevant
            }
        }

        id.addTextChangedListener(textWatcher)
        name.addTextChangedListener(textWatcher)
        phone.addTextChangedListener(textWatcher)
        address.addTextChangedListener(textWatcher)
        isChecked.setOnCheckedChangeListener { _, _ ->
            saveButton.isEnabled = canSave()
        }

        // Show back arrow in the toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val updatedId = id.text.toString()
            val updatedName = name.text.toString()
            val updatedPhone = phone.text.toString()
            val updatedAddress = address.text.toString()

            if (updatedId.isNotEmpty() &&
                updatedName.isNotEmpty() &&
                updatedPhone.isNotEmpty() &&
                updatedAddress.isNotEmpty() &&
                student != null) {
                student.id = updatedId
                student.name = updatedName
                student.phone = updatedPhone
                student.address = updatedAddress
                student.isChecked = isChecked.isChecked
            }
            finish()
        }

        val deleteButton: Button = findViewById(R.id.edit_student_delete_button)
        deleteButton.setOnClickListener {
            students.remove(student)
            val intent = Intent(this, StudentListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        val cancelButton: Button = findViewById(R.id.edit_student_cancel_button)
        cancelButton.setOnClickListener {
            finish()
        }
    }
}