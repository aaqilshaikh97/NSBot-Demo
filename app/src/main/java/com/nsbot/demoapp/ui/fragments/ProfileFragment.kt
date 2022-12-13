package com.nsbot.demoapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.nsbot.demoapp.config.LOGO_URL
import com.nsbot.demoapp.databinding.FragmentProfileBinding
import com.nsbot.demoapp.models.StoreTime
import com.nsbot.demoapp.models.StoreTime.Companion.getDisplayTime
import com.nsbot.demoapp.models.Timing
import com.nsbot.demoapp.ui.adapter.TimingAdapter
import com.nsbot.demoapp.ui.viewModel.MainViewModel

class ProfileFragment: Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seller = mainViewModel.appDataHelper.getSeller()

        binding.name.text = mainViewModel.appDataHelper.getBusinessName()

        val logo = mainViewModel.appDataHelper.getSellerLogo()
        if (logo.isNotEmpty()) {
            Glide.with(view).load("$LOGO_URL$logo").into(binding.profile)
        }

        val address = "${seller.landMark}, ${seller.address}\n${seller.city?.name}, ${seller.state?.name}, ${seller.country?.name}. Postal Code: ${seller.area?.pincode}"
        binding.address.text = address

        binding.phone.setOnClickListener {
            val account = mainViewModel.appDataHelper.getAccount()
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${account.mobile}")
            try {
                context?.startActivity(intent)
            } catch (ex: Exception) {
                Toast.makeText(context, "No app found for phone call", Toast.LENGTH_SHORT).show()
            }
        }

        binding.email.setOnClickListener {
            val account = mainViewModel.appDataHelper.getAccount()
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email")
            intent.putExtra(Intent.EXTRA_TEXT, "Body of email")
            intent.data = Uri.parse("mailto:${account.email}")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            try {
                context?.startActivity(intent)
            } catch (ex: Exception) {
                Toast.makeText(context, "No app found to send email", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            activity?.onBackPressed()
        }

        val timing = mainViewModel.appDataHelper.getStoreTiming()
        val list = ArrayList<Timing>()
        list.add(Timing("Monday", timing.monday, getDisplayTime(timing.mondayOpen), getDisplayTime(timing.mondayClose)))
        list.add(Timing("Tuesday", timing.tuesday, getDisplayTime(timing.tuesdayOpen), getDisplayTime(timing.tuesdayClose)))
        list.add(Timing("Wednesday", timing.wednesday, getDisplayTime(timing.wednesdayOpen), getDisplayTime(timing.wednesdayClose)))
        list.add(Timing("Thursday", timing.thursday, getDisplayTime(timing.thursdayOpen), getDisplayTime(timing.thursdayClose)))
        list.add(Timing("Friday", timing.friday, getDisplayTime(timing.fridayOpen), getDisplayTime(timing.fridayClose)))
        list.add(Timing("Saturday", timing.saturday, getDisplayTime(timing.saturdayOpen), getDisplayTime(timing.saturdayClose)))
        list.add(Timing("Sunday", timing.sunday, getDisplayTime(timing.sundayOpen), getDisplayTime(timing.sundayClose)))

        val adapter = TimingAdapter(list)
        binding.list.adapter = adapter

    }


}