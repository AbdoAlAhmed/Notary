package com.theideal.notary.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.theideal.notary.databinding.FragmentUserInfoBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : Fragment() {
    private lateinit var binding: FragmentUserInfoBinding
    private val userInfoViewModel by viewModel<UserInfoViewModel>()
    private val user: UserInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        binding.userInfoViewModel = userInfoViewModel
        binding.user = user.user

        userInfoViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                userInfoViewModel.snackBarComplete()
            }
        }

        userInfoViewModel.startMainActivity.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.lifecycleOwner = this

        return binding.root
    }


}