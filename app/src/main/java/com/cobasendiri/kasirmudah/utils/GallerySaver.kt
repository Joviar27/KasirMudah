package com.cobasendiri.kasirmudah.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GallerySaver(
    context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val appContext = context.applicationContext

    suspend fun saveBitmapToGallery(
        bitmap: Bitmap,
        filename: String
    ): Boolean = withContext(ioDispatcher) {
        val contentResolver = appContext.contentResolver
        val filename = "kasirmudah_${filename}_${System.currentTimeMillis()}.png"

        val imageDetails = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/KasirMudah")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageDetails
        ) ?: return@withContext false

        return@withContext try {
            contentResolver.openOutputStream(imageUri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                imageDetails.clear()
                imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
                contentResolver.update(imageUri, imageDetails, null, null)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            contentResolver.delete(imageUri, null, null)
            false
        }
    }
}