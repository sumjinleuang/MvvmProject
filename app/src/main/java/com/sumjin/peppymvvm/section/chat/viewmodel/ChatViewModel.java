package com.sumjin.peppymvvm.section.chat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hyphenate.chat.EMChatRoom;
import com.hyphenate.chat.EMClient;
import com.sumjin.peppymvvm.common.livedatas.SingleSourceLiveData;
import com.sumjin.peppymvvm.common.net.Resource;
import com.sumjin.peppymvvm.common.respositories.EMChatManagerRepository;

import org.jetbrains.annotations.NotNull;

public class ChatViewModel extends ConversationListViewModel{

    private EMChatManagerRepository chatManagerRepository;
    private SingleSourceLiveData<Resource<EMChatRoom>> chatRoomObservable;
    private SingleSourceLiveData<Resource<Boolean>> makeConversationReadObservable;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        chatManagerRepository = new EMChatManagerRepository();
        chatRoomObservable = new SingleSourceLiveData<>();
        makeConversationReadObservable = new SingleSourceLiveData<>();
    }

    public LiveData<Resource<EMChatRoom>> getChatRoomObservable() {
        return chatRoomObservable;
    }



    public void makeConversationReadByAck(String conversationId) {
        makeConversationReadObservable.setSource(chatManagerRepository.makeConversationReadByAck(conversationId));
    }

    public LiveData<Resource<Boolean>> getMakeConversationReadObservable() {
        return makeConversationReadObservable;
    }
}
