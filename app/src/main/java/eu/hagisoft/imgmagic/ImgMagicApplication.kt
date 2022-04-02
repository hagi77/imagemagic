package eu.hagisoft.imgmagic

import android.app.Application
import eu.hagisoft.imgmagic.di.modifyImageModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ImgMagicApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@ImgMagicApplication)
            modules(modifyImageModule)
        }
    }
}