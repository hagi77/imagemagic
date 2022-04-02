package eu.hagisoft.imgmagic.features.modifyimage.usecases

import android.graphics.Bitmap
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository

interface LoadScaledImageUseCase {
    operator fun invoke(): Bitmap
}

class LoadScaledImageUseCaseImpl(private val imagesRepository: ImagesRepository) : LoadScaledImageUseCase {
    override operator fun invoke(): Bitmap {
        return imagesRepository.loadImage()
    }
}