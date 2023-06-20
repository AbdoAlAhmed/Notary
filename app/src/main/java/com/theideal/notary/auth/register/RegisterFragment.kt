package com.theideal.notary.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.theideal.notary.databinding.FragmentRegsiterBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegsiterBinding
    private val registerViewModel by viewModel<RegisterViewModel>()
    private val user: RegisterFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegsiterBinding.inflate(inflater, container, false)
        binding.user = user.user
        binding.signInInformationViewModel = registerViewModel
        binding.lifecycleOwner = this
        registerViewModel.startMainActivity.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
                registerViewModel.doneStartMainActivity()
            }
        }
        registerViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        registerViewModel.navToUserInfo.observe(viewLifecycleOwner) {
            if (it) {
//                findNavController().navigate(
//                    RegisterFragmentDirections.actionSignInInformationFragmentToUserInfoFragment(
//                        user = user.user
//                    )
//                )
                registerViewModel.doneNavToUserInfo()
            }
        }

        return binding.root
    }


}