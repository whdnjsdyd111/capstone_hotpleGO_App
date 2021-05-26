package com.example.hotplego.ui.manager.hotple;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotplego.databinding.JusoPopupBinding;

public class JusoPopupActivity extends AppCompatActivity {
    private JusoPopupBinding binding;
    private WebView wv;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = JusoPopupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

        handler = new Handler();
    }

    private void init() {
        wv = binding.juso;

        wv.getSettings().setJavaScriptEnabled(true);

        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        wv.addJavascriptInterface(new AndroidBridge(), "TestApp");

        wv.setWebChromeClient(new WebChromeClient());

        wv.loadUrl("http://192.168.1.15:8000/popup/androidJuso");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String zip, final String address, final String lat, final String lng) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    init();
                    Intent intent = new Intent();
                    intent.putExtra("zip", zip);
                    intent.putExtra("address", address);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}
