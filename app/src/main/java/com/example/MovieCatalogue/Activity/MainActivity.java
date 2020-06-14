package com.example.MovieCatalogue.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.MovieCatalogue.PreferenceActivity;
import com.example.MovieCatalogue.Receiver.AlarmReceiver;
import com.example.MovieCatalogue.R;
import com.example.MovieCatalogue.adapter.MainFavoritePagerAdapter;
import com.example.MovieCatalogue.adapter.MainPagerAdapter;
import com.example.MovieCatalogue.db.FavoriteHelper;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import static com.example.MovieCatalogue.PreferenceFragment.DAILY;
import static com.example.MovieCatalogue.PreferenceFragment.RELEASE;


public class MainActivity extends AppCompatActivity {
    TabLayout tabs;
    public static int TAB_ACTIVE;
    public static final int ID_DAILY = 101;
    public static final int ID_RELEASE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = findViewById(R.id.tabs);


        FavoriteHelper favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        //inisiasi alarm jam 7
        AlarmReceiver alarmReceiverJam7 = new AlarmReceiver();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean jam7 = prefs.getBoolean(DAILY, false);

        Boolean jam8 = prefs.getBoolean(RELEASE, false);

        if (jam7){
            String repeatTime = "07:00";
            String repeatMessage = "Daily Reminder "+repeatTime;
            alarmReceiverJam7.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage, ID_DAILY);
            System.out.println("Set Alarm jam 7");
        }

        if (jam8){
            AlarmReceiver alarmReceiverJam8 = new AlarmReceiver();
            String repeatTime = "08:00";
            String repeatMessage = "Release Reminder " + repeatTime;
            alarmReceiverJam8.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage, ID_RELEASE);
            System.out.println("Set Alarm jam 8");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Toolbar toolbar = findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        ViewPager viewPager = findViewById(R.id.view_pager);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        if (message != null){
            MainFavoritePagerAdapter mainPagerAdapter = new MainFavoritePagerAdapter(this, getSupportFragmentManager());
            viewPager.setAdapter(mainPagerAdapter);
            toolbar.setTitle(getResources().getString(R.string.favorite_title));

        } else {
            MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager());
            viewPager.setAdapter(mainPagerAdapter);

        }

        tabs.setupWithViewPager(viewPager);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        if (message!=null){
            MenuItem item = menu.findItem(R.id.action_favorite_list);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        else if (item.getItemId() == R.id.action_favorite_list){
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            intent.putExtra("message", "favorite");
            startActivity(intent);
        }

        else if (item.getItemId() == R.id.action_search){
            Intent intent;

            TAB_ACTIVE = tabs.getSelectedTabPosition();
            System.out.println("POSTITION : "+TAB_ACTIVE);


            if (TAB_ACTIVE == 0){
                intent = new Intent(MainActivity.this, MovieSearchListActivity.class);
            } else {

                intent = new Intent(MainActivity.this, TvShowSearchListActivity.class);
            }

            startActivity(intent);
        }

        else if (item.getItemId() ==  R.id.action_preference){
            Intent intent = new Intent(MainActivity.this, PreferenceActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}