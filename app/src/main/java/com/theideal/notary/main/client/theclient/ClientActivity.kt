package com.theideal.notary.main.client.theclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.theideal.data.model.Contact
import com.theideal.notary.R
import com.theideal.notary.databinding.ActivityClientBinding

class ClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientBinding
    private val contact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.client_fragment_container_view) as NavHostFragment

        val intent = this.intent.getStringExtra("fragment")


        val navController = navHostFragment.navController

        val startDestination = if (intent == "TheClientFragment") {
            R.id.theClientFragment
        } else {
            R.id.createClientFragment
        }

        val graph = navController.navInflater.inflate(R.navigation.the_client)
        graph.setStartDestination(startDestination)
        navController.graph = graph

        setContentView(binding.root)
    }
}