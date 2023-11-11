package com.theideal.notary.main.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.data.model.User
import com.theideal.notary.auth.AuthenticationActivity
import com.theideal.notary.databinding.FragmentMoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private val moreViewModel by viewModel<MoreViewModel>()
    private lateinit var moreAdapter: MoreAdapter
    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moreViewModel.getContact()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)
        setupBinding()
        setupObservers()
        setupAdapter()
        return binding.root
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.moreViewModel = moreViewModel
        binding.user = user
    }

    private fun setupObservers() {
        moreViewModel.user.observe(viewLifecycleOwner) {
            user = it
            binding.user = user
        }

        moreViewModel.logout.observe(viewLifecycleOwner) {
            if (it) {
                navigateToAuthentication()
            }
        }
    }

    private fun setupAdapter() {
        moreAdapter = MoreAdapter(MoreAdapter.OnclickListener {
            moreViewModel.clickItem(it.id)
        })
        binding.rvMore.adapter = moreAdapter
    }

    private fun navigateToAuthentication() {
        requireActivity().startActivity(
            Intent(
                requireActivity(),
                AuthenticationActivity::class.java
            )
        )
        requireActivity().finish()
    }
}
