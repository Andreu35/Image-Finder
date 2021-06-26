package com.are.imagefinder.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    fun openNewFragment(action: Int, bundle: Bundle) {
        findNavController().navigate(action, bundle)
    }

    fun openNewFragment(action: Int) {
        findNavController().navigate(action)
    }

    fun setUpToolbar(toolbar: Toolbar, showTitle: Boolean, showHome: Boolean) {
        val activity = (activity as AppCompatActivity)
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar!!.setDisplayShowTitleEnabled(showTitle)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(showHome)
    }

    fun onBackPressed(){
        (activity as AppCompatActivity?)!!.onBackPressed()
    }
}