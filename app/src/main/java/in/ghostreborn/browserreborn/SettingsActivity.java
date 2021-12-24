package in.ghostreborn.browserreborn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();

        SwitchCompat javascriptEnabled = findViewById(R.id.javascript_switch);
        SharedPreferences preferences = getSharedPreferences(RebornConstants.PREFERENCES_NAME, MODE_PRIVATE);
        boolean isJavascriptEnabled = preferences.getBoolean(RebornConstants.JAVA_SCRIPT_ENABLED, false);
        javascriptEnabled.setChecked(isJavascriptEnabled);
        Editor editor = preferences.edit();

        javascriptEnabled.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b == true){
                editor.putBoolean(RebornConstants.JAVA_SCRIPT_ENABLED, true).apply();
            }else {
                editor.putBoolean(RebornConstants.JAVA_SCRIPT_ENABLED, false).apply();
            }
        });

    }
}