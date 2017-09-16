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

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by tessavoon on 9/14/17.
 */


public class MovieArrayAdapter extends ArrayAdapter<Movie> {


//    private static class ViewHolder {
//        ImageView ivMovieImage;
//        TextView tvTitle;
//        TextView tvOverview;
//    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getMovieType().ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return Movie.MovieTypes.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        int type = getItemViewType(position);

        if (type == Movie.MovieTypes.POPULAR.ordinal()) {

            PopularViewHolder popularViewHolder;
            View v = convertView;
            if (v == null) {
                v = getInflatedLayoutForType(type, parent);
                popularViewHolder = new PopularViewHolder(v);
                v.setTag(popularViewHolder);
            } else {
                popularViewHolder = (PopularViewHolder) v.getTag();
            }
            popularViewHolder.ivMovieImage.setImageResource(0);
            String mvImage = movie.getBackdropPath();
            Picasso.with(getContext()).load(mvImage).transform(new RoundedCornersTransformation(10, 10))
                 .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder).into(popularViewHolder.ivMovieImage);
            return v;

        } else if (type == Movie.MovieTypes.NORMAL.ordinal()) {
            NormalViewHolder normalViewHolder;
            View v = convertView;
            if (v == null) {
                v = getInflatedLayoutForType(type, parent);
                normalViewHolder = new NormalViewHolder(v);
                v.setTag(normalViewHolder);
            } else {
                normalViewHolder = (NormalViewHolder) v.getTag();
            }
            normalViewHolder.ivMovieImage.setImageResource(0);
            String mvImage = getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                    movie.getPosterPath() : movie.getBackdropPath();
            Picasso.with(getContext()).load(mvImage).transform(new RoundedCornersTransformation(10, 10))
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder).into(normalViewHolder.ivMovieImage);
            normalViewHolder.tvTitle.setText(movie.getOriginalTitle());
            normalViewHolder.tvOverview.setText(movie.getOverview());
            return v;
        } else {
            return convertView;
        }

    }

    private View getInflatedLayoutForType(int type, ViewGroup parent) {
        if (type == Movie.MovieTypes.POPULAR.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.popular_movie, parent, false);
        } else if (type == Movie.MovieTypes.NORMAL.ordinal()) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
        } else {
            return null;
        }
    }

    static class PopularViewHolder {
        @BindView(R.id.ivMovieImage) ImageView ivMovieImage;

        public PopularViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class NormalViewHolder {
        @BindView(R.id.ivMovieImage) ImageView ivMovieImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public NormalViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
