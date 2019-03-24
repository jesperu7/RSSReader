package com.jesperu.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferenceActivity extends AppCompatActivity {
    private Button button;
    public static String REFRESH = "refresh_rate";
    public static String URL = "url_provided";
    public static String LIMIT = "max_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        button = findViewById(R.id.preferenceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }


    public void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle b = new Bundle();
        EditText editTextLimit = findViewById(R.id.editLimit);
        EditText editTextRefresh = findViewById(R.id.editRefresh);
        EditText editTextURL = findViewById(R.id.editURL);

        String limit = editTextLimit.getText().toString();
        String refresh = editTextRefresh.getText().toString();
        String url = editTextURL.getText().toString();

        b.putString(REFRESH, refresh);
        b.putString(LIMIT, limit);
        b.putString(URL, url);

        intent.putExtras(b);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }


}
