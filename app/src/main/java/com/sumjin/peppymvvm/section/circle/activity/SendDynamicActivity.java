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
        //初始化toolbar
        this.setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //初始化是否九宫图控件
        mVideoAdapter = new GridImageAdapter(this, onAddVideoClickListener);
        mImageAdapter = new GridImageAdapter(this, onAddPicClickListener);
        int type =  getIntent().getIntExtra(Constants.KET_TYPE_DYNAMIC,0);
        switch (type){
            case R.id.send_txt:
                setTitle("发送文字");
                initTextView();
                break;
            case R.id.send_image:
                setTitle("发送图片");
                initImageView();
                break;
            case R.id.send_video:
                setTitle("发送视频");
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
        //设置边距
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
        //设置边距
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
        // 设置拖拽排序控件的代理

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
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            .setPictureUIStyle(mSelectorUIStyle)
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
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
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(SendDynamicActivity.this)
                        .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                        .setPictureUIStyle(mSelectorUIStyle)
                        .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                        .isWeChatStyle(true)// 是否开启微信图片选择风格
                        .isUseCustomCamera(true)// 是否使用自定义相机
                        .setRecyclerAnimationMode(animationMode)// 列表动画效果
                        //.isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                        .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
                        .minSelectNum(1)// 最小选择数量
                        //.maxVideoSelectNum(1) // 视频最大选择数量
                        //.minVideoSelectNum(1)// 视频最小选择数量
                        .imageSpanCount(4)// 每行显示个数
                        .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                        .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                        .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                        .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                        .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                        .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                        .isPreviewImage(true)// 是否可预览图片
                        .isCamera(true)// 是否显示拍照按钮
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .isCompress(true)// 是否压缩
                        .synOrAsy(false)//同步true或异步false 压缩 默认同步
                        .isGif(false)// 是否显示gif图片
                        .selectionData(mImageAdapter.getData())// 是否传入已选图片
                        .cutOutQuality(90)// 裁剪输出质量 默认100
                        .minimumCompressSize(100)// 小于多少kb的图片不压缩
                        .forResult(new MyResultCallback(mImageAdapter));

        }
    };

    private GridImageAdapter.onAddPicClickListener onAddVideoClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            chooseMode=PictureMimeType.ofVideo();
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(SendDynamicActivity.this)
                    .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                    //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                    .setPictureUIStyle(mSelectorUIStyle)
                    .setPictureWindowAnimationStyle(mWindowAnimationStyle)// 自定义相册启动退出动画
                    .isWeChatStyle(true)// 是否开启微信图片选择风格
                    .isUseCustomCamera(true)// 是否使用自定义相机
                    .setRecyclerAnimationMode(animationMode)// 列表动画效果
                    .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                    .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                    .maxVideoSelectNum(1) // 视频最大选择数量
                    .minVideoSelectNum(1)// 视频最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                    .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
                    .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
                    .isAndroidQTransform(false)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                    .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
                    .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                    .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                    .isPreviewImage(true)// 是否可预览图片
                    .isPreviewVideo(true)// 是否可预览视频
                    .isEnablePreviewAudio(true) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .isCompress(true)// 是否压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    .isGif(false)// 是否显示gif图片
                    .selectionData(mImageAdapter.getData())// 是否传入已选图片
                    .cutOutQuality(90)// 裁剪输出质量 默认100
                    .minimumCompressSize(100)// 小于多少kb的图片不压缩
                    .forResult(new MyResultCallback(mVideoAdapter));

        }
    };

    /**
     * 返回结果回调
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
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "绝对路径:" + media.getRealPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
                Log.i(TAG, "Size: " + media.getSize());
                // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
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
