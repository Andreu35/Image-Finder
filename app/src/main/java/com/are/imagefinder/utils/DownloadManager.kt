package com.are.imagefinder.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.are.imagefinder.R
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DownloadManager(private val context: Context) {

    /**
     * Download the image from the current position
     * @param image Image Path
     */
    fun downloadPicture(image: String) {
        CoroutineScope(Dispatchers.IO).launch {
            saveImage(
                Glide.with(context)
                    .asBitmap()
                    .load(image)
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
                    .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
                    .submit().get()
            )
        }
    }

    /**
     * Save image into File from Bitmap
     * @param image Bitmap
     */
    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null
        val imageFileName =
            "${context.resources.getString(R.string.app_name)}_${System.currentTimeMillis()}.jpg"
        val storageDir = File(
            "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}${File.separator}${
                context.resources.getString(
                    R.string.app_name
                )
            }"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery and show Toast.
            addImageToGallery(savedImagePath)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Image Saved Successfully!", Toast.LENGTH_LONG).show()
            }
        }
        return savedImagePath
    }

    /**
     * Add the saved image to the gallery system.
     */
    private fun addImageToGallery(imagePath: String?) {
        imagePath?.let { path ->
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
                data = Uri.fromFile(File(path))
            }
            context.sendBroadcast(mediaScanIntent)
        }
    }
}