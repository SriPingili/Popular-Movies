package com.example.android.popularmovies.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Trailer;

import java.util.ArrayList;

/**
 * Created by sp051821 on 11/12/17.
 */

public class CustomTrailerAdapter extends RecyclerView.Adapter<CustomTrailerAdapter.MovieViewHolder>{


    private ArrayList<Trailer> trailers;
    private Context context;

    public CustomTrailerAdapter(ArrayList<Trailer> trailers, Context context)
    {
        this.trailers = trailers;
        this.context = context;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailor_recycler_view_item,parent,false);
        MovieViewHolder movieViewHolder=new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.textView.setText(trailers.get(position).getVideoName());

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.trailor_name_id);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            watchYoutubeVideo(context,trailers.get(position).getVideoKey());

            Log.v("abcd","position : "+position);
            Toast.makeText(context,trailers.get(position).getVideoKey(),Toast.LENGTH_LONG).show();
        }


        /*
        * source :  https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
        * */
        public  void watchYoutubeVideo(Context context, String id){
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
        }
    }
}
