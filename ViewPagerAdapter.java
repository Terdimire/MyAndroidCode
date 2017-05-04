package com.tryndamere.lzm.alltesttwo.adapter;

import java.util.List;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter{

    List<View> list;
    Handler handler;

    public ViewPagerAdapter(List<View> list, Handler handler) {
        // TODO Auto-generated constructor stub
        this.list = list;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position%list.size()));
        View view = list.get(position%list.size());
        Log.d("msg",list.size()+"===几个====");
        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i("---", "ACTION_DOWN");
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i("---", "ACTION_MOVE");
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.i("---", "ACTION_CANCEL");
                        handler.sendEmptyMessageDelayed(0, 1000);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i("---", "ACTION_UP");
                        handler.sendEmptyMessageDelayed(0, 1000);
                        break;

                    default:
                        break;
                }
                return true;
            }
        });

        return list.get(position%list.size());
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position%list.size()));
    }

}
