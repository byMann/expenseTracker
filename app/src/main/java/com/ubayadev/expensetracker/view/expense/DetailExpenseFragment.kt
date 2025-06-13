package com.ubayadev.expensetracker.view.expense

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentDetailExpenseBinding
import com.ubayadev.expensetracker.util.convertUnixToFormattedDate
import com.ubayadev.expensetracker.util.formatToRupiah
import com.ubayadev.expensetracker.view.budgeting.EditBudgetFragmentArgs
import com.ubayadev.expensetracker.viewmodel.expenses.ListExpenseViewModel

class DetailExpenseFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDetailExpenseBinding
    private lateinit var viewModel: ListExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailExpenseBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil expense_id
        var expense_id = DetailExpenseFragmentArgs
            .fromBundle(requireArguments()).expenseId

        viewModel = ViewModelProvider(this)
            .get(ListExpenseViewModel::class.java)

        viewModel.getExpenseDetail(expense_id)

        observeViewModel()

    }

    fun observeViewModel() {
        viewModel.expenseDetailLD.observe(viewLifecycleOwner, Observer {
            binding.txtDetailExpenseDate.text = convertUnixToFormattedDate(it.date.toLong())
            binding.txtDetailExpenseNotes.text = it.notes
            binding.txtDetailExpenseNominal.text = "IDR " + formatToRupiah(it.nominal)
            binding.txtDetailExpenseBudgetName.text = it.name
        })
    }
}