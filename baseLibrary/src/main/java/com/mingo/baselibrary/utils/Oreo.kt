package com.mingo.baselibrary.utils

import android.os.Build

fun isMarsh(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun isOreo(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}