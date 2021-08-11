package com.sumjin.peppymvvm.common.view;

import com.sumjin.peppymvvm.section.base.IBaseCallback;
import com.sumjin.peppymvvm.common.model.domain.Titles;

import java.util.List;

public interface ICircleCallback extends IBaseCallback {

    void onCategoriesLoaded(List<Titles> titles);

}
