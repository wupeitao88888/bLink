package com.shiliuke.BabyLink;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;
import com.shiliuke.bean.IssueInfo;
import com.shiliuke.utils.PickDialogUtils;
import com.shiliuke.utils.StrUtil;
import com.shiliuke.utils.ToastUtil;

/**
 * 发布活动
 * Created by wpt on 2015/10/29.
 */
public class IssueActivity extends ActivitySupport implements View.OnClickListener {
    private Button activity_issue;

    private EditText subject_activity_content;//活动主题
    private EditText activity_address_content;//活动地点
    private TextView activity_time_content;//活动时间
    private TextView assemble_time;//集合时间
    private EditText assemble_address_content;//集合地点
    private EditText means_of_transportation_content;//交通工具
    private EditText contact_man_content;//联系人
    private EditText contact_phone_content;//联系人电话
    private EditText max_person_content;//上限人数
    private EditText cost_issue_content;//报名费用
    private EditText method_of_payment_content;//支付方式
private RelativeLayout assemble_content;
    private TextView assemble_time_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_issue);
        initView();
        setCtenterTitle("发布活动");
    }

    private void initView() {
        activity_issue = (Button) findViewById(R.id.activity_issue);
        subject_activity_content = (EditText) findViewById(R.id.subject_activity_content);
        activity_address_content = (EditText) findViewById(R.id.activity_address_content);
        activity_time_content = (TextView) findViewById(R.id.activity_time_content);
        assemble_address_content = (EditText) findViewById(R.id.assemble_address_content);
        means_of_transportation_content = (EditText) findViewById(R.id.means_of_transportation_content);
        assemble_time = (TextView) findViewById(R.id.assemble_time);
        contact_man_content = (EditText) findViewById(R.id.contact_man_content);
        contact_phone_content = (EditText) findViewById(R.id.contact_phone_content);
        max_person_content = (EditText) findViewById(R.id.max_person_content);
        cost_issue_content = (EditText) findViewById(R.id.cost_issue_content);
        method_of_payment_content = (EditText) findViewById(R.id.method_of_payment_content);
        assemble_content = (RelativeLayout) findViewById(R.id.assemble_content);
        assemble_time_content= (TextView) findViewById(R.id.assemble_time_content);
        activity_issue.setOnClickListener(this);
        activity_time_content.setOnClickListener(this);
        assemble_content.setOnClickListener(this);
        inputFilterSpace(subject_activity_content);
//        inputFilterSpace(activity_address_content);
//        inputFilterSpace(assemble_address_content);
        inputFilterSpace(means_of_transportation_content);
        inputFilterSpace(contact_man_content);
        inputFilterSpace(contact_phone_content);
        inputFilterSpace(max_person_content);
        inputFilterSpace(cost_issue_content);
        inputFilterSpace(method_of_payment_content);
    }
    public static void inputFilterSpace(final EditText edit){
        edit.setFilters(new InputFilter[]
                {
                        new InputFilter.LengthFilter(16),
                        new InputFilter(){
                            public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {
                                if(src.length()<1)
                                {
                                    return null;
                                }
                                else
                                {
                                    char temp [] = (src.toString()).toCharArray();
                                    char result [] = new char[temp.length];
                                    for(int i = 0,j=0; i< temp.length; i++){
                                        if(temp[i] == ' '){
                                            continue;
                                        }else{
                                            result[j++] = temp[i];
                                        }
                                    }
                                    return String.valueOf(result).trim();
                                }

                            }
                        }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_issue:

//                mIntent(this, SendIssueActivity.class);
                checkEdit();

                break;
            case R.id.activity_time_content:
                PickDialogUtils pickDialogUtils = new PickDialogUtils(IssueActivity.this, null);
                pickDialogUtils.dateTimePicKDialog(activity_time_content,true);
                break;
            case R.id.assemble_content:
                PickDialogUtils pickDialog = new PickDialogUtils(IssueActivity.this, null);
                pickDialog.dateTimePicKDialog(assemble_time_content);
                break;
        }
    }

    private void checkEdit() {
        if (!TextUtils.isEmpty(subject_activity_content.getText())) {
            if (!TextUtils.isEmpty(activity_address_content.getText())) {
                if (!TextUtils.isEmpty(activity_time_content.getText())) {
                    if (!TextUtils.isEmpty(assemble_time_content.getText())) {
                        if (!TextUtils.isEmpty(assemble_address_content.getText())) {
                            if (!TextUtils.isEmpty(means_of_transportation_content.getText())) {
                                if (!TextUtils.isEmpty(contact_man_content.getText())) {
                                    if (!TextUtils.isEmpty(contact_phone_content.getText())) {
                                        if (!TextUtils.isEmpty(max_person_content.getText())) {
                                            if (!StrUtil.isMobileNO(contact_phone_content.getText().toString())) {
                                                showToast("联系方式格式不正确");
                                                return;
                                            }
                                            if(activity_time_content.getText().toString().equals(assemble_time_content.getText().toString())){
                                                showToast("报名截止时间,应该大于发布日期");
                                                return;
                                            }
                                            IssueInfo issueInfo = new IssueInfo();
                                            issueInfo.setSubject_activity_content(subject_activity_content.getText().toString());
                                            issueInfo.setActivity_address_content(activity_address_content.getText().toString());
                                            issueInfo.setActivity_time_content(activity_time_content.getText().toString());
                                            issueInfo.setAssemble_address_content(assemble_address_content.getText().toString());
                                            issueInfo.setMeans_of_transportation_content(means_of_transportation_content.getText().toString());
                                            issueInfo.setContact_man_content(contact_man_content.getText().toString());
                                            issueInfo.setContact_phone_content(contact_phone_content.getText().toString());
                                            issueInfo.setMax_person_content(max_person_content.getText().toString());
                                            issueInfo.setCost_issue_content(cost_issue_content.getText().toString());
                                            issueInfo.setMethod_of_payment_content(method_of_payment_content.getText().toString());
                                            issueInfo.setAssemble_time(assemble_time_content.getText().toString());
                                            Intent intent = new Intent(this, SendIssueActivity.class);
                                            intent.putExtra("model", issueInfo);
                                            startActivityForResult(intent, 0);
                                        } else {
                                            toastShow("上限人数");
                                        }
                                    } else {
                                        toastShow("联系人电话");
                                    }
                                } else {
                                    toastShow("联系人");
                                }
                            } else {
                                toastShow("交通工具");
                            }
                        } else {
                            toastShow("集合地点");
                        }
                    } else {
                        toastShow("集合时间");
                    }
                } else {
                    toastShow("活动时间");
                }
            } else {
                toastShow("活动地点");
            }
        } else {
            toastShow("活动主题");
        }

    }

    public void toastShow(String ss) {
        ToastUtil.showShort(context, ss + "不能为空");
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == RESULT_OK) {
            finish();
        }
    }
}
