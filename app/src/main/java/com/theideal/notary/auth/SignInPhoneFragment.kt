package com.theideal.notary.auth

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneMultiFactorGenerator
import com.theideal.data.model.User
import com.theideal.notary.R
import com.theideal.notary.databinding.FragmentSignInPhoneBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit



class SignInPhoneFragment : Fragment() {
    private lateinit var binding: FragmentSignInPhoneBinding
    private val signInPhoneViewModel by viewModel<SignInPhoneViewModel>()
    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInPhoneBinding.inflate(inflater, container, false)
        binding.signInPhoneViewModel = signInPhoneViewModel
        binding.user = user
        binding.lifecycleOwner = this
        signInPhoneViewModel.dialogPhoneNotEnabled.observe(viewLifecycleOwner) {
            if (it) {
                signInWithPhone()

            }
        }
        signInPhoneViewModel.navToSinInEmail.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignInPhoneFragmentDirections.actionSignInPhoneFragmentToSignInMailFragment())
                signInPhoneViewModel.navToSinInEmailComplete()
            }
        }
        signInPhoneViewModel.navToCreateAccount.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SignInPhoneFragmentDirections.actionSignInPhoneFragmentToRegisterFragment())
                signInPhoneViewModel.navToCreateAccountComplete()
            }
        }

        return binding.root
    }


    private fun signInWithPhone() {
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setPhoneNumber("+201009768785")
            .setTimeout(120, TimeUnit.SECONDS)
            .requireSmsValidation(true)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }



    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(requireContext(), "complete", Toast.LENGTH_SHORT).show()
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
        }

    }
    private fun sigInPhoneNotEnabled() {
        val dialog = AlertDialog.Builder(requireContext())
        val dialogCreate = dialog.create()
        dialogCreate.setTitle(resources.getString(R.string.dialog_title_sign_in_with_phone_not_enabled))
        dialogCreate.setMessage(resources.getString(R.string.dialog_message_sign_in_with_phone_not_enabled))
        dialogCreate.show()
    }


}