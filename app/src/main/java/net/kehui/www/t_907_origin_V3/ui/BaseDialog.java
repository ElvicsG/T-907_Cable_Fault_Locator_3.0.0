package net.kehui.www.t_907_origin_V3.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import net.kehui.www.t_907_origin_V3.dao.DataDao;
import net.kehui.www.t_907_origin_V3.global.BaseAppData;

/**
 * Create by jwj on 2019/12/3
 */
public class BaseDialog extends Dialog {

    public DataDao dao;

    public BaseDialog(@NonNull Context context) {
        super(context);
        //数据库相关 //20200520
        BaseAppData db = Room.databaseBuilder(getContext(), BaseAppData.class, "database-wave").allowMainThreadQueries().build();
        dao = db.dataDao();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        BaseAppData db = Room.databaseBuilder(getContext(), BaseAppData.class, "database-wave").allowMainThreadQueries().build();
        dao = db.dataDao();
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        BaseAppData db = Room.databaseBuilder(getContext(), BaseAppData.class, "database-wave").allowMainThreadQueries().build();
        dao = db.dataDao();
    }

    /**
     * 对话框返回按键退出  //GC20220730
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
