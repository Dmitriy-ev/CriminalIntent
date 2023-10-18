package com.example.criminalintent.crimelist

import androidx.lifecycle.ViewModel
import com.example.criminalintent.repository.CrimeRepository

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}