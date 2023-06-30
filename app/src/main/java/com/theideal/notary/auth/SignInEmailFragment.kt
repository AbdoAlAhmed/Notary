package com.theideal.notary.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.User
import com.theideal.notary.databinding.FragmentSignInMailBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInEmailFragment : Fragment() {
    private lateinit var binding: FragmentSignInMailBinding
    private val signInEmailViewModel by viewModel<SignInEmailViewModel>()
    private val user = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInMailBinding.inflate(inflater, container, false)
        binding.signInEmailViewModel = signInEmailViewModel
        binding.user = user
        binding.lifecycleOwner = this
        signInEmailViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                signInEmailViewModel.snackBarComplete()
            }
        }
        signInEmailViewModel.startMainActivity.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return binding.root
    }

}