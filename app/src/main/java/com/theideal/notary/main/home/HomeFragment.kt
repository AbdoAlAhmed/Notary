package com.theideal.notary.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.notary.auth.AuthenticationActivity
import com.theideal.notary.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModel<HomeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeViewModel = homeViewModel
        binding.lifecycleOwner = this

        homeViewModel.signOut.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(
                    Intent(
                        requireActivity(),
                        AuthenticationActivity::class.java
                    )
                )
                requireActivity().finish()
            }
        }

        return binding.root
    }


}