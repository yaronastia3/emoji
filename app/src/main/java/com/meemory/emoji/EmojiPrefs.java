package com.meemory.emoji;

import android.content.Context;
import android.content.SharedPreferences;

public class EmojiPrefs {
    private static String emojiPrefsdata = "EmojiPrefsdata";
    private SharedPreferences preferences;

    public EmojiPrefs(Context context){
        String NAME = "EmojiPrefs";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void EmojiPrefsdata(String id){
        preferences.edit().putString(EmojiPrefs.emojiPrefsdata, id).apply();
    }

    public String EmojiPrefsdata(){
        return preferences.getString(emojiPrefsdata, "");
    }

}
