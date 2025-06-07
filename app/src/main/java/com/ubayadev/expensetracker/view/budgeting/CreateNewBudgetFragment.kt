package com.ubayadev.expensetracker.view.budgeting

import android.content.Context
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentCreateNewBudgetBinding
import com.ubayadev.expensetracker.view.AddBudgetListener
import com.ubayadev.expensetracker.viewmodel.budgeting.ListBudgetViewModel

class CreateNewBudgetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCreateNewBudgetBinding
    private lateinit var viewModel: ListBudgetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateNewBudgetBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ListBudgetViewModel::class.java)

        binding.btnAddBudget.setOnClickListener {
            val budgetName = binding.txtNewBudgetName.text.toString()
            val budgetNominal = binding.txtNewBudgetNominal.text.toString()

            if (budgetName != "" && budgetNominal.isDigitsOnly()) {
                val budgetNominalInt = budgetNominal.toInt()
                if (budgetNominalInt > 0) viewModel.create(budgetName, budgetNominalInt)
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

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.newBudgetSuccessLD.observe(viewLifecycleOwner, Observer {
            if (it) {
                // Kirim notifikasi berhasil ke BudgetingFragment
                parentFragmentManager.setFragmentResult(
                    "new_budget",
                    Bundle().apply { putBoolean("success", true) }
                )
                dismiss()
            }
        })
    }
}