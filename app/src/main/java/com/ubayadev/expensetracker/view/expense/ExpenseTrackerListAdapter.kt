package com.ubayadev.expensetracker.view.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.expensetracker.databinding.ExpenseItemLayoutBinding
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.view.ExpenseTrackerListener

class ExpenseTrackerListAdapter (val expList:ArrayList<Expense>)
    :RecyclerView.Adapter<ExpenseTrackerListAdapter.ExpenseTrackerViewHolder>(), ExpenseTrackerListener{
    class ExpenseTrackerViewHolder(var binding: ExpenseItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseTrackerViewHolder {
        var binding = ExpenseItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return ExpenseTrackerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expList.size
    }

    override fun onBindViewHolder(holder: ExpenseTrackerViewHolder, position: Int) {
        holder.binding.exp = expList[position]
        holder.binding.listener = this
    }

    override fun onDetailExpenseClick(v: View) {
        //Buka Modal Detail Expense
//        val action = TodoListFragmentDirections.actionEditTodoFragment(v.tag.toString().toInt())
//        Navigation.findNavController(v).navigate(action)
    }

}