package com.shiliuke.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shiliuke.BabyLink.R;
import com.shiliuke.adapter.FaceAdapter;
import com.shiliuke.adapter.FacePagerAdapter;
import com.shiliuke.adapter.MessagePlusAdapter;
import com.shiliuke.adapter.ViewPagerAdapter;
import com.shiliuke.bean.ChatEmoji;
import com.shiliuke.bean.MessagePlusEndity;
import com.shiliuke.model.OnSendMsg;
import com.shiliuke.utils.FaceConversionUtil;
import com.shiliuke.utils.L;
import com.shiliuke.utils.ToastUtil;


@SuppressLint("NewApi")
public class FaceRelativeLayout extends RelativeLayout implements
        OnItemClickListener, OnClickListener {

    private static final String TAG = "FaceRelativeLayout";
    private Context context;
    private Button send;
    /**
     * 表情页的监听事件
     */
    private OnCorpusSelectedListener mListener;
    /**
     * 显示表情页的viewpager
     */
    private ViewPager vp_face;
    /**
     * 表情页界面集合
     */
    private ArrayList<View> pageViews;
    /**
     * 加号页面集合
     */
    List<List<MessagePlusEndity>> function_list = null;

    /**
     * 游标显示布局
     */
    private LinearLayout layout_point;

    /**
     * 游标点集合
     */
    private ArrayList<ImageView> pointViews;

    /**
     * 表情集合
     */
    private List<List<ChatEmoji>> emojis;

    /**
     * 表情区域
     */
    private View view;

    /**
     * 输入框
     */
    private EditText et_sendmessage;

    /**
     * 表情数据填充器
     */
    private List<FaceAdapter> faceAdapters;

    /**
     * 功能数据填充器
     */
    private List<MessagePlusAdapter> functionAdapter_list = null;

    /**
     * 当前表情页
     */
    private int current = 0;

    /**
     * // 发送其他数据
     */
    ImageButton faceBtn = null;

    /**
     * 功能选中监听事件
     */
    FunctionClickListener mFunctionClickListener = null;

    OnItemClickListener functionOnItemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            MessagePlusEndity item = function_list.get(current).get(arg2);
            if (mFunctionClickListener != null) {
                mFunctionClickListener.onClick(item);

            }
        }
    };
    private OnSendMsg onSendMsg;
    private InputMethodManager mInputMethodManager;

    public void setmListener(OnCorpusSelectedListener mListener) {
        this.mListener = mListener;
    }

    public FaceRelativeLayout(Context context) {
        super(context);
        this.context = context;
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = mInputMethodManager.isActive();
//        if(!isOpen)
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = mInputMethodManager.isActive();
//        if(!isOpen)
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public FaceRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = mInputMethodManager.isActive();
//        if(!isOpen)
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void setOnCorpusSelectedListener(OnCorpusSelectedListener listener) {
        mListener = listener;
    }

    public void closeInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputMethodManager != null && this.getCurrentFocus() != null) {
        inputMethodManager.hideSoftInputFromWindow(et_sendmessage.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                }
    }


    @Override
    public void onClick(View v) {
        if (R.id.btn_face == v.getId()) {
            Init_viewPager();
            Init_Point();
            Init_Data();
            view.findViewById(R.id.btn_face);

            // 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);

                mInputMethodManager.toggleSoftInput(0,
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                view.setVisibility(View.VISIBLE);
                mInputMethodManager.hideSoftInputFromWindow(
                        et_sendmessage.getWindowToken(), 0);
            }
        } else if (R.id.et_sendmessage == v.getId()) {
            // 隐藏表情选择框
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
        } else if (R.id.btn_send == v.getId()) {
            if (!TextUtils.isEmpty(et_sendmessage.getText().toString())) {
                if (onSendMsg != null) {
                    onSendMsg.OnSccessCallBack(et_sendmessage.getText().toString());
                }
            } else {
                ToastUtil.showShort(context, "你还没有填写评论");
            }

        }

    }

    public void setOnSendMsgListener(OnSendMsg onSendMsg) {
        this.onSendMsg = onSendMsg;
    }





    /**
     * 表情选择监听
     */
    public interface OnCorpusSelectedListener {

        void onCorpusSelected(ChatEmoji emoji);

        void onCorpusDeleted();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        emojis = FaceConversionUtil.getInstace().emojiLists;

        function_list = new ArrayList<List<MessagePlusEndity>>();
        List<MessagePlusEndity> list = new ArrayList<MessagePlusEndity>();
        int[] a = new int[]{R.drawable.chat_photo_style,
                R.drawable.chat_camera_style, R.drawable.chat_u_style};
        String[] str = new String[]{"pictrue", "camera", "comment"};
        for (int i = 0; i < 3; i++) {
            MessagePlusEndity item = new MessagePlusEndity();
            item.icon = a[i];
            item.name = str[i];
            item.position = i;
            list.add(item);
        }

        function_list.add(list);
        onCreate();
    }

    private void onCreate() {
        Init_View();// 初始化控件
    }

    /**
     * 隐藏表情选择框
     */
    public boolean hideFaceView() {
        // 隐藏表情选择框
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 初始化控件
     */
    private void Init_View() {
        vp_face = (ViewPager) findViewById(R.id.vp_contains);
        et_sendmessage = (EditText) findViewById(R.id.et_sendmessage);
        layout_point = (LinearLayout) findViewById(R.id.iv_image);
        et_sendmessage.setOnClickListener(this);
        findViewById(R.id.btn_face).setOnClickListener(this);
        faceBtn = (ImageButton) findViewById(R.id.btn_face);
        faceBtn.setOnClickListener(this);
        view = findViewById(R.id.ll_facechoose);
        et_sendmessage.addTextChangedListener(watcher);
        send = (Button) findViewById(R.id.btn_send);
        send.setOnClickListener(this);
        et_sendmessage.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    view.setVisibility(View.GONE);
                }
            }
        });
        mInputMethodManager.showSoftInput(et_sendmessage, InputMethodManager.SHOW_FORCED);
    }


    public void setTextHinit(String ss) {
        et_sendmessage.setHint(ss);
    }

    public void clear() {
        et_sendmessage.setText("");
    }


    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {

            // TODO Auto-generated method stub
            // send.setVisibility(View.VISIBLE);
            // addBtn.setVisibility(View.INVISIBLE);
            // addBtn.setFocusable(false);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,

                                      int after) {

            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,

                                  int count) {

            // TODO Auto-generated method stub

//            String etStr = et_sendmessage.getText().toString().trim(); // 获得输入的值
//            int selectionStart = et_sendmessage.getSelectionStart();
//            if (selectionStart != 0) {
//                send.setVisibility(View.VISIBLE);
//                addBtn.setVisibility(View.INVISIBLE);
//            } else {
//                send.setVisibility(View.INVISIBLE);
//                addBtn.setVisibility(View.VISIBLE);
//            }
        }
    };

    /**
     * 初始化显示表情的viewpager
     */
    public void Init_viewPager() {
        pageViews = new ArrayList<View>();
        // 左侧添加空页
        View nullView1 = new View(context);
        // 设置透明背景
        nullView1.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView1);

        // 中间添加表情页

        faceAdapters = new ArrayList<FaceAdapter>();


        for (int i = 0; i < emojis.size() - 1; i++) {
            GridView view = new GridView(context);
            FaceAdapter adapter = new FaceAdapter(context, emojis.get(i));
            view.setAdapter(adapter);
            faceAdapters.add(adapter);

            view.setOnItemClickListener(this);
            view.requestFocus();
            view.setNumColumns(7);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(5, 0, 5, 0);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
        }

        // 右侧添加空页面
        View nullView2 = new View(context);
        // 设置透明背景
        nullView2.setBackgroundColor(Color.TRANSPARENT);
        pageViews.add(nullView2);
    }

    /**
     * 初始化游标
     */
    public void Init_Point() {

        pointViews = new ArrayList<ImageView>();
        layout_point.removeAllViews();
        ImageView imageView;
        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(R.mipmap.d1);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            layoutParams.width = 8;
            layoutParams.height = 8;
            layout_point.addView(imageView, layoutParams);
            if (i == 0 || i == pageViews.size() - 1) {
                imageView.setVisibility(View.GONE);
            }
            if (i == 1) {
                imageView.setBackgroundResource(R.mipmap.d2);
            }
            pointViews.add(imageView);

        }
    }


    /**
     * 填充数据
     */
    private void Init_Data() {
        vp_face.setAdapter(new ViewPagerAdapter(pageViews));

        vp_face.setCurrentItem(1);
        current = 0;
        vp_face.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {

                current = arg0 - 1;
                // 描绘分页点
                draw_Point(arg0);
                // 如果是第一屏或者是最后一屏禁止滑动，其实这里实现的是如果滑动的是第一屏则跳转至第二屏，如果是最后一屏则跳转到倒数第二屏.
                if (arg0 == pointViews.size() - 1 || arg0 == 0) {
                    if (arg0 == 0) {
                        vp_face.setCurrentItem(arg0 + 1);// 第二屏 会再次实现该回调方法实现跳转.
                        pointViews.get(1).setBackgroundResource(R.mipmap.d1);

                    } else {
                        vp_face.setCurrentItem(arg0 - 1);// 倒数第二屏
                        pointViews.get(arg0 - 1).setBackgroundResource(
                                R.mipmap.d2);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    /**
     * 绘制游标背景
     */
    public void draw_Point(int index) {
        for (int i = 1; i < pointViews.size(); i++) {
            if (index == i) {
                pointViews.get(i).setBackgroundResource(R.mipmap.d2);
            } else {
                pointViews.get(i).setBackgroundResource(R.mipmap.d1);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(arg2);
        if (emoji.getId() == R.drawable.face_del_icon) {
            int selection = et_sendmessage.getSelectionStart();
            String text = et_sendmessage.getText().toString();

            if (selection > 0) {
                String text2 = text.substring(selection - 1);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[");
                    int end = selection;
                    et_sendmessage.getText().delete(start, end);
                    return;
                }
                et_sendmessage.getText().delete(selection - 1, selection);
            }
        }
        if (!TextUtils.isEmpty(emoji.getCharacter())) {
            if (mListener != null)
                mListener.onCorpusSelected(emoji);
            SpannableString spannableString = FaceConversionUtil.getInstace()
                    .addFace(getContext(), emoji.getId(), emoji.getCharacter());
            et_sendmessage.append(spannableString);
        }

    }

    // 设置功能点击监听器
    public void setFunctionClickListener(FunctionClickListener listener) {
        mFunctionClickListener = listener;
    }

    // 点击功能按钮监听事件
    public interface FunctionClickListener {
        void onClick(MessagePlusEndity item);
    }
}