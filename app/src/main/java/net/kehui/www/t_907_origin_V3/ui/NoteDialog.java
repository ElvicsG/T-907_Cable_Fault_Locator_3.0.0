package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

/**
 * @author gong
 * @date 2021/12/22
 */
public class NoteDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNote;
    public TextView tvQuit;
    public TextView tvOffline;  //GC20231111

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_note, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.5);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.4);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        tvNote = view.findViewById(R.id.tv_note);
        tvQuit = view.findViewById(R.id.tv_fault_quit);
        tvQuit.setOnClickListener(this);
        //离线使用  //GC20231111
        tvOffline = view.findViewById(R.id.tv_offline);
        tvOffline.setOnClickListener(this);
    }

    /**
     * 点击离线使用按钮事件   //GC20231111
     */
    public void setTvOffline(View.OnClickListener clickListener) {
        tvOffline.setOnClickListener(clickListener);
    }

    /**
     * 点击退出按钮事件
     */
    public void setTvFaultQuit(View.OnClickListener clickListener) {
        tvQuit.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    public NoteDialog(@NonNull Context context) {
        super(context);
    }
}
