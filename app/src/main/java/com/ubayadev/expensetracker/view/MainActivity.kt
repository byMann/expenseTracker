package com.ubayadev.expensetracker.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.ActivityMainBinding
import com.ubayadev.expensetracker.util.getCurrentUsername

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // CHECK APAKAH UDH SIGN IN ATAU BLM
        if (getCurrentUsername(this) == "") {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navController = (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment)
            .navController

        binding.bottomNav.setupWithNavController(navController)
    }
}