package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.models.Paths
import eu.hagisoft.imgmagic.features.modifyimage.repositories.ImagesRepository
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ApplyPathToImageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.pow

class EditImageViewModel(
    imagesRepository: ImagesRepository,
    private val applyPathToImageUseCase: ApplyPathToImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ViewEvent>()
    val events = _events.asSharedFlow()

    init {
        val bitmap = imagesRepository.getImagePreview()
        val copy = _state.value.copy(image = bitmap)
        _state.value = copy
    }

    fun saveImageClicked() {
        viewModelScope.launch {
            _events.emit(ViewEvent.SaveImage)
        }
    }

    fun clearImageClicked() {
        viewModelScope.launch {
            _events.emit(ViewEvent.ClearImage)
        }
    }

    fun colorClicked(color: StrokeColor) {
        val newState = _state.value.copy(strokeColor = color)
        _state.value = newState
    }

    fun onStrokeWidthChanged(stroke: Float, fromUser: Boolean) {
        if (fromUser) {
            val newState = _state.value.copy(strokeValue = stroke)
            _state.value = newState
        }
    }

    fun performImageSaving(paths: Paths) {
        viewModelScope.launch {
            setInProgress(true)

            runCatching { applyPathToImageUseCase.invoke(paths) }
                .onSuccess {
                    _events.emit(ViewEvent.ImageSavingSuccess)
                    _events.emit(ViewEvent.Finish)
                }
                .onFailure {
                    _events.emit(ViewEvent.ImageSavingFailure)
                }

            setInProgress(false)
        }
    }

    private fun setInProgress(progress: Boolean) {
        val newState = _state.value.copy(progress = progress)
        _state.value = newState
    }

    data class ViewState(
        val image: Bitmap? = null,
        val strokeColor: StrokeColor = StrokeColor.BLACK,
        val strokeValue: Float = 6f,
        val progress: Boolean = false
    ) {
        val strokeWidth: Float
            get() = strokeValue.pow(2)
    }

    sealed class ViewEvent {
        object SaveImage : ViewEvent()
        object ImageSavingSuccess : ViewEvent()
        object ImageSavingFailure : ViewEvent()
        object ClearImage : ViewEvent()
        object Finish : ViewEvent()
    }

    enum class StrokeColor(val color: Int) {
        YELLOW(Color.YELLOW),
        BLACK(Color.BLACK),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE)
    }
}
