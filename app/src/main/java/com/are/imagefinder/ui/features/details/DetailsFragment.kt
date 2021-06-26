package com.are.imagefinder.ui.features.details

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.are.imagefinder.data.model.Item
import com.are.imagefinder.databinding.FragmentDetailsBinding
import com.are.imagefinder.extensions.autoCleared
import com.are.imagefinder.ui.BaseFragment
import com.are.imagefinder.utils.Constants
import com.are.imagefinder.utils.DownloadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : BaseFragment() {

    private var binding: FragmentDetailsBinding by autoCleared()
    private var currentPosition = 0
    private var items: MutableList<Item>? = null
    private var downloadManager: DownloadManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            items = it.getSerializable(Constants.ITEMS) as MutableList<Item>
            currentPosition = it.getInt(Constants.POSITION)
        }
        downloadManager = DownloadManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageDetailsClose.setOnClickListener { onBackPressed() }
        binding.imageDetailsDownload.setOnClickListener {
            if (checkPermission()) {
                downloadManager?.downloadPicture(
                    items?.get(
                        currentPosition
                    )?.media?.m!!
                )
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001)
            }
        }

        binding.viewPager2.apply {
            adapter = DetailsAdapter(items!!).apply {
                itemClickListener = { _, _, _ ->
                    showOrHideAnimation()
                }
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    currentPosition = position
                    binding.imageDetailsTitle.text = items?.get(position)?.title
                    binding.imageDetailsDescription.text =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(
                                items?.get(position)?.description,
                                Html.FROM_HTML_OPTION_USE_CSS_COLORS
                            )
                        } else {
                            Html.fromHtml(items?.get(position)?.description)
                        }
                }
            })
            setCurrentItem(currentPosition, false)
        }
    }

    /**
     * Show or hide with alpha animation the Title and Description Layouts.
     */
    private fun showOrHideAnimation() {
        val animationIn = AlphaAnimation(0f, 1f).apply {
            duration = 300
            fillAfter = true
            interpolator = LinearInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    binding.layoutTitle.alpha = 0f
                    binding.layoutDescription.alpha = 0f
                }

                override fun onAnimationEnd(animation: Animation?) {
                    binding.layoutTitle.alpha = 1f
                    binding.layoutDescription.alpha = 1f
                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })
        }
        val animationOut = AlphaAnimation(1f, 0f).apply {
            duration = 300
            fillAfter = true
            interpolator = LinearInterpolator()
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    binding.layoutTitle.alpha = 1f
                    binding.layoutDescription.alpha = 1f
                }

                override fun onAnimationEnd(animation: Animation?) {
                    binding.layoutTitle.alpha = 0f
                    binding.layoutDescription.alpha = 0f
                }

                override fun onAnimationRepeat(animation: Animation?) {}

            })
        }
        if (binding.layoutTitle.alpha == 1f && binding.layoutDescription.alpha == 1f) {
            binding.layoutTitle.startAnimation(animationOut)
            binding.layoutDescription.startAnimation(animationOut)
        } else {
            binding.layoutTitle.startAnimation(animationIn)
            binding.layoutDescription.startAnimation(animationIn)
        }

    }

    /**
     * Check permissions to save the picture
     */
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

}