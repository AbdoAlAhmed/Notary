package com.theideal.notary.main.client.saletransactions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.FragmentSaleTransactionsBinding
import com.theideal.notary.main.client.createclient.ClientActivity
import com.theideal.notary.main.company.CompanyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SaleTransactionsFragment : Fragment() {
    private val saleTransactionsViewModel by viewModel<SaleTransactionsViewModel>()
    private lateinit var binding: FragmentSaleTransactionsBinding
    private val billContact = BillContact()
    private val contact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        saleTransactionsViewModel.getAllClients()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSaleTransactionsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.billClient = billContact
        binding.contact = contact
        binding.saleTransactionsViewModel = saleTransactionsViewModel
        binding.rvSaleTransactions.adapter =
            SaleTransactionsAdapter(SaleTransactionsAdapter.SaleTransactionsListener { contact ->
                val intent = Intent(requireContext(), ClientActivity::class.java).apply {
                    putExtra("fragment", "TheClientFragment")
                    putExtra("contactId", contact.phone)
                    putExtra("contactName", contact.name)
                }
                startActivity(intent)
            })

        saleTransactionsViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                saleTransactionsViewModel.snackBarShown()
            }
        }
        saleTransactionsViewModel.startCompanyActivity.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), CompanyActivity::class.java)
            startActivity(intent)
//             start activity for result
        }
        saleTransactionsViewModel.createClient.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), ClientActivity::class.java))
                saleTransactionsViewModel.createClientStarting()
            }
        }

        return binding.root
    }

}