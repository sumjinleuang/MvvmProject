package com.sumjin.peppymvvm.section.circle.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;
import com.sumjin.peppymvvm.common.util.TimeFormatUtil;
import com.sumjin.peppymvvm.common.util.ToastUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CircleContentAdapter extends RecyclerView.Adapter<CircleContentAdapter.CommonViewHolder> {
    private List<DynamicInfo.DatasBean> mDatas=new ArrayList<>();

    public static final int TYPE_TEXT=1;
    public static final int TYPE_IMAGE=2;
    public static final int TYPE_VIDEO=3;
    public static final int TYPE_ADS=4;
    private OnItemClickListener mItemListener=null;

    @NonNull
    @Override
    public CircleContentAdapter.CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType==TYPE_TEXT){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_content_type, parent, false);
            return new TextViewHolder(itemView);
        }else if (viewType==TYPE_IMAGE){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_content_image, parent, false);
            return new ImageViewHolder(itemView);
        }else if (viewType==TYPE_VIDEO){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_content_video, parent, false);
            return new VideoViewHolder(itemView);
        }else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_content_ads, parent, false);
            return new AdsViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CircleContentAdapter.CommonViewHolder holder, int position) {
        DynamicInfo.DatasBean itemDatas = mDatas.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(itemDatas);
                }
            }
        });

        holder.setData(itemDatas);

        if (holder instanceof TextViewHolder) {

        }else if (holder instanceof ImageViewHolder){

        }else if (holder instanceof VideoViewHolder){

        }else if (holder instanceof AdsViewHolder){

        }


    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        String dynamic_type = mDatas.get(position).getDynamic_type();
        if ("text".equals(dynamic_type)){
            return TYPE_TEXT;
        }else if ("image".equals(dynamic_type)){
            return TYPE_IMAGE;
        }else if ("video".equals(dynamic_type)){
            return TYPE_VIDEO;
        }else if ("ads".equals(dynamic_type)){
            return TYPE_ADS;
        }else {
            return super.getItemViewType(position);
        }

    }

    public void setData(List<DynamicInfo.DatasBean> datasBean) {
        mDatas.clear();
        mDatas.addAll(datasBean);
        notifyDataSetChanged();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.share)
        public ImageView mShareBtn;

        @BindView(R.id.like)
        public ImageView mLikeBtn;

        @BindView(R.id.comment)
        public ImageView mCommentBtn;

        @BindView(R.id.comment_count)
        public TextView mCommentTv;

        @BindView(R.id.share_count)
        public  TextView mShareTv;

        @BindView(R.id.like_count)
        public TextView mLikeTv;

        @BindView(R.id.my_picture)
        public  ImageView mPictureIv;

        @BindView(R.id.username)
        public TextView mUsernameTv;

        @BindView(R.id.dynamic_recommend_time)
        public TextView mRecoTimeTv;

        @BindView(R.id.more_choose)
        public ImageView mMoreChoose;

        @BindView(R.id.tv_context_all)
        TextView mContentText;

        public CommonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            mShareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mCommentBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mLikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            mMoreChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu();
                }
            });
        }

        private void showPopupMenu(){
            PopupMenu popupMenu = new PopupMenu(mMoreChoose.getContext(),mMoreChoose);
            popupMenu.inflate(R.menu.dynamic_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.dynamic_focus:
                        case R.id.dynamic_backlist:
                        case R.id.dynamic_collection:
                            ToastUtil.showToast(menuItem.getTitle()+"");
                            return true;
                        default:
                            //do nothing
                    }

                    return false;
                }
            });
            popupMenu.show();
        }

        public void setData(DynamicInfo.DatasBean itemDatas) {
            mUsernameTv.setText(itemDatas.getNickname());
            try {
                mRecoTimeTv.setText(TimeFormatUtil.formateTime(itemDatas.getCreatetime()));
            } catch (ParseException e) {
                mRecoTimeTv.setText(itemDatas.getCreatetime());
                e.printStackTrace();
            }
            mCommentTv.setText(itemDatas.getComments()+"");
            mLikeTv.setText(itemDatas.getLikes()+"");
            mShareTv.setText(itemDatas.getShares()+"");
            mContentText.setText(itemDatas.getContent());

            if (!TextUtils.isEmpty(itemDatas.getAvatar())) {
                Glide.with(itemView.getContext()).load(itemDatas.getAvatar()).into(this.mPictureIv);
            }else {
                mPictureIv.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }

    class TextViewHolder extends CommonViewHolder {

        public TextViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    class ImageViewHolder extends CommonViewHolder {


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    class VideoViewHolder extends CommonViewHolder {

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    class AdsViewHolder extends CommonViewHolder {

        public AdsViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemListener=listener;
    }

    interface OnItemClickListener{
        void onItemClick(DynamicInfo.DatasBean itemDatas);
    }



}
