package com.theideal.notary.main.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.theideal.notary.R
import com.theideal.notary.databinding.FragmentClientBinding


class ClientFragment : Fragment() {
    private lateinit var binding: FragmentClientBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentClientBinding.inflate(inflater, container, false)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_client)
        val navController = navHostFragment?.findNavController()
        binding.bottomNavClient.setupWithNavController(navController!!)

        return binding.root
    }

}