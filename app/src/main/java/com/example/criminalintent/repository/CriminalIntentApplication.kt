package com.example.criminalintent.repository

import android.app.Application
import com.example.criminalintent.repository.CrimeRepository

class CriminalIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.init(this)
    }
}