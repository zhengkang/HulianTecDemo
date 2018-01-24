package com.hulian.huliantecdemo.database;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hulian.huliantecdemo.R;


/**
 * Created by Administrator on 2017/12/21.
 */

public class PopupWindowUtils {
    /**
     * 弹窗
     */
    private static PopupWindow popupWindow1;





    /**
     * 删除用户
     *
     * @param v
     */
    public static void showDelete(View v, final AddressBookMessageActivity addressBookMessageActivity) {
        View contentView;
        LayoutInflater mLayoutInflater = LayoutInflater.from(addressBookMessageActivity);
        contentView = mLayoutInflater.inflate(R.layout.pop_shanchu, null);
        popupWindow1 = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button negativeButton = (Button) contentView.findViewById(R.id.negativeButton);
        Button positiveButton = (Button) contentView.findViewById(R.id.positiveButton);
        TextView msg=(TextView) contentView.findViewById(R.id.text_msg);
        msg.setText("确定删除该用户吗？");
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow1.dismiss();
            }
        });
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addressBookMessageActivity.deleteUser();
                popupWindow1.dismiss();
            }
        });
        ColorDrawable cd = new ColorDrawable(0x000000);
        popupWindow1.setBackgroundDrawable(cd);
        //产生背景变暗效果
        WindowManager.LayoutParams lp =addressBookMessageActivity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        addressBookMessageActivity.getWindow().setAttributes(lp);
        popupWindow1.setOutsideTouchable(true);
        popupWindow1.setFocusable(true);
        popupWindow1.setAnimationStyle(R.style.AnimBottom);
        popupWindow1.showAtLocation((View) v.getParent(), Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        //popupWindow1.update();
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = addressBookMessageActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                addressBookMessageActivity.getWindow().setAttributes(lp);
            }
        });
    }


}
