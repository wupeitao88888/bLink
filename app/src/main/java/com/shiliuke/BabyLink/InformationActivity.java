package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/10/27.
 */
public class InformationActivity extends ActivitySupport {

    private EditText edit_city;
    private EditText edit_area;
    private EditText edit_name;
    private EditText edit_sex;
    private EditText edit_date;
    private EditText edit_relations;
    private Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_welcome);
        initView();
    }

    private void initView() {
        setCtenterTitle(getResources().getString(R.string.information));
        edit_city = (EditText) findViewById(R.id.edit_city);
        edit_area = (EditText) findViewById(R.id.edit_area);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_sex = (EditText) findViewById(R.id.edit_sex);
        edit_date = (EditText) findViewById(R.id.edit_date);
        edit_relations = (EditText) findViewById(R.id.edit_relations);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEmpty();
            }
        });

    }

    private void isEmpty(){
        if(TextUtils.isEmpty(edit_city.getText().toString())){
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
            return;
        }
    }

}
