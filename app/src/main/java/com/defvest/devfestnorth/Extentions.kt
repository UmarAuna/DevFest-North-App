package com.defvest.devfestnorth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.fragment.app.FragmentActivity
import android.view.View
import android.widget.ProgressBar

fun ProgressBar.showProgress() {
    visibility = View.VISIBLE
}

fun ProgressBar.hideProgress() {
    visibility = View.GONE
}
