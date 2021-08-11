package com.sumjin.peppymvvm.section.pet.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class PetViewActivity extends BaseInitActivity {
    @BindView(R.id.pet_iv)
    public ImageView petIv;

    @BindView(R.id.pet_back)
    public ImageView backIv;

    @BindView(R.id.pet_edit)
    public TextView editTv;

    @BindView(R.id.pet_name)
    public TextView nameTv;

    @BindView(R.id.pet_breed)
    public TextView breedTv;

    @BindView(R.id.pet_birthday)
    public TextView birthTv;

    @BindView(R.id.pet_age)
    public TextView ageTv;

    @BindView(R.id.pet_gender)
    public TextView genderTv;

    @BindView(R.id.pet_accine)
    public TextView accineTv;

    @BindView(R.id.pet_tips)
    public TextView tipTv;



    @Override
    protected void initView() {
      setStatus(Status.SUCCESS);
      setFitSystemForTheme();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view;
    }

    @Override
    protected void initListener() {
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetViewActivity.this, PetDetailActivity.class));
            }
        });
    }
}
