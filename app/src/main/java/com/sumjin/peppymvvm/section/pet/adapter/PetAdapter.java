package com.sumjin.peppymvvm.section.pet.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumjin.peppymvvm.R;
import com.sumjin.peppymvvm.common.model.domain.MsgPet;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.InnerHolder> {

    private List<MsgPet.DatasBean> mData = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull InnerHolder holder, int position) {
        MsgPet.DatasBean itemData = mData.get(position);
        holder.setData(itemData);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<MsgPet.DatasBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        //pet_cover
        //pet_name_tv
        //pet_info_tv
        //pet_detail_tv
        @BindView(R.id.pet_cover)
        public ImageView coverPet;

        @BindView(R.id.pet_name_tv)
        public TextView nameTv;

        @BindView(R.id.pet_info_tv)
        public TextView petInfoTv;

        @BindView(R.id.pet_detail_tv)
        public TextView petDetailTv;

        public InnerHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(MsgPet.DatasBean itemData) {
            nameTv.setText(itemData.getNickname());
            petDetailTv.setText(itemData.getMore_info());
            Glide.with(coverPet.getContext()).load(itemData.getAvatar()).into(coverPet);
        }
    }
}
