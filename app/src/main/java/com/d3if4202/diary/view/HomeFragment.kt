package com.d3if4202.diary.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.d3if4202.diary.R
import com.d3if4202.diary.database.Diary
import com.d3if4202.diary.database.DiaryDatabase
import com.d3if4202.diary.database.DiaryDatabaseDao
import com.d3if4202.diary.databinding.FragmentHomeBinding
import com.d3if4202.diary.viewModel.DiaryViewModel
import com.d3if4202.diary.viewModel.DiaryViewModelFactory
//CRETED BY DODY RIFKI SURAYA

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var diaryViewModel : DiaryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)


        setHasOptionsMenu(true)


        //corountine dan room
        val apllication = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(apllication).diaryDatabaseDao
        val viewModelFactory = DiaryViewModelFactory(dataSource, apllication)

        diaryViewModel = ViewModelProviders.of(this,
            viewModelFactory).get(DiaryViewModel::class.java)

        binding.diaryViewModel = diaryViewModel
        binding.setLifecycleOwner (this)

        diaryViewModel.navigateToInputDiary.observe(viewLifecycleOwner, Observer {
            it -> it?.let {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToInputDiaryFragment(it.diaryId))
            Log.e("ERR",it.diaryId.toString())
            diaryViewModel.doneNavigating()
        }
        })

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToInputDiaryFragment(0L))
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.btn_clear -> diaryViewModel.onClear()
        }
        return super.onOptionsItemSelected(item)
    }
}
