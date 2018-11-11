package com.welson.artcleeveryday.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.welson.artcleeveryday.MyApplication;
import com.welson.artcleeveryday.R;
import com.welson.artcleeveryday.constants.Constants;
import com.welson.artcleeveryday.fragment.RightDialogFragment;
import com.welson.artcleeveryday.entity.MainData;
import com.welson.artcleeveryday.fragment.SettingDialogFragment;
import com.welson.artcleeveryday.presenter.MainPresenter;
import com.welson.artcleeveryday.util.DensityUtil;
import com.welson.artcleeveryday.util.SharedPreferenceUtil;
import com.welson.artcleeveryday.util.StringUtil;
import com.welson.artcleeveryday.util.ToastUtil;
import com.welson.artcleeveryday.view.BaseView;
import com.welson.artcleeveryday.view.MainLinearLayout;
import com.welson.artcleeveryday.view.MainScrollView;
import com.welson.artcleeveryday.view.MainToolbar;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements BaseView,
        MainToolbar.OnRightClickListener,MainToolbar.OnLeftClickListener,View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName() + "-TAG";
    private TextView title,author,content,footer;
    private MainToolbar mainToolbar;
    private MainLinearLayout mainLayout;
    private LinearLayout leftLayout;
    private LinearLayout articleLayout;
    private MainScrollView scrollView;
    private LinearLayout collectItem;
    private LinearLayout settingItem;
    private LinearLayout commentItem;
    private ImageView topLine;
    private ImageView bottomLine;
    public MainPresenter presenter;
    private String titleStr,authorStr,contentStr,footerStr;
    private static int width,height;//屏幕宽度和高度
    private LayoutHandler handler;
    private static final int OPEN = 0;
    private static final int CLOSE = 1;
    public static boolean isLeftMenuOpen = false;
    public static final int leftMaxWidth = 394; //左边栏宽度
    private int dx = 0;
    float oldX = 0;
    private static final int scrollPeriod = 350;
    private boolean canMove = false;
    private RightDialogFragment fragment;
    private SettingDialogFragment settingDialogFragment;
    private int touchSlop;
    public MainData currData;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private ToastUtil toastUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSizeColor();
        initListener();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        fragment = new RightDialogFragment();
        settingDialogFragment = new SettingDialogFragment();
        //hideBottomUiMenu();
        //TODO for bottom UI
    }


    private void initView(){
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        content = findViewById(R.id.content);
        footer = findViewById(R.id.footer);
        mainLayout = findViewById(R.id.main_layout);
        leftLayout = findViewById(R.id.left_layout);
        collectItem = findViewById(R.id.collect);
        settingItem = findViewById(R.id.setting);
        commentItem = findViewById(R.id.zan);
        collectItem.setOnClickListener(this);
        settingItem.setOnClickListener(this);
        commentItem.setOnClickListener(this);
        scrollView = findViewById(R.id.scrollView);
        articleLayout = findViewById(R.id.article_layout);
        mainToolbar = findViewById(R.id.main_toolbar);
        topLine = findViewById(R.id.top_line);
        bottomLine = findViewById(R.id.bottom_line);
        mainToolbar.setOnLeftClickListener(this);
        mainToolbar.setOnRightClickListener(this);
        setSupportActionBar(mainToolbar);
        toastUtil = new ToastUtil(this);
    }

    private void initData(){
        scrollView.setVisibility(View.GONE);
        presenter = new MainPresenter(this,this);
        if (getIntent().getStringExtra("curr_date")!=null){
            String currDate = getIntent().getStringExtra("curr_date");
            MainData mainData = MyApplication.databaseUtil.getData(Integer.parseInt(currDate));
            showSuccess(mainData);
            sharedPreferenceUtil.setCurrentDate(String.valueOf(mainData.getDate().getCurr()));
            sharedPreferenceUtil.setBeforeDate(String.valueOf(mainData.getDate().getPrev()));
            sharedPreferenceUtil.setNextDate(String.valueOf(mainData.getDate().getNext()));
            Log.d("dingyl","curr_date : " + currDate);
        }else {
            presenter.requestData();
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        handler = new LayoutHandler(this);
        touchSlop = DensityUtil.dp2px(this,ViewConfiguration.get(this).getScaledTouchSlop()) ;
    }

    public void initSizeColor(){
        sharedPreferenceUtil = new SharedPreferenceUtil(this);
        int textSizeIdx = sharedPreferenceUtil.getTextSizeIdx();
        int colorIdx = sharedPreferenceUtil.getBackColor();
        boolean isNightMode = sharedPreferenceUtil.getEnableNightMode();
        title.setTextSize(Constants.textSizesTitle[textSizeIdx]);
        author.setTextSize(Constants.textSizesAuthor[textSizeIdx]);
        footer.setTextSize(Constants.textSizesAuthor[textSizeIdx]);
        content.setTextSize(Constants.textSizesContent[textSizeIdx]);
        if (!isNightMode){
            topLine.setBackgroundColor(Color.rgb(228,219,219));
            bottomLine.setBackgroundColor(Color.rgb(228,219,219));
            title.setTextColor(Constants.textColors[colorIdx]);
            author.setTextColor(Constants.textColors[colorIdx]);
            footer.setTextColor(Constants.textColors[colorIdx]);
            content.setTextColor(Constants.textColors[colorIdx]);
            mainLayout.setBackgroundColor(Constants.colors[colorIdx]);
            articleLayout.setBackgroundColor(Constants.colors[colorIdx]);
        }else {
            topLine.setBackgroundColor(Color.rgb(70,70,70));
            bottomLine.setBackgroundColor(Color.rgb(70,70,70));
            title.setTextColor(Constants.nightTextColor);
            author.setTextColor(Constants.nightTextColor);
            footer.setTextColor(Constants.nightTextColor);
            content.setTextColor(Constants.nightTextColor);
            mainLayout.setBackgroundColor(Constants.nightBackColor);
            articleLayout.setBackgroundColor(Constants.nightBackColor);
        }

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
        currData = mainData;
        titleStr = mainData.getTitle();
        authorStr = mainData.getAuthor();
        contentStr = StringUtil.getRealString(mainData.getContent());
        footerStr = String.format(getString(R.string.footer_deafault),mainData.getWc());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scrollToTop();
                scrollView.setVisibility(View.VISIBLE);
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
        /*FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.dialog,fragment);
        transaction.commit();*/
        fragment.show(getSupportFragmentManager(),"Dialog");
        //hideBottomUiMenu();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                oldX = event.getRawX();
                if (oldX > mainLayout.getLeft() && oldX < mainLayout.getLeft() + leftMaxWidth){
                    canMove = true;
                }else {
                    canMove = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int)(event.getRawX() - oldX);

                if (dx < leftMaxWidth && canMove && dx >= touchSlop && !isLeftMenuOpen){
                    /*leftLayout.layout(-leftLayout.getWidth()+dx,leftLayout.getTop(),dx,leftLayout.getHeight());
                    mainLayout.layout(dx,mainLayout.getTop(),dx+mainLayout.getWidth(),mainLayout.getHeight());
                    leftLayout.invalidate();
                    mainLayout.invalidate();*/
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",dx);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",dx);
                    animatorSet.play(animator1).with(animator2);
                    animatorSet.setDuration(0);
                    animatorSet.start();
                }
                if (dx <= -touchSlop && dx > -leftMaxWidth && isLeftMenuOpen){
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",leftMaxWidth+dx);
                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",leftMaxWidth+dx);
                    animatorSet.play(animator1).with(animator2);
                    animatorSet.setDuration(0);
                    animatorSet.start();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (dx >= leftMaxWidth/2){
                    openLeftLayout(leftMaxWidth-dx);
                }else if (dx >= touchSlop && dx < leftMaxWidth/2 && !isLeftMenuOpen){
                    closeLeftLayout(dx);
                }else if (dx <= -touchSlop && dx >= -leftMaxWidth/2){
                    openLeftLayout(-dx);
                }else if (dx < -leftMaxWidth/2){
                    closeLeftLayout(leftMaxWidth+dx);
                }
                break;
        }
        return super.onTouchEvent(event);
    }


    public void openLeftLayout(int x){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",leftMaxWidth);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",leftMaxWidth);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setDuration(getPeriodTime(x));
        animatorSet.start();
        isLeftMenuOpen = true;
    }

    public void closeLeftLayout(int x){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",0);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setDuration(getPeriodTime(x));
        animatorSet.start();
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
        animatorSet.setDuration(scrollPeriod);
        animatorSet.start();
        isLeftMenuOpen = true;
        //handler.removeMessages(CLOSE);
        //handler.sendEmptyMessageDelayed(OPEN,0);
    }

    private void smoothCloseLeftLayout(){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mainLayout,"translationX",0);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(leftLayout,"translationX",0);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setDuration(scrollPeriod);
        animatorSet.start();
        isLeftMenuOpen = false;
        //handler.removeMessages(OPEN);
        //handler.sendEmptyMessageDelayed(CLOSE,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collect:
                smoothCloseLeftLayout();
                Intent intent = new Intent(this,CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                smoothCloseLeftLayout();
                settingDialogFragment.show(getSupportFragmentManager(),"settingDialogFragment");
                break;
            case R.id.zan:
                smoothCloseLeftLayout();
                toastUtil.setToastText("点了个赞");
                break;
        }
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

    private void hideBottomUiMenu(){
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19){
            View view = this.getWindow().getDecorView();
            view.setSystemUiVisibility(View.GONE);
        }else if (Build.VERSION.SDK_INT >= 19){
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     *  根据距离获取滑动时间
     */
    private int getPeriodTime(int x){
        if (x < 0){
            return 0;
        }
        return (int)((1 - x * 1.0/leftMaxWidth)*scrollPeriod);
    }

    public void scrollToTop(){
        scrollView.fullScroll(View.FOCUS_UP);
    }
}
