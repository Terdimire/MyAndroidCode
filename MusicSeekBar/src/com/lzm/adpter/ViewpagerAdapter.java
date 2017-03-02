package com.lzm.adpter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lzm.musicseekbar.MusicBean;

public class ViewpagerAdapter extends PagerAdapter{
	
	private List<MusicBean> list;
	private Context mContext;
		
	public ViewpagerAdapter(List<MusicBean> list, Context mContext) {
		super();
		this.list = list;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(list.get(position).getImage());
		imageView.setScaleType(ScaleType.FIT_XY);
		container.addView(imageView);
		return imageView;
		
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
