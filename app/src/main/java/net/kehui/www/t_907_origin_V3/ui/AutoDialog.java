package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
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
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gong
 * @date 2021/12/02
 */
public class AutoDialog extends BaseDialog implements View.OnClickListener  {

    ImageView ivClose;
    EditText etWorkingMode;

    TextView tvConfirm;
    TextView tvQuit;
    public ImageView ivHVPULSE;
    public RadioGroup rgGear;
    public RadioButton rbGear16;
    public RadioButton rbGear32;
    public Spinner spWorkingMode;
    public HVControlView32 controlVoltage32;
    public HVControlView16 controlVoltage16;
    /**
     * seekBar32 控件添加//
     */
    public TextView hvValue;
    public KBubbleSeekBar32 seekBar32;
    public KBubbleSeekBar16 seekBar16;

    public TimeControlView controlTime;
    public ImageView ivWaring;
    public EditText etHVINDICATOR;
    public TextView tvHvGear;
    public TextView tvHvWorkingMode;
    public TextView tvHvCapacitor;
    public TextView tvHvIgnitionCoil;
    public ImageView ivVoltageHeight;

    private View view;
    private List<String> workingModeList = new ArrayList<>();

    public AutoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = LayoutInflater.from(getContext()).inflate(R.layout.layout_auto_dialog, null, false);
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
        ivClose = view.findViewById(R.id.iv_close);
        etHVINDICATOR = view.findViewById(R.id.et_HVINDICATOR);
        etWorkingMode = view.findViewById(R.id.et_working_mode);
        spWorkingMode = view.findViewById(R.id.sp_working_mode);
        controlTime = view.findViewById(R.id.control_time);
        controlVoltage32 = view.findViewById(R.id.control_voltage32);
        controlVoltage16 = view.findViewById(R.id.control_voltage16);

        //电压滑动控件添加  //GC20220413
        hvValue = view.findViewById(R.id.tv_value);
        seekBar32 = view.findViewById(R.id.seekBar32);
        seekBar16 = view.findViewById(R.id.seekBar16);

        ivHVPULSE = view.findViewById(R.id.iv_HV_PULSE);
        ivWaring = view.findViewById(R.id.iv_warning);
        rgGear = view.findViewById(R.id.rg_gear);
        rbGear16 = view.findViewById(R.id.rb_gear16);
        rbGear32 = view.findViewById(R.id.rb_gear32);;
        tvConfirm = view.findViewById(R.id.tv_confirm);
        tvQuit = view.findViewById(R.id.tv_quit);
        tvHvGear = view.findViewById(R.id.tv_hv_gear);
        tvHvWorkingMode = view.findViewById(R.id.tv_hv_working_mode);
        tvHvCapacitor = view.findViewById(R.id.tv_hv_capacitor);
        tvHvIgnitionCoil = view.findViewById(R.id.tv_hv_ignition_coil);
        ivVoltageHeight = view.findViewById(R.id.iv_voltage_height);
        tvQuit.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);

        //seekBar电压显示初始化
        hvValue.setText("设定电压：0kV");
        //放电周期效果
        controlTime.setArcColor("#026b02");
        controlTime.setDialColor1("#026b02");
        controlTime.setDialColor2("#01eeff");
        controlTime.setValueColor("#00ec03");
        controlTime.setCurrentValueColor("#026b02");
        controlTime.setTitle(getContext().getResources().getString(R.string.time));
        //32kV档位设定电压效果
        controlVoltage32.setArcColor("#a03225");
        controlVoltage32.setDialColor1("#a03225");
        controlVoltage32.setDialColor2("#01eeff");
        controlVoltage32.setValueColor("#d0210e");
        controlVoltage32.setCurrentValueColor("#a03225");
        controlVoltage32.setTitle(getContext().getResources().getString(R.string.voltage));
        //16kV档位设定电压效果
        controlVoltage16.setArcColor("#ff0000");
        controlVoltage16.setDialColor1("#ff0000");
        controlVoltage16.setDialColor2("#01eeff");
        controlVoltage16.setValueColor("#d0210e");
        controlVoltage16.setCurrentValueColor("#ff0000");
        controlVoltage16.setTitle(getContext().getResources().getString(R.string.voltage));
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
        double a = Constant.currentVoltage / Constant.setVoltage ;
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
        workingModeList.add(getContext().getResources().getString(R.string.DC));

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
    }

    /**
     * 点击退出按钮事件
     */
    public void setQuit(View.OnClickListener clickListener) {
        tvQuit.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_quit:
                //GTT   高压操作退出清状态
//                Constant.currentVoltage = 0;
//                Constant.isWarning = true;
//                Constant.isIgnitionCoil = true;
//                Constant.isCapacitor = true;
//                Constant.isWorkingMode = true;
//                Constant.isGear = true;
                /*//对话框退出状态记录     //GC20211210
                Constant.isShowHV = false;
                dismiss();*/
                break;
            default:
                break;
        }

    }

}
