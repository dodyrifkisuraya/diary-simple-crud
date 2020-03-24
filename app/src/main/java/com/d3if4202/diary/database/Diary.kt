package com.d3if4202.diary.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//CRETED BY DODY RIFKI SURAYA


@Entity(tableName = "diary_table")
data class Diary (
    @PrimaryKey(autoGenerate = true)
    var diaryId: Long = 0L,

    @ColumnInfo(name = "tanggal")
    var tanggal: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "isi")
    var pesan: String = "aduh"
)