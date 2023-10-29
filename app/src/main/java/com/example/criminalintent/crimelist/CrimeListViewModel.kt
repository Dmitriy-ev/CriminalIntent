package com.example.criminalintent.crimelist

import androidx.lifecycle.ViewModel
import com.example.criminalintent.model.Crime
import com.example.criminalintent.repository.CrimeRepository

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

    fun crimeAdd(crime: Crime){
        crimeRepository.addCrime(crime)
    }
}