package com.sumjin.peppymvvm.section.me.activity;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.widget.EaseRecyclerView;
import com.hyphenate.easeui.widget.EaseSearchTextView;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.chat.adapter.BlackContactAdapter;

import butterknife.BindView;

public class ContactBlackListActivity extends BaseInitActivity implements OnRefreshListener, EaseTitleBar.OnBackPressListener, View.OnClickListener, OnItemClickListener {
    @BindView(R.id.title_bar)
    public EaseTitleBar titleBar;
    @BindView(R.id.srl_refresh)
    public SmartRefreshLayout srlRefresh;
    @BindView(R.id.rv_list)
    public EaseRecyclerView rvList;
    @BindView(R.id.search_black)
    public EaseSearchTextView searchBlack;


    private BlackContactAdapter adapter;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BlackContactAdapter();
        rvList.setAdapter(adapter);

        registerForContextMenu(rvList);
    }

    @Override
    protected void initListener() {
        titleBar.setOnBackPressListener(this);
        srlRefresh.setOnRefreshListener(this);
        searchBlack.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.demo_black_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = ((EaseRecyclerView.RecyclerViewContextMenuInfo)item.getMenuInfo()).position;
        EaseUser user = adapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.action_friend_unblock :
                unBlock(user);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void unBlock(EaseUser user) {
        //移除黑名单 ，网络操作
       // viewModel.removeUserFromBlackList(user.getUsername());
    }

    private void finishRefresh() {
        if(srlRefresh != null) {
            srlRefresh.finishRefresh();
        }
    }



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_black_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_black :
                //搜索黑名单
               // SearchBlackActivity.actionStart(mContext);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //跳转联系人界面
        EaseUser user = adapter.getItem(position);
       // ContactDetailActivity.actionStart(mContext, user, DemoHelper.getInstance().getModel().isContact(user.getUsername()));

    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        //获取刷新黑名单。下拉刷新
        //viewModel.getBlackList();
    }
}
