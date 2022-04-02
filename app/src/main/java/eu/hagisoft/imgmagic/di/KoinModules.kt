package eu.hagisoft.imgmagic.di

import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepositoryImpl
import eu.hagisoft.imgmagic.features.modifyimage.ui.LoadImageViewModel
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCase
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modifyImageModule = module {
    single<ImagesRepository> { ImagesRepositoryImpl() }
    single<LoadScaledImageUseCase> { LoadScaledImageUseCaseImpl(get()) }

    viewModel { LoadImageViewModel(get()) }
}