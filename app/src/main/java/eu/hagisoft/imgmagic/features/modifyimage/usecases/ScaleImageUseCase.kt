package eu.hagisoft.imgmagic.features.modifyimage.usecases

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository

interface ScaleImageUseCase {
    suspend operator fun invoke(uri: Uri)
}

class ScaleImageUseCaseImpl(
    private val imagesRepository: ImagesRepository,
    private val context: Context
) : ScaleImageUseCase {

    private val screenSize: Int
        get() = context.resources.displayMetrics.widthPixels

    override suspend operator fun invoke(uri: Uri) {
        imagesRepository.setImageUri(uri)
        imagesRepository.getImageFile()?.let {

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFileDescriptor(it, null, options)

            options.inSampleSize = calculateInSampleSize(options, screenSize, screenSize)
            options.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeFileDescriptor(it, null, options)
            imagesRepository.setImagePreview(bitmap)
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}