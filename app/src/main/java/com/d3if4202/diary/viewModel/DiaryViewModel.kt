package com.d3if4202.diary.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.d3if4202.diary.R
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.database.DiaryDatabaseDao
import kotlinx.coroutines.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
//CRETED BY DODY RIFKI SURAYA

class DiaryViewModel (
    val database : DiaryDatabaseDao,
            application: Application
) : AndroidViewModel(application){
    private var viewModelJob = Job()



    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var diary = MutableLiveData<Diary?>()

    init {
        initializeDiary()
    }

    private fun initializeDiary(){
        uiScope.launch {
            diary.value = getDiaryFromDatabase()
        }
    }

    private suspend fun getDiaryFromDatabase(): Diary?{
        return withContext(Dispatchers.IO){
            var mydiary = database.getThisDiary()
            mydiary
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onWriteDiary(){
        uiScope.launch {
            val newDiary = Diary()
            insert(newDiary)
            diary.value = getDiaryFromDatabase()

            val thisDiary = diary.value ?: return@launch
            update(thisDiary)
            _navigateToInputDiary.value = thisDiary

        }

    }

    private suspend fun insert(diary: Diary){
        withContext(Dispatchers.IO){
            database.insert(diary)
        }
    }

    private suspend fun update(night: Diary) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    public fun onClear(){
        uiScope.launch {
            clear()
            diary.value = null
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    private val _navigateToInputDiary = MutableLiveData<Diary>()

    val navigateToInputDiary: LiveData<Diary> get() = _navigateToInputDiary

    fun doneNavigating(){
        _navigateToInputDiary.value = null
    }

    private val diaryAll = database.getAllDiary()
    val diaryAllString = Transformations.map(diaryAll){
        diaryAll -> formatDiary(diaryAll, application.resources)
    }


    fun formatDiary(diary: List<Diary>, resouce: Resources): Spanned{
        val sb = StringBuilder()
        sb.apply {

            diary.forEach{
                append("<br>")
                append(resouce.getString(R.string.tanggal))
                append("<b>\t${convertLongToDateString(it.tanggal)}</b><br>")
                append("\t${convertIsi(it.pesan, resouce)}<br>")
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
        }else{
            return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertLongToDateString(systemTime: Long): String {
        return SimpleDateFormat("EEEE, MMM dd yyyy")
            .format(systemTime).toString()
    }

    fun convertIsi(isi: String, resouce: Resources): String{
        return isi
    }
}