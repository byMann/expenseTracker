package com.ubayadev.expensetracker.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentSignInBinding
import com.ubayadev.expensetracker.databinding.FragmentSignUpBinding
import com.ubayadev.expensetracker.viewmodel.UserViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(UserViewModel::class.java)

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.btnCreateAccount.setOnClickListener {
            var username = binding.txtUsername.text.toString()
            var firstName = binding.txtFirstName.text.toString()
            var lastName =  binding.txtLastName.text.toString()
            var password = binding.txtPassword.text.toString()
            var repeatPassword = binding.txtRepeatPassword.text.toString()

            if (password != repeatPassword) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("The repeat password doesn't match with the password")
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            } else {
                viewModel.signUp(username, firstName, lastName, password)
            }
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.successLD.observe(viewLifecycleOwner, Observer {
            // Kalo success
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Account created successfully")
                    .setPositiveButton("OK") { _, _ ->
                        Navigation.findNavController(requireView()).popBackStack()
                    }
                    .show()
            }
        })

        viewModel.errorMessageLD.observe(viewLifecycleOwner, Observer {
            // Kalo ada error
            if (!it.equals("")) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(it)
                    .setPositiveButton("OK") { _, _ -> }
                    .show()
            }
        })
    }
}