package eu.hagisoft.imgmagic.di

import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepositoryImpl
import eu.hagisoft.imgmagic.features.modifyimage.ui.EditImageViewModel
import eu.hagisoft.imgmagic.features.modifyimage.ui.LoadImageViewModel
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ApplyPathToImageUseCase
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ApplyPathToImageUseCaseImpl
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ScaleImageUseCase
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ScaleImageUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val modifyImageModule = module {
    single<ImagesRepository> { ImagesRepositoryImpl(get()) }

    factory<ScaleImageUseCase> { ScaleImageUseCaseImpl(get(), get()) }
    factory<ApplyPathToImageUseCase> { ApplyPathToImageUseCaseImpl(get()) }

    viewModel { LoadImageViewModel(get()) }
    viewModel { EditImageViewModel(get(), get()) }
}