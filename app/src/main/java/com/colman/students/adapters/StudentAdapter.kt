package com.colman.students.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.colman.students.R
import com.colman.students.models.Student

class StudentAdapter(private var students: List<Student>,
                     private val onStudentClick: (Student) -> Unit,
                     private val onCheckedChange: (Student, Boolean) -> Unit) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        val idTextView: TextView = itemView.findViewById(R.id.student_id)
        val checkBox: CheckBox = itemView.findViewById(R.id.student_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_student_list_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.nameTextView.text = student.name
        holder.idTextView.text = student.id
        holder.checkBox.isChecked = student.isChecked

        // Handle click event
        holder.itemView.setOnClickListener {
            onStudentClick(student)
        }

        // Handle checkbox changes
        holder.checkBox.setOnCheckedChangeListener(null) // Avoid triggering during recycling
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(student, isChecked)
        }
    }

    override fun getItemCount(): Int = students.size
}
