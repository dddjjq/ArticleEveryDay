package com.welson.artcleeveryday;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.welson.artcleeveryday.entity.MainData;
import com.welson.artcleeveryday.presenter.MainPresenter;
import com.welson.artcleeveryday.util.StringUtil;
import com.welson.artcleeveryday.view.BaseView;
import com.welson.artcleeveryday.view.MainScrollView;
import com.welson.artcleeveryday.view.MainToolbar;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements BaseView,
        MainToolbar.OnRightClickListener,MainToolbar.OnLeftClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "-TAG";
    private TextView title,author,content,footer;
    private MainToolbar mainToolbar;
    private LinearLayout mainLayout;
    private LinearLayout leftLayout;
    private LinearLayout articleLayout;
    private MainScrollView scrollView;
    private MainPresenter presenter;
    private String titleStr,authorStr,contentStr,footerStr;
    private static int width,height;//屏幕宽度和高度
    private LayoutHandler handler;
    private static final int OPEN = 0;
    private static final int CLOSE = 1;
    public static boolean isLeftMenuOpen = false;
    private static final int leftMaxWidth = 390; //左边栏宽度
    private int dx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        content = findViewById(R.id.content);
        footer = findViewById(R.id.footer);
        mainLayout = findViewById(R.id.main_layout);
        leftLayout = findViewById(R.id.left_layout);
        scrollView = findViewById(R.id.scrollView);
        articleLayout = findViewById(R.id.article_layout);
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
    public boolean onTouchEvent(MotionEvent event) {
        float oldX = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int)(event.getX() - oldX);
                Log.d("dingyl","dx" + dx);
                if (dx < leftMaxWidth){
                    leftLayout.layout(-leftLayout.getWidth()+dx,leftLayout.getTop(),dx,leftLayout.getHeight());
                    mainLayout.layout(dx,mainLayout.getTop(),dx+mainLayout.getWidth(),mainLayout.getHeight());
                    leftLayout.invalidate();
                    mainLayout.invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dx > leftMaxWidth/2){
                    openLeftLayout();
                }else {
                    closeLeftLayout();
                }
                dx = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

    private void initListener(){
        articleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeftMenuOpen){
                    smoothCloseLeftLayout();
                }
            }
        });
        mainToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLeftMenuOpen){
                    smoothCloseLeftLayout();
                }
            }
        });
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

    private void openLeftLayout(){
        leftLayout.layout(0,leftLayout.getTop(),leftMaxWidth,leftLayout.getHeight());
        mainLayout.layout(leftMaxWidth,mainLayout.getTop(),leftMaxWidth+mainLayout.getWidth(),mainLayout.getHeight());
        leftLayout.postInvalidateDelayed(300);
        mainLayout.postInvalidateDelayed(300);
        isLeftMenuOpen = true;
    }

    private void closeLeftLayout(){
        leftLayout.layout(-leftLayout.getWidth(),leftLayout.getTop(),0,leftLayout.getHeight());
        mainLayout.layout(0,mainLayout.getTop(),mainLayout.getWidth(),mainLayout.getHeight());
        leftLayout.postInvalidateDelayed(300);
        mainLayout.postInvalidateDelayed(300);
        isLeftMenuOpen = false;
    }

    /**
     * 这里的另一种方法是使用Handler处理layout方法，从而改变侧边栏位置，不过画面会有卡顿，效果不好
     */
    private void smoothOpenLeftLayout(){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",leftMaxWidth);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",leftMaxWidth);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setDuration(400);
        animatorSet.start();
        isLeftMenuOpen = true;
        //handler.removeMessages(CLOSE);
        //handler.sendEmptyMessageDelayed(OPEN,0);
    }

    private void smoothCloseLeftLayout(){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",-leftMaxWidth);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",-leftMaxWidth);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setDuration(400);
        animatorSet.start();
        isLeftMenuOpen = false;
        //handler.removeMessages(OPEN);
        //handler.sendEmptyMessageDelayed(CLOSE,0);
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
                        activity.leftLayout.layout(-leftMaxWidth+openCurrentX,activity.leftLayout.getTop(),openCurrentX,height-activity.leftLayout.getTop());
                        activity.mainLayout.layout(openCurrentX,activity.mainLayout.getTop(),openCurrentX+width,height-activity.scrollView.getTop());
                        activity.getWindow().getDecorView().invalidate();
                        sendEmptyMessageDelayed(OPEN,0);
                        openCurrentX = openCurrentX + xStep;
                    }else {
                        activity.isLeftMenuOpen = true;
                        removeMessages(OPEN);
                        openCurrentX = 0;
                    }
                    break;
                case CLOSE:
                    if (closeCurrentX > 0){
                        closeCurrentX = closeCurrentX - xStep;
                        activity.leftLayout.layout(-activity.leftLayout.getWidth()+closeCurrentX,activity.leftLayout.getTop(),closeCurrentX,height-activity.leftLayout.getTop());
                        activity.mainLayout.layout(closeCurrentX,activity.mainLayout.getTop(),closeCurrentX+width,height-activity.scrollView.getTop());
                        activity.getWindow().getDecorView().invalidate();
                        sendEmptyMessageDelayed(CLOSE,0);
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
