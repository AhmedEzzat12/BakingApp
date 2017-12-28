package com.ntl.udacity.bakingapp.Fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ntl.udacity.bakingapp.Models.Step;
import com.ntl.udacity.bakingapp.R;
import com.squareup.picasso.Picasso;

public class StepDetailFragment extends Fragment implements Player.EventListener
{


    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private static final String PLAYER_POSITION = "player";
    private static final String PLAYER_STATE = "state";
    private SimpleExoPlayerView videoview;
    private SimpleExoPlayer simpleExoPlayer;
    private Step step;
    private Context context;
    private String videoURL;
    private long position = C.TIME_UNSET;
    private int playerState = 1000;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;

    public StepDetailFragment()
    {

    }

    public static StepDetailFragment getStepDetailFragmentInstance(Step step)
    {
        StepDetailFragment detailFragment = new StepDetailFragment();
        detailFragment.setStep(step);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        setRetainInstance(true);
        if (savedInstanceState != null)
        {
            position = savedInstanceState.getLong(PLAYER_POSITION);
            playerState = savedInstanceState.getInt(PLAYER_STATE);

        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    public void setStep(Step step)
    {
        this.step = step;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !getResources().getBoolean(R.bool.isTablet))
        {
            View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
            videoview = view.findViewById(R.id.step_detail_video);
            videoURL = step.getVideoURL();
            return view;
        } else
        {
            View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
            videoview = view.findViewById(R.id.step_detail_video);
            videoURL = step.getVideoURL();
            TextView instructionTV = view.findViewById(R.id.step_detail_recipe_instruction);
            ImageView imageView = view.findViewById(R.id.step_thumbnail);
            try
            {
                Picasso.with(inflater.getContext()).load(step.getThumbnailURL()).into(imageView);
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            instructionTV.setText(step.getDescription());

            return view;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (videoURL != null)
            InitializeVideoPlayer(videoURL);
    }

    private void InitializeVideoPlayer(String videoURL)
    {
        initializeMediaSession();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoURL), new DefaultDataSourceFactory(
                context, Util.getUserAgent(context, "Baking App"))
                , new DefaultExtractorsFactory(), null, null);
        TrackSelector trackSelector =
                new DefaultTrackSelector();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        videoview.setPlayer(simpleExoPlayer);
        simpleExoPlayer.addListener(this);
        if (position != C.TIME_UNSET)
            simpleExoPlayer.seekTo(position);
        if (playerState != 1000)
        {

            stateBuilder.setState(playerState, simpleExoPlayer.getContentPosition(), 1f);
            mediaSession.setPlaybackState(stateBuilder.build());
        }
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private void initializeMediaSession()
    {
        mediaSession = new MediaSessionCompat(context, "Step Detail");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback()
        {
            @Override
            public void onPlay()
            {
                simpleExoPlayer.setPlayWhenReady(true);
            }

            @Override
            public void onPause()
            {
                simpleExoPlayer.setPlayWhenReady(false);
            }

        });
        mediaSession.setActive(true);

    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (simpleExoPlayer != null)
        {
            relasePlayer();
        }

    }

    private void relasePlayer()
    {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        if (mediaSession != null)
        {
            mediaSession.setActive(false);
        }

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest)
    {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections)
    {

    }

    @Override
    public void onLoadingChanged(boolean isLoading)
    {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
    {
        if ((playbackState == Player.STATE_READY) && playWhenReady)
        {
            stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, simpleExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == Player.STATE_READY))
        {
            stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, simpleExoPlayer.getCurrentPosition(), 1f);
        }
        mediaSession.setPlaybackState(stateBuilder.build());

    }

    @Override
    public void onRepeatModeChanged(int repeatMode)
    {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled)
    {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error)
    {

    }

    @Override
    public void onPositionDiscontinuity(int reason)
    {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
    {

    }

    @Override
    public void onSeekProcessed()
    {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        if (simpleExoPlayer != null)
        {
            position = simpleExoPlayer.getContentPosition();
            playerState = simpleExoPlayer.getPlaybackState();
            outState.putLong(PLAYER_POSITION, position);
            outState.putInt(PLAYER_STATE, playerState);

        }


        super.onSaveInstanceState(outState);
    }
}
