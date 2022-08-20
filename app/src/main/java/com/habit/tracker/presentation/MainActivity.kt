package com.habit.tracker.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.habit.tracker.R
import com.habit.tracker.databinding.ActivityMainBinding
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.presentation.view.OrganizationFragment

class MainActivity : AppCompatActivity(),
    OrganizationFragment.OnRequestListActionsListener {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    }
    private val navController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navView.setupWithNavController(navController)
        setupNavController()
    }

    private fun setupNavController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_map, R.id.navigation_profile -> binding.navView.visibility =
                    View.VISIBLE
                else -> binding.navView.visibility = View.GONE
            }
        }
    }

    override fun onRequestItemClick(request: Request) {
        navController.navigate(R.id.action_navigation_organization_details_to_navigation_request_details)
    }

    override fun onAddNewClick() {
        navController.navigate(R.id.action_navigation_organization_details_to_navigation_create_request)
    }
}