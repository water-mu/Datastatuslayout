package com.mumu.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mumu.datastatuslayout.DataStatusLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DataStatusLayout mySl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mySl = findViewById(R.id.my_sl);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);

        mySl.setOnReloadListener(new DataStatusLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                Toast.makeText(MainActivity.this, "重新请求数据" + status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mySl.setStatus(DataStatusLayout.LOADING);
                break;
            case R.id.btn2:
                mySl.setStatus(DataStatusLayout.SUCCESS);
                break;
            case R.id.btn3:
                mySl.setStatus(DataStatusLayout.EMPTY);
                break;
            case R.id.btn4:
                mySl.setStatus(DataStatusLayout.ERROR);
                break;
            case R.id.btn5:
                mySl.setStatus(DataStatusLayout.NO_NETWORK);
                break;
        }
    }
}
