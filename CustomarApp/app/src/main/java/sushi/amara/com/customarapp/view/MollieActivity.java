package sushi.amara.com.customarapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import sushi.amara.com.customarapp.Common.Common;
import sushi.amara.com.customarapp.R;

public class MollieActivity extends AppCompatActivity {

    WebView webView;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mollie);

        webView= (WebView)findViewById(R.id.webView);

        if (getIntent().getStringExtra("url") !=null){
            url = getIntent().getStringExtra("url");

            WebSettings webSettings=webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);

        }
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                try {

                    if (url.equals("https://sushiamara.de/")){
                        Common.orderCharts.clear();
                        startActivity(new Intent(MollieActivity.this,FoodActivity.class)
                                .putExtra("store", Common.store)
                                .putExtra("get", Common.system)
                                .putExtra("address", Common.location));
                        finish();
                    }

                    // do whatever you want to do on a web link click

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

        });
    }

    @Override
    public void onBackPressed() {

        String webUrl = webView.getUrl();

        if (webUrl.equals("https://sushiamara.de/")){
            Common.orderCharts.clear();
                            startActivity(new Intent(MollieActivity.this,FoodActivity.class)
                        .putExtra("store", Common.store)
                        .putExtra("get", Common.system)
                        .putExtra("address", Common.location));
                finish();
        }
        else if (webUrl.equals("https://www.paypal.com/webapps/hermes?flow=1-P&ulReturn=true&token=EC-8FT63648T3585790Y&country.x=ES&locale.x=en_US")){
            Common.orderCharts.clear();
                            startActivity(new Intent(MollieActivity.this,FoodActivity.class)
                        .putExtra("store", Common.store)
                        .putExtra("get", Common.system)
                        .putExtra("address", Common.location));
                finish();
        }

        else {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                startActivity(new Intent(MollieActivity.this,FoodActivity.class)
                        .putExtra("store", Common.store)
                        .putExtra("get", Common.system)
                        .putExtra("address", Common.location));
                finish();
            }
        }


    }
}
