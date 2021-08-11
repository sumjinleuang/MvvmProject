package com.sumjin.peppymvvm.section.chat.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.interfaces.OnItemClickListener;
import com.hyphenate.easeui.manager.EaseSystemMsgManager;
import com.hyphenate.easeui.manager.EaseThreadManager;
import com.hyphenate.easeui.model.EaseEvent;
import com.hyphenate.easeui.modules.conversation.EaseConversationListLayout;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationChangeListener;
import com.hyphenate.easeui.modules.conversation.interfaces.OnConversationLoadListener;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationInfo;
import com.hyphenate.easeui.modules.menu.EasePopupMenuHelper;
import com.hyphenate.easeui.modules.menu.OnPopupMenuItemClickListener;
import com.hyphenate.easeui.modules.menu.OnPopupMenuPreShowListener;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseSearchTextView;
import com.sumjin.peppymvvm.DemoHelper;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.enums.SearchType;
import com.sumjin.peppymvvm.common.interfaceOrImplement.OnResourceParseCallback;
import com.sumjin.peppymvvm.common.livedatas.LiveDataBus;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.DemoConstant;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseActivity;
import com.sumjin.peppymvvm.section.base.BaseFragment;
import com.sumjin.peppymvvm.common.util.PreferenceManager;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.chat.activity.AddContactActivity;
import com.sumjin.peppymvvm.section.chat.activity.ChatActivity;
import com.sumjin.peppymvvm.section.chat.viewmodel.ConversationListViewModel;
import com.sumjin.peppymvvm.section.chat.viewmodel.MessageViewModel;
import com.sumjin.peppymvvm.section.dialog.DemoDialogFragment;
import com.sumjin.peppymvvm.section.dialog.SimpleDialogFragment;
import com.sumjin.peppymvvm.section.login.activity.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class TalkFragment extends BaseFragment implements OnItemClickListener, OnPopupMenuItemClickListener, OnPopupMenuPreShowListener, OnConversationLoadListener, OnConversationChangeListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.network_iv)
    public ImageView mLoginIV;

    private TextView mLoginTV;

    private ConversationListViewModel mViewModel;

    public LinearLayout llRoot;
    public EaseConversationListLayout conversationListLayout;
    public SwipeRefreshLayout srlRefresh;
    private EaseSearchTextView tvSearch;

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_talk_home, container, false);
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.ease_fragment_conversations;
    }

    @Override
    protected void initView(View rootView) {
        if (PreferenceManager.getInstance().getAccountIsLogin()) {
            setStatus(Status.SUCCESS);
        }else {
            setStatus(Status.NETWORK_ERROR);
        }
        setHasOptionsMenu(true);
        llRoot = rootView.findViewById(R.id.ll_root);
        srlRefresh = rootView.findViewById(R.id.srl_refresh);
        conversationListLayout = rootView.findViewById(R.id.list_conversation);
        conversationListLayout.init();

        //添加搜索会话布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.demo_layout_search, null);
        llRoot.addView(view, 0);
        tvSearch = view.findViewById(R.id.tv_search);
        conversationListLayout.getListAdapter().setEmptyLayoutId(R.layout.ease_layout_default_no_data);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (PreferenceManager.getInstance().getAccountIsLogin()) {
            setStatus(Status.SUCCESS);
        }else {
            setStatus(Status.NETWORK_ERROR);
        }
    }

    @Override
    protected void onRetry() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override
    protected void initListener() {
        conversationListLayout.setOnItemClickListener(this);
        conversationListLayout.setOnPopupMenuItemClickListener(this);
        conversationListLayout.setOnPopupMenuPreShowListener(this);
        conversationListLayout.setOnConversationLoadListener(this);
        conversationListLayout.setOnConversationChangeListener(this);
        srlRefresh.setOnRefreshListener(this);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //搜索界面
                //SearchConversationActivity.actionStart(mContext);
            }
        });
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(ConversationListViewModel.class);

        mViewModel.getReadObservable().observe(getViewLifecycleOwner(), response -> {
            parseResource(response, new OnResourceParseCallback<Boolean>() {
                @Override
                public void onSuccess(Boolean data) {
                    LiveDataBus.get().with(DemoConstant.MESSAGE_CHANGE_CHANGE).postValue(new EaseEvent(DemoConstant.MESSAGE_CHANGE_CHANGE, EaseEvent.TYPE.MESSAGE));
                    conversationListLayout.loadDefaultData();
                }
            });
        });

        mViewModel.getConversationInfoObservable().observe(getViewLifecycleOwner(), response -> {
            parseResource(response, new OnResourceParseCallback<List<EaseConversationInfo>>(true) {
                @Override
                public void onSuccess(@Nullable List<EaseConversationInfo> data) {
                    conversationListLayout.setData(data);
                }
            });
        });

        MessageViewModel messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        LiveDataBus messageChange = messageViewModel.getMessageChange();
        messageChange.with(DemoConstant.NOTIFY_CHANGE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.MESSAGE_CHANGE_CHANGE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.GROUP_CHANGE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.CHAT_ROOM_CHANGE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.CONVERSATION_DELETE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.CONVERSATION_READ, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.CONTACT_CHANGE, EaseEvent.class).observe(getViewLifecycleOwner(), this::loadList);
        messageChange.with(DemoConstant.MESSAGE_CALL_SAVE, Boolean.class).observe(getViewLifecycleOwner(), this::refreshList);
        messageChange.with(DemoConstant.MESSAGE_NOT_SEND, Boolean.class).observe(getViewLifecycleOwner(), this::refreshList);
    }

    @Override
    protected void loadData() {
        if (PreferenceManager.getInstance().getAccountIsLogin()) {
            if(DemoHelper.getInstance().isFirstInstall() && EMClient.getInstance().chatManager().getAllConversations().isEmpty()) {
                mViewModel.fetchConversationsFromServer();
            }else {
                conversationListLayout.loadDefaultData();
            }
        }else {

        }
    }

    @Override
    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        View errorView = inflater.inflate(R.layout.fragment_error, container, false);
        mLoginTV=errorView.findViewById(R.id.network_tips);

        mLoginTV.setText("你还未登录，请重新登录");
        return errorView;
    }

    /**
     * 会话条目点击事件
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        Object item = conversationListLayout.getItem(position).getInfo();
        if(item instanceof EMConversation) {
            if(EaseSystemMsgManager.getInstance().isSystemConversation((EMConversation) item)) {
                //SystemMsgsActivity.actionStart(mContext);
            }else {
                ChatActivity.actionStart(mContext, ((EMConversation)item).conversationId(), EaseCommonUtils.getChatType((EMConversation) item));
            }
        }
    }

    /**
     * 会话长按菜单条目点击事件
     * @param item
     * @param position
     */
    @Override
    public boolean onMenuItemClick(MenuItem item, int position) {
        EaseConversationInfo info = conversationListLayout.getItem(position);
        Object object = info.getInfo();

        if(object instanceof EMConversation) {
            switch (item.getItemId()) {
                case R.id.action_con_make_top :
                    conversationListLayout.makeConversationTop(position, info);
                    return true;
                case R.id.action_con_cancel_top :
                    conversationListLayout.cancelConversationTop(position, info);
                    return true;
                case R.id.action_con_delete :
                    showDeleteDialog(position, info);
                    return true;
            }
        }
        return false;
    }

    private void showDeleteDialog(int position, EaseConversationInfo info) {
        new SimpleDialogFragment.Builder((BaseActivity) mContext)
                .setTitle(R.string.delete_conversation)
                .setOnConfirmClickListener(R.string.delete, new DemoDialogFragment.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(View view) {
                        conversationListLayout.deleteConversation(position, info);
                        LiveDataBus.get().with(DemoConstant.CONVERSATION_DELETE).postValue(new EaseEvent(DemoConstant.CONVERSATION_DELETE, EaseEvent.TYPE.MESSAGE));
                    }
                })
                .showCancelButton(true)
                .show();
    }

    /**
     * 会话长按菜单显示前的监听事件，可以对PopupMenu增加条目{@link EaseConversationListLayout#addItemMenu(int, int, int, String)}，
     * 隐藏或者显示条目{@link EaseConversationListLayout#findItemVisible(int, boolean)}
     * @param menuHelper
     * @param position
     */
    @Override
    public void onMenuPreShow(EasePopupMenuHelper menuHelper, int position) {

    }


    @Override
    public void loadDataFinish(List<EaseConversationInfo> data) {
        finishRefresh();
    }

    @Override
    public void loadDataFail(String message) {
        finishRefresh();
    }

    /**
     * 停止刷新
     */
    public void finishRefresh() {
        if(!mContext.isFinishing() && srlRefresh != null) {
            runOnUiThread(()->srlRefresh.setRefreshing(false));
        }
    }

    @Override
    public void notifyItemChange(int position) {
        LiveDataBus.get().with(DemoConstant.MESSAGE_CHANGE_CHANGE).postValue(new EaseEvent(DemoConstant.MESSAGE_CHANGE_CHANGE, EaseEvent.TYPE.MESSAGE));
    }

    @Override
    public void notifyAllChange() {

    }

    @Override
    public void notifyItemRemove(int position) {

    }

    @Override
    public void onRefresh() {
        conversationListLayout.loadDefaultData();
    }

    /**
     * 切换到UI线程
     * @param runnable
     */
    public void runOnUiThread(Runnable runnable) {
        EaseThreadManager.getInstance().runOnMainThread(runnable);
    }

    /**
     * 解析Resource<T>
     * @param response
     * @param callback
     * @param <T>
     */
    public <T> void parseResource(Resource<T> response, @NonNull OnResourceParseCallback<T> callback) {
        if(mContext instanceof BaseInitActivity) {
            ((BaseInitActivity) mContext).parseResource(response, callback);
        }
    }

    private void loadList(EaseEvent change) {
        if(change == null) {
            return;
        }
        if(change.isMessageChange() || change.isNotifyChange()
                || change.isGroupLeave() || change.isChatRoomLeave()
                || change.isContactChange()
                || change.type == EaseEvent.TYPE.CHAT_ROOM || change.isGroupChange()) {
            conversationListLayout.loadDefaultData();
        }
    }

    private void refreshList(Boolean event) {
        if(event == null) {
            return;
        }
        if(event) {
            conversationListLayout.loadDefaultData();
        }
    }

    //添加好友
    @Override
    public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu) {
        menu.findItem(R.id.action_friend).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_parent_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId()==R.id.action_friend) {
            AddContactActivity.startAction(mContext, SearchType.CHAT);
        }
        return true;
    }
}
