package in.ghostreborn.browserreborn;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.EditText;

public class RebornWebUtils {

    public static void setWebView(WebView rebornWebView, Context context, EditText searchText) {

        SharedPreferences preferences = context.getSharedPreferences(RebornConstants.PREFERENCES_NAME, Context.MODE_PRIVATE);
        boolean isJavascriptEnabled = preferences.getBoolean(RebornConstants.JAVA_SCRIPT_ENABLED, false);

        rebornWebView.loadUrl("https://www.google.com");
        rebornWebView.getSettings().setJavaScriptEnabled(isJavascriptEnabled);
        rebornWebView.canGoBack();
        rebornWebView.setWebViewClient(new RebornWebViewClient(searchText));
        rebornWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                Request request = new Request(Uri.parse(url));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Cursor cursor = downloadManager.query(query);
                cursor.moveToFirst();

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, cursor.getString(4));
                downloadManager.enqueue(request);
            }
        });
    }

}
