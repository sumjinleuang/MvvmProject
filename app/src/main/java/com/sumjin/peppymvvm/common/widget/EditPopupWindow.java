package com.sumjin.peppymvvm.common.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sumjin.peppymvvm.R;

public class EditPopupWindow extends PopupWindow {
    private TextView tvSure,tvCancel;
    private EditText etMsg;

    public EditPopupWindow(Context context, EditPopupWindow.ICallListener confirmListener){
        init(context);
        setListener(confirmListener);
    }

    public EditPopupWindow(Context context, EditPopupWindow.ICallListener confirmListener, EditPopupWindow.ICallListener cancelListener){
        init(context);

        setListener(confirmListener,cancelListener);
    }

    public void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_msg,null);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(view);

        etMsg = view.findViewById(R.id.et_msg);

        tvSure = view.findViewById(R.id.tv_sureadd);
        tvCancel = view.findViewById(R.id.tv_canceladd);

    }

    /**
     * 仅带确认回调
     * @param listener
     */
    private void setListener(final EditPopupWindow.ICallListener listener){
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.callBack();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 同时带有确认和取消回调
     * @param listener
     * @param listener2
     */
    private void setListener(final EditPopupWindow.ICallListener listener, final EditPopupWindow.ICallListener listener2){
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.callBack();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener2.callBack();
            }
        });
    }

    public void setEtMsg(String hint,int height){
        etMsg.setHint(hint);
        etMsg.setMinHeight(height);
    }


    public interface ICallListener {
        void callBack();
    }
    public EditText etMsg(){
        return etMsg;
    }
}
