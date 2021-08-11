package com.sumjin.peppymvvm.section.pet.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.model.domain.MsgPet;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.Statuss;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.chat.viewmodel.ConversationListViewModel;
import com.sumjin.peppymvvm.section.pet.adapter.PetAdapter;
import com.sumjin.peppymvvm.section.pet.viewmodel.PetViewModel;

import butterknife.BindView;

public class PetHomeActivity extends BaseInitActivity {
    public static final int DEFAULT_SPAN_COUNT = 2;
    private static final String TAG = "PetHomeActivity";
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.list_pet)
    public RecyclerView mListContent;

    @BindView(R.id.add_pet_info)
    public TextView addPetInfo;

    @BindView(R.id.my_pet)
    public TextView myPet;

    private PetAdapter mPetAdapter;
    private PetViewModel mPetViewModel;

    @Override
    protected void initView() {
        setFitSystemForTheme();
        setStatus(Status.SUCCESS);
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("我的宠物");

        mListContent.setLayoutManager(new GridLayoutManager(this, DEFAULT_SPAN_COUNT));
        mPetAdapter = new PetAdapter();
        mListContent.setAdapter(mPetAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_pet_home;
    }

    @Override
    protected void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        myPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetHomeActivity.this,PetViewActivity.class));
            }
        });

        addPetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initViewModel() {
        mPetViewModel = new ViewModelProvider(this).get(PetViewModel.class);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initObservable() {
       String username = "test1";
       mPetViewModel.getPetMsg(username);

       mPetViewModel.getLiveData().observe(this, new Observer<Resource<MsgPet>>() {
           @Override
           public void onChanged(Resource<MsgPet> data) {
               updateView(data);
           }
       });
    }

    private void updateView(Resource<MsgPet> data) {
        Statuss status = data.status;
        switch (data.status){
            case CONTENT:
                Log.e(TAG,"内容 ----->"+data.data);
                setStatus(Status.SUCCESS);
                mPetAdapter.setData(data.data.getDatas());
                break;
            case LOADING:
                Log.e(TAG,"正在加载中 -----");
                setStatus(Status.LOADING);
                break;
            case ERROR:
                Log.e(TAG,"网络错误 -----");
               // setStatus(Status.NETWORK_ERROR);
                break;
            case EMPTY:
                Log.e(TAG,"内容为空 -----");
                setStatus(Status.EMPTY);
                break;

        }
    }
}
