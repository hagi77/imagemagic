package eu.hagisoft.imgmagic.features.modifyimage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoadImageViewModel(val loadScaledImageUseCase: LoadScaledImageUseCase) : ViewModel() {

    private val _state = MutableStateFlow<ViewState>(ViewState.Idle)
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ViewEvent>()
    val events = _events.asSharedFlow()

    fun loadImageClicked() {
        _state.value = ViewState.Loading

        viewModelScope.launch {
            delay(1000)
            _state.value = ViewState.Idle
            _events.emit(ViewEvent.GoToImageEdit)
        }
    }

    sealed class ViewState {
        object Idle : ViewState()
        object Loading: ViewState()
    }

    sealed class ViewEvent {
        object GoToImageEdit: ViewEvent()
    }
}
