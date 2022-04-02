package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import eu.hagisoft.imgmagic.databinding.EditImageFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditImageFragment : Fragment() {

    private val viewModel: EditImageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = EditImageFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }
}