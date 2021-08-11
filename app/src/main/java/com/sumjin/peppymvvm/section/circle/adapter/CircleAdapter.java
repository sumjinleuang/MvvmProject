package com.sumjin.peppymvvm.section.circle.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sumjin.peppymvvm.common.model.domain.Titles;
import com.sumjin.peppymvvm.section.circle.fragment.CirclePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class CircleAdapter extends FragmentPagerAdapter {

    List<Titles> mDatas=new ArrayList<>();

    public CircleAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Titles titles = mDatas.get(position);
        CirclePagerFragment circlePagerFragment=CirclePagerFragment.newInstance(titles);
        return circlePagerFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    public void setData(List<Titles> titles) {
        mDatas.clear();
        mDatas.addAll(titles);
        notifyDataSetChanged();
    }
}
