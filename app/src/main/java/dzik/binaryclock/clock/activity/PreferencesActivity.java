package dzik.binaryclock.clock.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import dzik.binaryclock.R;

public class PreferencesActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new Fragment()).commit();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings_title));
        }
    }

    //TODO: Actionbar with settings as a title

    public static class Fragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            findPreference(getString(R.string.reset_to_default_key)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    //TODO: first show a warning dialog
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    getActivity().recreate(); //The easiest way to update preferences' look accordingly
                    return false;
                }
            });
        }
    }
}
