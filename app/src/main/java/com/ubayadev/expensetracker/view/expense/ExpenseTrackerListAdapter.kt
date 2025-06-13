package com.ubayadev.expensetracker.view.expense

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ubayadev.expensetracker.databinding.ExpenseItemLayoutBinding
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.model.ExpenseWithBudgetName
import com.ubayadev.expensetracker.util.convertUnixToFormattedDate
import com.ubayadev.expensetracker.util.formatToRupiah
import com.ubayadev.expensetracker.view.ExpenseTrackerListener

class ExpenseTrackerListAdapter (val expList:ArrayList<ExpenseWithBudgetName>)
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
//        holder.binding.exp = expList[position]
//        holder.binding.listener = this

//        holder.binding.txtNominalExpense.setOnClickListener {
//            b->adapterOnClick(expList[position])
//        }

        holder.binding.txtNominalExpense.text = "IDR " + formatToRupiah(expList[position].nominal)
        holder.binding.txtBudgetCategory.text = expList[position].name
        holder.binding.txtDateExpense.text = convertUnixToFormattedDate(expList[position].date.toLong())

        holder.binding.txtNominalExpense.setOnClickListener {
            val action = ExpenseTrackerFragmentDirections.actionDetailExpense(expList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onDetailExpenseClick(v: View) {
        //Buka Modal Detail Expense
//        val action = TodoListFragmentDirections.actionEditTodoFragment(v.tag.toString().toInt())
//        Navigation.findNavController(v).navigate(action)
    }


    fun updateExpenseList(newExpenseList: List<ExpenseWithBudgetName>) {
        expList.clear()
        expList.addAll(newExpenseList)
        notifyDataSetChanged()
    }

}