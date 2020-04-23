package com.meemory.emoji;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;


import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

class Tool {
    static void gettingResult(String var1, Activity var2) {
        EmojiPrefs EmojiPrefs = new EmojiPrefs(var2);
        String link = "http://" + inputWord(var1, "$");
        EmojiPrefs.EmojiPrefsdata(link);

        var2.startActivity(new Intent(var2,  MainActivity.class));
        var2.finish();

        new Thread(() -> new SchedulerHelper().messageSchedule(var2)).start();
    }

    static String inputWord(String input, String word) {
        return input.substring(input.indexOf(word) + 1);
    }

    private CustomTabsSession session;
    private static final String CHROME = "com.android.chrome";
    private CustomTabsClient client;

    void getPolicy(Context context, String link){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                client = customTabsClient;
                client.warmup(0L);
                //Initialize a session as soon as possible.
                session = client.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                client = null;
            }
        };
        CustomTabsClient.bindCustomTabsService(getApplicationContext(), CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.sompty);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(session)
                .setToolbarColor(Color.parseColor("#531A92"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (loopBlack(CHROME, context))
            launchUrl.intent.setPackage(CHROME);

        launchUrl.launchUrl(context, Uri.parse(link));
    }
    boolean loopBlack(String targetPackage, Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
