package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.utils.L;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 网页文字
 */
public class HtmlTextActivity extends ActivitySupport {
    public static final String CONTENT = "content";
    public static final String TITLE = "title";
    public static final String TYPE = "type";
    public static final int TEXT = 1;
    public static final int URL = 2;
    private WebView webview_html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_text);
        setCtenterTitle(getIntent().getStringExtra(TITLE));
        webview_html = (WebView) findViewById(R.id.webview_html);
        webview_html.getSettings().setDefaultTextEncodingName("UTF-8");
        webview_html.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        if (getIntent().getIntExtra(TYPE, 1) == 1) {
            try {
                webview_html.loadData(getIntent().getStringExtra(CONTENT),"text/html; charset=UTF-8", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            webview_html.loadUrl(getIntent().getStringExtra(CONTENT));
        }
    }

}
