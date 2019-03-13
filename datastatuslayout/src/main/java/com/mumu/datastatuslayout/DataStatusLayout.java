package com.mumu.datastatuslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * edie create on 2019/3/8
 */
public class DataStatusLayout extends FrameLayout implements View.OnClickListener {
    public final static int LOADING = 0x100;
    public final static int SUCCESS = 0x101;
    public final static int EMPTY = 0x102;
    public final static int ERROR = 0x103;
    public final static int NO_NETWORK = 0x104;


    private int mLoadingViewId;
    private int mErrorViewId;
    private int mEmptyViewId;
    private int mNoNetworkViewId;

    private View mContentView;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mNoNetworkView;

    private Context mContext;

    private int mCurrentState;
    private OnReloadListener mOnReloadListener;

    public DataStatusLayout(@NonNull Context context) {
        super(context);
    }

    public DataStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DataStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DataStatusLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DataStatusLayout);
        mLoadingViewId = typedArray.getResourceId(R.styleable.DataStatusLayout_loading_view, NO_ID);
        mErrorViewId = typedArray.getResourceId(R.styleable.DataStatusLayout_error_view, NO_ID);
        mEmptyViewId = typedArray.getResourceId(R.styleable.DataStatusLayout_empty_view, NO_ID);
        mNoNetworkViewId = typedArray.getResourceId(R.styleable.DataStatusLayout_no_net_work_view, NO_ID);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException(getClass().getSimpleName() + " can host only one direct child");
        }
        initStatusView();
    }

    private void initStatusView() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
            mContentView.setVisibility(GONE);
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLoadingView = inflater.inflate(mLoadingViewId == NO_ID ? R.layout.view_default_loading : mLoadingViewId, this, false);
        mErrorView = inflater.inflate(mErrorViewId == NO_ID ? R.layout.view_default_error : mErrorViewId, this, false);
        mEmptyView = inflater.inflate(mEmptyViewId == NO_ID ? R.layout.view_default_empty : mEmptyViewId, this, false);
        mNoNetworkView = inflater.inflate(mNoNetworkViewId == NO_ID ? R.layout.view_default_no_net_work : mNoNetworkViewId, this, false);

        mLoadingView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mNoNetworkView.setVisibility(GONE);

        addView(mLoadingView);
        addView(mErrorView);
        addView(mEmptyView);
        addView(mNoNetworkView);

        View emptyReload = findViewById(R.id.empty_reload);
        if (emptyReload != null) {
            emptyReload.setOnClickListener(this);
        }
        View errorReload = findViewById(R.id.error_reload);
        if (errorReload != null) {
            errorReload.setOnClickListener(this);
        }
        View noNetworkReload = findViewById(R.id.no_network_reload);
        if (noNetworkReload != null) {
            noNetworkReload.setOnClickListener(this);
        }
    }

    public void setStatus(@Relish int state) {
        mCurrentState = state;


        boolean b0 = state == SUCCESS;
        if (mContentView != null) {
            mContentView.setVisibility(b0 ? VISIBLE : GONE);
        }
        boolean b = state == LOADING;
        mLoadingView.setVisibility(b ? VISIBLE : GONE);
        boolean b1 = state == EMPTY;
        boolean b2 = state == ERROR;
        boolean b3 = state == NO_NETWORK;
        mNoNetworkView.setVisibility(b3 ? VISIBLE : GONE);
        mErrorView.setVisibility(b2 ? VISIBLE : GONE);
        mEmptyView.setVisibility(b1 ? VISIBLE : GONE);
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        mOnReloadListener = onReloadListener;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.empty_reload || i == R.id.error_reload || i == R.id.no_network_reload) {
            if (mOnReloadListener != null) {
                mOnReloadListener.onReload(v, mCurrentState);
            }

        }
    }


    @IntDef({LOADING, SUCCESS, EMPTY, ERROR, NO_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Relish {
    }

    public interface OnReloadListener {
        void onReload(View v, int status);
    }
}
