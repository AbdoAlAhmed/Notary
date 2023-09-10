package com.theideal.notary.main.client.daily

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.ItemWrapper
import com.theideal.notary.databinding.FragmentDailyBinding
import com.theideal.notary.main.client.theclient.ClientActivity
import com.theideal.notary.main.company.CompanyActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyFragment : Fragment() {
    private val dailyViewModel by viewModel<DailyViewModel>()
    private lateinit var binding: FragmentDailyBinding
    private lateinit var dailyAdapter: DailyAdapter
    private val itemWrapper = ItemWrapper()
    private val billContact = BillContact()
    private val contact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyViewModel.getAllClients()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.contact = contact
        binding.billClient = billContact
        binding.dailyViewModel = dailyViewModel

        dailyAdapter = DailyAdapter(
            dailyViewModel,
            DailyAdapter.SaleTransactionsListener { it ->

                val intent = Intent(requireContext(), ClientActivity::class.java).apply {
                    putExtra("fragment", "TheClientFragment")
                    putExtra("contactId", it.contactId)
                    putExtra("contactName", it.name)
                }
                startActivity(intent)
            })
        binding.rvDaily.adapter =
            dailyAdapter


        dailyViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                dailyViewModel.snackBarShown()
            }
        }
        dailyViewModel.startCompanyActivity.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), CompanyActivity::class.java)
            startActivity(intent)
//             start activity for result
        }
        dailyViewModel.createClient.observe(viewLifecycleOwner) {
            if (it) {
                startActivity(Intent(requireContext(), ClientActivity::class.java))
                dailyViewModel.createClientStarting()
            }
        }



        return binding.root
    }

    override fun onResume() {
        dailyViewModel.getAllClients()
        super.onResume()
    }
}