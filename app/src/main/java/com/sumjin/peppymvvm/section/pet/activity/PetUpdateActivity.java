package com.sumjin.peppymvvm.section.pet.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Constants;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.me.activity.AboutPeppyActivity;

import butterknife.BindView;

public class PetUpdateActivity extends BaseInitActivity {

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.content_tv)
    public EditText mContent;

    private MenuItem mItem;

    private String mTitle;
    private String mValue;

    public static void actionStart(Context context, String type,String value) {
        Intent starter = new Intent(context, PetUpdateActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_PET_TYPE,type);
        bundle.putString(Constants.KEY_PET_VALUE,value);
        starter.putExtra(Constants.KEY_PET_BUNDLE,bundle);
        context.startActivity(starter);
    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getBundleExtra(Constants.KEY_PET_BUNDLE);
        mTitle = (String) bundle.get(Constants.KEY_PET_TYPE);
        mValue = (String) bundle.get(Constants.KEY_PET_VALUE);
        setTitle(mTitle);

        mContent.setHint(mValue);

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pet_update;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if (item.getItemId() == R.id.send){
            //发送网络请求
            String content = mContent.getText().toString().trim();
            ToastUtil.showToast("修改的内容:"+mTitle+"---修改为"+content);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_send_menu,menu);
        mItem = menu.findItem(R.id.send);
        mItem.setTitle("确定");
        return true;
    }
}
