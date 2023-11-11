package com.theideal.notary.main.supplier.theSupplier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.theideal.notary.R
import com.theideal.notary.databinding.ActivitySupplierBinding

class SupplierActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySupplierBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostSupplierFragment) as NavHostFragment
        val intent = this.intent.getStringExtra("fragment")
        val navController = navHostFragment.navController
        val startDestination = if (intent == "TheSupplierFragment") {
            R.id.theSupplierFragment
        } else {
            R.id.createSupplierFragment2
        }
        val graph = navController.navInflater.inflate(R.navigation.the_supplier)
        graph.setStartDestination(startDestination)
        navController.graph = graph

        setContentView(binding.root)
    }
}