package com.ubayadev.expensetracker.view.report

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentBudgetingBinding
import com.ubayadev.expensetracker.databinding.FragmentReportBinding
import com.ubayadev.expensetracker.databinding.ReportItemBinding
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.view.budgeting.BudgetListAdapter
import com.ubayadev.expensetracker.view.budgeting.BudgetingFragmentDirections
import com.ubayadev.expensetracker.viewmodel.budgeting.ListBudgetViewModel
import com.ubayadev.expensetracker.viewmodel.report.ListReportViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {
    private lateinit var viewModel: ListReportViewModel
    private lateinit var binding: FragmentReportBinding
    private val reportListAdapter = ReportListAdapter(arrayListOf())

    var totalBudgets: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)
            .get(ListReportViewModel::class.java)
        viewModel.fetch(getCurrentUsername(requireContext()))

        binding.reportRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reportRecyclerView.adapter = reportListAdapter
        binding.txtError.visibility = View.GONE
        binding.progressLoadReport.visibility = View.VISIBLE

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.reportsLD.observe(viewLifecycleOwner) { reportList ->
            binding.reportRecyclerView.visibility = if (reportList.isEmpty()) View.GONE else View.VISIBLE
            reportListAdapter.updateData(reportList)
        }

        val formatter: NumberFormat = DecimalFormat("#,###")
        viewModel.totalBudgetLD.observe(viewLifecycleOwner) { totalBudget ->
            totalBudgets = totalBudget
        }

        viewModel.totalSpentLD.observe(viewLifecycleOwner) { totalSpent ->
            binding.txtRekapBudget.text = "IDR: ${formatter.format(totalSpent ?: 0)} /  ${formatter.format(totalBudgets ?: 0)}"
        }

        viewModel.reportLoadingLD.observe(viewLifecycleOwner) { isLoading ->
            binding.progressLoadReport.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                binding.txtError.visibility = View.GONE
                binding.reportRecyclerView.visibility = View.GONE
            }
        }

        viewModel.reportErrorLD.observe(viewLifecycleOwner) { errorMsg ->
            binding.txtError.visibility = if (errorMsg.isNullOrEmpty()) View.GONE else View.VISIBLE
            binding.txtError.text = errorMsg
        }
    }
}