package com.sherlockshi.androidarchitecture.business.meizi_list;

import android.content.Context;

import com.sherlockshi.androidarchitecture.base.callback.Callback;
import com.sherlockshi.androidarchitecture.business.entity.MeiZi;
import com.sherlockshi.androidarchitecture.util.ErrorUtil;

import java.util.List;

/**
 * Author: SherlockShi
 * Date:   2016-11-16 20:52
 * Description:
 */
public class MeiZiListPresenter implements MeiZiListContract.IPresenter {

    private Context mContext;
    private MeiZiListContract.IView mView;
    private MeiZiListManager mMeiZiListManager = MeiZiListManager.getInstance();

    public MeiZiListPresenter(Context context, MeiZiListContract.IView view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getMeiZiList(String quantityPerPage, String pageNumber) {
        mView.showLoading();

        mMeiZiListManager.getMeiZiList(quantityPerPage, pageNumber, new Callback<List<MeiZi>>() {
            @Override
            public void onSuccess(List<MeiZi> object) {
                mView.updateMeiZiList(object);
                mView.hideLoading();
            }

            @Override
            public void onFail(int errorNo, String errorMsg) {
                ErrorUtil.processErrorMessage(mContext, errorNo, errorMsg, mView);
                mView.hideLoading();
            }
        });
    }
}
