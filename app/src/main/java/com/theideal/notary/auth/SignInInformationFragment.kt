package com.theideal.notary.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.theideal.notary.databinding.FragmentSignInInformationBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignInInformationFragment : Fragment() {
    private lateinit var binding: FragmentSignInInformationBinding
    private val signInInformationViewModel by viewModel<SignInInformationViewModel>()
    private val user: SignInInformationFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInInformationBinding.inflate(inflater, container, false)
        binding.user = user.user
        binding.signInInformationViewModel = signInInformationViewModel
        binding.lifecycleOwner = this
        signInInformationViewModel.startMainActivity.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
                signInInformationViewModel.doneStartMainActivity()
            }
        }
        signInInformationViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }


}