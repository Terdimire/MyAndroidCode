/*package com.lzm.musicseekbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

	public class MainActivity extends Activity {
	
	private List<MusicBean> musicList;
	private MediaPlayer mediaPlayer;
	private boolean flag;
	private int location;
    private TextView textView;
    private Button button;
    private SeekBar seekBar;
    private int duration = 0;
    private Timer timer;
    private TextView textView2;
    private TimeUtils timeUtils;
    private int currentt;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        textView =  (TextView) findViewById(R.id.text);
		button = (Button) findViewById(R.id.pause);
		seekBar = (SeekBar) findViewById(R.id.seeBar);
		textView2 = (TextView)findViewById(R.id.resulta);
		
		mediaPlayer = new MediaPlayer();
		timeUtils = new TimeUtils();
		flag = false;
		location = 0;
		lisitener();
        initData();
	}
    private void lisitener() {
    	*//**
    	 * 音乐播放完成的监听
    	 * *//*
    	mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				//下一首
				next();
			}
		});
    	seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				//fromUser 来自用户的操作
				if (fromUser&&mediaPlayer!=null) {
					int msec = duration*progress/1000;
					mediaPlayer.seekTo(msec);
					String time = new TimeUtils().TimeUtils(msec, currentt);
					
				}
			}
		});
	}
	private void initData() {
    	musicList = new ArrayList<MusicBean>();
    	musicList.add(new MusicBean("告白气球", "mnt/sdcard/gaobai.mp3"));
    	musicList.add(new MusicBean("烟花易冷", "mnt/sdcard/yanhua.mp3"));
    	musicList.add(new MusicBean("甜甜的", "mnt/sdcard/tiantian.mp3"));
    	musicList.add(new MusicBean("说好的幸福呢", "mnt/sdcard/xingfu.mp3"));
	}
	public void onClick(View view){
    	switch (view.getId()) {
		case R.id.play:
			play();
			break;
		case R.id.pause:
			if (mediaPlayer!=null &&mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				flag = true;
				textView.setText("暂停播放:"+musicList.get(location).getMusicName());
				((Button)view).setText("继续");
			}else {//如果没在播放判断一下是否处于暂停的状态
				if (flag) {//继续播放
					mediaPlayer.start();
					textView.setText("继续播放:"+musicList.get(location).getMusicName());
					flag = false;
					((Button)view).setText("暂停");
				}
			}
			
			break;
		case R.id.re_play:
			if (mediaPlayer!=null &&mediaPlayer.isPlaying()) {//如果正在播放,播放的位置滚动到最开始
				mediaPlayer.seekTo(0);
				textView.setText("重新播放:"+musicList.get(location).getMusicName());
			}else {//如果没有播放就调用play 播放
				play();
			}
			break;
		case R.id.stop:
			if (mediaPlayer!=null) {
				mediaPlayer.stop();
				textView.setText("停止播放:"+musicList.get(location).getMusicName());
				seekBar.setProgress(0);
				stopTimer();
			}
			break;
		case R.id.next:
			next();
			break;
		case R.id.before:
			if (location<=0) {
				location = musicList.size();
			}
			location--;
			stopTimer();
			play();
			break;		
		default:
			break;
		}
    }
	
	private void next() {
		location++;
		location = location%musicList.size();
		stopTimer();
		play();
	}
	private void play() {
		try {
		if (mediaPlayer != null) {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(musicList.get(location).getMusicPath());
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new MusicPrepared());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	private class MusicPrepared implements OnPreparedListener{

		@Override
		public void onPrepared(MediaPlayer mp) {
			mediaPlayer.start();
			textView.setText("正在播放:"+musicList.get(location).getMusicName());
			button.setText("暂停");
			//拿到当前音乐的播放总时长
			duration = mediaPlayer.getDuration();
			timer = new Timer();
			//每秒设置seekbar 的进度
		 TimerTask	task = new TimerTask() {
		 		
		 		@Override
		 		public void run() {
		 			if (mediaPlayer!=null) {
		 				int current = mediaPlayer.getCurrentPosition();
		 				int progress = current*1000/duration;
		 				mediaPlayer.getCurrentPosition();
		 				String	time = timeUtils.TimeUtils(current, duration);
		 				
		 				Bundle bundle = new Bundle();
		 				bundle.putString("time", time);
		 				bundle.putInt("current", current);
		 				Message msg =Message.obtain();
		 				msg.what = 0;
		 				msg.obj = progress;
		 				msg.setData(bundle);
		 				handler.handleMessage(msg);
		 			}
		 		}
		 	};
			timer.schedule(task,0, 1000);
		}
		
	}
	

	 Handler handler 	=new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				int progress = (Integer) msg.obj;
				Bundle bundle =msg.getData();
				//Log.d("msg", time);
				seekBar.setProgress(progress);
				Message message = Message.obtain();
				message.setData(bundle);
				handler2.sendMessage(msg);
			}
		};
	};
		Handler handler2 = new Handler(){
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				String time = bundle.getString("time");
				currentt =bundle.getInt("current");
				try {
					textView2.setText(time);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			};
		};
	private void stopTimer() {
		if (timer!=null) {
			timer.cancel();
			timer = null;
		}
	}
	@Override
	protected void onDestroy() {
		if (mediaPlayer!=null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
		super.onDestroy();
	}
}

*/