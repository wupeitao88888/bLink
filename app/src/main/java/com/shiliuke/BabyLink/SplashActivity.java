package com.shiliuke.BabyLink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.model.OnViewChangeListener;
import com.shiliuke.view.SwitchLayout;

public class SplashActivity extends ActivitySupport{
    /** Called when the activity is first created. */
    SwitchLayout switchLayout;//自定义的控件
    int mViewCount;//自定义控件中子控件的个数
    int mCurSel;//当前选中的imageView
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spash_layout);
        if(sharedPreferencesHelper.getBoolean("isfirst")){
            Intent intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        init();
    }

    private void init() {
        setRemoveTitle();
        switchLayout = (SwitchLayout) findViewById(R.id.switchLayoutID);
        sharedPreferencesHelper.putBoolean("isfirst", true);
        //得到子控件的个数
        mViewCount = switchLayout.getChildCount();
        //设置第一个imageView不被激活
        mCurSel = 0;
        switchLayout.setOnViewChangeListener(new MOnViewChangeListener());

    }

    //点击事件的监听器
    private class MOnClickListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            int pos = (Integer) v.getTag();
            System.out.println("pos:--" + pos);
            //设置当前显示的ImageView
            setCurPoint(pos);
            //设置自定义控件中的哪个子控件展示在当前屏幕中
            switchLayout.snapToScreen(pos);
        }
    }


    /**
     * 设置当前显示的ImageView
     * @param pos
     */
    private void setCurPoint(int pos) {
        if(pos < 0 || pos > mViewCount -1 || mCurSel == pos)
            return;
        mCurSel = pos;
    }

    //自定义控件中View改变的事件监听
    private class MOnViewChangeListener implements OnViewChangeListener {
        @Override
        public void onViewChange(int view) {
            System.out.println("view:--" + view);
            if(view < 0 || mCurSel == view){
                return ;
            }else if(view > mViewCount - 1){
                //当滚动到第五个的时候activity会被关闭
                System.out.println("finish activity");
                finish();
            }
            setCurPoint(view);
        }

    }

}