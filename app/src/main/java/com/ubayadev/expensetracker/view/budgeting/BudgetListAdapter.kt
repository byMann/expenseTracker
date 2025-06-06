package com.ubayadev.expensetracker.view.budgeting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.expensetracker.databinding.BudgetItemLayoutBinding
import com.ubayadev.expensetracker.model.Budget
import java.text.DecimalFormat
import java.text.NumberFormat

class BudgetListAdapter(var budgetList: ArrayList<Budget>): RecyclerView.Adapter<BudgetListAdapter.BudgetViewHolder>() {

    class BudgetViewHolder(var binding: BudgetItemLayoutBinding): RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        var binding = BudgetItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BudgetViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return budgetList.size
    }

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.binding.txtName.text = budgetList[position].name
        val formatter: NumberFormat = DecimalFormat("#,###")
        val nominal: Double = budgetList[position].nominal.toDouble()
        holder.binding.txtNominal.text = "IDR " + formatter.format(nominal)
    }

    fun updateBudgetList(newBudgetList: List<Budget>) {
        budgetList.clear()
        budgetList.addAll(newBudgetList)
        notifyDataSetChanged()
    }
}