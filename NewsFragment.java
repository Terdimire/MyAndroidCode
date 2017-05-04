package com.tryndamere.lzm.alltesttwo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tryndamere.lzm.alltesttwo.MainActivity;
import com.tryndamere.lzm.alltesttwo.R;
import com.tryndamere.lzm.alltesttwo.adapter.NewsAdatper;
import com.tryndamere.lzm.alltesttwo.adapter.ViewPagerAdapter;
import com.tryndamere.lzm.alltesttwo.bean.News;
import com.tryndamere.lzm.alltesttwo.bean.PagerBean;
import com.tryndamere.lzm.alltesttwo.http.UrlConnectionUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment {

    private ListView listView;
    private NewsAdatper adatper;
    private List<News.DataBean> list = new ArrayList<>();
    private String channelId;
    private String startNum;
    private List<String> listImage = new ArrayList<>();
    private List<ImageView> listPoint = new ArrayList<>();
    private LinearLayout linearLayout;
    private ImageLoader imageLoader;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private View headerView;
    private List<View> views;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                String result = (String) msg.obj;
                News news = JSON.parseObject(result,News.class);
                list = news.getData();
                adatper = new NewsAdatper(list,getActivity());
                listView.setAdapter(adatper);
                adatper.notifyDataSetChanged();
            }
            if (msg.what == 0) {
                int current = viewPager.getCurrentItem();
                viewPager.setCurrentItem(current + 1);
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_list,container,false);
        listView = (ListView) view.findViewById(R.id.lv);
        adatper = new NewsAdatper(list,getActivity());
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.listview_header,null);
       // LayoutInflater lif = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // headerView = lif.inflate(R.layout.listview_header, listView,false);
        viewPager = (ViewPager) headerView.findViewById(R.id.viewPager);

        linearLayout = (LinearLayout) headerView.findViewById(R.id.ll_dot);
        imageLoader = ImageLoader.getInstance();

        listView.addHeaderView(headerView);
        listView.setAdapter(adatper);

        Bundle bundle =getArguments();
        channelId = bundle.getString("channelId","0");
        startNum = bundle.getString("startNum","0");

        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initListView();

        super.onActivityCreated(savedInstanceState);
    }

    private void initListView() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                HashMap<String,String> urlMap = new HashMap<String,String>();
                urlMap.put("channelId",channelId);
                urlMap.put("startNum",startNum);
                String result = UrlConnectionUtils.sendPost("http://www.93.gov.cn/93app/data.do",urlMap,"UTF-8");
                if (result!=null){
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }
        }.start();
        RequestParams params = new RequestParams("http://qhb.2dyt.com/Bwei/news?type=2&postkey=1503d&page=1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                PagerBean pagerBean = JSON.parseObject(s,PagerBean.class);
                listImage = pagerBean.getListViewPager();
                Log.d("msg",listImage.toString());
                initViewPager();
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private void initViewPager() {
        views = new ArrayList<View>();
        for (int i = 0; i < listImage.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageLoader.displayImage(listImage.get(i), imageView);
            views.add(imageView);
        }
        viewPagerAdapter = new ViewPagerAdapter(views,handler);
        viewPager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0, 1000);
        viewPager.setCurrentItem(list.size() * 10000);
        initDot(views.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<listPoint.size() ; i++){

                    if(i == position % listPoint.size()){
                        listPoint.get(i).setImageResource(R.drawable.dot_unselected);
                    } else {
                        listPoint.get(i).setImageResource(R.drawable.dot_selected);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initDot(int size) {
        for (int i = 0; i < size; i++) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView imageView =  new ImageView(getActivity());
            params.setMargins(0,0,8,0);
            imageView.setLayoutParams(params);
            listPoint.add(imageView);
            if(i == 0){
                imageView.setImageResource(R.drawable.dot_unselected);
            } else {
                imageView.setImageResource(R.drawable.dot_selected);
            }
            linearLayout.addView(imageView);

        }
    }
}
