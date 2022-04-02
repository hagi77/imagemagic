package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hagisoft.imgmagic.features.modifyimage.usecases.LoadScaledImageUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadImageViewModel(val loadScaledImageUseCase: LoadScaledImageUseCase) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun loadImageClicked(view: View) {
        _loading.value = true

        viewModelScope.launch {
            delay(1000)
            _loading.value = false
        }
    }
}