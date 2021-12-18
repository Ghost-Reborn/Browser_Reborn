package in.ghostreborn.browserreborn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reborn_browser_layout);

        //Remove action bar
        getSupportActionBar().hide();

        WebView rebornWebView = findViewById(R.id.reborn_web_view);

        rebornWebView.loadUrl("https://www.google.com");
        rebornWebView.getSettings().setJavaScriptEnabled(true);
        rebornWebView.canGoBack();
        rebornWebView.setWebViewClient(new RebornWebViewClient());

        EditText rebornSearchBar = findViewById(R.id.reborn_web_bar);
        ImageButton rebornSearchButton = findViewById(R.id.reborn_web_search);
        ImageButton rebornWebBack = findViewById(R.id.reborn_web_back);

        rebornSearchButton.setOnClickListener(view -> rebornWebView.loadUrl(
                rebornSearchBar.getText().toString()
        ));

        rebornWebBack.setOnClickListener(view -> {
            rebornWebView.goBack();
        });

    }
}

class RebornWebViewClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }
}