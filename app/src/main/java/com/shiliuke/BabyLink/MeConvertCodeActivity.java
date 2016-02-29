package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import com.shiliuke.adapter.FragmentAdapter;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.fragment.FragmentConsume;
import com.shiliuke.fragment.FragmentUnconsume;
import com.shiliuke.utils.FragmentEvent;
import com.shiliuke.utils.MSG;
import com.shiliuke.view.IndexViewPager;

/**
 * 我的兑换码
 * Created by wupeitao on 15/11/5.
 */
public class MeConvertCodeActivity extends ActivitySupport implements View.OnClickListener, ViewPager.OnPageChangeListener,FragmentEvent.OnEventListener {
    private IndexViewPager convert_code_viewpager;
    private Button mconvert_code_unconsume,//我组织的
            convert_code_consume;//我参与的

    private FragmentUnconsume fe = new FragmentUnconsume();
    private FragmentConsume ft = new FragmentConsume();

    private Fragment[] fragments = {fe, ft};
    private boolean[] fragmentsUpdateFlag = {false, false};
    private FragmentAdapter adapter;

    Handler mainHandler = new Handler() {

        /*
         * （非 Javadoc）
         *
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO 自动生成的方法存根
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG.INTO_05:
                    fragments[2] = ft;
                    fragmentsUpdateFlag[2] = true;
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_me_convert_code);
        initView();
    }

    private void initView() {
        setCtenterTitle("兑换码");
        convert_code_viewpager = (IndexViewPager) findViewById(R.id.convert_code_viewpager);
        mconvert_code_unconsume = (Button) findViewById(R.id.mconvert_code_unconsume);
        convert_code_consume = (Button) findViewById(R.id.convert_code_consume);
        mconvert_code_unconsume.setOnClickListener(this);
        convert_code_consume.setOnClickListener(this);
        adapter = new FragmentAdapter(this.getSupportFragmentManager(),fragments,fragmentsUpdateFlag);
        convert_code_viewpager.setAdapter(adapter);
        convert_code_viewpager.setOnPageChangeListener(this);
        convert_code_viewpager.setCurrentItem(0);
        mconvert_code_unconsume.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mconvert_code_unconsume:
                convert_code_viewpager.setCurrentItem(0);
                mconvert_code_unconsume.setSelected(true);
                convert_code_consume.setSelected(false);
                break;
            case R.id.convert_code_consume:
                convert_code_viewpager.setCurrentItem(1);
                mconvert_code_unconsume.setSelected(false);
                convert_code_consume.setSelected(true);
                break;
        }
    }
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onEvent(int what, Bundle data, Object object) {
        mainHandler.sendEmptyMessage(what);
    }

}
