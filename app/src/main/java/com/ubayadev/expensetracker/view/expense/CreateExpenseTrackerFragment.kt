package com.ubayadev.expensetracker.view.expense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.ubayadev.expensetracker.R
import com.ubayadev.expensetracker.databinding.FragmentCreateExpenseTrackerBinding
import com.ubayadev.expensetracker.model.Expense
import com.ubayadev.expensetracker.util.convertToUnix
import com.ubayadev.expensetracker.util.getCurrentUsername
import com.ubayadev.expensetracker.viewmodel.budgeting.ListBudgetViewModel
import com.ubayadev.expensetracker.viewmodel.expenses.ListExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.exp

class CreateExpenseTrackerFragment : Fragment() {
    private lateinit var binding: FragmentCreateExpenseTrackerBinding
    private lateinit var viewModel: ListExpenseViewModel
    private lateinit var viewModelBudget:ListBudgetViewModel
    private var selectedBudgetId: Int? = null
    private var currentBudget: Int = 0;
    private var currentExpense: Int = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateExpenseTrackerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)
            .get(ListExpenseViewModel::class.java)

        viewModelBudget = ViewModelProvider(this)
            .get(ListBudgetViewModel::class.java)
        viewModelBudget.fetch(getCurrentUsername(requireContext()))


        binding.btnAddExpense.setOnClickListener {
            val dateString = binding.txtDateExpense.text.toString()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = try {
                sdf.parse(dateString)?.time?.div(1000) // Unix time dalam detik
            } catch (e: Exception) {
                null
            }

            val nominal = binding.txtNominalNewExpense.text.toString().toIntOrNull()
            val notes = binding.txtNoteNewExpense.text.toString()

            if (date != null && nominal != null && selectedBudgetId != null) {
                if (nominal < 0) {
                    Toast.makeText(view.context, "Nominal can't be negative", Toast.LENGTH_LONG).show()
                } else if (currentExpense + nominal > currentBudget) {
                    Toast.makeText(view.context, "Budget Limit Exceeded", Toast.LENGTH_LONG).show()
                } else {
                    viewModel.create(
                        convertToUnix(binding.txtDateExpense.text.toString()).toInt(),
                        nominal,
                        notes,
                        selectedBudgetId!!
                    )
                    Toast.makeText(view.context, "Data added", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(it).popBackStack()
                }
            } else {
                Toast.makeText(view.context, "Mohon isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelAddExpense.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }

        binding.txtDateExpense.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    // Setelah memilih tanggal, buka TimePicker
                    TimePickerDialog(
                        requireContext(),
                        { _, hourOfDay, minute ->
                            selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
                            selectedDate.set(Calendar.MINUTE, minute)

                            // Format: yyyy-MM-dd HH:mm
                            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            binding.txtDateExpense.setText(sdf.format(selectedDate.time))
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModelBudget.budgetsLD.observe(viewLifecycleOwner) { budgetList ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                budgetList.map { it.name } // Hanya tampilkan nama di Spinner
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerBudgetName.adapter = adapter

            // Simpan budgetId sesuai posisi Spinner
            binding.spinnerBudgetName.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: android.widget.AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedBudget = budgetList[position]
                    selectedBudgetId = selectedBudget.id

                    // Tampilkan max nominal budget
                    binding.txtMaxNominalExpense.text = "IDR ${selectedBudget.nominal}"

                    // Ambil total pengeluaran dari budget yang dipilih
                    viewModel.getCurrentExpense(selectedBudget.id)
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                    selectedBudgetId = null
                }
            })

            viewModel.currentExpenses.observe(viewLifecycleOwner) { totalSpent ->
                binding.txtCurrentExpenseBudget.text = "IDR $totalSpent"
                currentExpense = totalSpent

                val max = budgetList.find { it.id == selectedBudgetId }?.nominal ?: 0
                currentBudget = max
                binding.progressBarBudgetLeft.max = max
                binding.progressBarBudgetLeft.progress = totalSpent
            }
        }
    }


}