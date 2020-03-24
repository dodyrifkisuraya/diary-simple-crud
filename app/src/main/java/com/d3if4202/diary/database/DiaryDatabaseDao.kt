package com.d3if4202.diary.database

import androidx.lifecycle.LiveData
import androidx.room.*
//CRETED BY DODY RIFKI SURAYA

@Dao
interface DiaryDatabaseDao {
    @Insert
    fun insert(diary: Diary)

    @Update
    fun update(diary: Diary)

    @Query("SELECT * FROM diary_table WHERE diaryId = :key")
    fun get(key : Long): Diary?

    @Query("DELETE FROM diary_table")
    fun clear()

    @Query("SELECT * FROM diary_table ORDER BY diaryId DESC LIMIT 1")
    fun getThisDiary(): Diary?

    @Query("SELECT * FROM diary_table ORDER BY diaryId DESC")
    fun getAllDiary(): LiveData<List<Diary>>
}