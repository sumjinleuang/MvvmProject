package com.sumjin.peppymvvm.section.circle.fragment;

import android.app.Application;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.common.model.domain.Titles;
import com.sumjin.peppymvvm.section.circle.activity.SendDynamicActivity;
import com.sumjin.peppymvvm.section.circle.adapter.CircleAdapter;
import com.sumjin.peppymvvm.common.util.Constants;
import com.sumjin.peppymvvm.section.circle.viewmodel.CircleViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CircleFragment extends BaseFragment  {

    @BindView(R.id.home_indicator)
    TabLayout mTabLayout;

    @BindView(R.id.home_pager)
    ViewPager mContentPager;

    @BindView(R.id.circle_msg_tips)
    FloatingActionButton mMsgTipsBtn;


    @BindView(R.id.circle_send_dynamic)
    View mSendDnBtn;


    private CircleAdapter mCircleAdapter;
    private CircleViewModel mCircleViewModel;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView(View rootView) {
        setStatus(Status.SUCCESS);
        mTabLayout.setupWithViewPager(mContentPager);
        mCircleAdapter = new CircleAdapter(getChildFragmentManager());
        mContentPager.setAdapter(mCircleAdapter);
    }

    @Override
    protected void initListener() {
        mSendDnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopuMenu();

            }
        });
    }

    private void showPopuMenu() {
        PopupMenu popupMenu = new PopupMenu(getContext(),mSendDnBtn);
        popupMenu.inflate(R.menu.send_type_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.send_txt:
                    case R.id.send_image:
                    case R.id.send_video:
                        Intent intent=new Intent(getContext(), SendDynamicActivity.class);
                        intent.putExtra(Constants.KET_TYPE_DYNAMIC,menuItem.getItemId());
                        startActivity(intent);
                        return true;

                    default:
                        //do nothing
                }

                return false;
            }
        });
        popupMenu.show();
    }


    @Override
    protected void initViewModel() {
       ViewModelProvider.AndroidViewModelFactory instance =
               ViewModelProvider.AndroidViewModelFactory
                       .getInstance(getActivity().getApplication());
       mCircleViewModel = new ViewModelProvider(this, instance).get(CircleViewModel.class);
    }

    @Override
    protected void initObservable() {

      mCircleViewModel.getCategories();
      mCircleViewModel.getLiveData().observe(this, new Observer<List<Titles>>() {
          @Override
          public void onChanged(List<Titles> titles) {
              mCircleAdapter.setData(titles);
          }
      });

    }

}
