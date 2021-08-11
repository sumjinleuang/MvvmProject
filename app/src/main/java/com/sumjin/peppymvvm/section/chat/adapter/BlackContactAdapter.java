package com.sumjin.peppymvvm.section.chat.adapter;

import com.sumjin.peppymvvm.R;

public class BlackContactAdapter extends ContactListAdapter{

    @Override
    public int getEmptyLayoutId() {
        return R.layout.ease_layout_default_no_data;
    }
}
