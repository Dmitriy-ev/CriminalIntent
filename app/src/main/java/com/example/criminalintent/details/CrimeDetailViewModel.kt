package com.example.criminalintent.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.criminalintent.model.Crime
import com.example.criminalintent.repository.CrimeRepository
import java.io.File
import java.util.UUID

class CrimeDetailViewModel() : ViewModel() {
    private val crimeRepository: CrimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    val crimeLiveData: LiveData<Crime?> = crimeIdLiveData.switchMap { crimeId ->
        crimeRepository.getCrime(crimeId)
    }

    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }

    fun saveCrime(crime: Crime){
        crimeRepository.updateCrime(crime)
    }

    fun getPhoto(crime: Crime): File{
        return crimeRepository.getPhotoFile(crime)
    }
}