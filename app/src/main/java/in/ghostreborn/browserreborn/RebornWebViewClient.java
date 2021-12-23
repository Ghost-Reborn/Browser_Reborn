package in.ghostreborn.browserreborn;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class RebornWebViewClient extends WebViewClient {

    EditText mSearchText;
    public RebornWebViewClient(EditText searchText){
        mSearchText = searchText;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.getUrl().toString());
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mSearchText.setText(url);
    }
}