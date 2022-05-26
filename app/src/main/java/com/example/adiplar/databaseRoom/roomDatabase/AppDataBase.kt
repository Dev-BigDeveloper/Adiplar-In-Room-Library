package com.example.adiplar.databaseRoom.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.adiplar.databaseRoom.dao.ContactDao
import com.example.adiplar.databaseRoom.modelsRoom.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDataBase :RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context) : AppDataBase {
            if (instance == null){
                instance = Room.databaseBuilder(context, AppDataBase::class.java,"my_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}