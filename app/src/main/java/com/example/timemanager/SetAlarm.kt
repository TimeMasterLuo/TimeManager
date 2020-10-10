package com.example.timemanager

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

class SetAlarm : AppCompatActivity() {
    var friend_name = ""
    var chooseFriendFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
    }
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkBox2 -> {
                    if (checked)
                    {
                        //pump a message
                        val selectedItems = ArrayList<Int>() // Where we track the selected items
                         AlertDialog.Builder(this).apply {
                             setTitle("好友列表:")
                             setMultiChoiceItems(R.array.my_friends, null,
                                 DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                                     if (isChecked) {
                                         // If the user checked the item, add it to the selected items
                                         selectedItems.add(which)
                                     } else if (selectedItems.contains(which)) {
                                         // Else, if the item is already in the array, remove it
                                         selectedItems.remove(Integer.valueOf(which))
                                     }
                                 })
                                 // Set the action buttons
                             setPositiveButton("OK",
                                 DialogInterface.OnClickListener { dialog, id ->
                                     // User clicked OK, so save the selectedItems results somewhere
                                     // or return them to the component that opened the dialog
                                    })
                             setNegativeButton("CANCEL",
                                 DialogInterface.OnClickListener { dialog, id ->
                                 })
                            show()
                        }
                        //change some component visible
                        val friendName : TextView = findViewById(R.id.friend_name)
                        friendName.setVisibility(View.VISIBLE);
                        friendName.setText("小花")
                        val setPraise : TextView = findViewById(R.id.set_praise)
                        setPraise.setVisibility(View.VISIBLE);
                        val price : EditText = findViewById(R.id.price)
                        price.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        //change some component invisible
                        val friendName : TextView = findViewById(R.id.friend_name)
                        friendName.setVisibility(View.GONE);
                        val setPraise : TextView = findViewById(R.id.set_praise)
                        setPraise.setVisibility(View.GONE);
                        val price : EditText = findViewById(R.id.price)
                        price.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

}
