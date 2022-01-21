package com.example.watchatask.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchatask.R;
import com.example.watchatask.ui.MusicAdapter;
import com.example.watchatask.ui.MusicViewModel;


/**
 *  선호하는 곡 목록을 표시하는 list
 */
public class FavoriteFragment extends Fragment {

    private MusicViewModel musicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.favoriteListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        musicViewModel.getFavorites().observe(getViewLifecycleOwner(), musics -> {
            MusicAdapter adapter = new MusicAdapter((music) -> musicViewModel.toggleInFavorite(music), musics);
            recyclerView.setAdapter(adapter);
        });

        // RecyclerView에서 업데이트된 데이터만을 반영함
        musicViewModel.getToggledData().observe(getViewLifecycleOwner(), music -> {
            if (recyclerView.getAdapter() != null && music != null) {
                if (music.isFavorite()) recyclerView.getAdapter()
                        .notifyItemInserted(musicViewModel.getToggledFavoritePosition());
                else recyclerView.getAdapter().notifyItemRemoved(musicViewModel.getToggledFavoritePosition());
            }
        });
        return root;
    }
}