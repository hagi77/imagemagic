package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import eu.hagisoft.imgmagic.databinding.LoadImageFragmentBinding
import eu.hagisoft.imgmagic.utils.hide
import eu.hagisoft.imgmagic.utils.show
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadImageFragment : Fragment() {

    private val viewModel: LoadImageViewModel by viewModel()
    private lateinit var binding: LoadImageFragmentBinding

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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
            LoadImageViewModel.ViewEvent.GoToImageEdit -> {
                gotoEditImage()
            }
        }
    }

    private fun gotoEditImage() {
        LoadImageFragmentDirections.actionLoadImageFragmentToEditImageFragment().let {
            findNavController().navigate(it)
        }
    }
}