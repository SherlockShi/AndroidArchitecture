package com.sherlockshi.androidarchitecture.business.meizi_list;

import com.sherlockshi.androidarchitecture.base.callback.Callback;
import com.sherlockshi.androidarchitecture.business.ApiService;
import com.sherlockshi.androidarchitecture.business.entity.BaseEntity;
import com.sherlockshi.androidarchitecture.business.entity.MeiZi;
import com.sherlockshi.androidarchitecture.global.Constants;
import com.sherlockshi.androidarchitecture.util.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: SherlockShi
 * Date:   2016-11-16 20:44
 * Description:
 */
public class MeiZiListManager {

    private volatile static MeiZiListManager instance;

    private MeiZiListManager() {
    }

    public static MeiZiListManager getInstance() {
        if (instance == null) {
            synchronized (MeiZiListManager.class) {
                if (instance == null) {
                    instance = new MeiZiListManager();
                }
            }
        }
        return instance;
    }

    public void getMeiZiList(String quantityPerPage, String pageNumber, final Callback<List<MeiZi>> callback) {
        if (callback == null) {
            return;
        }

        Retrofit retrofit = RetrofitClient.INSTANCE.getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);

        Observable<BaseEntity<List<MeiZi>>> observable = apiService.getMeiZiList(quantityPerPage, pageNumber);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseEntity<List<MeiZi>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail(Constants.ErrorNo.ServerError, "");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(BaseEntity<List<MeiZi>> respEntity) {
                        if (respEntity != null && !respEntity.isError()) {
                            callback.onSuccess(respEntity.getResults());
                        }
                    }
                });
    }
}
