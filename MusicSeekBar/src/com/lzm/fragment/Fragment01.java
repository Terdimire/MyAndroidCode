package com.lzm.fragment;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.lzm.adpter.ViewpagerAdapter;
import com.lzm.musicseekbar.MusicBean;
import com.lzm.musicseekbar.R;
import com.lzm.musicseekbar.TimeUtils;

public class Fragment01 extends Fragment implements OnClickListener{
	private View view;
	private List<MusicBean> musicList;
	private MediaPlayer mediaPlayer;
	private boolean flag;
	private int location;
    private ImageView button,button2,button3,image_touxiang;
    private SeekBar seekBar;
    private int duration = 0;
    private Timer timer;
    private TextView textView2,textView;
    private TimeUtils timeUtils;
    private int currentt;
    private ViewpagerAdapter adapter;
    private ViewPager viewPager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view = inflater.inflate(R.layout.fragment01, container, false);
			button = (ImageView) view.findViewById(R.id.image_play);
			button2 = (ImageView) view.findViewById(R.id.image_next);
			button3 = (ImageView) view.findViewById(R.id.image_pre);
			image_touxiang = (ImageView)view.findViewById(R.id.image_touxiang);
			seekBar = (SeekBar)view.findViewById(R.id.seekBar);
			textView = (TextView)view.findViewById(R.id.text_name);
			textView2 = (TextView)view.findViewById(R.id.text_time);
			viewPager = (ViewPager)view.findViewById(R.id.view_pager);
			button.setOnClickListener(this);
			button2.setOnClickListener(this);
			button3.setOnClickListener(this);
			return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mediaPlayer = new MediaPlayer();
		timeUtils = new TimeUtils();
		flag = false;
		location = 0;
		initData();
		adapter = new ViewpagerAdapter(musicList, getActivity());
		viewPager.setAdapter(adapter);
		lisitener();
	}
	 private void lisitener() {
	    	/**
	    	 * 音乐播放完成的监听
	    	 * */
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
	    	viewPager.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					location =arg0;
					play();
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					
				}
			});
		}
		private void initData() {
	    	musicList = new ArrayList<MusicBean>();
	    	musicList.add(new MusicBean("告白气球", "mnt/sdcard/gaobai.mp3",R.drawable.a));
	    	musicList.add(new MusicBean("烟花易冷", "mnt/sdcard/yanhua.mp3",R.drawable.b));
	    	musicList.add(new MusicBean("甜甜的", "mnt/sdcard/tiantian.mp3",R.drawable.c));
	    	musicList.add(new MusicBean("说好的幸福呢", "mnt/sdcard/xingfu.mp3",R.drawable.d));
		}

		private void next() {
			location++;
			location = location%musicList.size();
			//stopTimer();
			Log.i("msg","play前"+ location); 
			
			play();
			viewPager.setCurrentItem(location);
			Log.i("msg","play后"+ location);
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
				textView.setText(musicList.get(location).getMusicName());
				image_touxiang.setImageResource(musicList.get(location).getImage());
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
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (mediaPlayer!=null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.image_play:
				Log.d("msg", "----------------"+mediaPlayer.isPlaying());
				if (mediaPlayer.isPlaying()==false&&flag==false) {
					play();
					button.setImageResource(R.drawable.pause);
				}
				if (mediaPlayer!=null &&mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
					button.setImageResource(R.drawable.play);
					flag = true;
				}else {//如果没在播放判断一下是否处于暂停的状态
					if (flag) {//继续播放
						mediaPlayer.start();
						button.setImageResource(R.drawable.pause);
						flag = false;
					}
				}
				
				break;
/*			case R.id.re_play:
				if (mediaPlayer!=null &&mediaPlayer.isPlaying()) {//如果正在播放,播放的位置滚动到最开始
					mediaPlayer.seekTo(0);
				}else {//如果没有播放就调用play 播放
					play();
				}
				break;*/
		/*	case R.id.stop:
				if (mediaPlayer!=null) {
					mediaPlayer.stop();
					textView.setText("停止播放:"+musicList.get(location).getMusicName());
					seekBar.setProgress(0);
					stopTimer();
				}
				break;*/
			case R.id.image_next:
				next();
				button.setImageResource(R.drawable.pause);
				break;
			case R.id.image_pre:
				if (location<=0) {
					location = musicList.size();
				}
				location--;
				viewPager.setCurrentItem(location);
				play();
				button.setImageResource(R.drawable.pause);
				break;		
			default:
				break;
			}
		}
}
