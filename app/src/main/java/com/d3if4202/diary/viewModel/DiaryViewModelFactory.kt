package com.d3if4202.diary.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.d3if4202.diary.database.DiaryDatabaseDao
import java.lang.IllegalArgumentException
//CRETED BY DODY RIFKI SURAYA

class DiaryViewModelFactory (
    private val dataSource: DiaryDatabaseDao,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("uncheck_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T{
        if(modelClass.isAssignableFrom(DiaryViewModel::class.java)){
            return DiaryViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow View Model Class")
    }
}