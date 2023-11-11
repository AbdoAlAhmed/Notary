package com.theideal.notary.main.client.allclient

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.Contact
import com.theideal.notary.R
import com.theideal.notary.databinding.DeleteDialogWithCondiationBinding
import com.theideal.notary.databinding.DialogEditBinding
import com.theideal.notary.databinding.FragmentClientsBinding
import com.theideal.notary.main.client.DailyAndClientsViewModel
import com.theideal.notary.main.client.theclient.ClientActivity
import com.theideal.notary.utils.SwipeCallBack
import org.koin.androidx.viewmodel.ext.android.viewModel


class ClientsFragment : Fragment() {

    private val clientsFragmentViewModel by viewModel<ClientsFragmentViewModel>()
    private val dailyAndClientsViewModel by viewModel<DailyAndClientsViewModel>()
    private lateinit var binding: FragmentClientsBinding
    private lateinit var clientsAdapter: ClientsAdapter
    private val contact = Contact()
    private val emptyContact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientsFragmentViewModel.getAllClients()
        clientsFragmentViewModel.getTotalDepts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClientsBinding.inflate(layoutInflater, container, false)
        binding.contact = contact

        binding.clientsViewModel = clientsFragmentViewModel
        binding.lifecycleOwner = this
        clientsAdapter = ClientsAdapter(dailyAndClientsViewModel, ClientsAdapter.ClickListener {
            val intent = Intent(requireContext(), ClientActivity::class.java).apply {
                putExtra("fragment", "TheClientFragment")
                putExtra("contactId", it.contactId)
                putExtra("contactName", it.name)
            }
            startActivity(intent)
        })
        binding.rvClients.adapter = clientsAdapter

        val swipeCallBack = SwipeCallBack(clientsAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvClients)

        clientsFragmentViewModel.moveToTheClientFromSearch.observe(viewLifecycleOwner) {
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

        clientsFragmentViewModel.snackBar.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                clientsFragmentViewModel.snackBarComplete()
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
        clientsFragmentViewModel.doneMoveToTheClientFromSearch()
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
                clientsFragmentViewModel.snackBar(getString(R.string.client_deleted))
                clientsFragmentViewModel.getAllClients()
                dialogCreate.dismiss()
            } else {
                clientsFragmentViewModel.snackBar(getString(R.string.contact_didnt_deleted))
                dialogCreate.dismiss()
            }
        }
        dialogCreate.show()
    }
}