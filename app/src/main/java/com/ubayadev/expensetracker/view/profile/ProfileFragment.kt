package com.ubayadev.expensetracker.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ubayadev.expensetracker.databinding.FragmentProfileBinding
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.util.logout
import com.ubayadev.expensetracker.view.AuthActivity
import com.ubayadev.expensetracker.view.MainActivity
import com.ubayadev.expensetracker.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var currentUser: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.profile = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        currentUser = getCurrentUsername(requireContext())

        binding.btnChange.setOnClickListener {
            if (currentUser.isNotEmpty()) {
                viewModel.changePassword(currentUser)
            } else {
                Toast.makeText(context, "Username tidak ditemukan.", Toast.LENGTH_LONG).show()
            }
        }

        binding.txtSignOut.setOnClickListener {
            viewModel.signOut()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.toastMessageLD.observe(viewLifecycleOwner) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToSignInLD.observe(viewLifecycleOwner) { navigate ->
            if (navigate == true) {
//                clearUserSession()
                logout(requireContext())

                val intent = Intent(activity, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()

                viewModel.onSignOutNavigated()
            }
        }
    }

    private fun clearUserSession() {
        val sharedPrefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPrefs.edit().clear().apply()
    }
}
