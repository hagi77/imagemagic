package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
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
import eu.hagisoft.imgmagic.databinding.LoadImageFragmentBinding
import eu.hagisoft.imgmagic.utils.hide
import eu.hagisoft.imgmagic.utils.show
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadImageFragment : Fragment() {

    private val viewModel: LoadImageViewModel by viewModel()
    private lateinit var binding: LoadImageFragmentBinding

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchPicker()
            } else {
                showPermissionsErrorMessage()
            }
        }

    private val requestImageLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            it?.let { uri ->
                viewModel.onImageChosen(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoadImageFragmentBinding.inflate(layoutInflater, container, false)
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

    private fun handleState(state: LoadImageViewModel.ViewState) {
        loading(state.loading)
    }

    private fun loading(loading: Boolean) {
        with(binding) {
            imageLoadCta.isEnabled = !loading
            if (loading) {
                imageLoadProgress.show()
            } else {
                imageLoadProgress.hide()
            }
        }
    }

    private fun handleEvent(event: LoadImageViewModel.ViewEvent) {
        when (event) {
            LoadImageViewModel.ViewEvent.GoToImageEdit -> gotoEditImage()
            LoadImageViewModel.ViewEvent.ChooseImage -> selectImage()
            LoadImageViewModel.ViewEvent.ImageLoadingError -> showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        Toast.makeText(context, R.string.image_load_fail_label, Toast.LENGTH_LONG).show()
    }

    private fun showPermissionsErrorMessage() {
        Toast.makeText(context, R.string.image_load_no_permission_fail_label, Toast.LENGTH_LONG)
            .show()
    }

    private fun gotoEditImage() {
        LoadImageFragmentDirections.actionLoadImageFragmentToEditImageFragment().let {
            findNavController().navigate(it)
        }
    }

    private fun selectImage() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                launchPicker()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun launchPicker() {
        requestImageLauncher.launch(arrayOf("image/*"))
    }
}
