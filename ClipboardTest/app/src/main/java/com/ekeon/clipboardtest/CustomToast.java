package com.ekeon.clipboardtest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ekeon on 2016. 1. 12..
 */
public class CustomToast extends Toast {

    private Context context;

    public CustomToast(Context context) {
        super(context);
        this.context = context;
    }

    public void showToast(String text, int duration) {
        LayoutInflater layoutInflater;
        View rootView;

        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = layoutInflater.inflate(R.layout.custom_toast, null);

        TextView textView = (TextView) rootView.findViewById(R.id.tv_text);
        textView.setText(text);

        show(this, rootView, duration);
    }

    private void show(Toast toast, View v, int duration) {
        toast.setGravity(Gravity.TOP, 0, 300);
        toast.setDuration(duration);
        toast.setView(v);
        toast.show();
    }
}
