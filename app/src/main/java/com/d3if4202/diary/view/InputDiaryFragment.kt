package com.d3if4202.diary.view

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.d3if4202.diary.R
import com.d3if4202.diary.database.DiaryDatabase
import com.d3if4202.diary.databinding.FragmentInputDiaryBinding
import com.d3if4202.diary.viewModel.InputDiaryViewModel
import com.d3if4202.diary.viewModel.InputDiaryViewModelFactory

//CRETED BY DODY RIFKI SURAYA

class InputDiaryFragment : Fragment() {

    private lateinit var bind: FragmentInputDiaryBinding
    private lateinit var isi: String
    private lateinit var safearguments: InputDiaryFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_input_diary, container, false)

        setHasOptionsMenu(true)


        val application = requireNotNull(this.activity).application
        safearguments = InputDiaryFragmentArgs.fromBundle(arguments!!)


        val dataSource = DiaryDatabase.getInstance(application).diaryDatabaseDao
        val viewModelFactory = InputDiaryViewModelFactory(safearguments.diaryKey, dataSource)
        val inputViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(InputDiaryViewModel::class.java)
        bind.inputViewModel = inputViewModel

        //cek arg
//        if (safearguments.diaryKey != 0L){
//            val isi = safearguments.isi
//            Log.e("ISI (INPUT)", "isi -> "+isi)
//            bind.etIsi.text = SpannableStringBuilder(isi)
//        }


        inputViewModel.navigateToHome.observe(viewLifecycleOwner, Observer {
            if (it == true) {

                this.findNavController()
                    .navigate(InputDiaryFragmentDirections.actionInputDiaryFragmentToHomeFragment())
                inputViewModel.doneNavigating()
            }
        })
        Log.e("ID (Fragment)", safearguments.diaryKey.toString()+" -> "+safearguments.isi.toString())
        return bind.root
    }

    fun getDiaryKey(): Long {
        val argumen = InputDiaryFragmentArgs.fromBundle(arguments!!)
        return argumen.diaryKey

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.simpan, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_simpan -> {
                if (safearguments.diaryKey == 0L) {
                    isi = bind.etIsi.text.toString()
                    bind.inputViewModel?.onWrite(isi)
                    findNavController().navigate(R.id.action_inputDiaryFragment_to_homeFragment)
                }else{
                    isi = bind.etIsi.text.toString()
                    bind.inputViewModel?.onUpdate(safearguments.diaryKey, isi)
                    findNavController().navigate(R.id.action_inputDiaryFragment_to_homeFragment)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
