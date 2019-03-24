package com.jesperu.rssreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<newsItem> mNewsList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public NewsViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textHeadline);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public NewsAdapter(ArrayList<newsItem> newsList) {
        mNewsList = newsList;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsViewHolder nvh = new NewsViewHolder(v, mListener);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        newsItem currentItem = mNewsList.get(position);

        holder.mTextView.setText(currentItem.getmHeadline());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
