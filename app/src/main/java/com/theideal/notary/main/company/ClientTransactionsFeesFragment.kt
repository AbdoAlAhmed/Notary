package com.theideal.notary.main.company

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theideal.data.model.AdditionalTransactionsFees
import com.theideal.notary.databinding.FragmentClientTransactionsFeesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClientTransactionsFeesFragment : Fragment() {
    private val transactionFeesViewModel by viewModel<TransactionFeesViewModel>()
    private val additionalTransactionsFees = AdditionalTransactionsFees()
    private lateinit var binding: FragmentClientTransactionsFeesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding =
            FragmentClientTransactionsFeesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.transactionFeesViewModel = transactionFeesViewModel
        binding.additionalTransactionFees = additionalTransactionsFees
        transactionFeesViewModel.navToSupplierTransactionsFees.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(ClientTransactionsFeesFragmentDirections
                    .actionClientTransactionsFeesFragmentToSupplierTransactionsFeesFragment())
                transactionFeesViewModel.navToSupplierTransactionsFeesComplete()
            }
        }
        return binding.root
    }


}