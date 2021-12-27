package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.entity.ParamInfo;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gong
 * @date 2021/12/15
 */
public class hvWaitTriggerDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvHvCancel;
    public TextView tvHv;
    public ImageView ivTriggerPULSE;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.hv_wait_trigger, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.5);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.4);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    private void initView() {
        //等待触发对话框初始化  //GC20211215
        tvHvCancel = view.findViewById(R.id.tv_hv_cancel);
        tvHv = view.findViewById(R.id.tv_hv);
        ivTriggerPULSE = view.findViewById(R.id.iv_trigger_PULSE);

        tvHvCancel.setOnClickListener(this);
    }

    /**
     * 点击取消测试按钮事件
     */
    public void setTvHvCancel(View.OnClickListener clickListener) {
        tvHvCancel.setOnClickListener(clickListener);
    }

    /**
     * 点击高压操作按钮事件
     */
    public void setHv(View.OnClickListener clickListener) {
        tvHv.setOnClickListener(clickListener);
    }

    /**
     * 点击单次放电按钮按钮事件
     */
    public void setIvTriggerPULSE(View.OnClickListener clickListener) {
        ivTriggerPULSE.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public hvWaitTriggerDialog(@NonNull Context context) {
        super(context);
    }
}
