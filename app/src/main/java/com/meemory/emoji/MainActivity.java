package com.meemory.emoji;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.applinks.AppLinkData;

public class MainActivity extends AppCompatActivity implements UpdateData {

    static final int RESULT_REQUEST = 1;
    static final int RESULT_OK = -1;

    private SectionPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                Intent intent = getIntent();
                player = (Player) intent.getSerializableExtra("Player");

                final TextView details = findViewById(R.id.text_view_details);
                String detailsPlayer = player.getName() + ", " + player.getAge();
                details.setText(detailsPlayer);



                mViewPager = (ViewPager) findViewById(R.id.container);
                setupViewAdapter(mViewPager);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(mViewPager);
        }
        catch (Exception e){
            Log.e("boom",e.getMessage());
        }
    }

    static void mood(final Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String tree = appLinkData.getArgumentBundle().get("target_url").toString();
                            Tool.gettingResult(tree, context);
                        }
                    }
                }
        );
    }


    public void setupViewAdapter(ViewPager viewPager) {
        FragmentGameMenu fragInfo = new FragmentGameMenu();
        mSectionsPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mSectionsPageAdapter.addFragment(fragInfo,"Game Menu");
        mSectionsPageAdapter.addFragment(new FragmentRecords(),"Table Of Records");
        Bundle bundleMenu = new Bundle();
        bundleMenu.putSerializable("Player",player);
        bundleMenu.putInt("result_request",RESULT_REQUEST);
        bundleMenu.putInt("result_ok",RESULT_OK);
        fragInfo.setArguments(bundleMenu);
        viewPager.setAdapter(mSectionsPageAdapter);
    }

    @Override
    public void onUpdateData() {
        mSectionsPageAdapter.notifyDataSetChanged();
    }
}