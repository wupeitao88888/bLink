package com.shiliuke.BabyLink;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiliuke.base.ActivitySupport;
import com.shiliuke.global.AppConfig;
import com.shiliuke.global.MApplication;
import com.shiliuke.internet.TaskID;
import com.shiliuke.internet.VolleyListerner;
import com.shiliuke.model.BasicRequest;
import com.shiliuke.utils.DateTimePickDialogUtil;
import com.shiliuke.utils.DialogUtil;
import com.shiliuke.view.LCDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvfl on 2015/11/18.
 */
public class CompleteActivity extends ActivitySupport implements VolleyListerner,OnClickListener {

    private EditText city_edit;
    private EditText area_edit;
    private EditText baby_name;
    private EditText birthday;
    private EditText baby_family;
    private Button register_Btn;
    private RadioButton man_radio;
    private RadioButton girl_radio;
    private String sex;
    private String member_id;
    private LinearLayout girl_torc;
    private LinearLayout man_torc;
    private LCDialog dialog;
    private RadioButton mam_radio;
    private RadioButton father_radio;
    private RadioButton daughter_radio;
    private RadioButton sun_radio;
    private TextView mam_text;
    private TextView father_text;
    private TextView daughter_text;
    private TextView sun_text;
    private String babyfamily;
    private TextView positive;
    private TextView cancle;
    public static int RESULT_CODE = 10001;
    public static int CITY_RESULT_CODE = 10002;
    private RelativeLayout psw_rl;
    private EditText password;
    private EditText commit_password;
    private boolean isVisible;
    private RadioButton other_radio;
    private TextView other_torc_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_activity_layout);
        member_id = getIntent().getStringExtra("member_id");
        isVisible = getIntent().getBooleanExtra("isVisible",false);
        initView();
    }

    private void initView() {
        setCtenterTitle("完善资料");
        psw_rl = (RelativeLayout) findViewById(R.id.psw_rl);
        password = (EditText) findViewById(R.id.password);
        commit_password = (EditText) findViewById(R.id.commit_password);
        if(isVisible){
            psw_rl.setVisibility(View.GONE);
        }
        city_edit = (EditText) findViewById(R.id.city_edit);
        area_edit = (EditText) findViewById(R.id.area_edit);
        baby_name = (EditText) findViewById(R.id.baby_name);
        birthday = (EditText) findViewById(R.id.birthday);
        baby_family = (EditText) findViewById(R.id.baby_family);
        register_Btn = (Button) findViewById(R.id.register_Btn);
        man_radio = (RadioButton) findViewById(R.id.man_radio);
        girl_radio = (RadioButton) findViewById(R.id.girl_radio);
        girl_torc = (LinearLayout) findViewById(R.id.girl_torc);
        man_torc = (LinearLayout) findViewById(R.id.man_torc);
        register_Btn.setOnClickListener(this);
        girl_torc.setOnClickListener(this);
        man_torc.setOnClickListener(this);
        birthday.setOnClickListener(this);
        baby_family.setOnClickListener(this);
        city_edit.setOnClickListener(this);
        area_edit.setOnClickListener(this);
        city_edit.setText("北京");
        sharedPreferencesHelper.putValue("city",city_edit.getText().toString());

    }

    @Override
    public void onResponse(String str, int taskid, Object obj) {
        Log.e("+++++++++++++++",str.toString());
        if( taskid == TaskID.ACTION_COMPLETE_DATA ){
            getCompleteOver(str);
        }else if( taskid == TaskID.ACTION_GET_INFORMATION_DATA){
            getInformationOver(str);
        }
        DialogUtil.stopDialogLoading(this);
    }

    @Override
    public void onResponseError(String error, int taskid) {
//        if( taskid == TaskID.ACTION_COMPLETE_DATA ){
            Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show();
//        }
        DialogUtil.stopDialogLoading(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_Btn:
                if(isVisible){
                    if(psw_rl.getVisibility() == View.VISIBLE){
                        if( TextUtils.isEmpty(password.getText().toString()) ){
                            Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if( TextUtils.isEmpty(commit_password.getText().toString()) ){
                            Toast.makeText(this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if( !password.getText().toString().equals(commit_password.getText().toString()) ){
                            Toast.makeText(this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                if(TextUtils.isEmpty(city_edit.getText().toString())){
                    Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(area_edit.getText().toString())){
                    Toast.makeText(this, "请选择小区", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(baby_name.getText().toString())){
                    Toast.makeText(this, "请填写宝宝昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(birthday.getText().toString())){
                    Toast.makeText(this, "请选择宝宝生日", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(baby_family.getText().toString())){
                    Toast.makeText(this, "请填写与宝宝的关系", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sex)){
                    Toast.makeText(this, "请选择宝宝性别", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<String,String>();
                params.put("city", city_edit.getText().toString());
                params.put("password", commit_password.getText().toString());
                params.put("home", area_edit.getText().toString());
                params.put("baby_nam", baby_name.getText().toString());
                params.put("baby_sex", sex);
                params.put("baby_date", birthday.getText().toString());
                params.put("baby_link", baby_family.getText().toString());
                params.put("member_id", member_id);
                BasicRequest.getInstance().getCompletePost(this,
                        TaskID.ACTION_COMPLETE_DATA, params);
                DialogUtil.startDialogLoading(this);

            break;
            case R.id.man_torc:
                if(girl_radio.isSelected()){
                    girl_radio.setSelected(false);
                }
                man_radio.setSelected(true);
                sex = "0";
            break;
            case R.id.girl_torc:
                if(man_radio.isSelected()){
                    man_radio.setSelected(false);
                }
                girl_radio.setSelected(true);
                sex = "1";
            break;
            case R.id.birthday:
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        CompleteActivity.this, null);
                dateTimePicKDialog.dateTimePicKDialog(birthday);
            break;
            case R.id.baby_family:
                showDialog();
            break;
            case R.id.mam_torc:
                babyfamily = mam_text.getText().toString();
                changeSelectRadio();
                mam_radio.setSelected(true);
                break;
            case R.id.father_torc:
                babyfamily = father_text.getText().toString();
                changeSelectRadio();
                father_radio.setSelected(true);
                break;
            case R.id.daughter_torc:
                babyfamily = daughter_text.getText().toString();
                changeSelectRadio();
                daughter_radio.setSelected(true);
                break;
            case R.id.sun_torc:
                babyfamily = sun_text.getText().toString();
                changeSelectRadio();
                sun_radio.setSelected(true);
            break;
            case R.id.other_torc:
                babyfamily = other_torc_text.getText().toString();
                changeSelectRadio();
                other_radio.setSelected(true);
            break;
            case R.id.positive:
                baby_family.setText(babyfamily);
                dialog.cancel();
            break;
            case R.id.cancle:
                dialog.cancel();
            break;
            case R.id.area_edit:
                Intent intent = new Intent(this,LocationAreaActivity.class);
                startActivityForResult(intent, RESULT_CODE);
            break;
            case R.id.city_edit:
                Intent city_intent = new Intent(this,CityChoiceActivity.class);
                startActivityForResult(city_intent,CITY_RESULT_CODE);
            break;
        }
    }

    private void changeSelectRadio(){
        if(mam_radio.isSelected()){
            mam_radio.setSelected(false);
        }
        if(father_radio.isSelected()){
            father_radio.setSelected(false);
        }
        if(daughter_radio.isSelected()){
            daughter_radio.setSelected(false);
        }
        if(sun_radio.isSelected()){
            sun_radio.setSelected(false);
        }
        if(other_radio.isSelected()){
            other_radio.setSelected(false);
        }
    };

    public void showDialog() {
        if(null == dialog){
            View dialogView = LayoutInflater.from(context).inflate(R.layout.complete_dialog_layout,
                    null);
            dialog = new LCDialog(CompleteActivity.this, R.style.MyDialog, dialogView);
            LinearLayout mam_torc = (LinearLayout) dialogView.findViewById(R.id.mam_torc);
            LinearLayout father_torc = (LinearLayout) dialogView.findViewById(R.id.father_torc);
            LinearLayout daughter_torc = (LinearLayout) dialogView.findViewById(R.id.daughter_torc);
            LinearLayout sun_torc = (LinearLayout) dialogView.findViewById(R.id.sun_torc);
            LinearLayout other_torc = (LinearLayout) dialogView.findViewById(R.id.other_torc);
            mam_radio = (RadioButton) dialogView.findViewById(R.id.mam_radio);
            father_radio = (RadioButton) dialogView.findViewById(R.id.father_radio);
            daughter_radio = (RadioButton) dialogView.findViewById(R.id.daughter_radio);
            sun_radio = (RadioButton) dialogView.findViewById(R.id.sun_radio);
            other_radio = (RadioButton) dialogView.findViewById(R.id.other_radio);

            mam_text = (TextView) dialogView.findViewById(R.id.mam_text);
            father_text = (TextView) dialogView.findViewById(R.id.father_text);
            daughter_text = (TextView) dialogView.findViewById(R.id.daughter_text);
            sun_text = (TextView) dialogView.findViewById(R.id.sun_text);
            other_torc_text = (TextView) dialogView.findViewById(R.id.other_torc_text);

            positive = (TextView) dialogView.findViewById(R.id.positive);
            cancle = (TextView) dialogView.findViewById(R.id.cancle);
            mam_torc.setOnClickListener(this);
            father_torc.setOnClickListener(this);
            daughter_torc.setOnClickListener(this);
            sun_torc.setOnClickListener(this);
            other_torc.setOnClickListener(this);
            positive.setOnClickListener(this);
            cancle.setOnClickListener(this);
        }
        dialog.show();

    }



    /**
     * 完善资料解析
     * @param object
     */
    private void getCompleteOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        String adtasObject = jsonObject.getString("datas");
        if(code != null && "0".equals(code)){
            sharedPreferencesHelper.putValue("member_id", adtasObject);
            DialogUtil.startDialogLoading(this);
            Map<String, String> params = new HashMap<String,String>();
            params.put("member_id", member_id);
            BasicRequest.getInstance().getInformationPost(CompleteActivity.this, TaskID.ACTION_GET_INFORMATION_DATA,
                    params);
        }else if(code != null && "1".equals(code)){
            Toast.makeText(context,adtasObject.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 获取个人信息
     * @param object
     */
    private void getInformationOver(Object object) {
        JSONObject jsonObject = JSON.parseObject(object.toString());
        String code = jsonObject.getString("code");
        if(code != null && "0".equals(code)){
            JSONObject adtasObject = jsonObject.getJSONObject("datas");
            Information information = JSON.parseObject(adtasObject.toString(), Information.class);
            AppConfig.loginInfo = information;
            sharedPreferencesHelper.putValue("member_id",information.getMember_id());
            sharedPreferencesHelper.putValue("member_name",information.getMember_name());
            sharedPreferencesHelper.putValue("password",information.getPassword());
            sharedPreferencesHelper.putValue("mobile",information.getMobile());
            sharedPreferencesHelper.putValue("openid",information.getOpenid());
            sharedPreferencesHelper.putValue("member_avar",information.getMember_avar());
            sharedPreferencesHelper.putValue("city",information.getCity());
            sharedPreferencesHelper.putValue("home",information.getHome());
            sharedPreferencesHelper.putValue("home2",information.getHome2());
            sharedPreferencesHelper.putValue("position",information.getPosition());
            sharedPreferencesHelper.putValue("baby_nam",information.getBaby_nam());
            sharedPreferencesHelper.putValue("baby_sex",information.getBaby_sex());
            sharedPreferencesHelper.putValue("baby_date",information.getBaby_date());
            sharedPreferencesHelper.putValue("baby_link",information.getBaby_link());
            sharedPreferencesHelper.putValue("add_time",information.getAdd_time());
            sharedPreferencesHelper.putValue("login_time",information.getLogin_time());
            mIntent(CompleteActivity.this, MainTab.class);
            finish();
        }else if(code != null && "1".equals(code)){
            String adtasObject = jsonObject.getString("datas");
            Toast.makeText(context,adtasObject.toString(),Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE && RESULT_OK == resultCode){
            area_edit.setText(data.getExtras().getString("area_text"));
        } else if (requestCode == CITY_RESULT_CODE && RESULT_OK == resultCode){
            city_edit.setText(data.getExtras().getString("city_text"));
            MApplication.locationBD.setCity(data.getExtras().getString("city_text"));
        }
    }
}
