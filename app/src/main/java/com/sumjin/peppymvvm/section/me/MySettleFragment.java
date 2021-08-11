package com.sumjin.peppymvvm.section.me;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.section.me.activity.SettleActivity;

import butterknife.BindView;

public class MySettleFragment extends BaseFragment {

    @BindView(R.id.me_icon)
    public ImageView mIconIv;

    @BindView(R.id.me_rl_background)
    public View mRlBg;

    @BindView(R.id.me_nickname)
    public TextView mNicknameTv;

    @BindView(R.id.me_presentation)
    public TextView mPresentTv;

    @BindView(R.id.me_to_settle)
    public TextView mSettle;

    @BindView(R.id.me_content_list)
    public RecyclerView mContentList;

    @BindView(R.id.me_pet_friends)
    public TextView mFriends;

    @BindView(R.id.me_pet_doctors)
    public TextView mDoctors;

    @BindView(R.id.me_order_center)
    public TextView mOrderCenter;

    @BindView(R.id.me_discount_coupon)
    public TextView mCoupon;


    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_mysettle;
    }

    @Override
    protected void initView(View rootView) {
        setStatus(Status.SUCCESS);

    }

    @Override
    protected void initListener() {
        mCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("进入领券界面");
            }
        });
        mOrderCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("进入个人订单界面");
            }
        });
        mDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("进入宠物医生界面");
            }
        });
        mFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("进入我的关注界面");
            }
        });
        mSettle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettleActivity.class));
            }
        });


    }
}
