package com.alfaradev.notification.utils.ext

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}