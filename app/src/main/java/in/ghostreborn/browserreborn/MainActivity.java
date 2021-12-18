package in.ghostreborn.browserreborn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView rebornWebView = new WebView(this);
        setContentView(rebornWebView);

        rebornWebView.loadUrl("https://www.google.com");
        rebornWebView.getSettings().setJavaScriptEnabled(true);
        rebornWebView.canGoBack();
        rebornWebView.setWebViewClient(new RebornWebViewClient());

    }
}

class RebornWebViewClient extends WebViewClient{
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }
}