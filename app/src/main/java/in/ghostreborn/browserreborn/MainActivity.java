package in.ghostreborn.browserreborn;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView rebornWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reborn_browser_layout);

        //Remove action bar
        getSupportActionBar().hide();

        requestPermissions(
                new String[]{
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1
        );

        rebornWebView = findViewById(R.id.reborn_web_view);
        AutoCompleteTextView rebornSearchBar = findViewById(R.id.reborn_web_bar);
        ImageButton rebornSearchButton = findViewById(R.id.reborn_web_search);
        ImageButton rebornWebHome = findViewById(R.id.reborn_web_home);
        ImageButton rebornWebBack = findViewById(R.id.reborn_web_back);
        ImageButton rebornWebForward = findViewById(R.id.reborn_web_forward);
        String[] autoComplete = {
                "https://google.com",
                "https://youtube.com"
        };
        rebornSearchBar.setThreshold(3);
        rebornSearchBar.setTextColor(Color.CYAN);
        rebornSearchBar.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, autoComplete));
        RebornWebUtils.setWebView(rebornWebView, this, rebornSearchBar);


        rebornWebBack.setOnClickListener(view -> rebornWebView.goBack());

        rebornWebForward.setOnClickListener(view -> rebornWebView.goForward());

        rebornSearchButton.setOnClickListener(view -> {
            String url = rebornSearchBar.getText().toString();
            if (!url.contains("http")){
                url = "https://www.google.com/search?&q=" + rebornSearchBar.getText().toString();
            }
            rebornWebView.loadUrl(url);
        });

        rebornWebHome.setOnClickListener(view -> {
            rebornWebView.loadUrl("https://www.google.com");
        });

    }

    long timeCheck;
    @Override
    public void onBackPressed() {
        if (timeCheck + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
        }else {
            rebornWebView.goBack();
        }
        timeCheck = System.currentTimeMillis();
    }
}