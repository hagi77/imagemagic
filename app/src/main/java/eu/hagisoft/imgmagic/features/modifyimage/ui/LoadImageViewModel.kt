package eu.hagisoft.imgmagic.features.modifyimage.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoadImageViewModel(val loadScaledImageUseCase: LoadScaledImageUseCase) : ViewModel() {

    private val _state = MutableStateFlow(ViewState(loading = false))
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<ViewEvent>()
    val events = _events.asSharedFlow()

    fun loadImageClicked() {
        _state.value = ViewState(loading = true)

        viewModelScope.launch {
            delay(500)
            _state.value = ViewState(loading = false)
            _events.emit(ViewEvent.GoToImageEdit)
        }
    }

    data class ViewState(val loading: Boolean)

    sealed class ViewEvent {
        object GoToImageEdit: ViewEvent()
    }
}
