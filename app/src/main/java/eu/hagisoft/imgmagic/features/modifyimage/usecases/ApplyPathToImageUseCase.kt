package eu.hagisoft.imgmagic.features.modifyimage.usecases

import eu.hagisoft.imgmagic.features.modifyimage.models.Paths
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository

interface ApplyPathToImageUseCase {
    suspend operator fun invoke(paths: Paths)
}

class ApplyPathToImageUseCaseImpl(private val imagesRepository: ImagesRepository): ApplyPathToImageUseCase {
    override suspend fun invoke(paths: Paths) {

    }
}