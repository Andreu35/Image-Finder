package com.are.imagefinder.ui.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.are.imagefinder.R
import com.are.imagefinder.databinding.FragmentSplashBinding
import com.are.imagefinder.extensions.autoCleared
import com.are.imagefinder.ui.BaseFragment
import kotlinx.coroutines.*

class SplashFragment : BaseFragment() {
    private val activityScope = CoroutineScope(Dispatchers.IO)
    private var binding: FragmentSplashBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityScope.launch(Dispatchers.Main) {
            delay(2000)
            openNewFragment(R.id.action_splash_to_home)
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}