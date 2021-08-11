package com.sumjin.peppymvvm.common.respositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.sumjin.peppymvvm.common.model.Api;
import com.sumjin.peppymvvm.common.model.domain.MsgPet;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.util.RetrofitManager;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetRepository {

    public LiveData<Resource<MsgPet>> getPetInfo(String username){
        final MutableLiveData<Resource<MsgPet>> data =new MediatorLiveData<>();

        data.setValue(Resource.moreLoading());
        
        Call<MsgPet> task = createTask(username);

        task.enqueue(new Callback<MsgPet>() {
            @Override
            public void onResponse(Call<MsgPet> call, Response<MsgPet> response) {
                int code = response.code();
                if (code== HttpURLConnection.HTTP_OK) {
                    MsgPet msgPet = response.body();
                    if (msgPet == null) {
                        data.setValue(Resource.<MsgPet>empty());
                        return;
                    }
                    data.setValue(Resource.content(msgPet));
                }
            }

            @Override
            public void onFailure(Call<MsgPet> call, Throwable t) {
                data.setValue(Resource.<MsgPet>error(t));
            }
        });

        return data;
    }


    private Call<MsgPet> createTask(String username){
        String type ="username";
        return RetrofitManager.getInstance().getRetrofit().create(Api.class).getPetContent(type,username);
    }
}
