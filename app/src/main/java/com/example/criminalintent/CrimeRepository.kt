package com.example.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.criminalintent.database.CrimeDao
import com.example.criminalintent.database.CriminalDatabase
import java.lang.IllegalStateException
import java.util.UUID
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database: CriminalDatabase = Room.databaseBuilder(
        context.applicationContext,
        CriminalDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao: CrimeDao = database.crimeDao()
    private val exec = Executors.newSingleThreadExecutor()

    companion object{
        private var INSTANCE: CrimeRepository? = null
        fun init(context: Context){
            if(INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get(): CrimeRepository{
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun addCrime(crime: Crime){
        exec.execute{
            crimeDao.addCrime(crime)
        }
    }
}