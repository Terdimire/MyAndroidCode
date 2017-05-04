package com.tryndamere.lzm.kuaikanmanhua.fragment.HomeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tryndamere.lzm.kuaikanmanhua.R;
import com.tryndamere.lzm.kuaikanmanhua.adapter.HotFragmentAdapter;
import com.tryndamere.lzm.kuaikanmanhua.utils.DateUtil;

/**
 * Created by Administrator on 2017/4/28 0028.
 */

public class HotFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hot_fragment,container,false);
        tabLayout = (TabLayout) view.findViewById(R.id.hot_fragment_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.hot_fragment_viewpager);
        init(view);
        return  view;
    }
    private void init(View view) {


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.hot_fragment_tablayout);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.hot_fragment_viewpager);


        HotFragmentAdapter adapter = new HotFragmentAdapter(getChildFragmentManager(), DateUtil.getData());


        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount()-1);
        // 让 TabLayout 和 ViewPager 关联
        tabLayout.setupWithViewPager(viewPager);

        // 设置 选中 未选中 字的颜色
        tabLayout.setTabTextColors(getResources().getColor(R.color.cgray),getResources().getColor(R.color.coffer));

        // 设置 指示器的颜色
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.title_bg));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
