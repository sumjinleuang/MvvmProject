package com.sumjin.peppymvvm.section.pet.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sumjin.peppymvvm.common.model.domain.MsgPet;
import com.sumjin.peppymvvm.common.respositories.PetRepository;
import com.sumjin.peppymvvm.common.net.Resource;

import org.jetbrains.annotations.NotNull;

public class PetViewModel extends AndroidViewModel {

    private LiveData<Resource<MsgPet>> mLiveData;
    private final PetRepository mPetRepository;

    public PetViewModel(@NonNull @NotNull Application application) {
        super(application);
        mPetRepository = new PetRepository();
    }

    public void getPetMsg(String username){
        mLiveData = mPetRepository.getPetInfo(username);
    }

    public LiveData<Resource<MsgPet>> getLiveData() {
        return mLiveData;
    }
}
