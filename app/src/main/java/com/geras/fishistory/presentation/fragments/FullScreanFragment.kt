package com.geras.fishistory.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.geras.fishistory.databinding.FullPhotoBinding

class FullScreanFragment : Fragment() {

    private var _binding: FullPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FullPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uri = arguments?.getString(URI) ?: ""
        binding.fishPhoto.load(uri)
    }

    companion object {

        private const val URI = "URI"

        @JvmStatic
        fun newInstance(uri: String): FullScreanFragment {
            val fragment = FullScreanFragment()
            val args = Bundle()
            args.putString(URI, uri)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
