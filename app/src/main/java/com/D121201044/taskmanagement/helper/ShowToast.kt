package com.D121201044.taskmanagement

import android.content.Context
import android.widget.Toast


fun Context.toast(message: String){
    Toast.makeText(this, message , Toast.LENGTH_SHORT).show()
}