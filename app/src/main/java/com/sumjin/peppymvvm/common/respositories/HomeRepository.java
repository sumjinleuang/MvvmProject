package com.sumjin.peppymvvm.common.respositories;

import androidx.lifecycle.MutableLiveData;

import com.sumjin.peppymvvm.common.model.Api;
import com.sumjin.peppymvvm.common.model.domain.OnSellContent;
import com.sumjin.peppymvvm.common.util.LogUtils;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.RetrofitManager;
import com.sumjin.peppymvvm.common.util.UrlUtils;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeRepository {


    public PageResponse getOnSellPageContent(int page,boolean isMore){
        PageResponse pageResponse = new PageResponse();

        final MutableLiveData<Resource<OnSellContent>> data= new MutableLiveData<>();
        if (isMore) {
            data.setValue(Resource.<OnSellContent>moreLoading());
        }else {
            data.setValue(Resource.<OnSellContent>loading());
        }
        pageResponse.setLiveData(data);


        Call<OnSellContent> task = createTask(page);

        Integer currentPage = page;

        Integer finalCurrentPage = currentPage-1;

       task.enqueue(new Callback<OnSellContent>() {
           @Override
           public void onResponse(Call<OnSellContent> call, Response<OnSellContent> response) {
               int code = response.code();
               if (code== HttpURLConnection.HTTP_OK) {
                   OnSellContent onSellContent = response.body();
                   if (onSellContent == null) {
                       if (isMore) {
                           data.setValue(Resource.<OnSellContent>loadEmpty());
                           pageResponse.setCurrentPage(finalCurrentPage);
                       }else {
                           data.setValue(Resource.<OnSellContent>empty());
                       }
                       pageResponse.setLiveData(data);
                       return;
                   }
                   if (isMore) {
                       data.setValue(Resource.loadMore(onSellContent));
                       pageResponse.setCurrentPage(currentPage);
                   }else {
                       data.setValue(Resource.content(onSellContent));
                   }
                   pageResponse.setLiveData(data);
               }
           }

           @Override
           public void onFailure(Call<OnSellContent> call, Throwable t) {
               if (isMore) {
                   data.setValue(Resource.<OnSellContent>loadError(t));
                   pageResponse.setCurrentPage(finalCurrentPage);

               } else {
                   data.setValue(Resource.<OnSellContent>error(t));
               }
               pageResponse.setLiveData(data);
           }
       });

       return pageResponse;

    }

    private Call<OnSellContent> createTask(int page) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);
        String onSellPageUrl = UrlUtils.getOnSellPageUrl(page);
        LogUtils.d(this,onSellPageUrl+"");
        return api.getOnSellPageContent(onSellPageUrl);
    }

}
