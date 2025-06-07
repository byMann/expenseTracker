package com.ubayadev.expensetracker.view.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentExpenseTrackerBinding
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.viewmodel.expenses.ListExpenseViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ExpenseTrackerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        viewModel =ViewModelProvider(this).get(ListExpenseViewModel::class.java)
        viewModel.refresh(getCurrentUsername(requireContext()))
        binding.recViewExpenseTracker.layoutManager = LinearLayoutManager(context)
        binding.recViewExpenseTracker.adapter = expListAdapter

        binding.addExpenseFab.setOnClickListener {
            val action = ExpenseTrackerFragmentDirections.actionCreateExpenseTrackerFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}