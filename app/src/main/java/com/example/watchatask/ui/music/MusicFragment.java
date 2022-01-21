package com.example.watchatask.ui.music;

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
 *  전체 곡 목록을 표시하는 Fragment
 */
public class MusicFragment extends Fragment {

    private MusicViewModel musicViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.musicListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        musicViewModel = new ViewModelProvider(requireActivity()).get(MusicViewModel.class);

        musicViewModel.getMusics(this.getContext()).observe(getViewLifecycleOwner(), musics -> {
            MusicAdapter adapter = new MusicAdapter((music) -> musicViewModel.toggleInMusic(music), musics);
            recyclerView.setAdapter(adapter);
        });

        // RecyclerView에서 업데이트된 데이터만을 반영함
        musicViewModel.getToggledData().observe(getViewLifecycleOwner(), music -> {
            if (recyclerView.getAdapter() != null && music != null) {
                recyclerView.getAdapter().notifyItemChanged(musicViewModel.getToggledMusicPosition());
            }
        });
        return root;
    }
}