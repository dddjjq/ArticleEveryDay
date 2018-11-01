package com.welson.artcleeveryday;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.welson.artcleeveryday.presenter.MainPresenter;
import com.welson.artcleeveryday.util.HtmlUtil;
import com.welson.artcleeveryday.util.StringUtil;
import com.welson.artcleeveryday.view.BaseView;

public class MainActivity extends AppCompatActivity implements BaseView{

    private TextView content;
    private LinearLayout mainLayout;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initView(){
        content = findViewById(R.id.content);
        mainLayout = findViewById(R.id.main_layout);
    }

    private void initData(){
        presenter = new MainPresenter(this);
        presenter.requestData();
    }

    @Override
    public void showSuccess(String body) {
        content.setText(StringUtil.getRealString(body));
    }

    @Override
    public void showError() {

    }
}
