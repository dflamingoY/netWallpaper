package com.mingo.baselibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mingo.baselibrary.R;


public class ViewMoreGroupView extends BaseLayout {
    private Context mContext;
    private Drawable mDrawable;
    private String mTitleName;
    private TextView mTvMsgCount;
    private int mPixelSize;
    private int secondColor = Color.parseColor("#999999");
    private int secondSize = 13;
    private int textColor = Color.parseColor("#c4c9e2");
    private boolean mIsShowArrow = true;
    private String mSecondTitle;
    private View mViewHint;
    private TextView mTvPrivate;
    private String mPrivateTitle;
    private TextView tvTitleName;
    private View mArrow;

    private ImageView ivTitleIcon;
    private Drawable mTitleIconDraw;

    public ViewMoreGroupView(@NonNull Context context) {
        this(context, null, 0);
    }

    public ViewMoreGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewMoreGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ViewMoreGroupView);
        mDrawable = array.getDrawable(R.styleable.ViewMoreGroupView_title_img);
        mTitleName = array.getString(R.styleable.ViewMoreGroupView_title_name);
        mPixelSize = array.getInt(R.styleable.ViewMoreGroupView_title_size, 13);
        secondColor = array.getColor(R.styleable.ViewMoreGroupView_second_color, secondColor);
        secondSize = array.getInt(R.styleable.ViewMoreGroupView_second_Size, 13);
        mIsShowArrow = array.getBoolean(R.styleable.ViewMoreGroupView_show_arrow, true);
        mSecondTitle = array.getString(R.styleable.ViewMoreGroupView_second_title);
        mPrivateTitle = array.getString(R.styleable.ViewMoreGroupView_privaty_Title);
        mTitleIconDraw = array.getDrawable(R.styleable.ViewMoreGroupView_title_icon);
        array.recycle();
        initView();
    }

    private void initView() {
        mTvMsgCount = findViewById(R.id.tv_MsgCount);
        mTvPrivate = findViewById(R.id.tv_Private);
        mTvMsgCount.setTextColor(secondColor);
        mTvMsgCount.setTextSize(secondSize);
        if (!TextUtils.isEmpty(mSecondTitle)) {
            mTvMsgCount.setText(mSecondTitle);
        }
        tvTitleName = findViewById(R.id.tv_TitleName);
        mViewHint = findViewById(R.id.viewHint);
        tvTitleName.setTextSize(mPixelSize);
        mArrow = findViewById(R.id.iv_Arrow);
        mArrow.setVisibility(mIsShowArrow ? VISIBLE : GONE);
        ivTitleIcon = findViewById(R.id.ivIcon);
        if (!TextUtils.isEmpty(mTitleName)) {
            tvTitleName.setText(mTitleName);
        }
        if (!TextUtils.isEmpty(mPrivateTitle)) {
            mTvPrivate.setText(mPrivateTitle);
        }
        if (null != mTitleIconDraw) {
            ivTitleIcon.setVisibility(VISIBLE);
            ivTitleIcon.setImageDrawable(mTitleIconDraw);
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_more_group_view;
    }

    public void setTitle(String title) {
        try {
            if (!TextUtils.isEmpty(title))
                tvTitleName.setText(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMsgCount(String count) {
        if (TextUtils.isEmpty(count)) {
            mTvMsgCount.setText("");
        } else
            mTvMsgCount.setText(count);
    }

    public String getText() {
        if (mTvMsgCount != null)
            return mTvMsgCount.getText().toString().trim();
        else return "";
    }

    /**
     * 设置提醒是否显示
     *
     * @param isVisible
     */
    public void setHintVisible(boolean isVisible) {
        if (mViewHint != null) {
            mViewHint.setVisibility(isVisible ? VISIBLE : GONE);
        }
    }

    /**
     * 设置箭头是否可见
     *
     * @param visible
     */
    public void setArrowVisible(int visible) {
        mArrow.setVisibility(visible);
    }

    public void setSecond_title(String title) {
        mSecondTitle = title;
        if (null != mTvMsgCount) {
            mTvMsgCount.setText(mSecondTitle);
        }
    }
}
