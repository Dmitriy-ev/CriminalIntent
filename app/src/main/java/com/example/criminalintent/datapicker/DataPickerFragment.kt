package com.example.criminalintent.datapicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_DATE = "date"

class DataPickerFragment : DialogFragment() {

    companion object {
        fun newInstance(date: Date): DataPickerFragment {
            val arg = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DataPickerFragment().apply {
                arguments = arg
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val dateListener = DatePickerDialog.OnDateSetListener {
                    _: DatePicker, year: Int, month: Int, day: Int ->
                val resultDate : Date = GregorianCalendar(year, month, day).time
                targetFragment?.let { fragment ->
                    (fragment  as Callbacks).onDataSet(resultDate)
                } }
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    interface Callbacks {
        fun onDataSet(date: Date)
    }
}