package com.nsbot.demoapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nsbot.demoapp.R
import com.nsbot.demoapp.config.SHARE_TEXT
import com.nsbot.demoapp.databinding.FragmentSettingsBinding
import com.nsbot.demoapp.models.TextIcon
import com.nsbot.demoapp.ui.activity.LoginActivity
import com.nsbot.demoapp.ui.adapter.TextIconAdapter
import com.nsbot.demoapp.ui.viewModel.MainViewModel

class SettingsFragment: Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        val list = ArrayList<TextIcon>()
        list.add(TextIcon(R.drawable.ic_baseline_timer_24, getString(R.string.screen_timeout)))
        list.add(TextIcon(R.drawable.ic_baseline_share_24, getString(R.string.share_app)))
        list.add(TextIcon(R.drawable.ic_baseline_logout_24, getString(R.string.logout)))

        val onItemClickListener = object : TextIconAdapter.OnItemClickListener {
            override fun onClick(textIcon: TextIcon) {

                when(textIcon.name) {

                    getString(R.string.screen_timeout) -> {
                        val screenTimeoutFragment = ScreenTimeoutFragment()
                        screenTimeoutFragment.show(childFragmentManager, "timeout")
                    }
                    getString(R.string.share_app) -> {
                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.putExtra(Intent.EXTRA_TEXT, SHARE_TEXT)
                        intent.type = "text/plain"
                        startActivity(Intent.createChooser(intent, "Share To:"))
                    }
                    getString(R.string.logout) -> {
                        mainViewModel.appDataHelper.logOut()
                        val intent = Intent(context, LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }

                }

            }
        }

        binding.list.adapter = TextIconAdapter(list, onItemClickListener)

    }

}