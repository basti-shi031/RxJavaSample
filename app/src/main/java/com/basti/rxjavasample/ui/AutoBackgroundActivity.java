package com.basti.rxjavasample.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.basti.rxjavasample.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by SHIBW-PC on 2016/1/21.
 */
public class AutoBackgroundActivity extends AppCompatActivity {

    private RelativeLayout mRootView;
    private Toolbar mToolbar;
    private int[] colors = {
            Color.RED, Color.YELLOW, Color.BLUE, Color.WHITE, Color.CYAN
    };
    private Subscription subscription;
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autobackground);

        initView();
        mCompositeSubscription =  new CompositeSubscription();
        subscription = Observable.timer(3, 3, TimeUnit.SECONDS)
                .take(10)
                .flatMap(new Func1<Long, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Long aLong) {
                        int index = (int) (aLong % 5);
                        return Observable.just(colors[index]);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("TAG", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i("TAG", String.valueOf(integer));
                        setBackground(integer);
                    }
                });
        mCompositeSubscription.add(subscription);

    }

    private void setBackground(Integer color) {
        mRootView.setBackgroundColor(color);
    }

    private void initView() {

        mRootView = (RelativeLayout) findViewById(R.id.background);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.unsubscribe();
    }
}
