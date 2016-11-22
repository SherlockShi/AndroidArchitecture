package com.sherlockshi.androidarchitecture.business.meizi_list;

import com.sherlockshi.androidarchitecture.base.presenter.IBasePresenter;
import com.sherlockshi.androidarchitecture.base.view.IBaseView;
import com.sherlockshi.androidarchitecture.business.entity.MeiZi;

import java.util.List;

/**
 * Author: SherlockShi
 * Date:   2016-11-16 20:36
 * Description:
 */
public class MeiZiListContract {

    public interface IView extends IBaseView {
        void updateMeiZiList(List<MeiZi> object);
    }

    interface IPresenter extends IBasePresenter {
        void getMeiZiList(String quantityPerPage, String pageNumber);
    }
}
