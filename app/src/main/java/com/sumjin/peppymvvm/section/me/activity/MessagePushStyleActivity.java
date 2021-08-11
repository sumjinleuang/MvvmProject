package com.sumjin.peppymvvm.section.me.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMPushManager;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseActivity;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;

import butterknife.BindView;

public class MessagePushStyleActivity extends BaseInitActivity implements EaseTitleBar.OnBackPressListener {
    @BindView(R.id.title_bar)
    public EaseTitleBar titleBar;
    @BindView(R.id.rv_list)
    public RecyclerView rvList;
    private int selectedPosition;

    private static final int[] names = {R.string.push_message_style_simple, R.string.push_message_show_detail};

    public static void actionStartForResult(BaseActivity context, int position, int requestCode) {
        Intent intent = new Intent(context, MessagePushStyleActivity.class);
        intent.putExtra("position", position);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        selectedPosition = getIntent().getIntExtra("position", 0);

        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        MessageStyleAdapter adapter = new MessageStyleAdapter();
        rvList.setAdapter(adapter);
        rvList.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initListener() {
        titleBar.setOnBackPressListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_msg_push_style;
    }

    @Override
    public void onBackPress(View view) {
        onBackPressed();
    }

    private class MessageStyleAdapter extends EaseBaseRecyclerViewAdapter<EMPushManager.DisplayStyle> {

        @Override
        public ViewHolder getViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.demo_item_message_style, parent, false);
            return new MessageStyleViewHolder(view);
        }

        private class MessageStyleViewHolder extends ViewHolder<EMPushManager.DisplayStyle> {
            private TextView tvName;
            private CheckBox cbStyle;

            public MessageStyleViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void initView(View itemView) {
                tvName = findViewById(R.id.tv_name);
                cbStyle = findViewById(R.id.cb_style);
            }

            @Override
            public void setData(EMPushManager.DisplayStyle item, int position) {
                tvName.setText(names[item.ordinal()]);
                if(selectedPosition == position) {
                    cbStyle.setChecked(true);
                }else {
                    cbStyle.setChecked(false);
                }
            }
        }
    }
}
