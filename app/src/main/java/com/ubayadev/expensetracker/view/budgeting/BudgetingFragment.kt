package com.ubayadev.expensetracker.view.budgeting

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.expensetracker.databinding.FragmentBudgetingBinding
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.viewmodel.budgeting.ListBudgetViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetingFragment : Fragment() {
    private lateinit var viewModel: ListBudgetViewModel
    private lateinit var binding: FragmentBudgetingBinding
    private val budgetListAdapter = BudgetListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetingBinding.inflate(
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
        viewModel.fetch(getCurrentUsername(requireContext()))

        binding.budgetRecycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.budgetRecycleView.adapter = budgetListAdapter
        binding.txtError.visibility = View.GONE

    }

    fun observeViewModel() {
        viewModel.budgetsLD.observe(viewLifecycleOwner, Observer {
            budgetListAdapter.updateBudgetList(it)
            // Kalo kosong
            if (it.isEmpty()) {
                Log.d("BUDGET", "EMPTY BUDGET")
                binding.budgetRecycleView?.visibility = View.GONE
            } else {
                binding.budgetRecycleView?.visibility = View.VISIBLE
            }
        })

        viewModel.budgetLoadingLD.observe(viewLifecycleOwner, Observer {
            if (it) binding.progressBar?.visibility = View.VISIBLE
            else binding.progressBar?.visibility = View.GONE
        })

        viewModel.budgetErrorLD.observe(viewLifecycleOwner, Observer {
            if (it != "") {
                binding.txtError.visibility = View.VISIBLE
                binding.txtError.text = it
            } else {
                binding.txtError.visibility = View.GONE
            }
        })
    }
}