package com.example.MovieCatalogue;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.MovieCatalogue.Receiver.AlarmReceiver;

import static com.example.MovieCatalogue.Activity.MainActivity.ID_DAILY;
import static com.example.MovieCatalogue.Activity.MainActivity.ID_RELEASE;

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String DAILY = "daily";
    public static String RELEASE = "release";

    private CheckBoxPreference isDaily;
    private CheckBoxPreference isRelease;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        
        init();
        setSummaries();
    }

    private void setSummaries() {
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        isDaily.setChecked(sh.getBoolean(DAILY,false));
        isRelease.setChecked(sh.getBoolean(RELEASE,false));
    }

    private void init() {
        DAILY = getResources().getString(R.string.key_daily_reminder);
        RELEASE = getResources().getString(R.string.key_daily_reminder);

        isDaily = findPreference(DAILY);
        isRelease = findPreference(RELEASE);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        AlarmReceiver alarmReceiverJam7 = new AlarmReceiver();
        AlarmReceiver alarmReceiverJam8 = new AlarmReceiver();

        if (s.equals(DAILY)) {


            String repeatTime = "07:00";
            String repeatMessage = "Daily Reminder "+repeatTime;
            alarmReceiverJam7.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage, ID_DAILY);
            System.out.println("Set Alarm jam 7 (preference)");

            isDaily.setChecked(sharedPreferences.getBoolean(DAILY, false));
        } else {
            alarmReceiverJam7.cancelAlarm(getContext(),AlarmReceiver.TYPE_REPEATING,ID_DAILY);
            System.out.println("matikan Alarm jam 7 (preference)");
        }
        if (s.equals(RELEASE)) {

            String repeatTime = "08:00";
            String repeatMessage = "Daily Reminder "+repeatTime;
            alarmReceiverJam8.setRepeatingAlarm(getContext(), AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage, 8);
            System.out.println("matikan Alarm jam 8 (preference)");

            isRelease.setChecked(sharedPreferences.getBoolean(RELEASE, false));
        } else {
            alarmReceiverJam8.cancelAlarm(getContext(), AlarmReceiver.TYPE_REPEATING, ID_RELEASE);
        }
    }
}