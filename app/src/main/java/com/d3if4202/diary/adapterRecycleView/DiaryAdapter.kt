package com.d3if4202.diary.adapterRecycleView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.databinding.DiaryItemBinding

class DiaryAdapter(val clickListener: DiaryListener) : ListAdapter<Diary, DiaryAdapter.ViewHolder>(DiaryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: DiaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Diary,
            clickListener: DiaryListener
        ) {
            binding.diary = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DiaryItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class DiaryDiffCallback : DiffUtil.ItemCallback<Diary>() {
    override fun areItemsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem.diaryId == newItem.diaryId
    }

    override fun areContentsTheSame(oldItem: Diary, newItem: Diary): Boolean {
        return oldItem == newItem
    }

}

class DiaryListener(val clickListener: (diaryId: Long) -> Unit){
    fun onClick(diary: Diary) = clickListener(diary.diaryId)
}