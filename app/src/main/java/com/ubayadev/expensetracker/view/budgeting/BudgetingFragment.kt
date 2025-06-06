package com.ubayadev.expensetracker.view.budgeting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ubayadev.expensetracker.R

/**
 * A simple [Fragment] subclass.
 * Use the [BudgetingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BudgetingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_budgeting, container, false)
    }
}