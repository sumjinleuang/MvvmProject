package com.sumjin.peppymvvm.section.login.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.hyphenate.EMError;
import com.hyphenate.easeui.domain.EaseUser;
import com.sumjin.peppymvvm.DemoHelper;
import com.sumjin.peppymvvm.MainActivity;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.interfaceOrImplement.OnResourceParseCallback;
import com.sumjin.peppymvvm.common.util.PreferenceManager;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.common.util.ToastUtil;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.login.viewmodels.LoginFragmentViewModel;
import com.sumjin.peppymvvm.section.login.viewmodels.LoginViewModel;

import butterknife.BindView;

public class LoginActivity extends BaseInitActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.login_username_input_box)
    public EditText mNameEt;

    @BindView(R.id.login_password_input_box)
    public EditText mPwdEt;

    @BindView(R.id.login_in_btn)
    public Button mLoginBtn;

    @BindView(R.id.login_register_btn)
    public Button mRegisterBtn;

    @BindView(R.id.login_skip_btn)
    public Button mSkipBtn;

    private LoginViewModel mViewModel;
    private LoginFragmentViewModel mFragmentViewModel;

    private String mUserName;
    private String mPwd;
    private boolean isTokenFlag =false;//是否是token登录


    @Override
    protected void initView() {
        mFragmentViewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        mFragmentViewModel.getLoginObservable().observe(this, response -> {
            parseResource(response, new OnResourceParseCallback<EaseUser>(true) {
                @Override
                public void onSuccess(EaseUser data) {
                    DemoHelper.getInstance().setAutoLogin(true);
                    PreferenceManager.getInstance().setAccountIsLogin(true);
                    //跳转到主页
                    //startActivity(new Intent(mContext, MainActivity.class));
                    mContext.finish();
                }

                @Override
                public void onError(int code, String message) {
                    super.onError(code, message);
                    if(code == EMError.USER_AUTHENTICATION_FAILED) {
                        ToastUtil.showToast(getResources().getString(R.string.demo_error_user_authentication_failed));
                    }else {
                        ToastUtil.showToast(message);
                    }
                }

                @Override
                public void onLoading(EaseUser data) {
                    super.onLoading(data);
                    showLoading();
                }

                @Override
                public void hideLoading() {
                    super.hideLoading();
                   // LoginFragment.this.dismissLoading();
                }
            });

        });
        setStatus(Status.SUCCESS);
        if(!TextUtils.isEmpty(DemoHelper.getInstance().getCurrentLoginUser())) {
            mNameEt.setText(DemoHelper.getInstance().getCurrentLoginUser());
        }
    }

    private void showLoading() {
        ToastUtil.showToast("加载中。。");
    }


    @Override
    protected void initListener() {
        mRegisterBtn.setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mSkipBtn.setOnClickListener(this);
        mNameEt.addTextChangedListener(this);
        mPwdEt.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(mContext).get(LoginViewModel.class);

    }

    @Override
    protected void initObservable() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_in_btn:
                hideKeyboard();
                if(TextUtils.isEmpty(mUserName) || TextUtils.isEmpty(mPwd)) {
                    ToastUtil.showToast(getResources().getString(R.string.em_login_btn_info_incomplete));
                    return;
                }
                if(!TextUtils.isEmpty(mUserName) && !TextUtils.isEmpty(mPwd) ) {
                    mFragmentViewModel.login(mUserName, mPwd, isTokenFlag);
                }
                break;
            case R.id.login_register_btn:

                break;
            case R.id.login_skip_btn:
                finish();
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mUserName = mNameEt.getText().toString().trim();
        mPwd = mPwdEt.getText().toString().trim();
    }
}
