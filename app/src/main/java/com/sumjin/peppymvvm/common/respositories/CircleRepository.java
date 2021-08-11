package com.sumjin.peppymvvm.common.respositories;

import androidx.lifecycle.MutableLiveData;

import com.sumjin.peppymvvm.common.model.Api;
import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;
import com.sumjin.peppymvvm.common.util.LogUtils;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.RetrofitManager;
import com.sumjin.peppymvvm.common.util.UrlUtils;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CircleRepository {

    public PageResponse getGoodsList(String username, int page,boolean isMore){
        PageResponse pageResponse = new PageResponse();

        final MutableLiveData<Resource<DynamicInfo>> data= new MutableLiveData<>();
        if (isMore) {
            data.setValue(Resource.<DynamicInfo>moreLoading());
        }else {
            data.setValue(Resource.<DynamicInfo>loading());
        }
        pageResponse.setLiveData(data);

        Call<DynamicInfo> task = createTask(username, page);

        Integer currentPage = page;

        Integer finalCurrentPage = currentPage-1;

        task.enqueue(new Callback<DynamicInfo>() {
            @Override
            public void onResponse(Call<DynamicInfo> call, Response<DynamicInfo> response) {
                int code = response.code();
                if (code== HttpURLConnection.HTTP_OK) {
                    DynamicInfo dynamicInfo = response.body();
                    if (dynamicInfo == null) {
                        if (isMore) {
                            data.setValue(Resource.<DynamicInfo>loadEmpty());
                            pageResponse.setCurrentPage(finalCurrentPage);
                        }else {
                            data.setValue(Resource.<DynamicInfo>empty());
                        }
                        pageResponse.setLiveData(data);
                        return;
                    }
                    if (isMore) {
                        data.setValue(Resource.loadMore(dynamicInfo));
                        pageResponse.setCurrentPage(currentPage);
                    }else {
                        data.setValue(Resource.content(dynamicInfo));
                    }
                    pageResponse.setLiveData(data);
                }
                if (isMore) {
                    data.setValue(Resource.<DynamicInfo>loadError(new Throwable()));
                    pageResponse.setCurrentPage(finalCurrentPage);

                } else {
                    data.setValue(Resource.<DynamicInfo>error(new Throwable()));
                }
                pageResponse.setLiveData(data);

            }

            @Override
            public void onFailure(Call<DynamicInfo> call, Throwable t) {
                if (isMore) {
                    data.setValue(Resource.<DynamicInfo>loadError(t));
                    pageResponse.setCurrentPage(finalCurrentPage);

                } else {
                    data.setValue(Resource.<DynamicInfo>error(t));
                }
                pageResponse.setLiveData(data);
            }
        });

        return pageResponse;
    }

    private Call<DynamicInfo> createTask(String username, int page) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String url = UrlUtils.createCirclePagerUrl(username, page);
        LogUtils.d(this,url+"");
        return api.getDynamicInfo(url);
    }
}
