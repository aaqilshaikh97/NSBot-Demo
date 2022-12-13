package com.nsbot.demoapp.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.nsbot.demoapp.R
import com.nsbot.demoapp.databinding.FragmentVideoPlayerBinding

class VideoPlayerFragment: Fragment() {

    private var mPlayer: SimpleExoPlayer? = null

    private lateinit var binding: FragmentVideoPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            activity?.onBackPressed()
        }

        startPlayer()
    }

    private fun startPlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(requireContext()),
            DefaultTrackSelector()
        )
        binding.exoPlayer.player = mPlayer

        //set source of video to play
        val dataSourceFactory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), getString(R.string.app_name))
        )
        val extractorMediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(VIDEO_URL))


        mPlayer?.prepare(extractorMediaSource, false, false)
        mPlayer?.playWhenReady = false

    }

    override fun onResume() {
        super.onResume()
        //resume video
        mPlayer?.playWhenReady = true
    }

    override fun onPause() {
        //pause video when app is paused or background
        mPlayer?.playWhenReady = false
        super.onPause()
    }

    private fun release() {
        if (mPlayer == null) {
            return
        }
        mPlayer!!.release()
        mPlayer = null
    }

    override fun onDestroyView() {
        release()
        super.onDestroyView()
    }

    companion object {
        private const val VIDEO_URL = "https://nsbot.tech//wp-content//uploads//2022//06//NSBOT-HOME-PAGE.mp4"
    }

}