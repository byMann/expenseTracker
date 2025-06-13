package com.ubayadev.expensetracker.view.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentExpenseTrackerBinding
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.viewmodel.expenses.ListExpenseViewModel


class ExpenseTrackerFragment : Fragment() {
    private lateinit var binding: FragmentExpenseTrackerBinding
    private lateinit var viewModel: ListExpenseViewModel
    private val expListAdapter = ExpenseTrackerListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpenseTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ListExpenseViewModel::class.java)

        viewModel.refresh(getCurrentUsername(requireContext()))

        binding.recViewExpenseTracker.layoutManager = LinearLayoutManager(context)
        binding.recViewExpenseTracker.adapter = expListAdapter

        binding.txtErrorExpenseTracker.visibility = View.GONE
        binding.progressBarExpenseTracker.visibility = View.VISIBLE

        binding.addExpenseFab.setOnClickListener {
            val action = ExpenseTrackerFragmentDirections.actionCreateExpenseTrackerFragment()
            Navigation.findNavController(it).navigate(action)
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.expenseLD.observe(viewLifecycleOwner, Observer {
            expListAdapter.updateExpenseList(it)

            if(it.isEmpty()) {
                binding.recViewExpenseTracker.visibility = View.GONE
                binding.txtErrorExpenseTracker.setText("Your Expense still empty.")
            } else {
                binding.recViewExpenseTracker.visibility = View.VISIBLE
            }
        })

        viewModel.expenseLoadingLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.progressBarExpenseTracker.visibility = View.GONE
            } else {
                binding.progressBarExpenseTracker.visibility = View.VISIBLE
            }
        })

        viewModel.expenseLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == false) {
                binding.txtErrorExpenseTracker.visibility = View.GONE
            } else {
                binding.txtErrorExpenseTracker.visibility = View.VISIBLE
            }
        })

    }
}