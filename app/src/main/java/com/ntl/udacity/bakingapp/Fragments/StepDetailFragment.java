package com.ntl.udacity.bakingapp.Fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class StepDetailFragment extends Fragment
{


    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private SimpleExoPlayerView videoview;
    private SimpleExoPlayer simpleExoPlayer;
    private Step step;
    private Context context;

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
            String VideoURL = step.getVideoURL();
            InitializeVideoPlayer(container.getContext(), VideoURL);
            return view;
        } else
        {
            View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
            videoview = view.findViewById(R.id.step_detail_video);
            String VideoURL = step.getVideoURL();
            InitializeVideoPlayer(container.getContext(), VideoURL);
            TextView instructionTV = view.findViewById(R.id.step_detail_recipe_instruction);
            instructionTV.setText(step.getDescription());

            return view;
        }
    }

    private void InitializeVideoPlayer(Context context, String videoURL)
    {
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
        videoview.setPlayer(simpleExoPlayer);
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        relasePlayer();
    }

    private void relasePlayer()
    {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }
}
