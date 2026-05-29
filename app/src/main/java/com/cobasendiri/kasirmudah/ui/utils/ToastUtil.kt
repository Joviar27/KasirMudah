package com.cobasendiri.kasirmudah.ui.utils

import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun String.showToast(context: Context){
        Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
    }
}