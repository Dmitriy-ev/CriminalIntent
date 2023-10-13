package com.example.criminalintent

import androidx.lifecycle.ViewModel
import com.example.criminalintent.database.CriminalDatabase

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}