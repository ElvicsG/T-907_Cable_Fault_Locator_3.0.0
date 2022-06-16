package net.kehui.www.t_907_origin_V3.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.timmy.tdialog.TDialog;

import net.kehui.www.t_907_origin_V3.ConnectService;
import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.application.AppConfig;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.application.MyApplication;
import net.kehui.www.t_907_origin_V3.base.BaseActivity;
import net.kehui.www.t_907_origin_V3.entity.ParamInfo;
import net.kehui.www.t_907_origin_V3.ui.AppUpdateDialog;
import net.kehui.www.t_907_origin_V3.ui.HelpCenterDialog;
import net.kehui.www.t_907_origin_V3.ui.HelpHomeDialog;
import net.kehui.www.t_907_origin_V3.ui.LanguageChangeDialog;
import net.kehui.www.t_907_origin_V3.ui.ShowRecordsDialog;
import net.kehui.www.t_907_origin_V3.util.AppUtils;
import net.kehui.www.t_907_origin_V3.util.StateUtils;
import net.kehui.www.t_907_origin_V3.util.UnitUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.kehui.www.t_907_origin_V3.application.Constant.CurrentUnit;
import static net.kehui.www.t_907_origin_V3.application.Constant.FT_UNIT;
import static net.kehui.www.t_907_origin_V3.application.Constant.MI_UNIT;

import androidx.fragment.app.FragmentManager;
import net.kehui.www.t_907_origin_V3.fragment.AdjustFragment;
import net.kehui.www.t_907_origin_V3.fragment.FileFragment;
import net.kehui.www.t_907_origin_V3.fragment.ModeFragment;
import net.kehui.www.t_907_origin_V3.fragment.RangeFragment;
import net.kehui.www.t_907_origin_V3.fragment.SettingFragment;
import net.kehui.www.t_907_origin_V3.fragment.WaveFragment;

/**
 * @author JWJ
 * @date 2019/12/1
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_unit)
    RadioGroup rgUnit;
    @BindView(R.id.rb_mi)
    RadioButton rbMi;
    @BindView(R.id.rb_ft)
    RadioButton rbFt;
    @BindView(R.id.et_cable_vop)
    EditText etCableVop;
    @BindView(R.id.tv_cable_vop_unit)
    TextView tvCableVopUnit;
    @BindView(R.id.et_cable_length)
    EditText etCableLength;
    @BindView(R.id.tv_cable_length_unit)
    TextView tvCableLengthUnit;
    @BindView(R.id.et_cable_id)
    EditText etCableId;
    @BindView(R.id.cb_test_lead)
    CheckBox cbTestLead;
    @BindView(R.id.et_length)
    EditText etLength;
    @BindView(R.id.tv_length_text)
    TextView tvLengthText;
    @BindView(R.id.tv_length_unit)
    TextView tvLengthUnit;
    @BindView(R.id.et_vop)
    EditText etVop;
    @BindView(R.id.tv_vop_text)
    TextView tvVopText;
    @BindView(R.id.tv_vop_unit)
    TextView tvVopUnit;
    /**
     * 测试缆横线控制  //20200521
     */
    @BindView(R.id.v_line1)
    View vLine1;
    @BindView(R.id.v_line2)
    View vLine2;
    @BindView(R.id.iv_wifi_status)
    ImageView ivWifiStatus;
    @BindView(R.id.iv_battery_status)
    ImageView ivBatteryStatus;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.ll_remote)
    LinearLayout llRemote;

    private int unit;
    private String version = "1";
    private String url = "baidu.com";

    private ProgressBar progressBar;
    private TextView tvProgress;
    private LanguageChangeDialog languageChangeDialog;
    private AppUpdateDialog mAppUpdateDialog;
    private TDialog tDialog;

    private boolean needStopServce = true;

    public static double leadCat ;    //jk20201130  脉冲电流延长线不选就不计算

    private FragmentManager fragmentManager;    //Q2
    private ModeFragment modeFragment;   //jk20210201
    private RangeFragment rangeFragment;
    private AdjustFragment adjustFragment;
    private WaveFragment waveFragment;
    private FileFragment fileFragment;
    private SettingFragment settingFragment;

    /**
     * 定义bundle的key-value（bundle就是个容器）
     */
    public static final String BUNDLE_COMMAND_KEY = "command";
    public final String BUNDLE_MODE_KEY = "mode";
    public static final String BUNDLE_DATA_TRANSFER_KEY = "dataTransfer";
    public static final String BUNDLE_PARAM_KEY = "bundle_param_key";

    /**
     * 定义监听广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;
            switch (action) {
                case ConnectService.BROADCAST_ACTION_DEVICE_CONNECTED:
                    //服务中toast只可以跟随系统语言     //GC20211214
                    Toast.makeText(MainActivity.this, R.string.connect_success, Toast.LENGTH_SHORT).show();
                    //网络连接，更换网络图标
                    ConnectService.isConnected = true;
                    ivWifiStatus.setImageResource(R.drawable.ic_wifi_connected);
                    //发送获取电量命令  //EN20200324    //每次重新进模式界面还会调用这里，所以需要避免重复发送电量命令    //GT屏蔽电量获取
                    /*if (ConnectService.canAskPower) {
                        handler.postDelayed(() -> {
                            ConnectService.canAskPower = false;
                            //电量
                            command = 0x06;
                            dataTransfer = 0x13;
                            //通过服务发送获取电量指令
                            startService();
                        }, 100);
                    }*/
                    break;
                case ConnectService.BROADCAST_ACTION_DEVICE_CONNECT_FAILURE:
                    //网络断开，更换网络图标、电量图标
                    ConnectService.isConnected = false;
                    ivWifiStatus.setImageResource(R.drawable.ic_no_wifi_connect);
                    ivBatteryStatus.setImageResource(R.drawable.ic_battery_no);
                    //界面电量状态记录   //GC20200314
                    Constant.batteryValue = -1;
                    break;
                case ConnectService.BROADCAST_ACTION_DOWIFI_COMMAND:
                    //处理获取到的电量数据
                    wifiStream = intent.getIntArrayExtra(ConnectService.INTENT_KEY_COMMAND);
                    assert wifiStream != null;
                    doWifiCommand(wifiStream);
                    break;
                default:
                    break;
            }
        }
    };

    public static int baseset = 0;  //Q1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //去掉协助  //GC20200716
        llRemote.setVisibility(View.GONE);
        //启动主服务
        startMainService();
        initUnit();
        initParamInfo();
        //初始化广播接收器
        initBroadcastReceiver();

        baseset = 1;
        showProgressMode(TDR);//jk20210123
    }

    public void startMainService() {
        Intent intent = new Intent(MainActivity.this, ConnectService.class);
        startService(intent);
    }

    /**
     * 计量单位初始化 (m ft)
     */
    int currentCheckedId = 0;
    private void initUnit() {
        tvMsg.setText(String.format(getString(R.string.main_notice), getString(R.string.mi)));
        unit = StateUtils.getInt(MainActivity.this, AppConfig.CURRENT_UNIT, MI_UNIT);
        if (unit == MI_UNIT) {
            rbMi.setChecked(true);
        } else {
            rbFt.setChecked(true);
        }
        changeUnitView(unit);

        //单位切换时，切换响应数值
        rgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currentCheckedId = checkedId;
                if (checkedId == rbFt.getId()) {
                    StateUtils.setInt(MainActivity.this, AppConfig.CURRENT_UNIT, FT_UNIT);
                    changeUnitView(FT_UNIT);
                } else if (checkedId == rbMi.getId()) {
                    StateUtils.setInt(MainActivity.this, AppConfig.CURRENT_UNIT, MI_UNIT);
                    changeUnitView(MI_UNIT);
                }
            }
        });
    }

    private void changeUnitView(int currentUnit) {
        CurrentUnit = currentUnit;
        if (currentUnit == MI_UNIT) {
            tvCableVopUnit.setText(R.string.mius);
            tvCableLengthUnit.setText(R.string.mi);
            tvLengthUnit.setText(R.string.mi);
            tvVopUnit.setText(R.string.mius);
            tvMsg.setText(String.format(getString(R.string.main_notice), "500" + getString(R.string.mi)));
            //改变数值
            initParamInfo();

        } else {
            tvCableVopUnit.setText(R.string.ftus);
            tvCableLengthUnit.setText(R.string.ft);
            tvLengthUnit.setText(R.string.ft);
            tvVopUnit.setText(R.string.ftus);
            tvMsg.setText(String.format(getString(R.string.main_notice), "1640" + getString(R.string.ft)));
            initParamInfo();
        }

    }

    /**
     * 设置框里面的测试参数初始化
     */
    private void initParamInfo() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(MainActivity.this, Constant.PARAM_INFO_KEY);
        unit = StateUtils.getInt(MainActivity.this, AppConfig.CURRENT_UNIT, MI_UNIT);
        CurrentUnit = unit;
        if (paramInfo != null) {
            //如果单位是米
            if (unit == MI_UNIT) {
                //待测电缆
                if (!TextUtils.isEmpty(paramInfo.getCableVop())) {
                    if (paramInfo.getCableVop().equals("0") || paramInfo.getCableVop().equals("0.0")) {
                        etCableVop.setText("");
                    } else {
                        etCableVop.setText(paramInfo.getCableVop());
                    }
                }
                if (!TextUtils.isEmpty(paramInfo.getCableLength())) {
                    if (paramInfo.getCableLength().equals("0") || paramInfo.getCableLength().equals("0.0")) {
                        etCableLength.setText("");
                    } else {
                        etCableLength.setText(paramInfo.getCableLength());
                    }
                }
                etCableId.setText(paramInfo.getCableId());
                //测试缆
                if (paramInfo.getTestLead()) {
                    cbTestLead.setChecked(paramInfo.getTestLead());
                }
                if (!TextUtils.isEmpty(paramInfo.getLength())) {
                    etLength.setText(String.valueOf(paramInfo.getLength()));
                }
                try {
                    if (paramInfo.getLength().equals("0")) {
                        etLength.setText("");
                    }
                } catch (Exception l_ex) {
                    etLength.setText("");
                }
                if (!TextUtils.isEmpty(paramInfo.getVop())) {
                    etVop.setText(String.valueOf(paramInfo.getVop()));
                }
                try {
                    if (paramInfo.getVop().equals("0")) {
                        etVop.setText("");
                    }
                } catch (Exception l_ex) {
                    etVop.setText("");
                }
            } else {
                //米转换为英尺显示  //待测电缆
                if (!TextUtils.isEmpty(paramInfo.getCableVop())) {
                    if (paramInfo.getCableVop().equals("0") || paramInfo.getCableVop().equals("0.0")) {
                        etCableVop.setText("");
                    } else {
                        etCableVop.setText(String.valueOf(UnitUtils.miToFt(Double.valueOf(paramInfo.getCableVop()))));
                    }
                }
                if (!TextUtils.isEmpty(paramInfo.getCableLength())) {
                    if (paramInfo.getCableLength().equals("0") || paramInfo.getCableLength().equals("0.0")) {
                        etCableLength.setText("");
                    } else {
                        etCableLength.setText(String.valueOf(UnitUtils.miToFt(Double.valueOf(paramInfo.getCableLength()))));
                    }
                }
                etCableId.setText(paramInfo.getCableId());
                //测试缆
                if (paramInfo.getTestLead()) {
                    cbTestLead.setChecked(paramInfo.getTestLead());
                }
                if (!TextUtils.isEmpty(paramInfo.getLength())) {
                    etLength.setText(String.valueOf(UnitUtils.miToFt(Double.valueOf(paramInfo.getLength()))));
                }
                try {
                    if (paramInfo.getLength().equals("0")) {
                        etLength.setText("");
                    }
                } catch (Exception l_ex) {
                    etLength.setText("");
                }
                if (!TextUtils.isEmpty(paramInfo.getVop()))  {
                    etVop.setText(String.valueOf(UnitUtils.miToFt(Double.valueOf(paramInfo.getVop()))));
                }
                try {
                    if (paramInfo.getVop().equals("0")) {
                        etVop.setText("");
                    }
                } catch (Exception l_ex) {
                    etVop.setText("");
                }
            }
        }

        //测试缆选中
        if (cbTestLead.isChecked()) {
            etLength.setEnabled(true);
            etVop.setEnabled(true);
            etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            tvLengthText.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            tvVopText.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            tvVopUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            tvLengthUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            vLine1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_F2));
            vLine2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_F2));
            leadCat = 1;//jk20201130  脉冲电流延长线不选就不计算
            //Log.e("leadCat", "leadCat" +leadCat);
        } else {
            etLength.setEnabled(false);
            etVop.setEnabled(false);
            etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
            tvLengthText.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvVopText.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvVopUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvLengthUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            vLine1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_99));
            vLine2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_99));
            etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            leadCat = 0;//jk20201130  脉冲电流延长线不选就不计算
            // Log.e("leadCat", "leadCat" +leadCat);
        }

        //监听选项，使测试缆颜色变化
        cbTestLead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etLength.setEnabled(true);
                etVop.setEnabled(true);
                etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                tvLengthText.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                tvVopText.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                tvVopUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                tvLengthUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_F2));
                vLine1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_F2));
                vLine2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_F2));
                leadCat = 1;//jk20201130  脉冲电流延长线不选就不计算
                //Log.e("leadCat", "leadCat" +leadCat);
            } else {
                etLength.setEnabled(false);
                etVop.setEnabled(false);
                etLength.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                etVop.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvLengthText.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvVopText.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvVopUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvLengthUnit.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                vLine1.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_99));
                vLine2.setBackgroundColor(getBaseContext().getResources().getColor(R.color.T_99));
                leadCat = 0;//jk20201130  脉冲电流延长线不选就不计算
                // Log.e("leadCat", "leadCat" +leadCat);
            }
        });
        etCableVop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etVop.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    double vop = Double.parseDouble(s.toString());
                    double maxVop;
                    if (CurrentUnit == MI_UNIT) {
                        maxVop = 300;
                    } else {
                        maxVop = Double.valueOf(UnitUtils.miToFt(300));
                    }
                    if (vop > maxVop) {
                        etVop.setText(maxVop + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 广播接收器初始化
     */
    private void initBroadcastReceiver() {
        IntentFilter ifDisplay = new IntentFilter();
        ifDisplay.addAction(ConnectService.BROADCAST_ACTION_DEVICE_CONNECTED);
        ifDisplay.addAction(ConnectService.BROADCAST_ACTION_DEVICE_CONNECT_FAILURE);
        ifDisplay.addAction(ConnectService.BROADCAST_ACTION_DOWIFI_COMMAND);
        registerReceiver(receiver, ifDisplay);
        //判断服务里的连接状态，如果已经连接，则发送广播，改变连接状态
        if (ConnectService.isConnected) {
            Intent intent = new Intent(ConnectService.BROADCAST_ACTION_DEVICE_CONNECTED);
            sendBroadcast(intent);
        }
    }

    /**
     * 通过服务发送获取电量指令
     */
    public void startService() {
        Intent intent = new Intent(MainActivity.this, ConnectService.class);
        //发送指令
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_COMMAND_KEY, command);
        bundle.putInt(BUNDLE_MODE_KEY, mode);
        bundle.putInt(BUNDLE_DATA_TRANSFER_KEY, dataTransfer);
        intent.putExtra(BUNDLE_PARAM_KEY, bundle);
        startService(intent);
    }

    /**
     * @param wifiArray 处理获取到的电量数据
     */
    private void doWifiCommand(int[] wifiArray) {
        if (wifiArray[5] == POWER_DISPLAY) {
            //判断获取到的数据为电量数据
            int batteryValue = wifiArray[6] * 256 + wifiArray[7];
            Log.e("电量测试", "电量 = " + batteryValue );   //jk20200907
            if (batteryValue <= 2600) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_zero);
                //主界面电量状态记录   //GC20200314
                Constant.batteryValue = 0;
            } else if (batteryValue <= 2818) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_one);
                Constant.batteryValue = 1;
            } else if (batteryValue <= 3018) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_two);
                Constant.batteryValue = 2;
            } else if (batteryValue <= 3120) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_three);
                Constant.batteryValue = 3;
            } else {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_four);
                Constant.batteryValue = 4;
            }
        }

    }

    @OnClick({R.id.tv_vop_save, R.id.iv_tdr_mode, R.id.iv_icms_mode, R.id.iv_icmd_mode, R.id.iv_mim_mode, R.id.iv_decay_mode, R.id.btn_language, R.id.btn_update, R.id.btn_help, R.id.btn_records, R.id.btn_remote})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_vop_save:
                saveParamInfo();
                break;
            case R.id.iv_tdr_mode:
                showProgressMode(TDR);
                break;
            case R.id.iv_icms_mode:
                showProgressMode(ICM);
                break;
            case R.id.iv_icmd_mode:
                showProgressMode(ICM_DECAY);
                break;
            case R.id.iv_mim_mode:
                showProgressMode(SIM);
                break;
            case R.id.iv_decay_mode:
                showProgressMode(DECAY);
                break;
            case R.id.btn_language:
                showLanguageChangeDialog();
                break;
            case R.id.btn_update:
                //版本更新
                Toast.makeText(MainActivity.this, R.string.check_update, Toast.LENGTH_SHORT).show();
                downloadFile();
                break;
            case R.id.btn_help:
//                 showHelpCenterDialog();
                //GC20200327
                showHelpHomeDialog();
                break;
            case R.id.btn_records:
                showRecordsDialog();
                break;
            case R.id.btn_remote:
                break;
            default:
                break;
        }
    }

    /**
     * 保存设置框里面的测试参数
     */
    private void saveParamInfo() {
        //待测电缆波速度限制
        if (!TextUtils.isEmpty(etCableVop.getText().toString())) {
            double vop = Double.parseDouble(etCableVop.getText().toString());
            double maxVop;
            double minVop;
            if (CurrentUnit == MI_UNIT) {
                maxVop = 300;
                minVop = 90;
            } else {
                maxVop = Double.valueOf(UnitUtils.miToFt(300));
                minVop = Double.valueOf(UnitUtils.miToFt(90));
            }
            if (vop > maxVop) {
                etCableVop.setText(maxVop + "");
            }
            if (vop < minVop) {
                etCableVop.setText(minVop + "");
            }
        }
        //测试缆波速度限制
        if (!TextUtils.isEmpty(etVop.getText().toString())) {
            double vop = Double.parseDouble(etVop.getText().toString());
            double maxVop;
            double minVop;
            if (CurrentUnit == MI_UNIT) {
                maxVop = 300;
                minVop = 90;
            } else {
                maxVop = Double.valueOf(UnitUtils.miToFt(300));
                minVop = Double.valueOf(UnitUtils.miToFt(90));
            }
            if (vop > maxVop) {
                etVop.setText(maxVop + "");
            }
            if (vop < minVop) {
                etVop.setText(minVop + "");
            }
        }

        //待测电缆的波速度、长度、ID
        String cableVop = etCableVop.getText().toString();
        String cableLength = etCableLength.getText().toString();
        String cableId = etCableId.getText().toString();
        //是否有测试缆信息
        boolean testLead;
        testLead = cbTestLead.isChecked();
        //测试线缆长度、波速度
        String length = etLength.getText().toString();
        String vop = etVop.getText().toString();
        //单位转换  //20200522
        int currentUnit = StateUtils.getInt(MainActivity.this, AppConfig.CURRENT_UNIT, MI_UNIT);
        if (currentUnit == FT_UNIT) {
            StateUtils.setInt(MainActivity.this, AppConfig.CURRENT_SAVE_UNIT, FT_UNIT);
            //如果当前是英尺，则数值转换为米保存
            if (!TextUtils.isEmpty(cableVop)) {
                cableVop = UnitUtils.ftToMi(Double.valueOf(cableVop));
            }
            if (!TextUtils.isEmpty(cableLength)) {
                cableLength = UnitUtils.ftToMi(Double.valueOf(cableLength));
            }
            if (!TextUtils.isEmpty(length)) {
                length = UnitUtils.ftToMi(Double.valueOf(length));
            }
            if (!TextUtils.isEmpty(vop)) {
                vop = UnitUtils.ftToMi(Double.valueOf(vop));
            }
        } else if (currentUnit == MI_UNIT) {
            StateUtils.setInt(MainActivity.this, AppConfig.CURRENT_SAVE_UNIT, MI_UNIT);
        }

        //实体类paramInfo的传递值setter
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(MainActivity.this, Constant.PARAM_INFO_KEY);
        if (paramInfo == null) {
            paramInfo = new ParamInfo();
        }
        //待测电缆
        if (TextUtils.isEmpty(cableVop)) {
            //处理拿到不输入的情况
            cableVop = "0";
            paramInfo.setCableVop(cableVop);
        } else {
            paramInfo.setCableVop(cableVop);
        }
        if (TextUtils.isEmpty(cableLength)) {
            cableLength = "0";
            paramInfo.setCableLength(cableLength);
        } else {
            paramInfo.setCableLength(cableLength);
        }
        paramInfo.setCableId(cableId);
        //测试缆
        paramInfo.setTestLead(testLead);
        if (TextUtils.isEmpty(length)) {
            length = "0";
            paramInfo.setLength(length);
        } else {
            paramInfo.setLength(length);
        }
        if (TextUtils.isEmpty(vop)) {
            vop = "0";
            paramInfo.setVop(vop);
        } else {
            paramInfo.setVop(vop);
        }
        StateUtils.setObject(MainActivity.this, paramInfo, Constant.PARAM_INFO_KEY);
        Toast.makeText(MainActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
    }

    public void showProgressMode(int mode) {// private void showProgressMode(int mode) {
        //开启线程
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("splash-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), threadFactory,
                new ThreadPoolExecutor.AbortPolicy());

        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //使程序休眠5秒
                    Intent intent = new Intent(getApplicationContext(), ModeActivity.class);
                    intent.setClass(MainActivity.this, ModeActivity.class);
                    intent.putExtra(BUNDLE_MODE_KEY, mode);
                    //如果已经启动，就不产生新的activity     //20200523  //G?
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    if (tDialog != null) {
                        tDialog.dismiss();
                    }
                    //启动MainActivity
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        singleThreadPool.shutdown();
    }

    private void showLanguageChangeDialog() {
        languageChangeDialog = new LanguageChangeDialog(this);
        if (!languageChangeDialog.isShowing()) {
            languageChangeDialog.show();
        }
        languageChangeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!languageChangeDialog.getCloseStatus()) {
                    //切换语言不需要重连 //G?
//                    ConnectService.needConnect = false;
                    Intent intent = new Intent(MainActivity.this, ConnectService.class);
                    stopService(intent);
                    Intent intentSplash = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intentSplash);

                }
            }
        });
    }

    /**
     * 开始下载文件
     */
    public void downloadFile() {
        String xmlurl = Constant.BASE_API + "app/version.xml";
        Request request = new Request.Builder().url(xmlurl).addHeader("Accept-Encoding", "identity").build();
        //Request request = new Request.Builder().url(xmlurl).build();
        OkHttpClient httpClient = new OkHttpClient();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //下载失败
                Log.i("下载失败：", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = null;
                byte[] buff = new byte[2048];
                int len = 0;
                try {
                    inputStream = response.body().byteStream();
                    InputStreamReader isr = new InputStreamReader(inputStream);
                    BufferedReader br = new BufferedReader(isr);
                    // 内存流 写入读取的数据
                    StringWriter sw = new StringWriter();
                    String str = null;
                    while ((str = br.readLine()) != null) {
                        sw.write(str);
                    }
                    br.close();
                    sw.close();
                    str = sw.toString();
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new StringReader(str));
                    int eventType = parser.getEventType();
                    version = "";
                    url = "";
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String nodeName = parser.getName();
                        switch (eventType) {
                            // 开始解析某个结点
                            case XmlPullParser.START_TAG: {
                                if ("version".equals(nodeName)) {
                                    version = parser.nextText();
                                } else if ("url".equals(nodeName)) {
                                    url = parser.nextText();
                                }
                                break;
                            }
                            // 完成解析某个结点
                            case XmlPullParser.END_TAG: {
                                if ("update".equals(nodeName)) {
                                    if (Integer.parseInt(version) > AppUtils.getAppVersionCode()) {
                                        //获取文案
                                        if (!AppUpdateDialog.isShowUpdating) {
                                            handler.sendEmptyMessage(0);
                                        }
                                    } else {
                                        Toast.makeText(MainActivity.this, R.string.has_new_version, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                break;
                            }
                            default:
                                break;
                        }
                        eventType = parser.next();
                    }

                } catch (Exception e) {
//                    Logs.i("下载出错：" + e.getMessage());
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            showAppUpdate(version, url);
            return false;
        }
    });

    private void showAppUpdate(String version, String url) {
        if (mAppUpdateDialog == null) {
            mAppUpdateDialog = new AppUpdateDialog(this);
        }
        if (!mAppUpdateDialog.isShowing()) {
            mAppUpdateDialog.show();
        }
        mAppUpdateDialog.setVersionEntity(url);
    }

    private void showHelpCenterDialog() {
        HelpCenterDialog helpCenterDialog = new HelpCenterDialog(this);
        if (!helpCenterDialog.isShowing()) {
            helpCenterDialog.show();
        }
    }

    /**
     * 主页界面帮助按钮    //GC20200327
     */
    private void showHelpHomeDialog() {
        HelpHomeDialog helpHomeDialog = new HelpHomeDialog(this);
        if (!helpHomeDialog.isShowing()) {
            helpHomeDialog.show();
        }
    }

    private void showRecordsDialog() {
        ShowRecordsDialog customDialog = new ShowRecordsDialog(this);
        customDialog.setFromMain(true);
        if (!customDialog.isShowing()) {
            customDialog.show();
        }
    }

    /**
     * 隐藏虚拟按键，暂时用不到。
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }

    @Override
    protected void onResume() {
        initParamInfo();
        String languageType = StateUtils.getString(MyApplication.getInstances(), AppConfig.CURRENT_LANGUAGE, "ch");
        Constant.currentLanguage = languageType;
        super.onResume();
    }

    @Override
    protected void onPause() {
        /*if (receiver != null) {
            unregisterReceiver(receiver);
        }*/
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
//        ConnectService.needConnect = false;
        Intent intent = new Intent(MainActivity.this, ConnectService.class);
        stopService(intent);
        super.onDestroy();
    }

}
