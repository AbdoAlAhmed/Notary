package com.theideal.notary.main.supplier.theSupplier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.theideal.data.model.BillContact
import com.theideal.notary.databinding.FragmentTheSupplierBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TheSupplierFragment : Fragment() {
    private val theSupplierViewModel by viewModel<TheSupplierViewModel>()
    private lateinit var binding: FragmentTheSupplierBinding
    private lateinit var args: TheSupplierFragmentArgs
    private val billContact = BillContact()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = TheSupplierFragmentArgs.fromBundle(requireArguments())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheSupplierBinding.inflate(inflater, container, false)
        binding.theSupplierViewModel = theSupplierViewModel
        binding.lifecycleOwner = this
        binding.rvBillsTheSupplier.adapter = BillsSupplierAdapter(BillsSupplierAdapter.OnClick {
            findNavController().navigate(TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(it))
        })
        val intent = requireActivity().intent.getStringExtra("contactId")
        if (intent != null) {
            theSupplierViewModel.getSupplier(intent)
            theSupplierViewModel.getBillBySupplierId(intent)
        } else {
            theSupplierViewModel.setSupplier(args.contact)
            theSupplierViewModel.getBillBySupplierId(args.contact.phone)
        }
        theSupplierViewModel.navToSupplierBill.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(TheSupplierFragmentDirections.actionTheSupplierFragmentToTheSupplierBillFragment(billContact))
                theSupplierViewModel.navToSupplierBillComplete()
            }
        }
        return binding.root
    }

}