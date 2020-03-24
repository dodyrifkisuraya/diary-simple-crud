package com.d3if4202.diary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//CRETED BY DODY RIFKI SURAYA

@Database(entities =[Diary::class], version = 1, exportSchema = false)
abstract class DiaryDatabase: RoomDatabase(){
    abstract val diaryDatabaseDao: DiaryDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        fun getInstance(context: Context): DiaryDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryDatabase::class.java,
                        "diary_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}