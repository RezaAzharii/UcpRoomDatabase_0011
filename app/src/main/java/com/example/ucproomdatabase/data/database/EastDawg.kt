package com.example.ucproomdatabase.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucproomdatabase.data.dao.DokterDao
import com.example.ucproomdatabase.data.dao.JadwalDao
import com.example.ucproomdatabase.data.entity.Dokter
import com.example.ucproomdatabase.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class EastDawg : RoomDatabase(){

    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): JadwalDao

    companion object{
        @Volatile
        private var Instance: EastDawg? = null

        fun getDatabase(context: Context): EastDawg{
            return (Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    EastDawg::class.java,
                    "EastDawg"
                )
                    .build().also { Instance = it }
            })
        }
    }
}