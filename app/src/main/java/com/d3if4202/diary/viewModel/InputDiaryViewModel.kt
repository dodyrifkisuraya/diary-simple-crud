package com.d3if4202.diary.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.database.DiaryDatabaseDao
import kotlinx.coroutines.*
//CRETED BY DODY RIFKI SURAYA

class InputDiaryViewModel (
    private val diaryKey: Long,
            val database: DiaryDatabaseDao): ViewModel(){


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?> get() = _navigateToHome
    fun doneNavigating(){
        _navigateToHome.value = null
    }

    fun onSetIsi(isi: String){
        Log.e("SET ISI", isi)
        uiScope.launch {
            withContext(Dispatchers.IO){
                val newDiary = Diary()
                newDiary.pesan = isi
                insert(newDiary)
//                val diary = database.get(diaryKey) ?: return@withContext
//                diary.pesan = isi
//                database.update(diary)

            }
            _navigateToHome.value = true
        }
    }


    private suspend fun insert(diary: Diary){
        withContext(Dispatchers.IO){
            database.insert(diary)
        }
    }

}