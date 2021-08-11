package com.sumjin.peppymvvm.common.model;

import com.sumjin.peppymvvm.common.model.domain.DynamicInfo;
import com.sumjin.peppymvvm.common.model.domain.MsgPet;
import com.sumjin.peppymvvm.common.model.domain.OnSellContent;
import com.sumjin.peppymvvm.common.respositories.ResultData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface Api {

    String URL_SHOP = "url_name:shop";
    String URL_PEPPY = "url_name:peppytest";

    @Headers(URL_PEPPY)
    @GET
    Call<DynamicInfo> getDynamicInfo(@Url String url);

    @Headers(URL_SHOP)
    @GET
    Call<OnSellContent> getOnSellPageContent(@Url String url);

    @Headers(URL_PEPPY)
    @GET("ContactPetList")
    Call<MsgPet>  getPetContent(@Query("type") String type,@Query("value") String value);

}
