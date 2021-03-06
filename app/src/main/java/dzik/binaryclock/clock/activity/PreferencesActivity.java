package dzik.binaryclock.clock.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import dzik.binaryclock.R;
import dzik.binaryclock.clock.dialogs.MessageDialog;

public class PreferencesActivity extends AppCompatActivity {
    private static final String THEME_DIALOG_TAG = "theme";
    private static final String CHANGE_TO_DEFAULT_DIALOG_TAG = "changeToDefault";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString(getString(R.string.theme_key), getString(R.string.theme_dark_value))
                .equals(getString(R.string.theme_dark_value))) {
            setTheme(R.style.DarkAppTheme);
        } else {
            setTheme(R.style.LightAppTheme);
        }
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new MyFragment()).commit();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings_title));
        }
    }

    public static class MyFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        private boolean mShowThemeDialog;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mShowThemeDialog = true;
            addPreferencesFromResource(R.xml.settings);
            findPreference(getString(R.string.reset_to_default_key)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    final MessageDialog dialog = MessageDialog.newInstance(getString(R.string.change_to_default_dialog_message),
                            getString(R.string.change_to_default_dialog_positive),
                            getString(R.string.change_to_default_dialog_negative), new MessageDialog.DialogListener() {
                                @Override
                                public void onPositiveClick() {
                                    mShowThemeDialog = false;
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                    String theme = preferences.getString(getString(R.string.theme_key), getString(R.string.theme_dark_value));
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.clear();
                                    editor.putString(getString(R.string.theme_key), theme);
                                    setDefaultColors(editor);
                                    editor.apply();
                                    dismissDialog(CHANGE_TO_DEFAULT_DIALOG_TAG);
                                    getActivity().recreate(); //The easiest way to update preferences' look accordingly
                                    mShowThemeDialog = true;
                                }

                                @Override
                                public void onNegativeClick() {
                                }

                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public int describeContents() {
                                    return 0;
                                }

                                @Override
                                public void writeToParcel(Parcel parcel, int i) {
                                }
                            });
                    dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), CHANGE_TO_DEFAULT_DIALOG_TAG);
                    return false;
                }
            });
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.theme_key)) && mShowThemeDialog) {
                final MessageDialog dialog = MessageDialog.newInstance(getString(R.string.theme_dialog_message),
                        getString(R.string.theme_dialog_positive),
                        getString(R.string.theme_dialog_negative), new MessageDialog.DialogListener() {
                    @Override
                    public void onPositiveClick() {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor editor = preferences.edit();
                        setDefaultColors(editor);
                        editor.apply();
                        refreshTheme();
                    }

                    @Override
                    public void onNegativeClick() {
                        refreshTheme();
                    }

                    @Override
                    public void onCancel() {
                        refreshTheme();
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel parcel, int i) {
                    }
                });
                dialog.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), THEME_DIALOG_TAG);
            }
        }

        private void dismissDialog(String tag) {
            Fragment fragment = ((AppCompatActivity) getActivity()).getSupportFragmentManager().findFragmentByTag(tag);
            if(fragment instanceof MessageDialog) {
                ((MessageDialog) fragment).dismiss();
            }
        }

        private void refreshTheme() {
            dismissDialog(THEME_DIALOG_TAG);
            getActivity().recreate();
        }

        private void setDefaultColors(SharedPreferences.Editor editor) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int background, hourCircle, minuteCircle, secondCircle, inactiveCircle, font;
            if(preferences.getString(getString(R.string.theme_key), getString(R.string.theme_dark_value))
                    .equals(getString(R.string.theme_dark_value))) {
                background = ContextCompat.getColor(getActivity(), R.color.defaultDarkBackground);
                hourCircle = ContextCompat.getColor(getActivity(), R.color.defaultDarkHourCircle);
                minuteCircle = ContextCompat.getColor(getActivity(), R.color.defaultDarkMinuteCircle);
                secondCircle = ContextCompat.getColor(getActivity(), R.color.defaultDarkSecondCircle);
                inactiveCircle = ContextCompat.getColor(getActivity(), R.color.defaultDarkInactiveCircle);
                font = ContextCompat.getColor(getActivity(), R.color.defaultDarkFontColor);
            } else {
                background = ContextCompat.getColor(getActivity(), R.color.defaultLightBackground);
                hourCircle = ContextCompat.getColor(getActivity(), R.color.defaultLightHourCircle);
                minuteCircle = ContextCompat.getColor(getActivity(), R.color.defaultLightMinuteCircle);
                secondCircle = ContextCompat.getColor(getActivity(), R.color.defaultLightSecondCircle);
                inactiveCircle = ContextCompat.getColor(getActivity(), R.color.defaultLightInactiveCircle);
                font = ContextCompat.getColor(getActivity(), R.color.defaultLightFontColor);
            }
            editor.putInt(getString(R.string.color_background_key), background);
            editor.putInt(getString(R.string.color_circle_hour_key), hourCircle);
            editor.putInt(getString(R.string.color_circle_minute_key), minuteCircle);
            editor.putInt(getString(R.string.color_circle_second_key), secondCircle);
            editor.putInt(getString(R.string.color_circle_inactive_key), inactiveCircle);
            editor.putInt(getString(R.string.color_numbers_key), font);
        }
    }
}
