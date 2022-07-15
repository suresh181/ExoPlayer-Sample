package com.example.exoplayersample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.exoplayersample.databinding.ActivityMainBinding
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.Util

class MainActivity : AppCompatActivity() {
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializePlayer() {

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(STREAM_URL))
        val mediaSource2 = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(STREAM_URL2))
        val mediaSource3 = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(STREAM_URL3))
        val mediaSource4 = ProgressiveMediaSource.Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(STREAM_URL4))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        val firstVideo = MediaItem.fromUri(STREAM_URL)
        val secondVideo = MediaItem.fromUri(STREAM_URL2)
        val thirdVideo = MediaItem.fromUri(STREAM_URL3)
        val fourVideo = MediaItem.fromUri(STREAM_URL4)
        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()

        exoPlayer.addMediaItem(firstVideo)
        exoPlayer.addMediaItem(secondVideo)
        exoPlayer.addMediaItem(thirdVideo)
        exoPlayer.addMediaItem(fourVideo)

        binding.playerView.setOnClickListener {
            binding.pause.visibility = View.VISIBLE
            binding.play.visibility = View.INVISIBLE
            if (exoPlayer!!.isPlaying){
                Handler().postDelayed({
                    binding.pause.visibility = View.INVISIBLE
                },1000)
                exoPlayer!!.pause()
            }else{
                binding.pause.visibility = View.INVISIBLE
                binding.play.visibility = View.VISIBLE
                Handler().postDelayed({
                    binding.play.visibility = View.INVISIBLE
                },1000)

                exoPlayer!!.play()
            }
        }

        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
        binding.playerView.player = exoPlayer
        binding.playerView.requestFocus()
    }

    private fun releasePlayer() {
        exoPlayer.release()
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

    companion object {
        const val STREAM_URL = "https://1080psongs.net/USER-DATA/1080P%20Videos/A%20TO%20Z%20Video%20Songs/K/KGF%20Chapter%202/1080P/Toofan/Toofan.mp4"
        const val STREAM_URL2 = "https://1080psongs.net/USER-DATA/1080P%20Videos/A%20TO%20Z%20Video%20Songs/D/Don/1080P/Private%20Party/Private%20Party.mp4"
        const val STREAM_URL3 = "https://1080psongs.net/USER-DATA/1080P%20Videos/A%20TO%20Z%20Video%20Songs/V/Vikram/1080P/Pathala%20Pathala/Pathala%20Pathala.mp4"
        const val STREAM_URL4 = "https://1080psongs.net/USER-DATA/1080P%20Videos/A%20TO%20Z%20Video%20Songs/K/Kaathuvaakula%20Rendu%20Kaadhal/1080P/Dippam%20Dappam/Dippam%20Dappam.mp4"

    }
}