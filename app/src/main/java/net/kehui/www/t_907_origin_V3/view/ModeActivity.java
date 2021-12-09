package net.kehui.www.t_907_origin_V3.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.percentlayout.widget.PercentRelativeLayout;

import com.timmy.tdialog.TDialog;
import com.timmy.tdialog.base.BindViewHolder;
import com.timmy.tdialog.listener.OnViewClickListener;

import net.kehui.www.t_907_origin_V3.ConnectService;
import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.adpter.MyChartAdapterBase;
import net.kehui.www.t_907_origin_V3.application.AppConfig;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.base.BaseActivity;
import net.kehui.www.t_907_origin_V3.entity.ParamInfo;
import net.kehui.www.t_907_origin_V3.fragment.AdjustFragment;
import net.kehui.www.t_907_origin_V3.fragment.FileFragment;
import net.kehui.www.t_907_origin_V3.fragment.ModeFragment;
import net.kehui.www.t_907_origin_V3.fragment.RangeFragment;
import net.kehui.www.t_907_origin_V3.fragment.SettingFragment;
import net.kehui.www.t_907_origin_V3.fragment.WaveFragment;
import net.kehui.www.t_907_origin_V3.ui.AppUpdateDialog;
import net.kehui.www.t_907_origin_V3.ui.AutoDialog;
import net.kehui.www.t_907_origin_V3.ui.HVControlView;
import net.kehui.www.t_907_origin_V3.ui.HVControlView2;
import net.kehui.www.t_907_origin_V3.ui.HelpModeDialog;
import net.kehui.www.t_907_origin_V3.ui.MoveView;
import net.kehui.www.t_907_origin_V3.ui.MoveWaveView;
import net.kehui.www.t_907_origin_V3.ui.SaveRecordsDialog;
import net.kehui.www.t_907_origin_V3.ui.ShowRecordsDialog;
import net.kehui.www.t_907_origin_V3.ui.SparkView.SparkView;
import net.kehui.www.t_907_origin_V3.ui.TimeControlView;
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
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static net.kehui.www.t_907_origin_V3.ConnectService.BROADCAST_ACTION_DEVICE_CONNECTED;
import static net.kehui.www.t_907_origin_V3.ConnectService.BROADCAST_ACTION_DEVICE_CONNECT_FAILURE;
import static net.kehui.www.t_907_origin_V3.ConnectService.BROADCAST_ACTION_DOWIFI_COMMAND;
import static net.kehui.www.t_907_origin_V3.ConnectService.BROADCAST_ACTION_DOWIFI_WAVE;
import static net.kehui.www.t_907_origin_V3.ConnectService.INTENT_KEY_COMMAND;
import static net.kehui.www.t_907_origin_V3.ConnectService.INTENT_KEY_WAVE;
import static net.kehui.www.t_907_origin_V3.application.Constant.CurrentUnit;
import static net.kehui.www.t_907_origin_V3.application.Constant.DISPLAY_ACTION;
import static net.kehui.www.t_907_origin_V3.application.Constant.FT_UNIT;
import static net.kehui.www.t_907_origin_V3.application.Constant.MI_UNIT;
import static net.kehui.www.t_907_origin_V3.application.Constant.batteryValue;
import static net.kehui.www.t_907_origin_V3.application.Constant.hasSavedPulseWidth;
import static net.kehui.www.t_907_origin_V3.application.Constant.waveLen;
import static net.kehui.www.t_907_origin_V3.ui.AutoDialog.HVINDICATOR;
import static net.kehui.www.t_907_origin_V3.ui.AutoDialog.WARNING;

public class ModeActivity extends BaseActivity {

    @BindView(R.id.tv_gain_add)
    ImageView tvGainAdd;
    @BindView(R.id.tv_gain_min)
    ImageView tvGainMin;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    /**
     * 当前点高度 //GT20200619
     */
    /*@BindView(R.id.tv_height)
    TextView tvHeight;*/
    /**
     * 自动测距结果 //GC20190708
     */
    @BindView(R.id.tv_information)
    TextView tvInformation;
    @BindView(R.id.tv_pulse_width)
    ImageView tvPulseWidth;
    @BindView(R.id.tv_compare)
    ImageView tvCompare;
    @BindView(R.id.tv_cal)
    ImageView tvCal;
    @BindView(R.id.tv_range)
    ImageView tvRange;
    @BindView(R.id.tv_file)
    ImageView tvFile;
    @BindView(R.id.ll_adjust)
    LinearLayout llAdjust;

    @BindView(R.id.content)
    FrameLayout Content1;
    /*@BindView(R.id.tv_home)
    ImageView tvHome;*/
    @BindView(R.id.tv_zero)
    ImageView tvZero;
    @BindView(R.id.tv_cursor_plus)
    ImageView tvCursorPlus;
    @BindView(R.id.tv_cursor_min)
    ImageView tvCursorMin;
    @BindView(R.id.tv_zoom_plus)
    ImageView tvZoomPlus;
    @BindView(R.id.tv_zoom_min)
    ImageView tvZoomMin;
    @BindView(R.id.tv_test)
    ImageView tvTest;
    @BindView(R.id.tv_help)
    ImageView tvHelp;
    @BindView(R.id.rl_feature)
    LinearLayout rlFeature;
    @BindView(R.id.mainWave)
    SparkView mainWave;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_range_value)
    TextView tvRangeValue;
    @BindView(R.id.tv_gain_value)
    TextView tvGainValue;
    @BindView(R.id.tv_vop_value)
    TextView tvVopValue;
    @BindView(R.id.tv_balance_value)
    TextView tvBalanceValue;
    @BindView(R.id.tv_zoom_value)
    TextView tvZoomValue;
    @BindView(R.id.ll_info)
    PercentRelativeLayout llInfo;
    @BindView(R.id.tv_balance_plus)
    ImageView tvBalancePlus;
    @BindView(R.id.tv_balance_min)
    ImageView tvBalanceMin;
    @BindView(R.id.layout_tv_memory)
    ImageView layoutTvMemory;
    @BindView(R.id.layout_tv_both)
    ImageView layoutTvBoth;
    @BindView(R.id.ll_compare)
    LinearLayout llCompare;
    @BindView(R.id.tv_vop_plus)
    ImageView tvVopPlus;
    @BindView(R.id.tv_vop_min)
    ImageView tvVopMin;
    @BindView(R.id.tv_vop_save)
    ImageView tvSave;
    @BindView(R.id.ll_cal)
    LinearLayout llCal;

    @BindView(R.id.tv_vop_j)
    ImageView tvVopj;
    @BindView(R.id.tv_vop_d)
    ImageView tvVopd;
    @BindView(R.id.tv_vop_g)
    ImageView tvVopg;
    @BindView(R.id.ll_cal_adjust)  //jk20210201
            LinearLayout llCalAdjust;
    @BindView(R.id.ll_lead)  //jk20210202
            LinearLayout llLead;
    @BindView(R.id.tv_250m)
    TextView tv250m;
    @BindView(R.id.tv_500m)
    TextView tv500m;
    @BindView(R.id.tv_1km)
    TextView tv1km;
    @BindView(R.id.tv_2km)
    TextView tv2km;
    @BindView(R.id.tv_4km)
    TextView tv4km;
    @BindView(R.id.tv_8km)
    TextView tv8km;
    @BindView(R.id.tv_16km)
    TextView tv16km;
    @BindView(R.id.tv_32km)
    TextView tv32km;
    @BindView(R.id.tv_64km)
    TextView tv64km;
    @BindView(R.id.ll_range)
    LinearLayout llRange;
    @BindView(R.id.tv_file_records)
    ImageView tvFileRecords;
    @BindView(R.id.ll_records)
    LinearLayout llRecords;
    @BindView(R.id.iv_compare_close)
    ImageView ivCompareClose;
    @BindView(R.id.iv_cal_close)
    ImageView ivGainClose;
    @BindView(R.id.iv_cal_close1) //jk20210201
            ImageView ivGainClose1;
    @BindView(R.id.iv_range_close)
    ImageView ivRangeClose;
    @BindView(R.id.iv_records_close)
    ImageView ivRecordsClose;
    @BindView(R.id.tv_decay_value)
    TextView tvDelayValue;
    @BindView(R.id.tv_delay_text)
    TextView tvDelayText;
    @BindView(R.id.tv_origin)
    ImageView tvOrigin;
    @BindView(R.id.tv_trigger_delay)
    ImageView tvTriggerDelay;
    @BindView(R.id.tv_records_save)
    ImageView tvRecordsSave;
    @BindView(R.id.tv_delay_plus)
    ImageView tvDelayPlus;
    @BindView(R.id.tv_delay_min)
    ImageView tvDelayMin;
    @BindView(R.id.ll_trigger_delay)
    LinearLayout llTriggerDelay;
    /**
     * 波宽度添加保存信息
     */
    @BindView(R.id.ll_pulse_width)
    LinearLayout llPulseWidth;
    @BindView(R.id.iv_pulse_width_close)
    ImageView ivPulseWidthClose;
    @BindView(R.id.et_pulse_width_id)
    public EditText etPulseWidth;
    @BindView(R.id.iv_wifi_status)
    ImageView ivWifiStatus;
    @BindView(R.id.iv_battery_status)
    ImageView ivBatteryStatus;
    @BindView(R.id.tv_balance_text)
    TextView tvBalanceText;
    @BindView(R.id.tv_balance_space)
    TextView tvBalanceSpace;
    @BindView(R.id.iv_close_trigger_delay)
    ImageView ivCloseTriggerDelay;
    @BindView(R.id.tv_delay_space)
    TextView tvDelaySpace;
    @BindView(R.id.tv_wave_text)
    TextView tvWaveText;
    @BindView(R.id.tv_wave_value)
    TextView tvWaveValue;
    @BindView(R.id.tv_wave_space)
    TextView tvWaveSpace;
    @BindView(R.id.view_move_vertical_wave)
    MoveWaveView viewMoveVerticalWave;
    @BindView(R.id.tv_wave_pre)
    ImageView tvWavePre;
    @BindView(R.id.tv_wave_next)
    ImageView tvWaveNext;
    @BindView(R.id.tv_distance_unit)
    TextView tvDistanceUnit;
    @BindView(R.id.rl_wave)
    RelativeLayout rlWave;
    @BindView(R.id.tv_vop_unit)
    TextView tvVopUnit;
    @BindView(R.id.mv_wave)
    MoveView mvWave;
    @BindView(R.id.tv_cable_vop_unit)
    TextView tvCableVopUnit;
    @BindView(R.id.tv_pulse_width_save)
    ImageView tvPulseWidthSave;
    @BindView(R.id.ll_horizontal_view)
    LinearLayout llHorizontalView;

    @BindView(R.id.tv_mode1)
    ImageView tvMode1;  //jk20210123
    @BindView(R.id.tv_range1)
    ImageView tvRange1;
    @BindView(R.id.tv_adjust1)
    ImageView tvAdjust1;
    @BindView(R.id.tv_wave1)
    ImageView tvWave1;
    @BindView(R.id.tv_file1)
    ImageView tvFile1;
    @BindView(R.id.tv_set1)
    ImageView tvSet1;

    @BindView(R.id.cb_test_lead1)
    CheckBox cbtestlead1;
    @BindView(R.id.et_pulse_length_id)
    EditText etLength1;
    @BindView(R.id.et_pulse_vop_id)
    EditText etVop1;
    @BindView(R.id.tv_save1)
    ImageView tvsave1;
    @BindView(R.id.iv_close)
    ImageView ivclose1;
    @BindView(R.id.tv_cable_lengtn_text)
    TextView tvlength1;
    @BindView(R.id.tv_cable_vop_unit2)
    TextView tvvop1;
    @BindView(R.id.tv_cable_vop_text)
    TextView tvvoplength;
    @BindView(R.id.tv_cable_vop_unit1)
    TextView tvvopu;

    /**
     * 信息栏  //GC20211201
     */
    @BindView(R.id.tv_info_working_mode)
    TextView tvInfoWorkingMode;
    @BindView(R.id.tv_info_TIME)
    TextView tvInfoTIME;
    @BindView(R.id.tv_info_set_voltage)
    TextView tvInfoSetVoltage;
    @BindView(R.id.tv_info_HV)
    TextView tvInfoHV;
    @BindView(R.id.iv_info_PULSE)
    ImageView ivPULSE;
    @BindView(R.id.ll_AUTO)
    LinearLayout llAUTO;
    @BindView(R.id.tv_info_mode)
    TextView tvInfoMode;
    @BindView(R.id.ll_info_hv)
    LinearLayout llInfoHv;

    private int index;
    //计算滑动时的基数
    private int fenzi1;
    //初始化滑块位置
    private int fenzi2;
    private int currentActionDownX = 0;

    private boolean alreadyDisplayWave;
    //20200407
    private boolean allowSetRange = true;
    //20200523
    private boolean canClickCancelButton;
    //设置是否需要进入页面接收数据，此处是为了适配从主页面展示波形时重复接收数据
    private boolean isReceiveData = true;

    private TDialog tDialog;

    private FragmentManager fragmentManager;
    private ModeFragment modeFragment;   //jk20210123
    private RangeFragment rangeFragment;
    private AdjustFragment adjustFragment;
    private WaveFragment waveFragment;
    private FileFragment fileFragment;
    private SettingFragment settingFragment;

    public static int flag_close; //jk20210203  物理按键返回控制
    /**
     * 定义bundle的key-value
     */
    public static final String BUNDLE_MODE_KEY = "mode";
    public static final String BUNDLE_COMMAND_KEY = "command";
    public static final String BUNDLE_DATA_TRANSFER_KEY = "dataTransfer";
    public static final String BUNDLE_PARAM_KEY = "bundle_param_key";
    public static final String BUNDLE_DATA_TRANSFER_KEY2 = "dataTransfer2";  //与连接服务ConnectService进行参数传递    //GC20211206
    public static final String BUNDLE_DATA_TRANSFER_KEY3 = "dataTransfer3";

    /**
     * 全局的handler对象用来执行UI更新
     */
    public static final int DO_WAVE = 1;
    public static final int VIEW_REFRESH = 2;
    public static final int DISPLAY_DATABASE = 3;

    public Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case DO_WAVE:
                //处理波形数据wifiStreamNew（通过广播获取）   //GC20211208
                int[] wifiStreamNew = msg.getData().getIntArray("WAVE");
                assert wifiStreamNew != null;
                doWifiWave(wifiStreamNew);
                break;
            case VIEW_REFRESH:
                if (mode != SIM) {
                    //组织波形数据    //SIM需要单独写
                    if (density < densityMax) {
                        //有缩放时
                        organizeZoomWaveData(currentStart);
                    } else {
                        organizeWaveData();
                    }
                    //显示波形
                    displayWave();
                }
                if (mode == TDR) {    //jk20200807
                    //长按测试按键，调整平衡、范围、增益后再自动定位   //GC20200916
                    if (isLongClick) {
                        llAdjust.setVisibility(View.GONE); //jk20210125
                        if (!longTestInit) {
                            longTestInit();
                        } else {
//                            tdrAutoTestLong();
                            if (!balanceIsReady) {
                                selectBalance();
                                //selectGain1();
                            } else {
                                if (!rangeIsReady) {
                                    selectRange();
                                } else {
                                    selectGain();
                                }
                            }
                        }
                    }
                }
                //TODO 20200407 波形绘制完毕，恢复测试按钮可用性，允许请求电量
                Constant.isTesting = false;
                ConnectService.canAskPower = true;
                allowSetRange = true;
                tvTest.setEnabled(true);
                Log.e("【请求电量时机控制】", "波形绘制完毕，允许请求电量。");  //GC20211209 是否需要添加波形和高压指令的判断？
                break;
            case DISPLAY_DATABASE:
                //数据库打开算法结果显示调试 //GT20200629
                /*if ((mode == ICM) ){
                    icmAutoTest();
                } else if((mode == ICM_DECAY)){
                    icmAutoTestDC();
                } */ //jk20201023
               /* if ((mode == TDR) ){
                   tdrAutoTest();  //jk20210310
                }*/
                //显示记录波形
                setDateBaseParameter();
                try {
                    organizeWaveData();
                    displayWave();
                } catch (Exception l_ex) {
                }
                break;
            default:
                break;
        }
        return false;
    });

    /**
     * 定义监听广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //G?? 作用是啥
            handler.sendEmptyMessage(intent.getIntExtra("display_action", 0));

            String action = intent.getAction();
            assert action != null;
            switch (action) {
                case BROADCAST_ACTION_DEVICE_CONNECTED:
                    Log.e("ModeActivity", "连接成功");
                    //网络连接，更换网络图标
                    ConnectService.isConnected = true;
                    ivWifiStatus.setImageResource(R.drawable.ic_wifi_connected);
                    //重连有对话框消对话框    //GC20200319
                    if (tDialog != null) {
                        tDialog.dismiss();
                        Log.e("DIA", "重连先取消对话框");
                    }
                    //如果网络连接后于读取本地波形数据，则再网络连接时设置读出的几个参数。
                    if (!isReceiveData || isDatabase) {
                        setModeNoCmd(Constant.Para[0]);
                        setRangeNoCmd(Constant.Para[1]);
                        setGain(Constant.Para[2]);
                        setVelocityNoCmd(Constant.Para[3]);
                        //读取本地数据时参数设置   //20200523
                        Constant.isTesting = false;
                        allowSetRange = true;
                        alreadyDisplayWave = false;
                    } else {
                        //取消测试中状态
                        //已显示波形状态恢复为true    //20200407
                        Constant.isTesting = false;
                        allowSetRange = true;
                        alreadyDisplayWave = false;
                        //连接设备初始化（包括重连）
                        //方式
                        setMode(mode);
                        handler.postDelayed(() -> {
                            //范围
                            setRange(range);
                        }, 20);
                        handler.postDelayed(() -> {
                            //增益
                            setGain(gain);
                        }, 20);
                        //不同模式下初始化发射不同命令  //GC20200424
                        if (mode == TDR) {
                            handler.postDelayed(() -> {
                                //脉宽
                                setPulseWidth(pulseWidth);
                            }, 20);
                        } else if (mode == ICM || mode == SIM) {
                            handler.postDelayed(() -> {
                                //延时
                                delay = 0;
                                setDelay(delay);
                            }, 20);
                            if (mode == SIM) {
                                handler.postDelayed(() -> {
                                    //脉宽
                                    setPulseWidth(pulseWidthSim);
                                }, 20);
                            }
                        }
                        //延时100毫秒发送测试命令，100毫秒是等待设备回复命令信息，如果不延时，有可能设备执行不完命令。
                        handler.postDelayed(ModeActivity.this::clickTest, 100);
                    }
                    break;
                case BROADCAST_ACTION_DEVICE_CONNECT_FAILURE:
                    //网络断开，更换网络图标、电量图标
                    ConnectService.isConnected = false;
                    ivWifiStatus.setImageResource(R.drawable.ic_no_wifi_connect);
                    ivBatteryStatus.setImageResource(R.drawable.ic_battery_no);
                    //界面电量状态记录   //GC20200314
                    batteryValue = -1;
                    break;
                case BROADCAST_ACTION_DOWIFI_COMMAND:
                    //处理命令数据wifiStream（通过广播获取）  //GC20211208
                    wifiStream = intent.getIntArrayExtra(INTENT_KEY_COMMAND);
                    assert wifiStream != null;
                    doWifiCommand(wifiStream);
                    break;
                case BROADCAST_ACTION_DOWIFI_WAVE:
                    //64公里波形数据过大，正常的广播无法传递，改成全局变量。
                    //int[] wifiStreamNew = ConnectService.mExtra;
                    //wifiStream = ConnectService.mExtra;
                    //assert wifiStreamNew != null;
                    //通过广播得到波形数据wifiStreamNew    //GC20211208
                    int[] wifiStreamNew = intent.getIntArrayExtra(INTENT_KEY_WAVE);
                    if (wifiStreamNew[3] == WAVE_TDR_ICM_DECAY || wifiStreamNew[3] == WAVE_SIM) {
                        setWaveParameter();
                    }
                    Message message = Message.obtain();
                    message.what = ModeActivity.DO_WAVE;
                    Bundle bundle = new Bundle();
                    bundle.putIntArray("WAVE", wifiStreamNew);
                    message.setData(bundle);
                    handler.sendMessage(message);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 开始下载文件  //jk20210202  changshi
     */
    public void showup() {
        Toast.makeText(ModeActivity.this, R.string.check_update, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (MainActivity.baseset == 1) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mode);
            Log.e("【启动页面】", "进入mode页面。" + "baseset" + MainActivity.baseset);
            ButterKnife.bind(this);

            tvZoomPlus.setVisibility(View.GONE); //jk20210126
            tvZoomMin.setVisibility(View.GONE);
            tvSet1.setVisibility(View.GONE);    //jk20210128
            tvFile1.setVisibility(View.GONE);
            tvHelp.setVisibility(View.GONE);//jk20210319 去掉帮助

            alreadyDisplayWave = false;
            mode = getIntent().getIntExtra(BUNDLE_MODE_KEY, 0);
            isReceiveData = getIntent().getBooleanExtra("isReceiveData", true);
            initUnit();
            initSparkView();
            initViewMoveWave();
            initBtnRangeView();
            //波宽度初始化
            initPulseWidth();
            initRange();
            initFrame();
            setChartListener();
            initMode();
            initBroadcastReceiver();
            CableInit(); //jk20210202
            //发送广播——处理从主页中显示的本地数据
            if (getIntent().getIntExtra("display_action", 0) == DISPLAY_DATABASE) {
                isReceiveData = false;
                Intent intent = new Intent(DISPLAY_ACTION);
                intent.putExtra("display_action", ModeActivity.DISPLAY_DATABASE);
                sendBroadcast(intent);
            }
        }
        MainActivity.baseset = 0;   //jk20210206  解决电源按键问题
        Log.e("【启动页面】", "baseset1" + MainActivity.baseset);
    }

    /**
     * 计量单位初始化 (m ft)
     */
    private void initUnit() {
        CurrentUnit = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_SAVE_UNIT, MI_UNIT);
        Constant.CurrentSaveUnit = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_SAVE_UNIT, MI_UNIT);
        if (CurrentUnit == MI_UNIT) {
            tvDistanceUnit.setText(R.string.mi);
            tvVopUnit.setText(R.string.mius);
        } else {
            tvDistanceUnit.setText(R.string.ft);
            tvVopUnit.setText(R.string.ftus);
        }
    }

    /**
     * 初始化sparkView
     */
    public void initSparkView() {
        for (int i = 0; i < 510; i++) {
            waveArray[i] = 128;
            //Constant.WaveData[i] = 128;
        }
        myChartAdapterMainWave = new MyChartAdapterBase(waveArray, null,
                false, 0, false);
        mainWave.setAdapter(myChartAdapterMainWave);
        setMoveView();
        Log.i("Draw", "初始化绘制结束");

    }

    /**
     * 初始化波形移动监听事件
     */
    private void initViewMoveWave() {

        viewMoveVerticalWave.setViewMoveWaveListener(new MoveWaveView.ViewMoveWaveListener() {
            @Override
            public void onMoved(float x, float y) {
                //上下移动波形
                mainWave.setSparkViewMove(y);
                Log.e("ModeActivity", y + "");
            }

            @Override
            public void onMoveEnded() {
            }
        });
        fenzi1 = (dataLength / (densityMax / density)) - 510;

        //下方滑条移动时，重新绘制波形
        mvWave.setViewMoveWaveListener(x -> {
            //滑块左侧位置（从0开始）
            int currentMoverX = get510Value(x, mvWave.getParentWidth());
            Log.e("【滑块】", "当前X：" + x + " /控件长度：" + mvWave.getParentWidth() + "左侧位置" + currentMoverX);
            //波形起始点在原始数据中的位置
            currentStart = currentMoverX * densityMax;
            //重新抽点绘制波形
            organizeZoomWaveData(currentStart);
            try {
                organizeCompareWaveData(currentStart);
            } catch (Exception ignored) {
            }
            displayWave();
            //根据起始点判断是否画实光标     //GC20200611   滑块移动
            if ((zero < currentStart) || (zero >= currentStart + 510 * density)) {
                mainWave.setScrubLineRealDisappear();
            } else {
                positionReal = (zero - currentStart) / density;
                mainWave.setScrubLineReal(positionReal);
            }
            //判断是否画虚光标——需要监听虚光标位置变化，用来计算距离
            positionVirtual = (pointDistance - currentStart) / density;
            if ((pointDistance < currentStart) || (pointDistance >= currentStart + 510 * density)) {
                mainWave.setScrubLineVirtualDisappear();
            } else {
                mainWave.setScrubLineVirtual(positionVirtual);
            }
            //判断是是否画标记光标    //GC20200330
            if (mode == SIM) {
                if ((simStandardZero < currentStart) || (simStandardZero >= currentStart + 510 * density)) {
                    mainWave.setScrubLineSimDisappear();
                } else {
                    positionSim = simStandardZero / density;
                    mainWave.setScrubLineSim(positionSim);
                }
            }
            //当前滑块左侧在510个点中的位置
            currentMoverPosition510 = currentMoverX;
            Log.e("【滑块】", "波形起始点位置: " + currentStart + " /当前滑块左侧510中的位置: " + currentMoverPosition510);
        });

    }

    /**
     * 将滑块左侧在滑条的原始位置转换为滑条长度为510的数值（由0开始）
     *
     * @param x      滑块左侧在滑条控件里面的位置，单位为像素
     * @param length 滑条长度
     * @return 转换好的值
     */
    private int get510Value(float x, float length) {
        return (int) (510 * x / length);
    }

    /**
     * 在sparkView界面显示波形
     */
    private void displayWave() {
        if (densityMax == 1) {
            //如果最大比例为1，不允许按缩放按键     //20200523  //GN界面优化可能用到
            tvZoomPlus.setEnabled(false);
            tvZoomMin.setEnabled(false);
            waveFragment.btnZoomIn.setEnabled(false);//jk20210126
            waveFragment.btnZoomOut.setEnabled(false);//jk20210126
            waveFragment.btnZoomIn.setImageResource(R.drawable.bg_fangda);//jk20210130cs
            waveFragment.btnZoomOut.setImageResource(R.drawable.bg_suoxiao);//jk20210130cs
            waveFragment.btnRes.setImageResource(R.drawable.bg_huanyuan);//jk20210130cs
        } else {
            tvZoomPlus.setEnabled(true);
            waveFragment.btnZoomIn.setEnabled(true);//jk20210126
            waveFragment.btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector);//jk20210130cs
        }
        if (mode == SIM) {
            if (isDatabase) {
                //数据库SIM上翻下翻按钮无效  //GC20200604
                tvWavePre.setEnabled(false);
                waveFragment.btnWavePrevious.setEnabled(false);
                tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_false);
                waveFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
                tvWaveNext.setEnabled(false);
                waveFragment.btnWaveNext.setEnabled(false);
                tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_false);
                waveFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
                //数据库SIM波形无波形序号
                tvWaveText.setVisibility(View.GONE);
                tvWaveValue.setVisibility(View.GONE);
            } else {
                tvWaveText.setVisibility(View.VISIBLE);
                tvWaveValue.setVisibility(View.VISIBLE);
            }
        }
        //画波形
        myChartAdapterMainWave.setmTempArray(waveDraw);
        myChartAdapterMainWave.setShowCompareLine(isCom);
        if (mode == SIM) {
            if (isCom) {
                myChartAdapterMainWave.setmCompareArray(waveCompare);
            }
        }
        myChartAdapterMainWave.notifyDataSetChanged();
        //有对话框消对话框
        if (tDialog != null) {
            tDialog.dismissAllowingStateLoss();
            Log.e("DIA", "正在接受数据隐藏" + " 波形绘制完成");
        }
        alreadyDisplayWave = true;

    }

    /**
     * 初始化英尺状态下范围按钮文字
     */
    private void initBtnRangeView() {
        if (CurrentUnit == FT_UNIT) {
            tv250m.setText(getResources().getString(R.string.btn_250m_to_ft));
            tv500m.setText(getResources().getString(R.string.btn_500m_to_ft));
            tv1km.setText(getResources().getString(R.string.btn_1km_to_yingli));
            tv2km.setText(getResources().getString(R.string.btn_2km_to_yingli));
            tv4km.setText(getResources().getString(R.string.btn_4km_to_yingli));
            tv8km.setText(getResources().getString(R.string.btn_8km_to_yingli));
            tv16km.setText(getResources().getString(R.string.btn_16km_to_yingli));
            tv32km.setText(getResources().getString(R.string.btn_32km_to_yingli));
            tv64km.setText(getResources().getString(R.string.btn_64km_to_yingli));
        }
    }

    /**
     * 初始化波宽度
     */
    private void initPulseWidth() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PULSE_WIDTH_INFO_KEY);
        if (paramInfo != null) {
            if (hasSavedPulseWidth) {
                //保存过脉宽才进行读取和初始化操作   //GC20200331
                pulseWidth = paramInfo.getPulseWidth();
                etPulseWidth.setText(String.valueOf(pulseWidth));
            }
        }

        etPulseWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    int pulseWidth = Integer.parseInt(s.toString());
                    int maxPulseWidth = 7000;
                    if (pulseWidth > maxPulseWidth) {
                        Toast.makeText(ModeActivity.this, getResources().getString(R.string
                                .maxpulsewidth), Toast.LENGTH_SHORT).show();
                        etPulseWidth.setText(maxPulseWidth + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 读取主页设置中存储的电缆长度，并初始化范围
     */
    private void initRange() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        double localRange;
        if (paramInfo != null && paramInfo.getCableLength() != null && !TextUtils.isEmpty(paramInfo.getCableLength())) {
            localRange = Double.valueOf(paramInfo.getCableLength());

            if (localRange == 0.0 || localRange == 0) {
                range = (0x11);
                rangeState = 1;
                gain = 13;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                }
                //初始化脉宽数值（未保存过脉宽） //GC20200331
                if (!hasSavedPulseWidth) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                //初始化SIM的脉宽值    //GC20200527
                pulseWidthSim = 320;
            } else if (localRange <= 250) {
                range = (0x99);
                rangeState = 0;
                gain = 13;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m_to_ft));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                pulseWidthSim = 320;
            } else if (localRange > 250 && localRange <= 500) {
                range = (0x11);
                rangeState = 1;
                gain = 13;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                pulseWidthSim = 320;
            } else if (localRange > 500 && localRange <= 1000) {
                range = (0x22);
                rangeState = 2;
                gain = 13;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 80;
                    etPulseWidth.setText(String.valueOf(80));
                }
                pulseWidthSim = 320;
            } else if (localRange > 1000 && localRange <= 2000) {
                range = (0x33);
                rangeState = 3;
                gain = 10;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 160;
                    etPulseWidth.setText(String.valueOf(160));
                }
                pulseWidthSim = 720;
            } else if (localRange > 2000 && localRange <= 4000) {
                range = (0x44);
                rangeState = 4;
                gain = 10;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 320;
                    etPulseWidth.setText(String.valueOf(320));
                }
                pulseWidthSim = 2560;
            } else if (localRange > 4000 && localRange <= 8000) {
                range = (0x55);
                rangeState = 5;
                gain = 10;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 640;
                    etPulseWidth.setText(String.valueOf(640));
                }
                pulseWidthSim = 3600;
            } else if (localRange > 8000 && localRange <= 16000) {
                range = (0x66);
                rangeState = 6;
                gain = 9;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 1280;
                    etPulseWidth.setText(String.valueOf(1280));
                }
                pulseWidthSim = 7120;
            } else if (localRange > 16000 && localRange <= 32000) {
                range = (0x77);
                rangeState = 7;
                gain = 9;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 2560;
                    etPulseWidth.setText(String.valueOf(2560));
                }
                pulseWidthSim = 10200;
            } else if (localRange > 32000) {
                range = (0x88);
                rangeState = 8;
                gain = 9;
                if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km_to_yingli));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km));
                }
                if (!hasSavedPulseWidth) {
                    pulseWidth = 5120;
                    etPulseWidth.setText(String.valueOf(5120));
                }
                pulseWidthSim = 10200;
            }
        } else {
            //没有设置的距离
            range = (0x11);
            rangeState = 1;
            if (CurrentUnit == FT_UNIT) {
                tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
            } else {
                tvRangeValue.setText(getResources().getString(R.string.btn_500m));
            }
            //GC20200331
            if (!hasSavedPulseWidth) {
                pulseWidth = 40;
                etPulseWidth.setText(String.valueOf(40));
            }
            //GC20200527
            pulseWidthSim = 320;
        }
        Constant.RangeValue = range;
        //GC20200428
        selectWaveLength();

    }

    /**
     * 根据方式、范围选取判断收取波形数据的点数 //GC20200428
     */
    private void selectWaveLength() {
        if (mode == TDR) {
            waveLen = READ_TDR_SIM[rangeState] + 9;
        } else if ((mode == ICM) || (mode == ICM_DECAY) || (mode == DECAY)) {
            waveLen = READ_ICM_DECAY[rangeState] + 9;
        } else if (mode == SIM) {
            waveLen = (READ_TDR_SIM[rangeState] + 9) * 9;
        }
        Log.e("#【收数长度选择】", " 需要绘制:" + waveLen);
    }

    /**
     * 初始化界面框架
     */
    public void initFrame() {
        //高压操作按钮和信息栏隐藏  //GC20211207
        llAUTO.setVisibility(View.GONE);
        llInfoHv.setVisibility(View.GONE);
        //jk20210123
        fragmentManager = getSupportFragmentManager();
        //setTabSelection(0);
        setTabSelection(1);
        setTabSelection(2);
        setTabSelection(3);
        setTabSelection(4);
        setTabSelection(5);
        //第一次启动时选中第0个tab
        llAdjust.setVisibility(View.GONE);
        setTabSelection(0);
        tvMode1.setImageResource(R.drawable.bg_mode_pressed1); //jk20210129
        //modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_pressed);  //jk20210129
        Constant.ModeValue = TDR;
        tvGainValue.setText(String.valueOf(gain));
        Constant.Gain = gain;
        //  velocity = getLocalValue();
        velocity = 172; //JK20210713
        setVelocity(velocity);
        Constant.Velocity = velocity;
        //  tvVopValue.setText(String.valueOf(velocity)); //JK20210713
        tvBalanceValue.setText(String.valueOf(balance));
        tvZoomValue.setText("1 : " + density);
        tvDelayValue.setText(delay + "μs");
        //初始化距离显示
        calculateDistance(Math.abs(pointDistance - zero));
        //初始化自动测距结果显示    //GC20190708
        tvInformation.setVisibility(View.GONE);
        //SIM光标位置初始化（可以自定义）    //GC20190712
        simOriginalZero = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_CURSOR_POSITION, 12);
        //SIM标记光标（可以自定义）   //GC20200612
        simStandardZero = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_CURSOR_POSITION, 12);
        //测试缆信息添加    //GC20200103
        leadLength = getLocalLength();
        leadVop = getLocalVop();
        //模式及面电量图标初始化 //GC20200314
        if (batteryValue == 0) {
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_zero);
        } else if (batteryValue == 1) {
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_one);
        } else if (batteryValue == 2) {
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_two);
        } else if (batteryValue == 3) {
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_three);
        } else if (batteryValue == 4) {
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_four);
        } else if (batteryValue == -1) {
            //无WIFI电量图标
            ivBatteryStatus.setImageResource(R.drawable.ic_battery_no);
        }

    }

    /**
     * 监听虚光标位置变化
     */
    private void setChartListener() {
        //波形下方部分区域拿出，做波形的左右滑动
        mainWave.setScrubListener(new SparkView.OnScrubListener() {
            @Override
            public void onActionDown(Object x, float y) {
                Log.e("【波形区域坐标】", "onActionDown:" + " /X:" + x + " /Y:" + y);
                //手指按下的横坐标位置记录
                currentActionDownX = (Integer) x;
            }

            @Override
            public void onScrubbed(Object value, float y) {
                //y值越小，波形滑动区域越大  //GC20200528
                //if (y < 600) {
                if (y < 600) {
                    if ((Integer) value <= 510) {
                        //画虚光标
                        mainWave.setScrubLineVirtual((Integer) value);
                        //虚光标变化的数值
                        positionVirtualChange = (int) value - positionVirtual;
                        Log.e("【波形区域虚光标】", "当前位置" + value + " /变化前positionVirtual:" + positionVirtual + " /虚光标变化值:" + positionVirtualChange);
                        //变化后虚光标在原始数据中的位置
                        pointDistance = pointDistance + positionVirtualChange * density;
                        //GT20200619
                        /*int height;
                        if (mode == SIM) {
                            height = Constant.SimData[pointDistance];
                        } else {
                            height = Constant.WaveData[pointDistance];
                        }
                        Log.e("【高度】", "当前点高度" + height);
                        tvHeight.setText("高度" + height);*/
                        //虚光标在画布中的位置
                        positionVirtual = (int) value;
                        //监听虚光标变化去掉 //GC20200611
                        /*int waveDataStart = currentMoverPosition510 * dataLength / 510;
                        Log.e("【虚光标】", "滑动结束：value:" + value + "/positionVirtual:" + positionVirtual + "/positionVirtualChange:" + positionVirtualChange + "/pointDistance" + pointDistance + "/zero:" + zero + "/waveDataStart:" + waveDataStart);
                        if (positionVirtual == 0 && zero == 0 && waveDataStart < 510) {
                            pointDistance = 0;
                        }*/
                        //距离显示
                        calculateDistance(Math.abs(pointDistance - zero));
                    }
                } else {
                    Log.e("【波形区域滑块】", "value：" + value + " /currentActionDownX：" + currentActionDownX);
                    //计算手指滑动的距离
                    moverMoveValue = (Integer) value - currentActionDownX;
                    //当前滑块左侧在510个点中的位置
                    currentMoverPosition510 = currentMoverPosition510 + moverMoveValue;
                    Log.e("【波形区域滑块】", "当前滑块左侧510中的位置：" + currentMoverPosition510);
                    currentStart = currentMoverPosition510 * densityMax;
                    //滑块x位置大于等于0且滑块X位置+滑块宽度不超过波形区域，则允许波形滑动。
                    if (currentMoverPosition510 >= 0 && (currentMoverPosition510 + (mvWave.getWidth() * 510 / mvWave.getParentWidth())) <= 510 && density != densityMax) {
                        //重新抽点绘制波形
                        organizeZoomWaveData(currentStart);
                        try {
                            organizeCompareWaveData(currentStart);
                        } catch (Exception ignored) {
                        }
                        //重新绘制波形
                        displayWave();
                        //根据起始点判断是否画实光标     //GC20200611   滑块移动
                        if ((zero < currentStart) || (zero >= currentStart + 510 * density)) {
                            mainWave.setScrubLineRealDisappear();
                        } else {
                            positionReal = (zero - currentStart) / density;
                            mainWave.setScrubLineReal(positionReal);
                        }
                        //判断是否画虚光标——需要监听虚光标位置变化，用来计算距离
                        positionVirtual = (pointDistance - currentStart) / density;
                        if ((pointDistance < currentStart) || (pointDistance >= currentStart + 510 * density)) {
                            mainWave.setScrubLineVirtualDisappear();
                        } else {
                            mainWave.setScrubLineVirtual(positionVirtual);
                        }
                        //判断是是否画标记光标    //GC20200330
                        if (mode == SIM) {
                            if ((simStandardZero < currentStart) || (simStandardZero >= currentStart + 510 * density)) {
                                mainWave.setScrubLineSimDisappear();
                            } else {
                                positionSim = simStandardZero / density;
                                mainWave.setScrubLineSim(positionSim);
                            }
                        }

                        //手指拖动时关联移动滑块到指定位置
                        int moverPosition;
                        moverPosition = mvWave.getParentWidth() * currentMoverPosition510 / 510;
                        setMoverPosition(moverPosition);
                    } else {
                        currentMoverPosition510 = currentMoverPosition510 - moverMoveValue;
                    }
                    currentActionDownX = (Integer) value;
                }
            }
        });

    }

    //初始化模式
    private void initMode() {
        switch (mode) {
            case TDR:
                initTDRView();
                break;
            case ICM:
                initICMSURGEView();
                break;
            case ICM_DECAY:
                initICMDECAYView();
                break;
            case SIM:
                initSIMView();
                break;
            case DECAY:
                initDecayView();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化广播接收器
     */
    private void initBroadcastReceiver() {
        IntentFilter ifDisplay = new IntentFilter();
        ifDisplay.addAction(BROADCAST_ACTION_DEVICE_CONNECTED);
        ifDisplay.addAction(BROADCAST_ACTION_DEVICE_CONNECT_FAILURE);
        ifDisplay.addAction(BROADCAST_ACTION_DOWIFI_COMMAND);
        ifDisplay.addAction(BROADCAST_ACTION_DOWIFI_WAVE);
        ifDisplay.addAction(DISPLAY_ACTION);
        registerReceiver(receiver, ifDisplay);
        //判断服务里连接状态，如果已经连接，则发送广播，改变连接状态
        if (ConnectService.isConnected) {
            Intent intent = new Intent(BROADCAST_ACTION_DEVICE_CONNECTED);
            sendBroadcast(intent);
        }
    }

    /**
     * 处理APP接收的波形数据
     */
    private void doWifiWave(int[] wifiArray) {
        if (wifiArray[3] == WAVE_TDR_ICM_DECAY) {
            //非二次脉冲波形
            //  for(int i=5;i<8;i++){    //jk20200821
            //          wifiArray[i]=128;
            //  }
            System.arraycopy(wifiArray, 8, waveArray, 0, dataMax);
            // System.arraycopy(wifiArray, 5, waveArray , 0, dataMax);       //jk20200821
            Constant.WaveData = waveArray;
            //GC20191231
            if (mode == ICM) {
                icmAutoTest();
            } else if (mode == ICM_DECAY) {
                icmAutoTestDC();    //GC20200109 增加DC方式下的自动测距
            } else if (mode == TDR) {
                //单击测试按键直接自动定位    //GC20200916
                if (!isLongClick) {
                    tdrAutoTest();
                }
            }
            //组织数据画波形
            handler.sendEmptyMessage(VIEW_REFRESH);
        } else if (wifiArray[3] == WAVE_SIM
                || wifiArray[3] == 0x88 || wifiArray[3] == 0x99 || wifiArray[3] == 0xAA || wifiArray[3] == 0xBB
                || wifiArray[3] == 0xCC || wifiArray[3] == 0xDD || wifiArray[3] == 0xEE || wifiArray[3] == 0xFF) {
            //二次脉冲波形
            if (wifiArray[3] == WAVE_SIM) {
                System.arraycopy(wifiArray, 8, waveArray, 0, dataMax);
                Constant.WaveData = waveArray;
                //重合判断准备    //GC20200529
                simSum[0] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[0] += waveArray[i];
                }
                Log.e("【MIM】", "第1条");
            }
            if (wifiArray[3] == 0x88) {
                System.arraycopy(wifiArray, 8, simArray1, 0, dataMax);
                Constant.TempData1 = simArray1;
                simSum[1] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[1] += simArray1[i];
                }
                Log.e("【MIM】", "第2条");
            }
            if (wifiArray[3] == 0x99) {
                System.arraycopy(wifiArray, 8, simArray2, 0, dataMax);
                Constant.TempData2 = simArray2;
                simSum[2] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[2] += simArray2[i];
                }
                Log.e("【MIM】", "第3条");
            }
            if (wifiArray[3] == 0xAA) {
                System.arraycopy(wifiArray, 8, simArray3, 0, dataMax);
                Constant.TempData3 = simArray3;
                simSum[3] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[3] += simArray3[i];
                }
                Log.e("【MIM】", "第4条");
            }
            if (wifiArray[3] == 0xBB) {
                System.arraycopy(wifiArray, 8, simArray4, 0, dataMax);
                Constant.TempData4 = simArray4;
                simSum[4] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[4] += simArray4[i];
                }
                Log.e("【MIM】", "第5条");
            }
            if (wifiArray[3] == 0xCC) {
                System.arraycopy(wifiArray, 8, simArray5, 0, dataMax);
                Constant.TempData5 = simArray5;
                simSum[5] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[5] += simArray5[i];
                }
                Log.e("【MIM】", "第6条");
            }
            if (wifiArray[3] == 0xDD) {
                System.arraycopy(wifiArray, 8, simArray6, 0, dataMax);
                Constant.TempData6 = simArray6;
                simSum[6] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[6] += simArray6[i];
                }
                Log.e("【MIM】", "第7条");
            }
            if (wifiArray[3] == 0xEE) {
                System.arraycopy(wifiArray, 8, simArray7, 0, dataMax);
                Constant.TempData7 = simArray7;
                simSum[7] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[7] += simArray7[i];
                }
                Log.e("【MIM】", "第8条");
            }
            if (wifiArray[3] == 0xFF) {
                System.arraycopy(wifiArray, 8, simArray8, 0, dataMax);
                Constant.TempData8 = simArray8;
                //数据全部获取完成，组织数据    //GC20200601
                if (density < densityMax) {
                    organizeZoomWaveData(currentStart);
                } else {
                    organizeWaveData();
                }
                simSum[8] = 0;
                for (int i = 0; i < dataMax; i++) {
                    simSum[8] += simArray8[i];
                }
                //SIM波形全部获取完成   //GC20200529
                receiveSimOver = true;
                Log.e("【MIM】", "第9条");
            }
            if (receiveSimOver) {
                //SIM筛选功能添加 //GC20200529
                selectBestSim();
                handler.sendEmptyMessage(VIEW_REFRESH);
                receiveSimOver = false;
            }
        }

    }

    //读取本地存储的波速度信息，默认为172，计算距离时要求是米单位，所以如果数据库里存的是英尺数，应该转换为米数。
    private double getLocalValue() {
        double vopValue = 172;
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        if (paramInfo != null) {
            if (paramInfo.getCableVop() != null && !TextUtils.isEmpty(paramInfo.getCableVop())) {
                //单位转换逻辑修正  //20200522
                vopValue = Double.valueOf(paramInfo.getCableVop());
            }
        }
        if (vopValue == 0 || vopValue == 0.0) {
            vopValue = 172;
        }
        return vopValue;
    }

    /**
     * 读取本地存储的测试缆状态
     */
    private double getLocalLength() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        if (paramInfo != null) {
            if (paramInfo.getLength() != null && !TextUtils.isEmpty(paramInfo.getLength())) {
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    leadLength = Double.valueOf(paramInfo.getLength());
                } else {
                    leadLength = Double.valueOf(UnitUtils.ftToMi(Double.valueOf(paramInfo.getLength())));
                }
            }
        }
        return leadLength;
    }

    /**
     * 读取本地存储的测试缆状态
     */
    private double getLocalVop() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        if (paramInfo != null) {
            if (paramInfo.getVop() != null && !TextUtils.isEmpty(paramInfo.getVop())) {
                if (Constant.CurrentSaveUnit == MI_UNIT) {
                    leadVop = Double.valueOf(paramInfo.getVop());
                } else {
                    leadVop = Double.valueOf(UnitUtils.ftToMi(Double.valueOf(paramInfo.getVop())));
                }
            }
        }
        if (leadVop == 0 || leadVop == 0.0) {
            leadVop = 172;
        }
        return leadVop;
    }

    /**
     * 处理APP接收的命令 + 电池电量接收
     */
    private void doWifiCommand(int[] wifiArray) {
        //判断仪器触发时：APP发送接收数据命令
        if ((wifiArray[5] == COMMAND_TRIGGER) && (wifiArray[6] == TRIGGERED)) {
            command = COMMAND_RECEIVE_WAVE;
            dataTransfer = mode;
            //发送指令
            startService();
            //未显示波形为否   //20200523
            alreadyDisplayWave = false;
            // Log.e("【时效测试】", "发送接收波形数据命令");
            ConnectService.canAskPower = false;
            if (tDialog != null) {
                tDialog.dismiss();
            }
            tDialog = new TDialog.Builder(getSupportFragmentManager())
                    .setLayoutRes(R.layout.receiving_data)
                    .setScreenWidthAspect(this, 0.25f)
                    .setCancelableOutside(false)
                    .create()
                    .show();
            Log.e("DIA", " 正在接受数据显示" + " ICM/SIM/DECAY");
        } else if (wifiArray[5] == POWER_DISPLAY) {
            int batteryValue = wifiArray[6] * 256 + wifiArray[7];
            if (batteryValue <= 2600) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_zero);
            } else if (batteryValue > 2600 && batteryValue <= 2818) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_one);
            } else if (batteryValue > 2818 && batteryValue <= 3018) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_two);
            } else if (batteryValue > 3018 && batteryValue <= 3120) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_three);
            } else if (batteryValue > 3120) {
                ivBatteryStatus.setImageResource(R.drawable.ic_battery_four);
            }
        }
        //处理收到的高压数值   //GC20211210
        else if (wifiArray[5] == COMMAND_VOLTAGE) {
            int hvValue = wifiArray[6] * 256 + wifiArray[7];
            //0x0ccc（3276）：32kV   0x0666：16kV   显示数值需要换算一下
            Constant.currentVoltage = hvValue / 3276.0 * 32;
            //主界面信息栏当前电压
            tvInfoHV.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
            //高压操作对话框当前电压
            handler.sendEmptyMessage(HVINDICATOR);
        }
        //添加处理高压模块反馈    //GC20211210
        else if (wifiArray[5] == COMMAND_QUERY_FEEDBACK) {
            byte temp = (byte) wifiArray[6];
            byte[] array;
            array = getBooleanArray(temp);
            //接地报警
            if (array[3] == 1) {
                handler.sendEmptyMessage(WARNING);
            }
            //高压包故障
            if (array[4] == 1) {

            }
            //电容有残压
            if (array[5] == 1) {

            }
            //工作方式故障
            if (array[6] == 1) {

            }
            //电压档位故障
            if (array[7] == 1) {

            }

        }
        //TODO 20200407 普通命令解析完毕，允许请求电量
        if (!Constant.isTesting) {
            ConnectService.canAskPower = true;
            Log.e("【请求电量时机控制】", "触发和电量命令数据处理完毕，允许请求电量。");
        }

    }

    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    private void initTDRView() {
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        tvMode.setText(getResources().getText(R.string.btn_tdr));
        //信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_tdr));
        tvWaveNext.setVisibility(View.GONE);
        tvWavePre.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvOrigin.setVisibility(View.GONE);
        tvTriggerDelay.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        tvBalancePlus.setVisibility(View.VISIBLE);  //jk20210125
        tvBalanceMin.setVisibility(View.VISIBLE);
        tvBalanceSpace.setVisibility(View.VISIBLE);
        tvBalanceText.setVisibility(View.VISIBLE);
        tvBalanceValue.setVisibility(View.VISIBLE);
    }

    private void initICMSURGEView() {
        tvMode.setText(getResources().getText(R.string.btn_icm));
        //信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_icm));
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        tvWaveNext.setVisibility(View.GONE);
        tvWavePre.setVisibility(View.GONE);
        tvBalancePlus.setVisibility(View.GONE);
        tvBalanceMin.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvPulseWidth.setVisibility(View.GONE);
        tvOrigin.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.VISIBLE);
        tvDelayText.setVisibility(View.VISIBLE);
        tvDelaySpace.setVisibility(View.VISIBLE);
    }

    private void initICMDECAYView() {
        tvMode.setText(getResources().getText(R.string.btn_icm_decay));
        //信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_icm_decay));
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        tvWaveNext.setVisibility(View.GONE);
        tvWavePre.setVisibility(View.GONE);
        tvBalancePlus.setVisibility(View.GONE);
        tvBalanceMin.setVisibility(View.GONE);
        //信息框比例前方多了空格   布局修正  //GC20200525
        tvBalanceSpace.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvTriggerDelay.setVisibility(View.GONE);
        tvPulseWidth.setVisibility(View.GONE);
        tvOrigin.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
    }

    private void initSIMView() {
        tvMode.setText(getResources().getText(R.string.btn_sim));
        //信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_sim));
        viewMoveVerticalWave.setVisibility(View.VISIBLE);
        tvWaveNext.setVisibility(View.VISIBLE);
        tvWavePre.setVisibility(View.VISIBLE);
        tvBalanceMin.setVisibility(View.GONE);
        tvBalancePlus.setVisibility(View.GONE);
        tvCompare.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.VISIBLE);
        tvDelayText.setVisibility(View.VISIBLE);
        tvPulseWidth.setVisibility(View.GONE);
        //SIM模式切换图片宽度   //20200521
        tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_selector);
        tvGainMin.setImageResource(R.drawable.bg_gain_min_s_selector);
        tvOrigin.setImageResource(R.drawable.bg_origin_s_selector);
        tvTriggerDelay.setImageResource(R.drawable.bg_trigger_delay_s_selector);
        tvCal.setImageResource(R.drawable.bg_cal_s_selector);
        tvRange.setImageResource(R.drawable.bg_range_s_selector);
        //SIM波形上翻状态初始化   //GC20200604
        tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_false);
        waveFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
        tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_false);
        waveFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
        tvFile.setImageResource(R.drawable.bg_file_s_selector);

        tvRecordsSave.setImageResource(R.drawable.bg_save_s_selector);
        tvFileRecords.setImageResource(R.drawable.bg_records_s_selector);
        tvSave.setImageResource(R.drawable.bg_save_s_selector);
        tvVopMin.setImageResource(R.drawable.bg_vop_min_s_selector);
        tvVopPlus.setImageResource(R.drawable.bg_vop_plus_s_selector);
        tvDelayMin.setImageResource(R.drawable.bg_delay_min_s_selector);
        tvDelayPlus.setImageResource(R.drawable.bg_delay_plus_s_selector);
    }

    private void initDecayView() {
        tvMode.setText(getResources().getText(R.string.btn_decay));
        //信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_decay));
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        tvWaveNext.setVisibility(View.GONE);
        tvWavePre.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvBalancePlus.setVisibility(View.GONE);
        tvBalanceMin.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvPulseWidth.setVisibility(View.GONE);
        tvOrigin.setVisibility(View.GONE);
        tvPulseWidth.setVisibility(View.GONE);
        tvTriggerDelay.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
    }

    /**
     * 计算距离  //GC20190709   //GC20191231 程序结构优化
     */

    private void calculateDistance(int cursorDistance) {
        double distance;
        int k = 1;
        int l;
        int lFault;

        //脉冲电流方式下range=6(32km)和range=7(64km)实时25M采样率，其余方式和范围实时100M采样率，此时相对其它方式采样周期扩大4倍
        if (((mode == DECAY) || mode == ICM || mode == ICM_DECAY) && (rangeState > 6)) {
            k = 4;
        }

        distance = (((double) cursorDistance * velocity) * k) / 2 * 0.01;
        if (mode == DECAY) {
            //DECAY方式距离/2
            distance = (((double) cursorDistance * velocity / 2) * k) / 2 * 0.01;
        } else if ((mode == TDR) || (mode == SIM)) {
            //250m范围距离/2  //GC20191225
            if (range == RANGE_250) {
                distance = (((double) cursorDistance * velocity / 2) * k) / 2 * 0.01;
            }
        } else if ((mode == ICM) || (mode == ICM_DECAY)) {
            //有测试线缆     //GC20200103
            //Log.e("leadCat", "leadCat" + MainActivity.leadCat);
            if ((leadLength > 0) && (catlead1 == 1)) {  //jk20201130  脉冲电流延长线不选就不计算//if (leadLength > 0) {
                //实际点数
                l = (int) (leadLength * 2000 / leadVop / 10);
                lFault = cursorDistance - l;
                distance = (((double) lFault * velocity) * k) / 2 * 0.01 + leadLength;
            }

        }

        //TODO 2019-1223-0947 显示的距离也存下来，保存波形的时候使用
        Constant.CurrentLocation = distance;
        //距离界面显示
        if (CurrentUnit == MI_UNIT) {
            tvDistance.setText(new DecimalFormat("0.00").format(distance));
        } else {
            tvDistance.setText(UnitUtils.miToFt(distance));
        }

    }

    //设置滑块参数
    private void setMoveView() {
        RectF sparkViewRectf = myChartAdapterMainWave.getDataBounds();
        viewMoveVerticalWave.setSparkViewRect(sparkViewRectf);
        //水平滑动view传递父view
        mvWave.setParentView(llHorizontalView);

    }

    /**
     * TDR单击自动测距
     */
    private void tdrAutoTest() {
        gainJudgmentTdr1();
        switch (gainState) {
            case 0:
                tvInformation.setText("");
                break;
            case 1:
                gainState = 0;
                //显示增益过大
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_high));
                return;
            case 2:
                gainState = 0;
                //显示增益过小
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_low));
                return;
            default:
                break;
        }
        tdrCurveFitting();
        tdtAutoCursor();

        //平衡调节重置
        step = 8;
        count = 6;
        //长按测试重置
        isLongClick = false;

    }

    /**
     * TDR曲线拟合程序
     */
    private void tdrCurveFitting() {
        /*以下部分是低压脉冲自动测距*/
        //判断低压脉冲波形向上还是向下
        findExtremePoint();
        Log.e("g", " /g = " + g);
        Log.e("u", " /u = " + u);
    /*   if (range == RANGE_250) {   //jk20200826
           g = 2 * g;
            u = 2 * u;
         } else {
        }*/
        double[] waveArray1 = new double[60050];
        //以高度128为零点
        for (int j = u; j < g; j++) {
            waveArray1[j] = waveArray[j] - 128;  //jk20201022 以高度128为零点
        }
        //曲线拟合部分
        double[] X = new double[1000];
        double[] Y = new double[1000];
        double[] atemp = new double[8];
        double[] b = new double[4];
        double[][] a = new double[4][4];

        for (int h = u; h < g; h++) {
            X[h - u] = h - u;
            Y[h - u] = waveArray1[h];
        }
        for (int i = 0; i < g - u; i++) {
            atemp[1] += X[i];
            atemp[2] += Math.pow(X[i], 2);
            atemp[3] += Math.pow(X[i], 3);
            atemp[4] += Math.pow(X[i], 4);
            atemp[5] += Math.pow(X[i], 5);
            atemp[6] += Math.pow(X[i], 6);
            b[0] += Y[i];
            b[1] += X[i] * Y[i];
            b[2] += Math.pow(X[i], 2) * Y[i];
            b[3] += Math.pow(X[i], 3) * Y[i];
        }

        atemp[0] = g - u;

        for (int i1 = 0; i1 < 4; i1++) {
            int k = i1;
            for (int j = 0; j < 4; j++) {
                a[i1][j] = atemp[k++];
            }
        }

        for (int k = 0; k < 3; k++) {
            int column = k;
            double mainelement = a[k][k];
            for (int i2 = k; i2 < 4; i2++) {
                 /*if (fabs(a[i2][k] > mainelement)) {
                    mainelement = fabs(a[i2][k]);
                    column = i2;
                 }*/
                if (Math.abs((a[i2][k])) > mainelement) {
                    mainelement = Math.abs((a[i2][k]));
                    column = i2;
                }
            }

            for (int j = k; j < 4; j++) {
                double atemp_1 = a[k][j];
                a[k][j] = a[column][j];
                a[column][j] = atemp_1;
            }

            double btemp = b[k];
            b[k] = b[column];
            b[column] = btemp;

            for (int i3 = k + 1; i3 < 4; i3++) {
                double Mik = a[i3][k] / a[k][k];
                for (int j = k; j < 4; j++) {
                    a[i3][j] -= Mik * a[k][j];
                }
                b[i3] -= Mik * b[k];
            }
        }

        b[3] /= a[3][3];

        for (int i = 2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < 4; j++) {
                sum += a[i][j] * b[j];
            }
            b[i] = (b[i] - sum) / a[i][i];
        }

         /*   int point = 0;
            int point1 = 0;
            int point2 = 0;
            int point3 = 0;

            int p1 = 0;
            int p2 = 0;
            int p3 = 0;
            int p4 = 0;*/
        autoLocation1 = equationSolving(b[3], b[2], b[1], b[0], -10, 10) + u;
         /*   double[] mat_sum = new double[1000];
            double[] mat_sum1 = new double[1000];

            for (int y = 0; y < g - u; y++) {
                mat_sum[y] = b[3] * y * y * y + b[2] * y * y + b[1] * y + b[0];
            }

            for (int i = 0; i < g - u - 1; i++) {
                if ((((mat_sum[i]) <= 0) && ((mat_sum[i + 1]) >= 0)) || ((mat_sum[i]) >= 0) && ((mat_sum[i + 1]) <= 0)) {
                    //Log.e("1", " /zou ");
                    p1++;
                    int z = i;
                    point = z + u + 1;
                    // Log.e("1", " /光标位置 = " + point);
                } else {
                    for (int f = 0; f < g - u - 1; f++) {
                        if ((((mat_sum[f]) <= 6) && ((mat_sum[f + 1]) >= 6)) || (((mat_sum[f]) >= 6) && ((mat_sum[f + 1]) <= 6))) {
                            // Log.e("2", " /zou ");
                            p2++;
                            int z1 = f;
                            // printf("z=%d\n", z);
                            point1 = z1 + u + 1;
                            //  Log.e("2", " /光标位置 = " + point1);
                        }
                    }
                }
            }
            //超短距离算法        //20200703
            if (p1 == 0 && p2 == 0) {
                if (u >= 4) {
                    //  Log.e("4", " /zou ");
                    int sum_s1 = 0;
                    for (int q = 5; q < 15; q++) {
                        sum_s1 = sum_s1 + waveArray[q];
                    }
                    sum_s1 = sum_s1 / 10;
                    for (int j = u; j < g; j++) {
                        waveArray1[j] = waveArray[j] - sum_s1;
                    }
                    for (int h = u; h < g; h++) {
                        X[h - u] = h - u;
                        Y[h - u] = waveArray1[h];
                    }
                    for (int i = 0; i < g - u; i++) {
                        atemp[1] += X[i];
                        atemp[2] += Math.pow(X[i], 2);
                        atemp[3] += Math.pow(X[i], 3);
                        atemp[4] += Math.pow(X[i], 4);
                        atemp[5] += Math.pow(X[i], 5);
                        atemp[6] += Math.pow(X[i], 6);
                        b[0] += Y[i];
                        b[1] += X[i] * Y[i];
                        b[2] += Math.pow(X[i], 2) * Y[i];
                        b[3] += Math.pow(X[i], 3) * Y[i];
                    }

                    atemp[0] = g - u;

                    for (int i1 = 0; i1 < 4; i1++) {
                        int k = i1;
                        for (int j = 0; j < 4; j++) {
                            a[i1][j] = atemp[k++];
                        }
                    }

                    for (int k = 0; k < 3; k++) {
                        int column = k;
                        double mainelement = a[k][k];
                        for (int i2 = k; i2 < 4; i2++) {
                            if (Math.abs((a[i2][k])) > mainelement) {
                                mainelement = Math.abs((a[i2][k]));
                                column = i2;
                            }
                        }

                        for (int j = k; j < 4; j++) {
                            double atemp_1 = a[k][j];
                            a[k][j] = a[column][j];
                            a[column][j] = atemp_1;
                        }

                        double btemp = b[k];
                        b[k] = b[column];
                        b[column] = btemp;

                        for (int i3 = k + 1; i3 < 4; i3++) {
                            double Mik = a[i3][k] / a[k][k];
                            for (int j = k; j < 4; j++) {
                                a[i3][j] -= Mik * a[k][j];
                            }
                            b[i3] -= Mik * b[k];
                        }
                    }

                    b[3] /= a[3][3];

                    for (int i = 2; i >= 0; i--) {
                        double sum = 0;
                        for (int j = i + 1; j < 4; j++) {
                            sum += a[i][j] * b[j];
                        }
                        b[i] = (b[i] - sum) / a[i][i];
                    }

                    for (int y = 0; y < g - u; y++) {
                        mat_sum[y] = b[3] * y * y * y + b[2] * y * y + b[1] * y + b[0];
                    }

                    for (int i = 0; i < g - u - 1; i++) {
                        if ((((mat_sum[i]) <= 0) && ((mat_sum[i + 1]) >= 0)) || ((mat_sum[i]) >= 0) && ((mat_sum[i + 1]) <= 0)) {
                            p4++;
                            int z = i;
                            point3 = z + u + 1;
                            // Log.e("1", " /光标位置 = " + point3);
                        }
                    }
                }
                if (p4 == 0) {
                    //  Log.e("3", " /zou ");
                    int sum_s = 0;
                    for (int q = 1; q < 10; q++) {
                        sum_s = sum_s + waveArray[q];
                    }
                    sum_s = sum_s / 9;
                    // Log.e("sum", " / = " + sum_s);

                    for (int j = 0; j < u + 7; j++) {
                        waveArray1[j] = waveArray[j] - sum_s; // waveArray1[j] = waveArray1[j] - 128;       //tdr[j] = tdr[j] - sum;   //以128为零点
                        //为了显示方便，opencv零点在左上角
                        //printf("tdr=%d\n", tdr[j]);
                    }

                    for (int h = 0; h < u + 7; h++) {
                        X[h] = h;
                        Y[h] = waveArray1[h];
                    }

                    for (int i = 0; i < u + 7; i++) {
                        atemp[1] += X[i];
                        atemp[2] += Math.pow(X[i], 2);
                        atemp[3] += Math.pow(X[i], 3);
                        atemp[4] += Math.pow(X[i], 4);
                        atemp[5] += Math.pow(X[i], 5);
                        atemp[6] += Math.pow(X[i], 6);
                        b[0] += Y[i];
                        b[1] += X[i] * Y[i];
                        b[2] += Math.pow(X[i], 2) * Y[i];
                        b[3] += Math.pow(X[i], 3) * Y[i];
                    }

                    atemp[0] = u + 7;

                    for (int i = 0; i < 4; i++) {
                        int k = i;
                        for (int j = 0; j < 4; j++) {
                            a[i][j] = atemp[k++];
                        }
                    }

                    for (int k = 0; k < 3; k++) {
                        int column = k;
                        double mainelement = a[k][k];
                        for (int i = k; i < 4; i++) {
                            if (Math.abs(a[i][k]) > mainelement) {
                                mainelement = Math.abs(a[i][k]);
                                column = i;
                            }
                        }

                        for (int j = k; j < 4; j++) {
                            double atemp2 = a[k][j];
                            a[k][j] = a[column][j];
                            a[column][j] = atemp2;
                        }

                        double btemp = b[k];
                        b[k] = b[column];
                        b[column] = btemp;

                        for (int i = k + 1; i < 4; i++) {
                            double Mik = a[i][k] / a[k][k];
                            for (int j = k; j < 4; j++) {
                                a[i][j] -= Mik * a[k][j];
                            }
                            b[i] -= Mik * b[k];
                        }
                    }

                    b[3] /= a[3][3];

                    for (int i = 2; i >= 0; i--) {
                        double sum = 0;
                        for (int j = i + 1; j < 4; j++) {
                            sum += a[i][j] * b[j];
                        }
                        b[i] = (b[i] - sum) / a[i][i];
                    }

                    for (int y = 0; y < u + 7; y++) {
                        mat_sum1[y] = b[3] * y * y * y + b[2] * y * y + b[1] * y + b[0];
                    }
                    for (int i = 0; i < u + 7 - 1; i++) {
                        if ((((mat_sum1[i]) <= 0) && ((mat_sum1[i + 1]) >= 0)) || (((mat_sum1[i]) >= 0) && ((mat_sum1[i + 1]) <= 0))) {
                            p3++;
                            int z = i;
                            point2 = z + 1;
                            //  Log.e("3", " /光标位置 = " + point2);
                        }
                    }
                }
            }

            if (p1 > 0) {
                autoLocation1 = point;
            } else if (p2 > 0) {
                autoLocation1 = point1;
            } else if (p3 > 0) {
                autoLocation1 = point2;
            } else {
                autoLocation1 = point3;
            }
        Log.e("autoLocation11", " /光标位置 = " + autoLocation1);*/
        if (autoLocation1 <= tdrPointuse[rangeState] + 3)  //jk20200923
        {
            autoLocation1 = 0;
        }
        Log.e("autoLocation1", " /光标位置 = " + autoLocation1);

        //jk20210305 对长距离自动测距修改，解决低压脉冲下长距离因取点问题与实际距离不符的现象
        //取曲线拟合光标前5个点
        int Ln;
        int waveArraySum = 0;
        int waveArraySum1 = 0;

        if (autoLocation1 >= tdrPoint[rangeState]) {
            for (Ln = autoLocation1; Ln > autoLocation1 - tdrPoint[rangeState]; Ln--) {
                waveArraySum = waveArray[Ln] + waveArraySum;
            }

            waveArraySum = waveArraySum / tdrPoint[rangeState];
            //Log.e("waveArraySum", " /waveArraySum = " + waveArraySum);

            waveArraySum = Math.abs(waveArraySum - 128);
            Log.e("waveArraySum", " /waveArraySum = " + waveArraySum);
            Log.e("pointflag", " /pointflag = " + pointflag);
            if (waveArraySum >= 10) {
                if (pointflag == 1) {
                    point_s1();
                } else {
                    point_x1();
                }
                if (point_s_pos <= pulsetdrRemove1[rangeState]) {
                    autoLocation = 0;
                }
            } else {
                autoLocation = autoLocation1;
            }

        } else {
            if (autoLocation1 != 0) {
         /*   for (Ln = autoLocation1; Ln > 0; Ln--) {
                waveArraySum1 = waveArray[Ln] + waveArraySum1;
            }
            waveArraySum1 =  waveArraySum1 / autoLocation1;
            Log.e("waveArraySum", " /waveArraySum = " + waveArraySum);

            waveArraySum1 =  Math.abs( waveArraySum1 - 128);

            if (waveArraySum1 >= 5){
                autoLocation = 0;
            }else {*/
                autoLocation = autoLocation1;

            } else {
                autoLocation = 0;
            }
        }
        Log.e("autoLocation", " /光标位置 = " + autoLocation);
    }

    /*
     ** 从零点到u的极值点查找  分极大值和极小值  分两部分写
     */
    int point_s_pos;

    private void point_s1() {
        //判断极值位置
        int j = 3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        while ((j >= 3) && (j < u)) {
            if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
//                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }

        if (maxNum == 0) {
            Log.e("tdr", "没有极大值");
            // tvInformation.setVisibility(View.VISIBLE);
            //  tvInformation.setText(getResources().getString(R.string.testAgain));
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }
            point_s_pos = maxPos;

            // Log.e("1", " /最大极大值位置 = " + maxPos);
        }


    }

    private void point_x1() {
        int i1 = 5;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos = minDataPos1[0];

        while ((i1 >= 5) && (i1 < u)) {
            if ((waveArray[i1] <= waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                            }
                        }
                    }
                }
            }
            i1++;
        }

        if (minNum1 > 0) {
            int min1 = minData1[0];
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minData1[k1] <= min1) {
                    min1 = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }

        }

        point_s_pos = minPos;


    }

    /**
     * TDR光标自动定位
     */
    private void tdtAutoCursor() {
        //实光标固定在零点
        zero = 0;   //实光标自动定位时固定在零点
        if (range == RANGE_250) {
            pointDistance = 2 * autoLocation;
        } else {
            pointDistance = autoLocation;
        }
        if (zero >= (currentMoverPosition510 * dataLength / 510) && zero <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            mainWave.setScrubLineReal(0);
        } else {
            mainWave.setScrubLineRealDisappear();
        }
        //重新定位虚光标
      /*  if (autoLocation >= (currentMoverPosition510 * dataLength / 510) && autoLocation <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionVirtual = (autoLocation - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineVirtual(positionVirtual);
        } else {
            mainWave.setScrubLineVirtualDisappear();
        }*/  //jk20200826
        if (pointDistance >= (currentMoverPosition510 * dataLength / 510) && pointDistance <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionVirtual = (pointDistance - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineVirtual(positionVirtual);
        } else {
            mainWave.setScrubLineVirtualDisappear();
        }
        calculateDistance(Math.abs(pointDistance - zero));
    }

    /**
     * TDR长按测试按键，初始化到500m范围，寻找合适的平衡、范围、增益后再自动定位  //GC20200916
     * 步骤1：初始化到500m范围和平衡中间档位，获取波形数据用作后续运算判断
     */
    private void longTestInit() {
        //初始化到500m范围
        if (range == RANGE_500) {
            gain = 13;
            setGain(gain);
        } else {
            setRange(0x11);
            setGain(gain);
            if (!hasSavedPulseWidth) {
                pulseWidth = 40;
                etPulseWidth.setText(String.valueOf(40));
            }
            setPulseWidth(pulseWidth);
        }
        //初始化到平衡中间档位（0-15）
        balance = 8;
        setBalance(balance);
        longTestInit = true;
        handler.postDelayed(ModeActivity.this::clickTest, 100);
        llAdjust.setVisibility(View.GONE); //jk20210125

        /*rangeFragment.btn250m.setEnabled(true);
        rangeFragment.btn500m.setEnabled(false);
        rangeFragment.btn1km.setEnabled(true);
        rangeFragment.btn2km.setEnabled(true);
        rangeFragment. btn4km.setEnabled(true);
        rangeFragment.btn8km.setEnabled(true);
        rangeFragment.btn16km.setEnabled(true);
        rangeFragment.btn32km.setEnabled(true);
        rangeFragment.btn64km.setEnabled(true);*/  //jk20210128
        rangeFragment.btn250m.setEnabled(true);
        rangeFragment.btn500m.setEnabled(false);
        rangeFragment.btn1km.setEnabled(true);
        rangeFragment.btn2km.setEnabled(true);
        rangeFragment.btn4km.setEnabled(true);
        rangeFragment.btn8km.setEnabled(true);
        rangeFragment.btn16km.setEnabled(true);
        rangeFragment.btn32km.setEnabled(true);
        rangeFragment.btn64km.setEnabled(true);
        rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
        rangeFragment.btn500m.setImageResource(R.drawable.bg_500m1); //jk20210129
        rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
        rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
        rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
        rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
        rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
        rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
        rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
    }

    /**
     * 低压脉冲长按自动测试  //GC20200917
     */
    private void tdrAutoTestLong() {
        //步骤2：寻找合适的平衡档位，对平衡判断6次
        while ((count > 0)) {
            count--;
            Log.e("tdr", "count" + count);
            step = step / 2;
            if (step <= 1) {
                step = 1;
            }
            //寻找发射脉冲的极大、极小值，用作判断平衡的状态
            findStartExtremePoint();
            balanceAutoTdr();
            switch (balanceState) {
                case 0:
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 1:
                    //波形波头偏下，平衡需要减小，减小后波头上升
                    balanceState = 0;
                    balance = balance - step;
                    if (balance < 0) {
                        balance = 0;
                    }
                    setBalance(balance);
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 2:
                    balanceState = 0;
                    balance = balance + step;
                    if (balance > 15) {
                        balance = 15;
                    }
                    setBalance(balance);
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                default:
                    break;
            }
        }
        //步骤3：寻找合适的范围
        selectRange();
        //步骤4：寻找合适的增益
        gainJudgmentTdr();
        switch (gainState) {
            case 0:
                //增益调整结束，给出最终结果
                tvInformation.setText("");
                break;
            case 1:
                tvInformation.setText("");
                gainState = 0;
                gain = gain - 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            case 2:
                tvInformation.setText("");
                gainState = 0;
                gain = gain + 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            default:
                break;
        }
        //给出自动测距结果
        tdrCurveFitting();
        tdtAutoCursor();

        //平衡调整结束 重置参数
        step = 8;
        count = 6;
        isLongClick = false;  //长按测试重置
    }

    /**
     * 步骤2：寻找合适的平衡档位    //GC20200916
     */
    private void selectBalance() {
        //对平衡判断6次
        while ((count > 0)) {
            count--;
//            Log.e("tdr", "count" + count);
            step = step / 2;
            if (step <= 1) {
                step = 1;
            }
            //寻找发射脉冲的极大、极小值，用作判断平衡的状态
            findStartExtremePoint();
            balanceAutoTdr();
            switch (balanceState) {
                case 0:
                    break;
                case 1:
                    //波形波头偏下，平衡需要减小，减小后波头上升
                    balanceState = 0;
                    balance = balance - step;
                    if (balance < 0) {
                        balance = 0;
                    }
                    setBalance(balance);
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 2:
                    balanceState = 0;
                    balance = balance + step;
                    if (balance > 15) {
                        balance = 15;
                    }
                    setBalance(balance);
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                default:
                    break;
            }
        }
        //平衡调整结束 重置参数
        step = 8;
        count = 6;
        balanceIsReady = true;
        handler.postDelayed(ModeActivity.this::clickTest, 100);
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    /**
     * 步骤3：寻找合适的范围
     */
    int rangeCount = 1;

    private void selectRange() {
        int i;
        int max1 = 0;
        int sub1;
        //计算波形有效数据的极值   //jk20200904 更改起始判断位置，
        for (i = pulselongtdrRemove[rangeState]; i < dataMax - removeTdrSim[rangeState] - 30; i++) {  //jk20200917
            //sub1 = waveArray[i] - 133;
            sub1 = waveArray[i] - Median_value;
            if (Math.abs(sub1) > max1) {
                max1 = Math.abs(sub1);
            }
        }

        //jk20210309 更改范围切换条件，采用找极值点的方式进行查找 建立单独程序
        findExtremePointRange();

        //找到的最大极值小于5，认为没有全长反射，就增大一个范围继续测试
//        if(max1 <= 11) {
        if ((rangeAdjust == 1) && (max1 <= 9)) {
            rangeCount++;
            Log.e("tdr", "rangeCount" + rangeCount);
            switch (rangeCount) {
                case 2:
                    setRange(0x22);
                    setGain(gain);
                    //selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 80;
                            setPulseWidth(80);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(80));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(false);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1km1); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 3:
                    setRange(0x33);
                    setGain(gain);
                    //selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 160;
                            setPulseWidth(160);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(160));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(false);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2km); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 4:
                    setRange(0x44);
                    setGain(gain);
                    // selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 320;
                            setPulseWidth(320);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(320));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(false);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4km); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 5:
                    setRange(0x55);
                    setGain(gain);
                    // selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 640;
                            setPulseWidth(640);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(640));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(false);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8km); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 6:
                    setRange(0x66);
                    setGain(gain);
                    //selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 1280;
                            setPulseWidth(1280);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(1280));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(false);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16km); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 7:
                    setRange(0x77);
                    setGain(gain);
                    //selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 2560;
                            setPulseWidth(2560);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(2560));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(false);
                    rangeFragment.btn64km.setEnabled(true);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32km); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                    return;
                case 8:
                    setRange(0x88);
                    setGain(gain);
                    //selectGain1();
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 5120;
                            setPulseWidth(5120);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(5120));
                    }
                    //G?
                    rangeCount = 1;
                    rangeIsReady = true;
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    rangeFragment.btn250m.setEnabled(true);
                    rangeFragment.btn500m.setEnabled(true);
                    rangeFragment.btn1km.setEnabled(true);
                    rangeFragment.btn2km.setEnabled(true);
                    rangeFragment.btn4km.setEnabled(true);
                    rangeFragment.btn8km.setEnabled(true);
                    rangeFragment.btn16km.setEnabled(true);
                    rangeFragment.btn32km.setEnabled(true);
                    rangeFragment.btn64km.setEnabled(false);
                    rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                    rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                    rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                    rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                    rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                    rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                    rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                    rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                    rangeFragment.btn64km.setImageResource(R.drawable.bg_64km); //jk20210129
                    break;
                default:
                    break;
            }

        }
        //范围调整结束
        rangeCount = 1;
        rangeIsReady = true;
        handler.postDelayed(ModeActivity.this::clickTest, 100);
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    /*
       根据极值点来切换范围  //jk20210309  找出当前范围下的极大值与极小值点，根据极大值和极小值位置确定
     */
    private void findExtremePointRange() {
        //判断极值位置
        int j = pulsetdrRemove1[rangeState] + 3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        int maxjudge = 0;
        int minjudge = 0;
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        // while ( (j >= pulsetdrRemove[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState]) ) {
        while ((j >= pulsetdrRemove1[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState])) {
            if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
// Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }

        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxDataPos[k] > 2 * pulsetdrRemove11[rangeState]) {
                    maxjudge = 1;
                    //   Log.e("tdr", "曲线拟合没有极大值t");
                    //   Log.e(" maxDataPos[k]", " maxDataPos[k]"+ maxDataPos[k]);
                }
            }

        }


        int i1 = pulsetdrRemove1[rangeState] + 5;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos = minDataPos1[0];
        int min1 = waveArray[0];

        // while ( (i1 >= pulsetdrRemove[rangeState] ) && (i1 < dataMax - removeTdrSim[rangeState]) ) {   //jk20200714
        while ((i1 >= pulsetdrRemove1[rangeState] + 5) && (i1 < dataMax - removeTdrSim[rangeState])) {   //jk20200714
            if ((waveArray[i1] <= waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] < waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] < waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] < waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] < waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                                // Log.e("ceshi", " /极小值位置 = " + i1);
                            }
                        }
                    }
                }
            }
            i1++;
        }

        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minDataPos1[k1] > 2 * pulsetdrRemove11[rangeState]) {
                    minjudge = 1;
                    // Log.e("tdr", "曲线拟合没有极小值t");
                    //   Log.e("minDataPos1[k1]", "minDataPos1[k1]"+ minDataPos1[k1]);
                }
            }
        }

        //找到的极值点都在设置的范围内，认为后面是直线，没有反射，切换范围查看
        if (minjudge == 0 && maxjudge == 0) {
            rangeAdjust = 1;
        }

        //特殊情况，测试线很短，波形都在前面显示 可能极大值和极小值一个在范围里面，一个在范围外面。 同时范围不够的也会出现这个状况

    }
    /*private void  findExtremePointRange(){
        //判断极值位置
        int j = pulsetdrRemove1[rangeState] + 3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        int maxjudge = 0;
        int minjudge = 0;
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        // while ( (j >= pulsetdrRemove[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState]) ) {
        while ( (j >= pulsetdrRemove1[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState]) ) {
            if ( (waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1]) ) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
// Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }

        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }

        }



        int i1 = pulsetdrRemove1[rangeState]+5 ;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos=minDataPos1[0];
        int min1 = waveArray[0];

        // while ( (i1 >= pulsetdrRemove[rangeState] ) && (i1 < dataMax - removeTdrSim[rangeState]) ) {   //jk20200714
        while ( (i1 >= pulsetdrRemove1[rangeState]+5 ) && (i1 < dataMax - removeTdrSim[rangeState]) ) {   //jk20200714
            if ((waveArray[i1] <= waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                                // Log.e("ceshi", " /极小值位置 = " + i1);
                            }
                        }
                    }
                }
            }
            i1++;
        }

        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minData1[k1] <= min1) {
                    min1 = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }
        }

        //找到的极值点都在设置的范围内，认为后面是直线，没有反射，切换范围查看
       if((minPos <= pulsetdrRemove11[rangeState]) && (maxPos <= 2*pulsetdrRemove11[rangeState])){
           rangeAdjust = 1;
       }
       //特殊情况，测试线很短，波形都在前面显示 可能极大值和极小值一个在范围里面，一个在范围外面。 同时范围不够的也会出现这个状况

    }*/


    /**
     * 步骤4：寻找合适的增益，给出自动测距结果
     */
    private void selectGain() {
        gainJudgmentTdr();
        switch (gainState) {
            case 0:
                //增益调整结束，给出最终结果
                tvInformation.setText("");
                break;
            case 1:
                tvInformation.setText("");
                gainState = 0;
                gain = gain - 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            case 2:
                tvInformation.setText("");
                gainState = 0;
                gain = gain + 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            default:
                break;
        }
        tdrCurveFitting();
        tdtAutoCursor();

        //长按测试重置
        isLongClick = false;
        longTestInit = false;
        balanceIsReady = false;
        rangeIsReady = false;
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    private void selectGain1() {
        gainJudgmentTdr();
        switch (gainState) {
            case 0:
                //增益调整结束，给出最终结果
                tvInformation.setText("");
                break;
            case 1:
                tvInformation.setText("");
                gainState = 0;
                gain = gain - 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            case 2:
                tvInformation.setText("");
                gainState = 0;
                gain = gain + 1;
                setGain(gain);
                handler.postDelayed(ModeActivity.this::clickTest, 100);
                return;
            default:
                break;
        }

        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    private void selectGain2() {
        tdrCurveFitting();
        tdtAutoCursor();

        //长按测试重置
        isLongClick = false;
        longTestInit = false;
        balanceIsReady = false;
        rangeIsReady = false;
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    /**
     * 寻找发射脉冲的极大、极小值，用作判断平衡切换
     */
    int b_pos = 0;
    int b1_pos = 0;
    int b2_pos = 0;

    /* private void findStartExtremePoint(){
         //判断极值位置
         int a;
         int b;
         //int j = 34;
         int j = 3;
         int maxNum = 0;
         int[] maxData = new int[65560];
         int[] maxDataPos = new int[65560];
         int max = maxData[0];
         int maxPos = maxDataPos[0];
         //寻找极大值
         //while ( (j >= 34) && (j < dataMax - removeTdrSim[rangeState]) ) {
         while ( (j >= 3) && (j < 100) ) {
             if ( (waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1]) ) {
                 if (waveArray[j - 1] >= waveArray[j - 2]) {
                     if (waveArray[j - 2] > waveArray[j - 3]) {
                         maxData[maxNum] = waveArray[j];
                         maxDataPos[maxNum] = j;
 //                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                         maxNum++;
                     }
                 }
             }
             j++;
         }
         if (maxNum == 0) {
             Log.e("tdr", "发射没有极大值");
 //            tvInformation.setVisibility(View.VISIBLE);
 //            tvInformation.setText(getResources().getString(R.string.testAgain));
         }else {
             for (int k = 0; k < maxNum; k++) {
                 if (maxData[k] >= max) {
                     max = maxData[k];
                     maxPos = maxDataPos[k];
                 }
             }

         }
         // a = Math.abs(max - 132);
         a = Math.abs(max - Median_value); //jk20210520
         b1_pos = maxPos;

         int i1 = 34 ;
         int minNum1 = 0;
         int[] minData1 = new int[65560];
         int[] minDataPos1 = new int[65560];
         int minPos=minDataPos1[0];
         int min1 = waveArray[0];
         //掐头去尾找极小值
         while ( (i1 >= 5 ) && (i1 < 100) ) {   //jk20200714
             if ((waveArray[i1] < waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                 if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                     if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                         if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                             if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                 minData1[minNum1] = waveArray[i1];
                                 minDataPos1[minNum1] = i1;
                                 minNum1++;
                                 // Log.e("ceshi", " /极小值位置 = " + i1);
                             }
                         }
                     }
                 }
             }
             i1++;
         }
         if (minNum1 > 0) {
             for (int k1 = 0; k1 < minNum1; k1++) {
                 if (minData1[k1] <= min1) {
                     min1 = minData1[k1];
                     minPos = minDataPos1[k1];
                 }
             }
         }
         // b = Math.abs(132 - min1);
         b = Math.abs(Median_value - min1); //jk20210520
         b2_pos = minPos;
         // Log.e("a", " /波形 " +a);
         //Log.e("b", " /波形 " +b);
         // Log.e("min1", " /zhi " +min1);
         // Log.e("minpos", " /zhi " + minPos);
         if(a < b && min1 <= 126 ){       //jk20200714
             b_pos = b2_pos;
             //  Log.e("1", " /波形向下 " );
         }else{
             b_pos = b1_pos;
             //  Log.e("2", " /波形向上 " );
         }

     }*/
    private void findStartExtremePoint() {   //jk20210830balance
        //判断极值位置
        int a;
        int b;
        //int j = 34;
        int j = 5;  //jk20210304
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos1 = maxDataPos[0];
        //寻找极大值（去除发射脉冲和末尾数据）
        while ((j >= 3) && (j < dataMax - removeTdrSim[rangeState])) {   //20210304 改变j的值
            if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
//                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }
        if (maxNum == 0) {
            Log.e("tdr", "发射没有极大值");
//            tvInformation.setVisibility(View.VISIBLE);
//            tvInformation.setText(getResources().getString(R.string.testAgain));
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos1 = maxDataPos[k];
                }
            }

        }
        a = Math.abs(max - 128);
        b1_pos = maxPos1;

        //int i1 = 34 ;
        int i1 = 5; //jk20210304
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos1 = minDataPos1[0];
        int min1 = waveArray[0];
        //掐头去尾找极小值
        while ((i1 >= 5) && (i1 < dataMax - removeTdrSim[rangeState])) {   //jk20210304
            if ((waveArray[i1] < waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                                // Log.e("ceshi", " /极小值位置 = " + i1);
                            }
                        }
                    }
                }
            }
            i1++;
        }
        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minData1[k1] <= min1) {
                    min1 = minData1[k1];
                    minPos1 = minDataPos1[k1];
                }
            }
        }
        b = Math.abs(128 - min1);
        b2_pos = minPos1;
        // Log.e("a", " /波形 " +a);
        //Log.e("b", " /波形 " +b);
        // Log.e("maxPos1", " /zhi " +maxPos1);
        // Log.e("minpos", " /zhi " + minPos1);
        if (a < b && min1 <= 126) {       //jk20200714
            b_pos = b2_pos;
            //  Log.e("1", " /波形向下 " );
        } else {
            b_pos = b1_pos;
            //  Log.e("2", " /波形向上 " );
        }

    }

    /**
     * 低压脉冲方式平衡自动调整
     */
    //int sum_num;
    void balanceAutoTdr() {
        int temp1 = 0;
        int temp2 = 0;
        int j;
        Log.e("b_pos", " /波形 " + b_pos);
        if (b_pos <= 50) {
            if (b_pos >= 21) {
                j = b_pos - 21;
            } else {
                j = 0;
            }
        } else {
            j = 34;
        }

       /* for(int k = 54; k < 60; k++){
            sum_num = sum_num + waveArray[k];
        }
        sum_num = sum_num / 6;*/

        for (int i = 0; i <= j; i++) {
            if (waveArray[i] < Median_value) {
                temp1 = temp1 + (Median_value - waveArray[i]);
            } else {
                temp2 = temp2 + (waveArray[i] - Median_value);
            }
        }
        Log.e("j", " /j " + j);
        Log.e("temp1", " /temp1 " + temp1);
        Log.e("temp2", " /temp2 " + temp2);

        if ((temp1 > temp2) && ((temp1 - temp2) > 5)) {
            balanceState = 1;
            return;
        }
        /* 不及波形上凸 */
        if ((temp2 > temp1) && ((temp2 - temp1) > 5)) {
            balanceState = 2;
        }

    }

    /**
     * 低压脉冲方式增益自动判断  //jk20200711
     */
    private void gainJudgmentTdr() {
        int i;
        int max = 0;
        int sub;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            sub = waveArray[i] - Median_value;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 55) {//if (max <= 45) {     //jk20200830
            gainState = 2;
            return;
        }
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            if ((waveArray[i] > 242) || (waveArray[i] < 20)) {
                //判断增益过大
                gainState = 1;
                return;
            }
        }

    }

    private void gainJudgmentTdr1() {
        int i;
        int max = 0;
        int sub;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            sub = waveArray[i] - Median_value;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 25) {//if (max <= 45) {
            gainState = 2;
            return;
        }
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            if ((waveArray[i] > 242) || (waveArray[i] < 20)) {
                //判断增益过大
                gainState = 1;
                return;
            }
        }

    }

    /**
     * 寻找极值点，判断向上向下   //jk20200714
     */
    private void findExtremePoint() {
        //判断极值位置
        int a;
        int b;
        int t1;
        // int j = pulsetdrRemove[rangeState] + 3;
        int j = pulsetdrRemove1[rangeState] + 3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        // while ( (j >= pulsetdrRemove[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState]) ) {
        while ((j >= pulsetdrRemove1[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState])) {
            if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] >= waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
//                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }

        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
            //tvInformation.setVisibility(View.VISIBLE);
            // tvInformation.setText(getResources().getString(R.string.testAgain));
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }

        }
        a = Math.abs(max - Median_value);

        int t2;
        //int i1 = pulsetdrRemove[rangeState] ;
        int i1 = pulsetdrRemove1[rangeState] + 5;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos = minDataPos1[0];
        int min1 = waveArray[0];

        // while ( (i1 >= pulsetdrRemove[rangeState] ) && (i1 < dataMax - removeTdrSim[rangeState]) ) {   //jk20200714
        while ((i1 >= pulsetdrRemove1[rangeState] + 5) && (i1 < dataMax - removeTdrSim[rangeState])) {   //jk20200714
            if ((waveArray[i1] < waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                                // Log.e("ceshi", " /极小值位置 = " + i1);
                            }
                        }
                    }
                }
            }
            i1++;
        }

        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minData1[k1] <= min1) {
                    min1 = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }
        }

        b = Math.abs(Median_value - min1);
        Log.e("a", " /波形 " + a);
        Log.e("b", " /波形 " + b);
        Log.e("min1", " /zhi " + min1);
        Log.e("max", " /zhi " + max);
        Log.e("minPos", " /zhi " + minPos);
        Log.e("maxPos", " /zhi " + maxPos);

        //jk20210308 修改去除波头点数，找到最大极值点时判断点数位置，然后找次极大值位置

        //排除波头对波形判断的影响

      /*  if((minPos >= pulsetdrRemove11[rangeState]) && (maxPos >= pulsetdrRemove11[rangeState])) {
            if (a < b) {  //if(a<=b && min1 <=120 ){       //jk20200714 如果最小值大于125并且向上的极值比向下的小，会提示增益过小
                point_x();
                // b_pos=b2_pos;
                //  Log.e("1", " /波形向下 " );
            } else {
                point_s();
                // b_pos=b1_pos;
                //  Log.e("2", " /波形向上 " );
            }
        }

        if((minPos < pulsetdrRemove11[rangeState]) && (maxPos < pulsetdrRemove11[rangeState])){
            if (a < b) {
                point_x();
            } else {
                point_s();
            }
        }*/

        //优先排除波头干扰，如果最大最小值都在判断的范围内，就是波头的极值比故障的大

        //查找第二大极值点
        int secondMax = 0;
        int secondmaxPos = 0;
        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
        } else {
            for (int k = 0; k < maxNum; k++) {
                if ((maxData[k] < max) && (maxData[k] >= secondMax)) {
                    secondMax = maxData[k];
                    secondmaxPos = maxDataPos[k];
                }
            }
        }

        int secondMin = 128;
        int secondminPos = 0;
        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if ((minData1[k1] > min1) && (minData1[k1] <= secondMin)) {
                    secondMin = minData1[k1];
                    secondminPos = minDataPos1[k1];
                }
            }
        }

        secondMAx = secondMax;
        secondMIn = secondMin;
        secondMAxPos = secondmaxPos;
        secondMInPos = secondminPos;
        //考虑实际测距中可能出现极大值和极小值是发射脉冲的现象，此时判断波形向上向下程序需要加判断  //jk20210310


       /*int flaggo = 0;
        if((minPos < pulsetdrRemove11[rangeState]) && (maxPos > 2*pulsetdrRemove11[rangeState])){
            point_s();
            flaggo = 1;
        }

        if(flaggo == 0) {
            if ((minPos > 2 * pulsetdrRemove11[rangeState]) && (maxPos < pulsetdrRemove11[rangeState])) {
                //特殊情况讨论，接上低压脉冲线开路，极大值在靠前处，极小值在后边
                if ((a - b) > 28) {
                    point_s();
                } else {
                    point_x();
                }
            } else {
                if (a < b) {
                    point_x();
                } else {
                    point_s();
                }
            }
        }*/
        int flaggo = 0;
        if ((minPos < pulsetdrRemove11[rangeState]) && (maxPos > 2 * pulsetdrRemove11[rangeState]) && (max >= Median_value + 8)) {
            point_s();
            flaggo = 1;
        }

        if (flaggo == 0) {
            if ((minPos > 2 * pulsetdrRemove11[rangeState]) && (maxPos < pulsetdrRemove11[rangeState]) && (min1 <= Median_value - 8)) {
                //对不接电缆时低压脉冲的修改。在不接电缆时，最小极值点在后面
                point_x();
            } else {
                if (a < b) {
                    point_x();
                } else {
                    point_s();
                }
            }

        }

    }

    /**
     * 低压脉冲波形向上  //jk20200711
     */
    int pointflag = 0;

    private void point_s() {
        //判断极值位置
        int t1;
        int j = pulsetdrRemove[rangeState] + 3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        while ((j >= pulsetdrRemove[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState])) {
            if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
//                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }

        if (maxNum == 0) {
            Log.e("tdr", "没有极大值");
            // tvInformation.setVisibility(View.VISIBLE);
            //  tvInformation.setText(getResources().getString(R.string.testAgain));
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }
            // Log.e("1", " /最大极大值位置 = " + maxPos);
        }

        g = maxPos;
        pointflag = 1;
        //b1_pos= maxPos;
        Log.e("2", " /最大极大值位置 = " + g);


        t1 = maxPos;//min1Pos;
        //脉冲起始点
        /*while (t1 > 1) {
            if (waveArray[t1] >= waveArray[t1-1]) {
                t1 = t1 - 1 ;
            } else {
                break;
            }
        }*/
        while (t1 > 1) {
            if (waveArray[t1] >= waveArray[t1 - 1]) {
                // if (waveArray[t1] != waveArray[t1-2]) {
                if (Math.abs(waveArray[t1] - Median_value) > 4) {
                    t1 = t1 - 1;
                    //
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        u = t1;
        Log.e("3", " /起始点 = " + u);

    }

    /**
     * 低压脉冲波形向下  //jk20200711
     */
    private void point_x() {
        int t2;
        int i1 = pulsetdrRemove1[rangeState] + 5;//  int i1 = pulsetdrRemove[rangeState] + 5;   //jk20200714 取后5个
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos = minDataPos1[0];

        while ((i1 >= pulsetdrRemove1[rangeState] + 5) && (i1 < dataMax - removeTdrSim[rangeState])) {   //jk20200714  取后5个
            if ((waveArray[i1] <= waveArray[i1 - 1]) && (waveArray[i1] <= waveArray[i1 + 1])) {
                if (waveArray[i1 - 1] <= waveArray[i1 - 2]) {
                    if (waveArray[i1 - 2] <= waveArray[i1 - 3]) {
                        if (waveArray[i1 - 3] <= waveArray[i1 - 4]) {
                            if (waveArray[i1 - 4] <= waveArray[i1 - 5]) {
                                minData1[minNum1] = waveArray[i1];
                                minDataPos1[minNum1] = i1;
                                minNum1++;
                                //Log.e("ceshi", " /极小值位置 = " + i1);
                            }
                        }
                    }
                }
            }
            i1++;
        }

        if (minNum1 > 0) {
            int min1 = minData1[0];
            for (int k1 = 0; k1 < minNum1; k1++) {
                if (minData1[k1] <= min1) {
                    min1 = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }

        }
        Log.e("minPos", " /g " + minPos);
        t2 = minPos;
        //  b2_pos=minPos;
        g = minPos;
        pointflag = 2;      // Log.e("2", " /最小极小值位置 = " + g);
        //脉冲起始点
        /*while (t2 > 1) {
            if (waveArray[t2] <= waveArray[t2-1]) {
                t2 = t2 - 1 ;
            } else {
                break;
            }
        }*/
        while (t2 > 1) {
            //if (waveArray[t2] <= waveArray[t2-1]) {
            if (waveArray[t2] <= waveArray[t2 - 1]) {
                // if (waveArray[t2] != waveArray[t2-2]) {
                if (Math.abs(waveArray[t2] - Median_value) > 4) {
                    t2 = t2 - 1;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        u = t2;
        //   Log.e("3", " /负脉冲起始点 = " + u);

    }

    /**
     * 脉冲电流故障自动计算过程  //GC20190708
     */
    private void icmAutoTest() {
        //1.判断增益是否合适
        gainJudgment();
        switch (gainState) {
            case 0:
                tvInformation.setText("");
                break;
            case 1:
                gainState = 0;
                //显示增益过大
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_high));
                return;
            case 2:
                gainState = 0;
                //显示增益过小
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_low));
                return;
            default:
                break;
        }
        //软件滤波
        softwareFilter();
        //积分
        integral();
        //2.击穿放电判断
        breakdownJudgment();
        //显示不击穿 //GC20191231
        if (!breakDown) {
            //显示不击穿    //GC20190710
            tvInformation.setVisibility(View.VISIBLE);
            tvInformation.setText(getResources().getString(R.string.not_break_down));
            return;
        }
        //计算方向脉冲
        calculatePulse();
        //计算故障距离并在界面显示
        correlationSimple();
        //放电脉冲位置确定——确定实光标
        breakPointCalculate();
        //光标自动定位
        icmAutoCursor();

    }

    /**
     * 脉冲电流直闪故障自动计算过程  //GC20200109
     */
    private void icmAutoTestDC() {
        //1.判断增益是否合适
        gainJudgment();
        switch (gainState) {
            case 0:
                tvInformation.setText("");
                break;
            case 1:
                gainState = 0;
                //显示增益过大
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_high));
                return;
            case 2:
                gainState = 0;
                //显示增益过小
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_low));
                return;
            default:
                break;
        }
        //软件滤波
        softwareFilter();
        //计算方向脉冲
        calculatePulse();
        //计算故障距离并在界面显示
        correlationSimpleDC();
        //光标自动定位
        icmAutoCursor();

    }

    /**
     * 脉冲电流方式增益自动判断
     */
    private void gainJudgment() {
        int i;
        int max = 0;
        int sub;

        //求ICM波形头直线段数值大小    //GC20200606
        int sum = 0;
        for (int j = 0; j < 10; j++) {
            sum += Constant.WaveData[j];
        }
        int ave = sum / 10;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeIcmDecay[rangeState]; i++) {
            sub = Constant.WaveData[i] - ave;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 25) {// if (max <= 42) {
            //判断增益过小——如果最大值小于 15% 38
            gainState = 2;
            return;
        }
        for (i = 0; i < dataMax - removeIcmDecay[rangeState]; i++) {
            if ((Constant.WaveData[i] > 242) || (Constant.WaveData[i] < 25)) {
//            if ((waveArray[i] > 242) || (waveArray[i] < 13)) {    //A20200527  ICM增益大小判断微调
                //判断增益过大
                gainState = 1;
                return;
            }
        }
    }

    /**
     * 脉冲电流方式软件滤波   方向脉冲法自动计算-软件滤波，一阶滞后滤波，低通截止频率约750kHz（两个采样频率都是这个截止频率）
     */
    private void softwareFilter() {
        //求ICM波形头直线断数值大小  //GC20200606
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Constant.WaveData[i];
        }
        int ave = sum / 10;
        for (int i = 1; i < dataMax - removeIcmDecay[rangeState]; i++) {
            if (rangeState > 6) {
                //25M采样——32km、64km范围
                waveArrayFilter[i] = 0.6232 * waveArrayFilter[i - 1] + 0.3768 * (double) (Constant.WaveData[i] - ave);//1.5M
            } else {
                waveArrayFilter[i] = 0.9058 * waveArrayFilter[i - 1] + 0.0942 * (double) (Constant.WaveData[i] - ave);//1.5M
            }
        }
    }

    /**
     * 脉冲电流方式数字积分   方向脉冲法自动计算-数字积分,反演电流
     */
    private void integral() {
        for (int i = 1; i < dataMax - removeIcmDecay[rangeState]; i++) {
            if (rangeState > 6) {
                //25M采样——32km、64km范围
                waveArrayIntegral[i] = waveArrayIntegral[i - 1] + waveArrayFilter[i] * 4e-8;
            } else {
                waveArrayIntegral[i] = waveArrayIntegral[i - 1] + waveArrayFilter[i] * 1e-8;
            }
        }
    }

    /**
     * 判断是否击穿放电
     */
    private void breakdownJudgment() {
        int i;
        //从触发开始计算初始值(去除波形前面的直线部分)
        int start = 140;    //GC20200110 提前一部分 start = 92;
        double sum = 0;
        int a = -1;
        double min = 0;
        for (i = start + 64; i < dataMax - 50; i++) {
            if (waveArrayIntegral[i] < min) {
                min = waveArrayIntegral[i];
                a = i;
            }
        }
        //GC20191231    //方法三  刷新击穿判断
        breakDown = false;
        //计算均值做基准
//        for (i = start + 64; i < start + 72; i++) {
        for (i = start + 174; i < start + 182; i++) {
            sum = sum + waveArrayIntegral[i];
        }
        sum = sum / (double) 8;
        Log.e("【算法】", " /min = " + min + " /最小位置 = " + a + " /sum*1.3 = " + sum * 1.3 + " /sum*1.2 = " + sum * 1.2);
        //积分电流
        for (i = start + 64; i < dataMax - 50; i++) {
            if (waveArrayIntegral[i] < 0) {
                if (waveArrayIntegral[i] < sum * (double) 1.3) {
                    breakDown = true;
                    break;
                }
            }
        }

    }

    /**
     * 脉冲电流方式  计算方向脉冲   方向脉冲法自动计算-使用滤波后电流的微分求VL=L * di/dt，滤波后电流*波阻抗 //使用滤波后电流进行微分
     */
    double L = 10e-6;
    double z = 25;

    private void calculatePulse() {
        double[] V = new double[65560];
        int i;
        double min = 0;
        //测距起点
        int breakPoint = 0;
        int start;

        //有测试缆  //GC20200103
        //Log.e("leadCat", "leadCat" +MainActivity.leadCat);
        if ((leadLength > 0) && (catlead1 == 1)) {  //if (leadLength > 0) {// if ((leadLength > 0) && (leadCat == 1)) {  //jk20201130  脉冲电流延长线不选就不计算
            z = (double) 50;
        } else {
            z = (double) 25;
        }
        //GC20191231
        for (i = 0; i < dataMax - removeIcmDecay[rangeState] - 1; i++) {
            if (rangeState > 6) {
                //25M采样——32km、64km范围
                V[i] = (waveArrayFilter[i + 1] - waveArrayFilter[i]) * 4.0e8;
            } else {
                V[i] = (waveArrayFilter[i + 1] - waveArrayFilter[i]) * 1.0e8;
            }
        }
        //方法三  确定测距起点
        start = 140;    //GC20200110 提前一部分 start = 120
        //测距起点使用一次导数的最小值，从触发后64点开始取，躲过电容放电脉冲
        for (i = start + 64; i < dataMax - 50; i++) {
            if ((V[i] < min) && (V[i] < 0)) {
                min = V[i];
                breakPoint = i;
            }
        }
        breakBk = breakPoint;

        //计算VL
        for (i = 0; i < dataMax - removeIcmDecay[rangeState]; i++) {
            V[i] = V[i] * L * -1.0;
        }
        //计算方向行波
        for (i = 0; i < dataMax - removeIcmDecay[rangeState]; i++) {
            s1[i] = V[i] + waveArrayFilter[i] * z;
            s2[i] = V[i] - waveArrayFilter[i] * z;
        }

    }

    /**
     * 脉冲电流直闪方式  计算故障距离(抽点做数据相关)  方向脉冲法自动计算-使用相关计算故障距离
     */
    double false_flag = 0;

    private void correlationSimpleDC() {
        int i;
        int j = 0;
        int k;
        float p;
        float[] P = new float[510];
        int w1;
        int w2;
        int w3;
        float[] s1Simple = new float[510];
        float[] s2Simple = new float[510];
        int maxBak;

        //GC20200109 DC方式下处理
        breakBk = 140;
        if (range >= 6) {//25M采样
            if (breakBk > (50 / 4)) {//需要修改，32km和64km采样频率变了，需要调整参数
                w1 = breakBk - (50 / 4);      //相关窗左侧
            } else {
                w1 = breakBk;
            }
            w2 = breakBk + (350 / 4);     //相关窗右侧
        } else {
            if (breakBk > 50) {//需要修改，32km和64km采样频率变了，需要调整参数
                w1 = breakBk - 50;      //相关窗左侧
            } else {
                w1 = breakBk;
            }
            w2 = breakBk + 350;     //相关窗右侧
        }
        for (i = 0; i < 510; i++) { //抽点
            s1Simple[i] = (float) s1[j];
            s2Simple[i] = (float) s2[j];
            j = j + densityMaxIcmDecay[rangeState];
        }
        w1 = w1 / densityMaxIcmDecay[rangeState];
        w2 = w2 / densityMaxIcmDecay[rangeState];
        w3 = 510 - w2;

        float[] S1 = new float[65556];
        float[] S2 = new float[65556];

        if (w2 >= 510) {  //jk20210420
            w2 = 510;
            false_flag = 1;
        }
        if (w1 >= 510) {  //jk20210420
            w1 = 510;
            false_flag = 1;
        }
        if (w3 >= 510) {  //jk20210420
            w3 = 510;
            false_flag = 1;
        }
        for (i = w1; i < w2; i++) {
            S1[i - w1] = s1Simple[i];
        }
        for (i = 0; i < w3; i++) {
            for (k = w1; k < w2; k++) {
                S2[k - w1] = s2Simple[k + i];
            }
            p = (float) 0.0;                    //清零
            for (j = 0; j < (w2 - w1); j++) { //进行相关运算
                p += S1[j] * S2[j] * -1.0;
            }
            P[i] = p;                //将整条波形的相关运算值存入P数组中
        }
        //计算P数组中的最大值，并确定位置
        float max = P[0];
        int maxIndex = 0;
        for (i = 0; i < w3; i++) {
            if (P[i] > max) {
                max = P[i];
                maxIndex = i;
            }
        }

        //换算为整条波形数据中的点数
        maxIndex = (w1 + maxIndex) * densityMaxIcmDecay[rangeState];
        //GC20191231
        maxBak = maxIndex;

        w1 = w1 * densityMaxIcmDecay[rangeState];
        w2 = w2 * densityMaxIcmDecay[rangeState];

        for (i = w1; i < w2; i++) {
            S1[i - w1] = (float) s1[i];
        }

        for (i = (maxIndex - densityMaxIcmDecay[rangeState]); i < (maxIndex + densityMaxIcmDecay[rangeState]); i++) {
            for (k = 0; k < w2 - w1; k++) {
                S2[k] = (float) s2[k + i];
            }
            //清零
            p = (float) 0.0;
            //进行相关运算S
            for (j = 0; j < (w2 - w1); j++) {
                p += S1[j] * S2[j] * -1.0;
            }
            //将整条波形的相关运算值存入P数组中
            P[i - (maxIndex - densityMaxIcmDecay[rangeState])] = p;
        }
        max = P[0];
        int maxIndex1 = 0;
        for (i = 0; i < densityMaxIcmDecay[rangeState] * 2; i++) {
            if (P[i] > max) {
                max = P[i];
                maxIndex1 = i;
            }
        }
        maxIndex = maxIndex - densityMaxIcmDecay[rangeState] + maxIndex1 - w1;

        //GC20191231
        if (maxIndex <= 0) {
            maxIndex = maxBak;
        }
        faultResult = maxIndex;
        //GN 可以没有，定光标位置即可出现距离
        calculateDistanceAuto(maxIndex);
    }

    /**
     * 脉冲电流方式  计算故障距离(抽点做数据相关)  方向脉冲法自动计算-使用相关计算故障距离        //GC20191231
     */
    private void correlationSimple() {
        int i;
        int j = 0;
        int k;
        double p;
        double[] P = new double[510];
        int w1;
        int w2;
        int w3;
        double[] s1Simple = new double[510];
        double[] s2Simple = new double[510];
        int maxBak;
        double distance;

        if (rangeState > 6) {
            //25M采样——32km、64km范围
            if (breakBk > (50 / 4)) {
                //相关窗左侧
                w1 = breakBk - (50 / 4);
            } else {
                w1 = breakBk;
            }
            //相关窗右侧
            w2 = breakBk + (350 / 4);
        } else {
            if (breakBk > 50) {
                //相关窗左侧
                w1 = breakBk - 50;
            } else {
                w1 = breakBk;
            }
            //相关窗右侧
            w2 = breakBk + 350;
        }

        //抽点
        for (i = 0; i < 510; i++) {
            s1Simple[i] = s1[j];
            s2Simple[i] = s2[j];
            j = j + densityMaxIcmDecay[rangeState];
        }
        w1 = w1 / densityMaxIcmDecay[rangeState];
        w2 = w2 / densityMaxIcmDecay[rangeState];
        w3 = 510 - w2;

        double[] S1 = new double[65556];
        double[] S2 = new double[65556];

        if (w2 >= 510) {  //jk20210420
            w2 = 510;
            false_flag = 1;
        }
        if (w1 >= 510) {  //jk20210420
            w1 = 510;
            false_flag = 1;
        }
        if (w3 >= 510) {  //jk20210420
            w3 = 510;
            false_flag = 1;
        }
        for (i = w1; i < w2; i++) {
            S1[i - w1] = s1Simple[i];
        }
        for (i = 0; i < w3; i++) {
            for (k = w1; k < w2; k++) {
                S2[k - w1] = s2Simple[k + i];
            }
            p = 0.0;
            //进行相关运算
            for (j = 0; j < (w2 - w1); j++) {
                p += S1[j] * S2[j] * -1.0;
            }
            //将整条波形的相关运算值存入P数组中
            P[i] = p;
        }

        //计算P数组中的最大值，并确定位置
        double max = P[0];
        int maxIndex = 0;
        for (i = 0; i < w3; i++) {
            if (P[i] > max) {
                max = P[i];
                maxIndex = i;
            }
        }

        //换算为整条波形数据中的点数
        maxIndex = (w1 + maxIndex) * densityMaxIcmDecay[rangeState];
        //GC20191231
        maxBak = maxIndex;

        //增加计算距离为0时的情况判断    找出所有的极值点
        if (maxIndex == 0) {
            double[] maxData = new double[65560];
            double[] maxData1 = new double[65560];
            int[] maxDataPos = new int[65560];
            //补偿系数
            double a = 0.05;
            i = 3;
            j = 1;
            maxDataPos[0] = 0;
            maxData[0] = P[0];
            while ((j < 255) && (i < w3)) {
                if ((P[i] > P[i - 1]) && (P[i] >= P[i + 1])) {
                    if ((i >= 3) && (P[i - 1] > P[i - 2])) {
                        if (P[i - 2] > P[i - 3]) {
                            maxDataPos[j] = i;
                            maxData[j] = P[i];
                            j++;
                        }
                    }
                }
                i++;
            }
            k = 0;
            for (i = 0; i < j; i++) {
                //找到幅值>0.3最大值的极值点
                if (maxData[i] > 0.3 * max) {
                    //max_data[i]是否换成max_data_pos[i]
                    distance = pointToDistance((int) maxData[i]);
                    //a待定 20190821
                    maxData1[k] = maxData[i] / ((double) 1 - ((double) 2 * a * (distance / (double) 1000)));
                    maxDataPos[k] = maxDataPos[i];
                    k++;
                }
            }
            //排序算法
            sort(maxData1, maxDataPos, k);
            if (pointToDistance(maxDataPos[0]) >= 10) {
                //故障距离在10米外
                maxIndex = maxDataPos[0];
            } else if ((pointToDistance(maxDataPos[1]) < 10) && (maxData1[1] < maxData1[0] * 0.4)) {
                maxIndex = maxDataPos[0];
            } else if ((pointToDistance(maxDataPos[1]) >= 80) && (maxData1[1] >= maxData1[0] * 0.4)) {
                maxIndex = maxDataPos[1];
            } else if ((pointToDistance(maxDataPos[1]) >= 10) && (pointToDistance(maxDataPos[1]) >= 80)) {
                if (maxData1[2] >= maxData1[0] * 0.4) {
                    if (Math.abs(pointToDistance(maxDataPos[2]) - (double) 2 * (pointToDistance(maxDataPos[1]) + (double) 10)) < (double) 10) {
                        maxIndex = maxDataPos[1];
                    }
                }
            } else if (pointToDistance(maxDataPos[2]) >= 80) {
                maxIndex = maxDataPos[2];
            } else if (((pointToDistance(maxDataPos[2]) >= 10) && (pointToDistance(maxDataPos[2]) >= 80))) {
                if (maxData1[2] >= maxData1[0] * 0.4) {
                    if (Math.abs(pointToDistance(maxDataPos[3]) - (double) 2 * (pointToDistance(maxDataPos[2]) + (double) 10)) < (double) 10) {
                        maxIndex = maxDataPos[2];
                    }
                } else {
                    maxIndex = maxDataPos[0];
                }
            }
        }

        w1 = w1 * densityMaxIcmDecay[rangeState];
        w2 = w2 * densityMaxIcmDecay[rangeState];

        for (i = w1; i < w2; i++) {
            S1[i - w1] = s1[i];
        }

        for (i = (maxIndex - densityMaxIcmDecay[rangeState]); i < (maxIndex + densityMaxIcmDecay[rangeState]); i++) {
            for (k = 0; k < w2 - w1; k++) {
                S2[k] = s2[k + i];
            }
            //清零
            p = 0.0;
            //进行相关运算S
            for (j = 0; j < (w2 - w1); j++) {
                p += S1[j] * S2[j] * -1.0;
            }
            //将整条波形的相关运算值存入P数组中
            P[i - (maxIndex - densityMaxIcmDecay[rangeState])] = p;
        }
        max = P[0];
        int maxIndex1 = 0;
        for (i = 0; i < densityMaxIcmDecay[rangeState] * 2; i++) {
            if (P[i] > max) {
                max = P[i];
                maxIndex1 = i;
            }
        }
        maxIndex = maxIndex - densityMaxIcmDecay[rangeState] + maxIndex1 - w1;

        //GC20191231
        if (maxIndex <= 0) {
            maxIndex = maxBak;
        }
        faultResult = maxIndex;
        //GN 可以没有，定光标位置即可出现距离
        calculateDistanceAuto(maxIndex);
    }

    /**
     * @param samplingPoints
     */
    int pointToDistance(int samplingPoints) {
        int Fx1;
        int k = 1;
        //脉冲电流方式下range=6(32km)和range=7(64km)实时25M采样率，其余方式和范围实时100M采样率，此时相对其它方式采样周期扩大4倍
        if (rangeState > 6) {
            k = 4;
        }
        //算出距离的整数部分
        Fx1 = (int) (((samplingPoints * velocity) * k) / 200);
        return (Fx1);

    }

    /**
     * @param samplingPoints 方向脉冲法自动计算-显示故障距离   //GC20191231 自动定光标已给出距离，可忽略
     */
    private void calculateDistanceAuto(int samplingPoints) {
        int k = 1;
        int l;
        int lFault;
        double distance;

        //脉冲电流方式下range=6(32km)和range=7(64km)实时25M采样率，其余方式和范围实时100M采样率，此时相对其它方式采样周期扩大4倍
        if (rangeState > 6) {
            k = 4;
        }
        //有测试缆  //GC20200103
        // Log.e("leadCat", "leadCat" +MainActivity.leadCat);
        if ((leadLength > 0) && (catlead1 == 1)) {  //jk20201130  脉冲电流延长线不选就不计算// if(leadLength > 0) {
            //实际点数
            l = (int) (leadLength * 2000 / leadVop / 10);
            lFault = samplingPoints - l;
            distance = (((double) lFault * velocity) * k) / 2 * 0.01 + leadLength;
        } else {
            distance = (((double) samplingPoints * velocity) * k) / 2 * 0.01;
        }
        //自动距离界面显示
//        tvAutoDistance.setText(new DecimalFormat("0.00").format(distance) + "m");
        //距离界面显示
//        tvDistance.setText(new DecimalFormat("0.00").format(distance) + "m");

    }

    /**
     * 排序——数组和数组长度  //GC20191231
     */
    private void sort(double[] a, int[] b, int l) {
        int i, j;
        double v;
        int k;
        //排序主体
        for (i = 0; i < l - 1; i++) {
            for (j = i + 1; j < l; j++) {
                //如前面的比后面的小，则交换。
                if (a[i] < a[j]) {
                    v = a[i];
                    a[i] = a[j];
                    a[j] = v;
                    k = b[i];
                    b[i] = b[j];
                    a[j] = k;
                }
            }
        }
    }

    /**
     * 击穿点位置判断,确定光标起始位置
     */
    private void breakPointCalculate() {
        int i;
        int j;
        int k;
        int start;
        double min = 0;
        double[] Diff = new double[65560];
        int pos = 0;

        double p;
        double[] P = new double[65560];
        int w1;
        int w2;
        int w3;

        double[] D1 = new double[65560];
        double[] D2 = new double[65560];
        double[] maxData = new double[65560];
        int[] maxDataPos = new int[65560];

        start = 140;    //GC20200110  start = 0;
        for (i = 0; i < (dataMax - 50); i++) {
            Diff[i] = (waveArrayFilter[i + 1] - waveArrayFilter[i]);
        }

        for (i = start + faultResult; i < (dataMax - 50); i++) {
            if ((Diff[i] < min) && (Diff[i] < 0)) {
                min = Diff[i];
                //pos位置减去了起始位置+故障距离
                pos = i - (start + faultResult);
            }
        }
        pos = pos + (start + faultResult);

        w1 = pos - 30;
        w2 = pos + 70;
        w3 = pos - (start + faultResult);
        for (i = w1; i < w2; i++) {
            D1[i - w1] = waveArrayFilter[i];
        }


        for (i = (start + faultResult); i < pos; i++) {
            for (k = i; k < (i + 100); k++) {
                D2[k - i] = waveArrayFilter[k];
            }
            p = 0.0;
            for (j = 0; j < (w2 - w1); j++) {
                //进行相关运算
                p += D1[j] * D2[j];
            }
            //将整条波形的相关运算值存入P数组中
            P[i - (start + faultResult)] = p;
        }

        //计算P数组中的最大值，并确定位置
        double max = P[0];
        int maxIndex = 0;
        for (i = 0; i < w3; i++) {
            if (P[i] > max) {
                max = P[i];
                maxIndex = i;
            }
        }
        breakBk = maxIndex;
        //找出所有的极值点，并找到>0.7倍最大值的极值点作为有效极值点
        i = 3;
        j = 0;
        while ((j < 255) && (i < w3)) {
            if ((P[i] > P[i - 1]) && (P[i] >= P[i + 1])) {
                if ((i >= 3) && (P[i - 1] > P[i - 2])) {
                    if (P[i - 2] > P[i - 3]) {
                        maxDataPos[j] = i;
                        maxData[j] = P[i];
                        j++;
                    }
                }
            }
            i++;
        }
        for (k = 0; k < j; k++) {
            if (maxData[k] > 0.7 * Math.abs(max)) {
                //有效极值点
                breakBk = maxDataPos[k];
                break;
            }
        }
        //实光标位置
        breakBk = breakBk + start + faultResult + 10;
    }

    /**
     * 脉冲电流方式光标自动定位 //GC20190708
     */
    private void icmAutoCursor() {
        //GC20200106
        if (false_flag == 0) {  //jk20210420 容错添加，如果出错就不走自动定位
            zero = breakBk;
            pointDistance = breakBk + faultResult;
            //positionReal = zero / densityMax;
            //positionVirtual = pointDistance / densityMax;
            // sc 20200109   光标定位
            density = getDensity();
            Log.e("cursor", "位置" + density);
            if (density == densityMax) {
                positionReal = zero / densityMax;
                positionVirtual = pointDistance / densityMax;
            }
            //重新定位实光标
            if (zero >= (currentMoverPosition510 * dataLength / 510) && zero <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
                positionReal = (zero - (currentMoverPosition510 * dataLength / 510)) / density;
                mainWave.setScrubLineReal(positionReal);
            } else {
                mainWave.setScrubLineRealDisappear();
            }
            //重新定位虚光标
            if (pointDistance >= (currentMoverPosition510 * dataLength / 510) && pointDistance <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
                positionVirtual = (pointDistance - (currentMoverPosition510 * dataLength / 510)) / density;
                mainWave.setScrubLineVirtual(positionVirtual);
            } else {
                mainWave.setScrubLineVirtualDisappear();
            }

            //光标定位
            //   mainWave.setScrubLineReal(positionReal);
            //  mainWave.setScrubLineVirtual(positionVirtual);
            //距离显示
            calculateDistance(Math.abs(pointDistance - zero));
        }
    }

    /**
     * 无缩放时波形抽点（抽点510个）——最终得到waveDraw和waveCompare
     */
    private void organizeWaveData() {
        //按最大比例抽出510个点
        for (int i = 0, j = 0; j < 510; i = i + densityMax, j++) {
            //组织TDR、ICM、ICM_DECAY、DECAY和SIM的第一条波形的数据
            waveDraw[j] = Constant.WaveData[i];
            //组织SIM的第二条波形的数据
            if (mode == SIM) {
                waveCompare[j] = Constant.SimData[i];
                if (!isDatabase) {
                    simDraw1[j] = simArray1[i];
                    simDraw2[j] = simArray2[i];
                    simDraw3[j] = simArray3[i];
                    simDraw4[j] = simArray4[i];
                    simDraw5[j] = simArray5[i];
                    simDraw6[j] = simArray6[i];
                    simDraw7[j] = simArray7[i];
                    simDraw8[j] = simArray8[i];
                }
            }
        }
        //计算需要处理波形的原始长度dataLength
        if ((mode == TDR) || (mode == SIM)) {
            dataLength = dataMax - removeTdrSim[rangeState];
            //250m范围取点  //GC20191223
            if (range == RANGE_250) {
                int[] waveDraw250 = new int[256];
                int[] waveCompare250 = new int[256];
                int[] simDraw2501 = new int[256];
                int[] simDraw2502 = new int[256];
                int[] simDraw2503 = new int[256];
                int[] simDraw2504 = new int[256];
                int[] simDraw2505 = new int[256];
                int[] simDraw2506 = new int[256];
                int[] simDraw2507 = new int[256];
                int[] simDraw2508 = new int[256];
                //取出前256个点的数数据（500m范围一半的点数不够，差值计算凑足510个点）
                for (int i = 0, j = 0; i < 256; i++, j++) {
                    //组织TDR、SIM第一条波形的数据
                    waveDraw250[j] = waveDraw[i];
                    //组织SIM的第二条波形的数据
                    if (mode == SIM) {
                        waveCompare250[j] = waveCompare[i];
                        if (!isDatabase) {
                            simDraw2501[j] = simDraw1[i];
                            simDraw2502[j] = simDraw2[i];
                            simDraw2503[j] = simDraw3[i];
                            simDraw2504[j] = simDraw4[i];
                            simDraw2505[j] = simDraw5[i];
                            simDraw2506[j] = simDraw6[i];
                            simDraw2507[j] = simDraw7[i];
                            simDraw2508[j] = simDraw8[i];
                        }
                    }
                }
                //利用原始的256个点数据算出差值的255个点
                for (int i = 0, j = 1; i < 255; i++, j += 2) {
                    //组织TDR、SIM第一条波形的数据
                    waveDraw[j] = waveDraw250[i] + (waveDraw250[i + 1] - waveDraw250[i]) / 2;
                    //组织SIM的第二条波形的数据
                    if (mode == SIM) {
                        waveCompare[j] = waveCompare250[i] + (waveCompare250[i + 1] - waveCompare250[i]) / 2;
                        if (!isDatabase) {
                            simDraw1[j] = simDraw2501[i] + (simDraw2501[i + 1] - simDraw2501[i]) / 2;
                            simDraw2[j] = simDraw2502[i] + (simDraw2502[i + 1] - simDraw2502[i]) / 2;
                            simDraw3[j] = simDraw2503[i] + (simDraw2503[i + 1] - simDraw2503[i]) / 2;
                            simDraw4[j] = simDraw2504[i] + (simDraw2504[i + 1] - simDraw2504[i]) / 2;
                            simDraw5[j] = simDraw2505[i] + (simDraw2505[i + 1] - simDraw2505[i]) / 2;
                            simDraw6[j] = simDraw2506[i] + (simDraw2506[i + 1] - simDraw2506[i]) / 2;
                            simDraw7[j] = simDraw2507[i] + (simDraw2507[i + 1] - simDraw2507[i]) / 2;
                            simDraw8[j] = simDraw2508[i] + (simDraw2508[i + 1] - simDraw2508[i]) / 2;
                        }
                    }
                }
                //将原始255个点分散
                for (int i = 0, j = 0; j < 255; i++, j++) {
                    waveDraw[2 * j] = waveDraw250[i];
                    //组织SIM的第二条波形的数据
                    if (mode == SIM) {
                        waveCompare[2 * j] = waveCompare250[i];
                        if (!isDatabase) {
                            simDraw1[2 * j] = simDraw2501[i];
                            simDraw2[2 * j] = simDraw2502[i];
                            simDraw3[2 * j] = simDraw2503[i];
                            simDraw4[2 * j] = simDraw2504[i];
                            simDraw5[2 * j] = simDraw2505[i];
                            simDraw6[2 * j] = simDraw2506[i];
                            simDraw7[2 * j] = simDraw2507[i];
                            simDraw8[2 * j] = simDraw2508[i];
                        }
                    }
                }
            }
        } else if ((mode == ICM) || (mode == DECAY) || (mode == ICM_DECAY)) {
            dataLength = dataMax - removeIcmDecay[rangeState];
            //250m范围取点
            if (range == RANGE_250) {
                dataLength = dataLength / 2;
            }
        }
        //设置滑动块宽度
        setHorizontalMoveView();

    }

    /**
     * 有缩放时波形抽点
     */
    private void organizeZoomWaveData(int start) {
        //根据起始点位置，按比例抽出510个点
        for (int i = start, j = 0; j < 510; i = i + density, j++) {
            //组织TDR、ICM、ICM_DECAY、DECAY和SIM的第一条波形的数据
            if (i < Constant.WaveData.length) {
                waveDraw[j] = Constant.WaveData[i];
            }
            //组织SIM的第二条波形的数据
            if (mode == SIM) {
                waveCompare[j] = Constant.SimData[i];
                if (!isDatabase) {
                    simDraw1[j] = simArray1[i];
                    simDraw2[j] = simArray2[i];
                    simDraw3[j] = simArray3[i];
                    simDraw4[j] = simArray4[i];
                    simDraw5[j] = simArray5[i];
                    simDraw6[j] = simArray6[i];
                    simDraw7[j] = simArray7[i];
                    simDraw8[j] = simArray8[i];
                }
            }
        }
    }

    /**
     * 记忆波形数据抽点（包含缩放时候）
     */
    private void organizeCompareWaveData(int start) {
        //根据起始点位置，按比例抽出510个点
        for (int i = start, j = 0; j < 510; i = i + density, j++) {
            //组织TDR、ICM、ICM_DECAY、DECAY
            waveCompareDraw[j] = waveCompare[i];
        }
        if ((mode == TDR) || (mode == SIM)) {
            //250m范围  //GC20191223
            if (range == RANGE_250) {
                int[] waveDraw250 = new int[256];
                //取出前256个点的数数据（用于差值计算）
                for (int i = 0, j = 0; i < 256; i++, j++) {
                    waveDraw250[j] = waveCompareDraw[i];
                }
                //利用原始的256个点数据算出差值的255个点
                for (int i = 0, j = 1; i < 255; i++, j += 2) {
                    waveCompareDraw[j] = waveDraw250[i] + (waveDraw250[i + 1] - waveDraw250[i]) / 2;
                }
                //将原始255个点分散
                for (int i = 0, j = 0; j < 255; i++, j++) {
                    waveCompareDraw[2 * j] = waveDraw250[i];
                }
            }
        }

    }

    /**
     * 缩放时根据虚光标位置确定起始点，然后抽510个点 //GC20200611
     */
    private void organizeClickZoomData() {
        //画光标，确定起始点   //GC20200611
        drawClickZoomCursor();
        //按比例抽出510个点
        for (int i = currentStart, j = 0; j < 510; i = i + density, j++) {
            //组织TDR、ICM、ICM_DECAY、DECAY和SIM的第一条波形的数据
            waveDraw[j] = Constant.WaveData[i + index];
            //组织SIM的第二条波形的数据
            if (mode == SIM) {
                waveCompare[j] = Constant.SimData[i];
                if (!isDatabase) {
                    simDraw1[j] = simArray1[i];
                    simDraw2[j] = simArray2[i];
                    simDraw3[j] = simArray3[i];
                    simDraw4[j] = simArray4[i];
                    simDraw5[j] = simArray5[i];
                    simDraw6[j] = simArray6[i];
                    simDraw7[j] = simArray7[i];
                    simDraw8[j] = simArray8[i];
                }
            }
        }
        //组织比对波形数据
        try {
            organizeCompareWaveData(currentStart);
        } catch (Exception ignored) {
        }
        //根据起始点计算当前滑块左侧在510个点中的位置
        currentMoverPosition510 = currentStart / densityMax;
        //计算水平滑块左侧位置的屏幕坐标
        int moverPosition;
        moverPosition = mvWave.getParentWidth() * currentMoverPosition510 / 510;
        //重新设置滑块大小
        setHorizontalMoveView();
        //移动滑块到指定位置
        setMoverPosition(moverPosition);

    }

    /**
     * 点击缩放按键时重新绘制光标    //GC20200611
     */
    private void drawClickZoomCursor() {
        Log.e("【滑块-点击缩放】", "densityMax:" + densityMax + "/density:" + density + "/pointDistance:" + pointDistance + "/positionVirtual" + positionVirtual
                + "/positionReal:" + positionReal + "/zero:" + zero + "/datalength:" + dataLength + "/currentStart:" + currentStart);
        if (pointDistance < 255 * density) {
            //虚光标无法位于正中心，起始点从0开始
            currentStart = 0;
            positionVirtual = pointDistance / density;
            //判断是否画实光标
            if (zero > currentStart + 510 * density) {
                mainWave.setScrubLineRealDisappear();
            } else {
                positionReal = zero / density;
                mainWave.setScrubLineReal(positionReal);
            }
            //判断是是否画标记光标
            if (mode == SIM) {
                if (simStandardZero >= currentStart + 510 * density) {
                    mainWave.setScrubLineSimDisappear();
                } else {
                    positionSim = simStandardZero / density;
                    mainWave.setScrubLineSim(positionSim);
                }
            }
        } else if ((pointDistance >= 255 * density) && (pointDistance < dataLength - 255 * density)) {
            //波形以虚光标原始位置为中心
            currentStart = pointDistance - 255 * density;
            positionVirtual = 255;
            //判断是否画实光标
            if ((zero < currentStart) || (zero >= currentStart + 510 * density)) {
                mainWave.setScrubLineRealDisappear();
            } else {
                positionReal = positionVirtual - (pointDistance - zero) / density;
                mainWave.setScrubLineReal(positionReal);
            }
            //判断是是否画标记光标
            if (mode == SIM) {
                if ((zero < currentStart) || (zero >= currentStart + 510 * density)) {
                    mainWave.setScrubLineSimDisappear();
                } else {
                    positionSim = (simStandardZero - currentStart) / density;
                    mainWave.setScrubLineSim(positionSim);
                }
            }
        } else {
            //波形靠最左侧
            currentStart = dataLength - 510 * density;
            positionVirtual = (pointDistance - currentStart) / density;
            //判断是否画实光标
            if (zero < currentStart) {
                mainWave.setScrubLineRealDisappear();
            } else {
                positionReal = (zero - currentStart) / density;
                mainWave.setScrubLineReal(positionReal);
            }
            //判断是是否画标记光标
            if (mode == SIM) {
                if (simStandardZero < currentStart) {
                    mainWave.setScrubLineSimDisappear();
                } else {
                    positionSim = (simStandardZero - currentStart) / density;
                    mainWave.setScrubLineReal(positionReal);
                }
            }
        }
        //还原状态
        if (density == densityMax) {
            currentStart = 0;
            positionVirtual = pointDistance / densityMax;
            //画实光标
            positionReal = zero / densityMax;
            mainWave.setScrubLineReal(positionReal);
            //画SIM标记光标    //GC20200330
            if (mode == SIM) {
                positionSim = simStandardZero / densityMax;
                mainWave.setScrubLineSim(positionSim);
            }
        }
        //画虚光标
        mainWave.setScrubLineVirtual(positionVirtual);
        Log.e("【滑块-点击缩放计算后】", "/pointDistance:" + pointDistance + "/positionVirtual" + positionVirtual
                + "/positionReal:" + positionReal + "/zero:" + zero + "/datalength:" + dataLength + "/currentStart:" + currentStart);

    }

    /**
     * 设置波形绘制参数
     */
    private void setWaveParameter() {
        //记录当前显示波形的参数
        Constant.ModeValue = mode;
        Constant.RangeValue = range;
        Constant.Gain = gain;
        Constant.Velocity = velocity;
        Constant.DensityMax = densityMax;
        if (density > densityMax) {
            density = densityMax;
            tvZoomValue.setText("1 : " + density);
        }
        //非显示数据库波形状态
        isDatabase = false;
        //擦除比较波形
        isCom = false;
        if (mode == TDR) {
            //需要绘制的波形原始数组初始化
            dataMax = READ_TDR_SIM[rangeState];
            waveArray = new int[dataMax];
            Constant.WaveData = new int[dataMax];
        } else if ((mode == ICM) || (mode == ICM_DECAY) || (mode == DECAY)) {
            dataMax = READ_ICM_DECAY[rangeState];
            waveArray = new int[dataMax];
            Constant.WaveData = new int[dataMax];
        } else if (mode == SIM) {
            dataMax = READ_TDR_SIM[rangeState];
            waveArray = new int[dataMax];
            Constant.WaveData = new int[dataMax];
            Constant.SimData = new int[dataMax];
            //SIM第二条波形原始数组初始化
            simArray1 = new int[dataMax];
            simArray2 = new int[dataMax];
            simArray3 = new int[dataMax];
            simArray4 = new int[dataMax];
            simArray5 = new int[dataMax];
            simArray6 = new int[dataMax];
            simArray7 = new int[dataMax];
            simArray8 = new int[dataMax];
            //利用比较功能绘制SIM的第二条波形数据
            isCom = true;

        }

        //SIM模式下不要重置零点，因为positionReal整除会丢失精度    //G??
        if (density == densityMax) {
            if (mode != SIM) {
                zero = positionReal * densityMax;
            }
            pointDistance = positionVirtual * densityMax;
        }

    }

    int min1Pos;
    int min2Pos;
    int min3Pos;
    int min4pos;
    int min5Pos;
    int min6Pos;
    int min7Pos;
    int min8Pos;
    boolean selectSim1;
    boolean selectSim2;
    boolean selectSim3;
    boolean selectSim4;
    boolean selectSim5;
    boolean selectSim6;
    boolean selectSim7;
    boolean selectSim8;
    int sim_g;
    int sim_u;
    int sim_point;
    // int sim_point8;

    /**
     * SIM最优筛选  //GC20200529
     */
    public void selectBestSim() {
        //添加增益判断    //GC20200609
        gainJudgmentSim();
        switch (gainState) {
            case 0:
                tvInformation.setText("");
                break;
            case 1:
                gainState = 0;
                //显示增益过大
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_high));
                //显示序号1波形
                selectSim = 1;
                setSelectSim(selectSim);
                return;
            case 2:
                gainState = 0;
                //显示增益过小
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.gain_too_low));
                //显示序号1波形
                selectSim = 1;
                setSelectSim(selectSim);
                return;
            default:
                break;
        }
        //筛选1.判断两条波形重合度
        int sum = 0;
        for (int i = 0, waveNum = 1; i < 8; i++, waveNum++) {
            double p = Math.abs((float) (simSum[waveNum] - simSum[0]) / simSum[0]);
//            if ((p > 0.213 || p < 0.019)) { //A20200606 重合系数微调
            if (p > 0.213) {
                //重合度不好,上下分离
                overlapNum[i] = 0;
                Log.e("SIM筛选1", waveNum + "不重合" + " /p = " + p);
            } else {
                //重合度好
                overlapNum[i] = waveNum;
                Log.e("SIM筛选1", waveNum + "重  合" + " /p = " + p);
            }
            sum += overlapNum[i];
        }
        if (sum == 0) {
            Log.e("SIM筛选1", " 没有重合的波形");
            tvInformation.setVisibility(View.VISIBLE);
            tvInformation.setText(getResources().getString(R.string.testAgain));
            //无重合显示波形1  //GC20200601
            selectSim = 1;
            setSelectSim(selectSim);
        } else {
            //筛选2.判断极值位置
            int j = pulseRemove[rangeState] + 3;
            int maxNum = 0;
            int[] maxData = new int[65560];
            int[] maxDataPos = new int[65560];
            //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
            while ((j >= pulseRemove[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState])) {
                if ((waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1])) {
                    if (waveArray[j - 1] >= waveArray[j - 2]) {
                        if (waveArray[j - 2] > waveArray[j - 3]) {
                            maxData[maxNum] = waveArray[j];
                            maxDataPos[maxNum] = j;
//                            Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                            maxNum++;
                        }
                    }
                }
                j++;
            }
            if (maxNum == 0) {
                Log.e("SIM筛选2", "没有极大值");
                tvInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getResources().getString(R.string.testAgain));
                //无极大值显示波形1  //GC20200601
                selectSim = 1;
                setSelectSim(selectSim);
            } else {
                int max = maxData[0];
                int maxPos = maxDataPos[0];
                for (int k = 0; k < maxNum; k++) {
                    if (maxData[k] >= max) {
                        max = maxData[k];
                        maxPos = maxDataPos[k];
                    }
                }
                Log.e("SIM筛选2", " /最大极大值位置 = " + maxPos);
                //3.寻找燃弧脉冲的极小值点位置（去除发射脉冲和末尾数据）
                for (int l = 0; l < 8; l++) {
                    //重合才寻找极小值
                    if (overlapNum[l] == 1) {
                        int i1 = pulseRemove[rangeState] + 5;
                        int minNum1 = 0;
                        int[] minData1 = new int[65560];
                        int[] minDataPos1 = new int[65560];
                        while ((i1 >= pulseRemove[rangeState] + 5) && (i1 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray1[i1] < simArray1[i1 - 1]) && (simArray1[i1] <= simArray1[i1 + 1])) {
                                if (simArray1[i1 - 1] <= simArray1[i1 - 2]) {
                                    if (simArray1[i1 - 2] <= simArray1[i1 - 3]) {
                                        if (simArray1[i1 - 3] <= simArray1[i1 - 4]) {
                                            if (simArray1[i1 - 4] <= simArray1[i1 - 5]) {
                                                minData1[minNum1] = simArray1[i1];
                                                minDataPos1[minNum1] = i1;
                                                minNum1++;
                                            }
                                        }
                                    }
                                }
                            }
                            i1++;
                        }
                        if (minNum1 > 0) {
                            int min1 = minData1[0];
                            for (int k1 = 0; k1 < minNum1; k1++) {
                                if (minData1[k1] <= min1) {
                                    min1 = minData1[k1];
                                    min1Pos = minDataPos1[k1];
                                }
                            }
                            //与极大值点位置比较，判断是否进行相关计算   //20200601 断线故障处理优化
                            if (min1Pos < maxPos + 60) { // if (min1Pos < maxPos + 30) { //20200610
                                selectSim1 = true;
                                Log.e("SIM筛选2", "1极小值符合要求  " + " /min1Pos = " + min1Pos);
                            } else {
                                Log.e("SIM筛选2", "1极小值不符合要求" + " /min1Pos = " + min1Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "1未找到极小值");
                        }

                    }

                    if (overlapNum[l] == 2) {
                        int i2 = pulseRemove[rangeState] + 5;
                        int minNum2 = 0;
                        int[] minData2 = new int[65560];
                        int[] minDataPos2 = new int[65560];
                        while ((i2 >= pulseRemove[rangeState] + 5) && (i2 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray2[i2] < simArray2[i2 - 1]) && (simArray2[i2] <= simArray2[i2 + 1])) {
                                if (simArray2[i2 - 1] <= simArray2[i2 - 2]) {
                                    if (simArray2[i2 - 2] <= simArray2[i2 - 3]) {
                                        if (simArray2[i2 - 3] <= simArray2[i2 - 4]) {
                                            if (simArray2[i2 - 4] <= simArray2[i2 - 5]) {
                                                minData2[minNum2] = simArray2[i2];
                                                minDataPos2[minNum2] = i2;
                                                minNum2++;
                                            }
                                        }
                                    }
                                }
                            }
                            i2++;
                        }
                        if (minNum2 >= 1) {
                            int min2 = minData2[0];
                            for (int k2 = 0; k2 < minNum2; k2++) {
                                if (minData2[k2] <= min2) {
                                    min2 = minData2[k2];
                                    min2Pos = minDataPos2[k2];
                                }
                            }
                            if (min2Pos < maxPos + 60) {
                                selectSim2 = true;
                                Log.e("SIM筛选2", "2极小值符合要求  " + " /min2Pos = " + min2Pos);
                            } else {
                                Log.e("SIM筛选2", "2极小值不符合要求" + " /min2Pos = " + min2Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "2未找到极小值");
                        }

                    }

                    if (overlapNum[l] == 3) {
                        int i3 = pulseRemove[rangeState] + 5;
                        int minNum3 = 0;
                        int[] minData3 = new int[65560];
                        int[] minDataPos3 = new int[65560];
                        while ((i3 >= pulseRemove[rangeState] + 5) && (i3 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray3[i3] < simArray3[i3 - 1]) && (simArray3[i3] <= simArray3[i3 + 1])) {
                                if (simArray3[i3 - 1] <= simArray3[i3 - 2]) {
                                    if (simArray3[i3 - 2] <= simArray3[i3 - 3]) {
                                        if (simArray3[i3 - 3] <= simArray3[i3 - 4]) {
                                            if (simArray3[i3 - 4] <= simArray3[i3 - 5]) {
                                                minData3[minNum3] = simArray3[i3];
                                                minDataPos3[minNum3] = i3;
                                                minNum3++;
                                            }
                                        }
                                    }
                                }
                            }
                            i3++;
                        }
                        if (minNum3 >= 1) {
                            int min3 = minData3[0];
                            for (int k3 = 0; k3 < minNum3; k3++) {
                                if (minData3[k3] <= min3) {
                                    min3 = minData3[k3];
                                    min3Pos = minDataPos3[k3];
                                }
                            }
                            if (min3Pos < maxPos + 60) {
                                selectSim3 = true;
                                Log.e("SIM筛选2", "3极小值符合要求  " + " /min3Pos = " + min3Pos);
                            } else {
                                Log.e("SIM筛选2", "3极小值不符合要求" + " /min3Pos = " + min3Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "3未找到极小值");
                        }

                    }

                    if (overlapNum[l] == 4) {
                        int i4 = pulseRemove[rangeState] + 5;
                        int minNum4 = 0;
                        int[] minData4 = new int[65560];
                        int[] minDataPos4 = new int[65560];
                        while ((i4 >= pulseRemove[rangeState] + 5) && (i4 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray4[i4] < simArray4[i4 - 1]) && (simArray4[i4] <= simArray4[i4 + 1])) {
                                if (simArray4[i4 - 1] <= simArray4[i4 - 2]) {
                                    if (simArray4[i4 - 2] <= simArray4[i4 - 3]) {
                                        if (simArray4[i4 - 3] <= simArray4[i4 - 4]) {
                                            if (simArray4[i4 - 4] <= simArray4[i4 - 5]) {
                                                minData4[minNum4] = simArray4[i4];
                                                minDataPos4[minNum4] = i4;
                                                minNum4++;
                                            }
                                        }
                                    }
                                }
                            }
                            i4++;
                        }
                        if (minNum4 >= 1) {
                            int min4 = minData4[0];
                            for (int k4 = 0; k4 < minNum4; k4++) {
                                if (minData4[k4] <= min4) {
                                    min4 = minData4[k4];
                                    min4pos = minDataPos4[k4];
                                }
                            }
                            if (min4pos < maxPos + 60) {
                                selectSim4 = true;
                                Log.e("SIM筛选2", "4极小值符合要求  " + " /min4pos = " + min4pos);
                            } else {
                                Log.e("SIM筛选2", "4极小值不符合要求" + " /min4pos = " + min4pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "4未找到极小值");
                        }

                    }

                    if (overlapNum[l] == 5) {
                        int i5 = pulseRemove[rangeState] + 5;
                        int minNum5 = 0;
                        int[] minData5 = new int[65560];
                        int[] minDataPos5 = new int[65560];
                        while ((i5 >= pulseRemove[rangeState] + 5) && (i5 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray5[i5] < simArray5[i5 - 1]) && (simArray5[i5] <= simArray5[i5 + 1])) {
                                if (simArray5[i5 - 1] <= simArray5[i5 - 2]) {
                                    if (simArray5[i5 - 2] <= simArray5[i5 - 3]) {
                                        if (simArray5[i5 - 3] <= simArray5[i5 - 4]) {
                                            if (simArray5[i5 - 4] <= simArray5[i5 - 5]) {
                                                minData5[minNum5] = simArray5[i5];
                                                minDataPos5[minNum5] = i5;
                                                minNum5++;
                                            }
                                        }
                                    }
                                }
                            }
                            i5++;
                        }
                        if (minNum5 >= 1) {
                            int min5 = minData5[0];
                            for (int k5 = 0; k5 < minNum5; k5++) {
                                if (minData5[k5] <= min5) {
                                    min5 = minData5[k5];
                                    min5Pos = minDataPos5[k5];
                                }
                            }
                            if (min5Pos < maxPos + 60) {
                                selectSim5 = true;
                                Log.e("SIM筛选2", "5极小值符合要求  " + " /min5Pos = " + min5Pos);
                            } else {
                                Log.e("SIM筛选2", "5极小值不符合要求" + " /min5Pos = " + min5Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "5未找到极小值");
                        }

                    }

                    if (overlapNum[l] == 6) {
                        int i6 = pulseRemove[rangeState] + 5;
                        int minNum6 = 0;
                        int[] minData6 = new int[65560];
                        int[] minDataPos6 = new int[65560];
                        while ((i6 >= pulseRemove[rangeState] + 5) && (i6 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray6[i6] < simArray6[i6 - 1]) && (simArray6[i6] <= simArray6[i6 + 1])) {
                                if (simArray6[i6 - 1] <= simArray6[i6 - 2]) {
                                    if (simArray6[i6 - 2] <= simArray6[i6 - 3]) {
                                        if (simArray6[i6 - 3] <= simArray6[i6 - 4]) {
                                            if (simArray6[i6 - 4] <= simArray6[i6 - 5]) {
                                                minData6[minNum6] = simArray6[i6];
                                                minDataPos6[minNum6] = i6;
                                                minNum6++;
                                            }
                                        }
                                    }
                                }
                            }
                            i6++;
                        }
                        if (minNum6 >= 1) {
                            int min6 = minData6[0];
                            for (int k6 = 0; k6 < minNum6; k6++) {
                                if (minData6[k6] <= min6) {
                                    min6 = minData6[k6];
                                    min6Pos = minDataPos6[k6];
                                }
                            }
                            if (min6Pos < maxPos + 60) {
                                selectSim6 = true;
                                Log.e("SIM筛选2", "6极小值符合要求  " + " /min6Pos" + min6Pos);
                            } else {
                                Log.e("SIM筛选2", "6极小值不符合要求" + " /min6Pos = " + min6Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "6未找到极小值");
                        }
                    }

                    if (overlapNum[l] == 7) {
                        int i7 = pulseRemove[rangeState] + 5;
                        int minNum7 = 0;
                        int[] minData7 = new int[65560];
                        int[] minDataPos7 = new int[65560];
                        while ((i7 >= pulseRemove[rangeState] + 5) && (i7 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray7[i7] < simArray7[i7 - 1]) && (simArray7[i7] <= simArray7[i7 + 1])) {
                                if (simArray7[i7 - 1] <= simArray7[i7 - 2]) {
                                    if (simArray7[i7 - 2] <= simArray7[i7 - 3]) {
                                        if (simArray7[i7 - 3] <= simArray7[i7 - 4]) {
                                            if (simArray7[i7 - 4] <= simArray7[i7 - 5]) {
                                                minData7[minNum7] = simArray7[i7];
                                                minDataPos7[minNum7] = i7;
                                                minNum7++;
                                            }
                                        }
                                    }
                                }
                            }
                            i7++;
                        }
                        if (minNum7 >= 1) {
                            int min7 = minData7[0];
                            for (int k7 = 0; k7 < minNum7; k7++) {
                                if (minData7[k7] <= min7) {
                                    min7 = minData7[k7];
                                    min7Pos = minDataPos7[k7];
                                }
                            }
                            if (min7Pos < maxPos + 60) {
                                selectSim7 = true;
                                Log.e("SIM筛选2", "7极小值符合要求  " + " /min7Pos = " + min7Pos);
                            } else {
                                Log.e("SIM筛选2", "7极小值不符合要求" + " /min7Pos = " + min7Pos);
                            }
                        } else {
                            Log.e("SIM筛选2", "7未找到极小值");
                        }
                    }

                    if (overlapNum[l] == 8) {
                        int i8 = pulseRemove[rangeState] + 5;
                        int minNum8 = 0;
                        int[] minData8 = new int[65560];
                        int[] minDataPos8 = new int[65560];
                        while ((i8 >= pulseRemove[rangeState] + 5) && (i8 < dataMax - removeTdrSim[rangeState])) {
                            if ((simArray8[i8] < simArray8[i8 - 1]) && (simArray8[i8] <= simArray8[i8 + 1])) {
                                if ((i8 > pulseRemove[rangeState] + 5) && (simArray8[i8 - 1] <= simArray8[i8 - 2])) {
                                    if ((simArray8[i8 - 2] <= simArray8[i8 - 3])) {
                                        if ((simArray8[i8 - 3] <= simArray8[i8 - 4])) {
                                            if ((simArray8[i8 - 4] < simArray8[i8 - 5])) {
                                                minData8[minNum8] = simArray8[i8];
                                                minDataPos8[minNum8] = i8;
                                                minNum8++;
                                            }
                                        }
                                    }
                                }
                            }
                            i8++;
                        }
                        if (minNum8 >= 1) {
                            int min8 = minData8[0];
                            for (int k8 = 0; k8 < minNum8; k8++) {
                                if (minData8[k8] <= min8) {
                                    min8 = minData8[k8];
                                    min8Pos = minDataPos8[k8];
                                }
                            }
                            if (min8Pos < maxPos + 60) {
                                selectSim8 = true;
                                Log.e("SIM筛选2", "8极小值符合要求  " + " /min8Pos = " + min8Pos + " /maxPos = " + maxPos);
                            } else {
                                Log.e("SIM筛选2", "8极小值不符合要求" + " /min8Pos = " + min8Pos + " /maxPos = " + maxPos);
                            }
                        } else {
                            Log.e("SIM筛选2", "8未找到极小值");
                        }
                    }
                }
            }
        }
        //筛选3.波形做相关，给出最终结果
        simRelevantJudgment();

        //jk20200804  光标自动定位
        pointDistance = sim_point;
        zero = simOriginalZero;
        //Log.e("SIMc2", "min1_pos"+min1Pos);
        Log.e("SIMc2", "sim_u" + sim_u);
        Log.e("SIMc2", "sim_g" + sim_g);
        Log.e("SIMc2", "pointDistance" + sim_point);
        if (range == RANGE_250) {
            zero = simOriginalZero * 2;
            pointDistance = sim_point * 2;
        }
        if (zero >= (currentMoverPosition510 * dataLength / 510) && zero <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionReal = (zero - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineReal(positionReal);
        } else {
            mainWave.setScrubLineRealDisappear();
        }


        //重新定位虚光标
       /* if (sim_point >= (currentMoverPosition510 * dataLength / 510) && sim_point <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionVirtual = (sim_point - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineVirtual(positionVirtual);
        } else {
            mainWave.setScrubLineVirtualDisappear();
        }*/
        //重新定位虚光标    //jk20200826
        if (pointDistance >= (currentMoverPosition510 * dataLength / 510) && pointDistance <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionVirtual = (pointDistance - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineVirtual(positionVirtual);
        } else {
            mainWave.setScrubLineVirtualDisappear();
        }
        calculateDistance(Math.abs(pointDistance - zero));

    }

    /**
     * 二次脉冲方式增益自动判断
     */
    private void gainJudgmentSim() {
        int i;
        int max = 0;
        int sub;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            sub = waveArray[i] - Median_value;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 29) {  //jk20201130  //max<=41
            //判断增益过小——如果最大值小于 15% 38
            gainState = 2;
            return;
        }
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            if ((waveArray[i] > 242) || (waveArray[i] < 20)) {
//            if ((waveArray[i] > 242) || (waveArray[i] < 13)) {    //A20200527  SIM增益大小判断微调
                //判断增益过大
                gainState = 1;
                return;
            }
        }
    }

    /**
     * 相关计算
     */
    int n, n1, n2, n3, n4, n5, n6, n7, n8;

    public void simRelevantJudgment() {
        simFilter();
        int selectWaveNum = 1;
        double r, r1;
        double rMax = 0.0;

        n = dataMax - removeTdrSim[rangeState];

        if (selectSim1) {
            n1 = min1Pos;
            //负脉冲起始点
           /* while (n1 > 1) {
                if (simArray1[n1] <= simArray1[n1-1]) {
                    n1 = n1 - 1 ;
                } else {
                    break;
                }
            }*/
            while (n1 > 1) {
                if (simArray1[n1] <= simArray1[n1 - 1]) {
                    // if (simArray1[n1] != simArray1[n1-2]) {
                    if ((Median_value - simArray1[n1]) >= 0) {
                        n1 = n1 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            //计算相关系数
//            r = correlationCalculation(waveArray, simArray1, n1);
            r = correlationCalculation(simArray0Filter, simArray1Filter, n1);
            r1 = correlationCalculation(simArray0Filter, simArray1Filter, n);
            Log.e("SIM筛选3", "1 相关系数 r1 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n1);
            //GC20200609
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 1;
                    Log.e("SIM筛选3", "最终选第1条");
                }
            }
        }

        if (selectSim2) {
            n2 = min2Pos;
            /*while (n2 > 1) {
                if (simArray2[n2] <= simArray2[n2-1]) {
                    n2 = n2 - 1 ;
                } else {
                    break;
                }
            }*/
            while (n2 > 1) {
                if (simArray2[n2] <= simArray2[n2 - 1]) {
                    //   if (simArray2[n2] != simArray2[n2-2]) {
                    if ((Median_value - simArray2[n2]) >= 0) {
                        n2 = n2 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray2Filter, n2);
            r1 = correlationCalculation(simArray0Filter, simArray2Filter, n);
            Log.e("SIM筛选3", "2 相关系数 r2 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n2);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 2;
                    Log.e("SIM筛选3", "最终选第2条");
                }
            }
        }

        if (selectSim3) {
            n3 = min3Pos;
           /*while (n3 > 1) {
                if (simArray3[n3] <= simArray3[n3-1]) {
                    n3 = n3 - 1 ;
                } else{
                    break;
                }
            }*/
            while (n3 > 1) {
                if (simArray3[n3] <= simArray3[n3 - 1]) {
                    //  if (simArray3[n3] != simArray3[n3-2]) {
                    if ((Median_value - simArray3[n3]) >= 0) {
                        n3 = n3 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray3Filter, n3);
            r1 = correlationCalculation(simArray0Filter, simArray3Filter, n);
            Log.e("SIM筛选3", "3 相关系数r3 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n3);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 3;
                    Log.e("SIM筛选3", "最终选第3条");
                }
            }
        }

        if (selectSim4) {
            n4 = min4pos;
            /*while (n4 > 1) {
                if (simArray4[n4] <= simArray4[n4 - 1]) {
                    n4 = n4 - 1;
                } else {
                    break;
                }
            }*/
            while (n4 > 1) {
                if (simArray4[n4] <= simArray4[n4 - 1]) {
                    //if (simArray4[n4] != simArray4[n4-2]) {
                    if ((Median_value - simArray4[n4]) >= 0) {
                        n4 = n4 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray4Filter, n4);
            r1 = correlationCalculation(simArray0Filter, simArray4Filter, n);
            Log.e("SIM筛选3", "4 相关系数 r4 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n4);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 4;
                    Log.e("SIM筛选3", "最终选第4条");
                }
            }
        }

        if (selectSim5) {
            n5 = min5Pos;
            /*while (n5 > 1) {
                if (simArray5[n5] <= simArray5[n5 - 1]) {
                    n5 = n5 - 1;
                } else {
                    break;
                }
            }*/
            while (n5 > 1) {
                if (simArray5[n5] <= simArray5[n5 - 1]) {
                    // if (simArray5[n5] != simArray5[n5-2]) {
                    if ((Median_value - simArray5[n5]) >= 0) {
                        n5 = n5 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray5Filter, n5);
            r1 = correlationCalculation(simArray0Filter, simArray5Filter, n);
            Log.e("SIM筛选3", "5 相关系数 r5 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n5);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 5;
                    Log.e("SIM筛选3", "最终选第5条");
                }
            }
        }

        if (selectSim6) {
            n6 = min6Pos;
            /*while (n6 > 1) {
                if (simArray6[n6] <= simArray6[n6 - 1]) {
                    n6 = n6 - 1;
                } else {
                    break;
                }
            }*/
            while (n6 > 1) {
                if (simArray6[n6] <= simArray6[n6 - 1]) {
                    //   if (simArray6[n6] != simArray6[n6-2]) {
                    if ((Median_value - simArray6[n6]) >= 0) {
                        n6 = n6 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray6Filter, n6);
            r1 = correlationCalculation(simArray0Filter, simArray6Filter, n);
            Log.e("SIM筛选3", "6 相关系数 r6 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n6);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 6;
                    Log.e("SIM筛选3", "最终选第6条");
                }
            }
        }

        if (selectSim7) {
            n7 = min7Pos;
            /*while (n7 > 1) {
                if (simArray7[n7] <= simArray7[n7 - 1]) {
                    n7 = n7 - 1;
                } else {
                    break;
                }
            }*/
            while (n7 > 1) {
                if (simArray7[n7] <= simArray7[n7 - 1]) {
                    //   if (simArray7[n7] != simArray7[n7-2]) {
                    if ((Median_value - simArray7[n7]) >= 0) {
                        n7 = n7 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray7Filter, n7);
            r1 = correlationCalculation(simArray0Filter, simArray7Filter, n);
            Log.e("SIM筛选3", "7 相关系数 r7 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n7);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 7;
                    Log.e("SIM筛选3", "最终选第7条");
                }
            }
        }

        if (selectSim8) {
            n8 = min8Pos;
           /* while (n8 > 1) {
                if (simArray8[n8] <= simArray8[n8 - 1]) {
                    n8 = n8 - 1;
                } else {
                    break;
                }
            }*/
            while (n8 > 1) {
                if (simArray8[n8] <= simArray8[n8 - 1]) {
                    // if (simArray8[n8] != simArray8[n8-2]) {
                    if ((Median_value - simArray8[n8]) >= 0) {
                        n8 = n8 - 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray8Filter, n8);
            r1 = correlationCalculation(simArray0Filter, simArray8Filter, n);
            Log.e("SIM筛选3", "8 相关系数 r8 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n8);
            if ((r - r1) > 0.1) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 8;
                    Log.e("SIM筛选3", "最终选第8条");
                }
            }
        }

        if (rMax > 0) {
            //有最优波形
            tvInformation.setVisibility(View.VISIBLE);
            tvInformation.setText("");
            //显示序号
            selectSim = selectWaveNum;
            setSelectSim(selectSim);

        } else {
            //没有最优波形
            tvInformation.setVisibility(View.VISIBLE);
            tvInformation.setText(getResources().getString(R.string.testAgain));
            //无相关显示波形1  //GC20200601
            selectSim = 1;
            setSelectSim(selectSim);
        }

        //光标定位  //jk20200804
        switch (selectWaveNum) {
            case 1:
                sim_g = min1Pos;
                sim_u = n1;
                simArray = simArray1;
                break;
            case 2:
                sim_g = min2Pos;
                sim_u = n2;
                simArray = simArray2;
                break;
            case 3:
                sim_g = min3Pos;
                sim_u = n3;
                simArray = simArray3;
                break;
            case 4:
                sim_g = min4pos;
                sim_u = n4;
                simArray = simArray4;
                break;
            case 5:
                sim_g = min5Pos;
                sim_u = n5;
                simArray = simArray5;
                break;
            case 6:
                sim_g = min6Pos;
                sim_u = n6;
                simArray = simArray6;
                break;
            case 7:
                sim_g = min7Pos;
                sim_u = n7;
                simArray = simArray7;
                break;
            case 8:
                sim_g = min8Pos;
                sim_u = n8;
                simArray = simArray8;
                break;
            default:
                break;
        }
        if (sim_u < 0) {
            sim_u = 0;
        }
        Log.e("SIM", "sim_u" + sim_u);
        Log.e("SIM", "sim_g" + sim_g);
        int[] simArray1_8 = new int[60050];
        for (int i = sim_u; i < sim_g; i++) {
            //133需要更改
            // simArray1_8[i] = simArray[i] - 133;
            //simArray1_8[i] = simArray[i] - 130;   //jk20200908
            simArray1_8[i] = simArray[i] - Median_value;
        }

        double[] X = new double[1000];
        double[] Y = new double[1000];
        double[] atemp = new double[8];
        double[] b = new double[4];
        double[][] a = new double[4][4];

        for (int h = sim_u; h < sim_g; h++) {
            X[h - sim_u] = h - sim_u;
            Y[h - sim_u] = simArray1_8[h];
        }
        for (int i = 0; i < sim_g - sim_u; i++) {
            atemp[1] += X[i];
            atemp[2] += Math.pow(X[i], 2);
            atemp[3] += Math.pow(X[i], 3);
            atemp[4] += Math.pow(X[i], 4);
            atemp[5] += Math.pow(X[i], 5);
            atemp[6] += Math.pow(X[i], 6);
            b[0] += Y[i];
            b[1] += X[i] * Y[i];
            b[2] += Math.pow(X[i], 2) * Y[i];
            b[3] += Math.pow(X[i], 3) * Y[i];
        }

        atemp[0] = sim_g - sim_u;

        for (int i1 = 0; i1 < 4; i1++) {
            int k = i1;
            for (int j = 0; j < 4; j++) {
                a[i1][j] = atemp[k++];
            }
        }

        for (int k = 0; k < 3; k++) {
            int column = k;
            double mainelement = a[k][k];
            for (int i2 = k; i2 < 4; i2++) {
                if (Math.abs((a[i2][k])) > mainelement) {
                    mainelement = Math.abs((a[i2][k]));
                    column = i2;
                }
            }

            for (int j = k; j < 4; j++) {
                double atemp_1 = a[k][j];
                a[k][j] = a[column][j];
                a[column][j] = atemp_1;
            }

            double btemp = b[k];
            b[k] = b[column];
            b[column] = btemp;

            for (int i3 = k + 1; i3 < 4; i3++) {
                double Mik = a[i3][k] / a[k][k];
                for (int j = k; j < 4; j++) {
                    a[i3][j] -= Mik * a[k][j];
                }
                b[i3] -= Mik * b[k];

            }
        }

        b[3] /= a[3][3];

        for (int i = 2; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < 4; j++) {
                sum += a[i][j] * b[j];
            }
            b[i] = (b[i] - sum) / a[i][i];
        }
        int sim_point8 = 0;
        //求出曲线拟合后求解纵坐标值为0时横坐标的结果  一元三次方程求解  //jk20210527
        sim_point8 = equationSolving(b[3], b[2], b[1], b[0], -5, 5) + sim_u;
       /* double[] sim_c1 = new double[1000];

        for(int x=0;x<sim_g-sim_u;x++)  {
            sim_c1[x]=b[3]*x*x*x+b[2]*x*x+b[1]*x+b[0];
        }
        for(int i=0; i<sim_g-sim_u;i++){
            if ((((sim_c1[i]) <= 0) && ((sim_c1[i + 1]) > 0)) || ((sim_c1[i]) >= 0) && ((sim_c1[i + 1]) < 0)){
               // int x = waveArray[i] - 134 - simArray1_8[i];
                //GC20200817    断线二次脉冲处理
              //  if (x <= 10) {
                    sim_point8 = i + sim_u + 1;
                    Log.e("SIMc2", " /i = " + i);
            //   }
            }else{
                for (int f = 0; f < g - u - 1; f++) {
                    if ((((sim_c1[f]) <= 3) && ((sim_c1[f + 1]) > 3)) || (((sim_c1[f]) >= 3) && ((sim_c1[f + 1]) < 3))) {
                       // int x = waveArray[i] - 134 - simArray1_8[i];
                    //   if (x <= 10) {
                            int z1 = f;
                            sim_point8 = z1 + sim_u + 1;
                            Log.e("SIMc2", " /z1 = " + z1);
                      //  }
                    }
                }
            }
        }*/
        if (sim_point8 <= 0) {            //jk20200820 对于结果为0的补充
            sim_point8 = sim_u;
        }
        sim_point = sim_point8;

        //清标志位
        selectSim1 = false;
        selectSim2 = false;
        selectSim3 = false;
        selectSim4 = false;
        selectSim5 = false;
        selectSim6 = false;
        selectSim7 = false;
        selectSim8 = false;

    }

    /*
     **  求解一元三次方程  //jk20210527
     */
    public int equationSolving(double a, double b, double c, double d, int x1, int x2) {
        double fx1, fx2, fx0;
        int x0;
        int num = 0;

        //需满足f(x1)与f(x2)异号，且x1 < x2
        fx1 = ((a * x1 + b) * x1 + c) * x1 + d;
        fx2 = ((a * x2 + b) * x2 + c) * x2 + d;
        if (fx1 * fx2 >= 0) {
            //缩小取值范围
            x1 = x1 / 2;
            x2 = x2 / 2;
            fx1 = ((a * x1 + b) * x1 + c) * x1 + d;
            fx2 = ((a * x2 + b) * x2 + c) * x2 + d;
            if (fx1 * fx2 > 0) {
                x1 = x1 / 2;
                x2 = x2 / 2;
                fx1 = ((a * x1 + b) * x1 + c) * x1 + d;
                fx2 = ((a * x2 + b) * x2 + c) * x2 + d;
                if (fx1 * fx2 > 0) {
                    x0 = 0;
                } else {
                    do {
                        x0 = (x1 + x2) / 2;
                        fx0 = ((a * x0 + b) * x0 + c) * x0 + d;
                        num++;

                        if (num < 10) {
                            if ((fx0 * fx1) < 0) {
                                x2 = x0;
                                fx2 = fx0;
                            } else {
                                x1 = x0;
                                fx1 = fx0;
                            }
                        } else {
                            break;
                        }
                    } while (Math.abs(fx0) >= 0.1);//求根精度
                }
            } else {
                do {
                    x0 = (x1 + x2) / 2;
                    fx0 = ((a * x0 + b) * x0 + c) * x0 + d;
                    num++;

                    if (num < 10) {
                        if ((fx0 * fx1) < 0) {
                            x2 = x0;
                            fx2 = fx0;
                        } else {
                            x1 = x0;
                            fx1 = fx0;
                        }
                    } else {
                        break;
                    }
                } while (Math.abs(fx0) >= 0.1);//求根精度
            }
        } else {
            do {
                x0 = (x1 + x2) / 2;
                fx0 = ((a * x0 + b) * x0 + c) * x0 + d;
                num++;

                if (num < 10) {
                    if ((fx0 * fx1) < 0) {
                        x2 = x0;
                        fx2 = fx0;
                    } else {
                        x1 = x0;
                        fx1 = fx0;
                    }
                } else {
                    break;
                }
            } while (Math.abs(fx0) >= 0.1);//求根精度

        }
        return x0;
    }

    /**
     * 一阶高通数字滤波（得到用作相关计算的数组）
     * k=0.9987;
     * k=0.9139  1.5M
     * k=0.9409  1M
     */
    public void simFilter() {
        double k = 0.9139;
        simArray0Filter[0] = (double) (waveArray[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray0Filter[i] = k * simArray0Filter[i - 1] + k * (double) (waveArray[i] - waveArray[i - 1]);
        }
        simArray1Filter[0] = (double) (simArray1[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray1Filter[i] = k * simArray1Filter[i - 1] + k * (double) (simArray1[i] - simArray1[i - 1]);
        }
        simArray2Filter[0] = (double) (simArray2[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray2Filter[i] = k * simArray2Filter[i - 1] + k * (double) (simArray2[i] - simArray2[i - 1]);
        }
        simArray3Filter[0] = (double) (simArray3[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray3Filter[i] = k * simArray3Filter[i - 1] + k * (double) (simArray3[i] - simArray3[i - 1]);
        }
        simArray4Filter[0] = (double) (simArray4[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray4Filter[i] = k * simArray4Filter[i - 1] + k * (double) (simArray4[i] - simArray4[i - 1]);
        }
        simArray5Filter[0] = (double) (simArray5[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray5Filter[i] = k * simArray5Filter[i - 1] + k * (double) (simArray5[i] - simArray5[i - 1]);
        }
        simArray6Filter[0] = (double) (simArray6[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray6Filter[i] = k * simArray6Filter[i - 1] + k * (double) (simArray6[i] - simArray6[i - 1]);
        }
        simArray7Filter[0] = (double) (simArray7[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray7Filter[i] = k * simArray7Filter[i - 1] + k * (double) (simArray7[i] - simArray7[i - 1]);
        }
        simArray8Filter[0] = (double) (simArray8[0] - 133);
        for (int i = 1; i < dataMax; i++) {
            simArray8Filter[i] = k * simArray8Filter[i - 1] + k * (double) (simArray8[i] - simArray8[i - 1]);
        }

    }

    /**
     * 计算相关系数
     */
    public double correlationCalculation(double[] a, double[] b, int n) {
        double d1, d2, d3;
        double mx, my;
        int i;
        d1 = d2 = d3 = mx = my = 0.0;

        for (i = 0; i < n; i++) {
            mx += a[i];
            my += b[i];
        }
        mx = mx / n;
        my = my / n;

        //计算相关系数的数据组成部分
        for (i = 0; i < n; i++) {
            d1 += (a[i] - mx) * (b[i] - my);
            d2 += (a[i] - mx) * (a[i] - mx);
            d3 += (b[i] - my) * (b[i] - my);
        }
//        Log.e("SIM筛选3", " /d1 = " + d1 + " /d2 = " + d2 + " /d3 = " + d3);
        d2 = Math.sqrt(d2 * d3);
        if (d2 == 0) {
            d1 = -1;
        } else {
            d1 = d1 / d2;
        }
        return d1;

    }

    /**
     * 打开数据库波形需要设置的参数  //GC20190713
     */
    public void setDateBaseParameter() {
        //显示数据库波形状态
        isDatabase = true;
        //设置数据库中的测试参数（网络正常时，打开记录应下发）
        setModeNoCmd(Constant.Para[0]);
        setRangeNoCmd(Constant.Para[1]);
        setGain(Constant.Para[2]);
        setVelocityNoCmd(Constant.Para[3]);
        Constant.ModeValue = Constant.Para[0];
        Constant.RangeValue = Constant.Para[1];
        Constant.Gain = Constant.Para[2];
        Constant.Velocity = Constant.Para[3];
        //实光标、虚光标、比例
        zero = Constant.PositionR;
        pointDistance = Constant.PositonV;
        positionVirtual = pointDistance / densityMax;
        positionReal = zero / densityMax;
        mainWave.setScrubLineVirtual(positionVirtual);
        mainWave.setScrubLineReal(positionReal);
        Constant.DensityMax = densityMax;
        //显示故障距离   //20200522  单位转化逻辑修正
        Constant.CurrentLocation = Constant.SaveLocation;
        if (CurrentUnit == MI_UNIT) {
            tvDistance.setText(new DecimalFormat("0.00").format(Constant.SaveLocation));
        } else {
            tvDistance.setText(UnitUtils.miToFt(Constant.SaveLocation));
        }
        //擦除比较波形
        isCom = false;
        //需要绘制的波形原始数组初始化
        if (mode == TDR) {
            dataMax = READ_TDR_SIM[rangeState];
        } else if ((mode == ICM) || (mode == ICM_DECAY) || (mode == DECAY)) {
            dataMax = READ_ICM_DECAY[rangeState];
        } else if (mode == SIM) {
            dataMax = READ_TDR_SIM[rangeState];
            //利用比较功能绘制SIM的第二条波形数据
            isCom = true;
        }
        initMode();
    }

    /**
     * @param mode 需要发送的方式控制命令值 / 响应信息栏方式变化
     */

    public void setMode(int mode) {
        this.mode = mode;
        command = COMMAND_MODE;
        dataTransfer = mode;

        switch (mode) {
            case TDR:
                tvMode.setText(getResources().getString(R.string.btn_tdr));
                //高压操作按钮和信息栏隐藏  //GC20211207
                llAUTO.setVisibility(View.GONE);
                llInfoHv.setVisibility(View.GONE);

                //GC20190709
                initTDRView();//jk20210125
                switchDensity();
                initCursor();
                /*调节栏显示*/
                adjustFragment.btnBalancePlus.setVisibility(View.VISIBLE);
                adjustFragment.btnBalanceMinus.setVisibility(View.VISIBLE);
                adjustFragment.btnDelayPlus.setVisibility(View.GONE);
                adjustFragment.btnDelayMinus.setVisibility(View.GONE);
                //操作栏fragment显示
                waveFragment.btnMemory.setVisibility(View.VISIBLE);
                waveFragment.btnCompare.setVisibility(View.VISIBLE);
                waveFragment.btnPulse.setVisibility(View.VISIBLE);
                waveFragment.btnZero.setVisibility(View.GONE);
                waveFragment.btnWavePrevious.setVisibility(View.GONE);
                waveFragment.btnWaveNext.setVisibility(View.GONE);
                waveFragment.btnLead1.setVisibility(View.GONE);

                settingFragment.btnZero.setVisibility(View.GONE);
                settingFragment.btnPulse.setVisibility(View.VISIBLE);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case ICM:
                tvMode.setText(getResources().getString(R.string.btn_icm));
                //GC20211207
                llAUTO.setVisibility(View.VISIBLE);
                llInfoHv.setVisibility(View.VISIBLE);

                initSparkView();//jk20210130changshi
                initICMSURGEView();
                switchDensity();
                initCursor();
                //  tvInductor.setVisibility(View.VISIBLE);
                //  vlInductor.setVisibility(View.VISIBLE);
                adjustFragment.btnBalancePlus.setVisibility(View.GONE);
                adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
                adjustFragment.btnDelayPlus.setVisibility(View.VISIBLE);
                adjustFragment.btnDelayMinus.setVisibility(View.VISIBLE);
                // adjustFragment.btnInductorPlus.setVisibility(View.VISIBLE);
                // adjustFragment.btnInductorMinus.setVisibility(View.VISIBLE);
                waveFragment.btnMemory.setVisibility(View.VISIBLE);
                waveFragment.btnCompare.setVisibility(View.VISIBLE);
                waveFragment.btnWavePrevious.setVisibility(View.GONE);
                waveFragment.btnWaveNext.setVisibility(View.GONE);
                waveFragment.btnLead1.setVisibility(View.VISIBLE);
                settingFragment.btnZero.setVisibility(View.GONE);
                settingFragment.btnPulse.setVisibility(View.GONE);
                waveFragment.btnZero.setVisibility(View.GONE);
                waveFragment.btnPulse.setVisibility(View.GONE);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                alreadyDisplayWave = false; //jk20210130
                //mainWave.setAdapter(myChartAdapterMainWave);  //jk20210130changshi
                tvInformation.setText("");  //20210130
                break;
            case ICM_DECAY:
                tvMode.setText(getResources().getString(R.string.btn_icm_decay));
                //GC20211207
                llAUTO.setVisibility(View.VISIBLE);
                llInfoHv.setVisibility(View.VISIBLE);

                initSparkView();//jk20210130changshi
                initICMDECAYView();
                switchDensity();
                initCursor();
                adjustFragment.btnBalancePlus.setVisibility(View.GONE);
                adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
                waveFragment.btnMemory.setVisibility(View.VISIBLE);
                waveFragment.btnCompare.setVisibility(View.VISIBLE);
                waveFragment.btnWavePrevious.setVisibility(View.GONE);
                waveFragment.btnWaveNext.setVisibility(View.GONE);
                waveFragment.btnLead1.setVisibility(View.VISIBLE);
                settingFragment.btnZero.setVisibility(View.GONE);
                settingFragment.btnPulse.setVisibility(View.GONE);
                waveFragment.btnZero.setVisibility(View.GONE);
                waveFragment.btnPulse.setVisibility(View.GONE);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                alreadyDisplayWave = false; //jk20210130
                tvInformation.setText("");  //20210130
                break;
            case SIM:
                tvMode.setText(getResources().getString(R.string.btn_sim));
                //GC20211207
                llAUTO.setVisibility(View.VISIBLE);
                llInfoHv.setVisibility(View.VISIBLE);


                initSparkView();//jk20210130changshi
                initSIMView();
                switchDensity();
                initCursor();
                adjustFragment.btnBalancePlus.setVisibility(View.GONE);
                adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
                adjustFragment.btnDelayPlus.setVisibility(View.GONE);
                adjustFragment.btnDelayMinus.setVisibility(View.GONE);
                adjustFragment.btnDelayPlus.setVisibility(View.VISIBLE);
                adjustFragment.btnDelayMinus.setVisibility(View.VISIBLE);
                //adjustFragment.btnInductorPlus.setVisibility(View.GONE);
                //adjustFragment.btnInductorMinus.setVisibility(View.GONE);
                waveFragment.btnMemory.setVisibility(View.GONE);
                waveFragment.btnCompare.setVisibility(View.GONE);
                waveFragment.btnWavePrevious.setVisibility(View.VISIBLE);
                waveFragment.btnWaveNext.setVisibility(View.VISIBLE);
                waveFragment.btnLead1.setVisibility(View.GONE);
                settingFragment.btnZero.setVisibility(View.VISIBLE);
                settingFragment.btnPulse.setVisibility(View.GONE);
                waveFragment.btnZero.setVisibility(View.VISIBLE);
                waveFragment.btnPulse.setVisibility(View.GONE);
                //waveFragment.btnWaveNext.setEnabled(false);
                //waveFragment.btnWavePrevious.setEnabled(false);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                alreadyDisplayWave = false; //jk20210130
                tvInformation.setText("");  //20210130
                break;
            case DECAY:
                tvMode.setText(getResources().getString(R.string.btn_decay));
                //GC20211207
                llAUTO.setVisibility(View.VISIBLE);
                llInfoHv.setVisibility(View.VISIBLE);

                initSparkView();//jk20210130changshi
                initDecayView();
                switchDensity();
                initCursor();

                adjustFragment.btnBalancePlus.setVisibility(View.GONE);
                adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
                adjustFragment.btnDelayPlus.setVisibility(View.GONE);
                adjustFragment.btnDelayMinus.setVisibility(View.GONE);
                //adjustFragment.btnInductorPlus.setVisibility(View.GONE);
                //adjustFragment.btnInductorMinus.setVisibility(View.GONE);
                waveFragment.btnMemory.setVisibility(View.VISIBLE);
                waveFragment.btnCompare.setVisibility(View.VISIBLE);
                waveFragment.btnWavePrevious.setVisibility(View.GONE);
                waveFragment.btnWaveNext.setVisibility(View.GONE);
                waveFragment.btnLead1.setVisibility(View.GONE);
                settingFragment.btnZero.setVisibility(View.GONE);
                settingFragment.btnPulse.setVisibility(View.GONE);
                waveFragment.btnZero.setVisibility(View.GONE);
                waveFragment.btnPulse.setVisibility(View.GONE);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                alreadyDisplayWave = false; //jk20210130
                tvInformation.setText("");  //20210130
                break;
            default:
                break;
        }
        startService();
    }

    public void setModeNoCmd(int mode) {
        this.mode = mode;
        command = COMMAND_MODE;
        dataTransfer = mode;
        startService();
        switch (mode) {
            case TDR:
                tvMode.setText(getResources().getString(R.string.btn_tdr));
                //GC20190709
                switchDensity();
                break;
            case ICM:
                switchDensity();
                tvMode.setText(getResources().getString(R.string.btn_icm));
                break;
            case ICM_DECAY:
                switchDensity();
                tvMode.setText(getResources().getString(R.string.btn_icm_decay));
                break;
            case SIM:
                switchDensity();
                tvMode.setText(getResources().getString(R.string.btn_sim));
                break;
            case DECAY:
                switchDensity();
                tvMode.setText(getResources().getString(R.string.btn_decay));
                break;
            default:
                break;
        }
    }

    /**
     * 比例选择 //GC20190709
     */
    private void switchDensity() {
        if ((mode == TDR) || (mode == SIM)) {
            densityMax = densityMaxTdrSim[rangeState];
        } else if ((mode == ICM) || (mode == ICM_DECAY) || (mode == DECAY)) {
            densityMax = densityMaxIcmDecay[rangeState];
            //比例显示  //GC20191223
            if (range == RANGE_250) {
                densityMax = densityMax / 2;
            }
        }
        density = densityMax;
        tvZoomValue.setText("1 : " + density);
        density = getDensity();

        //默认显示滚动条
        tvZoomMin.setEnabled(true);
        waveFragment.btnZoomOut.setEnabled(true);//jk20210126
        llHorizontalView.setVisibility(View.VISIBLE);

        tvZoomPlus.setEnabled(true);
        tvZoomMin.setEnabled(true);
        waveFragment.btnZoomIn.setEnabled(true);//jk20210126
        waveFragment.btnZoomOut.setEnabled(true);//jk20210126
        //jk20210130cs hai
        //设置滑动块的宽度
        setHorizontalMoveViewOnlyHeight();
    }

    /**
     * 设置滑动块的宽度
     */
    private void setHorizontalMoveView() {
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(rlWave.getWidth() / density, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mvWave.getParentWidth() * 510 * density / dataLength, getResources().getDimensionPixelSize(R.dimen.dp_20));
        mvWave.setLayoutParams(layoutParams);
    }

    private void setHorizontalMoveViewOnlyHeight() {
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(rlWave.getWidth() / density, 30);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mvWave.getWidth(), getResources().getDimensionPixelSize(R.dimen.dp_20));
        mvWave.setLayoutParams(layoutParams);
    }

    /**
     * 设置滑动块的宽度
     */
    private void setHorizontalMoveViewPosition(int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mvWave.getLayoutParams();
        layoutParams.leftMargin = fenzi2 - mvWave.getWidth() / 2;
        mvWave.setLayoutParams(layoutParams);
    }

    private void setMoverPosition(int position) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mvWave.getLayoutParams();
        layoutParams.leftMargin = position;
        mvWave.setLayoutParams(layoutParams);
    }

    /**
     * 光标位置和距离显示初始化 //GC20190709
     */
    private void initCursor() {
        int zero2 = 0;
        //光标距离
        if (mode == SIM) {
            //GC20190712
            zero = simOriginalZero;
            if (range == RANGE_250) {
                zero = zero * 2;
            }
            //GC20200330
            zero2 = simStandardZero;
            if (range == RANGE_250) {
                zero2 = simStandardZero * 2;
            }
        } else {
            zero = 0;
        }
        pointDistance = 255 * densityMax;
        //计算并在界面显示距离
        calculateDistance(Math.abs(pointDistance - zero));
       /* if(mode == ICM || mode == ICM_DECAY) {
            calculateDistance(Math.abs(pointDistance - zero));
        }
        if(mode == TDR){
            calculateDistance(Math.abs(autoLocation));
        }*/
        //界面定位
        positionReal = zero / densityMax;
        positionVirtual = pointDistance / densityMax;

        if (positionReal >= 0) {
            //G?? 条件是不是没用
            mainWave.setScrubLineReal(positionReal);
        }
        mainWave.setScrubLineVirtual(positionVirtual);
        //GC20200330
        if (mode == SIM) {
            positionSim = zero2 / densityMax;
            mainWave.setScrubLineSim(positionSim);
        } else {
            mainWave.setScrubLineSimDisappear();
        }
    }

    /**
     * @param range 需要发送的范围控制命令值 / 响应信息栏范围变化
     */
    public void setRange(int range) {
        //20200407
        if (allowSetRange == false) {
            return;
        }
        allowSetRange = false;
        this.range = range;

        switch (range) {
            case RANGE_250:
                range = 0x99;
                rangeState = 0;
                //GC20190709
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m));
                } else if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {  //jk20210129
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                gain = 13;
                //增益转为百分比   //GC20200313
                tvGainValue.setText("41");
                //handlerAuto.postDelayed(this::clickTest, 50);//jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_500:
                rangeState = 1;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {  //jk20210129
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                gain = 13;
                tvGainValue.setText("41");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_1_KM:
                rangeState = 2;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 80;
                        setPulseWidth(80);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(80));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                gain = 13;
                tvGainValue.setText("41");
                // handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_2_KM:
                rangeState = 3;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 160;
                        setPulseWidth(160);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(160));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 720;
                        setPulseWidth(720);
                    }, 20);
                }
                gain = 10;
                tvGainValue.setText("32");
                // handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_4_KM:
                rangeState = 4;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 320;
                        setPulseWidth(320);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(320));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                }
                gain = 10;
                tvGainValue.setText("32");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_8_KM:
                rangeState = 5;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 640;
                        setPulseWidth(640);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(640));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 3600;
                        setPulseWidth(3600);
                    }, 20);
                }
                gain = 10;
                tvGainValue.setText("32");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_16_KM:
                rangeState = 6;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 1280;
                        setPulseWidth(1280);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(1280));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 7120;
                        setPulseWidth(7120);
                    }, 20);
                }
                gain = 9;
                tvGainValue.setText("29");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_32_KM:
                rangeState = 7;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(2560));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                gain = 9;
                tvGainValue.setText("29");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            case RANGE_64_KM:
                rangeState = 8;
                switchDensity();
                initCursor();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 5120;
                        setPulseWidth(5120);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(5120));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                gain = 9;
                tvGainValue.setText("29");
                //handlerAuto.postDelayed(this::clickTest, 50); //jk20210125
                llAdjust.setVisibility(View.GONE); //jk20210125
                break;
            default:
                break;
        }
        //GC20200428
        selectWaveLength();

        //发送指令
        command = COMMAND_RANGE;
        dataTransfer = range;
        startService();
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    public void setRangeNoCmd(int range) {
        this.range = range;
        switch (range) {
            case RANGE_250:
                rangeState = 0;
                switchDensity();
                //GC20190709
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m));
                } else if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {  //jk20210129
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(false);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m1); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_500:
                rangeState = 1;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {  //jk20210129
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(false);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m1); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_1_KM:
                rangeState = 2;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 80;
                        setPulseWidth(80);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(80));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(false);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1km1); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_2_KM:
                rangeState = 3;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 160;
                        setPulseWidth(160);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(160));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 720;
                        setPulseWidth(720);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(false);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2km); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_4_KM:
                rangeState = 4;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 320;
                        setPulseWidth(320);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(320));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(false);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4km); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_8_KM:
                rangeState = 5;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 640;
                        setPulseWidth(640);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(640));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 3600;
                        setPulseWidth(3600);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(false);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8km); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_16_KM:
                rangeState = 6;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 1280;
                        setPulseWidth(1280);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(1280));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 7120;
                        setPulseWidth(7120);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(false);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16km); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_32_KM:
                rangeState = 7;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(2560));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(false);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32km); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k); //jk20210129
                break;
            case RANGE_64_KM:
                rangeState = 8;
                switchDensity();
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 5120;
                        setPulseWidth(5120);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(5120));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(false);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m); //jk20210129
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m); //jk20210129
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k); //jk20210129
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k); //jk20210129
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k); //jk20210129
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k); //jk20210129
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k); //jk20210129
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k); //jk20210129
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64km); //jk20210129
                break;
            default:
                break;
        }
        //GC20200428
        selectWaveLength();
        command = COMMAND_RANGE;
        //TODO 选择250，发送500命令，显示距离250
        if (range == RANGE_250) {
            dataTransfer = 0x11;
        } else {
            dataTransfer = range;
        }
        startService();

    }

    /**
     * @param gain 需要发送的增益控制命令值 / 响应信息栏增益变化
     */
    public void setGain(int gain) {
        this.gain = gain;
        Constant.Gain = gain;
        command = COMMAND_GAIN;
        dataTransfer = gain;
        //增益按钮状态变化（包含数据库打开）    //GC20200604
        if (gain == 31) {
            tvGainAdd.setEnabled(false);
            adjustFragment.btnGainPlus.setEnabled(false);
            tvGainMin.setEnabled(true);
            adjustFragment.btnGainMinus.setEnabled(true);
            if (mode == SIM) {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_false);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_1);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_s_selector);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
            } else {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_false);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_1);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_selector);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
            }
            gainButtonChanged = true;
        } else if (gain == 0) {
            tvGainAdd.setEnabled(true);
            adjustFragment.btnGainPlus.setEnabled(true);
            tvGainMin.setEnabled(false);
            adjustFragment.btnGainMinus.setEnabled(false);
            if (mode == SIM) {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_selector);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_s_false);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_2);//jk20210130
            } else {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_selector);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_false);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_2);//jk20210130
            }
            gainButtonChanged = true;
        } else {
            if (gainButtonChanged) {
                tvGainAdd.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                if (mode == SIM) {
                    tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_selector);
                    adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                    tvGainMin.setImageResource(R.drawable.bg_gain_min_s_selector);
                    adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
                } else {
                    tvGainAdd.setImageResource(R.drawable.bg_gain_plus_selector);
                    adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                    tvGainMin.setImageResource(R.drawable.bg_gain_min_selector);
                    adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
                }
                gainButtonChanged = false;
            }
        }
        //增益转为百分比   //GC20200313
        int temp = s2b(gain);
        tvGainValue.setText(String.valueOf(temp));
        startService();
    }

    public void setGain1(int gain) {
        this.gain = gain;
        Constant.Gain = gain;
        command = COMMAND_GAIN;
        dataTransfer = gain;
        //增益按钮状态变化（包含数据库打开）    //GC20200604
        if (gain == 31) {
            tvGainAdd.setEnabled(false);
            adjustFragment.btnGainPlus.setEnabled(false);
            tvGainMin.setEnabled(true);
            adjustFragment.btnGainMinus.setEnabled(true);
            if (mode == SIM) {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_false);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_1);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_s_selector);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
            } else {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_false);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_1);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_selector);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
            }
            gainButtonChanged = true;
        } else if (gain == 0) {
            tvGainAdd.setEnabled(true);
            adjustFragment.btnGainPlus.setEnabled(true);
            tvGainMin.setEnabled(false);
            adjustFragment.btnGainMinus.setEnabled(false);
            if (mode == SIM) {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_selector);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_s_false);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_2);//jk20210130
            } else {
                tvGainAdd.setImageResource(R.drawable.bg_gain_plus_selector);
                adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                tvGainMin.setImageResource(R.drawable.bg_gain_min_false);
                adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_2);//jk20210130
            }
            gainButtonChanged = true;
        } else {
            if (gainButtonChanged) {
                tvGainAdd.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                if (mode == SIM) {
                    tvGainAdd.setImageResource(R.drawable.bg_gain_plus_s_selector);
                    adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                    tvGainMin.setImageResource(R.drawable.bg_gain_min_s_selector);
                    adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
                } else {
                    tvGainAdd.setImageResource(R.drawable.bg_gain_plus_selector);
                    adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);  //jk20210130
                    tvGainMin.setImageResource(R.drawable.bg_gain_min_selector);
                    adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);//jk20210130
                }
                gainButtonChanged = false;
            }
        }
        //增益转为百分比   //GC20200313
        int temp = s2b(gain);
        tvGainValue.setText(String.valueOf(temp));
        handler.postDelayed(ModeActivity.this::clickTest, 100);
        startService();

    }  //jk20210129

    /**
     * 增益转为百分比31转100    //GC20200313
     *
     * @param s
     * @return
     */
    public int s2b(int s) {
        int b;
        float v = (float) s / 31.0f;
        float v1 = v * 100;
        b = (int) v1;
        return b;
    }

    public void setGainNoCmd(int gain) {
        this.gain = gain;
        command = COMMAND_GAIN;
        dataTransfer = gain;
        tvGainValue.setText(String.valueOf(gain));
    }

    public int getGain() {
        return gain;
    }


    /**
     * @param balance 需要发送的平衡控制命令值 / 响应信息栏平衡变化
     */
    public void setBalance(int balance) {
        this.balance = balance;
        command = COMMAND_BALANCE;
        dataTransfer = balance;
        //平衡按钮状态变化    //GC20200604
        if (balance == 15) {
            tvBalancePlus.setEnabled(false);
            adjustFragment.btnBalancePlus.setEnabled(false); //jk20210130
            tvBalancePlus.setImageResource(R.drawable.bg_balance_plus_false);
            adjustFragment.btnBalancePlus.setImageResource(R.drawable.bg_balance_jia); //jk20210130
            balanceButtonChanged = true;
        } else if (balance == 0) {
            tvBalanceMin.setEnabled(false);
            adjustFragment.btnBalanceMinus.setEnabled(false); //jk20210130
            tvBalanceMin.setImageResource(R.drawable.bg_balance_min_false);
            adjustFragment.btnBalanceMinus.setImageResource(R.drawable.bg_balance_jian); //jk20210130
            balanceButtonChanged = true;
        } else {
            if (balanceButtonChanged) {
                tvBalancePlus.setEnabled(true);
                adjustFragment.btnBalancePlus.setEnabled(true); //jk20210130
                tvBalancePlus.setImageResource(R.drawable.bg_balance_plus_selector);
                adjustFragment.btnBalancePlus.setImageResource(R.drawable.bg_balance_plus_selector); //jk20210130
                tvBalanceMin.setEnabled(true);
                adjustFragment.btnBalanceMinus.setEnabled(true); //jk20210130
                tvBalanceMin.setImageResource(R.drawable.bg_balance_min_selector);
                adjustFragment.btnBalanceMinus.setImageResource(R.drawable.bg_balance_min_selector); //jk20210130
                balanceButtonChanged = false;
            }
        }
        tvBalanceValue.setText(String.valueOf(balance));
        //发送指令
        startService();
    }

    public int getBalance() {
        return balance;
    }

    /**
     * @param delay 需要发送的延时控制命令值 / 响应信息栏延时变化   //jk20210327延时按钮修改尝试   在延时为0和1250时更改颜色
     */
    public void setDelay(int delay) {
        this.delay = delay;
        tvDelayValue.setText(delay + "μs");
        //延时按钮状态修改    //GC20200613
        if (delay == 0) {
            tvDelayMin.setEnabled(false);
            adjustFragment.btnDelayMinus.setEnabled(false);
        } else if (delay == 1250) {
            tvDelayPlus.setEnabled(false);
            adjustFragment.btnDelayPlus.setEnabled(false);
        } else {
            tvDelayPlus.setEnabled(true);
            adjustFragment.btnDelayMinus.setEnabled(true);
            tvDelayMin.setEnabled(true);
            adjustFragment.btnDelayPlus.setEnabled(true);
        }
        command = COMMAND_DELAY;
        //GC20200613    延时修改
        dataTransfer = delay / 5;
        //发送指令
        startService();
    }

    public int getDelay() {
        return delay;
    }

    /**
     * @param density 响应状态栏波速度变化
     */
    public void setDensity(int density) {
        this.density = density;
        tvZoomValue.setText("1 : " + density);
        organizeClickZoomData();
        displayWave();
    }

    public int getDensity() {
        return density;
    }

    /**
     * @param velocity 响应状态栏波速度变化
     */
    public void setVelocity(double velocity) {
        this.velocity = velocity;
        if (CurrentUnit == MI_UNIT) {
            tvVopValue.setText(String.valueOf(velocity));
        } else {
            tvVopValue.setText(UnitUtils.miToFt(velocity));
        }
        calculateDistance(Math.abs(pointDistance - zero));

        /*//GC20190709    //GC? 怎么简化下？
        if (!isReceiveData || isDatabase) {
        } else {
            calculateDistance(Math.abs(pointDistance - zero));
        }*/

    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocityNoCmd(int velocity) {
        //20200523  波速度修改
        this.velocity = velocity;
        if (CurrentUnit == MI_UNIT) {
            tvVopValue.setText(String.valueOf(velocity));
        } else {
            tvVopValue.setText(UnitUtils.miToFt(velocity));
        }

    }


    @OnClick({R.id.tv_500m, R.id.tv_250m, R.id.tv_1km, R.id.tv_2km, R.id.tv_4km, R.id.tv_8km, R.id.tv_16km, R.id.tv_32km, R.id.tv_64km,
            R.id.tv_gain_add, R.id.tv_gain_min, R.id.layout_tv_both, R.id.layout_tv_memory, R.id.tv_cursor_plus, R.id.tv_balance_plus, R.id.tv_balance_min, R.id.tv_pulse_width, R.id.tv_cal, R.id.tv_range,
            R.id.tv_file, /*R.id.tv_home, */R.id.tv_zero, R.id.tv_cursor_min, R.id.tv_zoom_plus, R.id.tv_zoom_min, R.id.tv_test, R.id.tv_help,
            R.id.tv_compare, R.id.tv_mode1, R.id.tv_range1, R.id.tv_adjust1, R.id.tv_wave1, R.id.tv_file1, R.id.tv_set1,
            R.id.ll_adjust, R.id.content,
            R.id.iv_pulse_width_close, R.id.tv_pulse_width_save, R.id.tv_save1, R.id.iv_close,
            R.id.tv_vop_save, R.id.iv_compare_close, R.id.iv_cal_close, R.id.iv_range_close, R.id.iv_records_close, R.id.iv_cal_close1,
            R.id.tv_vop_min, R.id.tv_vop_plus, R.id.tv_vop_j, R.id.tv_vop_d, R.id.tv_vop_g,
            R.id.tv_records_save, R.id.tv_file_records,
            R.id.tv_origin, R.id.tv_trigger_delay, R.id.tv_delay_plus, R.id.tv_delay_min, R.id.ll_trigger_delay, R.id.iv_close_trigger_delay,
            R.id.tv_wave_next, R.id.tv_wave_pre,
            R.id.iv_info_PULSE, R.id.iv_AUTO
    })
    public void onClick(View view) {
        //未与硬件连接状态下可以响应的按钮  //GC20200630
        switch (view.getId()) {
            case R.id.iv_info_PULSE:
                //发送单次放电指令  //GC20211209
                command = COMMAND_SINGLE_PULSE;
                //数据
                dataTransfer = 0x01;
                startService();
                //GTT
                /*Constant.currentVoltage = 3000 / 3276.0 * 32;
                //主界面信息栏当前电压
                tvInfoHV.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
                //高压操作对话框当前电压
//                handlerAuto.sendEmptyMessage(HVINDICATOR);
                handler.sendEmptyMessage(WARNING);*/
                break;
            case R.id.iv_AUTO:
                //进入高压操作界面按键  //GC20211202
                closeAllView();
                showAutoDialog();
                break;
            case R.id.tv_file:
                showFileView();
                break;
            case R.id.tv_file_records:
                showRecordsDialog();
                break;
            case R.id.tv_records_save:
                showSaveDialog();
                break;
            /*case R.id.tv_home:
                finish();
                break;*/
            case R.id.tv_mode1: //jk20210125
                tvMode1.setImageResource(R.drawable.bg_mode_pressed1);  //jk20210129
                tvRange1.setImageResource(R.drawable.bg_range_normal_111);
                tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
                tvWave1.setImageResource(R.drawable.bg_wave_normal);
                llCalAdjust.setVisibility(View.GONE);
                llRecords.setVisibility(View.GONE);
                llLead.setVisibility(View.GONE);
                waveFragment.btnFile1.setEnabled(true);  //jk20210427
                clickMode();
                break;
            case R.id.tv_range1:
                tvMode1.setImageResource(R.drawable.bg_mode_normal1);//jk20210129
                tvRange1.setImageResource(R.drawable.bg_range_pressed_1);
                tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
                tvWave1.setImageResource(R.drawable.bg_wave_normal);
                llCalAdjust.setVisibility(View.GONE);
                llRecords.setVisibility(View.GONE);
                llLead.setVisibility(View.GONE);
                waveFragment.btnFile1.setEnabled(true);  //jk20210427
                clickRange();
                break;
            case R.id.tv_adjust1:
                tvMode1.setImageResource(R.drawable.bg_mode_normal1);//jk20210129
                tvRange1.setImageResource(R.drawable.bg_range_normal_111);
                tvAdjust1.setImageResource(R.drawable.bg_adjust_pressed1);
                tvWave1.setImageResource(R.drawable.bg_wave_normal);
                llCalAdjust.setVisibility(View.GONE);
                llRecords.setVisibility(View.GONE);
                llLead.setVisibility(View.GONE);
                waveFragment.btnFile1.setEnabled(true);  //jk20210427
                clickAdjust();
                break;
            case R.id.tv_wave1:
                tvMode1.setImageResource(R.drawable.bg_mode_normal1);//jk20210129
                tvRange1.setImageResource(R.drawable.bg_range_normal_111);
                tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
                tvWave1.setImageResource(R.drawable.bg_wave_pressed1);
                llCalAdjust.setVisibility(View.GONE);
                llRecords.setVisibility(View.GONE);
                llLead.setVisibility(View.GONE);
                waveFragment.btnFile1.setEnabled(true);  //jk20210427
                clickWave();
                break;
            case R.id.tv_file1:
                clickFile();
                break;
            case R.id.tv_set1:
                clickSet();
                break;  //jk20210125
            case R.id.tv_zero:
                //零点切换  //GC20200612
                closeAllView();
                llAdjust.setVisibility(View.GONE); //jk20210125
                mainWave.setScrubLineReal(positionVirtual);
                positionReal = positionVirtual;
                //在原始数据中的位置
                zero = pointDistance;
                calculateDistance(0);
                break;
            case R.id.tv_cursor_min:
                closeAllView();
                llAdjust.setVisibility(View.GONE); //jk20210125
                if (positionVirtual > 0) {

                    int positionVirtualtemp = positionVirtual;
                    positionVirtualtemp -= 1;
                    mainWave.setScrubLineVirtual(positionVirtualtemp);
                    pointDistance = pointDistance + (positionVirtualtemp - positionVirtual) * density;
                    positionVirtual = positionVirtualtemp;
                    Log.e("【按钮调光标】", "positionVirtual" + positionVirtual);
                    if (positionVirtual == 0) {
                        pointDistance = 0;
                    }
                    calculateDistance(Math.abs(pointDistance - zero));
                    //GT20200619
                    /*int height;
                    if (mode == SIM) {
                        height = Constant.SimData[pointDistance];
                    } else {
                        height = Constant.WaveData[pointDistance];
                    }
                    Log.e("【高度】", "当前点高度" + height);
                    tvHeight.setText("高度" + height);*/
                }
                break;
            case R.id.tv_cursor_plus:
                closeAllView();
                llAdjust.setVisibility(View.GONE); //jk20210125
                if (positionVirtual < 509) {
                    int positionVirtualtemp = positionVirtual;
                    positionVirtualtemp += 1;
                    mainWave.setScrubLineVirtual(positionVirtualtemp);
                    pointDistance = pointDistance + (positionVirtualtemp - positionVirtual) * density;
                    positionVirtual = positionVirtualtemp;
                    calculateDistance(Math.abs(pointDistance - zero));
                    //GT20200619
                    /*int height;
                    if (mode == SIM) {
                        height = Constant.SimData[pointDistance];
                    } else {
                        height = Constant.WaveData[pointDistance];
                    }
                    Log.e("【高度】", "当前点高度" + height);
                    tvHeight.setText("高度" + height);*/
                }
                break;
            case R.id.tv_zoom_plus:
                closeAllView();
                llAdjust.setVisibility(View.GONE); //jk20210125
                int density = getDensity();
                if (density > 1) {
                    density = density / 2;
                    setDensity(density);
                    tvZoomMin.setEnabled(true);
                }
                //无法放大
                if (density == 1) {
                    tvZoomPlus.setEnabled(false);
                }
                break;
            case R.id.tv_zoom_min:
                closeAllView();
                llAdjust.setVisibility(View.GONE); //jk20210125
                density = getDensity();
                if (density < Constant.DensityMax) {
                    density = density * 2;
                    setDensity(density);
                    tvZoomPlus.setEnabled(true);
                }
                if (density == Constant.DensityMax) {
                    tvZoomMin.setEnabled(false);
                }
                break;
            default:
                break;
        }
        //如果未连接不执行
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            return;
        }
        //如果测试中不执行
        if (Constant.isTesting) {
            return;
        }

        switch (view.getId()) {
            case R.id.ll_adjust:
                break;
            case R.id.content:
                break;
            case R.id.tv_wave_pre:
                //SIM共8组，从1-8
                selectSim = getSelectSim();
                if (selectSim > 1) {
                    selectSim--;
                    setSelectSim(selectSim);
                }
                break;
            case R.id.tv_wave_next:
                selectSim = getSelectSim();
                if (selectSim < 8) {
                    selectSim++;
                    setSelectSim(selectSim);
                }
                break;
            case R.id.iv_close_trigger_delay:
                closeTriggerDelayView();
                break;
            case R.id.tv_origin:
                //光标零点按钮    //GC20200612
                int simZero = zero;
                if (mode == SIM && range == RANGE_250) {
                    simZero = simZero / 2;
                }
                setSimZero(simZero);
                break;
            case R.id.tv_trigger_delay:
                showTriggerDelayView();
                break;
            case R.id.tv_delay_plus:
                int delay = getDelay();
                //jk20201130 多次脉冲延时间隔增加
                if (mode == SIM) {
                    if (delay < 1250) {
                        delay = delay + 15;
                        if (delay > 1250) {
                            delay = 1250;
                        }
                        //GC20190704 延时发送命令修改   (延时从0到1250，点击一次增加5，共250阶)
                        setDelay(delay);
                    }
                } else if (delay < 1250) {
                    delay = delay + 5;
                    //GC20190704 延时发送命令修改   (延时从0到1250，点击一次增加5，共250阶)
                    setDelay(delay);
                }
                break;
            case R.id.tv_delay_min:
                delay = getDelay();
                if (mode == SIM) {  //jk20201130 多次脉冲延时间隔增加
                    if (delay > 0) {
                        delay = delay - 15;
                        if (delay < 0) {
                            delay = 0;
                        }
                        //GC20190704 延时发送命令修改   (延时从0到1250，点击一次增加5，共250阶)
                        setDelay(delay);
                    }
                } else if (delay > 0) {
                    delay = delay - 5;
                    setDelay(delay);
                }
                break;
            case R.id.iv_compare_close:
                closeCompareView();
                break;
            case R.id.iv_cal_close:
                closeCalView();
                break;
            case R.id.iv_cal_close1:
                closeCalView1();
                break;
            case R.id.iv_close:
                closeA1();
                waveFragment.btnFile1.setEnabled(true);  //jk20210427
                break;
            case R.id.iv_pulse_width_close:
                closePulseWidthView();
                break;
            case R.id.iv_range_close:
                closeRangeView();
                break;
            case R.id.iv_records_close:
                closeFileView();
                break;
            case R.id.tv_vop_save:
                saveVop();
                llCal.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_pulse_width_save:
                savePulseWidth();
                break;
            case R.id.tv_save1:  //jk20210203
                saveCableInit();
                llLead.setVisibility(View.GONE);
                waveFragment.btnFile1.setEnabled(true);  //jk20210830
                break;
            case R.id.tv_250m:
                setRange(0x99);
                setGain(gain);
                //切换范围时改变脉宽（未保存过脉宽且只在TDR模式下发射该命令）    //GC20200331
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_500m:
                setRange(0x11);
                setGain(gain);
                //GC20200331
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 40;
                        setPulseWidth(40);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(40));
                }
                //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_1km:
                setRange(0x22);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 80;
                        setPulseWidth(80);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(80));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 320;
                        setPulseWidth(320);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_2km:
                setRange(0x33);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 160;
                        setPulseWidth(160);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(160));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 720;
                        setPulseWidth(720);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_4km:
                setRange(0x44);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 320;
                        setPulseWidth(320);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(320));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_8km:
                setRange(0x55);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 640;
                        setPulseWidth(640);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(640));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidth = 3600;
                        setPulseWidth(3600);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_16km:
                setRange(0x66);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 1280;
                        setPulseWidth(1280);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(1280));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 7120;
                        setPulseWidth(7120);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_32km:
                setRange(0x77);
                setGain(gain);
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 2560;
                        setPulseWidth(2560);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(2560));
                }
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_64km:
                setRange(0x88);
                setGain(gain);
                //GC20200331
                if (!hasSavedPulseWidth && mode == TDR) {
                    handler.postDelayed(() -> {
                        pulseWidth = 5120;
                        setPulseWidth(5120);
                    }, 20);
                    etPulseWidth.setText(String.valueOf(5120));
                }
                //GC20200527
                if (mode == SIM) {
                    handler.postDelayed(() -> {
                        pulseWidthSim = 10200;
                        setPulseWidth(10200);
                    }, 20);
                }
                handler.postDelayed(this::clickTest, 50);
                tvGainAdd.setEnabled(true);
                tvGainMin.setEnabled(true);
                adjustFragment.btnGainPlus.setEnabled(true);
                adjustFragment.btnGainMinus.setEnabled(true);
                break;
            case R.id.tv_gain_add:
                int gain = getGain();
                if (gain < 31) {
                    gain++;
                    //GC20190704 增益发送命令修改   (命令范围0-31阶)
                    setGain(gain);
                }
                closeAllView();
                break;
            case R.id.tv_gain_min:
                gain = getGain();
                if (gain > 0) {
                    gain--;
                    //GC20190704 增益发送命令修改   (命令范围0-31阶)
                    setGain(gain);
                }
                closeAllView();
                break;
            case R.id.tv_balance_plus:
                int balance = getBalance();
                if (balance < 15) {
                    balance++;
                    setBalance(balance);
                }
                closeAllView();
                break;
            case R.id.tv_balance_min:
                balance = getBalance();
                if (balance > 0) {
                    balance--;
                    setBalance(balance);
                }
                closeAllView();
                break;
            case R.id.tv_vop_min:
                velocity = getVelocity();
                if (velocity > 90) {
                    velocity--;
                    setVelocity(velocity);
                    saveVop();
                }
                break;
            case R.id.tv_vop_plus:
                int velocity = (int) getVelocity();
                if (velocity < 300) {
                    velocity++;
                    setVelocity(velocity);
                    saveVop();
                }
                break;
            case R.id.tv_vop_j:   //jk20210201
                int velocity1 = 172;
                setVelocity(velocity1);
                Toast.makeText(this, "交联聚乙烯电缆波速度范围170-174，推荐使用172", Toast.LENGTH_SHORT).show();
                llCalAdjust.setVisibility(View.GONE);
                adjustFragment.btnVelPlus.setEnabled(true);  //jk20210407
                adjustFragment.btnVelMinus.setEnabled(true);
                break;
            case R.id.tv_vop_d:   //jk20210201
                int velocity2 = 192;
                setVelocity(velocity2);
                Toast.makeText(this, "聚乙烯电缆波速度范围190-194，推荐使用192", Toast.LENGTH_SHORT).show();
                llCalAdjust.setVisibility(View.GONE);
                adjustFragment.btnVelPlus.setEnabled(true);  //jk20210407
                adjustFragment.btnVelMinus.setEnabled(true);
                break;
            case R.id.tv_vop_g:   //jk20210201
                int velocity3 = 165;
                setVelocity(velocity3);
                Toast.makeText(this, "聚氯乙烯电缆波速度范围160-168，推荐使用165", Toast.LENGTH_SHORT).show();
                llCalAdjust.setVisibility(View.GONE);
                adjustFragment.btnVelPlus.setEnabled(true);  //jk20210407
                adjustFragment.btnVelMinus.setEnabled(true);
                break;
            case R.id.tv_pulse_width:
                showPulseWidth();
                break;
            case R.id.layout_tv_memory:
                clickMemory();
                break;
            case R.id.layout_tv_both:
                clickCompare();
                break;
            case R.id.tv_compare:
                showCompareView();
                break;
            case R.id.tv_cal:
                showCalView();
                break;
            case R.id.tv_range:
                showRangeView();
                break;
            case R.id.tv_test:
                isReceiveData = true;
                clickTest();
                llAdjust.setVisibility(View.GONE); //jk20210125
                step = 8;   //jk20200716
                count = 6;
                isLongClick = false;  //jk20200716
                break;
            case R.id.tv_help:
                closeAllView();
                showHelpModeDialog();
            default:
                break;
        }
    }

    /*
     handlerAuto.postDelayed(this::clickTest, 50);changshi
     */
    public void test1() {
        handler.postDelayed(() -> {
            //范围
            setRange(range);
        }, 20);
        handler.postDelayed(() -> {
            //增益
            setGain(gain);
        }, 20);
        //不同模式下初始化发射不同命令  //GC20200424
        if (mode == TDR) {
            handler.postDelayed(() -> {
                //脉宽
                setPulseWidth(pulseWidth);
            }, 20);
        } else if (mode == ICM || mode == SIM) {
            handler.postDelayed(() -> {
                //延时
                delay = 0;
                setDelay(delay);
            }, 20);
            if (mode == SIM) {
                handler.postDelayed(() -> {
                    //脉宽
                    setPulseWidth(pulseWidthSim);
                }, 20);
            }
        }
        //延时100毫秒发送测试命令，100毫秒是等待设备回复命令信息，如果不延时，有可能设备执行不完命令。
        handler.postDelayed(ModeActivity.this::clickTest, 100);
    }


    /**
     * @param index 侧边栏设置   //jk20210123
     */

    public void setTabSelection(int index) {
        //开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        //先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragment(transaction);

        switch (index) {
            case 0:
                if (modeFragment == null) {
                    modeFragment = new ModeFragment();
                    transaction.add(R.id.content, modeFragment);
                } else {
                    transaction.show(modeFragment);
                }
                break;
            case 1:
                if (rangeFragment == null) {
                    rangeFragment = new RangeFragment();
                    transaction.add(R.id.content, rangeFragment);
                } else {
                    transaction.show(rangeFragment);
                }
                break;
            case 2:
                if (adjustFragment == null) {
                    adjustFragment = new AdjustFragment();
                    transaction.add(R.id.content, adjustFragment);
                } else {
                    transaction.show(adjustFragment);
                }
                break;
            case 3:
                if (waveFragment == null) {
                    waveFragment = new WaveFragment();
                    transaction.add(R.id.content, waveFragment);
                } else {
                    transaction.show(waveFragment);
                }
                break;
            case 4:
                if (fileFragment == null) {
                    fileFragment = new FileFragment();
                    transaction.add(R.id.content, fileFragment);
                } else {
                    transaction.show(fileFragment);
                }
                break;
            case 5:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    transaction.show(settingFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();

    }

    /**
     * 隐藏不需要的Fragment   //jk20210123
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (modeFragment != null) {
            transaction.hide(modeFragment);
        }
        if (rangeFragment != null) {
            transaction.hide(rangeFragment);
        }
        if (adjustFragment != null) {
            transaction.hide(adjustFragment);
        }
        if (waveFragment != null) {
            transaction.hide(waveFragment);
        }
        if (fileFragment != null) {
            transaction.hide(fileFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }


    /*
     **方式按钮响应 //jk20210123
     */
    private void clickMode() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(0);
        tvMode1.setEnabled(false);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvFile1.setEnabled(true);
        tvSet1.setEnabled(true);
    }

    /*
     **范围按钮响应  //jk20210125
     */
    private void clickRange() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(1);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(false);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvFile1.setEnabled(true);
        tvSet1.setEnabled(true);
    }

    /*
     **调节按钮响应  //jk20210125
     */
    private void clickAdjust() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(2);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(false);
        tvWave1.setEnabled(true);
        tvFile1.setEnabled(true);
        tvSet1.setEnabled(true);
    }

    /*
     **操作按钮响应  //jk20210125
     */
    private void clickWave() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(3);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(false);
        tvFile1.setEnabled(true);
        tvSet1.setEnabled(true);
    }

    /*
     **文件按钮响应  //jk20210125
     */
    public void clickFile() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(4);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvFile1.setEnabled(false);
        tvSet1.setEnabled(true);
    }

    /*
     **设置按钮响应  //jk20210125
     */
    private void clickSet() {
        llAdjust.setVisibility(View.GONE);
        llPulseWidth.setVisibility(View.GONE);
        setTabSelection(5);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvFile1.setEnabled(true);
        tvSet1.setEnabled(false);
    }


    /**
     * 长按测试按键响应     //jk20200715
     */
    @OnLongClick({R.id.tv_test})
    public boolean onLongClick(View view) {
        isLongClick = true;
        isReceiveData = true;
        clickTest();
        llAdjust.setVisibility(View.GONE); //jk20210125
        return true;
    }

    /**
     * 模式界面帮助按钮   //GC20200327
     */
    private void showHelpModeDialog() {
        llAdjust.setVisibility(View.GONE); //jk20210125
        HelpModeDialog helpModeDialog = new HelpModeDialog(this);
        Constant.ModeValue = mode;
        if (!helpModeDialog.isShowing()) {
            helpModeDialog.show();
        }
    }

    public int getSelectSim() {
        return selectSim;
    }

    /**
     * @param selectSim SIM显示波形的组数
     */
    public void setSelectSim(int selectSim) {
        Log.e("SIM筛选", "setSelectSim");
        tvWaveText.setVisibility(View.VISIBLE);
        this.selectSim = selectSim;
        //波形上翻下翻按钮状态变化    //GC20200604
        if (selectSim == 1) {
            tvWavePre.setEnabled(false);
            waveFragment.btnWavePrevious.setEnabled(false);
            tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_false);
            waveFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
            tvWaveNext.setEnabled(true);
            waveFragment.btnWaveNext.setEnabled(true);
            tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_selector);
            waveFragment.btnWaveNext.setImageResource(R.drawable.bg_wavenext_selector);
            waveButtonChanged = true;
        } else if (selectSim == 8) {
            tvWavePre.setEnabled(true);
            waveFragment.btnWavePrevious.setEnabled(true);
            tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_selector);
            waveFragment.btnWavePrevious.setImageResource(R.drawable.bg_wavepre_selector);
            tvWaveNext.setEnabled(false);
            waveFragment.btnWaveNext.setEnabled(false);
            tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_false);
            waveFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
            waveButtonChanged = true;
        } else {
            if (waveButtonChanged) {
                tvWavePre.setEnabled(true);
                waveFragment.btnWavePrevious.setEnabled(true);
                tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_selector);
                waveFragment.btnWavePrevious.setImageResource(R.drawable.bg_wavepre_selector);
                tvWaveNext.setEnabled(true);
                waveFragment.btnWaveNext.setEnabled(true);
                tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_selector);
                waveFragment.btnWaveNext.setImageResource(R.drawable.bg_wavenext_selector);
                waveButtonChanged = false;
            }
        }
        switch (selectSim) {
            case 1:
                System.arraycopy(simDraw1, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData1;
                tvWaveValue.setText(R.string.wave_one);
                break;
            case 2:
                System.arraycopy(simDraw2, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData2;
                tvWaveValue.setText(R.string.wave_two);
                break;
            case 3:
                System.arraycopy(simDraw3, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData3;
                tvWaveValue.setText(R.string.wave_three);
                break;
            case 4:
                System.arraycopy(simDraw4, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData4;
                tvWaveValue.setText(R.string.wave_four);
                break;
            case 5:
                System.arraycopy(simDraw5, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData5;
                tvWaveValue.setText(R.string.wave_five);
                break;
            case 6:
                System.arraycopy(simDraw6, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData6;
                tvWaveValue.setText(R.string.wave_six);
                break;
            case 7:
                System.arraycopy(simDraw7, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData7;
                tvWaveValue.setText(R.string.wave_seven);
                break;
            case 8:
                System.arraycopy(simDraw8, 0, waveCompare, 0, 510);
                Constant.SimData = Constant.TempData8;
                tvWaveValue.setText(R.string.wave_eight);
                break;
            default:
                break;
        }
        displayWave();
    }

    /**
     * @param simZero SIM光标零点自定义设置    //GC20190712
     */
    public void setSimZero(int simZero) {
        this.simOriginalZero = simZero;
        //SIM标记光标（可以自定义）   //GC20200612
        this.simStandardZero = simZero;
        positionSim = simStandardZero / density;
        mainWave.setScrubLineSim(positionSim);
        StateUtils.setInt(ModeActivity.this, AppConfig.CURRENT_CURSOR_POSITION, simZero);
        Toast.makeText(this, getResources().getString(R.string.cursor_zero_set_success), Toast.LENGTH_SHORT).show();

    }

    private void saveVop() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        //单位转化逻辑修正  //20200522
        if (paramInfo != null) {
            paramInfo.setCableVop(String.valueOf(velocity));
        } else {
            paramInfo = new ParamInfo();
            paramInfo.setCableVop(String.valueOf(velocity));
        }
        Constant.Velocity = velocity;
        StateUtils.setObject(ModeActivity.this, paramInfo, Constant.PARAM_INFO_KEY);

    }

    /**
     * 该方法检测一个点击事件是否落入在一个View内，换句话说，检测这个点击事件是否发生在该View上。
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean touchEventInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }

        int[] location = new int[2];
        view.getLocationOnScreen(location);

        int left = location[0];
        int top = location[1];

        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();

        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }

        return false;
    }


    public void showFileView() {
        llAdjust.setVisibility(View.GONE);
        llRecords.setVisibility(View.VISIBLE);
        llRecords.setOnClickListener(null);
        closeCompareView();
        closeRangeView();
        closeCalView();
        closeTriggerDelayView();
        closePulseWidthView();
    }

    private void closeFileView() {
        llAdjust.setVisibility(View.VISIBLE);
        llRecords.setVisibility(View.GONE);
        llAdjust.setVisibility(View.GONE);
    }

    private void showRangeView() {
        //点击范围按钮的前提是与设备连接成功，否则吐司，禁止继续执行代码
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            return;
        }
        llRange.setVisibility(View.VISIBLE);
        llRange.setOnClickListener(null);
        closeFileView();
        closeCompareView();
        closeCalView();
        closeTriggerDelayView();
        closePulseWidthView();
    }

    private void closeRangeView() {
        llAdjust.setVisibility(View.VISIBLE);
        llRange.setVisibility(View.GONE);
    }

    private void showTriggerDelayView() {
        llTriggerDelay.setVisibility(View.VISIBLE);
        closeFileView();
        closeRangeView();
        closeCalView();
        closeCompareView();
        closePulseWidthView();
    }

    private void closeTriggerDelayView() {
        llAdjust.setVisibility(View.VISIBLE);
        llTriggerDelay.setVisibility(View.GONE);
    }

    public void showCalView() {
        llCal.setVisibility(View.VISIBLE);
        llCal.setOnClickListener(null);
        closeFileView();
        closeRangeView();
        closeCompareView();
        closeTriggerDelayView();
        closePulseWidthView();
    }

    public void showCalView1() {
        llCalAdjust.setVisibility(View.VISIBLE);
        llCalAdjust.setOnClickListener(null);
        closeFileView();
        closeRangeView();
        closeCompareView();
        closeTriggerDelayView();
        closePulseWidthView();
    }

    public void showLeadView1() {
        llLead.setVisibility(View.VISIBLE);
        closeFileView();
        closeRangeView();
        closeCompareView();
        closeTriggerDelayView();
        closePulseWidthView();
    }


    private void closeCalView() {
        // llAdjust.setVisibility(View.VISIBLE); jk20210201
        llCal.setVisibility(View.GONE);
    }

    private void closeCalView1() {
        // llAdjust.setVisibility(View.VISIBLE); jk20210201
        llCalAdjust.setVisibility(View.GONE);
    }

    private void closeA1() {
        // llAdjust.setVisibility(View.VISIBLE); jk20210201
        llLead.setVisibility(View.GONE);
    }


    private void closePulseWidthView() {
        //llAdjust.setVisibility(View.VISIBLE);
        llPulseWidth.setVisibility(View.GONE);
        llAdjust.setVisibility(View.GONE); //jk20210125
    }


    private void closeCompareView() {
        llAdjust.setVisibility(View.VISIBLE);
        llCompare.setVisibility(View.GONE);
    }

    private void showCompareView() {
        llCompare.setVisibility(View.VISIBLE);
        llCompare.setOnClickListener(null);
        closeFileView();
        closeRangeView();
        closeCalView();
        closeTriggerDelayView();
        closePulseWidthView();
    }

    private void closeAllView() {
        closeCalView();
        closeCompareView();
        closeRangeView();
        closeTriggerDelayView();
        closeFileView();
        closePulseWidthView();
    }

    /**
     * 设置波宽度，存储本地保存
     */
    public void showPulseWidth() {
        llPulseWidth.setOnClickListener(null);
        llPulseWidth.setVisibility(View.VISIBLE);
        closeFileView();
        closeRangeView();
        closeCompareView();
        closeCalView();
        closeTriggerDelayView();
        llAdjust.setVisibility(View.GONE); //jk20210125

    }

    /**
     * 存储波宽度到本地
     */
    private void savePulseWidth() {
        // 01 初始化操作值
        if (etPulseWidth.getText().toString().isEmpty() || "0".equals(etPulseWidth.getText().toString())) {
            //输入为空时认为没有保存操作   //GC20200331
            etPulseWidth.setText(String.valueOf(pulseWidth));
            hasSavedPulseWidth = false;
        } else {
            pulseWidth = Integer.valueOf(etPulseWidth.getText().toString());
            //已保存过脉宽 //GC20200331
            hasSavedPulseWidth = true;
        }
        // 02 本地保存波宽度信息
        savePulseWidthInfo();
        // 03 指令下达
        setPulseWidth(pulseWidth);
        closePulseWidthView();
    }

    /**
     * 存储本地保存
     */
    private void savePulseWidthInfo() {
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PULSE_WIDTH_INFO_KEY);
        if (paramInfo != null) {
            paramInfo.setPulseWidth(pulseWidth);
        } else {
            paramInfo = new ParamInfo();
            paramInfo.setPulseWidth(pulseWidth);
        }
        StateUtils.setObject(ModeActivity.this, paramInfo, Constant.PULSE_WIDTH_INFO_KEY);

    }

    /**
     * 波宽度指令下发
     */
    public void setPulseWidth(int pulseWidth) {
        command = COMMAND_PULSE_WIDTH;
        dataTransfer = calPulseWidth(pulseWidth);
        startService();
    }

    /**
     * 计算波宽度数值，计算公式为255-X/40;X为输入值
     */
    private int calPulseWidth(int pulseWidth) {

        if (pulseWidth < 0 || pulseWidth > 7000) {
            return 0;
        }
        pulseWidth = 255 - pulseWidth / 40;
        return pulseWidth;
    }

    /**
     * 弹出波形存储对话框
     */
    public void showSaveDialog() {
        closeFileView();
        SaveRecordsDialog saveRecordsDialog = new SaveRecordsDialog(this);
        Constant.ModeValue = mode;
        //TODO 20191226 存储zero和pointDistance
        saveRecordsDialog.setPositionReal(zero);
        saveRecordsDialog.setPositionVirtual(pointDistance);
        if (!saveRecordsDialog.isShowing()) {
            saveRecordsDialog.show();
        }
        llAdjust.setVisibility(View.GONE); //jk20210126
    }

    /**
     * 弹出波形记录对话框
     */
    public void showRecordsDialog() {
        closeFileView();
        ShowRecordsDialog showRecordsDialog = new ShowRecordsDialog(this);
        showRecordsDialog.setMode(mode);
        if (!showRecordsDialog.isShowing()) {
            showRecordsDialog.show();
        }
        llAdjust.setVisibility(View.GONE); //jk20210126
    }

    /**
     * 弹出高压操作界面对话框  //GC20211202
     */
    public int currentGear = 2;
    public int currentSetVoltage = 0;
    public int currentSetTime = 5;
    public boolean sendWorkingMode;
    public int workingModeData;

    private void showAutoDialog() {
        AutoDialog autoDialog = new AutoDialog(this);
        Constant.ModeValue = mode;
        if (!autoDialog.isShowing()) {
            autoDialog.show();

            //信息栏与高压操作界面参数传递和初始化    //GC20211207
            //打开对话框工作模式初始化
            autoDialog.spWorkingMode.setSelection(Constant.WorkingMode);
            //监听工作方式变化
            autoDialog.spWorkingMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (Constant.WorkingMode != position) {
                        sendWorkingMode = true;     //如果和记录的位置不一样，发送工作模式指令
                    }
                    //记录工作模式变化
                    Constant.WorkingMode = position;    //position=0,1,2
                    //mode界面工作模式同步
                    switch (position) {
                        case 0:
                            tvInfoWorkingMode.setText(R.string.PULSE);
                            workingModeData = 0x01;
                            break;
                        case 1:
                            tvInfoWorkingMode.setText(R.string.CYCLIC);
                            workingModeData = 0x02;
                            break;
                        case 2:
                            tvInfoWorkingMode.setText(R.string.DC);
                            workingModeData = 0x00;
                            break;
                        default:
                            break;
                    }
                    if (sendWorkingMode) {
                        sendWorkingMode = false;
                        //发送工作模式指令  //GC20211209
                        command = COMMAND_WORKING_MODE;
                        //数据
                        dataTransfer = workingModeData;
                        startService();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //档位初始化
            currentGear = Constant.gear;
            switch (currentGear) {
                case 1:
                    autoDialog.rgGear.check(autoDialog.rbGear32.getId());
                    break;
                case 2:
                    autoDialog.rgGear.check(autoDialog.rbGear16.getId());
                    break;
                default:
                    break;
            }
            //监听电压档位选项变化
            autoDialog.setRadioGroup(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (autoDialog.rbGear16.getId() == checkedId) {
                        autoDialog.rgGear.check(autoDialog.rbGear16.getId());
                        currentGear = 2;
                        //改变档位设定电压初始化为0
                        Constant.setVoltage = 0;
                        autoDialog.controlVoltage32.setVisibility(View.GONE);
                        autoDialog.controlVoltage16.setVisibility(View.VISIBLE);
                        autoDialog.controlVoltage16.setTemp(0, 16, 0);
                    } else if (autoDialog.rbGear32.getId() == checkedId) {
                        autoDialog.rgGear.check(autoDialog.rbGear32.getId());
                        currentGear = 1;
                        //改变档位设定电压初始化为0
                        Constant.setVoltage = 0;
                        autoDialog.controlVoltage16.setVisibility(View.GONE);
                        autoDialog.controlVoltage32.setVisibility(View.VISIBLE);
                        autoDialog.controlVoltage32.setTemp(0, 32, 0);
                    }
                }
            });
            //设定电压初始化
            currentSetVoltage = Constant.setVoltage;
            if (currentGear == 1) { //32kV档位
                autoDialog.controlVoltage16.setVisibility(View.GONE);
                autoDialog.controlVoltage32.setVisibility(View.VISIBLE);
                autoDialog.controlVoltage32.setTemp(0, 32, currentSetVoltage);
            } else if (currentGear == 2) {//16kV档位
                autoDialog.controlVoltage32.setVisibility(View.GONE);
                autoDialog.controlVoltage16.setVisibility(View.VISIBLE);
                autoDialog.controlVoltage16.setTemp(0, 16, currentSetVoltage);
            }
            //监听32kV设定电压变化
            autoDialog.controlVoltage32.setOnTempChangeListener(new HVControlView.OnTempChangeListener() {
                @Override
                public void change(int temp) {
                    currentSetVoltage = temp;
                }
            });
            //监听16kV设定电压变化
            autoDialog.controlVoltage16.setOnTempChangeListener(new HVControlView2.OnTempChangeListener() {
                @Override
                public void change(int temp) {
                    currentSetVoltage = temp;
                }
            });
            //放电周期初始化
            currentSetTime = Constant.time;
            autoDialog.controlTime.setTemp(0, 12, currentSetTime);
            //监听放电周期变化
            autoDialog.controlTime.setOnTempChangeListener(new TimeControlView.OnTempChangeListener() {
                @Override
                public void change(int temp) {
                    currentSetTime = temp;
                    //改变放电周期
                    Constant.time = currentSetTime;
                    tvInfoTIME.setText(currentSetTime + "");
                }
            });

            //点击单次放电按钮事件
            autoDialog.setIvHVPULSE(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //发送单次放电指令  //GC20211209
                    command = COMMAND_SINGLE_PULSE;
                    //数据
                    dataTransfer = 0x01;
                    startService();
                }
            });
            //发送高压设定电压指令
            autoDialog.setTvConfirmButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //记录改变的电压档位
                    Constant.gear = currentGear;
                    //记录改变的设定电压
                    Constant.setVoltage = currentSetVoltage;
                    tvInfoSetVoltage.setText(currentSetVoltage + "");

                    //发送高压设定电压指令    //GC20211209
                    /*ConnectService.isHV = true;
                    command = COMMAND_VOLTAGE_SET;
                    //电压数值
                    int temp =  currentSetVoltage * 3276 / 32;
                    dataTransfer = (byte) (temp >> 8 & 0xff);
                    dataTransfer2 = (byte) (temp & 0xff);
                    //档位
                    dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                    startService();*/

                    //GTT
                    byte temp = (byte) 23;
                    byte[] array;
                    array = getBooleanArray(temp);
                    //接地报警
                    if (array[3] == 1) {

                    }
                    //高压包故障
                    if (array[4] == 1) {

                    }
                    //电容有残压
                    if (array[5] == 1) {

                    }
                    //工作方式故障
                    if (array[6] == 1) {

                    }
                    //电压档位故障
                    if (array[7] == 1) {

                    }
                    /*Constant.currentVoltage = 3000 / 3276.0 * 32;
                    //主界面信息栏当前电压
                    tvInfoHV.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
                    //高压操作对话框当前电压
                    handler.sendEmptyMessage(HVINDICATOR);
                    //handlerAuto.sendEmptyMessage(WARNING);*/
                }
            });
        }
    }

    /**
     * 测试按钮
     */
    private void clickTest() {
        llAdjust.setVisibility(View.GONE); //jk20210125
        llCalAdjust.setVisibility(View.GONE);
        llLead.setVisibility(View.GONE);
        //TODO 20200407 点击测试按钮的前提是与设备连接成功，否则吐司，禁止继续执行代码
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO 20200415 如果测试中不要再测试
        if (Constant.isTesting) {
            return;
        }
        //TODO 20200407 点击测试后，禁用测试按钮，等待波形绘制完毕才能继续点。
        Constant.isTesting = true;
        tvTest.setEnabled(false);

        Constant.SaveToDBGain = Constant.Gain;
        closeAllView();
        llRange.setVisibility(View.INVISIBLE);

        if (tDialog != null) {
            tDialog.dismiss();
        }
        //初始化距离
        if (mode == TDR) {
            tDialog = new TDialog.Builder(getSupportFragmentManager())
                    .setLayoutRes(R.layout.receiving_data)
                    .setScreenWidthAspect(this, 0.25f)
                    .setCancelableOutside(false)
                    .create()
                    .show();
            Log.e("DIA", " 正在接受数据显示" + " TDR");
            command = COMMAND_TEST;
            dataTransfer = TESTING;
            startService();
            handler.postDelayed(() -> {
                command = COMMAND_RECEIVE_WAVE;
                dataTransfer = 0x11;
                startService();
                //Log.e("【时效测试】", "发送接收波形数据命令");
                //未显示波形设置为否 //20200523
                alreadyDisplayWave = false;
            }, 20);

        } else if ((mode == ICM) || (mode == ICM_DECAY) || (mode == SIM) || (mode == DECAY)) {
            tDialog = new TDialog.Builder(getSupportFragmentManager())
                    .setLayoutRes(R.layout.wait_trigger)
                    .setScreenWidthAspect(this, 0.3f)
                    .setCancelableOutside(false)
                    .addOnClickListener(R.id.tv_cancel)
                    .setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {

                            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                                Toast.makeText(ModeActivity.this, R.string.ask_cancel, Toast.LENGTH_SHORT).show();
                            }
                            return true;
                        }
                    })
                    .setOnViewClickListener(new OnViewClickListener() {
                        @Override
                        public void onViewClick(BindViewHolder viewHolder, View view,
                                                TDialog tDialog) {
                            //取消测试逻辑修正    //20200523    //GC
                            if (canClickCancelButton) {
                                //允许点击取消测试按钮为否
                                canClickCancelButton = false;
                                if (!alreadyDisplayWave) {
                                    tvZoomMin.setEnabled(false);
                                    tvZoomPlus.setEnabled(false);
                                    waveFragment.btnZoomIn.setEnabled(false);//jk20210126
                                    waveFragment.btnZoomOut.setEnabled(false);//jk20210126
                                    waveFragment.btnZoomIn.setImageResource(R.drawable.bg_fangda);//jk20210130cs
                                    waveFragment.btnZoomOut.setImageResource(R.drawable.bg_suoxiao);//jk20210130cs
                                    waveFragment.btnRes.setImageResource(R.drawable.bg_huanyuan);//jk20210130cs
                                    tvWavePre.setEnabled(false);
                                    waveFragment.btnWavePrevious.setEnabled(false);
                                    tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_false);
                                    waveFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
                                    tvWaveNext.setEnabled(false);
                                    waveFragment.btnWaveNext.setEnabled(false);
                                    tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_false);
                                    waveFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
                                    //后续优化保留  //GC20200604
                                } else {
                                    tvZoomMin.setEnabled(true);
                                    tvZoomPlus.setEnabled(true);
                                    waveFragment.btnZoomIn.setEnabled(true);//jk20210126
                                    waveFragment.btnZoomOut.setEnabled(true);//jk20210126
                                    waveFragment.btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector);//jk20210130cs
                                    waveFragment.btnZoomOut.setImageResource(R.drawable.bg_zoom2_selector);//jk20210130cs
                                    tvWavePre.setEnabled(true);
                                    waveFragment.btnWavePrevious.setEnabled(true);
                                    tvWavePre.setImageResource(R.drawable.bg_wave_pre_s_selector);
                                    waveFragment.btnWavePrevious.setImageResource(R.drawable.bg_wavepre_selector);
                                    tvWaveNext.setEnabled(true);
                                    waveFragment.btnWaveNext.setEnabled(true);
                                    tvWaveNext.setImageResource(R.drawable.bg_wave_next_s_selector);
                                    waveFragment.btnWaveNext.setImageResource(R.drawable.bg_wavenext_selector);
                                }
                                tDialog.dismiss();
                                //TODO 20200407 取消测试后，恢复测试按钮可用性
                                tvTest.setEnabled(true);
                                Constant.isTesting = false;
                                allowSetRange = true;

                                command = COMMAND_TEST;
                                dataTransfer = CANCEL_TEST;
                                startService();
                            }
                        }
                    })
                    .create()
                    .show();
            //TODO 20200507 取消测试按钮延时可用
            canClickCancelButton = false;
            handler.postDelayed(() -> {
                canClickCancelButton = true;
            }, 300);
            Log.e("DIA", " 等待触发显示");
            command = COMMAND_TEST;
            dataTransfer = TESTING;
            startService();

            //EN20200324
            ConnectService.canAskPower = false;
        }
    }

    /**
     * 比较按钮执行的方法  //GC20190703
     */
    public void clickCompare() {
        closeCompareView();
        if (isMemory) {
            //再优化   //GC20190703
            if ((modeBefore == mode) && (rangeBefore == range)) {
                isCom = !isCom;
                myChartAdapterMainWave.setmTempArray(waveDraw);
                myChartAdapterMainWave.setShowCompareLine(isCom);
                //myChartAdapterMainWave.setmCompareArray(waveCompare);
                organizeCompareWaveData(currentStart);
                myChartAdapterMainWave.setmCompareArray(waveCompareDraw);
                myChartAdapterMainWave.notifyDataSetChanged();
            } else {
                Toast.makeText(this, getResources().getString(R.string.You_can_not_compare), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.You_have_no_memory_data_can_not_compare), Toast.LENGTH_SHORT).show();
        }
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    /**
     * 记忆按钮执行的方法  //GC20190703
     */
    public void clickMemory() {
        if (alreadyDisplayWave) {
            closeCompareView();
            isMemory = true;
            waveCompare = new int[Constant.WaveData.length];
            System.arraycopy(Constant.WaveData, 0, waveCompare, 0, Constant.WaveData.length);
            //记录记忆数据的方式范围   //再优化   //GC20190703
            modeBefore = mode;
            rangeBefore = range;
        }

        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    /**
     * 通过服务下发指令
     */
    public void startService() {
        Intent intent = new Intent(ModeActivity.this, ConnectService.class);
        //发送指令
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_MODE_KEY, mode);
        bundle.putInt(BUNDLE_COMMAND_KEY, command);
        bundle.putInt(BUNDLE_DATA_TRANSFER_KEY, dataTransfer);
        //比普通指令增加2个字节的数据    //GC20211206
        bundle.putInt(BUNDLE_DATA_TRANSFER_KEY2, dataTransfer2);
        bundle.putInt(BUNDLE_DATA_TRANSFER_KEY3, dataTransfer3);
        intent.putExtra(BUNDLE_PARAM_KEY, bundle);
        startService(intent);
        llAdjust.setVisibility(View.GONE); //jk20210125
    }

    public boolean getReceiveData() {
        return isReceiveData;
    }

    public void setReceiveData(boolean receiveData) {
        isReceiveData = receiveData;
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
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

    /*
     *    延长线弹窗初始化  //jk20210202
     */
    private int unit1;
    public int catlead1;

    private void CableInit() {

        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        unit1 = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_UNIT, MI_UNIT);
        CurrentUnit = unit1;

        if (paramInfo != null) {

            //测试缆
            if (paramInfo.getTestLead()) {
                cbtestlead1.setChecked(paramInfo.getTestLead());
            }
            if (!TextUtils.isEmpty(paramInfo.getLength())) {
                etLength1.setText(String.valueOf(paramInfo.getLength()));
            }
            try {
                if (paramInfo.getLength().equals("0")) {
                    etLength1.setText("");
                }
            } catch (Exception l_ex) {
                etLength1.setText("");
            }
            if (!TextUtils.isEmpty(paramInfo.getVop())) {
                etVop1.setText(String.valueOf(paramInfo.getVop()));
            }
            try {
                if (paramInfo.getVop().equals("0")) {
                    etVop1.setText("");
                }
            } catch (Exception l_ex) {
                etVop1.setText("");
            }
        }

        //测试缆选中
        if (cbtestlead1.isChecked()) {
            etLength1.setEnabled(true);
            etVop1.setEnabled(true);
            etLength1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            etVop1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            tvlength1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            tvvop1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            tvvoplength.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            tvvopu.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
            catlead1 = 1;
            //Log.e("leadCat", "leadCat" +leadCat);
        } else {
            etLength1.setEnabled(false);
            etVop1.setEnabled(false);
            etLength1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            etVop1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvlength1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvvop1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvvoplength.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            tvvopu.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
            catlead1 = 0;
            // Log.e("leadCat", "leadCat" +leadCat);
        }

        //监听选项，使测试缆颜色变化
        cbtestlead1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etLength1.setEnabled(true);
                etVop1.setEnabled(true);
                etLength1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                etVop1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                tvlength1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                tvvop1.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                tvvoplength.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                tvvopu.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0FF6FF));
                catlead1 = 1;
            } else {
                etLength1.setEnabled(false);
                etVop1.setEnabled(false);
                etLength1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                etVop1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvlength1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvvop1.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvvoplength.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                tvvopu.setTextColor(getBaseContext().getResources().getColor(R.color.T_99));
                catlead1 = 0;
                // Log.e("leadCat", "leadCat" +leadCat);
            }
        });
        etVop1.addTextChangedListener(new TextWatcher() {
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
                        etVop1.setText(maxVop + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }

    /*
     **  保存延长线参数  //jk20210202
     */
    private void saveCableInit() {
        //测试缆波速度限制
        if (!TextUtils.isEmpty(etVop1.getText().toString())) {
            double vop = Double.parseDouble(etVop1.getText().toString());
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
                etVop1.setText(maxVop + "");
            }
            if (vop < minVop) {
                etVop1.setText(minVop + "");
            }
        }

        //是否有测试缆信息
        boolean testLead;
        testLead = cbtestlead1.isChecked();
        //测试线缆长度、波速度
        String length = etLength1.getText().toString();
        String vop = etVop1.getText().toString();
        //实体类paramInfo的传递值setter
        ParamInfo paramInfo = (ParamInfo) StateUtils.getObject(ModeActivity.this, Constant.PARAM_INFO_KEY);
        if (paramInfo == null) {
            paramInfo = new ParamInfo();
        }
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
        StateUtils.setObject(ModeActivity.this, paramInfo, Constant.PARAM_INFO_KEY);

        Toast.makeText(ModeActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();

    }

    /*
     **  下载新版本
     */
    private String version = "1";
    private String url = "baidu.com";
    private AppUpdateDialog mAppUpdateDialog;

    public void downloadFile1() {
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
                                            handler1.sendEmptyMessage(0);
                                        }
                                    } else {
                                        Toast.makeText(ModeActivity.this, R.string.has_new_version, Toast.LENGTH_SHORT).show();
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

    private Handler handler1 = new Handler(new Handler.Callback() {
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

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {  //jk20210204  退出到桌面但没退出APP 需要添加退出APP程序

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private boolean mIsExit;

    @Override
    /**
     * 双击返回键退出
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {

               /* Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);*/
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                return true;

            } else {
                //Toast.makeText(this, "再按一次回到桌面", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "再按一次回到桌面", Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 5000);

            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * //G? 不知道干啥
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) { //No call for super(). Bug on API Level > 11. // super.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }

}
