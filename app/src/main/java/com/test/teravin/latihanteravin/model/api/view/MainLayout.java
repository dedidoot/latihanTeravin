package com.test.teravin.latihanteravin.model.api.view;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.teravin.latihanteravin.R;


/**
 * Created by dedi on 05/12/16.
 * Happy Coding
 * <p>
 * Di kelas MainLayout bisa dipakai jika memakai recycler view
 * fungsi utama bisa menampilkan loading, ketika oncreate
 */

public class MainLayout extends FrameLayout {

    public int pagination = 1;
    public RecyclerView list;
    public SwipeRefreshLayout swipe_refresh;
    public RelativeLayout layout_loading;
    private ProgressBar pre_loader;
    private TextView text_error, text_error2;
    private Context context;
    public String CONNECTION_ERROR = "-1", DATA_EMPTY = "204", INTERNAL_SERVER_ERROR = "500";
    private OnClickTxtError onClickTxtError;
    private LinearLayout linear_status;
    public String statusLayout = "";

    public MainLayout(Context context) {
        super(context);
        init(context);
    }

    public MainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.main_layout_recycler_view, this);
        list = (RecyclerView) findViewById(R.id.list);
        swipe_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipe_refresh.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);

        layout_loading = (RelativeLayout) findViewById(R.id.layout_loading);
        pre_loader = (ProgressBar) findViewById(R.id.pre_loader);
        text_error = (TextView) findViewById(R.id.text_error);
        text_error2 = (TextView) findViewById(R.id.text_error2);
        linear_status = (LinearLayout) findViewById(R.id.linear_status);
        linear_status.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickTxtError != null) {
                    onClickTxtError.refreshPage();
                }
            }
        });
    }

    public void refreshPage(OnClickTxtError onClickTxtError) {
        this.onClickTxtError = onClickTxtError;
    }

    public void showLoading() {
        swipe_refresh.setVisibility(View.GONE);
        layout_loading.setVisibility(View.VISIBLE);
        pre_loader.setVisibility(View.VISIBLE);
    }

    public void refreshThisPage() {
        hideLoading();
        showLoading();
    }

    public void showSnackbar() {
        Snackbar.make(swipe_refresh, context.getString(R.string.koneksi_error), Snackbar.LENGTH_SHORT).show();
    }

    public void hideLoading() {
        layout_loading.setVisibility(View.GONE);
        swipe_refresh.setVisibility(View.VISIBLE);
        linear_status.setVisibility(GONE);
        statusLayout = "";
        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false);
        }
    }

    public void status(CharSequence status) {
        if (status.equals(DATA_EMPTY)) {
            layout_loading.setVisibility(View.VISIBLE);
            swipe_refresh.setVisibility(View.GONE);
            pre_loader.setVisibility(View.GONE);
            linear_status.setClickable(false);
            linear_status.setVisibility(VISIBLE);
            text_error.setText(context.getString(R.string.data_kosong));
            text_error2.setVisibility(GONE);
            statusLayout = DATA_EMPTY;
        } else if (status.equals(CONNECTION_ERROR)) {
            layout_loading.setVisibility(View.VISIBLE);
            swipe_refresh.setVisibility(View.GONE);
            pre_loader.setVisibility(View.GONE);
            linear_status.setClickable(true);
            linear_status.setVisibility(VISIBLE);
            text_error.setText(context.getString(R.string.koneksi_error));
            text_error2.setVisibility(VISIBLE);
            statusLayout = CONNECTION_ERROR;
        } else if (status.equals(INTERNAL_SERVER_ERROR)) {
            layout_loading.setVisibility(View.VISIBLE);
            swipe_refresh.setVisibility(View.GONE);
            pre_loader.setVisibility(View.GONE);
            linear_status.setClickable(true);
            linear_status.setVisibility(VISIBLE);
            text_error.setText(context.getString(R.string.server_error));
            text_error2.setVisibility(VISIBLE);
            statusLayout = INTERNAL_SERVER_ERROR;
        } else {
            layout_loading.setVisibility(View.VISIBLE);
            swipe_refresh.setVisibility(View.GONE);
            pre_loader.setVisibility(View.GONE);
            linear_status.setClickable(true);
            linear_status.setVisibility(VISIBLE);
            text_error.setText(status);
            text_error2.setVisibility(VISIBLE);
            statusLayout = INTERNAL_SERVER_ERROR;
        }
        if (swipe_refresh.isRefreshing()) {
            swipe_refresh.setRefreshing(false);
        }
    }

    public void setPagination(int count) {
        if (count >= 20) {
            pagination = pagination + 1;
        }
    }

    public interface OnClickTxtError {
        void refreshPage();
    }
}
