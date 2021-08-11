package com.sumjin.peppymvvm.section.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.enums.SearchType;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.chat.adapter.AddContactAdapter;

public class AddContactActivity extends SearchActivity implements AddContactAdapter.OnItemAddClickListener {

    private SearchType mType;

    public static void startAction(Context context, SearchType type) {
        Intent intent = new Intent(context, AddContactActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent(Intent intent) {
        super.initIntent(intent);
        mType = (SearchType) getIntent().getSerializableExtra("type");
    }

    @Override
    protected void initView() {
        super.initView();
        titleBar.setTitle(getString(R.string.em_search_add_contact));
        query.setHint(getString(R.string.em_search_add_contact_hint));
    }

    @Override
    protected void initData() {
        super.initData();
        ((AddContactAdapter)adapter).setOnItemAddClickListener(this);
    }

    @Override
    protected EaseBaseRecyclerViewAdapter getAdapter() {
        return new AddContactAdapter();
    }

    @Override
    public void searchMessages(String query) {
        // you can search the user from your app server here.
        if(!TextUtils.isEmpty(query)) {
            if (adapter.getData() != null && !adapter.getData().isEmpty()) {
                adapter.clearData();
            }
            adapter.addData(query);
        }
    }

    @Override
    protected void onChildItemClick(View view, int position) {
        // 跳转到好友页面
        String item = (String) adapter.getItem(position);
        EaseUser user = new EaseUser(item);
        ContactDetailActivity.actionStart(mContext, user, true);
    }




    @Override
    public void onItemAddClick(View view, int position) {

    }
}
