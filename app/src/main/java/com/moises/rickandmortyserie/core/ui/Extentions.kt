package com.moises.rickandmortyserie.core.ui

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun Context.toast(message : String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}
