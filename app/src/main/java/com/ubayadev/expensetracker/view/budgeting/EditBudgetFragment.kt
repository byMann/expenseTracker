package com.ubayadev.expensetracker.view.budgeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentEditBudgetBinding
import com.ubayadev.expensetracker.model.Budget
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
//            Navigation.findNavController(it).popBackStack()
        }

        binding.btnCancelUpdateBudget.setOnClickListener {
//            dismiss()
            Navigation.findNavController(it).popBackStack()
        }

        viewModel = ViewModelProvider(this)
            .get(ListBudgetViewModel::class.java)

        viewModel.getBudgetDetail(budgetId)
        viewModel.getCurrentExpenses(budgetId)

        observeViewMode()
    }

    fun observeViewMode() {
        viewModel.budgetDetailLD.observe(viewLifecycleOwner, Observer {
            budget = it
            binding.txtBudgetNameLayout.isEnabled = true
            binding.txtBudgetNominalLayout.isEnabled = true

            binding.txtUpdateBudgetName.setText(it.name)
            binding.txtUpdateBudgetNominal.setText(it.nominal.toString())
        })

        viewModel.currentExpensesLD.observe(viewLifecycleOwner, Observer {
            currentExpenses = it
            val formatter: NumberFormat = DecimalFormat("#,###")
            val expenses: Double = it.toDouble()
            binding.txtUpdateCurrentExpenses.text = "IDR " + formatter.format(expenses)
        })
    }
}