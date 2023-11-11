package com.theideal.notary.main.supplier.theSupplier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.FragmentCreateSupplierBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateSupplierFragment : Fragment() {
    private lateinit var binding: FragmentCreateSupplierBinding
    private val createSupplierViewModel by viewModel<CreateSupplierViewModel>()
    private val contact = Contact()
    // args and fragmnet


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreateSupplierBinding.inflate(inflater, container, false)
        binding.contact = contact
        binding.createSupplierViewModel = createSupplierViewModel
        binding.lifecycleOwner = this
        createSupplierViewModel.navToTheSupplier.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    CreateSupplierFragmentDirections
                        .actionCreateSupplierFragment2ToTheSupplierFragment(contact)
                )
            }
        }
        createSupplierViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                createSupplierViewModel.snackBarComplete()
            }
        }
        return binding.root
    }

}