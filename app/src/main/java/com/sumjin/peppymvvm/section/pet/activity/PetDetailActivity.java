package com.sumjin.peppymvvm.section.pet.activity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.widget.ArrowItemView;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class PetDetailActivity extends BaseInitActivity implements View.OnClickListener{
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.item_id)
    public ArrowItemView itemId;

    @BindView(R.id.item_gender)
    public ArrowItemView itemGender;

    @BindView(R.id.item_avatar)
    public ArrowItemView itemAvatar;

    @BindView(R.id.item_nickname)
    public ArrowItemView itemNickname;

    @BindView(R.id.item_breed)
    public ArrowItemView itemBreed;

    @BindView(R.id.item_color)
    public ArrowItemView itemColor;

    @BindView(R.id.item_second_color)
    public ArrowItemView itemSecondColor;

    @BindView(R.id.item_third_color)
    public ArrowItemView itemThirdColor;

    @BindView(R.id.item_heavy)
    public ArrowItemView itemHeavy;

    @BindView(R.id.item_birthday)
    public ArrowItemView itemBirthday;

    @BindView(R.id.item_doctor)
    public ArrowItemView itemDoctor;

    @BindView(R.id.item_tip)
    public ArrowItemView itemTip;

    @BindView(R.id.item_delete)
    public ArrowItemView itemDelete;


    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);

        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("宠物信息编辑");

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pet_detail;
    }

    @Override
    protected void initListener() {
        itemAvatar.setOnClickListener(this);
        itemNickname.setOnClickListener(this);
        itemGender.setOnClickListener(this);
        itemBreed.setOnClickListener(this);
        itemColor.setOnClickListener(this);
        itemSecondColor.setOnClickListener(this);
        itemThirdColor.setOnClickListener(this);
        itemHeavy.setOnClickListener(this);
        itemBirthday.setOnClickListener(this);
        itemDoctor.setOnClickListener(this);
        itemTip.setOnClickListener(this);
        itemDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_avatar:
                //头像
                break;
            case R.id.item_nickname:
                //昵称  网络获取
                PetUpdateActivity.actionStart(this,"宠物昵称","jacket");
                break;
            case R.id.item_gender:
                //性别
                PetUpdateActivity.actionStart(this,"宠物性别","男");
                break;
            case R.id.item_breed:
                //品种
                PetUpdateActivity.actionStart(this,"宠物品种","贵宾犬");
                break;
            case R.id.item_color:
                //肤色
                PetUpdateActivity.actionStart(this,"宠物肤色","白色");
                break;
            case R.id.item_second_color:
                //第二肤色
                PetUpdateActivity.actionStart(this,"宠物第二肤色","白色");
                break;
            case R.id.item_third_color:
                //第三肤色
                PetUpdateActivity.actionStart(this,"宠物第三肤色","白色");
                break;
            case R.id.item_heavy:
                //体重
                PetUpdateActivity.actionStart(this,"宠物近似体重","15");
                break;
            case R.id.item_birthday:
                //生日
                PetUpdateActivity.actionStart(this,"宠物生日","1.1");
                break;
            case R.id.item_doctor:
                //医生
                PetUpdateActivity.actionStart(this,"宠物医生","Doc Lee");
                break;
            case R.id.item_tip:
                //备注
                PetUpdateActivity.actionStart(this,"宠物备注","更多宠物信息");
                break;
            case R.id.item_delete:
                //注销
                break;
        }
    }


}
