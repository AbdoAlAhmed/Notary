package com.theideal.notary.main.client.createclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.theideal.data.model.Contact
import com.theideal.notary.databinding.FragmentCreateClientBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateClientFragment : Fragment() {
    private lateinit var binding: FragmentCreateClientBinding
    private val createClientViewModel by viewModel<CreateClientViewModel>()
    private var contact = Contact()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateClientBinding.inflate(inflater, container, false)
        binding.createClientViewModel = createClientViewModel
        binding.contact = contact
        binding.lifecycleOwner = this
        createClientViewModel.contact.observe(viewLifecycleOwner) {
            contact.contactId = it.contactId
        }
        createClientViewModel.clientCreatedNavToTheClient.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    CreateClientFragmentDirections.actionCreateClientFragmentToTheClientFragment(
                        contact
                    )
                )
                createClientViewModel.doneNavigatingToTheClient()
            }
        }
        createClientViewModel.snackBarMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                createClientViewModel.doneShowingSnackBar()
            }
        }

        return binding.root
    }

}