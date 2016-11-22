package com.sherlockshi.androidarchitecture.util;

import android.content.Context;

import com.sherlockshi.androidarchitecture.R;
import com.sherlockshi.androidarchitecture.base.view.IBaseView;
import com.sherlockshi.androidarchitecture.global.Constants;


/**
 * Author: SherlockShi
 * Date:   2016-10-30 22:46
 * Description:
 */

public class ErrorUtil {

    public static void processErrorMessage(Context context, int errorNo, String errorMsg, IBaseView view) {
        if (view == null) {
            return;
        }

        if (Constants.ErrorNo.ServerError == errorNo) {
            view.showMessage(context.getString(R.string.server_error_request_fail_please_retry_later));
        } else if (Constants.ErrorNo.ServerReturnErrorMsg == errorNo) {
            view.showMessage(errorMsg);
        }
    }
}
