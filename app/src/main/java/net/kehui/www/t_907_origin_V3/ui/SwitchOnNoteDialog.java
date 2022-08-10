package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

/**
 * @author gong
 * @date //GC20220726
 */
public class SwitchOnNoteDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvYes;
    public TextView tvNo;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_switch_on_note, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.5);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.4);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        //对话框初始化
        tvYes = view.findViewById(R.id.tv_yes);
        tvNo = view.findViewById(R.id.tv_no);

        tvNo.setOnClickListener(this);
    }

    /**
     * 点击取消测试按钮事件
     */
    public void setTvYes(View.OnClickListener clickListener) {
        tvYes.setOnClickListener(clickListener);
    }

    /**
     * 点击高压操作按钮事件
     */
    public void setTvNo(View.OnClickListener clickListener) {
        tvNo.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no:
                dismiss();
                break;
            default:
                break;
        }
    }

    public SwitchOnNoteDialog(@NonNull Context context) {
        super(context);
    }
}
