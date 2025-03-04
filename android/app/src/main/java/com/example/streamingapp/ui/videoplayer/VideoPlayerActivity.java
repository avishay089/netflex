package com.example.streamingapp.ui.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.streamingapp.R;
import com.example.streamingapp.model.Movie;

public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView videoView;
    private ProgressBar progressBar;
    private String videoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);

        videoUrl = getIntent().getStringExtra("videoUrl");
        if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
            setupVideoPlayer();
        }
    }

    private void setupVideoPlayer() {
        // Create media controller
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        // Set video path
        videoView.setVideoPath(videoUrl);

        // Add listeners
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Hide progress bar
                progressBar.setVisibility(View.GONE);
                // Start playing
                videoView.start();
                // Set video aspect ratio
                setVideoSize();
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(VideoPlayerActivity.this,
                        "Error playing video", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // Start loading
        videoView.requestFocus();
    }

    private void setVideoSize() {
        // Get video dimensions
        int videoWidth = videoView.getWidth();
        int videoHeight = videoView.getHeight();

        // Get screen dimensions
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Calculate scaling
        float heightRatio = (float) screenHeight / (float) videoHeight;
        float widthRatio = (float) screenWidth / (float) videoWidth;

        // Update video view size
        ViewGroup.LayoutParams layoutParams = videoView.getLayoutParams();
        if (heightRatio < widthRatio) {
            layoutParams.height = screenHeight;
            layoutParams.width = (int) (videoWidth * heightRatio);
        } else {
            layoutParams.width = screenWidth;
            layoutParams.height = (int) (videoHeight * widthRatio);
        }
        videoView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }
}