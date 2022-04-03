package eu.hagisoft.imgmagic.features.modifyimage.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import eu.hagisoft.imgmagic.R
import java.io.BufferedInputStream

interface ImagesRepository {
    fun loadImage(): Bitmap
    fun saveImage(image: Bitmap)
}

class ImagesRepositoryImpl(val context: Context): ImagesRepository {

    override fun loadImage(): Bitmap {
        val input = context.resources.openRawResource(R.raw.fall)
        val bufferedInputStream = BufferedInputStream(input)
        return BitmapFactory.decodeStream(bufferedInputStream)
    }

    override fun saveImage(image: Bitmap) {
        TODO("Not yet implemented")
    }
}