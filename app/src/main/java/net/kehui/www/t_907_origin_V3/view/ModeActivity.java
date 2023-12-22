package net.kehui.www.t_907_origin_V3.view;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import net.kehui.www.t_907_origin_V3.ConnectService;
import net.kehui.www.t_907_origin_V3.R;
import net.kehui.www.t_907_origin_V3.adpter.MyChartAdapterBase;
import net.kehui.www.t_907_origin_V3.application.AppConfig;
import net.kehui.www.t_907_origin_V3.application.Constant;
import net.kehui.www.t_907_origin_V3.application.MyApplication;
import net.kehui.www.t_907_origin_V3.base.BaseActivity;
import net.kehui.www.t_907_origin_V3.entity.ParamInfo;
import net.kehui.www.t_907_origin_V3.fragment.AdjustFragment;
import net.kehui.www.t_907_origin_V3.fragment.ModeFragment;
import net.kehui.www.t_907_origin_V3.fragment.OperationFragment;
import net.kehui.www.t_907_origin_V3.fragment.RangeFragment;
import net.kehui.www.t_907_origin_V3.ui.AppUpdateDialog;
import net.kehui.www.t_907_origin_V3.ui.AutoDialog;
import net.kehui.www.t_907_origin_V3.ui.HelpModeDialog;
import net.kehui.www.t_907_origin_V3.ui.KBubbleSeekBar12;
import net.kehui.www.t_907_origin_V3.ui.KBubbleSeekBar16;
import net.kehui.www.t_907_origin_V3.ui.KBubbleSeekBar32;
import net.kehui.www.t_907_origin_V3.ui.LanguageChangeDialog;
import net.kehui.www.t_907_origin_V3.ui.MoveView;
import net.kehui.www.t_907_origin_V3.ui.MoveWaveView;
import net.kehui.www.t_907_origin_V3.ui.NoteDialog;
import net.kehui.www.t_907_origin_V3.ui.WarningNoteDialog;
import net.kehui.www.t_907_origin_V3.ui.SaveRecordsDialog;
import net.kehui.www.t_907_origin_V3.ui.ShowRecordsDialog;
import net.kehui.www.t_907_origin_V3.ui.SparkView.SparkView;
import net.kehui.www.t_907_origin_V3.ui.HvWaitTriggerDialog;
import net.kehui.www.t_907_origin_V3.ui.SwitchOnNoteDialog;
import net.kehui.www.t_907_origin_V3.ui.EnsureHVDialog;
import net.kehui.www.t_907_origin_V3.ui.SwitchOnDialog;
import net.kehui.www.t_907_origin_V3.ui.SwitchingDialog;
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
import static net.kehui.www.t_907_origin_V3.application.Constant.isShowEnsureHV;
import static net.kehui.www.t_907_origin_V3.application.Constant.isShowHV;
import static net.kehui.www.t_907_origin_V3.application.Constant.isShowHVWait;
import static net.kehui.www.t_907_origin_V3.application.Constant.waveLen;

public class ModeActivity extends BaseActivity {

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
    @BindView(R.id.tv_zero)
    ImageView tvZero;
    @BindView(R.id.tv_cursor_plus)
    ImageView tvCursorPlus;
    @BindView(R.id.tv_cursor_min)
    ImageView tvCursorMin;
    @BindView(R.id.iv_AUTO)
    ImageView ivAUTO;
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
    @BindView(R.id.tv_vop_j)
    ImageView tvVopj;
    @BindView(R.id.tv_vop_d)
    ImageView tvVopd;
    @BindView(R.id.tv_vop_g)
    ImageView tvVopg;
    @BindView(R.id.ll_cal_adjust)
    LinearLayout llCalAdjust;
    @BindView(R.id.iv_cal_close1)
    ImageView ivCalClose1;
    @BindView(R.id.ll_lead)  //jk20210202
    LinearLayout llLead;
    @BindView(R.id.tv_file_records)
    ImageView tvFileRecords;
    @BindView(R.id.ll_records)
    LinearLayout llRecords;
    @BindView(R.id.iv_records_close)
    ImageView ivRecordsClose;
    @BindView(R.id.tv_decay_value)
    TextView tvDelayValue;
    @BindView(R.id.tv_delay_text)
    TextView tvDelayText;
    @BindView(R.id.tv_records_save)
    ImageView tvRecordsSave;
    @BindView(R.id.iv_wifi_status)
    ImageView ivWifiStatus;
    @BindView(R.id.iv_battery_status)
    ImageView ivBatteryStatus;
    @BindView(R.id.tv_balance_text)
    TextView tvBalanceText;
    @BindView(R.id.tv_balance_space)
    TextView tvBalanceSpace;
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
    @BindView(R.id.tv_operation1)
    ImageView tvWave1;
    @BindView(R.id.cb_test_lead1)
    CheckBox cbtestlead1;
    @BindView(R.id.et_pulse_length_id)
    EditText etLength1;
    @BindView(R.id.et_pulse_vop_id)
    EditText etVop1;
    @BindView(R.id.tv_lead_save)
    ImageView tvLeadSave;
    @BindView(R.id.iv_lead_close)
    ImageView ivLeadClose;
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
    @BindView(R.id.ll_AUTO)
    LinearLayout llAUTO;
    @BindView(R.id.tv_info_mode)
    TextView tvInfoMode;
    @BindView(R.id.ll_info_hv)
    LinearLayout llInfoHv;
    @BindView(R.id.iv_info_voltage_height)
    ImageView ivInfoVoltageHeight;
    /**
     * 添加波宽度保存
     */
    @BindView(R.id.ll_pulse_width)
    LinearLayout llPulseWidth;
    @BindView(R.id.iv_pulse_width_close)
    ImageView ivPulseWidthClose;
    @BindView(R.id.et_pulse_width_id)
    public EditText etPulseWidth;
    @BindView(R.id.tv_mode_working_mode)
    TextView tvModeWorkingMode;
    @BindView(R.id.tv_mode_TIME)
    TextView tvModeTIME;
    @BindView(R.id.ll_info_TIME)
    LinearLayout llInfoTIME;

    private int index;
    //计算滑动时的基数
    private int fenzi1;
    //初始化滑块位置
    private int fenzi2;
    private int currentActionDownX = 0;
    //20200523
    private boolean canClickCancelButton;
    //设置是否需要进入页面接收数据，此处是为了适配从主页面展示 波形时重复接收数据
    private boolean isReceiveData = true;

    private TDialog tDialog;    //正在接收数据对话框

    private FragmentManager fragmentManager;
    private ModeFragment modeFragment;   //jk20210123
    private RangeFragment rangeFragment;
    private AdjustFragment adjustFragment;
    private OperationFragment operationFragment;
    private LanguageChangeDialog languageChangeDialog;  //添加语言切换对话框   //GC20230912

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
                        if (!longTestInit) {
                            longTestInit();
                        } else {
//                            tdrAutoTestLong();
                            if (!balanceIsReady) {
                                selectBalance();
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
                allowSetMode = true;    //波形测试结束后可以点击   //GC20221019
                tvTest.setEnabled(true);
                Log.e("【请求电量时机控制】", "波形绘制完毕，允许请求电量。");  //GC20211209 是否需要添加波形和高压指令的判断？
                //TDR自动测距结束后，且是点击SIM，根据得到的合适范围进入SIM    //GC20220806
                if (Constant.isClickSim && !isLongClick) {
                    Constant.isClickSim = false;
                    clickSim();
                }
                //直闪模式设定电压清零    //GC20221114
                if (mode == ICM_DECAY) {
                    setHv0();
                }
                break;
            case DISPLAY_DATABASE:
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
            handler.sendEmptyMessage(intent.getIntExtra("display_action", 0));

            String action = intent.getAction();
            assert action != null;
            switch (action) {
                case BROADCAST_ACTION_DEVICE_CONNECTED:
                    //服务中toast只可以跟随系统语言     //GC20211214
                    Toast.makeText(ModeActivity.this, R.string.connect_success, Toast.LENGTH_SHORT).show();
                    Log.e("ModeActivity", "连接成功");
                    //网络连接，更换网络图标
                    ConnectService.isConnected = true;
                    ivWifiStatus.setImageResource(R.drawable.ic_wifi_connected);
                    //记录曾经连上过设备   //GC20231111
                    Constant.hasConnected = true;
                    //连接成功后重置离线使用模式的状态，否则联网成功后再断开没有提示   //GC20231111
                    Constant.isOffline = false;
                    //WIFI重连后恢复为默认档位    //GC20220917
                    Constant.gear = 2;
                    //WIFI重连有对话框消对话框    //GC20220920
                    if (tDialog != null) {
                        tDialog.dismiss();              //消正在接收数据对话框
                    }
                    if (hvWaitTriggerDialog != null) {
                        hvWaitTriggerDialog.dismiss();  //消测试等待对话框  //GC20211215
                    }
                    if (autoDialog != null) {
                        autoDialog.dismiss();           //消高压设置对话框
                    }
                    if (switchOnNoteDialog != null) {
                        switchOnNoteDialog.dismiss();   //消合闸提示对话框  //GC20220919
                        Constant.isShowSwitchOnNoteDialog = false;   //取消询问是否合闸对话框   //GC20231112
                    }
                    if (noteDialog != null) {
                        noteDialog.dismiss();           //消警告对话框  //GC20220920
                        Constant.isShowNoteDialog = false;  //GC20231111
                    }
                    if (switchOnDialog != null) {
                        switchOnDialog.dismiss();       //消实时监测合闸提示对话框  //GC20221203
                    }
                    if (warningNoteDialog != null) {
                        warningNoteDialog.dismiss();    //消接地报警提示对话框  //GC20221206
                    }
                    Constant.isTesting = false;
                    Constant.isClickSim = false;    //GC20221123
                    allowSetRange = true;
                    allowSetMode = true;        //重新连接成功后可以点击   //GC20221019
                    allowSetOperation = true;   //重新连接成功后可以点击   //GC20221019
                    alreadyDisplayWave = false;
                    //如果网络连接后于读取本地波形数据，则再网络连接时设置读出的几个参数。
                    if (!isReceiveData || isDatabase) {
                        //读取本地数据时参数设置   //20200523
                        setModeNoCmd(Constant.Para[0]);
                        setRangeNoCmd(Constant.Para[1]);
                        setGain(Constant.Para[2]);
                        setVelocityNoCmd(Constant.Para[3]);
                    } else {
                        //连接设备初始化（包括重连）
                        mode = TDR;
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
//                    Log.e("DIA", "Constant.isOffline = " + Constant.isOffline);
                    //网络断开时，消实时监测未合闸的提示对话框，并且显示设备已断开  //GC20231111
                    if (Constant.hasConnected) {  //连接成功过已断开对话框才会弹出
                        if (!Constant.isOffline) {  //离线使用模式下取消掉对话框也不会再弹出
                            if (!Constant.isShowNoteDialog) { //如果已经ShowNoteDialog就不再执行，否则对话框会闪烁
                                showNoteDialog();
                            }
                        }
                    }
                    /*if (noteDialog != null) {   //改对话框提示为T-A310已断开  //GC20220920
                        noteDialog.tvNote.setText(getResources().getString(R.string.connect_failed));
                    }*/
                    //界面电量状态记录   //GC20200314
                    batteryValue = -1;
                    //WIFI断线后认为分闸   //GC20220726
                    isSwitchOn = false;
                    //WIFI断线后报警状态重置 //GC20220920
                    Constant.isWarning = true;
                    break;
                case BROADCAST_ACTION_DOWIFI_COMMAND:
                    //通过广播得到命令数据wifiStream并直接处理  //GC20211208
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (MainActivity.baseset == 1) {  //直接进入Mode界面的方法修改    //GC20230911
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mode);
            Log.e("【启动页面】", "进入mode页面。");
//            Log.e("【启动页面】", "进入mode页面。" + "baseset" + MainActivity.baseset);
            ButterKnife.bind(this);

            startModeService(); //启动主服务 //GC20230911
            mode = getIntent().getIntExtra(BUNDLE_MODE_KEY, 0x11); //0改为0x11    //GC20230913
            isReceiveData = getIntent().getBooleanExtra("isReceiveData", true);
            initUnit();
            initSparkView();    //画直线
            initViewMoveWave();
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
//        } //GC20230911
//        MainActivity.baseset = 0;   //jk20210206  解决电源按键问题
//        Log.e("【启动页面】", "baseset1" + MainActivity.baseset);
    }

    /**
     *  //启动主服务    //GC20230911
     */
    public void startModeService() {
        Intent intent = new Intent(ModeActivity.this, ConnectService.class);
        startService(intent);
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
        }
        myChartAdapterMainWave = new MyChartAdapterBase(waveArray, null,
                false, 0, false);
        mainWave.setAdapter(myChartAdapterMainWave);
        setMoveView();
//        Constant.WaveData = null;   //需要保存的数据赋值null   //GC20231208
        Constant.allowSave = false;   //初始化不可保存   //GC20231208
        Log.i("Draw", "初始化绘制结束");
        //初始化清空自动测距参考信息    //GC20220808
        tvInformation.setText("");
        //擦波形后SIM序号为0，控制波形上翻、下翻按钮状态 //GC20220820
        selectSim = 0;
        //波形直线状态，控制不可放大缩小还原    //GC20220822
        alreadyDisplayWave = false;
        //计算需要处理波形的原始长度dataLength，横向滑条绘制相关 //GC20220822
        if ((mode == TDR) || (mode == SIM)) {
            dataLength = READ_TDR_SIM[rangeState] - removeTdrSim[rangeState];
        } else if ((mode == ICM) || (mode == DECAY) || (mode == ICM_DECAY)) {
            dataLength = READ_ICM_DECAY[rangeState] - removeIcmDecay[rangeState];
            //250m范围取点
            if (range == RANGE_250) {
                dataLength = dataLength / 2;
            }
        }
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
            //判断是是否画标记光标    //GC20200330    if (mode == SIM) {
            if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
        if (mode == SIM) {
            if (isDatabase) {
                //数据库SIM波形无波形序号
                tvWaveText.setVisibility(View.GONE);
                tvWaveValue.setVisibility(View.GONE);
                tvWaveSpace.setVisibility(View.GONE);
            } else {
                //SIM横向信息栏波形序号显示 //GC20220820
                tvWaveText.setVisibility(View.VISIBLE);
                tvWaveValue.setVisibility(View.VISIBLE);
                tvWaveSpace.setVisibility(View.VISIBLE);
            }
            //波形上翻、下翻按钮状态控制    //GC20220820
            setWavePreviousNext();
        }
        //画波形
        myChartAdapterMainWave.setmTempArray(waveDraw);
        myChartAdapterMainWave.setShowCompareLine(isCom);
        Constant.allowSave = true;   //绘制波形可保存   //GC20231208
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
        allowSetOperation = true;    //波形绘制完成后可以点击   //GC20221019
        setZoomInOutRes();  //GC20220822
    }

    /**
     * 操作栏放大、缩小、还原按钮状态设置   //GC20220822
     */
    public void setZoomInOutRes()  {
        if (!alreadyDisplayWave) {  //波形直线状态，控制不可放大缩小还原
            operationFragment.btnZoomIn.setEnabled(false);
            operationFragment.btnZoomIn.setImageResource(R.drawable.bg_fangda);
            operationFragment.btnZoomOut.setEnabled(false);
            operationFragment.btnZoomOut.setImageResource(R.drawable.bg_suoxiao);
            operationFragment.btnRes.setEnabled(false);
            operationFragment.btnRes.setImageResource(R.drawable.bg_huanyuan);
        } else {
            if (densityMax == 1) {  //如果最大比例为1，控制不可放大缩小还原
                operationFragment.btnZoomIn.setEnabled(false);
                operationFragment.btnZoomIn.setImageResource(R.drawable.bg_fangda);
                operationFragment.btnZoomOut.setEnabled(false);
                operationFragment.btnZoomOut.setImageResource(R.drawable.bg_suoxiao);
                operationFragment.btnRes.setEnabled(false);
                operationFragment.btnRes.setImageResource(R.drawable.bg_huanyuan);
            } else {
                if (density == densityMax) {    //当前比例与最大比例相同
                    operationFragment.btnZoomIn.setEnabled(true);
                    operationFragment.btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector);
                    operationFragment.btnZoomOut.setEnabled(false);
                    operationFragment.btnZoomOut.setImageResource(R.drawable.bg_suoxiao);
                    operationFragment.btnRes.setEnabled(false);
                    operationFragment.btnRes.setImageResource(R.drawable.bg_huanyuan);
                } else {
                    if (density == 1) { //无法再放大
                        operationFragment.btnZoomIn.setEnabled(false);
                        operationFragment.btnZoomIn.setImageResource(R.drawable.bg_fangda);
                    } else {            //可以放大
                        operationFragment.btnZoomIn.setEnabled(true);
                        operationFragment.btnZoomIn.setImageResource(R.drawable.bg_zoom1_selector);
                    }
                    operationFragment.btnZoomOut.setEnabled(true);
                    operationFragment.btnZoomOut.setImageResource(R.drawable.bg_zoom2_selector);
                    operationFragment.btnRes.setEnabled(true);
                    operationFragment.btnRes.setImageResource(R.drawable.bg_res_selector);
                }
            }
        }
    }

    /**
     * 操作栏波形上翻、下翻按钮状态设置   //GC20220820
     */
    public void setWavePreviousNext() {
        if (selectSim == 0) {   //数据库里面的SIM波形   //GC20220820
            operationFragment.btnWavePrevious.setEnabled(false);
            operationFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
            operationFragment.btnWaveNext.setEnabled(false);
            operationFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
        } else if (selectSim == 1) {
            operationFragment.btnWavePrevious.setEnabled(false);
            operationFragment.btnWavePrevious.setImageResource(R.drawable.wave_shang);
            operationFragment.btnWaveNext.setEnabled(true);
            operationFragment.btnWaveNext.setImageResource(R.drawable.bg_wavenext_selector);
        } else if (selectSim == 8) {
            operationFragment.btnWavePrevious.setEnabled(true);
            operationFragment.btnWavePrevious.setImageResource(R.drawable.bg_wavepre_selector);
            operationFragment.btnWaveNext.setEnabled(false);
            operationFragment.btnWaveNext.setImageResource(R.drawable.wave_xia);
        } else {
            operationFragment.btnWavePrevious.setEnabled(true);
            operationFragment.btnWavePrevious.setImageResource(R.drawable.bg_wavepre_selector);
            operationFragment.btnWaveNext.setEnabled(true);
            operationFragment.btnWaveNext.setImageResource(R.drawable.bg_wavenext_selector);
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
                gain = 13 + 3;
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
                gain = 13 + 3;
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
                gain = 13 + 3;
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
                gain = 13 + 3;
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
                gain = 10 + 3;
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
                gain = 10 + 3;
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
                gain = 10 + 3;
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
                gain = 9 + 3;
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
                gain = 9 + 3;
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
                gain = 9 + 3;
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
        //根据方式、范围选取判断收取波形数据的点数
        selectWaveLength();

    }

    /**
     * 根据方式、范围选取判断收取波形数据的点数
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
        //TDR方式下高压操作按钮和信息栏隐藏  //GC20211207
        llAUTO.setVisibility(View.GONE);
        llInfoHv.setVisibility(View.GONE);
        //jk20210123
        fragmentManager = getSupportFragmentManager();
        setTabSelection(1);
        setTabSelection(2);
        setTabSelection(3);
        //第一次启动时选中第0个tab
        setTabSelection(0);
        tvMode1.setImageResource(R.drawable.bg_mode_pressed1);
        Constant.ModeValue = TDR;
        //增益转为百分比   //GC20200313
        int temp = s2b(gain);
        tvGainValue.setText(String.valueOf(temp));
        Constant.Gain = gain;
        velocity = 172;
        if (CurrentUnit == MI_UNIT) {
            tvVopValue.setText(String.valueOf(velocity));
        } else {
            tvVopValue.setText(UnitUtils.miToFt(velocity));
        }
        Constant.Velocity = velocity;
        tvBalanceValue.setText(String.valueOf(balance));
        tvZoomValue.setText("1 : " + density);
        tvDelayValue.setText(delay + "μs");
        //初始化自动测距结果显示    //GC20190708
        tvInformation.setVisibility(View.GONE);
        //SIM光标位置初始化（可以自定义）    //GC20190712
        simOriginalZero = StateUtils.getInt(ModeActivity.this, AppConfig.CURRENT_CURSOR_POSITION, 12);
        //SIM标记光标（可以自定义）   //GC20200612
        simStandardZero = simOriginalZero;
        //实光标初始化（本地存储位置加载）     //GC20220811
        zero = simOriginalZero; //光标原始位置
        positionReal = simOriginalZero; //光标画布位置
        //初始化距离显示
        calculateDistance(Math.abs(pointDistance - zero));
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
                if (y < 600) {
                    if ((Integer) value <= 510) {
                        //画虚光标
                        mainWave.setScrubLineVirtual((Integer) value);
                        //虚光标变化的数值
                        positionVirtualChange = (int) value - positionVirtual;
//                        Log.e("【波形区域虚光标】", "当前位置" + value + " /变化前positionVirtual:" + positionVirtual + " /虚光标变化值:" + positionVirtualChange);
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
                        //判断是是否画标记光标    //GC20200330    if (mode == SIM) {
                        if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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

    /**
     * 选择不同方式下的VIEW状态
     */
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
            System.arraycopy(wifiArray, 8, waveArray, 0, dataMax);
            Constant.WaveData = waveArray;
            //GT屏蔽算法1  //GC20191231
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
                //GT屏蔽算法2    //SIM筛选功能添加 //GC20200529
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
        //ICM SIM判断仪器触发时：APP发送接收数据命令
        if ((wifiArray[5] == COMMAND_TRIGGER) && (wifiArray[6] == TRIGGERED)) {
            command = COMMAND_RECEIVE_WAVE;
            dataTransfer = mode;
            //发送指令
            startService();
            //未显示波形为否   //20200523
            alreadyDisplayWave = false;
            // Log.e("【时效测试】", "发送接收波形数据命令");
            ConnectService.canAskPower = false;
//            if (tDialog != null) {
//                tDialog.dismiss();
//            }
            //收到波形时取消对话框   //GC20211215
            if (hvWaitTriggerDialog != null) {
                hvWaitTriggerDialog.dismiss();
            }
            if (autoDialog != null) {
                autoDialog.dismiss();
            }
            tDialog = new TDialog.Builder(getSupportFragmentManager())
                    .setLayoutRes(R.layout.dialog_receiving_data)
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
        //处理高压数值   //GC20211208
//        else if (wifiArray[5] == COMMAND_VOLTAGE) {
        else if ((wifiArray[5] == COMMAND_VOLTAGE) && (wifiArray[4] == 0x04)) {
            int hvValue = wifiArray[6] * 256 + wifiArray[7];
            //0x0ccc（3276）：32kV   0x0666：16kV   显示数值需要换算一下
            Constant.currentVoltage = hvValue / 3276.0 * 32;  //接收高压数值换算    //GC20211227
//            Constant.currentVoltage = hvValue / 3276.0 * 8;
            //主界面信息栏更新当前电压
            tvInfoHV.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));    //保留两位小数(四舍五入）
            //高压设置对话框更新当前电压   //GC20211210
            if (Constant.isShowHV) {
                autoDialog.tvHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
                if (workingModeData == 0x01) {
                    //当前电压大于2kV     //GC20220803
                    if (Constant.currentVoltage >= 2) {
                        //当前电压数值变化的档位无效  //GC20220913
                        autoDialog.rbGear16.setClickable(false);
                        autoDialog.rbGear32.setClickable(false);
                        switch (currentGear) {
                            case 1:
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            case 2:
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            default:
                                break;
                        }
                    } else {
                        autoDialog.rbGear16.setClickable(true);
                        autoDialog.rbGear32.setClickable(true);
                        //当前电压数值变化的档位恢复  //GC20220913
                        switch (currentGear) {
                            case 1:
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                                break;
                            case 2:
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            if (Constant.isShowHVWait) {    //等待触发界面电容电压数值变化    //GC20221109
                hvWaitTriggerDialog.tvTriggerHVINDICATOR.setText(new DecimalFormat("0.00").format(Constant.currentVoltage));
            }
            //高压数值进度条UI更新   //GC20211214
            handleVoltageHeightView(Constant.currentVoltage);
        }
        //处理高压模块反馈  //GC20211208
        else if (wifiArray[5] == COMMAND_QUERY_FEEDBACK) {
            byte temp = (byte) wifiArray[6];
            byte[] array;
            array = getByteArray(temp);
            //更新合闸反馈
            if (mode != TDR) {  //TDR方式下无需处理合闸反馈    //GC20221203
                Log.e("合闸反馈", "进入实时反馈");
                if (!Constant.isShowSwitchOnNoteDialog) {   //如果在询问是否合闸对话框界面，不更新实时合闸反馈  //GC20231112
                    if (array[2] == 1) {
                        Log.e("合闸反馈", "T-A310处于合闸状态");
                        if (switchOnDialog != null) {
                            Constant.isSwitchOn = true;
                            switchOnDialog.dismiss();   //消实时监测合闸提示对话框  //GC20221203
                        }
                    } else {
                        Log.e("合闸反馈", "T-A310处于未合闸状态");
                        if (Constant.isSwitchOn) {   //添加条件限制，只提示一次 //GC20221203
                            Constant.isSwitchOn = false;
                            showSwitchOnDialog();
                        }
                    }
                }
            }
            //更新接地报警   //GC20211210
            if (array[3] == 1) {
                Log.e("接地报警反馈", "报警" + ConnectService.isConnected);
                if (Constant.isWarning) {   //添加条件限制，只报警一次  //GC20220920
                    Constant.isWarning = false;
                    showWarningNoteDialog();   //接地报警提示优化   //GC20221206
                    /*if (Constant.isShowHV) {
                        autoDialog.ivWaring.setImageResource(R.drawable.light_red);
                        Toast.makeText(ModeActivity.this, R.string.hv_warning_note2, Toast.LENGTH_LONG).show();
                    }*/
                }
            } else {
                Log.e("接地报警反馈", "正常" );
                if (warningNoteDialog != null) {   //接地报警退出优化   //GC20221206
                    Constant.isWarning = true;
                    warningNoteDialog.dismiss();   //消接地报警提示对话框
                }
                /*if (Constant.isShowHV) {
                    autoDialog.ivWaring.setImageResource(R.drawable.light_gray);
                }*/
            }
            //更新高压包故障
            if (array[4] == 1) {
                Log.e("高压包故障反馈", "报警" + ConnectService.isConnected);
                if (Constant.isIgnitionCoil) {
                    Constant.isIgnitionCoil = false;
                    if (Constant.isShowHV) {
                        autoDialog.tvHvIgnitionCoil.setText(getResources().getString(R.string.hv_ignition_coil_note2));
                        autoDialog.tvHvIgnitionCoil.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                    }
//                    Toast.makeText(ModeActivity.this, R.string.hv_ignition_coil_note2, Toast.LENGTH_LONG).show();
                    showNoteDialog();
                }
            } else {
                Log.e("高压包故障反馈", "正常" );
                /*Constant.isIgnitionCoil = true;
                if (Constant.isShowHV) {
                    autoDialog.tvHvIgnitionCoil.setText(getResources().getString(R.string.hv_ignition_coil_note));
                    autoDialog.tvHvIgnitionCoil.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimary));
                }*/
            }
            //更新电容有残压
            if (array[5] == 1) {
                Log.e("电容有残压反馈", "报警" + ConnectService.isConnected);
                if (Constant.isCapacitor) {
                    Constant.isCapacitor = false;
                    if (Constant.isShowHV) {
                        autoDialog.tvHvCapacitor.setText(getResources().getString(R.string.hv_capacity_note2));
                        autoDialog.tvHvCapacitor.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                    }
                    Toast.makeText(ModeActivity.this, R.string.hv_capacity_note2, Toast.LENGTH_LONG).show();
                }
            } else {
                Log.e("电容有残压反馈", "正常" );
                /*Constant.isCapacitor = true;
                if (Constant.isShowHV) {
                    autoDialog.tvHvCapacitor.setText(getResources().getString(R.string.hv_capacity_note));
                    autoDialog.tvHvCapacitor.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimary));
                }*/
            }
            //更新工作方式故障
            if (array[6] == 1) {
                Log.e("工作方式故障反馈", "报警" + ConnectService.isConnected);
                if (Constant.isWorkingMode) {
                    Constant.isWorkingMode = false;
                    if (Constant.isShowHV) {
                        autoDialog.tvHvWorkingMode.setText(getResources().getString(R.string.hv_working_mode_note2));
                        autoDialog.tvHvWorkingMode.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                    }
                    Toast.makeText(ModeActivity.this, R.string.hv_working_mode_note2, Toast.LENGTH_LONG).show();
                    //报警时取消方式转换倒计时 //GC20220710
                    timerMode.cancel();
                    showNoteDialog();
                }
            } else {
                Log.e("工作方式故障反馈", "正常" );
                /*Constant.isWorkingMode = true;
                if (Constant.isShowHV) {
                    autoDialog.tvHvWorkingMode.setText(getResources().getString(R.string.hv_working_mode_note));
                    autoDialog.tvHvWorkingMode.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimary));
                }*/
            }
            //更新电压档位故障
            if (array[7] == 1) {
                Log.e("电压档位故障反馈", "报警" + ConnectService.isConnected);
                if (Constant.isGear) {
                    Constant.isGear = false;
                    if (Constant.isShowHV) {
                        autoDialog.tvHvGear.setText(getResources().getString(R.string.hv_gear_note2));
                        autoDialog.tvHvGear.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                    }
                    Toast.makeText(ModeActivity.this, R.string.hv_gear_note2, Toast.LENGTH_LONG).show();
                    //报警时取消档位转换倒计时 //GC20220712
                    timerGear.cancel();
                    //电压档位故障
                    showNoteDialog();
                }
            } else {
                Log.e("电压档位故障反馈", "正常" );
               /* Constant.isGear = true;
                if (Constant.isShowHV) {
                    autoDialog.tvHvGear.setText(getResources().getString(R.string.hv_gear_note));
                    autoDialog.tvHvGear.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimary));
                }*/
            }
        }
        //处理主动检测是否合闸的反馈  //GC20220919
        else if (wifiArray[5] == COMMAND_SWITCH_ON_QUERY) {
            if (wifiArray[6] == 0x00) {
                Log.e("合闸状态反馈", "未合闸" );
                switchOnNoteDialog.tvNoteSwitchOn.setText(getResources().getString(R.string.switch_on_note3));
                switchOnNoteDialog.tvNoteSwitchOn.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                switchOnNoteDialog.ivScanSwitchOn.setVisibility(View.VISIBLE);
                switchOnNoteDialog.tvYes.setVisibility(View.INVISIBLE);
            } else if (wifiArray[6] == 0x01) {
                Log.e("合闸状态反馈", "合闸" );
                switchOnNoteDialog.tvNoteSwitchOn.setText(getResources().getString(R.string.switch_on_note2));
                switchOnNoteDialog.tvNoteSwitchOn.setTextColor(getBaseContext().getResources().getColor(R.color.blue_0a9bbe));
                switchOnNoteDialog.ivScanSwitchOn.setVisibility(View.GONE);
                switchOnNoteDialog.tvYes.setVisibility(View.VISIBLE);
            }
        }
        //TODO 20200407 普通命令解析完毕，允许请求电量
        if (!Constant.isTesting) {
            ConnectService.canAskPower = true;
            Log.e("【请求电量时机控制】", "触发和电量命令数据处理完毕，允许请求电量。");
        }

    }

    /**
     * 高压数值进度条高度计算和实现    //GC20211214
     */
    private int heightPosition;

    private void handleVoltageHeightView(double hvValue) {
        //根据最大值计算进度条高度
//        double a = hvValue / Constant.setVoltage;
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
        double a = hvValue / maxHV;

        int b = (int) (a * 100);
        if (b >= 0 && b < 10) {
            heightPosition = 0;
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
        changeInfoVoltageHeightView(heightPosition);
        if (Constant.isShowHV) {
            changeVoltageHeightView(heightPosition);
        }
        if (Constant.isShowHVWait) {    //等待触发界面电压进度条变化 //GC20221109
            changeWaitVoltageHeightView(heightPosition);
        }
    }

    /**
     * 信息栏进度条变换
     *
     * @param position
     */
    public void changeInfoVoltageHeightView(int position) {
        switch (position) {
            case 0:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_0);
                break;
            case 1:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_1);
                break;
            case 2:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_2);
                break;
            case 3:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_3);
                break;
            case 4:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_4);
                break;
            case 5:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_5);
                break;
            case 6:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_6);
                break;
            case 7:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_7);
                break;
            case 8:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_8);
                break;
            case 9:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_9);
                break;
            case 10:
                ivInfoVoltageHeight.setImageResource(R.drawable.ic_vltage_height_10);
                break;
            default:
                break;
        }

    }

    /**
     * 高压操作对话框进度条变换
     *
     * @param position
     */
    public void changeVoltageHeightView(int position) {
        switch (position) {
            case 0:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_0);
                break;
            case 1:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_1);
                break;
            case 2:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_2);
                break;
            case 3:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_3);
                break;
            case 4:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_4);
                break;
            case 5:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_5);
                break;
            case 6:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_6);
                break;
            case 7:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_7);
                break;
            case 8:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_8);
                break;
            case 9:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_9);
                break;
            case 10:
                autoDialog.ivVoltageHeight.setImageResource(R.drawable.ic_vltage_height_10);
                break;
            default:
                break;
        }

    }

    /**
     * 等待触发操作对话框电压进度条变化   //GC20221109
     *
     * @param position
     */
    public void changeWaitVoltageHeightView(int position) {
        switch (position) {
            case 0:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_0);
                break;
            case 1:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_1);
                break;
            case 2:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_2);
                break;
            case 3:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_3);
                break;
            case 4:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_4);
                break;
            case 5:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_5);
                break;
            case 6:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_6);
                break;
            case 7:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_7);
                break;
            case 8:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_8);
                break;
            case 9:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_9);
                break;
            case 10:
                hvWaitTriggerDialog.ivTriggerVoltageHeight.setImageResource(R.drawable.ic_vltage_height_10);
                break;
            default:
                break;
        }

    }

    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getByteArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte) (b & 1);
            b = (byte) (b >> 1);
        }
        return array;
    }

    /**
     * 不同方式VIEW初始化    //GC20220819
     */
    private void initTDRView() {
        //画布侧边滑条不显示
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        //横向信息栏
        tvMode.setText(getResources().getText(R.string.btn_tdr));
        tvDelayText.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        tvBalanceText.setVisibility(View.VISIBLE);
        tvBalanceSpace.setVisibility(View.VISIBLE);
        tvBalanceValue.setVisibility(View.VISIBLE);
        //竖向信息栏  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_tdr));
        //TDR高压操作按钮和竖向信息栏隐藏
        llAUTO.setVisibility(View.GONE);
        llInfoHv.setVisibility(View.GONE);
    }

    private void initTDRFragment() {
        //方式栏fragment显示
        modeFragment.btnTdr.setEnabled(false);
        modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_pressed);
        modeFragment.btnIcm.setEnabled(true);
        modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_normal11); //GC20221011
        modeFragment.btnIcmc.setEnabled(true);
        modeFragment.btnIcmc.setImageResource(R.drawable.icmz1);
        modeFragment.btnSim.setEnabled(true);
        modeFragment.btnSim.setImageResource(R.drawable.bg_mim_mode_normal);
        modeFragment.btnDecay.setEnabled(true);
        modeFragment.btnDecay.setImageResource(R.drawable.bg_decay_mode_normal);
        //调节栏fragment显示
        adjustFragment.btnGainPlus.setEnabled(true);
        adjustFragment.btnGainMinus.setEnabled(true);
        adjustFragment.btnBalancePlus.setVisibility(View.VISIBLE);
        adjustFragment.btnBalanceMinus.setVisibility(View.VISIBLE);
        adjustFragment.btnDelayPlus.setVisibility(View.GONE);
        adjustFragment.btnDelayMinus.setVisibility(View.GONE);
        //操作栏fragment显示
        operationFragment.btnMemory.setVisibility(View.VISIBLE);
        operationFragment.btnCompare.setVisibility(View.VISIBLE);
        operationFragment.btnWavePrevious.setVisibility(View.GONE);
        operationFragment.btnWaveNext.setVisibility(View.GONE);
        operationFragment.btnZero.setVisibility(View.VISIBLE);  //TDR光标零点按钮显示  //GC20220811
        operationFragment.btnLead1.setVisibility(View.GONE);
    }

    private void initICMSURGEView() {
        //画布侧边滑条不显示
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        //横向信息栏
        tvMode.setText(getResources().getText(R.string.btn_icm));
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.VISIBLE);
        tvDelayValue.setVisibility(View.VISIBLE);
        tvDelaySpace.setVisibility(View.VISIBLE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        //竖向信息栏  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_icm));
        //非TDR高压操作按钮和竖向信息栏显示
        llAUTO.setVisibility(View.VISIBLE);
        llInfoHv.setVisibility(View.VISIBLE);
        //竖向信息栏放电周期、工作方式显示  //GC20220914
        tvModeWorkingMode.setVisibility(View.VISIBLE);
        tvInfoWorkingMode.setVisibility(View.VISIBLE);
        tvModeTIME.setVisibility(View.VISIBLE);
        llInfoTIME.setVisibility(View.VISIBLE);
    }

    private void initICMSURGEFragment() {
        //方式栏fragment显示
        modeFragment.btnTdr.setEnabled(true);
        modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_normal);
        //定点模式添加    //GC20220809
        if (Constant.isClickLocate) {
            modeFragment.btnIcm.setEnabled(true);
            modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_normal11);  //GC20221011
            modeFragment.btnIcml.setEnabled(false);
            modeFragment.btnIcml.setImageResource(R.drawable.bg_locate_mode_pressed);
        } else {
            modeFragment.btnIcm.setEnabled(false);
            modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_pressed11);  //GC20221011
            modeFragment.btnIcml.setEnabled(true);
            modeFragment.btnIcml.setImageResource(R.drawable.bg_locate_mode_normal);
        }
        modeFragment.btnIcmc.setEnabled(true);
        modeFragment.btnIcmc.setImageResource(R.drawable.icmz1);
        modeFragment.btnSim.setEnabled(true);
        modeFragment.btnSim.setImageResource(R.drawable.bg_mim_mode_normal);
        modeFragment.btnDecay.setEnabled(true);
        modeFragment.btnDecay.setImageResource(R.drawable.bg_decay_mode_normal);
        //调节栏fragment显示
        adjustFragment.btnGainPlus.setEnabled(true);
        adjustFragment.btnGainMinus.setEnabled(true);
        adjustFragment.btnBalancePlus.setVisibility(View.GONE);
        adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
        adjustFragment.btnDelayPlus.setVisibility(View.VISIBLE);
        adjustFragment.btnDelayMinus.setVisibility(View.VISIBLE);
        //操作栏fragment显示
        operationFragment.btnMemory.setVisibility(View.VISIBLE);
        operationFragment.btnCompare.setVisibility(View.VISIBLE);
        operationFragment.btnWavePrevious.setVisibility(View.GONE);
        operationFragment.btnWaveNext.setVisibility(View.GONE);
        operationFragment.btnZero.setVisibility(View.GONE);
//        operationFragment.btnLead1.setVisibility(View.VISIBLE);   //GC20220914    “延长线”按钮不显示
    }

    private void initICMDECAYView() {
        //画布侧边滑条不显示
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        //横向信息栏
        tvMode.setText(getResources().getText(R.string.btn_icm_decay));
        //信息框比例前方多了空格   布局修正  //GC20200525
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        //竖向信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_icm_decay));
        //非TDR高压操作按钮和竖向信息栏显示    //GC20211207
        llAUTO.setVisibility(View.GONE); //GC20221011    旧VISIBLE
        llInfoHv.setVisibility(View.VISIBLE);
        //竖向信息栏放电周期、工作方式显示  //GC20220914
        tvModeWorkingMode.setVisibility(View.VISIBLE);
        tvInfoWorkingMode.setVisibility(View.VISIBLE);
        //直闪方式无周期显示 //GC20221205
        tvModeTIME.setVisibility(View.INVISIBLE);
        llInfoTIME.setVisibility(View.INVISIBLE);
    }

    private void initICMDECAYFragment() {
        //方式栏fragment显示
        modeFragment.btnTdr.setEnabled(true);
        modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_normal);
        modeFragment.btnIcm.setEnabled(true);
        modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_normal11); //GC20221011
        modeFragment.btnIcmc.setEnabled(false);
        modeFragment.btnIcmc.setImageResource(R.drawable.icmz);
        modeFragment.btnSim.setEnabled(true);
        modeFragment.btnSim.setImageResource(R.drawable.bg_mim_mode_normal);
        modeFragment.btnDecay.setEnabled(true);
        modeFragment.btnDecay.setImageResource(R.drawable.bg_decay_mode_normal);
        //调节栏fragment显示
        adjustFragment.btnGainPlus.setEnabled(true);
        adjustFragment.btnGainMinus.setEnabled(true);
        adjustFragment.btnBalancePlus.setVisibility(View.GONE);
        adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
        adjustFragment.btnDelayPlus.setVisibility(View.GONE);   //直闪方式没有延时 //GC20231207
        adjustFragment.btnDelayMinus.setVisibility(View.GONE);
        //操作栏fragment显示
        operationFragment.btnMemory.setVisibility(View.VISIBLE);
        operationFragment.btnCompare.setVisibility(View.VISIBLE);
        operationFragment.btnWavePrevious.setVisibility(View.GONE);
        operationFragment.btnWaveNext.setVisibility(View.GONE);
        operationFragment.btnZero.setVisibility(View.GONE);
//        operationFragment.btnLead1.setVisibility(View.VISIBLE);   //GC20220914    “延长线”按钮不显示
    }

    private void initSIMView() {
        //画布侧边滑条显示
        viewMoveVerticalWave.setVisibility(View.VISIBLE);
        //横向信息栏
        tvMode.setText(getResources().getText(R.string.btn_sim));
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.VISIBLE);
        tvDelayValue.setVisibility(View.VISIBLE);
        tvDelaySpace.setVisibility(View.VISIBLE);   //SIM波形序号显示后前面没有空格  //GC20220812
        //竖向信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_sim));
        //非TDR高压操作按钮和竖向信息栏显示    //GC20211207
        llAUTO.setVisibility(View.VISIBLE);
        llInfoHv.setVisibility(View.VISIBLE);
        //竖向信息栏放电周期、工作方式不显示     //GC20220914 mode界面UI修改
        tvModeWorkingMode.setVisibility(View.INVISIBLE);
        tvInfoWorkingMode.setVisibility(View.INVISIBLE);
        tvModeTIME.setVisibility(View.INVISIBLE);
        llInfoTIME.setVisibility(View.INVISIBLE);
    }

    private void initSIMFragment() {
        //方式栏fragment显示
        modeFragment.btnTdr.setEnabled(true);
        modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_normal);
        modeFragment.btnIcm.setEnabled(true);
        modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_normal11);  //GC20221011
        modeFragment.btnIcmc.setEnabled(true);
        modeFragment.btnIcmc.setImageResource(R.drawable.icmz1);
        modeFragment.btnSim.setEnabled(false);
        modeFragment.btnSim.setImageResource(R.drawable.bg_mim_mode_pressed);
        modeFragment.btnDecay.setEnabled(true);
        modeFragment.btnDecay.setImageResource(R.drawable.bg_decay_mode_normal);
        //调节栏fragment显示
        adjustFragment.btnGainPlus.setEnabled(true);
        adjustFragment.btnGainMinus.setEnabled(true);
        adjustFragment.btnBalancePlus.setVisibility(View.GONE);
        adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
        adjustFragment.btnDelayPlus.setVisibility(View.GONE);   //SIM方式没有延时 //GC20230911
        adjustFragment.btnDelayMinus.setVisibility(View.GONE);
        //操作栏fragment显示
        operationFragment.btnMemory.setVisibility(View.GONE);
        operationFragment.btnCompare.setVisibility(View.GONE);
        operationFragment.btnWavePrevious.setVisibility(View.VISIBLE);
        operationFragment.btnWaveNext.setVisibility(View.VISIBLE);
        operationFragment.btnZero.setVisibility(View.VISIBLE);
        operationFragment.btnLead1.setVisibility(View.GONE);
        //波形上翻、下翻按钮状态控制    //GC20220820
        setWavePreviousNext();
    }

    private void initDecayView() {
        //画布侧边滑条不显示
        viewMoveVerticalWave.setVisibility(View.INVISIBLE);
        //横向信息栏
        tvMode.setText(getResources().getText(R.string.btn_decay));
        tvBalanceText.setVisibility(View.GONE);
        tvBalanceValue.setVisibility(View.GONE);
        tvBalanceSpace.setVisibility(View.GONE);
        tvDelayText.setVisibility(View.GONE);
        tvDelayValue.setVisibility(View.GONE);
        tvDelaySpace.setVisibility(View.GONE);
        tvWaveText.setVisibility(View.GONE);
        tvWaveValue.setVisibility(View.GONE);
        tvWaveSpace.setVisibility(View.GONE);
        //竖向信息栏方式初始化  //GC20211207
        tvInfoMode.setText(getResources().getText(R.string.btn_decay));
        //非TDR高压操作按钮和竖向信息栏显示    //GC20211207
        llAUTO.setVisibility(View.VISIBLE);
        llInfoHv.setVisibility(View.VISIBLE);
        //竖向信息栏放电周期、工作方式显示  //GC20220914
        tvModeWorkingMode.setVisibility(View.VISIBLE);
        tvInfoWorkingMode.setVisibility(View.VISIBLE);
        tvModeTIME.setVisibility(View.VISIBLE);
        llInfoTIME.setVisibility(View.VISIBLE);
    }

    private void initDecayFragment() {
        //方式栏fragment显示
        modeFragment.btnTdr.setEnabled(true);
        modeFragment.btnTdr.setImageResource(R.drawable.bg_tdr_mode_normal);
        modeFragment.btnIcm.setEnabled(true);
        modeFragment.btnIcm.setImageResource(R.drawable.bg_icms_mode_normal11);  //GC20221011
        modeFragment.btnIcmc.setEnabled(true);
        modeFragment.btnIcmc.setImageResource(R.drawable.icmz1);
        modeFragment.btnSim.setEnabled(true);
        modeFragment.btnSim.setImageResource(R.drawable.bg_mim_mode_normal);
        modeFragment.btnDecay.setEnabled(false);
        modeFragment.btnDecay.setImageResource(R.drawable.bg_decay_mode_pressed);
        //调节栏fragment显示
        adjustFragment.btnGainPlus.setEnabled(true);
        adjustFragment.btnGainMinus.setEnabled(true);
        adjustFragment.btnBalancePlus.setVisibility(View.GONE);
        adjustFragment.btnBalanceMinus.setVisibility(View.GONE);
        adjustFragment.btnDelayPlus.setVisibility(View.GONE);
        adjustFragment.btnDelayMinus.setVisibility(View.GONE);
        //操作栏fragment显示
        operationFragment.btnMemory.setVisibility(View.VISIBLE);
        operationFragment.btnCompare.setVisibility(View.VISIBLE);
        operationFragment.btnWavePrevious.setVisibility(View.GONE);
        operationFragment.btnWaveNext.setVisibility(View.GONE);
        operationFragment.btnZero.setVisibility(View.GONE);
        operationFragment.btnLead1.setVisibility(View.GONE);
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
    private void tdrCurveFitting(){
        //判断低压脉冲波形向上还是向下
        tdrF();//jk20220711tdr
        findExtremePoint();
        Log.e("tdrExtreme", " /tdrExtreme = " + tdrExtreme);
        Log.e("tdrTurning", " /tdrTurning = " + tdrTurning);
        double[] waveArray1 = new double[60050];
        //以高度128为零点
        if(tdrExtreme - tdrTurning > 0){
            for (int j = tdrTurning; j < tdrExtreme; j++) {
                waveArray1[j] = waveArray[j] - medianValue;
            }
            //曲线拟合部分
            double[] X = new double[1000];
            double[] Y = new double[1000];
            double[] atemp = new double[8];
            double[] b = new double[4];
            double[][] a = new double[4][4];

            if (tdrExtreme - tdrTurning < 1000) { //jk20221020
                for (int h = tdrTurning; h < tdrExtreme; h++) {
                    X[h - tdrTurning] = h - tdrTurning;
                    Y[h - tdrTurning] = tdrFilter[h];    //jk20220711tdr
                }
                for (int i = 0; i < tdrExtreme - tdrTurning; i++) {
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

                atemp[0] = tdrExtreme - tdrTurning;

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

                int autoLocationTemp;
                //盛金公式  //jk20220711tdr
                autoLocationTemp = solve3Polynomial(b[3], b[2], b[1], b[0]) + tdrTurning;
                if(Math.abs(autoLocationTemp - tdrTurning) > pulseRemovePoint[rangeState]){
                    autoLocationTemp = tdrTurning;
                }
                if (autoLocationTemp <= pulseRemovePoint[rangeState] * 2) {
                    autoLocationTemp = simOriginalZero;   //更改自动测距时TDR实光标零点位置     //GC20220811  0
                }
                tdrAutoLocation = autoLocationTemp; //jk20220711tdr
            }else {
                tdrAutoLocation = tdrTurning;
            }
        }else{
            tdrAutoLocation = simOriginalZero;   //更改自动测距时TDR实光标零点位置     //GC20220811  0
        }
        Log.e("tdrAutoLocation", " /tdrAutoLocation = " + tdrAutoLocation);

    }

    /**
     * 滤波   //jk20220711tdr
     */
    private void tdrF(){
        double k = 0.9139;
        tdrFilter[0] = (double) (waveArray[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            tdrFilter[i] = k * tdrFilter[i - 1] + k * (double) (waveArray[i] - waveArray[i - 1]);
        }
    }

    /**
     * TDR光标自动定位
     */
    private void tdtAutoCursor() {
        //实光标固定在零点
        zero = simOriginalZero;     //更改自动测距时TDR实光标零点位置     //GC20220811  0
        pointDistance = tdrAutoLocation;
        if (range == RANGE_250) {
            zero =  2 * zero;
            pointDistance = 2 * pointDistance;
        }
        if (zero >= (currentMoverPosition510 * dataLength / 510) && zero <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionReal = zero / density;   //GC20220811   更改自动测距时绘制TDR实光标零点位置 0
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
        calculateDistance(Math.abs(pointDistance - zero));
    }

    /**
     * TDR长按测试按键，初始化到500m范围，寻找合适的平衡、范围、增益后再自动定位  //GC20200916
     * 步骤1：初始化到500m范围和平衡中间档位，获取波形数据用作后续运算判断
     */
    private void longTestInit() {
        //初始化到500m范围
        if (range == RANGE_500) {
            gain = 13 + 3;
            setGain(gain);
        } else {
            /*setRange(0x11);
            setGain(gain);
            if (!hasSavedPulseWidth) {
                pulseWidth = 40;
                etPulseWidth.setText(String.valueOf(40));
            }
            setPulseWidth(pulseWidth);*/
            //由ICM方式切换时无延时命令会无效    //GC20220806
            handler.postDelayed(() -> {
                //范围
                setRange(0x11);
            }, 20);
            handler.postDelayed(() -> {
                //增益
                setGain(gain);
            }, 20);
            handler.postDelayed(() -> {
                if (!hasSavedPulseWidth) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                //脉宽
                setPulseWidth(pulseWidth);
            }, 20);
        }
        //初始化到平衡中间档位（0-15）
//        balance = 8;
//        setBalance(balance);  //平衡不重置，跟随之前    //GC20231027
        longTestInit = true;
        handler.postDelayed(ModeActivity.this::clickTest, 100);
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
    }

    /**
     * 步骤3：寻找合适的范围
     */
    int rangeCount = 1;
    private void selectRange() {
        int i;
        int max1 = 0;
        int sub1;

        gainJudgmentTdr1();
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

        //计算波形有效数据的极值   //jk20200904 更改起始判断位置   //jk20220922 调整位置
        for (i = pulseRemoveTdrWave[rangeState] * 2 ; i < dataMax - removeTdrSim[rangeState]-30; i++) {  //jk20220711tdrRange pulselongtdrRemove数组变动
            sub1 = waveArray[i] - medianValue;
            if (Math.abs(sub1) > max1) {
                max1 = Math.abs(sub1);
            }
        }
        Log.e(" max1", " max1" +  max1);

        //更改范围切换条件，采用找极值点的方式进行查找    //jk20220711tdrRange
        findExtremePointRange();

        Log.e(" rangeAdjust", " rangeAdjust" +  rangeAdjust);
        if ((rangeAdjust == 0)&&(max1 <= 20)) { //jk20220711tdrRange    //GT20220822 旧30 改为20
            rangeCount++;
            Log.e("tdr", "rangeCount" + rangeCount);
            switch (rangeCount) {
                case 2:
                    range = 0x22;   //GC20220731
                    setRange(0x22);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 80;
                            setPulseWidth(80);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(80));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 3:
                    range = 0x33;   //GC20220731
                    setRange(0x33);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 160;
                            setPulseWidth(160);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(160));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 4:
                    range = 0x44;
                    setRange(0x44);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 320;
                            setPulseWidth(320);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(320));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 5:
                    range = 0x55;
                    setRange(0x55);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 640;
                            setPulseWidth(640);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(640));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 6:
                    range = 0x66;
                    setRange(0x66);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 1280;
                            setPulseWidth(1280);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(1280));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 7:
                    range = 0x77;
                    setRange(0x77);
                    setGain(gain);
                    if (!hasSavedPulseWidth && mode == TDR) {
                        handler.postDelayed(() -> {
                            pulseWidth = 2560;
                            setPulseWidth(2560);
                        }, 20);
                        etPulseWidth.setText(String.valueOf(2560));
                    }
                    handler.postDelayed(ModeActivity.this::clickTest, 100);
                    return;
                case 8:
                    range = 0x88;
                    setRange(0x88);
                    setGain(gain);
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
                    break;
                default:
                    break;
            }
        }
        //范围调整结束
        rangeMemory = range;    //GC20220731
        rangeCount = 1;
        rangeAdjust = 1; //jk20220711tdrRange
        rangeIsReady = true;
        handler.postDelayed(ModeActivity.this::clickTest, 100);
    }

    /**
     * 找出当前范围下的极大值与极小值点，根据极大值和极小值位置来切换范围    //jk20220711tdrRange
     */
    private void findExtremePointRange() {
        //判断极值位置
        int maxRange = 0;   //jk20220711tdrRange
        int minRange = 0;
        int j = pulseRemovePoint[rangeState]+3;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];

        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        while ( (j >= pulseRemovePoint[rangeState]+3) && (j < dataMax - removeTdrSim[rangeState]) ) {
            if ( (waveArray[j] > waveArray[j - 1]) && (waveArray[j] >= waveArray[j + 1]) ) {
                if (waveArray[j - 1] >= waveArray[j - 2]) {
                    if (waveArray[j - 2] > waveArray[j - 3]) {
                        maxData[maxNum] = waveArray[j];
                        maxDataPos[maxNum] = j;
//                        Log.e("SIM筛选2", " /极大值大小 = " + maxData[maxNum] + " /极大值位置 = " + maxDataPos[maxNum]);
                        maxNum++;
                    }
                }
            }
            j++;
        }
        if (maxNum == 0) {
            Log.e("tdr", "发射没有极大值");
        }else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }

        }

        //找所有极大值点   //jk20220711tdrRange
        for (int p = 0; p < maxNum; p++) {
            if ((maxDataPos[p] >= pulseRemoveTdrWave[rangeState])&&(Math.abs(maxData[p] - medianValue) >= 40)) {    //GT20220822 旧30 改为40
                maxRange = 1;
                break;
            }
        }

        int i1 = pulseRemovePoint[rangeState]+5 ;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos=minDataPos1[0];
        int min = waveArray[0];

        while ( (i1 >= pulseRemovePoint[rangeState]+5 ) && (i1 < dataMax - removeTdrSim[rangeState]) ) {   //jk20200714
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
                if (minData1[k1] <= min) {
                    min = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }
        }
        //查找第二大极值点
        int secondMax = 128;
        int secondmaxPos = maxDataPos[0];
        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
        } else {
            for (int k = 0; k < maxNum; k++) {
                if ((maxData[k] < max) && (maxData[k] >= secondMax)&&maxDataPos[k] >= maxPos) {
                    secondMax = maxData[k];
                    secondmaxPos = maxDataPos[k];
                }
            }
        }

        int secondMin = 128;
        int secondminPos = minDataPos1[1];
        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if ((minData1[k1] > min) && (minData1[k1] <= secondMin)&&minDataPos1[k1] >= minPos) {
                    secondMin = minData1[k1];
                    secondminPos = minDataPos1[k1];
                }
            }
        }
        //jk20220711tdrRange
        for (int r = 0; r < minNum1; r++) {
            if ((minDataPos1[r] >= pulseRemoveTdrWave[rangeState])&&(Math.abs(minData1[r] - medianValue) >= 40)) {  //GT20220822 旧30 改为40
                minRange = 1;
                break;
            }
        }
        if( minPos < pulseRemoveTdrWave[rangeState] &&  maxPos < pulseRemoveTdrWave[rangeState] && maxPos > minPos ){
            rangeAdjust = 1;
        } else {
            if ((minRange == 0) && (maxRange == 0)) {
                rangeAdjust = 0;
            }
        }
    }

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
//        balanceIsReady = false;   //平衡调节去掉（T-A310/2000）   //GC20231027
        rangeIsReady = false;
    }

    /**
     * 寻找发射脉冲的极大、极小值，用作判断平衡切换
     */
    int b_pos = 0;
    int b1_pos = 0;
    int b2_pos = 0;

    private void findStartExtremePoint() {
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
        int min = waveArray[0];
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
                if (minData1[k1] <= min) {
                    min = minData1[k1];
                    minPos1 = minDataPos1[k1];
                }
            }
        }
        b = Math.abs(128 - min);
        b2_pos = minPos1;
        if (a < b && min <= 126) {       //jk20200714
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

        for (int i = 0; i <= j; i++) {
            if (waveArray[i] < medianValue) {
                temp1 = temp1 + (medianValue - waveArray[i]);
            } else {
                temp2 = temp2 + (waveArray[i] - medianValue);
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
     * 低压脉冲方式增益自动判断
     */
    private void gainJudgmentTdr() {
        int i;
        int max = 0;
        int sub;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            sub = waveArray[i] - medianValue;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 55) {//if (max <= 45) {
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
     * 调节至合适增益
     */
    private void gainJudgmentTdr1() {
        int i;
        int max = 0;
        int sub;

        //计算波形有效数据的极值
        for (i = 0; i < dataMax - removeTdrSim[rangeState]; i++) {
            sub = waveArray[i] - medianValue;
            if (Math.abs(sub) > max) {
                max = Math.abs(sub);
            }
        }
        if (max <= 55) {    //jk20220711tdrRange    //GT20220822    增益参数修改 旧35 改为55
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
     * 寻找极值点，判断向上向下  //jk20220711tdr
     */
    private void findExtremePoint() {
        //判断极值位置
        int a;
        int b;
        int j = pulseRemovePoint[rangeState] ;
        int maxNum = 0;
        int[] maxData = new int[65560];
        int[] maxDataPos = new int[65560];
        int max = maxData[0];
        int maxPos = maxDataPos[0];
        //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
        while ((j >= pulseRemovePoint[rangeState] ) && (j < dataMax - removeTdrSim[rangeState])) {
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
            //tvInformation.setText(getResources().getString(R.string.testAgain));
        } else {
            for (int k = 0; k < maxNum; k++) {
                if (maxData[k] >= max) {
                    max = maxData[k];
                    maxPos = maxDataPos[k];
                }
            }
        }
        a = Math.abs(max - medianValue);

        int i1 = pulseRemovePoint[rangeState] + 2;
        int minNum1 = 0;
        int[] minData1 = new int[65560];
        int[] minDataPos1 = new int[65560];
        int minPos = minDataPos1[0];
        int min = waveArray[0];
        int secondJudgeMin; //jk20220711tdr
        int secondJudgeMax;

        while ((i1 >= pulseRemovePoint[rangeState] + 2) && (i1 < dataMax - removeTdrSim[rangeState])) {   //jk20200714
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
                if (minData1[k1] <= min) {
                    min = minData1[k1];
                    minPos = minDataPos1[k1];
                }
            }
        }

        b = Math.abs(medianValue - min);

        //jk20220711tdr
        int secondMax = 128;
        int secondMaxPos = maxDataPos[0];
        if (maxNum == 0) {
            Log.e("tdr", "曲线拟合没有极大值");
            secondMaxPos = 0;
        } else {
            for (int k = 0; k < maxNum; k++) {
                if ((maxData[k] < max) && (maxData[k] >= secondMax) && maxDataPos[k] >= maxPos ) {
                    secondMax = maxData[k];
                    secondMaxPos = maxDataPos[k];
                }
            }
        }

        //jk20220711tdr
        if( secondMaxPos == 0){
            secondJudgeMax = 0;
        }else{
            secondJudgeMax = Math.abs(secondMax - medianValue);
        }

        //jk20220711tdr
        int secondMin = 128;
        int secondMinPos = minDataPos1[0];
        if (minNum1 > 0) {
            for (int k1 = 0; k1 < minNum1; k1++) {
                if ((minData1[k1] > min) && (minData1[k1] <= secondMin)&&minDataPos1[k1] >= minPos) {
                    secondMin = minData1[k1];
                    secondMinPos = minDataPos1[k1];
                }
            }
        }else{
            secondMinPos = 0;
        }

        if( secondMinPos == 0){//jk20220711tdr
            secondJudgeMin = 0;
        }else{
            secondJudgeMin = Math.abs(secondMin - medianValue);
        }

        Log.e("a", " /波形 " + a);
        Log.e("b", " /波形 " + b);
        Log.e("min", " /zhi " + min);
        Log.e("max", " /zhi " + max);
        Log.e("minPos", " /zhi " + minPos);
        Log.e("maxPos", " /zhi " + maxPos);
        Log.e("secondMin", " /zhi " + secondMin);
        Log.e("secondMinPos", " /zhi " + secondMinPos);
        Log.e("secondMaxPos", " /zhi " + secondMaxPos);
        Log.e("secondJudgeMin", " /zhi " + secondJudgeMin);
        Log.e("secondJudgeMax", " /zhi " + secondJudgeMax);
        //jk20220711tdr
        if(a > b){
            if (minPos < maxPos && secondMinPos > maxPos && secondMinPos < pulseRemoveTdrWave[rangeState] &&secondMaxPos< pulseRemoveTdrWave[rangeState] && min < medianValue -15&& secondMin < medianValue -10) {
                tdrExtreme = minPos;
                point_x() ;
                Log.e("tdr", "tdrr1");
            }else if (maxPos < pulseRemoveTdrWave[rangeState] && minPos <= pulseRemoveTdrWave[rangeState] * 2 && secondMinPos > pulseRemoveTdrWave[rangeState]+ 10 && secondJudgeMin > secondJudgeMax  && secondMin< medianValue -15){
                tdrExtreme = secondMinPos ;
                point_x() ;
                Log.e("tdr", "tdrr2");
//            }else if ((maxPos < pulseRemoveTdrWave[rangeState] && minPos > pulseRemoveTdrWave[rangeState] * 2 && min < medianValue -15 && min- medianValue > secondJudgeMax)||(maxPos > pulseRemoveTdrWave[rangeState] &&  secondJudgeMin > secondJudgeMax && maxPos > minPos &&secondMinPos>maxPos&&secondMaxPos>secondMinPos&&min < medianValue -15)) {
            //极值点条件判断修改  //GT20220822
            }else if ((maxPos < pulseRemoveTdrWave[rangeState] && minPos > pulseRemoveTdrWave[rangeState] * 2 && min < medianValue -15 && Math.abs(min- medianValue) > secondJudgeMax)){//jk20220922
                tdrExtreme = minPos;
                point_x();
                Log.e("tdr", "tdrr3");
            }else if ((minPos <= pulseRemoveTdrWave[rangeState] * 2 && maxPos < pulseRemoveTdrWave[rangeState] && secondMaxPos > pulseRemoveTdrWave[rangeState] * 2 && secondMax > medianValue +15 && secondJudgeMin < secondJudgeMax && secondMaxPos - maxPos > pulseRemoveTdrWave[rangeState] ) ||  (maxPos < pulseRemoveTdrWave[rangeState] &&minPos > pulseRemoveTdrWave[rangeState]*2 &&  secondJudgeMax > min - medianValue && secondMaxPos - maxPos > pulseRemoveTdrWave[rangeState])) {
                tdrExtreme = secondMaxPos;
                point_s();
                Log.e("tdr", "tdrr4");
            } else {
                tdrExtreme = maxPos;
                point_s();
                Log.e("tdr", "tdrr5");
            }
        } else {
            if (minPos <= pulseRemoveTdrWave[rangeState] * 2 && maxPos > pulseRemoveTdrWave[rangeState] * 2 && max > medianValue + 15 && max - medianValue > secondJudgeMin && maxPos > 3 * minPos) {
                tdrExtreme = maxPos;
                point_s();
            } else if (maxPos < pulseRemoveTdrWave[rangeState] && minPos <= pulseRemoveTdrWave[rangeState] *2 && secondMaxPos > pulseRemoveTdrWave[rangeState] * 2 && secondMax > medianValue + 15 && secondJudgeMin < secondJudgeMax){
                tdrExtreme = secondMaxPos;
                point_s();
            }else if ((maxPos < pulseRemoveTdrWave[rangeState] && minPos <= pulseRemoveTdrWave[rangeState] * 2 && secondMinPos > pulseRemoveTdrWave[rangeState] * 2 && secondMin < medianValue -15 &&  secondJudgeMin > secondJudgeMax)||  (minPos <= pulseRemoveTdrWave[rangeState]*2 && secondMinPos > pulseRemoveTdrWave[rangeState]  &&secondJudgeMin > max - medianValue && secondMin < medianValue - 15)) {
                tdrExtreme = secondMinPos;
                point_x();
            } else {
                tdrExtreme = minPos;
                point_x();
            }
        }
    }

    /**
     * 低压脉冲波形向上 //jk20220711tdr
     */
    int pointflag = 0;
    private void point_s() {
        int t1;
        pointflag = 1;
        t1 = tdrExtreme;
        if(tdrExtreme > 5){
            while (t1 > 5) {
                if (waveArray[t1] >= waveArray[t1 - 1]) {
                    if ((waveArray[t1] == waveArray[t1-5])|| tdrFilter[t1] <= 1.2 || waveArray[t1] <= medianValue) {
                        break;
                    } else {
                        t1 = t1 - 1;
                    }
                } else {
                    break;
                }
            }
            tdrTurning = t1;
        }else{
            tdrTurning = 0;
        }
        Log.e("3", " /起始点 = " + tdrTurning);
    }

    /**
     * 低压脉冲波形向下  //jk20220711tdr
     */
    private void point_x() {
        int t2;
        t2 = tdrExtreme;
        pointflag = 2;
        //脉冲起始点
        if(tdrExtreme > 5) {
            while (t2 > 5) {
                if (waveArray[t2] <= waveArray[t2 - 1]) {
                    if ((waveArray[t2] == waveArray[t2 - 5]) || waveArray[t2] >= medianValue) {
                        break;
                    } else {
                        t2 = t2 - 5;
                    }
                } else {
                    break;
                }
            }
            tdrTurning = t2;
        }else{
            tdrTurning = 0;
        }
//            Log.e("3", " /负脉冲起始点 = " + tdrTurning);
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
            return;   //算法恢复 //GT20231031   //改为未击穿显示全长，继续向下走    //GC20231010
        }
        //计算方向脉冲
        calculatePulse();       //不击穿判断内部有修改   //GC20231010
        //计算故障距离并在界面显示
        correlationSimple();
//        noBreakSimple();  //算法恢复  //GT20231031    //添加不击穿时的算法     //GC20231010
        //放电脉冲位置确定——确定实光标
        breakPointCalculate();  //不击穿判断内部有修改   //GC20231010
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
        /*//重新选择start和stop（添加脉冲电流测全长算法） //GC20231010
        int stop;
        if(breakDown){
            start = 140;    //GC20200110 提前一部分 start = 120
            stop = dataMax - removeIcmDecay[rangeState];    //dataMax - 50旧的不对修改
        }else{
            start = 0;//未击穿，在calculatePulse函数中
            stop = 204;
        }*/ //算法恢复 //GT20231031
        //测距起点使用一次导数的最小值，从触发后64点开始取，躲过电容放电脉冲
        for (i = start + 64; i < dataMax - 50; i++) {
//        for (i = start + 64; i <stop ; i++) {   //重新选择start和stop    //GC20231010  //算法恢复 //GT20231031
            if ((V[i] < min) && (V[i] < 0)) {
                min = V[i];
                breakPoint = i;
            }
        }
        breakdownPosition = breakPoint;

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
        breakdownPosition = 140;
        if (range >= 6) {//25M采样
            if (breakdownPosition > (50 / 4)) {//需要修改，32km和64km采样频率变了，需要调整参数
                w1 = breakdownPosition - (50 / 4);      //相关窗左侧
            } else {
                w1 = breakdownPosition;
            }
            w2 = breakdownPosition + (350 / 4);     //相关窗右侧
        } else {
            if (breakdownPosition > 50) {//需要修改，32km和64km采样频率变了，需要调整参数
                w1 = breakdownPosition - 50;      //相关窗左侧
            } else {
                w1 = breakdownPosition;
            }
            w2 = breakdownPosition + 350;     //相关窗右侧
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
        //可以没有，定光标位置即可出现距离
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
            if (breakdownPosition > (50 / 4)) {
                //相关窗左侧
                w1 = breakdownPosition - (50 / 4);
            } else {
                w1 = breakdownPosition;
            }
            //相关窗右侧
            w2 = breakdownPosition + (350 / 4);
        } else {
            if (breakdownPosition > 50) {
                //相关窗左侧
                w1 = breakdownPosition - 50;
            } else {
                w1 = breakdownPosition;
            }
            //相关窗右侧
            w2 = breakdownPosition + 350;
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
        //可以没有，定光标位置即可出现距离
        calculateDistanceAuto(maxIndex);
    }

    /**
     * 脉冲电流方式  计算全长 //GC20231010
     */
    private void noBreakSimple() {
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
            if (breakdownPosition > (60 / 4)) {
                //相关窗左侧
                w1 = breakdownPosition - (60 / 4);
            } else {
                w1 = breakdownPosition;
            }
            //相关窗右侧
            w2 = breakdownPosition + (60 / 4);
        } else {
            if (breakdownPosition > 60) {
                //相关窗左侧
                w1 = breakdownPosition - 60;
            } else {
                w1 = breakdownPosition;
            }
            //相关窗右侧
            w2 = breakdownPosition + 60;
        }

        //抽点
        for (i = 0; i < 510; i++) {
            s1Simple[i] = waveArrayFilter[j];
            s2Simple[i] = waveArrayFilter[j];
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
        //G?20231010    //算法与907L不同
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
            if(breakDown){
                for (j = 0; j < (w2 - w1); j++) {
                    p += S1[j] * S2[j] * -1.0;
                }
            } else{
                for (j = 0; j < (w2 - w1); j++) {
                    p += S1[j] * S2[j]; //未击穿找全长算法此处修改   //GC20231010
                }
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

        //GC20231010    //计算
        int index = breakdownPosition + maxIndex;
        int num;
        if (index - 50 > 0) {
            for (num = index; num > index - 50; num--) {
//                if(waveArrayFilter[num] <= waveArrayFilter[num - 1]) {  //参数修改
                if ( ((waveArrayFilter[num] - waveArrayFilter[num - 1]) < 0.2) & ((waveArrayFilter[num + 1] - waveArrayFilter[num ]) < 0.2) ){
                    break;
                }
            }
            if (num == index - 50) {
                faultResult = index - 15 - breakdownPosition;
            } else {
                faultResult = num - breakdownPosition;
            }
        }

        //可以没有，定光标位置即可出现距离
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
        breakdownPosition = maxIndex;
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
                breakdownPosition = maxDataPos[k];
                break;
            }
        }
        //实光标位置
        breakdownPosition = breakdownPosition + start + faultResult + 10;
        /*if (breakDown) {    //击穿时定光标 //GC20231010

        }
        else {    //不击穿时定光标 //GC20231010
            int i;
            if (breakdownPosition > 30) {   //找实光标
                for (i = breakdownPosition; i > breakdownPosition - 30; i--) {
                    if (waveArrayFilter[i] >= waveArrayFilter[i-1]) {
                        break;  //G?20231010
                    }
                }
                if (i == breakdownPosition - 30) {
                    breakdownPosition = breakdownPosition - 15;
                } else {
                    breakdownPosition = i;
                }
            }
        }*/ //算法恢复  //GT20231031
    }

    /**
     * 脉冲电流方式光标自动定位 //GC20190708
     */
    private void icmAutoCursor() {
        //GC20200106
        if (false_flag == 0) {  //jk20210420 容错添加，如果出错就不走自动定位
            zero = breakdownPosition;
            pointDistance = breakdownPosition + faultResult;
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
            //距离显示
            calculateDistance(Math.abs(pointDistance - zero));
        } else {
            Log.e(" ICM算法光标", "进入容错 ");
            false_flag = 0; //GC20231107
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
            //判断是是否画标记光标    //GC20200330    if (mode == SIM) {
            if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
            //判断是是否画标记光标    //GC20200330    if (mode == SIM) {
            if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
            //判断是是否画标记光标    //GC20200330    if (mode == SIM) {
            if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
            //画SIM标记光标    //GC20200330    if (mode == SIM) {
            if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
        if (density == densityMax) {
            //SIM方式零点不变     //GC20220811    (mode != SIM)   TDR也不重置零点
            if (mode == ICM) {
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
    int simExtreme;  //向下极小值
    int simTurning;  //向下转折处
    int simAutoLocation;    //SIM自动定位位置
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
            if (p > 0.213) {    //重合系数
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
            //筛选2.判断极值位置    //pulseRemoveSim加载数值变动     //jk20220711Sim
            int j = pulseRemoveSimWave[rangeState] + 3;
            int maxNum = 0;
            int[] maxData = new int[65560];
            int[] maxDataPos = new int[65560];
            //寻找全长脉冲的极大值（去除发射脉冲和末尾数据）
            while ((j >= pulseRemoveSimWave[rangeState] + 3) && (j < dataMax - removeTdrSim[rangeState])) {
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
                        int i1 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum1 = 0;
                        int[] minData1 = new int[65560];
                        int[] minDataPos1 = new int[65560];
                        while ((i1 >= pulseRemoveSimWave[rangeState] + 5) && (i1 < dataMax - removeTdrSim[rangeState])) {
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
                            int min = minData1[0];
                            for (int k1 = 0; k1 < minNum1; k1++) {
                                if (minData1[k1] <= min) {
                                    min = minData1[k1];
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
                        int i2 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum2 = 0;
                        int[] minData2 = new int[65560];
                        int[] minDataPos2 = new int[65560];
                        while ((i2 >= pulseRemoveSimWave[rangeState] + 5) && (i2 < dataMax - removeTdrSim[rangeState])) {
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
                        int i3 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum3 = 0;
                        int[] minData3 = new int[65560];
                        int[] minDataPos3 = new int[65560];
                        while ((i3 >= pulseRemoveSimWave[rangeState] + 5) && (i3 < dataMax - removeTdrSim[rangeState])) {
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
                        int i4 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum4 = 0;
                        int[] minData4 = new int[65560];
                        int[] minDataPos4 = new int[65560];
                        while ((i4 >= pulseRemoveSimWave[rangeState] + 5) && (i4 < dataMax - removeTdrSim[rangeState])) {
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
                        int i5 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum5 = 0;
                        int[] minData5 = new int[65560];
                        int[] minDataPos5 = new int[65560];
                        while ((i5 >= pulseRemoveSimWave[rangeState] + 5) && (i5 < dataMax - removeTdrSim[rangeState])) {
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
                        int i6 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum6 = 0;
                        int[] minData6 = new int[65560];
                        int[] minDataPos6 = new int[65560];
                        while ((i6 >= pulseRemoveSimWave[rangeState] + 5) && (i6 < dataMax - removeTdrSim[rangeState])) {
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
                        int i7 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum7 = 0;
                        int[] minData7 = new int[65560];
                        int[] minDataPos7 = new int[65560];
                        while ((i7 >= pulseRemoveSimWave[rangeState] + 5) && (i7 < dataMax - removeTdrSim[rangeState])) {
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
                        int i8 = pulseRemoveSimWave[rangeState] + 5;
                        int minNum8 = 0;
                        int[] minData8 = new int[65560];
                        int[] minDataPos8 = new int[65560];
                        while ( (i8 >= pulseRemoveSimWave[rangeState] + 5) && (i8 < dataMax - removeTdrSim[rangeState]) ) {
                            if((simArray8[i8] < simArray8[i8 - 1]) && (simArray8[i8] <= simArray8[i8 + 1])) {
                                if (simArray8[i8 - 1] <= simArray8[i8 - 2]) {
                                    if (simArray8[i8 - 2] <= simArray8[i8 - 3]) {
                                        if (simArray8[i8 - 3] <= simArray8[i8 - 4]) {
                                            if (simArray8[i8 - 4] < simArray8[i8 - 5]) {
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
                            for(int k8 = 0; k8 < minNum8; k8++) {
                                if(minData8[k8] <= min8) {
                                    min8 = minData8[k8];
                                    min8Pos = minDataPos8[k8];
                                }
                            }
                            if (min8Pos < maxPos + 60) {
                                selectSim8 = true;
                                Log.e("SIM筛选2", "8极小值符合要求  " + " /min8Pos = " + min8Pos + " /maxPos = " + maxPos);
                            }else {
                                Log.e("SIM筛选2", "8极小值不符合要求" + " /min8Pos = " + min8Pos + " /maxPos = " + maxPos);
                            }
                        }else {
                            Log.e("SIM筛选2", "8未找到极小值");
                        }
                    }
                }
            }
        }
        //筛选3.波形做相关，给出最终结果
        simRelevantJudgment();

        //jk20200804  光标自动定位
        pointDistance = simAutoLocation;
        zero = simOriginalZero;
        Log.e("SIMc2", "simTurning" + simTurning);
        Log.e("SIMc2", "simExtreme" + simExtreme);
        Log.e("SIMc2", "pointDistance" + simAutoLocation);
        if (range == RANGE_250) {
            zero = simOriginalZero * 2;
            pointDistance = simAutoLocation * 2;
        }
        if (zero >= (currentMoverPosition510 * dataLength / 510) && zero <= ((currentMoverPosition510 * dataLength / 510) + (510 * density))) {
            positionReal = (zero - (currentMoverPosition510 * dataLength / 510)) / density;
            mainWave.setScrubLineReal(positionReal);
        } else {
            mainWave.setScrubLineRealDisappear();
        }

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
            sub = waveArray[i] - medianValue;
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
                //判断增益过大
                gainState = 1;
                return;
            }
        }
    }

    /**
     * 相关计算
     */
    int n, n1, n2, n3, n4, n5, n6, n7, n8, p;
    int n1Temp, n2Temp, n3Temp, n4Temp, n5Temp, n6Temp, n7Temp, n8Temp; //jk20221019
    public void simRelevantJudgment() {
        simFilter();
        int selectWaveNum = 1;
        double r, r1;
        double rMax = 0.0;

        n = dataMax - removeTdrSim[rangeState];

        if (selectSim1) {//jk20220711Sim
            n1 = min1Pos;
            //负脉冲起始点
            while (n1 > 1) {
                if (simArray1[n1] <= simArray1[n1-1]) {
                    if (simArray1[n1] == simArray1[n1-5] || simArray1[n1] > medianValue) {
                        break ;
                    }else{
                        n1 = n1 -5;
                    }
                } else {
                    break;
                }
            }
            n1Temp = n1; //jk20221019
            while (n1 > 1){
                if(Math.abs(simArray1Filter[n1+1]-simArray0Filter[n1+1]) <= 1.5) {
                    n1 = n1 + 1;
                    if (n1 == dataLength) { //jk20221019
                        n1 = n1Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n1 > 1){
                if(Math.abs(simArray1Filter[n1-1]-simArray0Filter[n1-1]) >= 1.5) {
                    n1 = n1 - 1;
                } else {
                    break;
                }
            }
            //计算相关系数
            r = correlationCalculation(simArray0Filter, simArray1Filter, n1);
            r1 = correlationCalculation(simArray0Filter, simArray1Filter, n);
            Log.e("SIM筛选3", "1 相关系数 r1 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n1);
            //GC20200609
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 1;
                    Log.e("SIM筛选3", "最终选第1条");
                }
            }
        }

        if (selectSim2) {   //jk20220711Sim
            n2 = min2Pos;
            while (n2 > 1) {
                if (simArray2[n2] <= simArray2[n2-1]) {
                    if (simArray2[n2] == simArray2[n2-5] || simArray2[n2] > medianValue) {
                        break ;
                    }else{
                        n2 = n2 -5;
                    }
                } else {
                    break;
                }
            }
            n2Temp = n2; //jk20221019
            while (n2 > 1){
                if(Math.abs(simArray2Filter[n2+1]-simArray0Filter[n2+1]) <= 1.5) {
                    n2 = n2 + 1;
                    if (n2 == dataLength) { //jk20221019
                        n2 = n2Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n2 > 1){
                if(Math.abs(simArray2Filter[n2-1]-simArray0Filter[n2-1]) >= 1.5) {
                    n2 = n2 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray2Filter, n2);
            r1 = correlationCalculation(simArray0Filter, simArray2Filter, n);
            Log.e("SIM筛选3", "2 相关系数 r2 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n2);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 2;
                    Log.e("SIM筛选3", "最终选第2条");
                }
            }
        }

        if (selectSim3) {   //jk20220711Sim
            n3 = min3Pos;
            while (n3 > 1) {
                if (simArray3[n3] <= simArray3[n3-1]) {
                    if (simArray3[n3] == simArray3[n3-5]|| simArray3[n3] > medianValue) {
                        break ;
                    }else{
                        n3 = n3 -5;
                    }
                } else {
                    break;
                }
            }
            n3Temp = n3;
            while (n3 > 1){
                if(Math.abs(simArray3Filter[n3+1]-simArray0Filter[n3+1]) <= 1.5) {
                    n3 = n3 + 1;
                    if (n3 == dataLength) { //jk20221019
                        n3 = n3Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n3 > 1){
                if(Math.abs(simArray3Filter[n3-1]-simArray0Filter[n3-1]) >= 1.5) {
                    n3 = n3 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray3Filter, n3);
            r1 = correlationCalculation(simArray0Filter, simArray3Filter, n);
            Log.e("SIM筛选3", "3 相关系数r3 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n3);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 3;
                    Log.e("SIM筛选3", "最终选第3条");
                }
            }
        }

        if (selectSim4) {   //jk20220711Sim
            n4 = min4pos;
            while (n4 > 1) {
                if (simArray4[n4] <= simArray4[n4-1]) {
                    if (simArray4[n4] == simArray4[n4-5]|| simArray4[n4] > medianValue) {
                        break ;
                    }else{
                        n4 = n4 -5;
                    }
                } else {
                    break;
                }
            }
            n4Temp = n4;
            while (n4 > 1){
                if(Math.abs(simArray4Filter[n4+1]-simArray0Filter[n4+1]) <= 1.5) {
                    n4 = n4 + 1;
                    if (n4 == dataLength) { //jk20221019
                        n4 = n4Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n4 > 1){
                if(Math.abs(simArray4Filter[n4-1]-simArray0Filter[n4-1]) >= 1.5) {
                    n4 = n4 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray4Filter, n4);
            r1 = correlationCalculation(simArray0Filter, simArray4Filter, n);
            Log.e("SIM筛选3", "4 相关系数 r4 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n4);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 4;
                    Log.e("SIM筛选3", "最终选第4条");
                }
            }
        }

        if (selectSim5) {   //jk20220711Sim
            n5 = min5Pos;
            while (n5 > 1) {
                if (simArray5[n5] <= simArray5[n5-1]) {
                    if (simArray5[n5] == simArray5[n5-5] || simArray5[n5] > medianValue) {
                        break ;
                    }else{
                        n5 = n5 -5;
                    }
                } else {
                    break;
                }
            }
            n5Temp = n5;
            while (n5 > 1){
                if(Math.abs(simArray5Filter[n5+1]-simArray0Filter[n5+1]) <= 1.5) {
                    n5 = n5 + 1;
                    if (n5 == dataLength) { //jk20221019
                        n5 = n5Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n5 > 1){
                if(Math.abs(simArray5Filter[n5-1]-simArray0Filter[n5-1]) >= 1.5) {
                    n5 = n5 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray5Filter, n5);
            r1 = correlationCalculation(simArray0Filter, simArray5Filter, n);
            Log.e("SIM筛选3", "5 相关系数 r5 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n5);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 5;
                    Log.e("SIM筛选3", "最终选第5条");
                }
            }
        }

        if (selectSim6) {   //jk20220711Sim
            n6 = min6Pos;
            while (n6 > 1) {
                if (simArray6[n6] <= simArray6[n6-1]) {
                    if (simArray6[n6] == simArray6[n6-5]|| simArray6[n6] > medianValue) {
                        break ;
                    }else{
                        n6 = n6 -5;
                    }
                } else {
                    break;
                }
            }
            n6Temp = n6;
            while (n6 > 1){
                if(Math.abs(simArray6Filter[n6+1]-simArray0Filter[n6+1]) <= 1.5) {
                    n6 = n6 + 1;
                    if (n6 == dataLength) { //jk20221019
                        n6 = n6Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n6 > 1){
                if(Math.abs(simArray6Filter[n6-1]-simArray0Filter[n6-1]) >= 1.5) {
                    n6 = n6 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray6Filter, n6);
            r1 = correlationCalculation(simArray0Filter, simArray6Filter, n);
            Log.e("SIM筛选3", "6 相关系数 r6 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n6);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 6;
                    Log.e("SIM筛选3", "最终选第6条");
                }
            }
        }

        if (selectSim7) {   //jk20220711Sim
            n7 = min7Pos;
            while (n7 > 1) {
                if (simArray7[n7] <= simArray7[n7-1]) {
                    if (simArray7[n7] == simArray7[n7-5] || simArray7[n7] > medianValue) {
                        break ;
                    }else{
                        n7 = n7 -5;
                    }
                } else {
                    break;
                }
            }
            n7Temp = n7;
            while (n7 > 1){
                if(Math.abs(simArray7Filter[n7+1]-simArray0Filter[n7+1]) <= 1.5) {
                    n7 = n7 + 1;
                    if (n7 == dataLength) { //jk20221019
                        n7 = n7Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n7 > 1){
                if(Math.abs(simArray7Filter[n7-1]-simArray0Filter[n7-1]) >= 1.5) {
                    n7 = n7 - 1;
                } else {
                    break;
                }
            }
            r = correlationCalculation(simArray0Filter, simArray7Filter, n7);
            r1 = correlationCalculation(simArray0Filter, simArray7Filter, n);
            Log.e("SIM筛选3", "7 相关系数 r7 = " + r + " /整体相关系数 = " + r1  + " /负脉冲起始点" + n7);
            if ( (r - r1) > 0.1 ) {
                if (r > rMax) {
                    rMax = r;
                    selectWaveNum = 7;
                    Log.e("SIM筛选3", "最终选第7条");
                }
            }
        }

        if(selectSim8) {    //jk20220711Sim
            n8 = min8Pos;
            while (n8 > 1) {
                if (simArray8[n8] <= simArray8[n8-1]) {
                    if (simArray8[n8] == simArray8[n8-5]|| simArray8[n8] > medianValue) {
                        break ;
                    }else{
                        n8 = n8 -5;
                    }
                } else {
                    break;
                }
            }
            n8Temp = n8;
            while (n8 > 1){
                if(Math.abs(simArray8Filter[n8+1]-simArray0Filter[n8+1]) <= 1.5) {
                    n8 = n8 + 1;
                    if (n8 == dataLength) { //jk20221019
                        n8 = n8Temp;
                        break;
                    }
                } else {
                    break;
                }
            }
            while (n8 > 1){
                if(Math.abs(simArray8Filter[n8-1]-simArray0Filter[n8-1]) >= 1.5) {
                    n8 = n8 - 1;
                } else {
                    break;
                }
            }

            r = correlationCalculation(simArray0Filter, simArray8Filter, n8);
            r1 = correlationCalculation(simArray0Filter, simArray8Filter, n);
            Log.e("SIM筛选3", "8 相关系数 r8 = " + r + " /整体相关系数 = " + r1 + " /负脉冲起始点" + n8);
            if ( (r - r1) > 0.1 ) {
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
        switch(selectWaveNum) {
            case 1:
                simExtreme = min1Pos;
                simTurning = n1;
                simArray = simArray1;
                simArrayF = simArray1Filter;    //jk20220711Sim
                break;
            case 2:
                simExtreme = min2Pos;
                simTurning = n2;
                simArray = simArray2;
                simArrayF = simArray2Filter;    //jk20220711Sim
                break;
            case 3:
                simExtreme = min3Pos;
                simTurning = n3;
                simArray = simArray3;
                simArrayF = simArray3Filter;
                break;
            case 4:
                simExtreme = min4pos;
                simTurning = n4;
                simArray = simArray4;
                simArrayF = simArray4Filter;
                break;
            case 5:
                simExtreme = min5Pos;
                simTurning = n5;
                simArray = simArray5;
                simArrayF = simArray5Filter;
                break;
            case 6:
                simExtreme = min6Pos;
                simTurning = n6;
                simArray = simArray6;
                simArrayF = simArray6Filter;
                break;
            case 7:
                simExtreme = min7Pos;
                simTurning = n7;
                simArray = simArray7;
                simArrayF = simArray7Filter;
                break;
            case 8:
                simExtreme = min8Pos;
                simTurning = n8;
                simArray = simArray8;
                simArrayF = simArray8Filter;
                break;
            default:
                break;
        }
        if(simTurning < 0){
            simTurning = 0;
        }
        Log.e("SIM", "simTurning"+ simTurning);
        Log.e("SIM", "simExtreme"+ simExtreme);
        p = simArray[simTurning] - medianValue;

        double[] X = new double[1000];
        double[] Y = new double[1000];
        double[] atemp = new double[8];
        double[] b = new double[4];
        double[][] a = new double[4][4];

        if (simExtreme - simTurning < 1000) { //jk20221020
            for (int h = simTurning; h < simExtreme; h++) {
                X[h - simTurning] = h - simTurning;
                Y[h - simTurning] = simArrayF[h];    //jk20220711Sim
            }

            for (int i = 0; i < simExtreme - simTurning; i++) {
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

            atemp[0] = simExtreme - simTurning;

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
            int autoLocation = 0;
            //求出曲线拟合后求解纵坐标值为0时横坐标的结果  一元三次方程求解  //jk20210527
//        autoLocation = equationSolving(b[3], b[2], b[1], b[0], -5, 5) + simTurning;
            autoLocation = solve3Polynomial(b[3], b[2], b[1], b[0]) + simTurning;  // jk20220711Sim
            if (autoLocation <= 0) {  //对于结果为0的补充
                autoLocation = simTurning;
                //针对开路测试情况，SIM虚光标定位到零点  //GC20220814
                if (simTurning == 0){
                    autoLocation = simOriginalZero;
                }
            }
            simAutoLocation = autoLocation;
        } else {
            simAutoLocation = simTurning;
        }

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

    /**
     * 盛金公式  //jk20220711tdr //jk20220711Sim
     */
    public int solve3Polynomial(double a, double b, double c, double d) {
        double x1 = 0.0, x2 = 0.0, x3 = 0.0;
        double A = 0.0, B = 0.0, C =0.0, DET = 0.0;
        double X1 = 0, X2 = 0, X3 = 0, XZ = 0;
        int XZ1;

        // 1. 计算重根判别式
        A = b*b - 3*a*c;
        if(Math.abs(A) < 1e-14) {
            A = 0.0;
        }
        B = b*c - 9*a*d;
        if(Math.abs(B) < 1e-14) {
            B = 0.0;
        }
        C = c*c - 3*b*d;
        if(Math.abs(C) < 1e-14) {
            C = 0.0;
        }

        // 2. 计算总判别式
        DET = B*B - 4*A*C;
        if(Math.abs(DET) < 1e-14) {
            DET = 0;
        }

        // 3. 条件一，计算根
        if((A == 0) && (B == 0)) {
            x1 = (-1*c)/b;
            x2 = x1;
            x3 = x1;
            //Log.i("roots", "条件一:" + x1 );
            X1 = Math.round(x1);
            X2 = Math.round(x2);
            X3 = Math.round(x3);
        }

        // 4. 条件二，计算根
        if(DET > 0) {
            double Y1 = A*b + 1.5*a*(-1*B + Math.sqrt(DET));
            double Y2 = A*b + 1.5*a*(-1*B - Math.sqrt(DET));
            //Log.i("SQSddd", "Y1: " + Y1 + " Y2: " + Y2);
            double y1 = getCubeRoot(Y1);
            double y2 = getCubeRoot(Y2);
            x1 = (-1.0*b-(y1+y2))/(3.0*a);				// 一个实根
            double vec1 = (-1*b + 0.5*(y1 + y2))/(3*a);
            double vec2 = 0.5*Math.sqrt(y1 - y2)/(3*a);
//    		x2 = Math.
            double x3_real = 0.0, x2_real = 0.0;		// 实部
            x2_real = (-b + getCubeRoot(Y1)) / (3 * a);
            x3_real = x2_real;
            double x2_virtual = 0.0, x3_virtual = 0.0;  // 虚部
            x2_virtual = ((Math.sqrt(3) / 2) * (y1 - y2 )) / (3 * a);
            x3_virtual = -x2_virtual;
            //Log.i("roots", "条件二:" + x1 );

            X1 = Math.round(x1);
            X2 = Math.round(x2_real);
            X3 = Math.round(x3_real);
        }

        // 5. 条件三，计算根
        if(DET == 0 && A != 0 && B != 0)
        {
            double K = (b*c - 9*a*d)/(b*b -3*a*c);
            K = Math.round(K);
            x1 = (-1.0*b)/a + K;
            x2 = -1*0.5*K;
            x3 = x2;
            //Log.i("roots", "条件三:" + x1 );
            X1 = Math.round(x1);
            X2 = Math.round(x2);
            X3 = Math.round(x3);
        }

        // 6.条件四，计算根
        if(DET < 0)
        {
            double sqA = Math.sqrt(A);
            double T = (A*b - 1.5*a*B)/(A*sqA);
            double theta = Math.acos(T);
            double csth = Math.cos(theta/3);
            double sn3th = Math.sqrt(3)*Math.sin(theta/3);
            x1 = (-1*b - 2*sqA*csth)/(3*a);
            x2 = (-1*b + sqA*(csth + sn3th))/(3*a);
            x3 = (-1*b + sqA*(csth - sn3th))/(3*a);
            //Log.i("SQSd", "条件四:" + x1 );
            X1 = Math.round(x1);
            X2 = Math.round(x2);
            X3 = Math.round(x3);
        }

        if (X1 >= X2) {
            XZ = X2;
            if (XZ > X3) {
                XZ = X3;
            } else {
                XZ = X2;
            }
        }
        else {
            XZ = X1;
            if (XZ > X3) {
                XZ = X3 ;
            } else {
                XZ = X1 ;
            }
        }

        if(XZ > pulseRemovePoint[rangeState] || XZ < (-1)* pulseRemovePoint[rangeState]){
            XZ = 0;
        }
        XZ1 = (int)XZ;
        Log.i("XZ1", "XZ1" + XZ1 );
        // 7. 返回计算结果
        return XZ1;
    }

    /**
     * 盛金公式  //jk20220711tdr //jk20220711Sim
     */
    public double getCubeRoot(double value) {
        if (value < 0) {
            return -Math.pow(-value, 1.0/3.0);
        } else if (value == 0) {
            return 0;
        } else {
            return Math.pow(value, 1.0/3.0);
        }

    }

    /**
     * 求解一元三次方程  //jk20210527
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
        simArray0Filter[0] = (double) (waveArray[0] - medianValue);    //jk20220711Sim  medianValue 替换133
        for (int i = 1; i < dataMax; i++) {
            simArray0Filter[i] = k * simArray0Filter[i - 1] + k * (double) (waveArray[i] - waveArray[i - 1]);
        }
        simArray1Filter[0] = (double) (simArray1[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray1Filter[i] = k * simArray1Filter[i - 1] + k * (double) (simArray1[i] - simArray1[i - 1]);
        }
        simArray2Filter[0] = (double) (simArray2[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray2Filter[i] = k * simArray2Filter[i - 1] + k * (double) (simArray2[i] - simArray2[i - 1]);
        }
        simArray3Filter[0] = (double) (simArray3[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray3Filter[i] = k * simArray3Filter[i - 1] + k * (double) (simArray3[i] - simArray3[i - 1]);
        }
        simArray4Filter[0] = (double) (simArray4[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray4Filter[i] = k * simArray4Filter[i - 1] + k * (double) (simArray4[i] - simArray4[i - 1]);
        }
        simArray5Filter[0] = (double) (simArray5[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray5Filter[i] = k * simArray5Filter[i - 1] + k * (double) (simArray5[i] - simArray5[i - 1]);
        }
        simArray6Filter[0] = (double) (simArray6[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray6Filter[i] = k * simArray6Filter[i - 1] + k * (double) (simArray6[i] - simArray6[i - 1]);
        }
        simArray7Filter[0] = (double) (simArray7[0] - medianValue);
        for (int i = 1; i < dataMax; i++) {
            simArray7Filter[i] = k * simArray7Filter[i - 1] + k * (double) (simArray7[i] - simArray7[i - 1]);
        }
        simArray8Filter[0] = (double) (simArray8[0] - medianValue);
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
        //打开数据库波形，清空自动测距参考信息    //GC20220808
        tvInformation.setText("");
        //不同方式下VIEW状态设置
        initMode();
        //显示数据库波形状态
        isDatabase = true;
        //设置数据库中的测试参数（网络正常时，打开记录应下发）
        setModeNoCmd(Constant.Para[0]);
        setRangeNoCmd(Constant.Para[1]);
        setGain(Constant.Para[2]);
        setVelocityNoCmd(Constant.Para[3]);
        Constant.ModeValue = Constant.Para[0];
        Constant.RangeValue = Constant.Para[1];
//        Constant.Gain = Constant.Para[2]; //setGain应该是已经传递给Constant.Gain了   //GC20231226
        Constant.SaveToDBGain = Constant.Para[2];   //数据库数据打开后再次保存时增益赋值  //GC20231226
        Constant.Velocity = Constant.Para[3];
        //实光标、虚光标、比例
        zero = Constant.PositionR;
        pointDistance = Constant.PositonV;
        positionVirtual = pointDistance / densityMax;
        positionReal = zero / densityMax;
        mainWave.setScrubLineVirtual(positionVirtual);
        mainWave.setScrubLineReal(positionReal);
        //数据库标记光标绘制位置刷新     //GC20220808
        if ((mode == TDR) || (mode == SIM)) {
            mainWave.setScrubLineSim(positionReal);
        } else {
            mainWave.setScrubLineSimDisappear();
        }
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
            //打开数据库波形，序号为0，控制波形上翻、下翻按钮状态     //GC20220820
            selectSim = 0;
            dataMax = READ_TDR_SIM[rangeState];
            //利用比较功能绘制SIM的第二条波形数据
            isCom = true;
        }
        //如果放大缩小过还原到最初状态    //GC20221025
        currentStart = 0;
    }

    /**
     * @param mode 需要发送的方式控制命令值 / 响应信息栏方式变化
     */
    private boolean isSetMode;
    public void setMode(int mode) {
        this.mode = mode;
        isSetMode = true;   //切换测试方式状态记录  //GC20220921
        switch (mode) {
            case TDR:
                initTDRView();
                initTDRFragment();      //GC20220819
                break;
            case ICM:
                initICMSURGEView();
                initICMSURGEFragment(); //GC20220819
                break;
            case ICM_DECAY:
                initICMDECAYView();
                initICMDECAYFragment(); //GC20221011
                break;
            case SIM:
                initSIMView();
                initSIMFragment();      //GC20220819
                break;
            case DECAY:
                initDecayView();
                initDecayFragment();    //GC20220819
                break;
            default:
                break;
        }
        //方式变化时，选取判断收取波形数据的点数
        selectWaveLength();
        initSparkView();    //切换方式擦波形画直线    //GC20220825
        //GC20190709
        switchDensity();
        initCursor();
        //发送指令
        command = COMMAND_MODE;
        dataTransfer = mode;
        startService();
    }

    public void setModeNoCmd(int mode) {
        this.mode = mode;
        switch (mode) {
            case TDR:
                initTDRView();
                initTDRFragment();  //GC20220821
                break;
            case ICM:
                initICMSURGEView();
                initICMSURGEFragment(); //GC20220821
                break;
            case ICM_DECAY:
                initICMDECAYView();
                initICMDECAYFragment(); //GC20221011
                break;
            case SIM:
                initSIMView();
                initSIMFragment();  //GC20220821
                break;
            case DECAY:
                initDecayView();
                initDecayFragment();    //GC20220821
                break;
            default:
                break;
        }
        //方式变化时，选取判断收取波形数据的点数
        selectWaveLength();
        //GC20190709
        switchDensity();
        //发送指令
        command = COMMAND_MODE;
        dataTransfer = mode;
        startService();
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
        llHorizontalView.setVisibility(View.VISIBLE);
        //设置滑动块的宽度
        setHorizontalMoveView();    //比例选择后，横向滑块改变//GC20220822
    }

    /**
     * 设置滑动块的宽度
     */
    private void setHorizontalMoveView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mvWave.getParentWidth() * 510 * density / dataLength, getResources().getDimensionPixelSize(R.dimen.dp_20));
        mvWave.setLayoutParams(layoutParams);
    }

    private void setHorizontalMoveViewOnlyHeight() {
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
        //光标距离  //GC20200330    if (mode == SIM) {
        if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标，方式、范围改变时初始化
            //GC20190712
            zero = simOriginalZero;
            if (range == RANGE_250) {
                zero = zero * 2;
            }
            zero2 = simStandardZero;
            if (range == RANGE_250) {
                zero2 = zero2 * 2;
            }
        } else {
            zero = 0;
        }
        pointDistance = 255 * densityMax;
        //计算并在界面显示距离
        calculateDistance(Math.abs(pointDistance - zero));
        //画布光标位置
        positionReal = zero / densityMax;
        positionVirtual = pointDistance / densityMax;
        if (positionReal >= 0) {
            mainWave.setScrubLineReal(positionReal);
        }
        mainWave.setScrubLineVirtual(positionVirtual);
        //GC20200330    if (mode == SIM) {
        if ((mode == TDR) || (mode == SIM)) {    //GC20220811    添加TDR方式下辅助光标
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
        this.range = range;
        switch (range) {
            case RANGE_250:
                range = 0x99;
                rangeState = 0;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m));
                } else if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                //切换范围时改变SIM的发射脉宽   //GC20200527
                if (mode == SIM) {
                    pulseWidthSim = 320;
                }
                gain = 13 + 3;  //初始增益调大    //GC20220823    旧13/41 新16/51
                //范围切换时按钮状态跟随变化 //GC20220709
                rangeFragment.btn250m.setEnabled(false);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m1);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_500:
                rangeState = 1;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 40;
                    etPulseWidth.setText(String.valueOf(40));
                }
                if (mode == SIM) {
                    pulseWidthSim = 320;
                }
                gain = 13 + 3;  //初始增益调大    //GC20220823
                //GC20220709
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(false);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m1);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_1_KM:
                rangeState = 2;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_1km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 80;
                    etPulseWidth.setText(String.valueOf(80));
                }
                if (mode == SIM) {
                    pulseWidthSim = 320;
                }
                gain = 13 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(false);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1km1);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_2_KM:
                rangeState = 3;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_2km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 160;
                    etPulseWidth.setText(String.valueOf(160));
                }
                if (mode == SIM) {
                    pulseWidthSim = 720;
                }
                gain = 10 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(false);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2km);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_4_KM:
                rangeState = 4;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_4km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 320;
                    etPulseWidth.setText(String.valueOf(320));
                }
                if (mode == SIM) {
                    pulseWidth = 2560;
                }
                gain = 10 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(false);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4km);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_8_KM:
                rangeState = 5;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_8km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 640;
                    etPulseWidth.setText(String.valueOf(640));
                }
                if (mode == SIM) {
                    pulseWidth = 3600;
                }
                gain = 10 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(false);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8km);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_16_KM:
                rangeState = 6;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_16km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 1280;
                    etPulseWidth.setText(String.valueOf(1280));
                }
                if (mode == SIM) {
                    pulseWidthSim = 7120;
                }
                gain = 9 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(false);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16km);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_32_KM:
                rangeState = 7;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_32km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 2560;
                    etPulseWidth.setText(String.valueOf(2560));
                }
                if (mode == SIM) {
                    pulseWidthSim = 10200;
                }
                gain = 9 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(false);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32km);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_64_KM:
                rangeState = 8;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_64km_to_yingli));
                }
                if (!hasSavedPulseWidth && mode == TDR) {
                    pulseWidth = 5120;
                    etPulseWidth.setText(String.valueOf(5120));
                }
                if (mode == SIM) {
                    pulseWidthSim = 10200;
                }
                gain = 9 + 3;
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(false);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64km);
                break;
            default:
                break;
        }
        //范围变化时，选取判断收取波形数据的点数
        selectWaveLength();
        initSparkView();    //切换范围画直线擦波形    //GC20220825
        //GC20190709
        switchDensity();
        initCursor();
        //增益转为百分比   //GC20200313
        int temp = s2b(gain);
        tvGainValue.setText(String.valueOf(temp));
        //发送指令
        command = COMMAND_RANGE;
        dataTransfer = range;
        startService();
    }

    public void setRangeNoCmd(int range) {
        this.range = range;
        switch (range) {
            case RANGE_250:
                range = 0x99;
                rangeState = 0;
                //GC20190709
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m));
                } else if (CurrentUnit == FT_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_250m_to_ft));
                }
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
                rangeFragment.btn250m.setEnabled(false);
                rangeFragment.btn500m.setEnabled(true);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m1);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_500:
                rangeState = 1;
                if (CurrentUnit == MI_UNIT) {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m));
                } else {
                    tvRangeValue.setText(getResources().getString(R.string.btn_500m_to_ft));
                }
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
                rangeFragment.btn250m.setEnabled(true);
                rangeFragment.btn500m.setEnabled(false);
                rangeFragment.btn1km.setEnabled(true);
                rangeFragment.btn2km.setEnabled(true);
                rangeFragment.btn4km.setEnabled(true);
                rangeFragment.btn8km.setEnabled(true);
                rangeFragment.btn16km.setEnabled(true);
                rangeFragment.btn32km.setEnabled(true);
                rangeFragment.btn64km.setEnabled(true);
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m1);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_1_KM:
                rangeState = 2;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1km1);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_2_KM:
                rangeState = 3;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2km);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_4_KM:
                rangeState = 4;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4km);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_8_KM:
                rangeState = 5;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8km);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_16_KM:
                rangeState = 6;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16km);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_32_KM:
                rangeState = 7;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32km);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64k);
                break;
            case RANGE_64_KM:
                rangeState = 8;
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
                rangeFragment.btn250m.setImageResource(R.drawable.bg_250m);
                rangeFragment.btn500m.setImageResource(R.drawable.bg_500m);
                rangeFragment.btn1km.setImageResource(R.drawable.bg_1k);
                rangeFragment.btn2km.setImageResource(R.drawable.bg_2k);
                rangeFragment.btn4km.setImageResource(R.drawable.bg_4k);
                rangeFragment.btn8km.setImageResource(R.drawable.bg_8k);
                rangeFragment.btn16km.setImageResource(R.drawable.bg_16k);
                rangeFragment.btn32km.setImageResource(R.drawable.bg_32k);
                rangeFragment.btn64km.setImageResource(R.drawable.bg_64km);
                break;
            default:
                break;
        }
        //方式变化时，选取判断收取波形数据的点数
        selectWaveLength();
        //GC20190709
        switchDensity();
        //发送指令
        command = COMMAND_RANGE;
        dataTransfer = range;
        startService();
    }

    /**
     * @param gain 需要发送的增益控制命令值 / 响应信息栏增益变化
     */
    public void setGain(int gain) {
        this.gain = gain;
        //增益转为百分比   //GC20200313
        int temp = s2b(gain);
        tvGainValue.setText(String.valueOf(temp));
        setGainState(); //GC20220824
        Constant.Gain = gain;
        command = COMMAND_GAIN;
        dataTransfer = gain;
        startService();
    }

    /**
     * 调节栏增益按钮状态设置   //GC20220824
     */
    public void setGainState() {
        //增益按钮状态变化（包含数据库打开）
        if (gain == 31) {
            adjustFragment.btnGainPlus.setEnabled(false);
            adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_1);
            adjustFragment.btnGainMinus.setEnabled(true);
            adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);
        } else if (gain == 0) {
            adjustFragment.btnGainPlus.setEnabled(true);
            adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);
            adjustFragment.btnGainMinus.setEnabled(false);
            adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_2);
        } else {
            adjustFragment.btnGainPlus.setEnabled(true);
            adjustFragment.btnGainPlus.setImageResource(R.drawable.bg_gain_plus_selector);
            adjustFragment.btnGainMinus.setEnabled(true);
            adjustFragment.btnGainMinus.setImageResource(R.drawable.bg_gain_min_selector);
        }
    }

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

    public int getGain() {
        return gain;
    }

    /**
     * @param balance 需要发送的平衡控制命令值 / 响应信息栏平衡变化
     */
    public void setBalance(int balance) {
        this.balance = balance;
        tvBalanceValue.setText(String.valueOf(balance));
        setBalanceState();  //GC20220824
        command = COMMAND_BALANCE;
        dataTransfer = balance;
        startService();
    }

    /**
     * 调节栏增益按钮状态设置   //GC20220824
     */
    public void setBalanceState() {
        //平衡按钮状态变化
        if (balance == 15) {
            adjustFragment.btnBalancePlus.setEnabled(false);
            adjustFragment.btnBalancePlus.setImageResource(R.drawable.bg_balance_jia);
            adjustFragment.btnBalanceMinus.setEnabled(true);
            adjustFragment.btnBalanceMinus.setImageResource(R.drawable.bg_balance_min_selector);
        } else if (balance == 0) {
            adjustFragment.btnBalancePlus.setEnabled(true);
            adjustFragment.btnBalancePlus.setImageResource(R.drawable.bg_balance_plus_selector);
            adjustFragment.btnBalanceMinus.setEnabled(false);
            adjustFragment.btnBalanceMinus.setImageResource(R.drawable.bg_balance_jian);
        } else {
            adjustFragment.btnBalancePlus.setEnabled(true);
            adjustFragment.btnBalancePlus.setImageResource(R.drawable.bg_balance_plus_selector);
            adjustFragment.btnBalanceMinus.setEnabled(true);
            adjustFragment.btnBalanceMinus.setImageResource(R.drawable.bg_balance_min_selector);
        }
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
        setDelayState();    //GC20220824
        command = COMMAND_DELAY;
        dataTransfer = delay / 5;
        startService();
    }

    /**
     * 调节栏增益按钮状态设置   //GC20220824
     */
    public void setDelayState() {
        //延时按钮状态变化
        if (delay == 0) {
            adjustFragment.btnDelayPlus.setEnabled(true);
            adjustFragment.btnDelayMinus.setEnabled(false);
        } else if (delay == 1250) {
            adjustFragment.btnDelayPlus.setEnabled(false);
            adjustFragment.btnDelayMinus.setEnabled(true);
        } else {
            adjustFragment.btnDelayPlus.setEnabled(true);
            adjustFragment.btnDelayMinus.setEnabled(true);
        }
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
        setVelocityState(); //GC20220824
        if (CurrentUnit == MI_UNIT) {
            tvVopValue.setText(String.valueOf(velocity));
        } else {
            tvVopValue.setText(UnitUtils.miToFt(velocity));
        }
        calculateDistance(Math.abs(pointDistance - zero));

    }

    /**
     * 调节栏增益按钮状态设置   //GC20220824
     */
    public void setVelocityState() {
        //延时按钮状态变化
        if(velocity == 90){
            adjustFragment.btnVelPlus.setEnabled(true);
            adjustFragment.btnVelMinus.setEnabled(false);
        } else if(velocity == 300){
            adjustFragment.btnVelPlus.setEnabled(false);
            adjustFragment.btnVelMinus.setEnabled(true);
        } else {
            adjustFragment.btnVelPlus.setEnabled(true);
            adjustFragment.btnVelMinus.setEnabled(true);
        }
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocityNoCmd(int velocity) {
        //20200523  波速度修改
        this.velocity = velocity;
        setVelocityState(); //GC20220824
        if (CurrentUnit == MI_UNIT) {
            tvVopValue.setText(String.valueOf(velocity));
        } else {
            tvVopValue.setText(UnitUtils.miToFt(velocity));
        }

    }

    @OnClick({
            R.id.tv_mode1, R.id.tv_range1, R.id.tv_adjust1, R.id.tv_operation1, R.id.tv_cursor_min, R.id.tv_cursor_plus, R.id.tv_zero, R.id.tv_help, R.id.iv_AUTO, R.id.tv_test,
            R.id.tv_lead_save, R.id.iv_lead_close, R.id.tv_records_save, R.id.tv_file_records, R.id.iv_records_close,
            R.id.tv_vop_j, R.id.tv_vop_d, R.id.tv_vop_g, R.id.iv_cal_close1,
            R.id.tv_pulse_width_save, R.id.iv_pulse_width_close
    })
    public void onClick(View view) {
        //未与硬件连接状态下可以响应的按钮  //GC20200630
        switch (view.getId()) {
            case R.id.tv_mode1:
                closeAllView();
                clickMode();
                break;
            case R.id.tv_range1:
                closeAllView();
                clickRange();
                break;
            case R.id.tv_adjust1:
                closeAllView();
                clickAdjust();
                break;
            case R.id.tv_operation1:
                closeAllView();
                clickOperation();
                break;
            case R.id.tv_cursor_min:
                closeAllView();
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
            case R.id.tv_zero:
                //零点切换  //GC20200612
                closeAllView();
                mainWave.setScrubLineReal(positionVirtual);
                positionReal = positionVirtual;
                //在原始数据中的位置
                zero = pointDistance;
                calculateDistance(0);
                break;
            case R.id.tv_help:
                closeAllView();
                showHelpModeDialog();
                break;
            case R.id.iv_AUTO:
                //按键进入高压操作界面    //GC20211202
                closeAllView();
                showAutoDialog();
                break;
            case R.id.tv_lead_save:
                llLead.setVisibility(View.GONE);
                setOperationRestore();    //GC20220810
                saveCableInit();
                break;
            case R.id.iv_lead_close:
                llLead.setVisibility(View.GONE);
                setOperationRestore();    //GC20220810
                break;
            case R.id.tv_records_save:
                llRecords.setVisibility(View.GONE);
                setOperationRestore();    //GC20220810
//                if (Constant.WaveData == null) {  //初始化不可保存   //GC20231208
                if (!Constant.allowSave) {
                    Toast.makeText(this, "无有效数据，无法保存！", Toast.LENGTH_SHORT).show();
                    return;
                }
                showSaveDialog();
                break;
            case R.id.tv_file_records:
                llRecords.setVisibility(View.GONE);
                setOperationRestore();    //GC20220810
                showRecordsDialog();
                break;
            case R.id.iv_records_close:
                llRecords.setVisibility(View.GONE);
                setOperationRestore();    //GC20220810
                break;
            case R.id.tv_vop_j:
                llCalAdjust.setVisibility(View.GONE);
                setAdjustRestore();    //GC20220810
                setVelocity(172);
                Toast.makeText(this, "交联聚乙烯电缆波速度范围170-174m/us，推荐使用172m/us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_vop_d:
                llCalAdjust.setVisibility(View.GONE);
                setAdjustRestore();    //GC20220810
                setVelocity(192);
                Toast.makeText(this, "聚乙烯电缆波速度范围190-194m/us，推荐使用192m/us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_vop_g:
                llCalAdjust.setVisibility(View.GONE);
                setAdjustRestore();    //GC20220810
                setVelocity(165);
                Toast.makeText(this, "聚氯乙烯电缆波速度范围160-168m/us，推荐使用165m/us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_cal_close1:
                llCalAdjust.setVisibility(View.GONE);
                setAdjustRestore();    //GC20220810
                break;
            case R.id.tv_test:  //离线可弹等待触发对话框    //GC20221115
                closeAllView();
                isReceiveData = true;
                clickTest();
                step = 8;
                count = 6;
                isLongClick = false;
                break;
            default:
                break;
        }
        //如果未连接不执行
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            allowSetOperation = true;    //未连接,操作可以点击 //GC20221019
            return;
        }
        //如果测试中不执行后续代码
        if (Constant.isTesting) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_pulse_width_save:
                savePulseWidth();
                break;
            case R.id.iv_pulse_width_close:
                llPulseWidth.setVisibility(View.GONE);
                break;
            default:
                break;
        }
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
                if (operationFragment == null) {
                    operationFragment = new OperationFragment();
                    transaction.add(R.id.content, operationFragment);
                } else {
                    transaction.show(operationFragment);
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
        if (operationFragment != null) {
            transaction.hide(operationFragment);
        }
    }

    /**
     * 方式按钮响应
     */
    private void clickMode() {
        setTabSelection(0);
        tvMode1.setEnabled(false);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvMode1.setImageResource(R.drawable.bg_mode_pressed1);
        tvRange1.setImageResource(R.drawable.bg_range_normal_111);
        tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
        tvWave1.setImageResource(R.drawable.bg_wave_normal);
    }

    /**
     * 范围按钮响应
     */
    private void clickRange() {
        setTabSelection(1);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(false);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(true);
        tvMode1.setImageResource(R.drawable.bg_mode_normal1);
        tvRange1.setImageResource(R.drawable.bg_range_pressed_1);
        tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
        tvWave1.setImageResource(R.drawable.bg_wave_normal);
    }

    /**
     * 调节按钮响应
     */
    private void clickAdjust() {
        setTabSelection(2);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(false);
        tvWave1.setEnabled(true);
        tvMode1.setImageResource(R.drawable.bg_mode_normal1);
        tvRange1.setImageResource(R.drawable.bg_range_normal_111);
        tvAdjust1.setImageResource(R.drawable.bg_adjust_pressed1);
        tvWave1.setImageResource(R.drawable.bg_wave_normal);
    }

    /**
     * 操作按钮响应
     */
    private void clickOperation() {
        setTabSelection(3);
        tvMode1.setEnabled(true);
        tvRange1.setEnabled(true);
        tvAdjust1.setEnabled(true);
        tvWave1.setEnabled(false);
        tvMode1.setImageResource(R.drawable.bg_mode_normal1);
        tvRange1.setImageResource(R.drawable.bg_range_normal_111);
        tvAdjust1.setImageResource(R.drawable.bg_adjust_normal_1);
        tvWave1.setImageResource(R.drawable.bg_wave_pressed1);
    }

    /**
     * 长按测试按键响应     //jk20200715
     */
    @OnLongClick({R.id.tv_test})
    public boolean onLongClick(View view) {
        isLongClick = true;
        isReceiveData = true;
        clickTest();
        return true;
    }

    /**
     * 模式界面帮助按钮   //GC20200327
     */
    private void showHelpModeDialog() {
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
        //画SIM波形，控制波形上翻、下翻按钮状态    //GC20220820
        setWavePreviousNext();
        //横向信息栏显示“波形序号：”
        tvWaveText.setVisibility(View.VISIBLE);
        this.selectSim = selectSim;
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

    public void showCalView() {
        llCalAdjust.setVisibility(View.VISIBLE);
        setAdjustFalse();    //GC20220810
    }

    public void showLeadView1() {
        llLead.setVisibility(View.VISIBLE);
        setOperationFalse();    //GC20220810
    }

    public void showFileView() {
        llRecords.setVisibility(View.VISIBLE);
        setOperationFalse();    //GC20220810
    }

    private void closeAllView() {
        llCalAdjust.setVisibility(View.GONE);
        llLead.setVisibility(View.GONE);
        llRecords.setVisibility(View.GONE);
        //波宽度已经去掉，不显示
        llPulseWidth.setVisibility(View.GONE);
        //弹窗取消后，按钮恢复有效   //GC20220810
        setAdjustRestore();
        setOperationRestore();
    }

    /**
     * 调节栏按钮状态恢复设置    //GC20220810
     */
    public void setAdjustRestore() {
        //三种方式下都有
        setGainState();     //增益
        setVelocityState(); //波速度
        adjustFragment.btnVelAdjust.setEnabled(true);   //常用波速度
        //以下根据方式选择
        if (mode == TDR) {
            setBalanceState();  //平衡
        } else {
            setDelayState();    //延时
        }
    }

    /**
     * 调节栏按钮点击无效设置    //GC20220810
     */
    public void setAdjustFalse() {
        //三种方式下都有
        adjustFragment.btnGainPlus.setEnabled(false);   //增益
        adjustFragment.btnGainMinus.setEnabled(false);
        adjustFragment.btnVelPlus.setEnabled(false);    //波速度
        adjustFragment.btnVelMinus.setEnabled(false);
        adjustFragment.btnVelAdjust.setEnabled(false);  //常用波速度
        //以下根据方式选择
        if (mode == TDR) {  //平衡
            adjustFragment.btnBalancePlus.setEnabled(false);
            adjustFragment.btnBalanceMinus.setEnabled(false);
        } else {            //延时
            adjustFragment.btnDelayPlus.setEnabled(false);
            adjustFragment.btnDelayMinus.setEnabled(false);
        }
    }
    
    /**
     * 操作栏按钮状态恢复设置   //GC20220810
     */
    private void setOperationRestore() {
        //三种方式下都有
        setZoomInOutRes();
        operationFragment.btnFile1.setEnabled(true);
        operationFragment.btnUP.setEnabled(true);
        //以下根据方式选择
        if (mode == ICM) {  //延长线
            operationFragment.btnLead1.setEnabled(true);
        }else {             //光标零点
            operationFragment.btnZero.setEnabled(true);
        }
        if (mode == SIM) {  //波形上翻、下翻
            setWavePreviousNext();
        } else {            //记忆、比较
            operationFragment.btnMemory.setEnabled(true);
            operationFragment.btnCompare.setEnabled(true);
        }
    }

    /**
     * 操作栏按钮点击无效设置   //GC20220810
     */
    public void setOperationFalse() {
        //三种方式下都有
        operationFragment.btnZoomIn.setEnabled(false);
        operationFragment.btnZoomOut.setEnabled(false);
        operationFragment.btnRes.setEnabled(false);
        operationFragment.btnFile1.setEnabled(false);
        operationFragment.btnUP.setEnabled(false);
        //以下根据方式选择
        if (mode == ICM) {  //延长线
            operationFragment.btnLead1.setEnabled(false);
        } else {            //光标零点
            operationFragment.btnZero.setEnabled(false);
        }
        if (mode == SIM) {  //波形上翻、下翻
            operationFragment.btnWavePrevious.setEnabled(false);
            operationFragment.btnWaveNext.setEnabled(false);
        } else {            //记忆、比较
            operationFragment.btnMemory.setEnabled(false);
            operationFragment.btnCompare.setEnabled(false);
        }
    }

    /**
     * 设置波宽度，存储本地保存
     */
    public void showPulseWidth() {
        llPulseWidth.setVisibility(View.VISIBLE);
        setOperationFalse();    //GC20220810
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
        llPulseWidth.setVisibility(View.GONE);
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
        SaveRecordsDialog saveRecordsDialog = new SaveRecordsDialog(this);
        Constant.ModeValue = mode;
        //TODO 20191226 存储zero和pointDistance
        saveRecordsDialog.setPositionReal(zero);
        saveRecordsDialog.setPositionVirtual(pointDistance);
        if (!saveRecordsDialog.isShowing()) {
            saveRecordsDialog.show();
        }
    }

    /**
     * 弹出波形记录对话框
     */
    public void showRecordsDialog() {
        ShowRecordsDialog showRecordsDialog = new ShowRecordsDialog(this);
        showRecordsDialog.setMode(mode);
        if (!showRecordsDialog.isShowing()) {
            showRecordsDialog.show();
        }
    }

    /**
     * 弹出高压操作界面     //GC20211202
     */
    public int currentGear = 2; //默认高电压档
    public int gearMemory = 2; //GC20220712
    public int currentSetVoltage = 0;
    public int currentSetTime = 5;
    public boolean sendWorkingMode;
    public boolean clickGear;
    public int workingModeData = 0x01;
    private AutoDialog autoDialog;
    /**
     * 闪电动画   //GC20220711
     */
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimatorHv;  //GC20221115
    private int[] lightning = {R.drawable.lightning, R.drawable.lightning_false};

    private void showAutoDialog() {
        autoDialog = new AutoDialog(this);
        if (!autoDialog.isShowing()) {
            autoDialog.show();
            Constant.isShowHV = true;   //高压设置对话框显示状态记录     //GC20211210
            //档位初始化
            currentGear = Constant.gear;
            switch (currentGear) {
                case 1:
                    autoDialog.rgGear.check(autoDialog.rbGear16.getId());
                    autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));    //档位选中效果修改  //GC20220913
                    if (Constant.WorkingMode != 0) {    //工作方式非“单次”时,另一个按钮无效    //GC20220929
                        autoDialog.rbGear32.setClickable(false);
                        autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                    }
                    break;
                case 2:
                    autoDialog.rgGear.check(autoDialog.rbGear32.getId());
                    autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                    if (Constant.WorkingMode != 0) {    //工作方式非“单次”时,另一个按钮无效    //GC20220929
                        autoDialog.rbGear16.setClickable(false);
                        autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                    }
                    break;
                default:
                    break;
            }
            //监听电压档位选项变化
            autoDialog.setRadioGroup(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (autoDialog.rbGear16.getId() == checkedId) {
                        autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));    //档位选中效果修改  //GC20220913
                        autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        currentGear = 1;
                        //改变档位设定电压初始化为0
                        Constant.setVoltage = 0;
                        //档位变化实时记录档位和电压数值 //GC20220414
                        Constant.gear = currentGear;
                        tvInfoSetVoltage.setText(0 + "");   //GC20220414
//                        autoDialog.hvValue.setText("设定电压：0kV"); //GC20220413
                        autoDialog.hvValue.setText(0 + "");  //GC20230912
                        autoDialog.seekBar32.setVisibility(View.GONE);
                        autoDialog.seekBar16.setVisibility(View.VISIBLE);
                        autoDialog.seekBar16.setProgress(0);
                    } else if (autoDialog.rbGear32.getId() == checkedId) {
                        autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));    //档位选中效果修改  //GC20220913
                        autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        currentGear = 2;
                        //改变档位设定电压初始化为0
                        Constant.setVoltage = 0;
                        //档位变化实时记录档位和电压数值 //GC20220414
                        Constant.gear = currentGear;
                        tvInfoSetVoltage.setText(0 + "");
//                        autoDialog.hvValue.setText("设定电压：0kV"); //GC20220413
                        autoDialog.hvValue.setText(0 + "");   //GC20230912
                        autoDialog.seekBar16.setVisibility(View.GONE);
                        autoDialog.seekBar32.setVisibility(View.VISIBLE);
                        autoDialog.seekBar32.setProgress(0);
                    }
                    judgeHV();  //档位变化时判断是否有动画 //GC20220917
                    //如果未连接不执行  //GC20220917
                    if (!ConnectService.isConnected) {
                        Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    clickGear = true;
                    showSwitchingDialog();  //开始倒计时弹出电压档位转换对话框   //GC20220917
                    timerGear.start();
                    //发送高压设定档位指令    //GC20220917    档位切换实时响应
                    ConnectService.isHV = true;
                    command = COMMAND_VOLTAGE_SET;
                    //电压数值0
                    dataTransfer = (byte) (0);
                    dataTransfer2 = (byte) (0);
                    //档位
                    dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                    startService();
                }
            });
            //设定电压初始化
            currentSetVoltage = Constant.setVoltage;
            autoDialog.hvValue.setText(currentSetVoltage + "");  //seekBar32/16设定电压显示 //GC20230912
            if (currentGear == 2) { //32kV档位 / 8kV
//                autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV"); //seekBar32设定电压显示 //GC20220413
                autoDialog.seekBar16.setVisibility(View.GONE);
                autoDialog.seekBar32.setVisibility(View.VISIBLE);
                autoDialog.seekBar32.setProgress(currentSetVoltage);
            } else if (currentGear == 1) {//16kV档位 / 4kV
//                autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV"); //seekBar16设定电压显示 //GC20220413
                autoDialog.seekBar32.setVisibility(View.GONE);
                autoDialog.seekBar16.setVisibility(View.VISIBLE);
                autoDialog.seekBar16.setProgress(currentSetVoltage);
            }
            judgeHV();  //高压设置对话框弹出时判断是否有动画 //GC20220711
            //设定电压变化监听seekBar32    //GC20220413
            autoDialog.seekBar32.setOnProgressChangedListener(new KBubbleSeekBar32.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
//                    autoDialog.hvValue.setText("设定电压：" + progress + "kV");
                    autoDialog.hvValue.setText(progress + ""); //GC20230912
                    if (currentSetVoltage != progress) {
                        if ((Constant.WorkingMode != 2) && !needSet0) {    //直流时、直流切换至单次电压归0时不记录电压变化状态 //GC20221010
                            isSetVoltageChanged = true; //GC20220926 seekBar32设定电压变化时状态记录
                        }
                    }
                    currentSetVoltage = progress;
                    //切换到直流时操作     //GC20221010
                    if (Constant.WorkingMode == 2) {
                        //记录改变的设定电压
                        Constant.setVoltage = currentSetVoltage;
                        tvInfoSetVoltage.setText(currentSetVoltage + "");
                        //发送高压设定电压指令    //GC20211209
                        ConnectService.isHV = true;
                        command = COMMAND_VOLTAGE_SET;
                        //电压数值
                        int temp = currentSetVoltage * 3276 / 32;    //GC20211227
//                        int temp =  currentSetVoltage * 3276 / 8;
                        dataTransfer = (byte) (temp >> 8 & 0xff);
                        dataTransfer2 = (byte) (temp & 0xff);
                        //档位
                        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                        startService();
                    } else {
                        judgeHV();  //判断seekBar32改变是否有动画 //GC20220926
                    }
                }

                @Override
                public void getProgressOnActionUp(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }
            });
            //设定电压变化监听seekBar16    //GC20220413
            autoDialog.seekBar16.setOnProgressChangedListener(new KBubbleSeekBar16.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
//                    autoDialog.hvValue.setText("设定电压：" + progress + "kV");
                    autoDialog.hvValue.setText(progress + "");   //GC20230912
                    if (currentSetVoltage != progress) {
                        if ((Constant.WorkingMode != 2) && !needSet0) {    //直流时、直流切换至单次电压归0时不记录电压变化状态 //GC20221010
                            isSetVoltageChanged = true; //GC20220926 seekBar16设定电压变化时状态记录
                        }
                    }
                    currentSetVoltage = progress;
                    //切换到直流时操作     //GC20221010
                    if (Constant.WorkingMode == 2) {
                        //记录改变的设定电压
                        Constant.setVoltage = currentSetVoltage;
                        tvInfoSetVoltage.setText(currentSetVoltage + "");
                        //发送高压设定电压指令    //GC20211209
                        ConnectService.isHV = true;
                        command = COMMAND_VOLTAGE_SET;
                        //电压数值
                        int temp = currentSetVoltage * 3276 / 32;    //GC20211227
//                        int temp =  currentSetVoltage * 3276 / 8;
                        dataTransfer = (byte) (temp >> 8 & 0xff);
                        dataTransfer2 = (byte) (temp & 0xff);
                        //档位
                        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                        startService();
                    } else {
                        judgeHV();  //判断seekBar16改变时是否有动画 //GC20220926
                    }
                }

                @Override
                public void getProgressOnActionUp(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }
            });
            //设定电压点击“－”按钮事件 //GC20220927
            autoDialog.setIvMinus(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentSetVoltage > 0) {
                        currentSetVoltage--;
                        isSetVoltageChanged = true; //GC20220926 点击“－”设定电压变化时状态记录
                        autoDialog.hvValue.setText(currentSetVoltage + "");  //GC20230912
                        if (currentGear == 2) { //32kV档位 / 8kV
//                            autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV");
                            autoDialog.seekBar32.setProgress(currentSetVoltage);
                        } else if (currentGear == 1) {//16kV档位 / 4kV
//                            autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV");
                            autoDialog.seekBar16.setProgress(currentSetVoltage);
                        }
                    } else {
                        Toast.makeText(ModeActivity.this, R.string.hv_toast2, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //设定电压点击“+”按钮事件 //GC20220927
            autoDialog.setIvPlus(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentGear == 2) { //32kV档位 / 8kV
                        if (currentSetVoltage < 32) {    //GC20211227
                            currentSetVoltage++;
                            isSetVoltageChanged = true; //GC20220926 8kV点击“+”设定电压变化时状态记录
//                            autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV");
                            autoDialog.hvValue.setText(currentSetVoltage + "");  //GC20230912
                            autoDialog.seekBar32.setProgress(currentSetVoltage);
                        } else {
                            Toast.makeText(ModeActivity.this, R.string.hv_toast4, Toast.LENGTH_SHORT).show();
                        }
                    } else if (currentGear == 1) {//16kV档位 / 4kV
                        if (currentSetVoltage < 16) {    //GC20211227
                            currentSetVoltage++;
                            isSetVoltageChanged = true; //GC20220926 4kV点击“+”设定电压变化时状态记录
//                            autoDialog.hvValue.setText("设定电压：" + currentSetVoltage + "kV");
                            autoDialog.hvValue.setText(currentSetVoltage + "");  //GC20230912
                            autoDialog.seekBar16.setProgress(currentSetVoltage);
                        } else {
                            Toast.makeText(ModeActivity.this, R.string.hv_toast3, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            //点击“电压确认”按钮事件
            autoDialog.setLlConfirmButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setLlConfirmButton();   //原封放入该函数里方便调用  //GC20230227
                }
            });
            //点击“寻找击穿电压”按钮事件    //GC20231009
            autoDialog.setLlGetBreakdownVoltageButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBreakdownVoltage();
                }
            });
            //工作方式初始化   //GC20220913
            switch (Constant.WorkingMode) {
                case 0:
                    autoDialog.rgGearMode.check(autoDialog.rbGearPULSE.getId());
                    autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
//                    autoDialog.llConfirm.setVisibility(View.VISIBLE); //GC20221010
                    autoDialog.llConfirm.setVisibility(View.INVISIBLE); //初始化单次时“电压确认”按钮不显示    //GC20230227
                    autoDialog.llTIME.setVisibility(View.INVISIBLE);    //没有周期调整
                    break;
                case 1:
                    autoDialog.rgGearMode.check(autoDialog.rbGearCYCLIC.getId());
                    autoDialog.rbGearCYCLIC.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                    autoDialog.llConfirm.setVisibility(View.VISIBLE); //“电压确认”按钮周期时才显示  //GC20221010
                    autoDialog.llTIME.setVisibility(View.VISIBLE);    //有周期调整
                    break;
                case 2:
                    autoDialog.rgGearMode.check(autoDialog.rbGearDC.getId());
                    autoDialog.rbGearDC.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                    autoDialog.llConfirm.setVisibility(View.INVISIBLE); //直流档位没有电压确认按钮  //GC20221010
                    autoDialog.llTIME.setVisibility(View.INVISIBLE);    //没有周期调整
                    break;
                default:
                    break;
            }
            //监听工作方式选项变化    //GC20220913
            autoDialog.setRadioGroupMode(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (autoDialog.rbGearPULSE.getId() == checkedId) {
                        tvInfoWorkingMode.setText(R.string.PULSE);
//                        autoDialog.llConfirm.setVisibility(View.VISIBLE);   //GC20221010
                        autoDialog.llConfirm.setVisibility(View.INVISIBLE); //方式变化为单次时“电压确认”按钮不显示    //GC20230227
                        autoDialog.llTIME.setVisibility(View.INVISIBLE);    //没有周期调整
                        if (Constant.WorkingMode == 2) {    //由直流档切换到单次 //GC20221010
                            needSet0 = true;
                        }
                        Constant.WorkingMode = 0;
                        workingModeData = 0x01;
                        autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                        if (Constant.isClickLocate) {   //定点方式下“直流”无    //GC20220913
                            autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧INVISIBLE
                        } else {
                            autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧VISIBLE
                            autoDialog.rbGearDC.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        }
                        autoDialog.rbGearCYCLIC.setVisibility(View.VISIBLE);
                        autoDialog.rbGearCYCLIC.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        //工作方式“单次”，电压档位切换有效  //GC20220913
                        autoDialog.rbGear16.setClickable(true);
                        autoDialog.rbGear32.setClickable(true);
                        switch (currentGear) {
                            case 1:
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                                break;
                            case 2:
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                                break;
                            default:
                                break;
                        }
                        //工作方式“单次”，“放电”按钮可点击  //GC20211220
                        autoDialog.ivHVPULSE.setEnabled(true);
                        autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_selector);

                    } else if (autoDialog.rbGearCYCLIC.getId() == checkedId) {
                        tvInfoWorkingMode.setText(R.string.CYCLIC);
                        autoDialog.llConfirm.setVisibility(View.VISIBLE); //GC20221010
                        autoDialog.llTIME.setVisibility(View.VISIBLE);    //有周期调整
                        Constant.WorkingMode = 1;
                        workingModeData = 0x02;
                        autoDialog.rbGearCYCLIC.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                        autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧INVISIBLE
                        //工作方式非“单次”，电压档位切换无效   //GC20220913
                        autoDialog.rbGear16.setClickable(false);
                        autoDialog.rbGear32.setClickable(false);
                        switch (currentGear) {
                            case 1:
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            case 2:
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            default:
                                break;
                        }
                        //工作方式非“单次”，“放电”按钮无法点击  //GC20211220
                        autoDialog.ivHVPULSE.setEnabled(false);
                        autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);

                    } else if (autoDialog.rbGearDC.getId() == checkedId) {
                        tvInfoWorkingMode.setText(R.string.DC);
                        autoDialog.llConfirm.setVisibility(View.INVISIBLE); //直流档位没有电压确认按钮  //GC20221010
                        autoDialog.llTIME.setVisibility(View.INVISIBLE);    //没有周期调整
                        needSet0 = true;    //切换至直流档时   //GC20221010
                        Constant.WorkingMode = 2;
                        workingModeData = 0x00;
                        autoDialog.rbGearDC.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                        autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                        autoDialog.rbGearCYCLIC.setVisibility(View.INVISIBLE);
                        //工作方式非“单次”，电压档位切换无效   //GC20220913
                        autoDialog.rbGear16.setClickable(false);
                        autoDialog.rbGear32.setClickable(false);
                        switch (currentGear) {
                            case 1:
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            case 2:
                                autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                                autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                                break;
                            default:
                                break;
                        }
                        //工作方式非“单次”，“放电”按钮无法点击  //GC20211220
                        autoDialog.ivHVPULSE.setEnabled(false);
                        autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
                    }
                    //发送工作方式指令  //GC20211209
                    command = COMMAND_WORKING_MODE;
                    //数据
                    if (workingModeData == 0x02) {
                        //周期时间指令下发  //GC20211223
                        dataTransfer = (currentSetTime << 4) + 2;
                    } else {
                        dataTransfer = workingModeData;
                    }
                    startService();
                    //切换和切出直流档位时设定电压要清零     //GC20221010
                    if (needSet0) {
                        if (currentSetVoltage != 0) {
                            handler.postDelayed(ModeActivity.this::setHv0, 20);
                            if (currentGear == 2) { //32kV档位 / 8kV
                                autoDialog.seekBar32.setProgress(0);
                            } else if (currentGear == 1) {//16kV档位 / 4kV
                                autoDialog.seekBar16.setProgress(0);
                            }
                            autoDialog.ivLightning.setImageResource(R.drawable.lightning_false);
                            autoDialog.ivLightning2.setImageResource(R.drawable.lightning_false);    //GC20230228
                        }
                        needSet0 = false;
                    }
                }
            });
            //工作方式初始化旧UI
            autoDialog.spWorkingMode.setSelection(Constant.WorkingMode);
            //监听工作方式变化旧UI
            autoDialog.spWorkingMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (Constant.WorkingMode != position) {
                        sendWorkingMode = true;     //如果和记录的位置不一样，发送工作方式指令
                    }
                    //记录工作方式变化
                    Constant.WorkingMode = position;    //position=0,1,2
                    //mode界面信息栏工作方式同步
                    switch (position) {
                        case 0:
                            tvInfoWorkingMode.setText(R.string.PULSE);
                            workingModeData = 0x01;
                            //只有单次时电压档位切换有效   //GC20220617
                            autoDialog.rbGear16.setClickable(true);
                            autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                            autoDialog.rbGear32.setClickable(true);
                            autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                            //工作方式“单次”，“放电”按钮可点击  //GC20211220
                            autoDialog.ivHVPULSE.setEnabled(true);
                            autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_selector);
                            break;
                        case 1:
                            tvInfoWorkingMode.setText(R.string.CYCLIC);
                            workingModeData = 0x02;
                            autoDialog.rbGear16.setClickable(false);
                            autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                            autoDialog.rbGear32.setClickable(false);
                            autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                            //工作方式非“单次”，“放电”按钮无法点击  //GC20211220
                            autoDialog.ivHVPULSE.setEnabled(false);
                            autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
                            break;
                        case 2:
                            tvInfoWorkingMode.setText(R.string.DC);
                            workingModeData = 0x00;
                            autoDialog.rbGear16.setClickable(false);
                            autoDialog.rbGear16.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                            autoDialog.rbGear32.setClickable(false);
                            autoDialog.rbGear32.setTextColor(getBaseContext().getResources().getColor(R.color.T_red));
                            //工作方式非“单次”，“放电”按钮无法点击  //GC20211220
                            autoDialog.ivHVPULSE.setEnabled(false);
                            autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
                            break;
                        default:
                            break;
                    }
                    if (sendWorkingMode) {
                        sendWorkingMode = false;
                        //发送工作方式指令  //GC20211209
                        command = COMMAND_WORKING_MODE; //旧UI
                        //数据
                        if (workingModeData == 0x02) {
                            //GC20211223
                            dataTransfer = (currentSetTime << 4) + 2;
                        } else {
                            dataTransfer = workingModeData;
                        }
                        startService();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            //根据方式调整界面右侧UI显示
            if (mode == SIM) {
                //工作方式旧UI
                /*autoDialog.tvWorkingMode.setVisibility(View.GONE);
                autoDialog.spWorkingMode.setVisibility(View.GONE);
                autoDialog.vWorkingMode.setVisibility(View.GONE);*/
                //SIM方式下工作方式不可选   //GC20220913
                autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧INVISIBLE
                autoDialog.rbGearPULSE.setVisibility(View.INVISIBLE);
                autoDialog.rbGearCYCLIC.setVisibility(View.INVISIBLE);
                //SIM方式下无放电周期  //GC20220617
                autoDialog.llTIME.setVisibility(View.INVISIBLE);
            } else {
                //工作方式旧UI
                /*autoDialog.tvWorkingMode.setVisibility(View.VISIBLE);
                autoDialog.spWorkingMode.setVisibility(View.VISIBLE);
                autoDialog.vWorkingMode.setVisibility(View.VISIBLE);*/
                //非SIM方式下工作方式可选     //GC20220913
                if (Constant.isClickLocate) {   //定点方式下“直流”无    //GC20220913
                    autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧INVISIBLE
                } else {
                    autoDialog.rbGearDC.setVisibility(View.GONE);  //GC20221111 旧VISIBLE
                }
                autoDialog.rbGearPULSE.setVisibility(View.VISIBLE);
                autoDialog.rbGearCYCLIC.setVisibility(View.VISIBLE);
                //非SIM方式下有放电周期  //GC20220617    //初始化添加工作方式限制  //GC20221010
                if (Constant.WorkingMode == 1) {
                    autoDialog.llTIME.setVisibility(View.VISIBLE);
                }
                //“放电周期”的时间初始化
                currentSetTime = Constant.time;
                autoDialog.seekBar12.setProgress(currentSetTime);
                //放电周期时间变化监听seekBar12    //GC20220619
                autoDialog.seekBar12.setOnProgressChangedListener(new KBubbleSeekBar12.OnProgressChangedListener() {
                    @Override
                    public void onProgressChanged(KBubbleSeekBar12 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                        if (progress < 4 ) {    //最小时间修改为4s //GC20221122
                            currentSetTime = 4;
                            autoDialog.seekBar12.setProgress(currentSetTime);
                            Toast.makeText(ModeActivity.this, R.string.time_toast1, Toast.LENGTH_SHORT).show();
                        } else {
                            currentSetTime = progress;
                            //改变放电周期
                            Constant.time = currentSetTime;
                            tvInfoTIME.setText(currentSetTime + "");
                            if (workingModeData == 0x02) {
                                //发送工作方式指令
                                command = COMMAND_WORKING_MODE; //周期
                                //数据
                                dataTransfer = (currentSetTime << 4) + 2;
                                startService();
                            }
                        }
                    }

                    @Override
                    public void getProgressOnActionUp(KBubbleSeekBar12 bubbleSeekBar, int progress, float progressFloat) {

                    }

                    @Override
                    public void getProgressOnFinally(KBubbleSeekBar12 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                    }
                });
            }
            //放电周期点击“－”按钮事件 //GC20220927
            autoDialog.setIvMinusTime(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentSetTime > 4) {   //最小时间修改为4s //GC20221122
                        currentSetTime--;
                        autoDialog.seekBar12.setProgress(currentSetTime);
                    } else {
                        Toast.makeText(ModeActivity.this, R.string.time_toast1, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //放电周期点击“+”按钮事件 //GC20220927
            autoDialog.setIvPlusTime(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentSetTime < 12) {
                        currentSetTime++;
                        autoDialog.seekBar12.setProgress(currentSetTime);
                    } else {
                        Toast.makeText(ModeActivity.this, R.string.time_toast2, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //“放电”按钮状态初始化——工作方式非“单次”时无法点击   //GC20211220
            if (Constant.WorkingMode != 0) {
                autoDialog.ivHVPULSE.setEnabled(false);
                autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
            }
            //点击“放电”按钮事件
            autoDialog.setIvHVPULSE(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (needEnsure) {
                        showEnsureHVDialog();   //高压设置界面弹故障电压确认对话框  //GC20230227
                    } else {
                        setHVPULSE();   //故障放电方法写入函数好调用 //GC20230227
                    }
                }
            });
            //点击进入测试界面（退出）按钮事件
            autoDialog.setQuit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //对话框退出状态记录     //GC20211210
                    Constant.isShowHV = false;
                    isSetVoltageChanged = false;    //GC20220926 点击进入测试界面设定电压变化状态重置
                    tvTest.setEnabled(true);   //GC20220729
                    allowSetMode = true;        //高压设置界面退出（测距）后方式可点击   //GC20221019
                    ivAUTO.setEnabled(true);
                    autoDialog.dismiss();
                    //定点模式进入测试界面（退出）时，切换至ICM界面  //GC20220809
                    if (Constant.isClickLocate) {
                        allowSetRange = true;   //定点模式进入ICM什么都不做
                        Constant.isClickLocate = false;
                        setMode(0x22);
                        //“定点模式”点击进入测试按钮时工作方式初始化为单次   //GC20220921
                        if (Constant.WorkingMode != 0) {
                            handler.postDelayed(ModeActivity.this::setWorkingModeSingle, 150);
                        }
                    } else {
                        //退出高压操作对话框时弹出等待触发对话框   //GC20211213
                        if (ConnectService.isConnected) {
                            //延时100毫秒发送测试命令，100毫秒是等待设备回复命令信息，如果不延时，有可能设备执行不完命令。
                            handler.postDelayed(ModeActivity.this::clickTest, 100);
                        }
                    }
                }
            });
            //点击叉号退出事件  //GC20220713
            autoDialog.setClose(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //对话框退出状态记录
                    Constant.isShowHV = false;
                    isSetVoltageChanged = false;    //GC20220926 点击叉号设定电压变化状态重置
                    allowSetRange = true;
                    tvTest.setEnabled(true);   //GC20220729
                    allowSetMode = true;        //高压设置界面叉号退出后方式可点击   //GC20221019
                    ivAUTO.setEnabled(true);
                    autoDialog.dismiss();
                    //定点模式叉号退出时，切换至ICM界面  //GC20220809
                    if (Constant.isClickLocate) {
                        Constant.isClickLocate = false;
                        setMode(0x22);
                        //“定点模式”点击叉号退出时工作方式初始化为单次   //GC20220921
                        if (Constant.WorkingMode != 0) {
                            handler.postDelayed(ModeActivity.this::setWorkingModeSingle, 150);
                        }
                    }
                }
            });
        }
    }

    /**
     * 击穿电压判断   //GC20231008
     */
    Boolean findFlag = false;
    Boolean isFinding = false;
    public int HVSetting;   //初始电压设置为3kV   //GC20231008
    public void getBreakdownVoltage() {
        setWorkingModeDC();
        HVSetting = 1;      //初始电压
        if (!isFinding) {
            isFinding = true;
            //切到直流档后等100ms再发送设定电压指令
            handler.postDelayed(ModeActivity.this::setHv, 100);
            //等待示数稳定后判断当前电压值是否与设定电压相同 //GC20231009
            Log.e(" BreakDownVoltage", "走到这1 倒计时开始");
            timerBreakDown.start();    //倒计时
        }
    }

    /**
     * 发送设定电压   //GC20231008
     */
    public void setHv() {
        //发送高压设定电压指令
        ConnectService.isHV = true;
        command = COMMAND_VOLTAGE_SET;
        //电压数值
        int temp = HVSetting * 3276 / 32;
        dataTransfer = (byte) (temp >> 8 & 0xff);
        dataTransfer2 = (byte) (temp & 0xff);
        //档位
        dataTransfer3 = 0x03;   //同 GEAR1 = 0x01 GEAR2 = 0x02
        startService();
    }

    /**
     * 倒计时Xs处理    //GC20231008
     */
    private CountDownTimer timerBreakDown = new CountDownTimer(6000, 100) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            Log.e(" BreakDownVoltage", "走到这2 倒计时结束");
            int tempHV = Math.round((int)Constant.currentVoltage);  //Math.floor() 方法 ：向下取整 Math.ceil() 方法 ：向上取整 Math.round() 方法 ：四舍五入
            if (tempHV == HVSetting) {
                Log.e(" BreakDownVoltage", " tempHV = " + tempHV);
                Log.e(" BreakDownVoltage", "走到这3 ==");
                findFlag = false;   //当前电压等于设定电压，继续寻找
                if (HVSetting < 8) {
                    HVSetting++;    //未找到击穿电压，继续增加设定值
                    Log.e(" BreakDownVoltage", " 增加HVSetting到 " + HVSetting);
                    isFinding = false;
                    setHv();
                    timerBreakDown.start();    //倒计时
                } else {
                    //结果2：显示找不到击穿电压   //GC20231009
                    autoDialog.hvValueBreakdownNote.setText(getResources().getString(R.string.no_Breakdown_voltage));
                    autoDialog.hvValueBreakdown.setVisibility(View.GONE);
                    autoDialog.hvValueBreakdownKV.setVisibility(View.GONE);
                    setWorkingModeSingle();
                    isFinding = false;
                }
            } else if (tempHV < HVSetting) {
                Log.e(" BreakDownVoltage", " tempHV = " + tempHV);
                Log.e(" BreakDownVoltage", "走到这5< ，击穿电压为" + HVSetting);
                findFlag = true;    //当前电压小于设定电压，找到击穿电压了
                //结果1：显示击穿电压的数值   //GC20231009
                autoDialog.hvValueBreakdownNote.setVisibility(View.VISIBLE);
                autoDialog.hvValueBreakdownNote.setText(getResources().getString(R.string.Breakdown_voltage));
                autoDialog.hvValueBreakdown.setVisibility(View.VISIBLE);
                autoDialog.hvValueBreakdown.setText(HVSetting + "");
                autoDialog.hvValueBreakdownKV.setVisibility(View.VISIBLE);
                setWorkingModeSingle();
                isFinding = false;
            } else {
                Log.e(" BreakDownVoltage", " tempHV = " + tempHV);
                Log.e(" BreakDownVoltage", "走到这6> ，异常");
                Log.e(" BreakDownVoltage", " HVSetting" + HVSetting);
                setWorkingModeSingle();
                isFinding = false;
            }
        }

    };

    /**
     * 故障放电执行的方法   //GC20230227
     */
    public void setHVPULSE() {
        //“放电”按钮先无法点击  //GC20231108
        if (isShowHV) {
            //在高压设置界面弹出
            autoDialog.ivHVPULSE.setEnabled(false);
            autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
        } else if (isShowHVWait) {
            //在测距界面弹出
            hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
            hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);
        }
        //原来直接发送，现在延时700ms    //GC20230225
        handler.postDelayed(() -> {
            //发送单次放电指令  //GC20211209
            command = COMMAND_SINGLE_PULSE; //故障放电
            //数据
            dataTransfer = 0x01;
            startService();
        }, 700);    //旧700  //GC20231013 改为400
//        handler.postDelayed(ModeActivity.this::setDischarge, 1400);   //GC20231013
        handler.postDelayed(() -> {
            //发送查询指令  //GC20211209
            command = COMMAND_VOLTAGE_QUERY;
            //数据
            dataTransfer = 0x00;
            startService();
            if (isShowEnsureHV) {    //放电电压确认对话框显示时才执行     //GC20230301
                ensureHVDialog.dismiss();
                Constant.isShowEnsureHV = false;
            }
        }, 720);    //旧720 //旧是20 //GC20230225    //GC20231013改为1420
        //延时1.5s恢复点击放电按钮  //GC20231108
        handler.postDelayed(() -> {
            if (isShowHV) {
                //在高压设置界面弹出
                autoDialog.ivHVPULSE.setEnabled(true);
                autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_selector);
            } else if (isShowHVWait) {
                //在测距界面弹出
                hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(true);
                hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_selector);
            }
        }, 1500);
    }

    /**
     * 电容放电（残压放电）   //GC20231013
     */
    public void setDischarge() {
        //发送电容放电指令
        command = 0xA0;
        //数据
        dataTransfer = 0x01;
        startService();
    }

    /**
     * 高压设置界面“电压确认”按钮事件的方法函数   //GC20230227
     */
    public void setLlConfirmButton() {
        //记录改变的电压档位
        Constant.gear = currentGear;
        /*gearMemory = currentGear;
        if (clickGear) {
            //开始倒计时弹出电压档位转换对话框   //GC20220712
            showSwitchingDialog();
            timerGear.start();
        }*/ //GC20220917
        //记录改变的设定电压
        Constant.setVoltage = currentSetVoltage;
        tvInfoSetVoltage.setText(currentSetVoltage + "");
        Toast.makeText(ModeActivity.this, R.string.hv_toast, Toast.LENGTH_SHORT).show();
        isSetVoltageChanged = false;    //GC20220926 点击“电压确认”设定电压变化状态记录
        judgeHV();  //点击“电压确认”判断是否有动画 //GC20220711
        //发送高压设定电压指令    //GC20211209
        ConnectService.isHV = true;
        command = COMMAND_VOLTAGE_SET;
        //电压数值
        int temp = currentSetVoltage * 3276 / 32;   //发送高压数值换算  //GC20211227
//        int temp =  currentSetVoltage * 3276 / 8;
        dataTransfer = (byte) (temp >> 8 & 0xff);
        dataTransfer2 = (byte) (temp & 0xff);
        //档位
        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
        startService();
        if (isShowEnsureHV) {
            setHVPULSE();   //放电电压确认对话框显示时才执行     //GC20230301
        }
    }

    /**
     *  当前电压大于2kV     //GC20220803
     */
    public void dangerousNote() {
        Toast.makeText(ModeActivity.this, "当前显示电压大于2kV，请先点击“电容放电”按钮放掉电压后再进行转换操作！", Toast.LENGTH_LONG).show();
    }

    /**
     * 判断电压数值 //GC20220711
     */
    private void judgeHV() {
        if (isSetVoltageChanged) {  //GC20220926
            //画闪电动画
            if (valueAnimator == null) {
                valueAnimator = ValueAnimator.ofInt(0, 2).setDuration(1000);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int i = (int) animation.getAnimatedValue();
                        autoDialog.ivLightning.setImageResource(lightning[i % lightning.length]);
                        autoDialog.ivLightning2.setImageResource(lightning[i % lightning.length]);  //GC20230228
                    }
                });
            }
            valueAnimator.start();
            needEnsure = true;  //judgeHV状态记录   //GC20230227
            //工作方式不可选择  //GC20220926
            /*autoDialog.rbGearDC.setClickable(false);
            autoDialog.rbGearPULSE.setClickable(false);
            autoDialog.rbGearCYCLIC.setClickable(false);*/  //改为工作方式可以选择    //GC20230227
            switch (Constant.WorkingMode) {
                case 0: //单次
                    /*autoDialog.rbGearDC.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                    autoDialog.rbGearCYCLIC.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));*/
                    break;
                case 1: //周期
                    /*autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));*/
                    //周期时“放电”按钮无法点击    //“放电”按钮控制逻辑修改 ，只在周期不可点击 //GC20230227  //GC20220926
                    autoDialog.ivHVPULSE.setEnabled(false);
                    autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_false);
                    break;
                case 2: //直流
                    autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.colorPrimaryDark));
                    break;
                default:
                    break;
            }
        } else {
            //闪烁动画关闭
            if (valueAnimator != null) {
                valueAnimator.end();
            }
            //根据设定电压判断闪电符号颜色
            if (currentSetVoltage != 0) {
                autoDialog.ivLightning.setImageResource(R.drawable.lightning);
                autoDialog.ivLightning2.setImageResource(R.drawable.lightning);  //GC20230228
            } else {
                autoDialog.ivLightning.setImageResource(R.drawable.lightning_false);
                autoDialog.ivLightning2.setImageResource(R.drawable.lightning_false);   //GC20230228
            }
            needEnsure = false; //judgeHV状态记录   //GC20230227
            //工作方式可恢复选择   //GC20220926
            /*autoDialog.rbGearDC.setClickable(true);
            autoDialog.rbGearPULSE.setClickable(true);
            autoDialog.rbGearCYCLIC.setClickable(true);*/   //改为工作方式可以选择    //GC20230227
            switch (Constant.WorkingMode) {
                case 0: //单次
                   /* autoDialog.rbGearDC.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                    autoDialog.rbGearCYCLIC.setTextColor(getBaseContext().getResources().getColor(R.color.white));*/
                    //只有单次时“放电”按钮可恢复点击  //GC20220926
                    autoDialog.ivHVPULSE.setEnabled(true);
                    autoDialog.ivHVPULSE.setImageResource(R.drawable.bg_pulse_selector);
                    break;
                case 1: //周期
                    /*autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.white));*/
                    break;
                case 2: //直流
                    autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.white));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 判断触发界面电压数值 //GC20221115
     */
    private void judgeHVWait() {
        if (isSetHVVoltageChanged) {  //增加判断条件  //GC20230224
            //画闪电动画
            if (valueAnimatorHv == null) {
                valueAnimatorHv = ValueAnimator.ofInt(0, 2).setDuration(1000);
                valueAnimatorHv.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimatorHv.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int i = (int) animation.getAnimatedValue();
                        hvWaitTriggerDialog.ivHvLightning.setImageResource(lightning[i % lightning.length]);
                        hvWaitTriggerDialog.ivHvLightning2.setImageResource(lightning[i % lightning.length]);   //GC20230228
                    }
                });
            }
            valueAnimatorHv.start();
            //“放电”按钮无法点击  //GC20220926
            /*hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
            hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);*/   //设定电压修改后“放电”按钮仍然可以使用   //GC20230227
            needEnsure = true;  //judgeHVWait状态记录   //GC20230227
        } else {
            //闪烁动画关闭
            if (valueAnimatorHv != null) {
                valueAnimatorHv.end();
            }
            //根据设定电压判断闪电符号颜色
            if (currentSetVoltage != 0) {
                hvWaitTriggerDialog.ivHvLightning.setImageResource(R.drawable.lightning);
                hvWaitTriggerDialog.ivHvLightning2.setImageResource(R.drawable.lightning);  //GC20230228
            } else {
                hvWaitTriggerDialog.ivHvLightning.setImageResource(R.drawable.lightning_false);
                hvWaitTriggerDialog.ivHvLightning2.setImageResource(R.drawable.lightning_false);    //GC20230228
            }
            needEnsure = false;  //judgeHVWait状态记录   //GC20230227
        }
    }

    /**
     * 点击“测试”按钮响应事件
     */
    private void clickTest() {
        //TODO 20200407 点击测试按钮的前提是与设备连接成功，否则吐司，禁止继续执行代码
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            if ((mode == ICM) || (mode == ICM_DECAY) || (mode == SIM) || (mode == DECAY)) {
                showHvWaitTriggerDialog();  //离线可弹等待触发对话框    //GC20221115
            }
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

        //测试时取消对话框   //GC20211215
        if (hvWaitTriggerDialog != null) {
            hvWaitTriggerDialog.dismiss();
        }
        //初始化距离
        if (mode == TDR) {
            if (!Constant.isClickSim) {  //不弹出正在接收数据对话框  //GC20221114
                tDialog = new TDialog.Builder(getSupportFragmentManager())
                        .setLayoutRes(R.layout.dialog_receiving_data)
                        .setScreenWidthAspect(this, 0.25f)
                        .setCancelableOutside(false)
                        .create()
                        .show();
                Log.e("DIA", " 正在接受数据显示" + " TDR");
            } else {
                Log.e("DIA", " SIM正在寻找合适范围");
            }
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
            //弹出自定义等待触发对话框  //GC20211215
            showHvWaitTriggerDialog();
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
     * 低压脉冲方式下调整增益发送测试命令    //GC20220622
     */
    public void gainTest() {
        if (mode == TDR) {
            handler.postDelayed(ModeActivity.this::clickTest, 100);
        }
    }

    /**
     * 自定义合闸提示对话框2 //GC20220726
     */
    public SwitchOnNoteDialog switchOnNoteDialog;
    public void showSwitchOnNoteDialog() {
        //如果未连接不执行（点击ICM、SIM、定点方式）  //GC20220825
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            allowSetMode = true;    //未连接,方式可以点击 //GC20221019
            Constant.isClickLocate = false;    //定点模式状态取消，定点按钮点击无效  //GC20220825
            switch (modeClick) {
                case 0x22:
                    setMode(0x22);
                    break;
                case 0x33:
                    setMode(0x33);
                    break;
                case 0x55:
                    setMode(0x55);  //GC20221026
                    break;
                default:
                    break;
            }
            return;
        }
        switchOnNoteDialog = new SwitchOnNoteDialog(this);
        if (!switchOnNoteDialog.isShowing()) {
            tvTest.setEnabled(false);   //GC20220729
            ivAUTO.setEnabled(false);
            switchOnNoteDialog.show();
            Constant.isShowSwitchOnNoteDialog = true;   //显示询问是否合闸对话框   //GC20231112
            //外部点击禁止
            switchOnNoteDialog.setCanceledOnTouchOutside(false);
            handler.postDelayed(() -> { //合闸查询命令发送延时添加，尝试解决直闪切到其它方式高压命令无效bug  //GC20221205
                //进入合闸提示对话框，开始发送合闸查询命令  //GC20220919    907主板调试需要屏蔽
                command = COMMAND_SWITCH_ON_QUERY;
                //数据
                dataTransfer = 0x01;
                startService();
                timerSwitch.start();
            }, 50);
            //点击“是，下一步”按钮事件 //最终是“下一步”
            switchOnNoteDialog.setTvYes(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerSwitch.cancel();   //GC20220919
                    switchOnNoteDialog.dismiss();
                    Constant.isShowSwitchOnNoteDialog = false;   //取消询问是否合闸对话框   //GC20231112
                    isSwitchOn = true;
                    switch (modeClick) {
                        case 0x22:
                            setMode(0x22);
                            modeTest();
                            break;
                        case 0x33:
                            simAutoTest();  //GC20220806
                            break;
                        case 0x55:
                            setMode(0x55);  //GC20221026
                            modeTest();
                            break;
                        default:
                            break;
                    }
                }
            });
            //点击“否，上一步”按钮事件  //最终是“返回”
            switchOnNoteDialog.setTvNo(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvTest.setEnabled(true);   //GC20220729
                    allowSetMode = true;    //点击“否，上一步”，方式可以点击 //GC20221019
                    if (Constant.isClickSim) {  //点击“否，上一步”，SIM方式记录取消   //GC20221017
                        Constant.isClickSim = false;
                    }
                    if (mode == ICM_DECAY) {    //点击“否，上一步”，直闪工作方式变为DC档   //GC20221110
                        handler.postDelayed(ModeActivity.this::setWorkingModeDC, 100);
                    }
                    ivAUTO.setEnabled(true);
                    timerSwitch.cancel();   //GC20220919
                    switchOnNoteDialog.dismiss();
                    Constant.isShowSwitchOnNoteDialog = false;   //取消询问是否合闸对话框   //GC20231112
                }
            });
        }
    }

    /**
     * 自定义放电电压确认对话框 //GC20230227
     */
    public EnsureHVDialog ensureHVDialog;
    public void showEnsureHVDialog() {
        ensureHVDialog = new EnsureHVDialog(this);
        Constant.isShowEnsureHV = true;   //放电电压确认对话框显示状态记录     //GC20230301
        if (!ensureHVDialog.isShowing()) {
            ensureHVDialog.show();
            //外部点击禁止
            ensureHVDialog.setCanceledOnTouchOutside(false);
            ensureHVDialog.tvNoteEnsure.setText("放电电压为：" + currentSetVoltage + "kV，请确认"); //seekBar32设定电压显示 //GC20220413
            //点击“放电”按钮事件
            ensureHVDialog.setTvEnsure(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isShowHV) {
                        setLlConfirmButton();   //在高压设置界面弹出   //GC20230227
                    } else if (isShowHVWait) {
                        setHvConfirm();         //在测距界面弹出   //GC20230227
                    }
                }
            });
            //点击“返回”按钮事件
            ensureHVDialog.setTvBack(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ensureHVDialog.dismiss();
                    Constant.isShowEnsureHV = false;   //放电电压确认对话框显示状态记录     //GC20230301
                }
            });
        }
    }

    /**
     * 自定义实时监测合闸提示对话框2  //GC20221203
     */
    public SwitchOnDialog switchOnDialog;
    public void showSwitchOnDialog() {
        switchOnDialog = new SwitchOnDialog(this);
        if (!switchOnDialog.isShowing()) {
            switchOnDialog.show();
            //外部点击禁止
            switchOnDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 自定义接地报警提示对话框 //GC20221206
     */
    public WarningNoteDialog warningNoteDialog;
    public void showWarningNoteDialog() {
        warningNoteDialog = new WarningNoteDialog(this);
        if (!warningNoteDialog.isShowing()) {
            warningNoteDialog.show();
            //外部点击禁止
            warningNoteDialog.setCanceledOnTouchOutside(false);
            if (Constant.WorkingMode == 2) {
                setHv0();   //直闪模式下只重置设定电压  //GC20221206
            } else {
                resetHvWorkingMode();   //接地报警时重置设定电压和工作方式
            }
            if (Constant.isShowHV) {
                //设定电压UI重置为0
                autoDialog.hvValue.setText(0 + ""); //seekBar32/16设定电压显示   //GC20230912
                if (currentGear == 2) { //32kV档位 / 8kV
//                    autoDialog.hvValue.setText("设定电压：" + 0 + "kV"); //seekBar32设定电压显示
                    autoDialog.seekBar32.setProgress(0);
                } else if (currentGear == 1) {//16kV档位 / 4kV
//                    autoDialog.hvValue.setText("设定电压：" + 0 + "kV"); //seekBar16设定电压显示
                    autoDialog.seekBar16.setProgress(0);
                }
                //工作方式UI重置单次
                autoDialog.rgGearMode.check(autoDialog.rbGearPULSE.getId());
                autoDialog.rbGearPULSE.setTextColor(getBaseContext().getResources().getColor(R.color.yellow5));
                autoDialog.llTIME.setVisibility(View.INVISIBLE);
            }
            if (Constant.isShowHVWait) {
                //设定电压UI重置
                hvWaitTriggerDialog.hvTriggerValue.setText(0 + "");  //seekBar32/16设定电压显示   //GC20230912
                if (currentGear == 2) { //32kV档位 / 8kV
//                    hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + 0 + "kV"); //seekBar32设定电压显示
                    hvWaitTriggerDialog.seekBar32Trigger.setProgress(0);
                } else if (currentGear == 1) {//16kV档位 / 4kV
//                    hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + 0 + "kV"); //seekBar16设定电压显示
                    hvWaitTriggerDialog.seekBar16Trigger.setProgress(0);
                }
                //工作方式UI重置
                if (Constant.WorkingMode != 2) {    //不是直闪模式才重置为单次  //GC20221206
                    hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(true);
                    hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_selector);
                }
            }
        }
    }

    /**
     * 倒计时0.6s处理    //GC20220919
     */
    private CountDownTimer timerSwitch = new CountDownTimer(500, 100) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //进入合闸提示对话框，开始发送合闸查询命令  //GC20220919    907主板调试需要屏蔽
            command = COMMAND_SWITCH_ON_QUERY;
            //数据
            dataTransfer = 0x01;
            startService();
            timerSwitch.start();    //倒计时结束，继续发送查询命令
        }

    };

    /**
     * 方式测试方法    //GC20220706
     */
    public void modeTest() {
        //SIM方式按照记忆的TDR范围初始化    //GC20220709
        if (mode == SIM) {
            range = rangeMemory;
        }
        handler.postDelayed(() -> {
            //范围
            setRange(range);
        }, 20);
        //如果未连接不执行（点击TDR、ICM、定点方式）  //GC20220825
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            allowSetMode = true;    //未连接,方式可以点击 //GC20221019
            return;
        }
        handler.postDelayed(() -> {
            //增益
            setGain(gain);
            Log.e("增益", "增益 = " + gain );
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
        //档位初始化（907主板调试需要屏蔽） //GC20220802
        if (!isFirstStart) {
            handler.postDelayed(() -> {
                //发送高压设定电压指令  //GC20211209
                isFirstStart = true;
                ConnectService.isHV = true;
                command = COMMAND_VOLTAGE_SET;
                //电压数值0
                dataTransfer = 0;
                dataTransfer2 = 0;
                //档位
                dataTransfer3 = 0x02;
                startService();
            }, 400);
            if ((mode == ICM) || (mode == SIM)) {   //初始化工作方式为单次    //GC20231031
                handler.postDelayed(this::setWorkingModeSingle, 450);
            }
        }

        if (isSetMode) {
            isSetMode = false;
            /*//改变测试方式时设定电压归0     //GC20220921
            if (Constant.setVoltage != 0) {
                handler.postDelayed(ModeActivity.this::setHv0, 350);
            }
            //改变测试方式时工作方式初始化为单次   //GC20220921
            if (Constant.WorkingMode != 0) {
                handler.postDelayed(ModeActivity.this::setWorkingModeSingle, 450);
            }*/ //GC20220921    在这里时间太长不合适，转移到fragment事件
        }

        //测试方式转换对话框提示逻辑
        if (mode == TDR) {
            if (modeMemory == SIM) {
                handler.postDelayed(ModeActivity.this::clickTest, 500);  //情形1：上次方式是SIM,直接测试    //增益调整后切换至TDR增益未响应100变500    //GC20220812
            } else if ((modeMemory == ICM) || (modeMemory == ICM_DECAY)) {          //添加上次是直闪情况(modeMemory == ICM)  //GC20221026
                showSwitchingDialog();                                              //情形2：上次方式是ICM（定点），弹方式转换对话框
                timerMode.start();
            }
        } else if (mode == ICM) {
            if ((modeMemory == ICM) || (modeMemory == ICM_DECAY)) {     //添加上次是直闪情况(modeMemory == ICM)  //GC20221026
                showAutoDialog();       //情形1：上次是ICM（定点），直接弹出高压操作对话框
            } else {
                showSwitchingDialog();  //情形2：上次是TDR或SIM,弹方式转换对话框
                timerMode.start();
            }
        } else if (mode == ICM_DECAY) { //添加直闪方式    //GC20221026
            if (modeMemory == ICM) {    //情形1：上次是ICM（定点），直接弹出等待触发对话框
                if (Constant.WorkingMode != 2) {    //切换到直闪方式时要设置到DC档-情形①  //GC20221110
                    handler.postDelayed(ModeActivity.this::setWorkingModeDC, 100);
                }
                handler.postDelayed(ModeActivity.this::clickTest, 600); //旧100
            } else {
                showSwitchingDialog();  //情形2：上次是TDR或SIM,弹方式转换对话框
                timerMode.start();
            }
        } else if (mode == SIM) {
            //SIM方式转换对话框消失   //GC20221114
            if (switchingDialog != null) {
                switchingDialog.dismiss();
            }
            showAutoDialog();    //方式（SIM）测试方法，第二步：弹出高压操作对话框  //GC20220806
        }
        modeMemory = mode;
    }

    /**
     * 方式（SIM）测试方法，第一步：转换至TDR方式自动寻找范围  //GC20220806
     */
    public void simAutoTest() {
        //如果未连接不执行（点击SIM方式） //GC20220825
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            setMode(0x33);
            return;
        }
        isLongClick = true;
        isReceiveData = true;
        if (modeMemory == TDR) {    //TDR方式下，直接自动测距
            showSwitchingDialog();  //SIM方式转换对话框弹出   //GC20221114
            handler.postDelayed(ModeActivity.this::clickTest, 100);
        } else {                    //ICM方式下，先弹方式转换对话框进入TDR方式，再进行自动测距
            modeClick = 0x11;
            setMode(0x11);
            modeTest();
        }
    }

    /**
     * 方式（SIM）测试方法，第二步：进入SIM方式界面    //GC20220806
     */
    public void clickSim() {
        //点击方式选项记录
        modeClick = 0x33;
        setMode(0x33);
        modeTest();
    }

    /**
     * 测试方式对话框取消倒计时处理   //GC20220903 转换开关时间缩短
     */
    private CountDownTimer timerMode = new CountDownTimer(3500, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            if (mode != ICM_DECAY) {    //不是直闪方式方式转换提示对话框可直接取消  //GC20221110
                if (!Constant.isClickSim) {  //SIM方式转换时对话框不消失 //GC20221114
                    //取消正在转换对话框
                    if (switchingDialog != null) {
                        switchingDialog.dismiss();
                    }
                    Toast.makeText(ModeActivity.this, R.string.switching_success, Toast.LENGTH_LONG).show();
                }
            }
            if (mode == TDR) {
                handler.postDelayed(ModeActivity.this::clickTest, 500); //ICM增益调整后切换至TDR增益未响应    //GC20220812
            } else if (mode == ICM_DECAY) {
                if (Constant.WorkingMode != 2) {    //切换到直闪方式时要设置到DC档-情形②  //GC20221110
                    handler.postDelayed(ModeActivity.this::setWorkingModeDC, 1200);
                }
//                handler.postDelayed(ModeActivity.this::clickTest, 1500); //添加直闪方式    //GC20221026 旧500
                handler.postDelayed(() -> {
                    //直闪方式等待时间长，取消正在转换对话框时间滞后 //GC20221110
                    if (switchingDialog != null) {
                        switchingDialog.dismiss();
                    }
                    Toast.makeText(ModeActivity.this, R.string.switching_success, Toast.LENGTH_LONG).show();
                    clickTest();
                }, 1700);
            } else {
                showAutoDialog();
            }
        }

    };

    /**
     * 电压档位对话框取消倒计时处理   //GC20220903 转换开关时间缩短
     */
    private CountDownTimer timerGear = new CountDownTimer(6000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            //取消正在转换对话框
            if (switchingDialog != null) {
                switchingDialog.dismiss();
            }
            if (clickGear) {
                //电压档位转换toast提示   //GC20220712
                Toast.makeText(ModeActivity.this, R.string.switching_gear_success, Toast.LENGTH_LONG).show();
                clickGear = false;
            }
        }

    };

    /**
     * 自定义方式、档位转换对话框    //GC20220710
     */
    private SwitchingDialog switchingDialog;

    private void showSwitchingDialog() {
        switchingDialog = new SwitchingDialog(this);
        if (!switchingDialog.isShowing()) {
            switchingDialog.show();
            if (clickGear) {
                //电压档位转换对话框提示语句 //GC20220712
                switchingDialog.tvNoteSw.setText(R.string.switching2);
            }
            switchingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 切换范围后发送测试命令    //GC20220706
     */
    public void rangeTest() {
        //记忆TDR方式下的范围   //GC20220709
        if (mode == TDR) {
            rangeMemory = range;
        }
        handler.postDelayed(() -> {
            //范围
            setRange(range);
        }, 20);
        //如果未连接不执行（点击范围）  //GC20220825
        if (!ConnectService.isConnected) {
            Toast.makeText(ModeActivity.this, R.string.test_on_no_connect, Toast.LENGTH_SHORT).show();
            allowSetRange = true;   //范围可以点击    //GC20220825
            return;
        }
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
        handler.postDelayed(ModeActivity.this::clickTest, 100);
    }

    /**
     * 自定义等待触发对话框创建 //GC20211215
     */
    private HvWaitTriggerDialog hvWaitTriggerDialog;
    private void showHvWaitTriggerDialog() {
        hvWaitTriggerDialog = new HvWaitTriggerDialog(this);
        if (!hvWaitTriggerDialog.isShowing()) {
            hvWaitTriggerDialog.show();
            Constant.isShowHVWait = true;   //等待触发对话框显示状态记录     //GC20221109
            //外部点击禁止
            hvWaitTriggerDialog.setCanceledOnTouchOutside(false);
            /*if (Constant.WorkingMode != 0) {
                //工作方式不是“单次”时无法点击“放电”按钮   //GC20211220
                hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
                hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);
                if (Constant.WorkingMode == 2) {    //工作方式是“直流”时无电压确认，可直接调节电压 //GC20221109
                    hvWaitTriggerDialog.llHv.setVisibility(View.GONE);
                    hvWaitTriggerDialog.llHvConfirm.setVisibility(View.GONE);
                }
            }*/
            /*if (Constant.WorkingMode == 1) {            //工作方式是“周期”时无法点击“放电”按钮   //GC20211220
                hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
                hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);
            } else if (Constant.WorkingMode == 2) {     //工作方式是“直流”时无电压确认，可直接调节电压 //GC20221109
                hvWaitTriggerDialog.llHv.setVisibility(View.GONE);
                hvWaitTriggerDialog.llHvConfirm.setVisibility(View.GONE);
                hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_capacitor_pulse_selector); //添加电容放电UI  //GC20221210
            }*/
            switch (Constant.WorkingMode) {
                case 0:
                    hvWaitTriggerDialog.llHvConfirm.setVisibility(View.GONE);    //“电压确认”按钮单次不显示    //GC20230227
                    break;
                case 1: //工作方式是“周期”时无法点击“放电”按钮   //GC20211220
                    hvWaitTriggerDialog.llHvConfirm.setVisibility(View.VISIBLE); //“电压确认”按钮周期才显示    //GC20230227
                    hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
                    hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);
                    break;
                case 2: //工作方式是“直流”时无电压确认，可直接调节电压 //GC20221109
                    hvWaitTriggerDialog.llHv.setVisibility(View.GONE);
                    hvWaitTriggerDialog.llHvConfirm.setVisibility(View.GONE);
                    hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_capacitor_pulse_selector); //添加电容放电UI  //GC20221210
                    break;
                default:
                    break;
            }
            //点击单次放电按钮事件
            hvWaitTriggerDialog.setIvTriggerPULSE(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constant.WorkingMode == 2) {
                        //发送电容放电指令  //GC20221210
                        command = 0xA0;
                        //数据
                        dataTransfer = 0x01;
                        startService();
                    } else {
                        if (needEnsure) {
                            showEnsureHVDialog();   //测距界面弹故障电压确认对话框  //GC20230227
                        } else {
                            setHVPULSE();   //测距界面对话框放电事件   //GC20230227
                        }
                    }
                }
            });
            //设定电压初始化   //GC20221102
            currentSetVoltage = Constant.setVoltage;
            if (mode == ICM_DECAY) {    //直闪方式可以直接调节设定电压    //GC20221109
                allowSetHv = true;
            } else {
                allowSetHv = true;  //可直接点击SeekBar //GC20230224 //allowSetHv = false; //GC20221108
            }
            hvWaitTriggerDialog.hvTriggerValue.setText(currentSetVoltage + ""); //seekBar32/16设定电压显示    //GC20230912
            if (currentGear == 2) { //32kV档位 / 8kV
//                hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV"); //seekBar32设定电压显示
                hvWaitTriggerDialog.seekBar16Trigger.setVisibility(View.GONE);
                hvWaitTriggerDialog.seekBar32Trigger.setVisibility(View.VISIBLE);
                hvWaitTriggerDialog.seekBar32Trigger.setProgress(currentSetVoltage);
                if (allowSetHv) {
                    hvWaitTriggerDialog.seekBar32Trigger.setEnabled(true);  //设定电压seekBar可以操作    //GC20221109
                } else {
                    hvWaitTriggerDialog.seekBar32Trigger.setEnabled(false); //GC20221108
                }
            } else if (currentGear == 1) {//16kV档位 / 4kV
//                hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV"); //seekBar16设定电压显示
                hvWaitTriggerDialog.seekBar32Trigger.setVisibility(View.GONE);
                hvWaitTriggerDialog.seekBar16Trigger.setVisibility(View.VISIBLE);
                hvWaitTriggerDialog.seekBar16Trigger.setProgress(currentSetVoltage);
                if (allowSetHv) {
                    hvWaitTriggerDialog.seekBar16Trigger.setEnabled(true); //设定电压seekBar可以操作    //GC20221109
                } else {
                    hvWaitTriggerDialog.seekBar16Trigger.setEnabled(false); //GC20221108
                }
            }
            judgeHVWait();  //测距对话框弹出时判断是否有动画 //GC20230224
            //设定电压点击seekBar32事件
            hvWaitTriggerDialog.setSeekBar32Trigger(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allowSetHv) {  //GC20221108
                        Toast.makeText(ModeActivity.this, "请点击电压微调按钮后再操作！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            //设定电压点击seekBar16事件
            hvWaitTriggerDialog.setSeekBar16Trigger(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allowSetHv) {  //GC20221108
                        Toast.makeText(ModeActivity.this, "请点击电压微调按钮后再操作！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            //设定电压变化监听seekBar32 //GC20221102
            hvWaitTriggerDialog.seekBar32Trigger.setOnProgressChangedListener(new KBubbleSeekBar32.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
//                    hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + progress + "kV");
                    hvWaitTriggerDialog.hvTriggerValue.setText(progress + ""); //GC20230912
                    if (currentSetVoltage != progress) {
                        if ((Constant.WorkingMode != 2) && !needSet0) {    //直流时、直流切换至单次电压归0时不记录电压变化状态 //GC20221010
                            isSetHVVoltageChanged = true; //GC20230224 seekBar32设定电压变化时状态记录
                        }
                    }
                    currentSetVoltage = progress;
                    //切换到直流时操作     //GC20221010
                    if (Constant.WorkingMode == 2) {
                        //记录改变的设定电压
                        Constant.setVoltage = currentSetVoltage;
                        tvInfoSetVoltage.setText(currentSetVoltage + "");
                        //发送高压设定电压指令    //GC20211209
                        ConnectService.isHV = true;
                        command = COMMAND_VOLTAGE_SET;
                        //电压数值
                        int temp = currentSetVoltage * 3276 / 32;    //GC20211227
//                        int temp =  currentSetVoltage * 3276 / 8;
                        dataTransfer = (byte) (temp >> 8 & 0xff);
                        dataTransfer2 = (byte) (temp & 0xff);
                        //档位
                        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                        startService();
                    } else {
                        judgeHVWait();  //触发界面判断seekBar32改变是否有动画 //GC20221115
                    }
                }

                @Override
                public void getProgressOnActionUp(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(KBubbleSeekBar32 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }
            });
            //设定电压变化监听seekBar16 //GC20221102
            hvWaitTriggerDialog.seekBar16Trigger.setOnProgressChangedListener(new KBubbleSeekBar16.OnProgressChangedListener() {
                @Override
                public void onProgressChanged(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
//                    hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + progress + "kV");
                    hvWaitTriggerDialog.hvTriggerValue.setText(progress + ""); //GC20230912
                    if (currentSetVoltage != progress) {
                        if ((Constant.WorkingMode != 2) && !needSet0) {    //直流时、直流切换至单次电压归0时不记录电压变化状态 //GC20221010
                            isSetHVVoltageChanged = true; //GC20230224 seekBar16设定电压变化时状态记录
                        }
                    }
                    currentSetVoltage = progress;
                    //切换到直流时操作     //GC20221010
                    if (Constant.WorkingMode == 2) {
                        //记录改变的设定电压
                        Constant.setVoltage = currentSetVoltage;
                        tvInfoSetVoltage.setText(currentSetVoltage + "");
                        //发送高压设定电压指令    //GC20211209
                        ConnectService.isHV = true;
                        command = COMMAND_VOLTAGE_SET;
                        //电压数值
                        int temp = currentSetVoltage * 3276 / 32;    //GC20211227
//                        int temp =  currentSetVoltage * 3276 / 8;
                        dataTransfer = (byte) (temp >> 8 & 0xff);
                        dataTransfer2 = (byte) (temp & 0xff);
                        //档位
                        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
                        startService();
                    } else {
                        judgeHVWait();  //触发界面判断seekBar16改变是否有动画 //GC20221115
                    }
                }

                @Override
                public void getProgressOnActionUp(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat) {

                }

                @Override
                public void getProgressOnFinally(KBubbleSeekBar16 bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {

                }
            });
            //设定电压点击“－”按钮事件 //GC20221102
            hvWaitTriggerDialog.setIvTriggerMinus(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allowSetHv) {  //GC20221108
                        Toast.makeText(ModeActivity.this, "请点击电压微调按钮后再操作！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentSetVoltage > 0) {
                        currentSetVoltage--;
                        isSetHVVoltageChanged = true; //GC20230224 点击“－”设定电压变化时状态记录
                        hvWaitTriggerDialog.hvTriggerValue.setText(currentSetVoltage + ""); //GC20230912
                        if (currentGear == 2) { //32kV档位 / 8kV
//                            hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV");
                            hvWaitTriggerDialog.seekBar32Trigger.setProgress(currentSetVoltage);
                        } else if (currentGear == 1) {//16kV档位 / 4kV
//                            hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV");
                            hvWaitTriggerDialog.seekBar16Trigger.setProgress(currentSetVoltage);
                        }
                    } else {
                        Toast.makeText(ModeActivity.this, R.string.hv_toast2, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //设定电压点击“+”按钮事件 //GC20221102
            hvWaitTriggerDialog.setIvTriggerPlus(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!allowSetHv) {  //GC20221108
                        Toast.makeText(ModeActivity.this, "请点击电压微调按钮后再操作！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (currentGear == 2) { //32kV档位 / 8kV
                        if (currentSetVoltage < 32) {    //GC20211227
                            currentSetVoltage++;
                            isSetHVVoltageChanged = true; //GC20230224 4kV点击“+”设定电压变化时状态记录
//                            hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV");
                            hvWaitTriggerDialog.hvTriggerValue.setText(currentSetVoltage + "");    //GC20230912
                            hvWaitTriggerDialog.seekBar32Trigger.setProgress(currentSetVoltage);
                        } else {
                            Toast.makeText(ModeActivity.this, R.string.hv_toast4, Toast.LENGTH_SHORT).show();
                        }
                    } else if (currentGear == 1) {//16kV档位 / 4kV
                        if (currentSetVoltage < 16) {    //GC20211227
                            currentSetVoltage++;
                            isSetHVVoltageChanged = true; //GC20230224 4kV点击“+”设定电压变化时状态记录
//                            hvWaitTriggerDialog.hvTriggerValue.setText("设定电压：" + currentSetVoltage + "kV");
                            hvWaitTriggerDialog.hvTriggerValue.setText(currentSetVoltage + "");    //GC20230912
                            hvWaitTriggerDialog.seekBar16Trigger.setProgress(currentSetVoltage);
                        } else {
                            Toast.makeText(ModeActivity.this, R.string.hv_toast3, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            //点击“电压确认”按钮事件  //GC20221102
            hvWaitTriggerDialog.setHvConfirm(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setHvConfirm(); //原封放入该函数里方便调用  //GC20230227
                }
            });
            //改为点击“电压微调”按钮事件    //GC20221102    //点击“高压设置”按钮事件
            hvWaitTriggerDialog.setHv(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //取消测试
                    cancelTest();
                    //按钮UI显示控制    //GC20221102
                    hvWaitTriggerDialog.tvNoteWait.setText(R.string.wait_trigger2);
                    hvWaitTriggerDialog.llHv.setVisibility(View.GONE);
                    hvWaitTriggerDialog.llHvConfirm.setVisibility(View.VISIBLE);
                    //“电压确认”按钮符号初始化   //GC20221115
                    if (valueAnimatorHv != null) {
                        valueAnimatorHv.end();    //闪烁动画关闭
                    }
                    if (currentSetVoltage != 0) {
                        hvWaitTriggerDialog.ivHvLightning.setImageResource(R.drawable.lightning);
                    } else {
                        hvWaitTriggerDialog.ivHvLightning.setImageResource(R.drawable.lightning_false);
                    }
                    if (Constant.WorkingMode == 0) {   //工作方式“单次”时，点击“调节电压”后，放电按钮不可点击   //GC20221108
                        hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(false);
                        hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_false);
                    }
                    allowSetHv = true;  //设定电压可调节   //GC20221108
                    if (currentGear == 2) { //32kV档位 / 8kV
                        hvWaitTriggerDialog.seekBar32Trigger.setEnabled(true);
                    } else if (currentGear == 1) {//16kV档位 / 4kV
                        hvWaitTriggerDialog.seekBar16Trigger.setEnabled(true);
                    }
                }
            });
            //点击“取消测试”按钮事件
            hvWaitTriggerDialog.setLlHvCancel(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mode == ICM_DECAY) {    //直闪方式取消测试操作    //GC20221109
                        //当前电压大于2kV     //GC20230112
                        if (Constant.currentVoltage >= 2) {
                            dangerousNote();
                            return;
                        } else {
                            cancelTest();
                        }
                    } else {
                        /*if (!allowSetHv) {  //在显示“电压确认”按钮的状态下退出，不需要取消测试命令   //GC20221108
                            cancelTest();
                        }*/
                        cancelTest();   //取消测试与是否可以设置SeekBar无关 //GC20230224
                    }
                    isSetHVVoltageChanged = false;    //GC20230224 点击取消测试设定电压变化状态重置
                    hvWaitTriggerDialog.dismiss();
                    Constant.isShowHVWait = false;   //等待触发对话框显示状态记录     //GC20221109
                    //取消测试时设定电压归0     //GC20220921
                    if (Constant.setVoltage != 0) {
                        handler.postDelayed(ModeActivity.this::setHv0, 20);
                    }
                    if (mode != ICM_DECAY) {    //直闪方式取消测试方式不重置 //GC20221109
                        //取消测试时工作方式初始化为单次   //GC20220921
                        if (Constant.WorkingMode != 0) {
                            handler.postDelayed(ModeActivity.this::setWorkingModeSingle, 120);
                        }
                    }
                }
            });
        }
    }

    /**
     * 测距界面“电压确认”按钮事件   //GC20230227
     */
    public void setHvConfirm() {
        //记录改变的设定电压
        Constant.setVoltage = currentSetVoltage;
        tvInfoSetVoltage.setText(currentSetVoltage + "");
        Toast.makeText(ModeActivity.this, R.string.hv_toast, Toast.LENGTH_SHORT).show();
        isSetHVVoltageChanged = false;  //点击“电压确认”设定电压变化状态记录     //GC20230224
        judgeHVWait();                  //点击“电压确认”判断是否有动画    //GC20230224
        //发送高压设定电压指令    //GC20211209
        ConnectService.isHV = true;
        command = COMMAND_VOLTAGE_SET;
        //电压数值
        int temp = currentSetVoltage * 3276 / 32;   //发送高压数值换算  //GC20211227
//        int temp =  currentSetVoltage * 3276 / 8;
        dataTransfer = (byte) (temp >> 8 & 0xff);
        dataTransfer2 = (byte) (temp & 0xff);
        //档位
        dataTransfer3 = currentGear;    //同 GEAR1 = 0x01 GEAR2 = 0x02
        startService();
        if (isShowEnsureHV) {
            setHVPULSE();   //放电电压确认对话框显示时才执行     //GC20230301
        }
        //按钮UI显示控制    //GC20221102
        /*hvWaitTriggerDialog.tvNoteWait.setText(R.string.wait_trigger);
        hvWaitTriggerDialog.llHv.setVisibility(View.VISIBLE);
        hvWaitTriggerDialog.llHvConfirm.setVisibility(View.GONE);
        if (Constant.WorkingMode == 0) {   //工作方式“单次”时，点击“电压确认”后，放电按钮可点击    //GC20221108
            hvWaitTriggerDialog.ivTriggerPULSE.setEnabled(true);
            hvWaitTriggerDialog.ivTriggerPULSE.setImageResource(R.drawable.bg_pulse_selector);
        }*/ //和高压设置界面调节电压方式统一 //GC20230224
        /*allowSetHv = false; //设定电压不可调节  //GC20221108
        if (currentGear == 2) { //32kV档位 / 8kV
            hvWaitTriggerDialog.seekBar32Trigger.setEnabled(false);
        } else if (currentGear == 1) {//16kV档位 / 4kV
            hvWaitTriggerDialog.seekBar16Trigger.setEnabled(false);
        }*/ //可直接点击SeekBar //GC20230224
        /*handler.postDelayed(() -> {
            //测试命令  //GC20221109
            command = COMMAND_TEST;
            dataTransfer = TESTING;
            startService();
        }, 50);*/   //测试状态下可以发送高压指令么？//GC20230227   待测试
    }

    /**
     * 取消测试操作
     */
    public void cancelTest() {
        if (canClickCancelButton) {
            //允许点击取消测试按钮为否
            canClickCancelButton = false;
            //TODO 20200407 取消测试后，恢复测试按钮可用性
            tvTest.setEnabled(true);
            Constant.isTesting = false;
            allowSetRange = true;
            allowSetMode = true;        //取消测试后按钮可以点击   //GC20221019
            allowSetOperation = true;   //取消测试后按钮可以点击   //GC20221019
            command = COMMAND_TEST;
            dataTransfer = CANCEL_TEST;
            startService();
        }
    }

    /**
     * 设定电压重置为“0”   //GC20220921
     */
    public void setHv0() {
        //改变设定电压值
        Constant.setVoltage = 0;
        //信息栏改变设定电压
        tvInfoSetVoltage.setText(0 + "");
        //发送高压设定电压指令
        ConnectService.isHV = true;
        command = COMMAND_VOLTAGE_SET;
        //电压数值
        dataTransfer = 0;
        dataTransfer2 = 0;
        //档位
        dataTransfer3 = Constant.gear;  //同 GEAR1 = 0x01 GEAR2 = 0x02
        startService();
    }

    /**
     * 工作方式“单次”指令下发 //GC20220921
     */
    public void setWorkingModeSingle() {
        //工作方式记录为单次
        Constant.WorkingMode = 0;
        //信息栏
        tvInfoWorkingMode.setText(R.string.PULSE);
        //发送单次工作方式指令
        command = COMMAND_WORKING_MODE; //单次
        //数据
        dataTransfer = 0x01;
        startService();
    }

    /**
     * 工作方式“直流”指令下发 //GC20221109
     */
    public void setWorkingModeDC() {
        //工作方式记录为直流
        Constant.WorkingMode = 2;
        //信息栏
        tvInfoWorkingMode.setText(R.string.DC);
        //发送直流工作方式指令
        command = COMMAND_WORKING_MODE; //直流
        //数据
        dataTransfer = 0x00;
        startService();
    }

    /**
     * 重置设定电压和工作方式  //GC20220921
     */
    public void resetHvWorkingMode() {
        //改变测试方式时设定电压归0
        if (Constant.setVoltage != 0) {
            setHv0();
            //改变测试方式时工作方式初始化为单次
            if (Constant.WorkingMode != 0) {
                handler.postDelayed(ModeActivity.this::setWorkingModeSingle, 100);
            }
        } else {
            //改变测试方式时工作方式初始化为单次
            if (Constant.WorkingMode != 0) {
                setWorkingModeSingle();
            }
        }
        //切换到TDR的操作移动到这里（直闪方式时需要重置方式、另外之前逻辑不完整，未考虑转换至TDR带电压情况）    //GC20221110
        if (modeClick == 0x11) {
            handler.postDelayed(() -> {
                setMode(0x11);
                modeTest();
            }, 600);
        }
    }

    /**
     * 故障反馈对话框创建    //GC20220920
     */
    private NoteDialog noteDialog;
    private void showNoteDialog() {
        //有对话框取先消掉  //GC20220920
        if (tDialog != null) {
            tDialog.dismiss();              //消正在接收数据对话框
        }
        if (hvWaitTriggerDialog != null) {
            hvWaitTriggerDialog.dismiss();  //消测试等待对话框
        }
        if (autoDialog != null) {
            autoDialog.dismiss();           //消高压设置对话框
        }
        if (switchOnNoteDialog != null) {
            switchOnNoteDialog.dismiss();   //消合闸提示对话框
            Constant.isShowSwitchOnNoteDialog = false;   //取消询问是否合闸对话框   //GC20231112
        }
        if (switchOnDialog != null) {
            switchOnDialog.dismiss();       //消实时监测合闸提示对话框  //GC20221203
        }
        if (warningNoteDialog != null) {
            warningNoteDialog.dismiss();    //消接地报警提示对话框    //GC20221206
        }
        noteDialog = new NoteDialog(this);
        if (!noteDialog.isShowing()) {
            noteDialog.show();
            Constant.isShowNoteDialog = true;   //已显示noteDialog //GC20231111
            //外部点击禁止
            noteDialog.setCanceledOnTouchOutside(false);
            /*if (!Constant.isWarning) {
                noteDialog.tvNote.setText(getResources().getString(R.string.hv_warning_note2));
            }*/ //接地报警指示更换提示    //GC20221206
            if (!Constant.isGear) {
                noteDialog.tvNote.setText(getResources().getString(R.string.hv_gear_note2));
            }
            if (!Constant.isWorkingMode) {
                noteDialog.tvNote.setText(getResources().getString(R.string.hv_working_mode_note2));
            }
            if (!Constant.isIgnitionCoil) {
                noteDialog.tvNote.setText(getResources().getString(R.string.hv_ignition_coil_note2));
            }
            if (!ConnectService.isConnected) {  //显示设备已断开   //GC20231111
                noteDialog.tvNote.setText(getResources().getString(R.string.connect_failed));
                noteDialog.tvOffline.setVisibility(View.VISIBLE);  //显示离线使用
            }
            //点击离线使用按钮事件
            noteDialog.setTvOffline(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteDialog.dismiss();
                    Constant.isShowNoteDialog = false;  //已取消noteDialog //GC20231111
                    Constant.isOffline = true;  //进入离线使用模式  //GC20231111

                }
            });
            //点击退出按钮事件
            noteDialog.setTvFaultQuit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteDialog.dismiss();
                    ModeActivity.this.finish(); //结束当前Activity
                    onDestroy();
                    Intent startMain = new Intent(Intent.ACTION_MAIN);
                    startMain.addCategory(Intent.CATEGORY_HOME);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                    System.exit(0); //退出程序
                }
            });
        }
    }

    /**
     * 比较按钮执行的方法  //GC20190703
     */
    public void clickCompare() {
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
        allowSetOperation = true;    //GC20221019
    }

    /**
     * 记忆按钮执行的方法  //GC20190703
     */
    public void clickMemory() {
        if (alreadyDisplayWave) {
            isMemory = true;
            waveCompare = new int[Constant.WaveData.length];
            System.arraycopy(Constant.WaveData, 0, waveCompare, 0, Constant.WaveData.length);
            //记录记忆数据的方式范围   //再优化   //GC20190703
            modeBefore = mode;
            rangeBefore = range;
        }
        allowSetOperation = true;    //GC20221019
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

    /**
     * 延长线弹窗初始化  //jk20210202
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

    /**
     * 保存延长线参数  //jk20210202
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

    /**
     * 双击返回键退出
     */
    private boolean mIsExit;
    @Override
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
     * 开始下载文件   //改为语言更换   //GC20230912
     */
    public void showUp() {
//        Toast.makeText(ModeActivity.this, R.string.check_update, Toast.LENGTH_SHORT).show();
        showLanguageChangeDialog(); //GC20230912
    }

    /**
     * 语言更换对话框   //GC20230912
     */
    private void showLanguageChangeDialog() {
        languageChangeDialog = new LanguageChangeDialog(this);
        if (!languageChangeDialog.isShowing()) {
            languageChangeDialog.show();
        }
        languageChangeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!languageChangeDialog.getCloseStatus()) {
                    //切换语言不需要重连
                    Intent intent = new Intent(ModeActivity.this, ConnectService.class);
                    stopService(intent);
                    Intent intentSplash = new Intent(ModeActivity.this, ModeActivity.class);
                    startActivity(intentSplash);

                }
            }
        });
    }

    /**
     * 语言更换成功后刷新并记录 //GC20230912
     */
    @Override
    protected void onResume() {
        String languageType = StateUtils.getString(MyApplication.getInstances(), AppConfig.CURRENT_LANGUAGE, "follow_sys"); //默认跟随系统语言  //GC20230912
        Constant.currentLanguage = languageType;    //GC20230912
        super.onResume();
    }

    @Override
    protected void onPause() {
        /*if (receiver != null) {
            unregisterReceiver(receiver);
        }*/
        super.onPause();
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
        //Destroy停止服务   //GC20230912
        Intent intent = new Intent(ModeActivity.this, ConnectService.class);    //GC20230912
        stopService(intent);
        super.onDestroy();
    }

}
