package com.theideal.notary.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.notary.databinding.FragmentUserInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserInfoFragment : Fragment() {
    private lateinit var binding: FragmentUserInfoBinding
    private val userInfoViewModel by viewModel<UserInfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)


        return binding.root
    }


}