package in.ghostreborn.browserreborn;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

//TODO the filename is changed on second
// download and the file name changed is of the first
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reborn_browser_layout);

        //Remove action bar
        getSupportActionBar().hide();

        WebView rebornWebView = findViewById(R.id.reborn_web_view);

        rebornWebView.loadUrl("https://www.happymod.com");
        rebornWebView.getSettings().setJavaScriptEnabled(true);
        rebornWebView.canGoBack();
        rebornWebView.setWebViewClient(new RebornWebViewClient());
        rebornWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Request request = new Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "temp");
                downloadManager.enqueue(request);

                DownloadManager.Query query = new DownloadManager.Query();
                Cursor cursor = downloadManager.query(query);
                cursor.moveToFirst();
                String fileURI = cursor.getString(6);
                fileURI = fileURI.substring(fileURI.lastIndexOf("/") + 1);
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File from = new File(dir, "temp");
                File to = new File(dir, fileURI);
                boolean done = from.renameTo(to);
                Log.e("ERROR: ", done + "");
            }
        });

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