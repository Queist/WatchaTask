package com.example.watchatask.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  SharedPreference를 관리하는 class
 *  Singleton Pattern으로 작성됨
 */
public class DataManager {

    private static Data data = null;

    public static Data loadData() {
        return data;
    }

    public static void init(Context context) {
        data = new Data(context);
    }

    public static class Data {

        private SharedPreferences sharedPreferences;


        Data(Context context) {
            sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        }

        public void setFavorite(int id, boolean isFavorite) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(String.format("%d", id), isFavorite);
            editor.apply();
        }

        public boolean isFavorite(int id) {
            return sharedPreferences.getBoolean(String.format("%d", id), false);
        }
    }
}
