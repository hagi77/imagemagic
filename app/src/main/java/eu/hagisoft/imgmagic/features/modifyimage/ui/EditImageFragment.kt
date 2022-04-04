package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.state.collect { handleState(it) }
                }
                launch {
                    viewModel.events.collect { handleEvent(it) }
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
        binding.imageEditSlider.value = state.strokeValue

        with(binding.imageEditImageview) {
            setStrokeColor(state.strokeColor.color)
            setStrokeWidth(state.strokeWidth)
            state.image?.let {
                setImageBitmap(it)
            }
        }

        binding.imageEditViewAnimator.displayedChild = if (state.progress) 1 else 0
    }

    private fun handleEvent(event: EditImageViewModel.ViewEvent) {
        when (event) {
            EditImageViewModel.ViewEvent.ClearImage -> binding.imageEditImageview.clear()
            EditImageViewModel.ViewEvent.SaveImage -> viewModel.performImageSaving(binding.imageEditImageview.getPaths())
            EditImageViewModel.ViewEvent.Finish -> findNavController().popBackStack()
            EditImageViewModel.ViewEvent.ImageSavingSuccess -> showSuccessMessage()
            EditImageViewModel.ViewEvent.ImageSavingFailure -> showFailureMessage()
        }
    }

    private fun showFailureMessage() {
        Toast.makeText(
            context,
            R.string.image_save_fail_label,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showSuccessMessage() {
        Toast.makeText(
            context,
            R.string.image_save_success_label,
            Toast.LENGTH_LONG
        ).show()
    }
}