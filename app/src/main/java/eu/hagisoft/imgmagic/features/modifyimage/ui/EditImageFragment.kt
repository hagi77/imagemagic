package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import eu.hagisoft.imgmagic.R
import eu.hagisoft.imgmagic.databinding.EditImageFragmentBinding
import eu.hagisoft.imgmagic.utils.displayChildIfNecessary
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageFragment : Fragment() {

    private val viewModel: EditImageViewModel by viewModel()
    private lateinit var binding: EditImageFragmentBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchImageSaving()
            } else {
                showPermissionsErrorMessage()
            }
        }

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

        binding.imageEditViewAnimator.displayChildIfNecessary(if (state.progress) 1 else 0)
    }

    private fun handleEvent(event: EditImageViewModel.ViewEvent) {
        when (event) {
            EditImageViewModel.ViewEvent.ClearImage -> binding.imageEditImageview.clear()
            EditImageViewModel.ViewEvent.SaveImage -> handleImageSaving()
            EditImageViewModel.ViewEvent.Finish -> findNavController().popBackStack()
            EditImageViewModel.ViewEvent.ImageSavingSuccess -> showSuccessMessage()
            EditImageViewModel.ViewEvent.ImageSavingFailure -> showFailureMessage()
        }
    }

    private fun handleImageSaving() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchImageSaving()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun launchImageSaving() {
        viewModel.performImageSaving(binding.imageEditImageview.getPaths())
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

    private fun showPermissionsErrorMessage() {
        Toast.makeText(
            context,
            R.string.image_write_no_permission_fail_label,
            Toast.LENGTH_LONG
        ).show()
    }
}