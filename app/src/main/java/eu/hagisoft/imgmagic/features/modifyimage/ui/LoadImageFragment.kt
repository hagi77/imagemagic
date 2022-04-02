package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.hagisoft.imgmagic.R
import eu.hagisoft.imgmagic.databinding.LoadImageFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoadImageFragment : Fragment() {

    private val viewModel: LoadImageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = LoadImageFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    companion object {
        fun newInstance() = LoadImageFragment()
    }
}