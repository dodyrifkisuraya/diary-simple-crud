package com.d3if4202.diary.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d3if4202.diary.database.DiaryDatabaseDao
//CRETED BY DODY RIFKI SURAYA

class InputDiaryViewModelFactory (
    private val diaryKey: Long,
    private val dataSource: DiaryDatabaseDao) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T{
        if (modelClass.isAssignableFrom(InputDiaryViewModel::class.java)){
            return InputDiaryViewModel(diaryKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}