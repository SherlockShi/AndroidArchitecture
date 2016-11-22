package com.sherlockshi.androidarchitecture.business;


import com.sherlockshi.androidarchitecture.business.entity.BaseEntity;
import com.sherlockshi.androidarchitecture.business.entity.MeiZi;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Author: SherlockShi
 * Date:   2016-10-26 11:11
 * Description:
 */

public interface ApiService {

    // 妹子列表
    @GET("福利/{quantityPerPage}/{pageNumber}")
    Observable<BaseEntity<List<MeiZi>>> getMeiZiList(@Path("quantityPerPage") String quantityPerPage,
                                                     @Path("pageNumber") String pageNumber);

}
