package com.example.criminalintent.crimelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.R
import com.example.criminalintent.model.Crime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

private const val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {

    interface Callbacks {
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter = CrimeAdapter()

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUi(crimes)
                }
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun updateUi(crimes: List<Crime>) {
        adapter.setItems(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newIntent(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        private lateinit var crime: Crime
        val titleTextView: TextView = view.findViewById(R.id.crime_title)
        val dateTextView: TextView = view.findViewById(R.id.crime_date)
        val solvedImageView: ImageView = view.findViewById(R.id.crime_solved)
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.formatDate()
            solvedImageView.visibility = if (crime.isSolved) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }

    private inner class CrimeAdapter : RecyclerView.Adapter<CrimeViewHolder>() {

        private var items: List<Crime> = ArrayList()

        fun setItems(newItems: List<Crime>) {
            val diffUtilCallback = CrimeDiffUtil(items, newItems,)
            items = ArrayList(newItems)
            DiffUtil.calculateDiff(diffUtilCallback, false).dispatchUpdatesTo(this)
        }

        fun updateCrimes(items: List<Crime>){
            this.items = items
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
            return CrimeViewHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
            val crime = items[position]
            holder.bind(crime)
        }
    }

    private fun Date.formatDate(): String {
        val locale = Locale("en", "EN")
        val dateFormat = SimpleDateFormat("EEEE yyyy-MM-dd", locale)
        return dateFormat.format(this)
    }
}