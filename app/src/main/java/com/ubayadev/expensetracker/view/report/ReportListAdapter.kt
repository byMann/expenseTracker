package com.ubayadev.expensetracker.view.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.expensetracker.databinding.ReportItemBinding
import com.ubayadev.expensetracker.model.Budget
import com.ubayadev.expensetracker.model.ReportData
import com.ubayadev.expensetracker.viewmodel.report.ListReportViewModel
import java.text.DecimalFormat
import java.text.NumberFormat

class ReportListAdapter(var reportList: ArrayList<ReportData>): RecyclerView.Adapter<ReportListAdapter.ReportViewHolder>() {

    class ReportViewHolder(var binding: ReportItemBinding): RecyclerView.ViewHolder(binding.root){}

    // update data dari Fragment
    fun updateData(newReportList: List<ReportData>) {
        reportList.clear()
        reportList.addAll(newReportList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ReportItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val currentReport = reportList[position]
        val formatter: NumberFormat = DecimalFormat("#,###")

        val totalBudget = currentReport.budget.nominal
        val totalSpent = currentReport.totalExpense
        val budgetLeft = totalBudget - totalSpent

        holder.binding.txtNameBudget.text = currentReport.budget.name
        holder.binding.txtNominalBudget.text = "IDR ${formatter.format(totalBudget)}"
        holder.binding.txtExpense.text = "Spent: IDR ${formatter.format(totalSpent)}"
        holder.binding.txtLeftBudget.text = "Left: IDR ${formatter.format(budgetLeft)}"

        // Setup progress bar
        holder.binding.progressExpense.max = totalBudget
        holder.binding.progressExpense.progress = totalSpent
    }
}