package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var add: Button
    private lateinit var dialog: AlertDialog
    private lateinit var layout: LinearLayout
    private var editMode = false
    private lateinit var editableTaskView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add = findViewById(R.id.add)
        layout = findViewById(R.id.container)

        buildDialog()
        add.setOnClickListener {
            editMode = false
            buildDialog()
            dialog.show()
        }
    }

    private fun buildDialog() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog, null)

        val name = view.findViewById<EditText>(R.id.nameEdit)

        builder.setView(view)
            .setTitle(if (editMode) "Edit Task" else "Enter your Task")
            .setPositiveButton(if (editMode) "UPDATE" else "SAVE") { _, _ ->
                if (editMode) {
                    editableTaskView.text = name.text.toString()
                } else {
                    addCard(name.text.toString())
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
                editMode = false // Reset editMode when dialog is dismissed
            }

        dialog = builder.create()
    }

    private fun addCard(name: String) {
        val view = layoutInflater.inflate(R.layout.card, null)

        val nameView = view.findViewById<TextView>(R.id.name)
        val delete = view.findViewById<Button>(R.id.delete)
        val edit = view.findViewById<Button>(R.id.edit)

        nameView.text = name

        edit.setOnClickListener {
            editMode = true
            editableTaskView = nameView
            buildDialog()
            dialog.show()
        }

        delete.setOnClickListener {
            layout.removeView(view)
        }
        layout.addView(view)
    }
}
