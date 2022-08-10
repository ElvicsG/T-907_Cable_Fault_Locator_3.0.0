package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gong
 * @date 2021/12/02
 */
public class AutoDialog extends BaseDialog implements View.OnClickListener {

    ImageView ivCloseAuto;
    EditText etWorkingMode;

    TextView tvConfirm;
    TextView tvQuit;
    public ImageView ivHVPULSE;
    public RadioGroup rgGear;
    public RadioButton rbGear16;
    public RadioButton rbGear32;
    public Spinner spWorkingMode;
    /**
     * seekBar32 控件添加//
     */
    public TextView hvValue;
    public KBubbleSeekBar32 seekBar32;
    public KBubbleSeekBar16 seekBar16;
    public KBubbleSeekBar12 seekBar12;

    public ImageView ivWaring;
    public EditText etHVINDICATOR;
    public TextView tvHvGear;
    public TextView tvHvWorkingMode;
    public TextView tvHvCapacitor;
    public TextView tvHvIgnitionCoil;
    public ImageView ivVoltageHeight;
    //GC20220617
    public LinearLayout llTIME;
    public TextView tvWorkingMode;
    public View vWorkingMode;
    public LinearLayout llPULSE;
    public TextView tvPULSE;
    public LinearLayout llPULSE2;
    public TextView tvPULSE2;
    public ImageView ivHVPULSE2;
    //GC20220711
    public ImageView ivLightning;

    private View view;
    private List<String> workingModeList = new ArrayList<>();

    public AutoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_auto, null, false);
        setContentView(view);
        //自定义高压操作界面对话框    //GC20211202
        initView();
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.98);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.96);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initData();

    }

    private void initView() {
        //对话框初始化    //GC20211203
        ivCloseAuto = view.findViewById(R.id.iv_close_auto);
        etHVINDICATOR = view.findViewById(R.id.et_HVINDICATOR);
        etWorkingMode = view.findViewById(R.id.et_working_mode);
        spWorkingMode = view.findViewById(R.id.sp_working_mode);

        //电压滑动控件添加  //GC20220413
        hvValue = view.findViewById(R.id.tv_value);
        seekBar32 = view.findViewById(R.id.seekBar32);
        seekBar16 = view.findViewById(R.id.seekBar16);
        seekBar12 = view.findViewById(R.id.seekBar12);

        ivHVPULSE = view.findViewById(R.id.iv_HV_PULSE);
        ivWaring = view.findViewById(R.id.iv_warning);
        rgGear = view.findViewById(R.id.rg_gear);
        rbGear16 = view.findViewById(R.id.rb_gear16);
        rbGear32 = view.findViewById(R.id.rb_gear32);

        tvConfirm = view.findViewById(R.id.tv_confirm);
        tvQuit = view.findViewById(R.id.tv_quit);
        //叉号退出  //GC20220713
        ivCloseAuto = view.findViewById(R.id.iv_close_auto);
        tvHvGear = view.findViewById(R.id.tv_hv_gear);
        tvHvWorkingMode = view.findViewById(R.id.tv_hv_working_mode);
        tvHvCapacitor = view.findViewById(R.id.tv_hv_capacitor);
        tvHvIgnitionCoil = view.findViewById(R.id.tv_hv_ignition_coil);
        ivVoltageHeight = view.findViewById(R.id.iv_voltage_height);
        //GC20220617
        llTIME = view.findViewById(R.id.ll_TIME);
        tvWorkingMode = view.findViewById(R.id.tv_working_mode);
        vWorkingMode = view.findViewById(R.id.v_working_mode);
        llPULSE = view.findViewById(R.id.ll_PULSE);
        tvPULSE = view.findViewById(R.id.tv_PULSE);
        llPULSE2 = view.findViewById(R.id.ll_PULSE2);
        tvPULSE2 = view.findViewById(R.id.tv_PULSE2);
        ivHVPULSE2 = view.findViewById(R.id.iv_HV_PULSE2);
        //GC20220711
        ivLightning = view.findViewById(R.id.iv_lightning);

        tvQuit.setOnClickListener(this);
        //叉号退出  //GC20220713
        ivCloseAuto.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        //seekBar电压显示初始化
        hvValue.setText("设定电压：0kV");
        //接地报警初始化
        if (!Constant.isWarning) {
            ivWaring.setImageResource(R.drawable.light_red);
        } else {
            ivWaring.setImageResource(R.drawable.light_gray);
        }
        //电压档位故障
        if (!Constant.isGear) {
            tvHvGear.setText(R.string.hv_gear_note2);
            tvHvGear.setTextColor(getContext().getResources().getColor(R.color.T_red));
        } else {
            tvHvGear.setText(R.string.hv_gear_note);
            tvHvGear.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        //工作方式故障
        if (!Constant.isWorkingMode) {
            tvHvWorkingMode.setText(R.string.hv_working_mode_note2);
            tvHvWorkingMode.setTextColor(getContext().getResources().getColor(R.color.T_red));
        } else {
            tvHvWorkingMode.setText(R.string.hv_working_mode_note);
            tvHvWorkingMode.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        //电容有残压
        if (!Constant.isCapacitor) {
            tvHvCapacitor.setText(R.string.hv_capacity_note2);
            tvHvCapacitor.setTextColor(getContext().getResources().getColor(R.color.T_red));
        } else {
            tvHvCapacitor.setText(R.string.hv_capacity_note);
            tvHvCapacitor.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
        //高压包状态初始化
        if (!Constant.isIgnitionCoil) {
            tvHvIgnitionCoil.setText(R.string.hv_ignition_coil_note2);
            tvHvIgnitionCoil.setTextColor(getContext().getResources().getColor(R.color.T_red));
        } else {
            tvHvIgnitionCoil.setText(R.string.hv_ignition_coil_note);
            tvHvIgnitionCoil.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        }

    }

    private void initData() {
        setEtHVINDICATOR();
        setSpWorkingMode();
    }

    /**
     * 当前电压初始化
     */
    private void setEtHVINDICATOR() {
        etHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
        etHVINDICATOR.setEnabled(false);
        int heightPosition = 0;
        //根据最大值计算进度条高度
        double a = Constant.currentVoltage / Constant.setVoltage;
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
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_0);
                break;
            case 1:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_1);
                break;
            case 2:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_2);
                break;
            case 3:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_3);
                break;
            case 4:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_4);
                break;
            case 5:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_5);
                break;
            case 6:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_6);
                break;
            case 7:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_7);
                break;
            case 8:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_8);
                break;
            case 9:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_9);
                break;
            case 10:
                ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_10);
                break;
            default:
                break;
        }
    }

    /**
     * 工作模式初始化    //GC20211203
     */
    private void setSpWorkingMode() {
        workingModeList.add(getContext().getResources().getString(R.string.PULSE));
        workingModeList.add(getContext().getResources().getString(R.string.CYCLIC));
        //定点模式没有直流  //GC20220809
        if (!Constant.isClickLocate) {
            workingModeList.add(getContext().getResources().getString(R.string.DC));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, workingModeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkingMode.setAdapter(adapter);

    }

    /**
     * 监听电压档位选项变化
     */
    public void setRadioGroup(RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        rgGear.setOnCheckedChangeListener(checkedChangeListener);
    }

    /**
     * 点击电压确认按钮事件
     */
    public void setTvConfirmButton(View.OnClickListener clickListener) {
        tvConfirm.setOnClickListener(clickListener);
    }

    /**
     * 点击单次放电按钮事件
     */
    public void setIvHVPULSE(View.OnClickListener clickListener) {
        ivHVPULSE.setOnClickListener(clickListener);
        ivHVPULSE2.setOnClickListener(clickListener);
    }

    /**
     * 点击进入测试界面（退出）按钮事件
     */
    public void setQuit(View.OnClickListener clickListener) {
        tvQuit.setOnClickListener(clickListener);
    }

    /**
     * 叉号退出  //GC20220713
     */
    public void setClose(View.OnClickListener clickListener) {
        ivCloseAuto.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_auto:
                dismiss();
                break;
            case R.id.tv_quit:
                break;
            default:
                break;
        }

    }

}
