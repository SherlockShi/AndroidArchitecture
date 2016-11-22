package com.sherlockshi.androidarchitecture.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sherlockshi.androidarchitecture.R;
import com.sherlockshi.androidarchitecture.business.entity.MeiZi;
import com.sherlockshi.androidarchitecture.business.meizi_list.MeiZiListContract;
import com.sherlockshi.androidarchitecture.business.meizi_list.MeiZiListPresenter;
import com.sherlockshi.androidarchitecture.global.Config;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeiZiListContract.IView {
    
    private static final String QUANTITY_PER_PAGE = "10";
    
    private int mCurrentPage = 1;

    private ProgressBar mProgressBar;

    private RecyclerView rvMeiZi;
    private BaseQuickAdapter<MeiZi> mAdapterMeiZi;
    private List<MeiZi> mMeiZiList = new ArrayList<MeiZi>();
    
    private MeiZiListPresenter mMeiZiListPresenter;

    private int mScreenWidth;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    private void initData() {
        mMeiZiListPresenter = new MeiZiListPresenter(this, this);
        mMeiZiListPresenter.getMeiZiList(QUANTITY_PER_PAGE, mCurrentPage + "");
        
        initMeiZiLayout();
    }

    private void initMeiZiLayout() {
        mAdapterMeiZi = new BaseQuickAdapter<MeiZi>(R.layout.rv_item_meizi, mMeiZiList) {
            @Override
            public void convert(BaseViewHolder holder, final MeiZi object) {
                if (object != null) {
                    Picasso.with(MainActivity.this).load(object.getUrl() + Config.BASE_URL_SUBFIX + mScreenWidth/2).into((ImageView) (holder.getView(R.id.iv_image)));

                    int indexOfT = object.getPublishedAt().indexOf("T");

                    holder.setText(R.id.tv_content, object.getPublishedAt().substring(0, indexOfT));
                }
            }
        };

        mAdapterMeiZi.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showFullScreenImage(position);
            }
        });

        rvMeiZi.setAdapter(mAdapterMeiZi);
    }

    private void showFullScreenImage(int position) {
        ImageView ivImage = null;

        View popupView = getLayoutInflater().inflate(R.layout.pw_meizi, null);
        mPopupWindow = new PopupWindow(popupView, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        ivImage = (ImageView) popupView.findViewById(R.id.iv_image);

        if (ivImage != null) {
            showLoading();
            Picasso.with(MainActivity.this)
                    .load(mMeiZiList.get(position).getUrl() + Config.BASE_URL_SUBFIX + mScreenWidth)
                    .into(ivImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            hideLoading();
                        }

                        @Override
                        public void onError() {
                            hideLoading();
                            showMessage(getString(R.string.load_image_fail));
                        }
                    });
        }

        mPopupWindow.showAtLocation(findViewById(R.id.clyt_root), Gravity.TOP | Gravity.START, 0, 0);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    private void initView() {
        getScreenWidth();

        // ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        // RecyclerView
        rvMeiZi = (RecyclerView) findViewById(R.id.rv_meizi);
        //设置布局管理器
        rvMeiZi.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        //设置Item增加、移除动画
        rvMeiZi.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void updateMeiZiList(List<MeiZi> object) {
        mMeiZiList.clear();
        mMeiZiList.addAll(object);
        mAdapterMeiZi.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void getScreenWidth() {
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();
    }
}
