package com.xht97.photosaying.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xht97.photosaying.R;
import com.xht97.photosaying.adapter.ViewPagerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MeFragment extends Fragment {

    ImageView imageViewUserLogo;
    TextView textViewUserPlace;
    TextView textViewUserSignature;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("MeFragment", "Create View");
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 在创建完View后对View中的组件修改
        updateView();
    }

    private void initData() {

    }

    private void updateView() {

        imageViewUserLogo = (ImageView) getView().findViewById(R.id.iv_me_user_logo);
        textViewUserPlace = (TextView) getView().findViewById(R.id.tv_me_user_place);
        textViewUserSignature = (TextView) getView().findViewById(R.id.tv_me_user_signature);
        tabLayout = (TabLayout) getView().findViewById(R.id.tl_me);
        viewPager = (ViewPager) getView().findViewById(R.id.vp_me);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MePhotoFragment());
        fragmentList.add(new MeTextFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.removeAllTabs();
        List<String> titleList = new ArrayList<>();
        titleList.add("作品");
        titleList.add("文字");
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)), true);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", Context.MODE_PRIVATE);
        boolean isUserLogin = sharedPreferences.getBoolean("isUserLogin", false);
        if(isUserLogin) {
            // TODO: 用户登录逻辑待完成，目前仅为展示页面
        }

    }

}
