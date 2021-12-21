package in.ghostreborn.browserreborn;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.WebView;

public class RebornWebUtils {

    public static void setWebView(WebView rebornWebView, Context context) {
        rebornWebView.loadUrl("https://www.happymod.com");
        rebornWebView.getSettings().setJavaScriptEnabled(true);
        rebornWebView.canGoBack();
        rebornWebView.setWebViewClient(new RebornWebViewClient());
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
                String fileURI = cursor.getString(6);

                // Parse filename using download address
                // TODO find alternative efficient way
                fileURI = fileURI.substring(fileURI.lastIndexOf("/") + 1);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileURI);
                downloadManager.enqueue(request);
            }
        });
    }

}
