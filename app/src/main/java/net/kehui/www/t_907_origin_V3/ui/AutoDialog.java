package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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
import net.kehui.www.t_907_origin_V3.entity.Data;
import net.kehui.www.t_907_origin_V3.entity.ParamInfo;
import net.kehui.www.t_907_origin_V3.util.ScreenUtils;
import net.kehui.www.t_907_origin_V3.util.StateUtils;
import net.kehui.www.t_907_origin_V3.util.UnitUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static net.kehui.www.t_907_origin_V3.application.Constant.MI_UNIT;
import static net.kehui.www.t_907_origin_V3.base.BaseActivity.DECAY;
import static net.kehui.www.t_907_origin_V3.base.BaseActivity.ICM;
import static net.kehui.www.t_907_origin_V3.base.BaseActivity.ICM_DECAY;
import static net.kehui.www.t_907_origin_V3.base.BaseActivity.SIM;
import static net.kehui.www.t_907_origin_V3.base.BaseActivity.TDR;

/**
 * @author gong
 * @date 2021/12/02
 */
public class AutoDialog extends BaseDialog implements View.OnClickListener {

    ImageView ivClose;
    EditText etHVINDICATOR;
    EditText etWorkingMode;

    TextView tvConfirm;
    TextView tvQuit;
    ImageView ivHVPULSE;
    public RadioGroup rgGear;
    public RadioButton rbGear16;
    public RadioButton rbGear32;
    public Spinner spWorkingMode;
    public HVControlView controlVoltage32;
    public HVControlView2 controlVoltage16;
    public TimeControlView controlTime;
    public ImageView ivWaring;

    private View view;
    private ParamInfo paramInfo;
    private List<String> workingModeList = new ArrayList<>();

    private int positionVirtual;
    private int positionReal;

    /**
     * 全局的handler对象用来执行UI更新
     */
    public static final int HVINDICATOR = 1;
    public static final int WARNING = 2;
    public static final int CANCEL_WARNING = 3;

    public Handler handlerAuto = new Handler(msg -> {
        switch (msg.what) {
            case HVINDICATOR:
                etHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
                break;
            case WARNING:
                Constant.isWarning = true;
                ivWaring.setImageResource(R.drawable.light_red);
                break;
            case CANCEL_WARNING:
                Constant.isWarning = false;
                ivWaring.setImageResource(R.drawable.light_gray);
                break;
            default:
                break;
        }
        return false;
    });

    public void setPositionReal(int positionReal) {
        this.positionReal = positionReal;
    }

    public void setPositionVirtual(int positionVirtual) {
        this.positionVirtual = positionVirtual;
    }

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
        //对话框初始化
        ivClose = view.findViewById(R.id.iv_close);
        etHVINDICATOR = view.findViewById(R.id.et_HVINDICATOR);
        etWorkingMode = view.findViewById(R.id.et_working_mode);
        spWorkingMode = view.findViewById(R.id.sp_working_mode);
        tvConfirm = view.findViewById(R.id.tv_confirm);
        tvQuit = view.findViewById(R.id.tv_quit);

        tvConfirm.setOnClickListener(this);
        tvQuit.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        //GC20211203
        controlTime = view.findViewById(R.id.control_time);
        controlVoltage32 = view.findViewById(R.id.control_voltage32);
        controlVoltage16 = view.findViewById(R.id.control_voltage16);
        ivHVPULSE = view.findViewById(R.id.iv_HV_PULSE);
        ivWaring = view.findViewById(R.id.iv_warning);
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
        //档位
        rgGear = view.findViewById(R.id.rg_gear);
        rbGear16 = view.findViewById(R.id.rb_gear16);
        rbGear32 = view.findViewById(R.id.rb_gear32);

    }

    private void initData() {
        //接地报警初始化   、、？
        /*if (Constant.isWarning) {
            ivWaring.setImageResource(R.drawable.light_red);
        } else {
            ivWaring.setImageResource(R.drawable.light_gray);
        }*/
        setEtHVINDICATOR();
        setSpWorkingMode();
    }

    /**
     * 当前电压初始化
     */
    private void setEtHVINDICATOR() {
        etHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
        etHVINDICATOR.setEnabled(false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_quit:
                dismiss();
                break;
            default:
                break;
        }

    }

}
