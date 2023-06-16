package com.theideal.notary.main.supplier

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.theideal.notary.databinding.FragmentSupplierBinding
import com.theideal.notary.main.company.CompanyActivity
import com.theideal.notary.main.supplier.theSupplier.SupplierActivity
import com.theideal.notary.main.supplier.theSupplier.SupplierAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SupplierFragment : Fragment() {
    private lateinit var binding: FragmentSupplierBinding
    private val supplierViewModel by viewModel<SupplierViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supplierViewModel.getSuppliersList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSupplierBinding.inflate(inflater, container, false)
        binding.supplierViewModel = supplierViewModel
        binding.lifecycleOwner = this
        binding.rvSuppliers.adapter = SupplierAdapter(SupplierAdapter.OnClick {
           val intent = Intent(activity, SupplierActivity::class.java).apply {
                putExtra("fragment", "TheSupplier")
                putExtra("contactId", it.phone)
           }
            startActivity(intent)
        })

        supplierViewModel.startSupplierActivity.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(activity, SupplierActivity::class.java)
                startActivity(intent)
                supplierViewModel.startSupplierActivityComplete()
            }
        }
        supplierViewModel.startCompanyActivity.observe(viewLifecycleOwner) {
            if (it) {
                val intent = Intent(activity, CompanyActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

}