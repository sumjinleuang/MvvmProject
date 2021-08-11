package com.sumjin.peppymvvm;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.section.circle.fragment.CircleFragment;
import com.sumjin.peppymvvm.section.home.fragment.HomeFragment;
import com.sumjin.peppymvvm.section.me.MySettleFragment;
import com.sumjin.peppymvvm.section.chat.fragment.TalkFragment;
import com.sumjin.peppymvvm.common.util.LogUtils;
import com.sumjin.peppymvvm.section.pet.activity.PetHomeActivity;

import butterknife.BindView;

public class MainActivity extends BaseInitActivity {
    @BindView(R.id.main_navigation_bar)
    public BottomNavigationView mNavigationView;

    private HomeFragment mHomeFragment;
    private CircleFragment mCircleFragment;
    private TalkFragment mTalkFragment;
    private MySettleFragment mMySettleFragment;
    private FragmentManager mFm;

    private TextView mTvMainHomeMsg, mTvMainFriendsMsg, mTvMainDiscoverMsg, mTvMainAboutMeMsg;
    private int[] badgeIds = {R.layout.demo_badge_home, R.layout.demo_badge_friends, R.layout.demo_badge_discover, R.layout.demo_badge_about_me, R.layout.demo_badge_about_me};
    private int[] msgIds = {R.id.tv_main_home_msg, R.id.tv_main_friends_msg, R.id.tv_main_discover_msg, R.id.tv_main_about_me_msg,R.id.tv_main_about_me_msg};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        mHomeFragment = new HomeFragment();
        mCircleFragment = new CircleFragment();
        mTalkFragment = new TalkFragment();
        mMySettleFragment = new MySettleFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);

        addTabBadge();

    }

    private void addTabBadge() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) mNavigationView.getChildAt(0);
        int childCount = menuView.getChildCount();
        Log.e("TAG", "bottom child count = " + childCount);
        BottomNavigationItemView itemTab;
        for (int i = 0; i < childCount; i++) {
            itemTab = (BottomNavigationItemView) menuView.getChildAt(i);
            View badge = LayoutInflater.from(this).inflate(badgeIds[i], menuView, false);
            switch (i) {
                case 0:
                    mTvMainHomeMsg = badge.findViewById(msgIds[0]);
                    break;
                case 1:
                    mTvMainFriendsMsg = badge.findViewById(msgIds[1]);
                    break;
                case 2:
                    mTvMainDiscoverMsg = badge.findViewById(msgIds[2]);
                    break;
                case 3:
                    mTvMainAboutMeMsg = badge.findViewById(msgIds[3]);
                    break;

            }
            itemTab.addView(badge);
        }
    }

    @Override
    protected void initListener() {
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LogUtils.d(MainActivity.this, "item ---->" + item);
                if (item.getItemId() == R.id.home) {
                    switchFragment(mHomeFragment);
                } else if (item.getItemId() == R.id.circle) {
                    switchFragment(mCircleFragment);
                } else if (item.getItemId() == R.id.peppy) {
                    startActivity(new Intent(MainActivity.this, PetHomeActivity.class));
                } else if (item.getItemId() == R.id.contact) {
                    switchFragment(mTalkFragment);
                } else if (item.getItemId() == R.id.mysettle) {
                    switchFragment(mMySettleFragment);
                }

                return true;
            }
        });
    }

    private BaseFragment lastOneFragment = null;

    private void switchFragment(BaseFragment targetFragment) {
        if (lastOneFragment == targetFragment) {
            return;
        }

        FragmentTransaction ft = mFm.beginTransaction();
        if (!targetFragment.isAdded()) {
            ft.add(R.id.main_pager_container, targetFragment);
        } else {
            ft.show(targetFragment);
        }

        if (lastOneFragment != null) {
            ft.hide(lastOneFragment);
        }
        lastOneFragment = targetFragment;
        ft.commit();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


}