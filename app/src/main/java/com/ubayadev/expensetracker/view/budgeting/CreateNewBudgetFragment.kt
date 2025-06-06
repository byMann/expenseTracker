package com.ubayadev.expensetracker.view.budgeting

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            val budgetName = binding.txtBudgetName.text.toString()
            val budgetNominal = binding.txtBudgetNominal.text.toString().toInt()
            viewModel.create(budgetName, budgetNominal)
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