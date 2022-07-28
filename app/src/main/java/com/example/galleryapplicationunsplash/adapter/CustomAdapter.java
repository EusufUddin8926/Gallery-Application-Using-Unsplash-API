package com.example.galleryapplicationunsplash.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.galleryapplicationunsplash.FullImageActivity;
import com.example.galleryapplicationunsplash.R;
import com.example.galleryapplicationunsplash.model.ImageSource;


import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder> implements Observer<List<ImageSource>> {

    private ArrayList<ImageSource> sources = new ArrayList<>();
    private Context mContext;

    public CustomAdapter(ArrayList<ImageSource> sources, Context mContext) {
        this.sources = sources;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        CustomHolder customHolder = new CustomHolder(view);
        return customHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {

        Glide.with(mContext).load(sources.get(position).getUrls().getRegular())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, FullImageActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("image", sources.get(position).getUrls().getRegular());
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return sources.size();
    }


    protected class CustomHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public void onChanged(List<ImageSource> newsSource) {
        sources.addAll(newsSource);
        notifyItemRangeInserted(sources.size(), newsSource.size());
    }
}
