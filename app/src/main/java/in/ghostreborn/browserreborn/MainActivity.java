package in.ghostreborn.browserreborn;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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

        WebView rebornWebView = findViewById(R.id.reborn_web_view);
        EditText rebornSearchBar = findViewById(R.id.reborn_web_bar);
        RebornWebUtils.setWebView(rebornWebView, this, rebornSearchBar);


        ImageButton rebornSearchButton = findViewById(R.id.reborn_web_search);
        ImageButton rebornWebBack = findViewById(R.id.reborn_web_back);

        rebornSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = rebornSearchBar.getText().toString();
                if (!url.contains("http")){
                    url = "https://www.google.com/search?&q=" + rebornSearchBar.getText().toString();
                }
                rebornWebView.loadUrl(url);
            }
        });

        rebornWebBack.setOnClickListener(view -> {
            rebornWebView.loadUrl("https://google.com");
        });

        rebornWebBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(MainActivity.this, rebornWebBack);
                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.go_backward:
                                rebornWebView.goBack();
                                break;
                            case R.id.go_forward:
                                rebornWebView.goForward();
                                break;
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

    }
}

class RebornWebViewClient extends WebViewClient {

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