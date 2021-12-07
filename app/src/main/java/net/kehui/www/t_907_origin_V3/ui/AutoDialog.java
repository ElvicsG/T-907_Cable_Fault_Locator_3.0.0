package net.kehui.www.t_907_origin_V3.ui;

import android.content.Context;
import android.os.Bundle;
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
 * @date 2021/10/13
 */
public class AutoDialog extends BaseDialog implements View.OnClickListener {

    ImageView ivClose;
    EditText etMethod;
    EditText etHVINDICATOR;
    EditText etWorkingMode;

    TextView tvEnsure;
    ImageView ivHVPULSE;
    public RadioGroup rgGear;
    public RadioButton rbGear1;
    public RadioButton rbGear2;
    public Spinner spWorkingMode;
    public HVControlView controlVoltage;
    public HVControlView2 controlVoltage2;
    public TimeControlView controlTime;


    private View view;
    private ParamInfo paramInfo;
    private List<String> workingModeList = new ArrayList<>();

    private int positionVirtual;
    private int positionReal;

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
        initView();
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(getContext()) * 0.98);
        params.height = (int) (ScreenUtils.getScreenHeight(getContext()) * 0.96);
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        initData();

    }

    private void initView() {
        ivClose = view.findViewById(R.id.iv_close);
        etMethod = view.findViewById(R.id.et_method);
        etHVINDICATOR = view.findViewById(R.id.et_HVINDICATOR);
        etWorkingMode = view.findViewById(R.id.et_working_mode);
        spWorkingMode = view.findViewById(R.id.sp_working_mode);
        tvEnsure = view.findViewById(R.id.tv_ensure);
        tvEnsure.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        controlTime = view.findViewById(R.id.control_time);
        controlVoltage = view.findViewById(R.id.control_voltage);
        controlVoltage2 = view.findViewById(R.id.control_voltage2);
        ivHVPULSE = view.findViewById(R.id.iv_HV_PULSE);
        //放电周期效果
        controlTime.setArcColor("#026b02");
        controlTime.setDialColor1("#026b02");
        controlTime.setDialColor2("#01eeff");
        controlTime.setValueColor("#00ec03");
        controlTime.setCurrentValueColor("#026b02");
        controlTime.setTitle(getContext().getResources().getString(R.string.time));
        //设定电压效果
        controlVoltage.setArcColor("#a03225");
        controlVoltage.setDialColor1("#a03225");
        controlVoltage.setDialColor2("#01eeff");
        controlVoltage.setValueColor("#d0210e");
        controlVoltage.setCurrentValueColor("#a03225");
        controlVoltage.setTitle(getContext().getResources().getString(R.string.voltage));
        //设定电压效果2
        controlVoltage2.setArcColor("#ff0000");
        controlVoltage2.setDialColor1("#ff0000");
        controlVoltage2.setDialColor2("#01eeff");
        controlVoltage2.setValueColor("#d0210e");
        controlVoltage2.setCurrentValueColor("#ff0000");
        controlVoltage2.setTitle(getContext().getResources().getString(R.string.voltage));
        rgGear = view.findViewById(R.id.rg_gear);
        rbGear1 = view.findViewById(R.id.rb_gear1);
        rbGear2 = view.findViewById(R.id.rb_gear2);//对话框初始化

    }

    private void initData() {
        getMainParamInfo();
        setEtMethod();
        setEtHVINDICATOR();
        setSpWorkingMode();
    }

    private void getMainParamInfo() {
        paramInfo = (ParamInfo) StateUtils.getObject(getContext(), Constant.PARAM_INFO_KEY);

    }

    /**
     * 测试方法
     */
    private void setEtMethod() {
        int mode = Constant.ModeValue;
        switch (mode) {
            case TDR:
                etMethod.setText(getContext().getResources().getString(R.string.btn_tdr));
                Constant.Mode = TDR;
                break;
            case ICM:
                etMethod.setText(getContext().getResources().getString(R.string.btn_icm));
                Constant.Mode = ICM;
                break;
            case ICM_DECAY:
                etMethod.setText(getContext().getResources().getString(R.string.btn_icm_decay));
                Constant.Mode = ICM_DECAY;
                break;
            case SIM:
                etMethod.setText(getContext().getResources().getString(R.string.btn_sim));
                Constant.Mode = SIM;
                break;
            case DECAY:
                etMethod.setText(getContext().getResources().getString(R.string.btn_decay));
                Constant.Mode = DECAY;
                break;
            default:
                break;
        }
        etMethod.setEnabled(false);

    }

    /**
     * 当前电压数值预留
     */
    private void setEtHVINDICATOR() {
        Constant.SaveLocation = Constant.CurrentLocation;
        if (Constant.CurrentUnit == MI_UNIT) {
            etHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.SaveLocation));
        } else {
            etHVINDICATOR.setText(UnitUtils.miToFt(Constant.SaveLocation));
        }
        etHVINDICATOR.setEnabled(false);
    }

    /**
     * 工作模式选择和传递    //GC21211203
     */
    private void setSpWorkingMode() {
        workingModeList.add(getContext().getResources().getString(R.string.PULSE));
        workingModeList.add(getContext().getResources().getString(R.string.CYCLIC));
        workingModeList.add(getContext().getResources().getString(R.string.DC));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, workingModeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkingMode.setAdapter(adapter);

    }

    public void setRadioGroup(RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        rgGear.setOnCheckedChangeListener(checkedChangeListener);
    }

    /**
     * 设置确认按键点击事件
     */
    public void setIvHVPULSE(View.OnClickListener clickListener) {
        ivHVPULSE.setOnClickListener(clickListener);
    }

    /**
     * 设置确认按键点击事件
     */
    public void setTvEnsureButton(View.OnClickListener clickListener) {
        tvEnsure.setOnClickListener(clickListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_HV_PULSE:
                dismiss();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_ensure:
                /*final Data data = formatData(new Data());
                Flowable.create((FlowableOnSubscribe<List>) e -> {
                    dao.insertData(data);
                    List list = Arrays.asList(dao.query());
                    e.onNext(list);
                    e.onComplete();
                }, BackpressureStrategy.BUFFER)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(List list) {
                        //数据库保存提示 //20200520
//                        Toast.makeText(getContext(), list.size() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        //数据库保存修改   //20200520
                        Toast.makeText(getContext(), getContext().getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                    }
                });*/
//                dismiss();
                break;
            default:
                break;
        }

    }

    private Data formatData(Data data) {
        data.mode = Constant.Mode + "";
        data.location = Constant.SaveLocation;
        if (data.location == 0) {
            if (!TextUtils.isEmpty(etHVINDICATOR.getText().toString())) {
                data.location = Double.parseDouble(etHVINDICATOR.getText().toString());
            }
        }

        if (paramInfo != null) {
            data.line = paramInfo.getCableLength();
        } else {
            data.line = "";
        }

        data.waveData = Constant.WaveData;
        data.waveDataSim = Constant.SimData;
        //TODO 20191226 存储zero 和pointDistance
        data.positionReal = positionReal;
        data.positionVirtual = positionVirtual;
        //参数数据 方式  范围 增益 波速度
        data.para = new int[]{Constant.ModeValue, Constant.RangeValue, Constant.SaveToDBGain, (int) Constant.Velocity};
        return data;
    }

}
