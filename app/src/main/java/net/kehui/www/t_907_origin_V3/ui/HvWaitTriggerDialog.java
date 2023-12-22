package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

import java.text.DecimalFormat;

/**
 * @author gong
 * @date 2021/12/15
 */
public class HvWaitTriggerDialog extends BaseDialog implements View.OnClickListener {

    public TextView tvNoteWait;
    /**
     * 电压显示 //GC20221109
     */
    public ImageView ivTriggerVoltageHeight;
    public TextView tvTriggerHVINDICATOR;
    public ImageView ivTriggerPULSE;

    /**
     * 电压控制添加   //GC20221102
     */
    public LinearLayout llTriggerValue;
    public LinearLayout llTriggerSeekBar;
    public ImageView ivTriggerMinus;
    public ImageView ivTriggerPlus;
    public TextView hvTriggerValue;
    public KBubbleSeekBar32 seekBar32Trigger;
    public KBubbleSeekBar16 seekBar16Trigger;
    public LinearLayout llHv;
    public LinearLayout llHvConfirm;
    public ImageView ivHvLightning;   //GC20221115
    public ImageView ivHvLightning2;   //GC20230228
    public LinearLayout llHvCancel;

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_hv_wait_trigger, null);
        setContentView(view);
        initView();

        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ( ScreenUtils.getScreenWidth(getContext()) * 0.6);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.6);    //GC20221102
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //电容电压显示初始化 //GC20221109
        setEtHVINDICATOR();
    }

    private void initView() {
        tvNoteWait = view.findViewById(R.id.tv_note_wait);
        //电压进度条 //GC20221109
        ivTriggerVoltageHeight = view.findViewById(R.id.iv_trigger_voltage_height);
        //电容电压数值    //GC20221109
        tvTriggerHVINDICATOR = view.findViewById(R.id.tv_trigger_HVINDICATOR);
        ivTriggerPULSE = view.findViewById(R.id.iv_trigger_PULSE);
        //电压控制  //GC20221102
        llTriggerValue = view.findViewById(R.id.ll_trigger_value);
        llTriggerSeekBar = view.findViewById(R.id.ll_trigger_seekBar);
        ivTriggerMinus = view.findViewById(R.id.iv_trigger_minus);
        ivTriggerPlus = view.findViewById(R.id.iv_trigger_plus);
        hvTriggerValue = view.findViewById(R.id.tv_trigger_value);
        seekBar32Trigger = view.findViewById(R.id.seekBar32_trigger);
        seekBar16Trigger = view.findViewById(R.id.seekBar16_trigger);
        //等待触发对话框初始化  //GC20211215
        llHv = view.findViewById(R.id.ll_hv);
        llHvConfirm = view.findViewById(R.id.ll_hv_confirm);
        ivHvLightning = view.findViewById(R.id.iv_hv_lightning); //GC20221115
        ivHvLightning2 = view.findViewById(R.id.iv_hv_lightning2); //GC20230228
        llHvCancel = view.findViewById(R.id.ll_hv_cancel);

        llHvCancel.setOnClickListener(this);
    }

    /**
     * 电容电压初始化  //GC20221109
     */
    private void setEtHVINDICATOR() {
        tvTriggerHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
        tvTriggerHVINDICATOR.setEnabled(false);
        int heightPosition = 0;
        //根据最大值计算进度条高度
//        double a = Constant.currentVoltage / Constant.setVoltage;
        //根据电压档位选择最大值换算 32/16 //GC20231027
        int maxHV = 0;
        switch (Constant.gear) {
            case 1:
                maxHV = 16;
                break;
            case 2:
                maxHV = 32;
                break;
            default:
                break;
        }
        double a = Constant.currentVoltage / maxHV;

        int b = (int) (a * 100);
        if (b >= 0 && b < 10) {
        } else if (b >= 10 && b < 20) {
            heightPosition = 1;
        } else if (b >= 20 && b < 30) {
            heightPosition = 2;
        } else if (b >= 30 && b < 40) {
            heightPosition = 3;
        } else if (b >= 40 && b < 50) {
            heightPosition = 4;
        } else if (b >= 50 && b < 60) {
            heightPosition = 5;
        } else if (b >= 60 && b < 70) {
            heightPosition = 6;
        } else if (b >= 70 && b < 80) {
            heightPosition = 7;
        } else if (b >= 80 && b < 90) {
            heightPosition = 8;
        } else if (b >= 90 && b < 100) {
            heightPosition = 9;
        } else if (b >= 100) {
            heightPosition = 10;
        }
        switch (heightPosition) {
            case 0:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_0);
                break;
            case 1:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_1);
                break;
            case 2:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_2);
                break;
            case 3:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_3);
                break;
            case 4:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_4);
                break;
            case 5:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_5);
                break;
            case 6:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_6);
                break;
            case 7:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_7);
                break;
            case 8:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_8);
                break;
            case 9:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_9);
                break;
            case 10:
                ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_10);
                break;
            default:
                break;
        }
    }

    /**
     * 点击“取消测试”按钮事件
     */
    public void setLlHvCancel(View.OnClickListener clickListener) {
        llHvCancel.setOnClickListener(clickListener);
    }

    /**
     * 点击“高压设置”按钮事件   //GC20221102    改成电压微调
     */
    public void setHv(View.OnClickListener clickListener) {
        llHv.setOnClickListener(clickListener);
    }

    /**
     * 点击“电压确认”按钮事件   //GC20221102
     */
    public void setHvConfirm(View.OnClickListener clickListener) {
        llHvConfirm.setOnClickListener(clickListener);
    }

    /**
     * 点击单次放电按钮按钮事件
     */
    public void setIvTriggerPULSE(View.OnClickListener clickListener) {
        ivTriggerPULSE.setOnClickListener(clickListener);
    }

    /**
     * 点击减号 //GC20221102
     */
    public void setIvTriggerMinus(View.OnClickListener clickListener) {
        ivTriggerMinus.setOnClickListener(clickListener);
    }

    /**
     * 点击加号 //GC20221102
     */
    public void setIvTriggerPlus(View.OnClickListener clickListener) {
        ivTriggerPlus.setOnClickListener(clickListener);
    }

    /**
     * seekBar32Trigger //GC20221108
     */
    public void setSeekBar32Trigger(View.OnClickListener clickListener) {
        seekBar32Trigger.setOnClickListener(clickListener);
    }

    /**
     * seekBar16Trigger //GC20221108
     */
    public void setSeekBar16Trigger(View.OnClickListener clickListener) {
        seekBar16Trigger.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_hv_cancel:
//                dismiss();    //GC20221110
                break;
            default:
                break;
        }
    }

    public HvWaitTriggerDialog(@NonNull Context context) {
        super(context);
    }
}
