package com.d3if4202.diary

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.d3if4202.diary.database.Diary

@BindingAdapter("tanggalDiary")
fun TextView.setTanggalDiary(item: Diary?){
    item?.let {
        text = convertLongToDateString(item.tanggal)
    }
}

@BindingAdapter("isiDiary")
fun EditText.setIsiDiary(item: Diary?){
    item?.let {
        text = SpannableStringBuilder("bindingAda")
    }
}

@BindingAdapter("isiPesanDiary")
fun TextView.setIsiDiary(item: Diary?){
    item?.let {
        text = item.pesan
    }
}