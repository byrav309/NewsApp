package com.exmample.android.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.RecyclerViewHolder> {

    private List<NewsDetails> items;
    private Context context;

    public NewsAdapter(List<NewsDetails> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setItems(List<NewsDetails> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        NewsDetails newsDetails = items.get(position);
        holder.author.setText(newsDetails.getAuthor());
        holder.title.setText(newsDetails.getTitle());
        holder.section.setText(newsDetails.getSection());
        holder.date.setText(newsDetails.getDate());
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, section, date;

        public RecyclerViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_tv);
            author = view.findViewById(R.id.author_tv);
            section = view.findViewById(R.id.section_tv);
            date = view.findViewById(R.id.date_tv);
        }
    }
}
