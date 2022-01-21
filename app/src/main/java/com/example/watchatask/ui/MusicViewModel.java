package com.example.watchatask.ui;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.watchatask.model.DataManager;
import com.example.watchatask.model.Music;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *  음악 데이터를 관리하는 ViewModel
 *  음악 데이터를 불러오고 데이터의 변화를 SharedPreference에 업데이트함
 */
public class MusicViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Music>> musics;
    private MutableLiveData<ArrayList<Music>> favorites;
    private MutableLiveData<Music> toggledData;
    private int toggledMusicPosition;
    private int toggledFavoritePosition;

    public MusicViewModel() {
        toggledData = new MutableLiveData<>();
        toggledData.setValue(null);
    }

    public int getToggledMusicPosition() {
        return toggledMusicPosition;
    }

    public int getToggledFavoritePosition() {
        return toggledFavoritePosition;
    }

    public LiveData<ArrayList<Music>> getMusics(Context context) {
        if (musics == null) {
            musics = new MutableLiveData<>();
            requestData(musics, context);
        }
        return musics;
    }

    public LiveData<ArrayList<Music>> getFavorites() {
        if (favorites == null) {
            favorites = new MutableLiveData<>();
            if (musics.getValue() != null) {
                ArrayList<Music> _favorites = new ArrayList<>();
                for (Music music : musics.getValue()) {
                    if (music.isFavorite()) _favorites.add(music);
                }
                favorites.setValue(_favorites);
            }
        }
        return favorites;
    }

    public LiveData<Music> getToggledData() {
        return toggledData;
    }

    // MusicList에서 toggle
    public void toggleInMusic(Music music) {
        toggledMusicPosition = musics.getValue().indexOf(music);
        music.toggle();
        DataManager.loadData().setFavorite(music.getId(), music.isFavorite());
        if (music.isFavorite()) {
            favorites.getValue().add(music);
            toggledFavoritePosition = favorites.getValue().size() - 1;
        }
        else {
            toggledFavoritePosition = favorites.getValue().indexOf(music);
            favorites.getValue().remove(toggledFavoritePosition);
        }
        toggledData.setValue(music);
    }

    // FavoriteList에서 toggle
    public void toggleInFavorite(Music music) {
        toggledFavoritePosition = favorites.getValue().indexOf(music);
        favorites.getValue().remove(music);
        music.toggle();
        DataManager.loadData().setFavorite(music.getId(), music.isFavorite());
        toggledMusicPosition = musics.getValue().indexOf(music);
        toggledData.setValue(music);
    }

    // api로부터 data 요청
    private void requestData(MutableLiveData<ArrayList<Music>> musics, Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="https://itunes.apple.com/search?term=greenday&entity=song";

        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    ArrayList<Music> musicArrayList = new ArrayList<>();
                    System.out.println(response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray result = jsonResponse.getJSONArray("results");
                        for (int i = 0; i < result.length(); i++) {
                            JSONObject jsonObject = result.getJSONObject(i);
                            int id = jsonObject.getInt("trackId");
                            musicArrayList.add(new Music(
                                    id,
                                    jsonObject.getString("trackName"),
                                    jsonObject.getString("collectionName"),
                                    jsonObject.getString("artistName"),
                                    jsonObject.getString("artworkUrl60"),
                                    DataManager.loadData().isFavorite(id)));
                        }
                        musics.setValue(musicArrayList);
                        favorites = new MutableLiveData<>();
                        ArrayList<Music> _favorites = new ArrayList<>();
                        for (Music music : musicArrayList) {
                            if (music.isFavorite()) _favorites.add(music);
                        }
                        favorites.setValue(_favorites);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    System.out.println("Error occur");
                });

        queue.add(request);
    }
}