package com.basti.rxjavasample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.basti.rxjavasample.callback.OnItemClickListener;
import com.basti.rxjavasample.ui.AutoBackgroundActivity;
import com.basti.rxjavasample.ui.QuickClickAcitivty;
import com.basti.rxjavasample.utils.DividerItemDecoration;
import com.basti.rxjavasample.utils.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private List<String> mList;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initDate();

    }

    private void initDate() {
        mList = new ArrayList<>();

        mAdapter = new MyAdapter(mList, MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, AutoBackgroundActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, QuickClickAcitivty.class));
                        break;
                }
            }
        });

        Observable.just(getResources().getString(R.string.item1), getResources().getString(R.string.item2), "test2")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mList.add(s);
                        //mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                    }
                });
    }

    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }
}
