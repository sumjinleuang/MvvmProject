package com.sumjin.peppymvvm.section.home.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.model.domain.OnSellContent;
import com.sumjin.peppymvvm.common.util.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.InnerHolder> {

    private List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> mData=new ArrayList<>();
    private OnSellPageItemClickListener mContentItemClickListener =null;

    @NonNull
    @Override
    public GoodsAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_content,parent,false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.InnerHolder holder, int position) {

        OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean itemData = mData.get(position);

        holder.setData(itemData);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContentItemClickListener != null) {
                    mContentItemClickListener.onSellItemClick(itemData);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> dataBeans) {
        mData.clear();
        mData.addAll(dataBeans);
        notifyDataSetChanged();
    }

    public void setMoreLoaded(List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> moreResult) {
        int oldDataSize = mData.size();
        List<OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean> moreData = moreResult;
        mData.addAll(moreData);
        notifyItemChanged(oldDataSize-1,moreData.size());
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.on_sell_cover)
        public ImageView cover;

        @BindView(R.id.on_sell_content_title_tv)
        public TextView titleTv;

        @BindView(R.id.on_sell_origin_prise_tv)
        public TextView originalPriseTv;

        @BindView(R.id.on_sell_off_prise_tv)
        public TextView offPriseTv;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void setData(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data) {
            titleTv.setText(data.getTitle());
            // LogUtils.d(this,"pic url --- > " + data.getPict_url());
            String coverPath = UrlUtils.getCoverPath(data.getPict_url());
            Glide.with(cover.getContext()).load(coverPath).into(cover);
            String originalPrise = data.getZk_final_price();
            originalPriseTv.setText("￥" + originalPrise + " ");
            //设置下划线
            originalPriseTv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            int couponAmount = data.getCoupon_amount();
            float originPriseFloat = Float.parseFloat(originalPrise);
            float finalPrise = originPriseFloat - couponAmount;
            offPriseTv.setText("券后价：" + String.format("%.2f",finalPrise));
        }



    }

    public void setOnSellPageItemClickListener(OnSellPageItemClickListener listener) {
        this.mContentItemClickListener = listener;
    }

    public interface OnSellPageItemClickListener {
        void onSellItemClick(OnSellContent.DataBean.TbkDgOptimusMaterialResponseBean.ResultListBean.MapDataBean data);
    }
}
