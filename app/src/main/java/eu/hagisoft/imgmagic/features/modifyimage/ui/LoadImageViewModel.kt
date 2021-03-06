package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ScaleImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoadImageViewModel(private val scaleImageUseCase: ScaleImageUseCase) : ViewModel() {

    private val _state = MutableStateFlow(ViewState(loading = false))
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ViewEvent>()
    val events = _events.asSharedFlow()

    fun loadImageClicked() {
        viewModelScope.launch {
            _events.emit(ViewEvent.ChooseImage)
        }
    }

    fun onImageChosen(uri: Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _state.value = ViewState(loading = true)

                runCatching { scaleImageUseCase(uri) }
                    .onSuccess { _events.emit(ViewEvent.GoToImageEdit) }
                    .onFailure { _events.emit(ViewEvent.ImageLoadingError) }

                _state.value = ViewState(loading = false)
            }
        }
    }

    data class ViewState(val loading: Boolean)

    sealed class ViewEvent {
        object ChooseImage : ViewEvent()
        object GoToImageEdit : ViewEvent()
        object ImageLoadingError : ViewEvent()
    }
}
