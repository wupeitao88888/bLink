/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shiliuke.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.shiliuke.BabyLink.R;
import com.shiliuke.view.LCDialog;


// TODO: Auto-generated Javadoc

public class DialogUtil {

    private static LCDialog dialog;

    public static void startDialogLoading(Context context) {
        startDialogLoading(context, true);
    }

    public static void startDialogLoading(Context context, boolean isCancelable) {
        try {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.progress_dialog,
                    null);
            dialog = new LCDialog(context, R.style.PgDialog, dialogView);
            if (!isCancelable) {
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
            }
            dialog.show();
        } catch (Exception e) {

        }
    }

    public static void stopDialogLoading(Context context) {
        try {
            if (dialog != null) {

                dialog.cancel();
            }
        } catch (Exception e) {

        }
    }
}
