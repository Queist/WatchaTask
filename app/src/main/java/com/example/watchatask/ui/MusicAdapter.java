package com.example.watchatask.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.watchatask.R;
import com.example.watchatask.model.Music;
import com.example.watchatask.utils.EventHandler;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

/**
 *  곡들을 표현하기 위한 RecyclerView Adapter
 *  두 Fragment에서 동일한 Adapter를 사용
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private ArrayList<Music> musics;
    private EventHandler e;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton favoriteToggle;
        private final ImageView artwork;
        private final TextView trackName, artistName, collectionName;

        public ViewHolder(View view) {
            super(view);
            favoriteToggle = view.findViewById(R.id.favoriteToggle);
            artwork = view.findViewById(R.id.artwork);
            trackName = view.findViewById(R.id.trackName);
            artistName = view.findViewById(R.id.artistName);
            collectionName = view.findViewById(R.id.collectionName);
        }

        /**
         * @param e ViewModel이 관리하는 click handler를 인자로 받아 binding
         */
        public void bindData(EventHandler e, Music music) {
            Glide.with(this.itemView).load(music.getArtworkURL()).into(artwork);
            favoriteToggle.setImageResource(music.isFavorite() ? R.drawable.ic_star_on_30dp : R.drawable.ic_star_off_30dp);
            trackName.setText(music.getTrack());
            artistName.setText(music.getArtist());
            collectionName.setText(music.getCollection());
            favoriteToggle.setOnClickListener((view) -> e.onClickStar(music));
        }
    }

    public MusicAdapter(EventHandler e, ArrayList<Music> musics) {
        this.e = e;
        this.musics = musics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bindData(e, musics.get(position));
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }
}
