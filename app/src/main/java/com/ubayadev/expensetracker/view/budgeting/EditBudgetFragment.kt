package com.ubayadev.expensetracker.view.budgeting

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentEditBudgetBinding
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.util.formatToRupiah
import com.ubayadev.expensetracker.viewmodel.budgeting.ListBudgetViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class EditBudgetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentEditBudgetBinding
    private lateinit var viewModel: ListBudgetViewModel
    private var budget: Budget? = null
    private var currentExpenses: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditBudgetBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil budget_id
        var budgetId = EditBudgetFragmentArgs
            .fromBundle(requireArguments()).budgetId

        binding.txtBudgetNameLayout.isEnabled = false
        binding.txtBudgetNominalLayout.isEnabled = false

        binding.btnUpdateBudget.setOnClickListener {
            val newBudgetName = binding.txtUpdateBudgetName.text.toString()
            val newBudgetNominal = binding.txtUpdateBudgetNominal.text.toString()

            if (newBudgetName != "" && newBudgetNominal.isDigitsOnly()) {
                val newBudgetNominalInt = newBudgetNominal.toInt()
                if (newBudgetNominalInt > 0) viewModel.update(budget!!, newBudgetName, newBudgetNominalInt, currentExpenses)
                else {
                    Toast
                        .makeText(requireContext(),"Please provide a valid name and nominal", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast
                    .makeText(requireContext(),"Please provide a valid name and nominal", Toast.LENGTH_LONG)
                    .show()
            }
        }

        binding.btnCancelUpdateBudget.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        viewModel = ViewModelProvider(this)
            .get(ListBudgetViewModel::class.java)

        viewModel.getBudgetDetail(budgetId)
        viewModel.getCurrentExpenses(budgetId)

        observeViewMode(view)
    }

    fun observeViewMode(view: View) {
        viewModel.budgetDetailLD.observe(viewLifecycleOwner, Observer {
            budget = it
            binding.txtBudgetNameLayout.isEnabled = true
            binding.txtBudgetNominalLayout.isEnabled = true

            binding.txtUpdateBudgetName.setText(it.name)
            binding.txtUpdateBudgetNominal.setText(it.nominal.toString())
        })

        viewModel.currentExpensesLD.observe(viewLifecycleOwner, Observer {
            currentExpenses = it
//            val formatter: NumberFormat = DecimalFormat("#,###")
//            val expenses: Double = it.toDouble()
            binding.txtUpdateCurrentExpenses.text = "IDR " + formatToRupiah(currentExpenses)
        })

        viewModel.editBudgetSuccessLD.observe(viewLifecycleOwner, Observer {
            val messages: Map<String, Any>  = it
            Log.d("UPDATE MESSAGES", it.toString())
            if (it.get("success").toString().toBoolean()) {
                // Kirim notifikasi berhasil ke BudgetingFragment
                parentFragmentManager.setFragmentResult(
                    "edit_budget",
                    Bundle().apply {
                        putBoolean("success", true)
                        putString("previous_name", messages.get("previous_name").toString())
                        putString("current_name", messages.get("current_name").toString())
                        putInt("previous_nominal", messages.get("previous_nominal").toString().toInt())
                        putInt("current_nominal", messages.get("current_nominal").toString().toInt())
                    }
                )
                Navigation.findNavController(view).popBackStack()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage(messages.get("message").toString())
                    .setPositiveButton("OK", null)
                    .show()
            }
        })
    }
}