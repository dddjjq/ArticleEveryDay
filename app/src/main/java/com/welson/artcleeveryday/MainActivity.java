package com.welson.artcleeveryday;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.welson.artcleeveryday.entity.MainData;
import com.welson.artcleeveryday.presenter.MainPresenter;
import com.welson.artcleeveryday.util.HtmlUtil;
import com.welson.artcleeveryday.util.StringUtil;
import com.welson.artcleeveryday.view.BaseView;
import com.welson.artcleeveryday.view.MainToolbar;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements BaseView,
        MainToolbar.OnRightClickListener,MainToolbar.OnLeftClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "-TAG";
    private TextView title,author,content,footer;
    private MainToolbar mainToolbar;
    private LinearLayout mainLayout;
    private LinearLayout leftLayout;
    private ScrollView scrollView;
    private MainPresenter presenter;
    private String titleStr,authorStr,contentStr,footerStr;
    private static int width,height;//屏幕宽度和高度
    private LayoutHandler handler;
    private static final int OPEN = 0;
    private static final int CLOSE = 1;
    private boolean isLeftMenuOpen = false;
    private static final int leftMaxWidth = 480;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        initView();
        initData();
    }

    private void initView(){
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        content = findViewById(R.id.content);
        footer = findViewById(R.id.footer);
        mainLayout = findViewById(R.id.main_layout);
        leftLayout = findViewById(R.id.left_layout);
        scrollView = findViewById(R.id.scrollView);
        mainToolbar = findViewById(R.id.main_toolbar);
        mainToolbar.setOnLeftClickListener(this);
        mainToolbar.setOnRightClickListener(this);
        setSupportActionBar(mainToolbar);
    }

    private void initData(){
        presenter = new MainPresenter(this);
        presenter.requestData();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        handler = new LayoutHandler(this);
    }

    @Override
    public void showSuccess(MainData mainData) {
        titleStr = mainData.getTitle();
        authorStr = mainData.getAuthor();
        contentStr = StringUtil.getRealString(mainData.getContent());
        footerStr = String.format(getString(R.string.footer_deafault),mainData.getWc());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(titleStr);
                author.setText(authorStr);
                content.setText(contentStr);
                footer.setText(footerStr);
            }
        });
    }

    @Override
    public void showError() {

    }

    @Override
    public void onLeftClick() {
        if (!isLeftMenuOpen){
            smoothOpenLeftLayout();
        }else {
            smoothCloseLeftLayout();
        }
    }

    @Override
    public void onRightClick() {

    }

    private void smoothOpenLeftLayout(){
        handler.sendEmptyMessageDelayed(OPEN,0);
    }

    private void smoothCloseLeftLayout(){
        handler.sendEmptyMessageDelayed(CLOSE,0);
    }
    static class LayoutHandler extends Handler{

        WeakReference<MainActivity> reference;
        private MainActivity activity;
        private int openCurrentX;
        private int closeCurrentX;
        private int xStep = 30;

        LayoutHandler(MainActivity mActivity){
            reference = new WeakReference<>(mActivity);
            activity = reference.get();
            closeCurrentX = leftMaxWidth;
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case OPEN:
                    if (openCurrentX < leftMaxWidth){
                        //Log.d(TAG,"width< /3");
                        activity.leftLayout.layout(-leftMaxWidth+openCurrentX,activity.leftLayout.getTop(),openCurrentX,height-activity.leftLayout.getTop());
                        activity.mainLayout.layout(openCurrentX,activity.mainLayout.getTop(),openCurrentX+width,height-activity.scrollView.getTop());
                        activity.getWindow().getDecorView().invalidate();
                        sendEmptyMessageDelayed(OPEN,0);
                        openCurrentX = openCurrentX + xStep;
                        Log.d(TAG,"closeCurrentX : " + openCurrentX);
                    }else {
                        //Log.d(TAG,"> width/3");
                        activity.isLeftMenuOpen = true;
                        removeMessages(OPEN);
                        openCurrentX = 0;
                    }
                    break;
                case CLOSE:
                    Log.d(TAG,"closeCurrentX : " + closeCurrentX);
                    if (closeCurrentX >= 0){
                        activity.leftLayout.layout(-activity.leftLayout.getWidth()+closeCurrentX,activity.leftLayout.getTop(),closeCurrentX,height-activity.leftLayout.getTop());
                        activity.mainLayout.layout(closeCurrentX,activity.mainLayout.getTop(),closeCurrentX+width,height-activity.scrollView.getTop());
                        activity.getWindow().getDecorView().invalidate();
                        sendEmptyMessageDelayed(CLOSE,0);
                        closeCurrentX = closeCurrentX - xStep;
                        Log.d(TAG,"closeCurrentX : " + closeCurrentX);
                    }else {
                        activity.isLeftMenuOpen = false;
                        removeMessages(CLOSE);
                        closeCurrentX = leftMaxWidth;
                    }
                    break;
            }
        }
    }
}
