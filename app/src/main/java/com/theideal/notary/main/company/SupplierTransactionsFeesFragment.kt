package com.theideal.notary.main.company

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theideal.data.model.AdditionalTransactionsFees
import com.theideal.notary.databinding.FragmentSupplierTransactionsFeesBinding
import com.theideal.notary.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SupplierTransactionsFeesFragment : Fragment() {
    private lateinit var binding: FragmentSupplierTransactionsFeesBinding
    private val additionalTransactionsFees = AdditionalTransactionsFees()
    private val transactionFeesViewModel by viewModel<TransactionFeesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentSupplierTransactionsFeesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.transactionFeesViewModel = transactionFeesViewModel
        binding.additionalTransactionFees = additionalTransactionsFees
        transactionFeesViewModel.completeCompanyInfo.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finish()
                transactionFeesViewModel.completeCompanyInfoComplete()
            }
        }
        return binding.root
    }

}