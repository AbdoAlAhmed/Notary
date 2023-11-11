package com.theideal.notary.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.User
import com.theideal.notary.databinding.FragmentForgetPasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ForgetPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgetPasswordBinding
    private val authViewModel: AuthenticationViewModel by viewModel<AuthenticationViewModel>()
    private val user = User()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgetPasswordBinding.inflate(inflater, container, false)
        binding.authViewModel = authViewModel
        binding.user = user
        binding.lifecycleOwner = this
        authViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                authViewModel.snackBarMessageDisplayed()
            }
        }
        authViewModel.navToSignInFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSignInMailFragment())
                authViewModel.doneNavToSignInFragment()
            }
        }

        return binding.root
    }


}