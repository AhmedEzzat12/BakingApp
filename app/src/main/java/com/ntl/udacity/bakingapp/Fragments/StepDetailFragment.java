package com.ntl.udacity.bakingapp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ntl.udacity.bakingapp.Models.Step;
import com.ntl.udacity.bakingapp.R;
import com.squareup.picasso.Picasso;

public class StepDetailFragment extends Fragment
{


    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private static final String PLAYER_POSITION = "player";
    private static final String PLAYER_STATE = "state";
    private SimpleExoPlayerView videoview;
    private SimpleExoPlayer simpleExoPlayer;
    private Step step;
    private Context context;
    private String videoURL;
    private long position;
    private int playerState;

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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        position = sharedPreferences.getLong(PLAYER_POSITION, C.TIME_UNSET);
        playerState = sharedPreferences.getInt(PLAYER_STATE, 0);

        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "Baking App"), bandwidthMeter);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoURL),
                dataSourceFactory, extractorsFactory, null, null);
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
        if (position != C.TIME_UNSET)
            simpleExoPlayer.seekTo(position);
        videoview.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause()
    {
        if (simpleExoPlayer != null)
        {
            position = simpleExoPlayer.getContentPosition();
            playerState = simpleExoPlayer.getPlaybackState();
            SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit();
            sharedPreferences.putLong(PLAYER_POSITION, position);
            sharedPreferences.putInt(PLAYER_STATE, playerState);
            sharedPreferences.apply();

        }
        relasePlayer();
        super.onPause();
    }

    private void relasePlayer()
    {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }
}
