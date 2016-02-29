package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;

/**
 * 帮助中心
 * Created by wupeitao on 15/11/10.
 */
public class HelpActivity extends ActivitySupport {
    private TextView help_shiquan;
    private TextView shanwuhezuo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_help);
        setCtenterTitle("帮助中心");
        help_shiquan = (TextView) findViewById(R.id.help_shiquan);
        shanwuhezuo = (TextView) findViewById(R.id.shanwuhezuo);
        help_shiquan.setText(Html.fromHtml("<font style=\"font-weight:bold;\" color = #000000 >问题反馈及咨询:</font><font color = #000000 >\t\t18301500153\t\t李老师</font>"));
        shanwuhezuo.setText(Html.fromHtml("<font style=\"font-weight:bold;\" color = #000000 >商务合作:</font><font color = #000000 >\t\t18515139771\t\t丛先生</font>"));


    }
}
