package com.theideal.notary.main.company

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theideal.data.model.Company
import com.theideal.notary.databinding.FragmentCompanyBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateCompanyFragment : Fragment() {
    private val companyViewModel by viewModel<CompanyViewModel>()
    private lateinit var binding: FragmentCompanyBinding
    private val company = Company()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCompanyBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.companyViewModel = companyViewModel
        binding.company = company
//        companyViewModel.navToClientTransactionsFees.observe(viewLifecycleOwner) {
//            if (it) {
//                findNavController().navigate(CreateCompanyFragmentDirections.actionCompanyFragmentToClientTransactionsFeesFragment2())
//                companyViewModel.navToClientTransactionsFeesComplete()
//            }
//        }
//        companyViewModel.navToWithCompany.observe(viewLifecycleOwner) {
//            if (it) {
//                findNavController().navigate(CreateCompanyFragmentDirections.actionCompanyFragmentToWorkWithCompanyFragment())
//                companyViewModel.naveToWorkWithCompanyComplete()
//            }
//
//        }
        companyViewModel.companyComplete.observe(viewLifecycleOwner){
            if (it){
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
                companyViewModel.companyCompleteComplete()
            }
        }
        return binding.root
    }

}