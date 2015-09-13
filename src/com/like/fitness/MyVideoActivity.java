package com.like.fitness;

import java.lang.reflect.Type;
import java.util.List;

import org.json.JSONArray;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.google.gson.reflect.TypeToken;
import com.like.adapter.VideoGridAdapter;
import com.like.entity.VideoEntity;
import com.like.network.GsonUtil;

public class MyVideoActivity extends BaseActivity implements Callback {

	private GridView mGridView;
	private VideoGridAdapter mAdapter;
	private SurfaceView mPlayerContainer;
	private SurfaceHolder mSurfaceHolder;
	private MediaPlayer mPlayer;
	private String mVideoPath;
	private ImageView mPlayIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_video);
		mGridView = (GridView) findViewById(R.id.video_grid);
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				VideoEntity video = mAdapter.getItem(position);
				mVideoPath = video.path;
			}
		});
		mPlayIcon = (ImageView) findViewById(R.id.player_indicator);
		mPlayerContainer = (SurfaceView) findViewById(R.id.surface_view);
		mSurfaceHolder = mPlayerContainer.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// Surface类型
		// initData();
		// mAdapter = new VideoGridAdapter(this, videoEntities);
		// mGridView.setAdapter(mAdapter);
		mPlayerContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mPlayer == null)
					return;
				if(mPlayer.isPlaying()) {
					mPlayer.pause();
					mPlayIcon.setVisibility(View.VISIBLE);
					mPlayerContainer.setBackgroundResource(R.drawable.player_preview);
				} else {
					mPlayer.start();
					mPlayIcon.setVisibility(View.GONE);
					mPlayerContainer.setBackgroundColor(0x00000000);
				}
			}
		});
		updateList();
	}

	private void updateList() {
		mDataFetcher.fetchAllVideo(mLoginUser.coachid,
				new Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						Type type = new TypeToken<List<VideoEntity>>() {
						}.getType();
						List<VideoEntity> videos = GsonUtil.gson.fromJson(
								response.toString(), type);
						for (int i = 0; i < 20; i++) {
							videos.add(new VideoEntity(
									"http://www.w3school.com.cn/i/movie.mp4"));
						}
						mVideoPath = videos.get(0).path;
						mAdapter = new VideoGridAdapter(mContext, videos);
						mGridView.setAdapter(mAdapter);
						sfCreated(mSurfaceHolder);
					}
				}, mErrorListener);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	private void sfCreated(final SurfaceHolder holder) {
		// 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
		// String path = APIS.BASE_URL + mGoods.video_url;
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mPlayIcon.setVisibility(View.VISIBLE);
					mPlayerContainer.setBackgroundResource(R.drawable.player_preview);
				}
			});
		}
		else
			mPlayer.reset();
		Thread mediaThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mPlayer.setDataSource(mVideoPath);
					mPlayer.prepare();
					mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					mPlayer.setDisplay(mSurfaceHolder);
				} catch (Exception e) {
					e.printStackTrace();
					mPlayerContainer.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(mContext, "视屏正在加载中，请稍后",
									Toast.LENGTH_LONG).show();
//							play.setImageResource(R.drawable.zt);
						}
					});
					if (!isFinishing()) {
						sfCreated(holder);
					}
				}
			}
		});
		mediaThread.start();
	}

}
