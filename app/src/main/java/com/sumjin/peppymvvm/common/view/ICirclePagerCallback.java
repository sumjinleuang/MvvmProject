package com.sumjin.peppymvvm.common.view;

import com.sumjin.peppymvvm.section.base.IBaseCallback;
import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;

import java.util.List;

public interface ICirclePagerCallback extends IBaseCallback {
    void onCircleLoaded(List<DynamicInfo.DatasBean> datasBean);

    void onMoreLoaded(List<DynamicInfo.DatasBean> datasBean);

    void onMoreError();

    void onMoreEmpty();

    int getTitleId();
}
