package com.example.criminalintent.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.Locale

class DataPickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            this,
            initialYear,
            initialMonth,
            initialDay
        )
    }
    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(Calendar.YEAR, p1)
        calendar.set(Calendar.MONTH, p2)
        calendar.set(Calendar.DAY_OF_MONTH, p3)

        val selectDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)

        val selectedDateBundle = Bundle()
        selectedDateBundle.putString("SELECTED_DATE", selectDate)
        setFragmentResult("REQUEST_KEY", selectedDateBundle)
    }
}