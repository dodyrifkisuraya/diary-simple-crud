package com.d3if4202.diary.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.database.DiaryDatabaseDao
import kotlinx.coroutines.*

//CRETED BY DODY RIFKI SURAYA

class DiaryViewModel(
    val database: DiaryDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var diary = MutableLiveData<Diary?>()


    init {
        initializeDiary()
    }

    private fun initializeDiary() {
        uiScope.launch {
            diary.value = getDiaryFromDatabase()
        }
    }

    private suspend fun getDiaryFromDatabase(): Diary? {
        return withContext(Dispatchers.IO) {
            var mydiary = database.getThisDiary()
            mydiary
        }
    }

    fun getIsiDiary(key: Long): String? {
        var isi: String = "kosong"

        uiScope.launch {
            withContext(Dispatchers.IO) {
                val diary = database.get(key)
                isi = database.getIsi(key)
                Log.e("Get ISi", diary!!.pesan.toString())
                return@withContext
            }
        }
        Log.e("ISI-nya---------------", isi)
        return isi
    }

    fun getDiary(key: Long): Diary?{
        var diary : Diary? = null
        uiScope.launch {
            withContext(Dispatchers.IO){
                 diary = database.get(key)
                return@withContext
            }
        }
        return diary
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun onClear() {
        uiScope.launch {
            clear()
            diary.value = null
        }
    }

    suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private val _navigateToInputDiary = MutableLiveData<Diary>()

    val navigateToInputDiary: LiveData<Diary> get() = _navigateToInputDiary

    fun doneNavigating() {
        _navigateToInputDiary.value = null
    }

    private val _navigateToInputDataDiary = MutableLiveData<Long>()

    val navigateToInputDataDiary get() = _navigateToInputDataDiary

    val diaryAll = database.getAllDiary()

    fun onDiaryClicked(diaryId: Long) {
        _navigateToInputDataDiary.value = diaryId
        _navigateToInputDiary.value = getDiary(diaryId)
    }

    fun onInputDataDiaryNavigated() {
        _navigateToInputDataDiary.value = null
    }
}