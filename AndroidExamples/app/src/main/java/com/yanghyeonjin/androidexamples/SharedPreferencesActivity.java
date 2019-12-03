package com.yanghyeonjin.androidexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class SharedPreferencesActivity extends AppCompatActivity {

    EditText et_save_shared_pref;
    String shared = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        et_save_shared_pref = findViewById(R.id.et_save_shared_pref);

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        String value = sharedPreferences.getString("key", "");
        et_save_shared_pref.setText(value);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String value = et_save_shared_pref.getText().toString();
        editor.putString("key", value);
        /*editor.commit();*/
        editor.apply();
    }
}
