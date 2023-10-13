package com.example.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())

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

    private fun updateUi(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    companion object {
        fun newIntent(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private inner class CrimeViewHolder(view: View) : BaseViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        private lateinit var crime: Crime
        val titleTextView: TextView = view.findViewById(R.id.crime_title)
        val dateTextView: TextView = view.findViewById(R.id.crime_date)
        val solvedImageView: ImageView = view.findViewById(R.id.crime_solved)

        override fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.data.formatDate()
            solvedImageView.visibility = if(crime.isSolved){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${crime.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class SeriousCrimeViewHolder(view: View) : BaseViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        private lateinit var crime: Crime
        val titleTextView: TextView = view.findViewById(R.id.crime_title)
        val dateTextView: TextView = view.findViewById(R.id.crime_date)
        val callButton: Button = view.findViewById(R.id.crime_police)

        override fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.data.toString()
            callButton.setOnClickListener {
                Toast.makeText(context, "Звонок отправлен", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${crime.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(private var crimes: List<Crime>) : RecyclerView.Adapter<CrimeViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return CrimeViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false))

//            when (viewType) {
//                1 -> CrimeViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false))
//                else -> SeriousCrimeViewHolder(inflater.inflate(R.layout.list_item_crime_police, parent, false))
//            }
        }

        override fun getItemCount(): Int = crimes.size

        override fun onBindViewHolder(holder: CrimeViewHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }

//        override fun getItemViewType(position: Int): Int {
//            val crime = crimes[position]
//            return if (crime.requiresPolice) {
//                1
//            } else {
//                0
//            }
//        }
    }

    private fun Date.formatDate(): String{
        val locale = Locale("en", "EN")
        val dateFormat = SimpleDateFormat("EEEE yyyy-MM-dd", locale)
        return dateFormat.format(this)
    }
}