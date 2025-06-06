package com.ubayadev.expensetracker.view

import android.app.AlertDialog
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentSignInBinding
import com.ubayadev.expensetracker.util.login
import com.ubayadev.expensetracker.viewmodel.UserViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [SignInFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignInFragment() : Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(UserViewModel::class.java)

//        viewModel.init()

        binding.btnSignIn.setOnClickListener {
            var username = binding.txtUsername.text.toString()
            var password = binding.txtPassword.text.toString()

            viewModel.signIn(username, password)
        }

        binding.btnSignUp.setOnClickListener {
            val action = SignInFragmentDirections.actionSignUp()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.successLD.observe(viewLifecycleOwner, Observer {
            // Kalo success
            if (it) {
                // Simpan di Shared Preference
                login(requireContext(), binding.txtUsername.text.toString())

                // Pindah ke Main Activity
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Signed in successfully.")
                    .setPositiveButton("OK") { _, _ ->
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
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