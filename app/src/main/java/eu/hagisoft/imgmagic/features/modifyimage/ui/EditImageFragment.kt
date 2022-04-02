package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import eu.hagisoft.imgmagic.R
import eu.hagisoft.imgmagic.databinding.EditImageFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageFragment : Fragment() {

    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var binding: EditImageFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditImageFragmentBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect {
                        handleState(it)
                    }
                }
                launch {
                    viewModel.events.collect {
                        handleEvent(it)
                    }
                }
            }
        }
    }

    private fun handleState(state: EditImageViewModel.ViewState) {
        val selectedColorButtonId = when (state.strokeColor) {
            EditImageViewModel.StrokeColor.BLACK -> R.id.image_edit_black_cta
            EditImageViewModel.StrokeColor.YELLOW -> R.id.image_edit_yellow_cta
            EditImageViewModel.StrokeColor.GREEN -> R.id.image_edit_green_cta
            EditImageViewModel.StrokeColor.BLUE -> R.id.image_edit_blue_cta
        }
        binding.imageEditColorsGroup.check(selectedColorButtonId)
        binding.imageEditSlider.value = state.strokeWidth
    }

    private fun handleEvent(event: EditImageViewModel.ViewEvent) {

    }
}