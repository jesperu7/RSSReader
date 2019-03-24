package com.jesperu.rssreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private ArrayList<newsItem> mNewsList;

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textHeadline);
        }
    }

    public NewsAdapter(ArrayList<newsItem> newsList) {
        mNewsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        newsItem currentItem = mNewsList.get(position);

        holder.mTextView.setText(currentItem.getmHeadline());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
