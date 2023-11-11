package com.theideal.notary.main.client.daily

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.ItemWrapper
import com.theideal.notary.R
import com.theideal.notary.databinding.DeleteDialogWithCondiationBinding
import com.theideal.notary.databinding.DialogEditBinding
import com.theideal.notary.databinding.FragmentDailyBinding
import com.theideal.notary.main.client.DailyAndClientsViewModel
import com.theideal.notary.main.client.theclient.ClientActivity
import com.theideal.notary.main.company.CompanyActivity
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyFragment : Fragment() {
    private val dailyViewModel by viewModel<DailyViewModel>()
    private lateinit var binding: FragmentDailyBinding
    private lateinit var dailyAdapter: DailyAdapter
    private val dailyAndClientsViewModel by viewModel<DailyAndClientsViewModel>()
    private val itemWrapper = ItemWrapper()
    private val billContact = BillContact()
    private val contact = Contact()
    private var emptyContact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dailyViewModel.getAllClientsToday()

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

        dailyAdapter = DailyAdapter(dailyAndClientsViewModel, DailyAdapter.SaleTransactionsListener { it ->

            val intent = Intent(requireContext(), ClientActivity::class.java).apply {
                putExtra("fragment", "TheClientFragment")
                putExtra("contactId", it.contactId)
                putExtra("contactName", it.name)
            }
            startActivity(intent)
        })
        binding.rvDaily.adapter = dailyAdapter

        val swipeCallBack = SwipeCallBack(dailyAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvDaily)


        dailyViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                dailyViewModel.snackBarComplete()
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
        dailyViewModel.moveToTheClientFromSearch.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                moveToTheClientFromSearch(contact)
            }
        }

        dailyAndClientsViewModel.editContact.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                editContactDialog(it)
                dailyAndClientsViewModel.editDialogClose()
            }
        }

        dailyAndClientsViewModel.deleteDialogClient.observe(viewLifecycleOwner) {
            if (it.contactId != "") {
                deleteContactDialog(it)
                dailyAndClientsViewModel.deleteDialogClose()
            }
        }

        return binding.root
    }

    private fun moveToTheClientFromSearch(contact: Contact) {
        val intent = Intent(requireContext(), ClientActivity::class.java).apply {
            putExtra("fragment", "TheClientFragment")
            putExtra("contactId", contact.contactId)
            putExtra("contactName", contact.name)
        }
        startActivity(intent)
        dailyViewModel.moveToTheClientFromSearchDailyCompleted()
    }

    private fun editContactDialog(contact: Contact) {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialog = dialogBuilder.create()
        val view = DialogEditBinding.inflate(layoutInflater)
        view.contact = contact
        dialog.setView(view.root)
        view.btnEditClient.setOnClickListener {
            val name = mapOf("name" to contact.name)
            val phone = mapOf("phone" to contact.phone)
            dailyAndClientsViewModel.updateContactName(contact, name)
            dailyAndClientsViewModel.updateContactPhone(contact, phone)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun deleteContactDialog(contact2: Contact) {
        val dialogAlert = AlertDialog.Builder(context)
        val dialogCreate = dialogAlert.create()
        val view = DeleteDialogWithCondiationBinding.inflate(layoutInflater)
        view.contact = emptyContact
        view.lifecycleOwner = this
        dialogCreate.setView(view.root)
        view.btnSureToDeleteContact.setOnClickListener {
            if (emptyContact.name == contact2.name) {
                dailyAndClientsViewModel.deleteClient(contact2)
                dailyViewModel.snackBar(getString(R.string.client_deleted))
                dailyViewModel.getAllClientsToday()
                dialogCreate.dismiss()
            }else{
                dialogCreate.dismiss()
                dailyViewModel.snackBar(getString(R.string.contact_didnt_deleted))
            }
        }
        dialogCreate.show()
    }

    override fun onResume() {
        dailyViewModel.getAllClientsToday()
        super.onResume()
    }
}