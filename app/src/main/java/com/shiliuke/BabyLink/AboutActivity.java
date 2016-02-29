package com.shiliuke.BabyLink;

import android.os.Bundle;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/12/23.
 */
public class AboutActivity extends ActivitySupport {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        setCtenterTitle("关于我们");

    }
}
