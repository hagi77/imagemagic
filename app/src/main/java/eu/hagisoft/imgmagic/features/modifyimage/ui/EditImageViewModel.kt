package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.pow

class EditImageViewModel(val loadScaledImageUseCase: LoadScaledImageUseCase) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ViewEvent>()
    val events = _events.asSharedFlow()

    init {
        val bitmap = loadScaledImageUseCase()
        val copy = _state.value.copy(image = bitmap)
        _state.value = copy
    }

    fun saveImageClicked() {
        viewModelScope.launch {
            _events.emit(ViewEvent.GoToSaveImage)
        }
    }

    fun clearImageClicked() {

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

    data class ViewState(
        val image: Bitmap? = null,
        val strokeColor: StrokeColor = StrokeColor.BLACK,
        val strokeValue: Float = 6f
    ) {
        val strokeWidth: Float
            get() = strokeValue.pow(2)
    }

    sealed class ViewEvent {
        object GoToSaveImage : ViewEvent()
    }

    enum class StrokeColor(val color: Int) {
        YELLOW(Color.YELLOW),
        BLACK(Color.BLACK),
        GREEN(Color.GREEN),
        BLUE(Color.BLUE)
    }
}
