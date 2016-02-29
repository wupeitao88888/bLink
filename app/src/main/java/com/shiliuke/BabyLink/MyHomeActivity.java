package com.shiliuke.BabyLink;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiliuke.base.ActivitySupport;

/**
 * Created by lc-php1 on 2015/10/30.
 */
public class MyHomeActivity extends ActivitySupport {

    private GridView custom_gridView;
    private TextView user_name;
    private ImageView image_user_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_home_layout);
        initView();
    }

    private void initView() {
        custom_gridView = (GridView) findViewById(R.id.custom_gridView);
        user_name = (TextView) findViewById(R.id.user_name);
        image_user_icon = (ImageView) findViewById(R.id.image_user_icon);
        GridViewAdapter adapter = new GridViewAdapter();
        custom_gridView.setAdapter(adapter);
    }

    class GridViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
