package com.example.tessav.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tessav.flicks.R;
import com.example.tessav.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.tessav.flicks.R.id.tvOverview;
import static com.example.tessav.flicks.R.id.tvTitle;

/**
 * Created by tessavoon on 9/14/17.
 */


public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView ivMovieImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivMovieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivMovieImage.setImageResource(0);
        String mvImage = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                movie.getPosterPath() : movie.getBackdropPath();
        Picasso.with(getContext()).load(mvImage).into(viewHolder.ivMovieImage);
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
