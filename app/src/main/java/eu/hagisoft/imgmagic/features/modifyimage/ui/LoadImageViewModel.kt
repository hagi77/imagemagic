package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.ScaleImageUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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
            _state.value = ViewState(loading = true)
            scaleImageUseCase(uri)
            _state.value = ViewState(loading = false)
            _events.emit(ViewEvent.GoToImageEdit)
        }
    }

    data class ViewState(val loading: Boolean)

    sealed class ViewEvent {
        object ChooseImage: ViewEvent()
        object GoToImageEdit: ViewEvent()
    }
}
