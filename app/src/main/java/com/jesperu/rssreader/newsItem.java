package com.jesperu.rssreader;

import android.widget.TextView;

public class newsItem {
    private String mHeadline;

    public newsItem(String headline){
        mHeadline = headline;
    }

    public String getmHeadline(){
        return mHeadline;
    }

    public void setmHeadline(String h){
        TextView textView = textView.findViewById();
        textView.setText(h);
    }
}
