package com.inledco.fluvalsmart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.inledco.fluvalsmart.R;

public class WebActivity extends BaseActivity
{
    private Toolbar web_toolbar;
    private ProgressBar web_loading;
    private WebView web_show;

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_web );

        initView();
        initData();
        initEvent();
    }

    @Override
    protected void initView ()
    {
        web_toolbar = (Toolbar) findViewById( R.id.web_toolbar );
        web_loading = (ProgressBar) findViewById( R.id.web_loading );
        web_show = (WebView) findViewById( R.id.web_show );

        setSupportActionBar( web_toolbar );
    }

    @Override
    protected void initData ()
    {
        Intent intent = getIntent();
        String url = intent.getStringExtra( "url" );
        url = "http://www.fluvalaquatics.com/us/home/";
        showWebView( web_show, url );
    }

    @Override
    protected void initEvent ()
    {
        web_toolbar.setNavigationOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick ( View view )
            {
                finish();
            }
        } );
    }

    private void showWebView ( final WebView web, String url )
    {
        web.getSettings()
           .setJavaScriptEnabled( true );
        web.getSettings()
           .setLoadWithOverviewMode( true );
        web.getSettings()
           .setDefaultTextEncodingName( "utf-8" );
        web.getSettings()
           .setCacheMode( WebSettings.LOAD_NO_CACHE );
        web.setWebChromeClient( new WebChromeClient()
        {
            /**
             * 获取到链接标题
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle ( WebView view, String title )
            {
                super.onReceivedTitle( view, title );
                web_toolbar.setTitle( title );
            }

            @Override
            public void onProgressChanged ( WebView view, int newProgress )
            {
                super.onProgressChanged( view, newProgress );
                web_loading.setProgress( newProgress );
                if ( newProgress >= 100 )
                {
                    web_loading.setVisibility( View.GONE );
                }
            }
        } );

        web.setWebViewClient( new WebViewClient()
        {
            @Override
            public void onPageFinished ( WebView view, String url )
            {
                super.onPageFinished( view, url );
            }


        } );
        web.loadUrl( url );
    }
}
