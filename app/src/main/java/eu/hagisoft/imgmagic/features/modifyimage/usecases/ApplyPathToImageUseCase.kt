package eu.hagisoft.imgmagic.features.modifyimage.usecases

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.provider.MediaStore
import eu.hagisoft.imgmagic.features.modifyimage.models.Paths
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

interface ApplyPathToImageUseCase {
    suspend operator fun invoke(paths: Paths): Result<String>
}

class ApplyPathToImageUseCaseImpl(
    private val imagesRepository: ImagesRepository,
    private val context: Context
) : ApplyPathToImageUseCase {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    override suspend fun invoke(paths: Paths): Result<String> {
        val uri = imagesRepository.getImageFile()?.let {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            options.inMutable = true
            BitmapFactory.decodeFileDescriptor(it, null, options)
        }?.let { bitmap ->
            val canvas = Canvas(bitmap)
            paths.getScaledPaths(bitmap.width, bitmap.height).forEach {
                updatePaint(it.second, it.third)
                canvas.drawPath(it.first, paint)
            }
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                bitmap,
                "modified bitmap",
                "modified bitmap description"
            )
        }

        return if (uri.isNullOrEmpty()) {
            throw IllegalStateException()
        } else {
            success(uri)
        }
    }

    private fun updatePaint(color: Int, strokeWidth: Float) {
        paint.color = color
        paint.strokeWidth = strokeWidth
    }
}