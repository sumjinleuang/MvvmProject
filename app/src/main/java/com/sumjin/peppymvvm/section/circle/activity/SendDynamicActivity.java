package com.sumjin.peppymvvm.section.circle.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.util.Status;
import com.sumjin.peppymvvm.section.base.BaseInitActivity;
import com.sumjin.peppymvvm.section.circle.adapter.GridImageAdapter;
import com.sumjin.peppymvvm.common.util.Constants;
import com.sumjin.peppymvvm.common.util.GlideEngine;
import com.sumjin.peppymvvm.common.util.ScreenUtils;
import com.sumjin.peppymvvm.common.view.FullyGridLayoutManager;
import com.sumjin.peppymvvm.common.view.GridSpacingItemDecoration;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;

public class SendDynamicActivity extends BaseInitActivity {
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.send_location)
    public View mLocationBtn;

    @BindView(R.id.send_notify)
    public View mNotifyBtn;

    @BindView(R.id.send_iswatch)
    public View mWatchBtn;

    @BindView(R.id.circle_content_list)
    public RecyclerView mContentList;

    @BindView(R.id.circle_video_list)
    public RecyclerView mVideoList;

    private static final String TAG = "SendDynamicActivity";
    private MenuItem mItem;
    private GridImageAdapter mImageAdapter;
    private GridImageAdapter mVideoAdapter;
    private PictureParameterStyle mPictureParameterStyle;


    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initView() {
        setStatus(Status.SUCCESS);
        //?????????toolbar
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //??????????????????????????????
        mVideoAdapter = new GridImageAdapter(this, onAddVideoClickListener);
        mImageAdapter = new GridImageAdapter(this, onAddPicClickListener);
        int type =  getIntent().getIntExtra(Constants.KET_TYPE_DYNAMIC,0);
        switch (type){
            case R.id.send_txt:
                setTitle("????????????");
                initTextView();
                break;
            case R.id.send_image:
                setTitle("????????????");
                initImageView();
                break;
            case R.id.send_video:
                setTitle("????????????");
                initVideoView();
                break;
        }


    }

    private void initVideoView() {
        mContentList.setVisibility(View.GONE);
        mVideoList.setVisibility(View.VISIBLE);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                1, GridLayoutManager.VERTICAL, false);
        mVideoList.setLayoutManager(manager);
        //????????????
        mVideoList.addItemDecoration(new GridSpacingItemDecoration(1,
                ScreenUtils.dip2px(this, 8), false));

        maxSelectNum=1;
        mVideoAdapter.setSelectMax(maxSelectNum);
        mVideoList.setAdapter(mVideoAdapter);
    }

    private void initImageView() {
        mVideoList.setVisibility(View.GONE);
        mContentList.setVisibility(View.VISIBLE);

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                3, GridLayoutManager.VERTICAL, false);
        mContentList.setLayoutManager(manager);
        //????????????
        mContentList.addItemDecoration(new GridSpacingItemDecoration(3,
                ScreenUtils.dip2px(this, 8), false));

        maxSelectNum=9;
        mImageAdapter.setSelectMax(maxSelectNum);
        mContentList.setAdapter(mImageAdapter);
    }

    private void initTextView() {
        mContentList.setVisibility(View.GONE);
        mVideoList.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        // ?????????????????????????????????

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mWatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mImageAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mImageAdapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                    PictureSelector.create(SendDynamicActivity.this)
                            .themeStyle(R.style.picture_default_style) // xml????????????
                            .setPictureUIStyle(mSelectorUIStyle)
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                            .isNotPreviewDownload(true)// ????????????????????????????????????
                            .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                            .openExternalPreview(position, selectList);

            }
        });

        mVideoAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mImageAdapter.getData();
            if (selectList.size() > 0) {
                LocalMedia media = selectList.get(position);
                String mimeType = media.getMimeType();
                int mediaType = PictureMimeType.getMimeType(mimeType);
                PictureSelector.create(SendDynamicActivity.this)
                            .themeStyle(R.style.picture_default_style)
                            .setPictureUIStyle(mSelectorUIStyle)
                            .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
            }
        });


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_send_dynamic;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else if (item.getItemId() == R.id.send){
            sendDynamic();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendDynamic() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_send_menu,menu);
        mItem = menu.findItem(R.id.send);
       // mItem.setVisible(false);
        return true;
    }

    private int maxSelectNum;
    private int chooseMode ;
    private int animationMode = AnimationType.DEFAULT_ANIMATION;
    private PictureWindowAnimationStyle mWindowAnimationStyle = PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle();
    private PictureSelectorUIStyle mSelectorUIStyle=PictureSelectorUIStyle.ofDefaultStyle();

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            chooseMode=PictureMimeType.ofImage();
            // ???????????? ??????????????????????????????api????????????
            PictureSelector.create(SendDynamicActivity.this)
                        .openGallery(chooseMode)// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                        .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                        //.theme(themeId)// ?????????????????? ???????????? values/styles   ?????????R.style.picture.white.style v2.3.3??? ????????????setPictureStyle()????????????
                        .setPictureUIStyle(mSelectorUIStyle)
                        .setPictureWindowAnimationStyle(mWindowAnimationStyle)// ?????????????????????????????????
                        .isWeChatStyle(true)// ????????????????????????????????????
                        .isUseCustomCamera(true)// ???????????????????????????
                        .setRecyclerAnimationMode(animationMode)// ??????????????????
                        //.isWithVideoImage(true)// ?????????????????????????????????,??????ofAll???????????????
                        .isMaxSelectEnabledMask(true)// ?????????????????????????????????????????????????????????
                        .maxSelectNum(maxSelectNum)// ????????????????????????
                        .minSelectNum(1)// ??????????????????
                        //.maxVideoSelectNum(1) // ????????????????????????
                        //.minVideoSelectNum(1)// ????????????????????????
                        .imageSpanCount(4)// ??????????????????
                        .isReturnEmpty(false)// ????????????????????????????????????????????????
                        .closeAndroidQChangeWH(true)//??????????????????????????????????????????,?????????true
                        .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// ??????????????????????????????????????????,?????????false
                        .isAndroidQTransform(false)// ??????????????????Android Q ??????????????????????????????????????????compress(false); && .isEnableCrop(false);??????,????????????
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                        .isOriginalImageControl(false)// ????????????????????????????????????????????????true?????????????????????????????????????????????????????????????????????????????????
                        .selectionMode(PictureConfig.MULTIPLE)// ?????? or ??????
                        .isSingleDirectReturn(true)// ????????????????????????????????????PictureConfig.SINGLE???????????????
                        .isPreviewImage(true)// ?????????????????????
                        .isCamera(true)// ????????????????????????
                        .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                        .isCompress(true)// ????????????
                        .synOrAsy(false)//??????true?????????false ?????? ????????????
                        .isGif(false)// ????????????gif??????
                        .selectionData(mImageAdapter.getData())// ????????????????????????
                        .cutOutQuality(90)// ?????????????????? ??????100
                        .minimumCompressSize(100)// ????????????kb??????????????????
                        .forResult(new MyResultCallback(mImageAdapter));

        }
    };

    private GridImageAdapter.onAddPicClickListener onAddVideoClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            chooseMode=PictureMimeType.ofVideo();
            // ???????????? ??????????????????????????????api????????????
            PictureSelector.create(SendDynamicActivity.this)
                    .openGallery(chooseMode)// ??????.PictureMimeType.ofAll()?????????.ofImage()?????????.ofVideo()?????????.ofAudio()
                    .imageEngine(GlideEngine.createGlideEngine())// ??????????????????????????????????????????
                    //.theme(themeId)// ?????????????????? ???????????? values/styles   ?????????R.style.picture.white.style v2.3.3??? ????????????setPictureStyle()????????????
                    .setPictureUIStyle(mSelectorUIStyle)
                    .setPictureWindowAnimationStyle(mWindowAnimationStyle)// ?????????????????????????????????
                    .isWeChatStyle(true)// ????????????????????????????????????
                    .isUseCustomCamera(true)// ???????????????????????????
                    .setRecyclerAnimationMode(animationMode)// ??????????????????
                    .isWithVideoImage(true)// ?????????????????????????????????,??????ofAll???????????????
                    .isMaxSelectEnabledMask(true)// ?????????????????????????????????????????????????????????
                    .maxVideoSelectNum(1) // ????????????????????????
                    .minVideoSelectNum(1)// ????????????????????????
                    .imageSpanCount(4)// ??????????????????
                    .isReturnEmpty(false)// ????????????????????????????????????????????????
                    .closeAndroidQChangeWH(true)//??????????????????????????????????????????,?????????true
                    .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// ??????????????????????????????????????????,?????????false
                    .isAndroidQTransform(false)// ??????????????????Android Q ??????????????????????????????????????????compress(false); && .isEnableCrop(false);??????,????????????
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// ????????????Activity????????????????????????????????????
                    .isOriginalImageControl(false)// ????????????????????????????????????????????????true?????????????????????????????????????????????????????????????????????????????????
                    .selectionMode(PictureConfig.SINGLE)// ?????? or ??????
                    .isSingleDirectReturn(true)// ????????????????????????????????????PictureConfig.SINGLE???????????????
                    .isPreviewImage(true)// ?????????????????????
                    .isPreviewVideo(true)// ?????????????????????
                    .isEnablePreviewAudio(true) // ?????????????????????
                    .isCamera(true)// ????????????????????????
                    .isZoomAnim(true)// ?????????????????? ???????????? ??????true
                    .isCompress(true)// ????????????
                    .synOrAsy(false)//??????true?????????false ?????? ????????????
                    .isGif(false)// ????????????gif??????
                    .selectionData(mImageAdapter.getData())// ????????????????????????
                    .cutOutQuality(90)// ?????????????????? ??????100
                    .minimumCompressSize(100)// ????????????kb??????????????????
                    .forResult(new MyResultCallback(mVideoAdapter));

        }
    };

    /**
     * ??????????????????
     */
    private static class MyResultCallback implements OnResultCallbackListener<LocalMedia> {

        private WeakReference<GridImageAdapter> mAdapterWeakReference;

        public MyResultCallback(GridImageAdapter adapter) {
            super();
            this.mAdapterWeakReference = new WeakReference<>(adapter);
        }

        @Override
        public void onResult(List<LocalMedia> result) {
            for (LocalMedia media : result) {
                Log.i(TAG, "????????????:" + media.isCompressed());
                Log.i(TAG, "??????:" + media.getCompressPath());
                Log.i(TAG, "??????:" + media.getPath());
                Log.i(TAG, "????????????:" + media.getRealPath());
                Log.i(TAG, "????????????:" + media.isCut());
                Log.i(TAG, "??????:" + media.getCutPath());
                Log.i(TAG, "??????????????????:" + media.isOriginal());
                Log.i(TAG, "????????????:" + media.getOriginalPath());
                Log.i(TAG, "Android Q ??????Path:" + media.getAndroidQToPath());
                Log.i(TAG, "??????: " + media.getWidth() + "x" + media.getHeight());
                Log.i(TAG, "Size: " + media.getSize());
                // TODO ????????????PictureSelectorExternalUtils.getExifInterface();??????????????????????????????????????????????????????????????????????????????
            }
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }

        @Override
        public void onCancel() {
            Log.i(TAG, "PictureSelector Cancel");
        }
    }


    public Context getContext() {
        return this;
    }
}
