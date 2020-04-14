package com.d3if4202.diary.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.database.DiaryDatabaseDao
import kotlinx.coroutines.*

//CRETED BY DODY RIFKI SURAYA

class InputDiaryViewModel(
    private val diaryKey: Long,
    val database: DiaryDatabaseDao
) : ViewModel() {

    private val diary = MediatorLiveData<Diary>()

    fun getDiary() = diary

    init {
        diary.addSource(database.getDiaryId(diaryKey), diary::setValue)
        Log.e("diary", diaryKey.toString()+" -> "+ diary.value?.diaryId)
    }


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToHome = MutableLiveData<Boolean?>()
    val navigateToHome: LiveData<Boolean?> get() = _navigateToHome
    fun doneNavigating() {
        _navigateToHome.value = null
    }

    fun onWrite(isi: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val newDiary = Diary()
                newDiary.pesan = isi
                insert(newDiary)
            }
            _navigateToHome.value = true
        }
    }

    fun onUpdate(key: Long, isi: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val diary = database.get(key = key)
                diary?.pesan = isi
                diary?.let { update(it) }
            }
        }
    }

    fun getIsiDiary(key: Long): String? {
        var isi: String = "kosong"

        uiScope.launch {
            withContext(Dispatchers.IO) {
                val diary = database.get(key)
                isi = diary?.pesan.toString()
                return@withContext
            }
        }
        return isi
    }


    private suspend fun insert(diary: Diary) {
        withContext(Dispatchers.IO) {
            database.insert(diary)
        }
    }

    private suspend fun update(diary: Diary) {
        withContext(Dispatchers.IO) {
            database.update(diary)
        }
    }

}