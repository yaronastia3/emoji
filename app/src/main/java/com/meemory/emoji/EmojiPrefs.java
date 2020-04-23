package com.meemory.emoji;

import android.content.Context;
import android.content.SharedPreferences;

class EmojiPrefs {
    private static String emojiPrefsdata = "EmojiPrefsdata";
    private SharedPreferences preferences;
    EmojiPrefs(Context context){
        String NAME = "EmojiPrefs";
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
    void EmojiPrefsdata(String id){
        preferences.edit().putString(EmojiPrefs.emojiPrefsdata, id).apply();
    }
    String EmojiPrefsdata(){
        return preferences.getString(emojiPrefsdata, "");
    }
}
