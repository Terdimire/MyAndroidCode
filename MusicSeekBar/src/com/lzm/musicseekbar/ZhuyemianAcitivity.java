package com.lzm.musicseekbar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.lzm.fragment.Fragment01;
import com.lzm.fragment.Fragment02;
import com.lzm.fragment.Fragment03;


public class ZhuyemianAcitivity extends FragmentActivity {

	public RadioGroup radioGroup;
	private FrameLayout frameLayout;
	private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.zhuyemian_acitivity);
        frameLayout = (FrameLayout) findViewById(R.id.fram);
        fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fram, new Fragment01());
		transaction.commit();
 
    }
    public void onClick(View view){
		switch (view.getId()) {
		case R.id.radio_01:
			 FragmentTransaction transaction = fragmentManager.beginTransaction();
			transaction.replace(R.id.fram, new Fragment01());
			transaction.commit();
			break;
		case R.id.radio_02:
			 FragmentTransaction transaction2 = fragmentManager.beginTransaction();

			transaction2.replace(R.id.fram, new Fragment02());
			transaction2.commit();
			break;
		case R.id.radio_03:
			 FragmentTransaction transaction3 = fragmentManager.beginTransaction();

			transaction3.replace(R.id.fram, new Fragment03());
			transaction3.commit();
			break;							
		default:
			break;
			}
    }
    
}
