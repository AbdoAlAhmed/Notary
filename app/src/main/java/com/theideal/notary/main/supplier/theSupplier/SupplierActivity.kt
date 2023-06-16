package com.theideal.notary.main.supplier.theSupplier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.theideal.notary.R
import com.theideal.notary.databinding.ActivitySupplierBinding

class SupplierActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}