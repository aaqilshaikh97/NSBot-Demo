package com.nsbot.demoapp.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nsbot.demoapp.databinding.FragmentDialogTiemoutBinding
import com.nsbot.demoapp.ui.viewModel.MainViewModel

class ScreenTimeoutFragment: DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var safebinding: FragmentDialogTiemoutBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentDialogTiemoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timer = mainViewModel.appDataHelper.getScreenTimeout()
        binding.timer.text = "$timer Seconds"

        binding.seekBar.max = 55
        binding.seekBar.progress = timer - 5

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
            ) {
                val p = progress+5
                binding.timer.text = "$p Seconds"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.save.setOnClickListener {
            mainViewModel.appDataHelper.setScreenTimeout(binding.seekBar.progress+5)
            dismissAllowingStateLoss()
        }

    }
}