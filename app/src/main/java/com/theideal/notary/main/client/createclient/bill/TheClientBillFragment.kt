package com.theideal.notary.main.client.createclient.bill

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.theideal.data.model.BillContact
import com.theideal.data.model.Contact
import com.theideal.data.model.Item
import com.theideal.notary.R
import com.theideal.notary.databinding.DialogOtherFeesBinding
import com.theideal.notary.databinding.DialogPayTheBillBinding
import com.theideal.notary.databinding.DialogSellItemClientBinding
import com.theideal.notary.databinding.FragmentClientBillBinding
import com.theideal.notary.utils.SwipeCallBack
import com.theideal.notary.utils.pdf
import org.koin.androidx.viewmodel.ext.android.viewModel


class TheClientBillFragment : Fragment() {
    private lateinit var binding: FragmentClientBillBinding
    private val clientBillViewModel by viewModel<ClientBillViewModel>()
    private val contact = Contact()
    private var billContact = BillContact()
    private val item = Item()
    private var itemId = ""
    private lateinit var adapter: TheClientBillAdapter
    private lateinit var args: TheClientBillFragmentArgs
    private val requestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        args = TheClientBillFragmentArgs.fromBundle(requireArguments())
        clientBillViewModel.getSuppliersList()
        clientBillViewModel.getContact(args.billContact.contactId)
        clientBillViewModel.getItemsByBillId(args.billContact.billId)
        clientBillViewModel.setTotalToBillContact(billContact)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentClientBillBinding.inflate(inflater)
        binding.contact = contact
        binding.clientBillViewModel = clientBillViewModel
        clientBillViewModel.updateBillContact(args.billContact)
        clientBillViewModel.billContact.observe(viewLifecycleOwner) {
            billContact = it
            binding.bill = billContact
        }
        clientBillViewModel.setBillStatus(args.billContact.status)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarClientBill)
        adapter = TheClientBillAdapter(
            clientBillViewModel,
            TheClientBillAdapter.OnClick {

            })
        binding.rvClientBill.adapter = adapter
        val itemTouchCallBack = SwipeCallBack(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallBack)
        itemTouchHelper.attachToRecyclerView(binding.rvClientBill)


        // check permissions
        clientBillViewModel.checkPermission(
            requireContext().applicationContext,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
        // request permissions
        clientBillViewModel.requestPermission(requireActivity(), permissions, requestCode)


        clientBillViewModel.dialogTransfer.observe(viewLifecycleOwner) {
            if (it) {
                if (adapter.isListEmpty()) {
                    noItemDialog()
                } else {
                    otherFeesDialog()
                }
            }
        }
        clientBillViewModel.dialogTransaction.observe(viewLifecycleOwner) {
            if (it) {
                addItemDialog()
            }
        }
        clientBillViewModel.confirmDeleteDialog.observe(viewLifecycleOwner) {
            if (it != "") {
                itemId = it
                confirmDeleteDialog()
            }
        }
        clientBillViewModel.dialogUpdate.observe(viewLifecycleOwner) {
            if (it.itemId != "") {
                updateItemDialog(it)
            }
        }
        clientBillViewModel.confirmDeleteBillDialog.observe(viewLifecycleOwner) {
            if (it) {
                confirmDeleteBillDialog()
            }
        }
        clientBillViewModel.setBillStatus.observe(viewLifecycleOwner) {
            customViewForCloseBill(it)
        }


        binding.lifecycleOwner = this



        return binding.root
    }

    private fun customViewForCloseBill(status: String) {
        when (status) {
            "closed" -> {
                binding.apply {
                    btnPay.visibility = View.GONE
                    btnSell.setBackgroundColor(resources.getColor(R.color.sunset_orange))
                    btnSell.text = getString(R.string.edit_bill)
                    btnSell.setOnClickListener {
                        updateBill()
                    }
                }
            }
            "deferred" -> {
                binding.apply {
                    btnSell.setBackgroundColor(resources.getColor(R.color.sunset_orange))
                    btnSell.text = getString(R.string.edit_bill)
                    btnSell.setOnClickListener {
                        updateBill()
                    }
                }
            }
            else -> {
                binding.apply {
                    btnPay.visibility = View.VISIBLE
                    btnSell.setBackgroundColor(resources.getColor(R.color.ride_red))
                    btnSell.text = getString(R.string.sell)
                    btnSell.setOnClickListener {
                        clientBillViewModel!!.sellDialogOpen()
                    }
                }
            }
        }
    }

    private fun noItemDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle("No item")
        dialogCreate.setMessage("You must add at least one item")
        dialogCreate.show()
    }

    private fun otherFeesDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogOtherFeesBinding.inflate(layoutInflater)
        view.billContact = billContact
        dialogCreate.setView(view.root)
        view.btnAddOtherFees.setOnClickListener {
            val otherFee = mapOf(
                "theBillOtherFees" to billContact.theBillOtherFees!!
            )
            clientBillViewModel.updateBill(billContact.billId, keyValue = otherFee)
            clientBillViewModel.updateBillContact(billContact)
            dialogCreate.dismiss()
            transferDialog()
            clientBillViewModel.transferDialogComplete()
        }
        dialogCreate.show()
    }

    private fun transferDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogPayTheBillBinding.inflate(layoutInflater)
        view.billContact = billContact
        billContact.status = billContact.setStatus()

        dialogCreate.setView(view.root)
        view.btnPayTheBill.setOnClickListener {
            val billInfo = mapOf(
                "paidMoney" to billContact.paidMoney!!,
                "discount" to billContact.discount!!,
                "deptCalculate" to billContact.deptCalculate,
                "allFees" to billContact.allFees!!,
                "status" to billContact.setStatus(),
                "totalMoney" to billContact.totalMoney!!
            )
            clientBillViewModel.updateBill(billContact.billId, keyValue = billInfo)
            clientBillViewModel.setBillStatus(billContact.setStatus())
            clientBillViewModel.updateBillContact(billContact)
            dialogCreate.dismiss()
        }
        dialogCreate.show()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addItemDialog() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogSellItemClientBinding.inflate(layoutInflater)
        view.clientBillViewModel = clientBillViewModel
        view.contact = contact
        view.item = item
        view.btnSellItem.setOnClickListener {
            item.supplierId = contact.name
            clientBillViewModel.addItemToBillClient(billContact.billId, item)
            dialogCreate.dismiss()
            clientBillViewModel.transactionDialogComplete()
            clientBillViewModel.setTotalToBillContact(billContact)
            adapter.addItem(item)
        }
        dialogCreate.setView(view.root)
        dialogCreate.show()
    }

    private fun confirmDeleteDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val dialogCreate = alertDialog.create()
        dialogCreate.setMessage(resources.getString(R.string.confirm_delete_item))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE,
            resources.getString(R.string.sure)
        ) { dialog, _ ->
            clientBillViewModel.deleteItemFromBill(billContact.billId, itemId)
            dialog.dismiss()
            clientBillViewModel.confirmDeleteDialogComplete()
            adapter.removeItem(itemId)
            clientBillViewModel.setTotalToBillContact(billContact)
        }
        dialogCreate.show()
    }

    private fun updateItemDialog(item: Item) {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        val view = DialogSellItemClientBinding.inflate(layoutInflater)
        view.btnSellItem.apply {
            text = resources.getString(R.string.update)
            setBackgroundColor(resources.getColor(R.color.dark_ocean_blue))
        }
        view.tvSupplierName.apply {
            visibility = View.VISIBLE
        }
        view.clientBillViewModel = clientBillViewModel
        view.contact = contact
        view.item = item
        view.btnSellItem.setOnClickListener {
            item.supplierId = contact.name
            clientBillViewModel.updateItem(billContact.billId, item)
            clientBillViewModel.setTotalToBillContact(billContact)
            adapter.updateItem(item)
            dialogCreate.dismiss()
            clientBillViewModel.updateDialogComplete()
            adapter.refreshList()
        }

        dialogCreate.setView(view.root)
        dialogCreate.show()
    }

    private fun updateBill() {
        val dialogAlert = AlertDialog.Builder(requireContext())
        val dialogCreate = dialogAlert.create()
        dialogCreate.setTitle(resources.getString(R.string.reopen_the_bill))
        dialogCreate.setMessage(resources.getString(R.string.reopen_the_bill_message))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE,
            resources.getString(R.string.sure)
        ) { dialog, _ ->
            clientBillViewModel.updateBill(billContact.billId, keyValue = mapOf("status" to "open"))
            clientBillViewModel.setBillStatus("open")
        }
        dialogCreate.show()

    }

    private fun confirmDeleteBillDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        val dialogCreate = alertDialog.create()
        dialogCreate.setMessage(resources.getString(R.string.confirm_delete_bill))
        dialogCreate.setButton(
            AlertDialog.BUTTON_POSITIVE,
            resources.getString(R.string.sure)
        ) { dialog, _ ->
            clientBillViewModel.deleteBill(billContact.billId)
            dialog.dismiss()
            clientBillViewModel.confirmDeleteBillDialogComplete()
            findNavController().popBackStack()
        }
        dialogCreate.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_the_contact, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                clientBillViewModel.deleteBillDialogOpen()
            }

            R.id.action_share -> {
                if (billContact.status != "open") {
                    pdf(requireContext(), binding.root, billContact)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.print_error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.action_print -> {
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != null && requestCode == requestCode) {
            clientBillViewModel.permissionGranted()
        } else {
            clientBillViewModel.permissionDenied()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun customView(): View {
        // How to make a list of views
        val customView = LayoutInflater.from(requireContext())
            .inflate(R.layout.pdf_print, null, false)
        customView.findViewById<LinearLayout>(R.id.pdf_print_layout).addView(binding.rvClientBill)
        return customView
    }
}