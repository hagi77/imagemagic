package eu.hagisoft.imgmagic.features.modifyimage.repositories

import android.graphics.Bitmap

interface ImagesRepository {
    fun loadImage(): Bitmap
    fun saveImage(image: Bitmap)
}

class ImagesRepositoryImpl: ImagesRepository {
    override fun loadImage(): Bitmap {
        TODO("Not yet implemented")
    }

    override fun saveImage(image: Bitmap) {
        TODO("Not yet implemented")
    }
}