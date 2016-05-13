package org.meruvian.midas.social.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.meruvian.midas.core.defaults.DefaultActivity;
import org.meruvian.midas.social.R;

import java.net.CookieManager;

/**
 * Created by ludviantoovandi on 04/11/14.
 */
public class WebViewActivity extends DefaultActivity {
    private WebView webView;
    private Toolbar toolbar;
    private ProgressDialog dialog;

    @Override
    public int layout() {
        return R.layout.activity_webview;
    }

    @Override
    public void onViewCreated() {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_webview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(WebViewActivity.this);
        dialog.setMessage(getString(R.string.loading));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                webView.stopLoading();
            }
        });

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setDatabaseEnabled(false);
        webView.clearSslPreferences();
        webView.clearMatches();
        webView.clearCache(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.loadUrl(getIntent().getStringExtra("url"));

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (!dialog.isShowing())
                    dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String code = Uri.parse(url).getQueryParameter("code");

                if (code != null && !"".equalsIgnoreCase(code)) {
                    Intent resultIntent = new Intent();
                    resultIntent.setData(Uri.parse(url));
                    setResult(RESULT_OK, resultIntent);

                    finish();
                }

                dialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode == 404) {
                    view.loadUrl("file:///android_asset/404.html");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_webview, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_refresh) {
            webView.loadUrl(getIntent().getStringExtra("url"));
        }

        return super.onOptionsItemSelected(item);
    }

}
