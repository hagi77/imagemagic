package eu.hagisoft.imgmagic.features.modifyimage.repositories

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import java.io.FileDescriptor

interface ImagesRepository {
    fun setImageUri(uri: Uri)
    fun getImageFile(): FileDescriptor?
    fun getImagePreview(): Bitmap?
    fun setImagePreview(image: Bitmap)
}

class ImagesRepositoryImpl(val context: Context) : ImagesRepository {
    private var imageUri: Uri? = null
    private var imagePreview: Bitmap? = null

    override fun setImageUri(uri: Uri) {
        imageUri = uri
    }

    override fun getImageFile(): FileDescriptor? =
        imageUri?.let {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(it, "r")
            parcelFileDescriptor?.fileDescriptor
        }

    override fun getImagePreview(): Bitmap? = imagePreview

    override fun setImagePreview(image: Bitmap) {
        imagePreview = image
    }
}
